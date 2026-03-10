package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiDraw;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.integration.jei.ModularUIJeiPlugin;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.viewport.GuiContext;
import com.cleanroommc.modularui.theme.WidgetTheme;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widget.ParentWidget;
import com.cleanroommc.modularui.widget.sizer.Area;
import com.cleanroommc.modularui.widgets.*;
import com.cleanroommc.modularui.widgets.slot.IOnSlotChanged;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.networking.sideconfig.PacketAutoConfig;
import com.sabrepenguin.techreborn.networking.sideconfig.PacketSideConfig;
import com.sabrepenguin.techreborn.networking.TechRebornPacketHandler;
import com.sabrepenguin.techreborn.tileentity.MachineIOManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class TRGuis {

	public static final int PLAYER_INVENTORY_HEIGHT = 82;

	public static final UITexture OUTPUT = UITexture.builder().location(Tags.MODID, "gui/output_slot").nonOpaque().fullImage().build();
	public static final UITexture INPUT = UITexture.builder().location(Tags.MODID, "gui/input_slot").nonOpaque().fullImage().build();
	public static final UITexture CHECK = UITexture.builder().location(Tags.MODID, "gui/check").nonOpaque().fullImage().build();
	public static final UITexture CONFIG = UITexture.builder().location(Tags.MODID, "gui/config_button").nonOpaque().fullImage().build();
	public static final UITexture UPGRADE = UITexture.builder().location(Tags.MODID, "gui/upgrade_base").fullImage().build();

	public static ButtonWidget<?> createJeiButton(String recipeUID) {
		return new ButtonWidget<>()
				.tooltip(tooltip -> tooltip.addLine(IKey.str("Open JEI")))
				.invisible()
				.onMousePressed(mouseButton -> {
					if (ModularUIJeiPlugin.getRuntime() != null) {
						ModularUIJeiPlugin.getRuntime().getRecipesGui().showCategories(Collections.singletonList(recipeUID));
						return true;
					}
					return false;
				});
	}

	public static Expandable createUpdateTab(IItemHandler handler, String groupName, IOnSlotChanged listener) {
		return new Expandable()
				.name("upgrade tab")
				.background(GuiTextures.MC_BACKGROUND)
				.excludeAreaInRecipeViewer()
				.stencilTransform((r, expanded) -> {
					r.width = Math.max(20, r.width - 5);
					r.height = Math.max(20, r.height - 5);
				})
				.animationDuration(500)
				.collapsedView(UPGRADE.asWidget().size(16).pos(4, 4).margin(4))
				.expandedView(new ParentWidget<>()
						.name("expanded upgrade tab")
						.coverChildren()
						.child(UPGRADE.asWidget().size(16).pos(4, 4).margin(4))
						.child(IKey.str("Upgrades").asWidget().scale(1f).pos(25, 8).marginRight(8))
						.child(SlotGroupWidget.builder()
								.matrix("II", "II")
								.key('I', i -> new ItemSlot().slot(new ModularSlot(handler, i)
										.slotGroup(groupName)
										.filter(stack -> stack.getItem() == TRItems.upgrades)
										.changeListener(listener)))
								.build()
								.margin(5, 5, 25, 15).name("crafting").alignX(0.5f)));
	}

	public static void addConfigPanel(ModularPanel mainPanel, IPanelHandler panelHandler) {
		mainPanel.child(new ButtonWidget<>()
				.size(20)
				.right(5)
				.bottom(PLAYER_INVENTORY_HEIGHT + 6)
				.overlay(CONFIG)
				.onMousePressed(mouseButton -> {
					panelHandler.openPanel();
					return panelHandler.isPanelOpen();
				}));
	}

	public static ModularPanel createConfigPanel(
			PanelSyncManager syncManager, IPanelHandler syncHandler, BlockPos pos, Area area,
			MachineIOManager manager,
			Supplier<EnumFacing> getFacing,
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
				.bottom(6)
				.overlay(CONFIG)
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
			IPanelHandler panelHandler = syncManager.syncedPanel("slot_config_" + i, true,
					(syncManager1, syncHandler1) ->
							createSlotConfigPanel(syncManager1, syncHandler1, pos, manager, position.handlerSlot(), position.slot(), getFacing, position.action()));
			panel.child(new TRButtonWidget<>()
					.pos(position.x() - ((position.size() - 18) / 2), position.y() - ((position.size() - 18) / 2))
					.size(position.size())
					.onMousePressed(mouseButton -> {
						panelHandler.openPanel();
						return panelHandler.isPanelOpen();
					}));
		}
		return panel;
	}

	private static ModularPanel createSlotConfigPanel(
			PanelSyncManager syncManager, IPanelHandler syncHandler, BlockPos pos,
			MachineIOManager manager,
			int handlerIndex, int slot,
			Supplier<EnumFacing> getFacing, SlotAction action) {
		SlotAction slotAction = manager.getHandlerActionType(handlerIndex);
		int increaseBy = slotAction == SlotAction.BIDIRECTIONAL ? 30 : 15;
		ModularPanel panel = new Dialog<>("dialog_" + handlerIndex + "_" + slot).size(104, 104 + increaseBy);
		panel.child(ButtonWidget.panelCloseButton());
		for (int i = 0; i < 6; i++) {
			addEnumFacingButtons(pos, panel, i, manager, handlerIndex, slot, getFacing, increaseBy);
		}
		int index = manager.getRealIndex(handlerIndex, slot);
		switch (slotAction) {
			case INPUT:
				panel.child(createButton(pos, manager, index, true, 6));
				panel.child(IKey.lang("techreborn.auto_input.gui").asWidget().left(18).bottom(7));
				break;
			case OUTPUT:
				panel.child(createButton(pos, manager, index, false, 6));
				panel.child(IKey.lang("techreborn.auto_output.gui").asWidget().left(18).bottom(7));
				break;
			case BIDIRECTIONAL:
				panel.child(createButton(pos, manager, index, true, 20))
						.child(IKey.lang("techreborn.auto_input.gui").asWidget().left(18).bottom(21));
				panel.child(createButton(pos, manager, index, false, 6))
						.child(IKey.lang("techreborn.auto_output.gui").asWidget().left(18).bottom(7));
				break;
			default: break;
		}
		return panel;
	}

	private static ButtonWidget<?> createButton(BlockPos pos, MachineIOManager manager, int index, boolean input, int bottom) {
		return new ButtonWidget<>()
				.left(5)
				.bottom(bottom)
				.size(11)
				.onMousePressed(mouseButton -> {
					TechRebornPacketHandler.INSTANCE.sendToServer(new PacketAutoConfig(pos, index, input));
					return true;
				}).overlay((context, x, y, width, height, theme) -> {
			if (manager.getCurrentValue(index, input)) {
				CHECK.draw(x, y, width, height);
			}
		});
	}

	private static void addEnumFacingButtons(
			BlockPos pos, ModularPanel panel, int relativeIndex,
			MachineIOManager manager,
			int handlerIndex, int slot,
			Supplier<EnumFacing> getFacing,
			int bottomOffset) {
		ButtonWidget<?> button = new ButtonWidget<>().size(32);
		switch (relativeIndex) {
			case 0: button.alignX(0.5f).bottom(bottomOffset + 4); break;
			case 1: button.alignX(0.5f).top(4); break;
			case 2: button.alignX(0.5f).topRel(0.5f, -bottomOffset/2, 0.5f); break;
			case 3: button.right(4).bottom(4 + bottomOffset); break;
			case 4: button.topRel(0.5f, -bottomOffset/2, 0.5f).left(4); break;
			case 5: button.topRel(0.5f, -bottomOffset/2, 0.5f).right(4); break;
		}
		Supplier<EnumFacing> absoluteFacing = () -> TRGuis.getAbsoluteFacing(relativeIndex, getFacing.get());
		DrawableBlockFace face = new DrawableBlockFace(pos, absoluteFacing);
		button.background(face);
		button.hoverBackground(face);
		button.overlay((context, x, y, width, height, theme) -> {
			SlotAction currentAction = manager.getSide(absoluteFacing.get()).getSlotAction(handlerIndex, slot);
			switch (currentAction) {
				case OUTPUT -> OUTPUT.draw(x, y, width, height);
				case INPUT -> INPUT.draw(x, y, width, height);
			}
		});
		button.onMousePressed(mouseButton -> {
			int sideIndex = absoluteFacing.get().getIndex();
			TechRebornPacketHandler.INSTANCE.sendToServer(new PacketSideConfig(
					pos, sideIndex, handlerIndex, slot
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
			case 4 -> facing.rotateY();
			case 5 -> facing.rotateYCCW();
			default -> EnumFacing.NORTH;
		};
	}

	private static class DrawableBlockFace implements IDrawable {

		private final BlockPos pos;
		private final Supplier<EnumFacing> getFacing;

		public DrawableBlockFace(BlockPos pos, Supplier<EnumFacing> getFacing) {
			this.pos = pos;
			this.getFacing = getFacing;
		}

		@Override
		public void draw(GuiContext context, int x, int y, int width, int height, WidgetTheme widgetTheme) {
			Minecraft mc = Minecraft.getMinecraft();
			if (mc.world == null) return;
			IBlockState state = mc.world.getBlockState(pos);
			EnumFacing facing = getFacing.get();
			IBakedModel model = mc.getBlockRendererDispatcher().getModelForState(state);
			List<BakedQuad> quads = model.getQuads(state, facing, 0);
			TextureAtlasSprite sprite = null;
			if (!quads.isEmpty()) {
				sprite = quads.get(0).getSprite();
			} else {
				List<BakedQuad> unculled = model.getQuads(state, null, 0);
				for (BakedQuad quad: unculled) {
					if (quad.getFace() == facing) {
						sprite = quad.getSprite();
						break;
					}
				}
				if (sprite == null)
					sprite = model.getParticleTexture();
			}
			if (sprite != null) {
				GuiDraw.drawSprite(sprite, 0, 0, width, height);
			}
		}
	}
}
