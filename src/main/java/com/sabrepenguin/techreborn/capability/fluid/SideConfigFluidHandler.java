package com.sabrepenguin.techreborn.capability.fluid;

import com.sabrepenguin.techreborn.capability.ISlotChange;
import com.sabrepenguin.techreborn.capability.stackhandler.SideConfig;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SideConfigFluidHandler implements IFluidHandler, ISlotChange {

    private final IFluidHandler[] handlers;

    private final SlotAction[] handlerDirection;
    private final SlotAction[] tankDirection;

    private final int tanks;
    private final int allocSpace;

    @SafeVarargs
    public SideConfigFluidHandler(SideConfig<IFluidHandler>... configs) {
        this.handlers = new IFluidHandler[configs.length];
        this.handlerDirection = new SlotAction[configs.length];

        int tanksCount = 0;
        for (int i = 0; i < configs.length; i++) {
            this.handlers[i] = configs[i].handler();
            this.handlerDirection[i] = configs[i].action();
            tanksCount += configs[i].handler().getTankProperties().length;
        }
        tanks = tanksCount;

        this.tankDirection = new SlotAction[tanks];
        for (int i = 0; i < tanks; i++) {
            this.tankDirection[i] = SlotAction.DISABLED;
        }

        int space = 0;
        for (IFluidHandler handler: handlers) {
            space += handler.getTankProperties().length;
        }
        allocSpace = space;
    }

    public boolean rotateSlot(int handlerIndex, int tank) {
        if (tank < 0 || tank > this.tanks) return false;
        this.tankDirection[tank] = this.handlerDirection[handlerIndex].rotate(this.tankDirection[tank]);
        return true;
    }

    public SlotAction getSlotAction(int tank) {
        if (tank < 0 || tank > this.tanks) return SlotAction.DISABLED;
        return this.tankDirection[tank];
    }

    public SlotAction getHandlerAction(int handlerIndex) {
        if (handlerIndex < 0 || handlerIndex > handlerDirection.length) return SlotAction.DISABLED;
        return this.handlerDirection[handlerIndex];
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        List<IFluidTankProperties> properties = new ArrayList<>(allocSpace);
        for (IFluidHandler handler: handlers) {
            Collections.addAll(properties, handler.getTankProperties());
        }
        return properties.toArray(new IFluidTankProperties[0]);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0)
            return 0;
        resource = resource.copy();

        int totalFillAmount = 0;
        int globalTankIndex = 0;
        for (IFluidHandler handler : handlers)
        {
            IFluidTankProperties[] props = handler.getTankProperties();
            boolean allowed = false;
            for (int tankIndex = 0; tankIndex < props.length; tankIndex++, globalTankIndex++) {
                if (this.tankDirection[globalTankIndex] == SlotAction.INPUT || this.tankDirection[globalTankIndex] == SlotAction.BIDIRECTIONAL) {
                    allowed = true;
                    break;
                }
            }
            if (allowed) {
                int fillAmount = handler.fill(resource, doFill);
                totalFillAmount += fillAmount;
                resource.amount -= fillAmount;
                if (resource.amount <= 0)
                    break;
            }
        }
        return totalFillAmount;
    }

    @Override
    public @Nullable FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0)
            return null;
        resource = resource.copy();

        FluidStack totalDrained = null;
        int globalTankIndex = 0;
        for (IFluidHandler handler : handlers)
        {
            IFluidTankProperties[] props = handler.getTankProperties();
            boolean allowed = false;
            for (int tankIndex = 0; tankIndex < props.length; tankIndex++, globalTankIndex++) {
                if (this.tankDirection[globalTankIndex] == SlotAction.OUTPUT || this.tankDirection[globalTankIndex] == SlotAction.BIDIRECTIONAL) {
                    allowed = true;
                    break;
                }
            }

            if (allowed) {
                FluidStack drain = handler.drain(resource, doDrain);
                if (drain != null) {
                    if (totalDrained == null)
                        totalDrained = drain;
                    else
                        totalDrained.amount += drain.amount;

                    resource.amount -= drain.amount;
                    if (resource.amount <= 0)
                        break;
                }
            }
        }
        return totalDrained;
    }

    @Override
    public @Nullable FluidStack drain(int maxDrain, boolean doDrain) {
        if (maxDrain == 0)
            return null;

        FluidStack totalDrained = null;
        int globalTankIndex = 0;
        for (IFluidHandler handler : handlers)
        {
            IFluidTankProperties[] props = handler.getTankProperties();
            boolean allowed = false;
            for (int tankIndex = 0; tankIndex < props.length; tankIndex++, globalTankIndex++) {
                if (this.tankDirection[globalTankIndex] == SlotAction.OUTPUT || this.tankDirection[globalTankIndex] == SlotAction.BIDIRECTIONAL) {
                    allowed = true;
                    break;
                }
            }
            if (allowed) {
                if (totalDrained == null) {
                    totalDrained = handler.drain(maxDrain, doDrain);
                    if (totalDrained != null) {
                        maxDrain -= totalDrained.amount;
                    }
                } else {
                    FluidStack copy = totalDrained.copy();
                    copy.amount = maxDrain;
                    FluidStack drain = handler.drain(copy, doDrain);
                    if (drain != null) {
                        totalDrained.amount += drain.amount;
                        maxDrain -= drain.amount;
                    }
                }

                if (maxDrain <= 0)
                    break;
            }
        }
        return totalDrained;
    }

    public boolean runTransfer(World world, BlockPos pos, EnumFacing facing, boolean[] autoInput, boolean[] autoOutput) {
        TileEntity adjacentTE = world.getTileEntity(pos.offset(facing));
        if (adjacentTE == null || !adjacentTE.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()))
            return false;
        IFluidHandler adjacentInventory = adjacentTE.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
        if (adjacentInventory == null) // Theoretically never null
            return false;
        boolean changedInventory = false;
        IFluidTankProperties[] tanks = this.getTankProperties();
        IFluidTankProperties[] adjTanks = adjacentInventory.getTankProperties();
        for (int thisTank = 0; thisTank < tanks.length; thisTank++) {
            boolean autoIn = autoInput[thisTank];
            boolean autoOut = autoOutput[thisTank];
            SlotAction action = tankDirection[thisTank];
            if (autoIn && action == SlotAction.INPUT) {
                for (IFluidTankProperties adj : adjTanks) {
                    if (adj.getContents() != null && adj.getContents().getFluid() != null) {
                        FluidStack stack = adj.getContents();
                        FluidStack simulated = adjacentInventory.drain(stack, false);
                        if (simulated != null && simulated.amount != 0) {
                            int amountUsed = this.fill(simulated, false);
                            if (amountUsed > 0) {
                                stack.amount = amountUsed;
                                FluidStack stackCopy = stack.copy();
                                adjacentInventory.drain(stack, true); // TODO: This feels inefficient
                                this.fill(stackCopy, true);
                                break;
                            }
                        }
                    }
                }
            }
            if (autoOut && action == SlotAction.OUTPUT) {
                IFluidTankProperties currentFluidProperties = tanks[thisTank];
                if (currentFluidProperties.getContents() != null && currentFluidProperties.getContents().getFluid() != null) {
                    FluidStack fluidInSlot = currentFluidProperties.getContents();
                    if (fluidInSlot != null && fluidInSlot.getFluid() != null) {
                        FluidStack extracted = this.drain(fluidInSlot, false);
                        if (extracted != null && extracted.amount != 0) {
                            int amountPushed = adjacentInventory.fill(extracted, true);
                            if (amountPushed > 0) {
                                fluidInSlot.amount = amountPushed;
                                this.drain(fluidInSlot, true);
                                changedInventory = true;
                            }
                        }
                    }
                }
            }
        }

        return changedInventory;
    }

    public NBTTagCompound writeToNbt() {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (SlotAction action: this.tankDirection) {
            list.appendTag(new NBTTagByte(action.getByte()));
        }
        compound.setTag("SlotConfig", list);
        return compound;
    }

    public void readFromNbt(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("SlotConfig", 1);
        for (int i = 0; i < list.tagCount(); i++) {
            if (i < tankDirection.length) {
                tankDirection[i] = SlotAction.getByIndex(((NBTTagByte) list.get(i)).getByte());
            }
        }
    }
}
