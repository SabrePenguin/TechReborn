package com.sabrepenguin.techreborn.tileentity.components;

import com.sabrepenguin.techreborn.capability.stackhandler.LimitedItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.RestrictedItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.SideConfig;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.tileentity.IOManager;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.IntConsumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemIOComponent implements MachineComponent {
	private final ItemStackHandler inventory;

	protected final RestrictedItemStackHandler inputs;
	protected final RestrictedItemStackHandler _internalOutput;
	protected final LimitedItemStackHandler output;
	protected final RestrictedItemStackHandler battery;
	protected final IOManager ioManager;

	private final TileEntity parent;

	public ItemIOComponent(TileEntity parent, int inputSize, int outputSize, boolean energySlot) {
		this.parent = parent;
		inventory = new ItemStackHandler(inputSize + outputSize + (energySlot ? 1 : 0));

		inputs = new RestrictedItemStackHandler(inventory, 0, inputSize);
		_internalOutput = new RestrictedItemStackHandler(inventory, inputSize, inputSize + outputSize);
		output = new LimitedItemStackHandler(_internalOutput, SlotAction.OUTPUT);
		battery = energySlot ? new RestrictedItemStackHandler(inventory, inputSize + outputSize): null;

		ioManager = new IOManager(SideConfig.input(inputs), SideConfig.output(output), SideConfig.bidirectional(battery));
	}

	public ItemIOComponent(TileEntity parent, int inputSize, int outputSize, boolean energySlot, IntConsumer runnable) {
		this.parent = parent;
		inventory = new ItemStackHandler(inputSize + outputSize + (energySlot ? 1 : 0)) {
			@Override
			protected void onContentsChanged(int slot) {
				if (slot < inputSize) {
					runnable.accept(slot);
				}
			}
		};

		inputs = new RestrictedItemStackHandler(inventory, 0, inputSize);
		_internalOutput = new RestrictedItemStackHandler(inventory, inputSize, inputSize + outputSize);
		output = new LimitedItemStackHandler(_internalOutput, SlotAction.OUTPUT);
		battery = energySlot ? new RestrictedItemStackHandler(inventory, inputSize + outputSize): null;

		ioManager = new IOManager(SideConfig.input(inputs), SideConfig.output(output), SideConfig.bidirectional(battery));
	}

	public @Nullable IItemHandlerModifiable getBatterySlot() {
		return battery;
	}

	public IOManager getManager() {
		return ioManager;
	}

	public IItemHandlerModifiable getInventory() {
		return inventory;
	}

	public @Nullable IItemHandlerModifiable getInputs() {
		return inputs;
	}

	public @Nullable IItemHandlerModifiable getOutputs() {
		return output;
	}

	@ApiStatus.Internal
	public @Nullable IItemHandlerModifiable getInternalOutputs() {
		return _internalOutput;
	}

	@Override
	public boolean update() {
		return ioManager.performTransfer(parent.getWorld(), parent.getPos());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		ioManager.readFromNBT(compound.getCompoundTag("IOManager"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setTag("IOManager", ioManager.writeToNBT());
		return compound;
	}
}
