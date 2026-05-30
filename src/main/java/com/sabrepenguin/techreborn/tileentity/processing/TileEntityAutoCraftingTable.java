package com.sabrepenguin.techreborn.tileentity.processing;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.GenericSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ItemDisplayWidget;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.ToggleButton;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.*;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.registries.VanillaRegistry;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import com.sabrepenguin.techreborn.util.ItemStackWrapper;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityAutoCraftingTable extends TileEntityProcessing implements IGuiHolder<PosGuiData> {

	protected boolean locked = false;
	protected final Object2ObjectMap<ItemStackWrapper, IntList> balanceCache = new Object2ObjectOpenHashMap<>(9);

	public TileEntityAutoCraftingTable() {
		super(9, 1, TechRebornConfig.machineConfig.autocrafter.maxEnergy,
				TechRebornConfig.machineConfig.autocrafter.maxInput, RegistryHandler.instance().getCraftingRegistry());
		if (this.registry instanceof VanillaRegistry vanillaRegistry) {
			vanillaRegistry.bind(this);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("locked")) {
			locked = compound.getBoolean("locked");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("locked", locked);
		return super.writeToNBT(compound);
	}

	@Override
	protected boolean canProcess() {
		if (locked) {
			for (int i = 0; i < inputs.getSlots(); i++) {
				ItemStack stack = inputs.getStackInSlot(i);
				if (stack.isEmpty())
					continue;
				if (stack.getCount() == 1)
					return false;
			}
		}
		return super.canProcess();
	}

	@Override
	protected void processItem() {
		ItemStack outputItem = output.getStackInSlot(0);
		ItemStack cachedOutput = cachedRecipe.getOutput().get(0);
		if (outputItem.isEmpty()) {
			_internalOutput.insertItem(0, cachedOutput.copy(), false);
		}
		else if (outputItem.isItemEqual(cachedOutput)) {
			ItemStack outputCopy = cachedOutput.copy();
			ItemStack remainder = _internalOutput.insertItem(0, outputCopy, true);
			if (remainder.isEmpty()) {
				_internalOutput.insertItem(0, outputCopy, false);
			} else {
				// Not enough room in output
				return;
			}
		} else {
			// Output does not match
			return;
		}
		List<ItemStack> leftovers = RecipeUtils.applyRecipeToHandler(inputs, cachedRecipe.getInputs());
		if (!leftovers.isEmpty()) {
			BlockPos pos = this.pos.offset(world.getBlockState(this.pos).getValue(BlockHorizontal.FACING));
			for (ItemStack stack : leftovers) {
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			}
		}
	}

	@Override
	public void update() {
		if (world.isRemote)
			return;
		if (locked) {
			if (refreshRecipe) {
				InventoryUtils.setBalanceCache(inputs, balanceCache);
				InventoryUtils.balanceHandler(inputs, balanceCache);
			}
		}
		super.update();
	}

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.auto_crafting_table.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("inputs", 9, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true))
				.syncValue("display_item", GenericSyncValue.forItem(() -> {
					if (cachedRecipe != null)
						return cachedRecipe.getOutput().get(0);
					return ItemStack.EMPTY;
				}, null));
		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioManager, getFacing,
				new SlotPosition(SlotAction.INPUT, 30, 22, 0, 0),
				new SlotPosition(SlotAction.INPUT, 48, 22, 0, 1),
				new SlotPosition(SlotAction.INPUT, 66, 22, 0, 2),
				new SlotPosition(SlotAction.INPUT, 30, 40, 0, 3),
				new SlotPosition(SlotAction.INPUT, 48, 40, 0, 4),
				new SlotPosition(SlotAction.INPUT, 66, 40, 0, 5),
				new SlotPosition(SlotAction.INPUT, 30, 58, 0, 6),
				new SlotPosition(SlotAction.INPUT, 48, 58, 0, 7),
				new SlotPosition(SlotAction.INPUT, 66, 58, 0, 8),
				SlotPosition.BigSlot(SlotAction.OUTPUT, 120, 39, 1, 0),
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
						.pos(90, 49)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(progress))
				.child(TRGuis.createJeiButton(VanillaRecipeCategoryUid.CRAFTING)
						.pos(90, 51).size(20, 15))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyStorage)
						.pos(9, 6));

		panel.child(new ItemSlot().pos(30, 22)
						.slot(new ModularSlot(inputs, 0)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(48, 22)
						.slot(new ModularSlot(inputs, 1)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(66, 22)
						.slot(new ModularSlot(inputs, 2)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(30, 40)
						.slot(new ModularSlot(inputs, 3)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(48, 40)
						.slot(new ModularSlot(inputs, 4)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(66, 40)
						.slot(new ModularSlot(inputs, 5)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(30, 58)
						.slot(new ModularSlot(inputs, 6)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(48, 58)
						.slot(new ModularSlot(inputs, 7)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(66, 58)
						.slot(new ModularSlot(inputs, 8)
								.slotGroup("inputs").changeListener(this::onInputChange).filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new LargeItemSlot().pos(120, 49)
						.slot(new ModularSlot(output, 0)
								.accessibility(false, true))
						.size(24))
				.child(new ItemDisplayWidget().pos(120, 22)
						.syncHandler("display_item")
						.displayAmount(true))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(battery, 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));

		panel.child(new ToggleButton()
				.right(5)
				.top(5)
				.size(16, 12)
				.stateOverlay(TRGuiTextures.LOCK)
				.value(new BooleanSyncValue(() -> locked, val -> {
					this.markDirty();
					locked = val;
					if (locked) {
						refreshRecipe = true; // TODO find way to set another value
					}
				})));

		panel.child(TRGuis.createUpdateTab(upgrades, "upgrades", this::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
