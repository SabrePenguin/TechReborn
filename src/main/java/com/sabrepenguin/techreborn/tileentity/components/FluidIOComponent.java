package com.sabrepenguin.techreborn.tileentity.components;

import com.sabrepenguin.techreborn.capability.stackhandler.SideConfig;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.tileentity.IOFluidManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FluidIOComponent implements MachineComponent {
    private final IOFluidManager ioFluidManager;
    private final FluidHandlerConcatenate fluidInventory;
    private final @Nullable IItemHandlerModifiable bucketInputs;
    private final @Nullable IItemHandlerModifiable bucketOutputs;
    private final @Nullable FluidHandlerConcatenate input;
    private final @Nullable List<FluidTank> inputTanks;
    private final @Nullable FluidHandlerConcatenate output;
    private final @Nullable List<FluidTank> outputTanks;

    private final TileEntity parent;

    public FluidIOComponent(TileEntity parent, int inputSize, int inputCapacity, int outputSize, int outputCapacity,
                            @Nullable IItemHandlerModifiable bucketInputs, @Nullable IItemHandlerModifiable bucketOutputs) {
        this.parent = parent;
        this.bucketInputs = bucketInputs;
        this.bucketOutputs = bucketOutputs;
        List<IFluidHandler> topHandlers = new ArrayList<>();
        List<SideConfig<IFluidHandler>> handlers = new ArrayList<>();
        if (inputSize == 0) {
            input = null;
            inputTanks = null;
        } else {
            inputTanks = new ArrayList<>();
            for (int i = 0; i < inputSize; i++) {
                inputTanks.add(new FluidTank(inputCapacity));
            }
            input = new FluidHandlerConcatenate(new ArrayList<>(inputTanks));
            topHandlers.add(input);
            handlers.add(new SideConfig<>(input, SlotAction.INPUT));
        }
        if (outputSize == 0) {
            output = null;
            outputTanks = null;
        } else {
            outputTanks = new ArrayList<>();
            for (int i = 0; i < inputSize; i++) {
                outputTanks.add(new FluidTank(outputCapacity));
            }
            output = new FluidHandlerConcatenate(new ArrayList<>(outputTanks));
            topHandlers.add(output);
            handlers.add(new SideConfig<>(output, SlotAction.OUTPUT));
        }
        fluidInventory = new FluidHandlerConcatenate(topHandlers);
        ioFluidManager = new IOFluidManager(handlers.toArray(new SideConfig[0]));
    }

    @Override
    public boolean update() {
        if (bucketInputs == null || bucketOutputs == null) return false;
        for (int i = 0; i < bucketInputs.getSlots(); i++) { // TODO: Ensure it doesn't run when nothing is happening
            ItemStack stackInSlot = bucketInputs.getStackInSlot(i);
            if (stackInSlot.isEmpty()) {
                continue;
            }
            ItemStack inputCopy = stackInSlot.copy(); // TODO: Replace with more efficient in-place stuff
            if (FluidUtil.getFluidHandler(inputCopy) == null) {
                continue;
            }
            FluidStack containedFluid = FluidUtil.getFluidContained(inputCopy);
            boolean hasFluid = containedFluid != null && containedFluid.amount > 0;
            assert inputTanks != null;
            FluidTank targetTank = inputTanks.get(i);
            FluidActionResult result;
            if (hasFluid) {
                result = FluidUtil.tryEmptyContainer(inputCopy, targetTank, Integer.MAX_VALUE, null, false);
            } else {
                result = FluidUtil.tryFillContainer(inputCopy, targetTank, Integer.MAX_VALUE, null, false);
            }

            if (result.isSuccess()) {
                ItemStack rStack = result.result;
                ItemStack remainder = bucketOutputs.insertItem(i, rStack, true);
                if (remainder.isEmpty()) {
                    ItemStack extracted = bucketInputs.extractItem(i, 1, false);
                    FluidActionResult finalResult;
                    if (hasFluid) {
                        finalResult = FluidUtil.tryEmptyContainer(extracted, targetTank, Integer.MAX_VALUE, null, true);
                    } else {
                        finalResult = FluidUtil.tryFillContainer(extracted, targetTank, Integer.MAX_VALUE, null, true);
                    }
                    if (finalResult.isSuccess()) {
                        bucketOutputs.insertItem(i, finalResult.getResult(), false);
                    }
                }
            }
        }
        return ioFluidManager.performTransfer(parent.getWorld(), parent.getPos());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("inputTanks") && inputTanks != null) {
            NBTTagList list = compound.getTagList("inputTanks", 10);
            for (int i = 0; i < inputTanks.size(); i++) {
                inputTanks.get(i).readFromNBT(list.getCompoundTagAt(i));
            }
        }
        if (compound.hasKey("outputTanks") && outputTanks != null) {
            NBTTagList list = compound.getTagList("outputTanks", 10);
            for (int i = 0; i < outputTanks.size(); i++) {
                outputTanks.get(i).readFromNBT(list.getCompoundTagAt(i));
            }
        }
        ioFluidManager.readFromNBT(compound.getCompoundTag("FluidManager"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (inputTanks != null) {
            NBTTagList list = new NBTTagList();
            for (FluidTank tank: inputTanks) {
                list.appendTag(tank.writeToNBT(new NBTTagCompound()));
            }
            compound.setTag("inputTanks", list);
        }
        if (outputTanks != null) {
            NBTTagList list = new NBTTagList();
            for (FluidTank tank: outputTanks) {
                list.appendTag(tank.writeToNBT(new NBTTagCompound()));
            }
            compound.setTag("outputTanks", list);
        }
        compound.setTag("FluidManager", ioFluidManager.writeToNBT());
        return compound;
    }

    public IOFluidManager getIoFluidManager() {
        return ioFluidManager;
    }

    public IFluidHandler getFluidHandler() {
        return fluidInventory;
    }

    @Nullable
    public FluidHandlerConcatenate getInput() {
        return input;
    }

    @Nullable
    public List<FluidTank> getInputTanks() {
        return inputTanks;
    }

    @Nullable
    public FluidHandlerConcatenate getOutput() {
        return output;
    }

    @Nullable
    public List<FluidTank> getOutputTanks() {
        return outputTanks;
    }

    public static class FluidIOComponentBuilder {
        TileEntity parent;
        IItemHandlerModifiable bucketInputs;
        IItemHandlerModifiable bucketOutputs;
        int inputSize;
        int inputCapacity;
        int outputSize;
        int outputCapacity;

        public FluidIOComponentBuilder(TileEntity parent) {
            this.parent = parent;
        }

        public FluidIOComponentBuilder input(int size, int capacity) {
            this.inputSize = size;
            this.inputCapacity = capacity;
            return this;
        }

        public FluidIOComponentBuilder handlers(IItemHandlerModifiable input, IItemHandlerModifiable output) {
            this.bucketInputs = input;
            this.bucketOutputs = output;
            return this;
        }

        public FluidIOComponentBuilder output(int size, int capacity) {
            this.outputSize = size;
            this.outputCapacity = capacity;
            return this;
        }

        public FluidIOComponent build() {
            return new FluidIOComponent(parent, inputSize, inputCapacity, outputSize, outputCapacity,
                    bucketInputs, bucketOutputs);
        }
    }
}
