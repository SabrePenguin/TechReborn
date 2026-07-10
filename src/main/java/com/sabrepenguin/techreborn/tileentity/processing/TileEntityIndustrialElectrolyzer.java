package com.sabrepenguin.techreborn.tileentity.processing;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.PowerDisplayWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRGuiTextures;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.jei.TRRecipePlugin;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import java.util.function.Supplier;

public class TileEntityIndustrialElectrolyzer extends TileEntityGeneric implements IGuiHolder<PosGuiData> {
	@SuppressWarnings("ConstantConditions")
	ItemStack emptyCell = new ItemStack(TRItems.cell);
	public TileEntityIndustrialElectrolyzer() {
		super(2, 4, TechRebornConfig.machineConfig.industrialElectrolyzer.maxEnergy,
				TechRebornConfig.machineConfig.industrialElectrolyzer.maxInput, RegistryHandler.instance().getIndustrialElectrolyzerRegistry());
		ioComponent.addInputFilter((itemStack, slot) -> {
			boolean isEmptyCell;
			if (!itemStack.isItemEqual(emptyCell))
				isEmptyCell = false;
			else {
				FluidStack fluidStack = FluidUtil.getFluidContained(itemStack);
				isEmptyCell = fluidStack == null || fluidStack.amount <= 0;
			}
			return (slot == 1) == isEmptyCell;
		});
	}

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.industrial_electrolyzer.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("inputs", 2, 100, true))
				.registerSlotGroup(new SlotGroup("outputs", 4))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioComponent.getManager(), getFacing,
				new SlotPosition(SlotAction.INPUT, 81, 62, 0, 0),
				new SlotPosition(SlotAction.INPUT, 47, 62, 0, 1),
				new SlotPosition(SlotAction.OUTPUT, 51, 20, 1, 2),
				new SlotPosition(SlotAction.OUTPUT, 71, 20, 1, 3),
				new SlotPosition(SlotAction.OUTPUT, 91, 20, 1, 4),
				new SlotPosition(SlotAction.OUTPUT, 111, 20, 1, 5),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 6)
		);

		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(new ProgressWidget()
						.size(20)
						.pos(80, 40)
						.texture(TRGuiTextures.UP_PROGRESS_ARROW, 20)
						.value(recipeComponent.getProgress())
						.direction(ProgressWidget.Direction.UP))
				.child(TRGuis.createJeiButton(TRRecipePlugin.INDUSTRIAL_ELECTROLYZER_UID)
						.pos(82, 40).size(16, 20))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyComponent.getEnergyStorage())
						.pos(9, 6));

		panel.child(new ItemSlot().pos(81, 62)
						.slot(new ModularSlot(ioComponent.getInputs(), 0)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)
								.filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(47, 62)
						.slot(new ModularSlot(ioComponent.getInputs(), 1)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)
								.filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new ItemSlot().pos(51, 20)
						.slot(new ModularSlot(ioComponent.getOutputs(), 0)
								.slotGroup("outputs")
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(71, 20)
						.slot(new ModularSlot(ioComponent.getOutputs(), 1)
								.slotGroup("outputs")
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(91, 20)
						.slot(new ModularSlot(ioComponent.getOutputs(), 2)
								.slotGroup("outputs")
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(111, 20)
						.slot(new ModularSlot(ioComponent.getOutputs(), 3)
								.slotGroup("outputs")
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(ioComponent.getBatterySlot(), 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));

		panel.child(TRGuis.createUpdateTab(upgradeComponent.getUpgrades(), "upgrades", upgradeComponent::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
