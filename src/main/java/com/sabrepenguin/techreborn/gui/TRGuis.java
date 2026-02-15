package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.drawable.ItemDrawable;
import com.cleanroommc.modularui.widget.ParentWidget;
import com.cleanroommc.modularui.widgets.Expandable;
import com.cleanroommc.modularui.widgets.SlotGroupWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraftforge.items.IItemHandler;

public class TRGuis {

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
								.margin(5, 5, 25, 5).name("crafting").alignX(0.5f)));
	}
}
