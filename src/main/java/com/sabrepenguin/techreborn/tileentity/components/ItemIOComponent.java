package com.sabrepenguin.techreborn.tileentity.components;

import com.sabrepenguin.techreborn.capability.stackhandler.LimitedItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.RestrictedItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.SideConfig;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.tileentity.IOManager;
import com.sabrepenguin.techreborn.util.ObjIntPredicate;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemIOComponent implements MachineComponent {
	private final ItemStackHandler inventory;

	@Nullable
	protected final RestrictedItemStackHandler inputs;
	@Nullable
	protected final RestrictedItemStackHandler _internalOutput;
	@Nullable
	protected final LimitedItemStackHandler output;
	@Nullable
	protected final RestrictedItemStackHandler fluidItemInputs;
	@Nullable
	protected final RestrictedItemStackHandler _internalFluidItemOutputs;
	protected final LimitedItemStackHandler fluidItemOutputs;
	protected final RestrictedItemStackHandler battery;
	protected final IOManager ioManager;

	private final TileEntity parent;

	// TODO: Make everything use this instead
	ItemIOComponent(TileEntity parent, int inputSize, int outputSize, int bidirectionalSize, boolean energySlot, IntConsumer runnable, int fluidTanks) {
		this.parent = parent;
		int invSize = inputSize + outputSize + bidirectionalSize + (energySlot ? 1 : 0) + (fluidTanks * 2);
		if (inputSize > 0 && fluidTanks != 0) {
			inventory = new ItemStackHandler(invSize) {
				@Override
				protected void onContentsChanged(int slot) {
					if (slot < inputSize || (invSize - (fluidTanks * 2) < slot) && slot < invSize) {
						runnable.accept(slot);
					}
				}
			};
		} else if (inputSize > 0) {
			inventory = new ItemStackHandler(invSize) {
				@Override
				protected void onContentsChanged(int slot) {
					if (slot < inputSize) {
						runnable.accept(slot);
					}
				}
			};
		} else {
			inventory = new ItemStackHandler(invSize);
		}
		//TODO: Make the calculation logic a little cleaner. What is the number where?
		if (inputSize != 0) { //TODO: Fix this logic. What if inputSize = 1 and bidirectionalSize = 1?
			inputs = new RestrictedItemStackHandler(inventory, 0, inputSize);
		} else if (bidirectionalSize != 0) {
			inputs = new RestrictedItemStackHandler(inventory, 0, bidirectionalSize);
		} else {
			inputs = null;
		}
		if (outputSize != 0) {
			_internalOutput = new RestrictedItemStackHandler(inventory, inputSize, inputSize + outputSize);
			output = new LimitedItemStackHandler(_internalOutput, SlotAction.OUTPUT);
		} else {
			_internalOutput = null;
			output = null;
		}
		battery = energySlot ? new RestrictedItemStackHandler(inventory, inputSize + outputSize): null;
		if (fluidTanks != 0) {
			int fluidTanksStart = inputSize + outputSize + (energySlot ? 1 : 0);
			fluidItemInputs = new RestrictedItemStackHandler(inventory, fluidTanksStart, fluidTanksStart + fluidTanks);
			_internalFluidItemOutputs = new RestrictedItemStackHandler(inventory, fluidTanksStart + fluidTanks, fluidTanksStart + (fluidTanks * 2));
			fluidItemOutputs = new LimitedItemStackHandler(_internalFluidItemOutputs, SlotAction.OUTPUT);
		} else {
			fluidItemInputs = null;
			_internalFluidItemOutputs = null;
			fluidItemOutputs = null;
		}
		List<SideConfig> configs = new ArrayList<>();
		if (inputSize != 0) {
			configs.add(SideConfig.input(inputs));
		} else if (bidirectionalSize != 0) {
			configs.add(SideConfig.bidirectional(inputs));
		}
		if (outputSize != 0)
			configs.add(SideConfig.output(output));
		if (battery != null) {
			configs.add(SideConfig.bidirectional(battery));
		}
		if (fluidTanks != 0) {
			configs.add(SideConfig.input(fluidItemInputs));
			configs.add(SideConfig.output(fluidItemOutputs));
		}
		ioManager = new IOManager(configs.toArray(new SideConfig[0]));
	}

	public ItemIOComponent(TileEntity parent, int inputSize, int outputSize, boolean energySlot) {
		this.parent = parent;
		inventory = new ItemStackHandler(inputSize + outputSize + (energySlot ? 1 : 0));

		inputs = new RestrictedItemStackHandler(inventory, 0, inputSize);
		_internalOutput = new RestrictedItemStackHandler(inventory, inputSize, inputSize + outputSize);
		output = new LimitedItemStackHandler(_internalOutput, SlotAction.OUTPUT);
		battery = energySlot ? new RestrictedItemStackHandler(inventory, inputSize + outputSize): null;

		ioManager = new IOManager(SideConfig.input(inputs), SideConfig.output(output), SideConfig.bidirectional(battery));
		fluidItemInputs = null;
		_internalFluidItemOutputs = null;
		fluidItemOutputs = null;
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
		if (outputSize != 0) {
			_internalOutput = new RestrictedItemStackHandler(inventory, inputSize, inputSize + outputSize);
			output = new LimitedItemStackHandler(_internalOutput, SlotAction.OUTPUT);
		} else {
			_internalOutput = null;
			output = null;
		}
		battery = energySlot ? new RestrictedItemStackHandler(inventory, inputSize + outputSize): null;
		List<SideConfig> configs = new ArrayList<>();
		configs.add(SideConfig.input(inputs));
		if (outputSize != 0) {
			configs.add(SideConfig.output(output));
		}
		if (battery != null) {
			configs.add(SideConfig.bidirectional(battery));
		}
		ioManager = new IOManager(configs.toArray(new SideConfig[0]));
		fluidItemInputs = null;
		_internalFluidItemOutputs = null;
		fluidItemOutputs = null;
	}

	public ItemIOComponent addInputFilter(ObjIntPredicate<ItemStack> filter) {
		if (inputs != null)
			inputs.setFilter(filter);
		return this;
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

	@Nullable
	public RestrictedItemStackHandler getFluidItemInputs() {
		return fluidItemInputs;
	}

	@Nullable
	public LimitedItemStackHandler getFluidItemOutputs() {
		return fluidItemOutputs;
	}

	@ApiStatus.Internal
	public @Nullable IItemHandlerModifiable getInternalOutputs() {
		return _internalOutput;
	}

	/**
	 * A helper intended to allow easy insertion and removal into a slot the player should never insert into
	 * @return An insertable version of getFluidItemOutputs()
	 */
	@ApiStatus.Internal
	public @Nullable IItemHandlerModifiable getInternalFluidOutputs() {
		return _internalFluidItemOutputs;
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

	public static class ItemIOComponentBuilder {
		final TileEntity parent;
		boolean hasEnergySlot;
		IntConsumer runnable;

		int inputSize;
		int outputSize;
		int bidirectionalSize;
		int interactableFluidTanks;

		public ItemIOComponentBuilder(TileEntity parent) {
			this.parent = parent;
			hasEnergySlot = false;
			runnable = null;
		}

		public ItemIOComponentBuilder inputSize(int size) {
			if (bidirectionalSize != 0) {
				throw new RuntimeException("Input size cannot be larger than 0 if bidirectional is larger than 0");
			}
			inputSize = size;
			return this;
		}

		public ItemIOComponentBuilder outputSize(int size) {
			outputSize = size;
			return this;
		}

		public ItemIOComponentBuilder bidirectionalSize(int size) {
			if (inputSize > 0) {
				throw new RuntimeException("Bidirectional size cannot be larger than 0 if input is larger than 0");
			}
			bidirectionalSize = size;
			return this;
		}

		public ItemIOComponentBuilder fluidTanksSize(int size) {
			interactableFluidTanks = size;
			return this;
		}

		public ItemIOComponentBuilder hasEnergySlot() {
			hasEnergySlot = true;
			return this;
		}

		public ItemIOComponentBuilder onSlotChangedAction(IntConsumer runnable) {
			this.runnable = runnable;
			return this;
		}

		public ItemIOComponent build() {
			return new ItemIOComponent(parent, inputSize, outputSize, bidirectionalSize, hasEnergySlot, runnable, interactableFluidTanks);
		}
	}
}
