package com.sabrepenguin.techreborn.tileentity.processing;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.gui.LargeItemSlot;
import com.sabrepenguin.techreborn.gui.PowerDisplayWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.jei.TRRecipePlugin;
import com.sabrepenguin.techreborn.tileentity.TileEntityIOManager;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import com.sabrepenguin.techreborn.util.UpgradeUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityRecycler extends TileEntityIOManager implements IGuiHolder<PosGuiData> {

	private final int maxReceive;

	protected int totalProcessTime;
	private float cachedProcessMultiplier;
	private final int energyCost;
	private float cachedEnergyMultiplier;
	protected int cachedProcessTime;
	private int cachedEnergyCost;

	private final ItemStack recipeOutput = new ItemStack(TRItems.part, 1, Part.PartMeta.scrap.metadata());
	private final Random random = new Random();
	private final int chance = 6;
	protected int processTime = 0;
	private boolean isActive = false;

	public TileEntityRecycler() {
		super(1, 1, 4000, 128, 0, true);
		this.maxReceive = 128;
		totalProcessTime = 45;
		cachedProcessMultiplier = 1.0f;
		energyCost = 8;
		cachedEnergyMultiplier = 1.0f;
		cachedProcessTime = totalProcessTime;
		cachedEnergyCost = energyCost;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		processTime = compound.getInteger("Processed");
		isActive = compound.getBoolean("IsActive");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Processed", processTime);
		compound.setBoolean("IsActive", isActive);
		return super.writeToNBT(compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		boolean previousState = isActive;
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.getNbtCompound());
		if (world != null && this.world.isRemote && previousState != isActive) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	private void refreshRecipe() {
		refreshRecipe = false;
		processTime = 0;
	}

	private boolean canProcess() {
		ItemStack output = this.output.getStackInSlot(0);
		if (inputs.getStackInSlot(0).isEmpty()) return false;
		if (output.isEmpty()) return true;
		if (!output.isItemEqual(recipeOutput)) return false;
		int res = output.getCount() + recipeOutput.getCount();
		return res <= this.output.getSlotLimit(0) && res <= output.getMaxStackSize();
	}

	private void processItem() {
		ItemStack outputItem = output.getStackInSlot(0);
		if (outputItem.isEmpty()) {
			if (random.nextInt(chance) == 0)
				_internalOutput.insertItem(0, recipeOutput.copy(), false);
		}
		else if (outputItem.isItemEqual(recipeOutput)) {
			ItemStack outputCopy = recipeOutput.copy();
			ItemStack remainder = _internalOutput.insertItem(0, outputCopy, true);
			if (remainder.isEmpty()) {
				if (random.nextInt(chance) == 0)
					_internalOutput.insertItem(0, outputCopy, false);
			} else {
				// Not enough room in output
				return;
			}
		} else {
			// Output does not match
			return;
		}
		inputs.extractItem(0, 1, false);
	}

	private void updateState(boolean state) {
		IBlockState currentState = world.getBlockState(pos);
		world.setBlockState(pos, currentState.withProperty(BlockHorizontalMachine.ACTIVE, state), 3);
	}

	// ITickable
	@Override
	public void update() {
		if (world.isRemote)
			return;
		super.update();

		boolean isDirty = false;

		if (refreshRecipe) {
			refreshRecipe();
		}

		boolean active = false;
		if (canProcess()) {
			if (energyStorage.internalExtract(cachedEnergyCost, true) == cachedEnergyCost) {
				energyStorage.internalExtract(cachedEnergyCost, false);
				processTime++;
				active = true;
			}
			if (processTime >= cachedProcessTime) {
				processItem();
				processTime = 0;
				isDirty = true;
			}
		} else if (processTime > 0) {
			processTime = 0;
			isDirty = true;
		}

		if (active != isActive) {
			isActive = active;
			updateState(active);
			isDirty = true;
		}

		if (isDirty) {
			markDirty();
		}
	}

	// TileEntityIOManager
	@Override
	protected void recalculateCosts() {
		// Processing
		this.cachedProcessMultiplier = UpgradeUtils.getProcessingTimeMultiplier(upgrades);
		this.cachedProcessTime = (int) (cachedProcessMultiplier * totalProcessTime);
		this.cachedEnergyMultiplier = UpgradeUtils.getTotalCostMultiplier(upgrades);
		this.cachedEnergyCost = (int) (cachedEnergyMultiplier * energyCost);

		// Input, Output, Capacity
		this.energyStorage.setMaxEnergy(UpgradeUtils.getTotalEnergyStorageIncrease(upgrades, baseCapacity));

		float newTransfer = UpgradeUtils.getEnergyTransferMultiplier(upgrades);
		this.energyStorage.setMaxReceive((int) (maxReceive * newTransfer));
		this.energyStorage.setMaxExtract(cachedEnergyCost);
	}

	// IGuiHolder
	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.recycler.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("inputs", 2, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioManager, getFacing,
				new SlotPosition(SlotAction.INPUT, 55, 39, 0, 0),
				SlotPosition.BigSlot(SlotAction.OUTPUT, 101, 39, 1, 0),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 0)
		);
		DoubleSyncValue progress = new DoubleSyncValue(() -> (double) processTime / cachedProcessTime,
				value -> processTime = (int) (value * cachedProcessTime));

		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(new ProgressWidget()
						.size(20)
						.pos(75, 39)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(progress))
				.child(TRGuis.createJeiButton(TRRecipePlugin.RECYCLER_UID)
						.pos(75, 41).size(20, 15))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyStorage)
						.pos(9, 6));

		panel.child(new ItemSlot().pos(55, 39)
						.slot(new ModularSlot(inputs, 0)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)
								.filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new LargeItemSlot().pos(101, 39)
						.slot(new ModularSlot(output, 0)
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(battery, 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));

		panel.child(TRGuis.createUpdateTab(upgrades, "upgrades", this::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
