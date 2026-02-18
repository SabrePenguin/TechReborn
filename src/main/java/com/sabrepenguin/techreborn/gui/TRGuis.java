package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.drawable.ItemDrawable;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.ParentWidget;
import com.cleanroommc.modularui.widget.sizer.Area;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.Dialog;
import com.cleanroommc.modularui.widgets.Expandable;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.sabrepenguin.techreborn.capability.stackhandler.SideConfigItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.networking.PacketSideConfig;
import com.sabrepenguin.techreborn.networking.TechRebornPacketHandler;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Supplier;

public class TRGuis {

	public static final int PLAYER_INVENTORY_HEIGHT = 82;

	public static Expandable createUpdateTab(IItemHandler handler, String groupName) {
		return new Expandable()
				.name("upgrade tab")
				.background(GuiTextures.MC_BACKGROUND)
				.excludeAreaInRecipeViewer()
				.stencilTransform((r, expanded) -> {
					r.width = Math.max(20, r.width - 5);
					r.height = Math.max(20, r.height - 5);
				})
				.animationDuration(500)
				.collapsedView(new ItemDrawable(TRItems.gem, 1).asIcon().asWidget().size(20).pos(4, 4).margin(4))
				.expandedView(new ParentWidget<>()
						.name("expanded upgrade tab")
						.coverChildren()
						.child(new ItemDrawable(TRItems.gem, 1).asIcon().asWidget().size(20).pos(4, 4).margin(4))
						.child(IKey.str("Upgrades").asWidget().scale(1f).pos(27, 10).marginRight(8))
						.child(SlotGroupWidget.builder()
								.matrix("II", "II")
								.key('I', i -> new ItemSlot().slot(new ModularSlot(handler, i).slotGroup(groupName))
										.addTooltipLine("This slot is empty"))
								.build()
								.margin(5, 5, 25, 15).name("crafting").alignX(0.5f)));
	}

	public static void addConfigPanel(ModularPanel mainPanel, IPanelHandler panelHandler) {
		mainPanel.child(new ButtonWidget<>()
				.size(20)
				.right(5)
				.bottom(PLAYER_INVENTORY_HEIGHT + 3)
				.onMousePressed(mouseButton -> {
					panelHandler.openPanel();
					return panelHandler.isPanelOpen();
				}));
	}

	public static ModularPanel createConfigPanel(
			PanelSyncManager syncManager, IPanelHandler syncHandler, BlockPos pos, Area area,
			SideConfigItemStackHandler[] handlers, Supplier<EnumFacing> getFacing,
			SlotPosition... slotPositions) {
		ModularPanel panel = new ModularPanel("config_panel") {
			@Override
			public boolean isDraggable() {
				return false;
			}
		}.background(IDrawable.EMPTY)
				.height(area.height - PLAYER_INVENTORY_HEIGHT)
				.alignX(0.5f)
				.topRel(0.5f, -(area.height/2) - (area.height%2), 0f);
		// Close button
		ButtonWidget<?> buttonWidget = new ButtonWidget<>();
		panel.child(buttonWidget
				.size(20)
				.right(5)
				.bottom(3)
				.onMousePressed(mouseButton -> {
					if (mouseButton == 0 || mouseButton == 1) {
						buttonWidget.getPanel().closeIfOpen();
						return true;
					}
					return false;
				}));
		// Button creation. Creates a button on top of each individual slot
		for (int i = 0; i < slotPositions.length; i++) {
			SlotPosition position = slotPositions[i];
			IPanelHandler panelHandler = syncManager.syncedPanel("slot config " + i, true,
					(syncManager1, syncHandler1) ->
							createSlotConfigPanel(syncManager1, syncHandler1, pos, handlers, position.handlerSlot(), position.slot(), getFacing));
			panel.child(new TRButtonWidget<>()
					.pos(position.x(), position.y())
					.onMousePressed(mouseButton -> {
						panelHandler.openPanel();
						return panelHandler.isPanelOpen();
					}));
		}
		return panel;
	}

	private static ModularPanel createSlotConfigPanel(
			PanelSyncManager syncManager, IPanelHandler syncHandler, BlockPos pos,
			SideConfigItemStackHandler[] handlers, int handlerIndex, int slot,
			Supplier<EnumFacing> getFacing) {
		ModularPanel panel = new Dialog<>("dialog_" + handlerIndex + "_" + slot).size(52);
		panel.child(ButtonWidget.panelCloseButton());
		for (int i = 0; i < 6; i++) {
			addEnumFacingButtons(pos, panel, i, handlers, handlerIndex, slot, getFacing);
		}
		return panel;
	}

	private static void addEnumFacingButtons(
			BlockPos pos, ModularPanel panel, int relativeIndex,
			SideConfigItemStackHandler[] handlers, int handlerIndex, int slot,
			Supplier<EnumFacing> getFacing) {
		ButtonWidget<?> button = new ButtonWidget<>().size(16);
		switch (relativeIndex) {
			case 0: button.alignX(0.5f).bottom(2); break;
			case 1: button.alignX(0.5f).top(2); break;
			case 2: button.align(Alignment.CENTER); break;
			case 3: button.right(2).bottom(2); break;
			case 4: button.alignY(0.5f).left(2); break;
			case 5: button.alignY(0.5f).right(2); break;
		}
		button.onMousePressed(mouseButton -> {
			EnumFacing front = getFacing.get();
			EnumFacing absolute = TRGuis.getAbsoluteFacing(relativeIndex, front);
			int sideIndex = absolute.getIndex();
			SideConfigItemStackHandler h = handlers[sideIndex];
			boolean enabled = h.getSlotEnabled(handlerIndex, slot);
			TechRebornPacketHandler.INSTANCE.sendToServer(new PacketSideConfig(
					pos, sideIndex, handlerIndex, slot, !enabled
			));
			return true;
		});
		panel.child(button);
	}

	private static EnumFacing getAbsoluteFacing(int side, EnumFacing facing) {
		return switch (side) {
			case 0 -> EnumFacing.DOWN;
			case 1 -> EnumFacing.UP;
			case 2 -> facing;
			case 3 -> facing.getOpposite();
			case 4 -> facing.rotateYCCW();
			case 5 -> facing.rotateY();
			default -> EnumFacing.NORTH;
		};
	}
}
