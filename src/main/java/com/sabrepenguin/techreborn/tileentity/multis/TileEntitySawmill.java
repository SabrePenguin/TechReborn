package com.sabrepenguin.techreborn.tileentity.multis;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.FluidSlotSyncHandler;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.PowerDisplayWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRFluidSlot;
import com.sabrepenguin.techreborn.gui.TRItemSlot;
import com.sabrepenguin.techreborn.gui.TRGuiTextures;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.jei.TRRecipePlugin;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;
import java.util.function.Supplier;

public class TileEntitySawmill extends TileEntityGenericFluidMulti implements IGuiHolder<PosGuiData> {
    public TileEntitySawmill() {
        super(1, 3, 1, 16_000, 0, 0,
                TechRebornConfig.machineConfig.industrialSawmill.maxEnergy,
                TechRebornConfig.machineConfig.industrialSawmill.maxInput,
                RegistryHandler.instance().getSawmillRegistry(), MultiblockStructure.SAWMILL,
                TechRebornConfig.machineConfig.industrialSawmill.async);
    }

    @Override
    public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
        String lang = "tile.techreborn.industrial_sawmill.name";
        ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
        syncManager.syncValue("overlay", multiblockComponent.getValid());
        syncManager.registerSlotGroup(new SlotGroup("inputs", 1, 100, true))
                .registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
                .registerSlotGroup(new SlotGroup("power", 1, 50, true))
                .registerSlotGroup(new SlotGroup("tanks", 1, 150, true));

        Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
        TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioComponent.getManager(), getFacing,
                Arrays.asList(
                new SlotPosition(SlotAction.INPUT, 81, 35, 0, 0),
                new SlotPosition(SlotAction.OUTPUT, 126, 17, 1, 1),
                new SlotPosition(SlotAction.OUTPUT, 126, 35, 1, 2),
                new SlotPosition(SlotAction.OUTPUT, 126, 53, 1, 3),
                new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 4),
                new SlotPosition(SlotAction.INPUT, 30, 24, 3, 5),
                new SlotPosition(SlotAction.OUTPUT, 30, 46, 4, 6)),
                this.fluidIoComponent.getIoFluidManager(),
                Arrays.asList(
                        new SlotPosition(SlotAction.INPUT, 54, 18, 0, 0, 17, 51)
                )
        );

        panel.bindPlayerInventory()
                .child(IKey.lang(lang)
                        .asWidget()
                        .align(Alignment.TopCenter)
                        .top(7))
                .child(new ProgressWidget()
                        .size(20)
                        .pos(103, 34)
                        .texture(GuiTextures.PROGRESS_ARROW, 20)
                        .value(recipeComponent.getProgress()))
                .child(TRGuis.createJeiButton(TRRecipePlugin.DISTILLATION_TOWER_UID)
                        .pos(103, 36).size(20, 15))
                .child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyComponent.getEnergyStorage())
                        .pos(9, 6));

        if (ioComponent.getInputs() == null || ioComponent.getOutputs() == null || ioComponent.getBatterySlot() == null ||
            ioComponent.getFluidItemInputs() == null || ioComponent.getFluidItemOutputs() == null) {
            TechReborn.LOGGER.error("A portion of ioComponent was null when it should be impossible to do so.");
            return panel;
        }

        panel.child(new ItemSlot().pos(81, 35)
                        .slot(new ModularSlot(ioComponent.getInputs(), 0)
                                .slotGroup("inputs")
                                .changeListener(this::onInputChange)
                                .filter(InventoryUtils.IS_UPGRADE.negate())))
                .child(new ItemSlot().pos(126, 17)
                        .slot(new ModularSlot(ioComponent.getOutputs(), 0)
                                .accessibility(false, true)))
                .child(new ItemSlot().pos(126, 35)
                        .slot(new ModularSlot(ioComponent.getOutputs(), 1)
                                .accessibility(false, true)))
                .child(new ItemSlot().pos(126, 53)
                        .slot(new ModularSlot(ioComponent.getOutputs(), 2)
                                .accessibility(false, true)))
                .child(new ItemSlot().pos(7, 59)
                        .slot(new ModularSlot(ioComponent.getBatterySlot(), 0)
                                .slotGroup("power")
                                .filter(InventoryUtils.IS_POWER_ITEM)))
                .child(new TRFluidSlot()
                        .syncHandler(new FluidSlotSyncHandler(this.fluidIoComponent.getInputTanks().get(0)))
                        .alwaysShowFull(false)
                        .contentPadding(2, 4)
                        .pos(54, 18)
                        .size(17, 51)
                        .overlay(TRGuiTextures.FLUID_TANK_OVERLAY))
                .child(new TRItemSlot()
                        .pos(30, 24)
                        .slot(new ModularSlot(ioComponent.getFluidItemInputs(), 0)
                                .changeListener(this::onInputChange)
                                .accessibility(true, true)))
                .child(new TRItemSlot()
                        .pos(30, 46)
                        .slot(new ModularSlot(ioComponent.getFluidItemOutputs(), 0)
                                .accessibility(false, true)))
        ;

        TRGuis.createMultiblockCover(panel, data, multiblockComponent::isValid, multiblockComponent.getCurrentStructure());
        panel.child(TRGuis.createUpdateTab(upgradeComponent.getUpgrades(), "upgrades", upgradeComponent::onUpgradeChange).leftRelOffset(1f, 1));
        return panel;
    }
}
