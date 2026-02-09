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
import com.cleanroommc.modularui.widgets.slot.IOnSlotChanged;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.capability.stackhandler.LimitedItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.RestrictedItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotType;
import com.sabrepenguin.techreborn.tileentity.ISetWorldNameable;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityElectricFurnace extends TileEntity implements ISetWorldNameable, ITickable, IGuiHolder<PosGuiData>, IOnSlotChanged {
	private final ItemStackHandler inventory;
	private final RestrictedItemStackHandler input;
	private final RestrictedItemStackHandler battery;
	private final LimitedItemStackHandler output;

	private String customName;

	public TileEntityElectricFurnace() {
		inventory = new ItemStackHandler(3);
		input = new RestrictedItemStackHandler(inventory, 0);
		output = new LimitedItemStackHandler(new RestrictedItemStackHandler(inventory, 1), SlotType.OUTPUT);
		battery = new RestrictedItemStackHandler(inventory, 2);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}



	@Override
	public void update() {
		if (this.world.isRemote)
			return;
	}

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		ModularPanel panel = ModularPanel.defaultPanel("electric_furnace");
		syncManager.registerSlotGroup(new SlotGroup("inputs", 1));
		if (hasCustomName()) {
			panel.child(IKey.str(getName())
					.asWidget()
					.align(Alignment.TopCenter)
					.top(7));
		} else {
			panel.child(IKey.lang("tile.techreborn.electric_furnace.name")
					.asWidget()
					.align(Alignment.TopCenter)
					.top(7));
		}
		panel.bindPlayerInventory();
		panel.child(new ProgressWidget()
				.size(20)
				.leftRelOffset(0.5f, 3)
				.topRelOffset(0.25f, -2)
				.texture(GuiTextures.PROGRESS_ARROW, 20)
				.value(new DoubleSyncValue(() -> 0.0, value -> {}))
		);
		panel.child(new ItemSlot().pos(56, 35)
						.slot(new ModularSlot(input, 0)
								.slotGroup("inputs")
								.changeListener(this)))
				.child(new ItemSlot().pos(116, 35)
						.slot(new ModularSlot(output, 0)
								.accessibility(false, true)));
		return panel;
	}

	@Override
	public void onChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {

	}

	@Override
	public ITextComponent getDisplayName() {
		if (hasCustomName()) {
			return new TextComponentString(customName);
		}
		return new TextComponentTranslation("tile.techreborn.electric_furnace.name");
	}

	@Override
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public String getName() {
		return customName;
	}

	@Override
	public boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}
}
