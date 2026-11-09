package com.sabrepenguin.techreborn.tileentity.tier0;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.IOnSlotChanged;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.stackhandler.*;
import com.sabrepenguin.techreborn.gui.FurnaceFuelWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.tileentity.IOManager;
import com.sabrepenguin.techreborn.tileentity.components.ItemIOComponent;
import com.sabrepenguin.techreborn.tileentity.components.SidedConfigComponent;
import com.sabrepenguin.techreborn.tileentity.components.specialized.FurnaceRecipeComponent;
import com.sabrepenguin.techreborn.util.ObjIntPredicate;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityIronFurnace extends TileEntity implements ITickable, IOnSlotChanged, IGuiHolder<PosGuiData>, ISideConfigTE {

	protected final ItemIOComponent ioComponent;
	protected final FurnaceRecipeComponent recipeComponent;
	protected final SidedConfigComponent<IOManager, SideConfigStackHandler> configComponent;

	public TileEntityIronFurnace() {
		ioComponent = new ItemIOComponent(this, 2, 1, false, this::refreshSlot)
				.addInputFilter(ObjIntPredicate.fuel(1));
		recipeComponent = new FurnaceRecipeComponent(RegistryHandler.instance().getFurnaceRegistry(), ioComponent.getInputs(), ioComponent.getInternalOutputs(), 1, this::updateState, 160);
		configComponent = new SidedConfigComponent<>(ioComponent.getManager(), this::updateTE);
	}

	protected void refreshSlot(int slot) {
		recipeComponent.informRefreshRecipe();
	}

	private void updateState(boolean state) {
		IBlockState currentState = world.getBlockState(pos);
		world.setBlockState(pos, currentState.withProperty(BlockHorizontalMachine.ACTIVE, state), 3);
	}

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		recipeComponent.writeToNBT(compound);
		ioComponent.writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
		ioComponent.readFromNBT(compound);
		recipeComponent.readFromNBT(compound);
        super.readFromNBT(compound);
    }

	@Override
	public SidedConfigComponent<IOManager, SideConfigStackHandler> getSidedConfigComponent() {
		return configComponent;
	}

	@Override
	public void updateTE() {
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}

	@Override
	public IOManager getManager() {
		return ioComponent.getManager();
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public @Nullable SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 3, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public @Nullable <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == null) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getInventory());
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getManager().getSide(facing));
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
		if (this.world.isRemote)
			return;

		boolean updated = ioComponent.update() | recipeComponent.update();

		if (updated) {
			markDirty();
		}
    }


	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		ModularPanel panel = ModularPanel.defaultPanel("iron_furnace");
		syncManager.registerSlotGroup(new SlotGroup("input", 1))
				.registerSlotGroup(new SlotGroup("fuel", 1, 80, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		IPanelHandler panelHandler = syncManager.syncedPanel("config", true,
				(syncManager1, syncHandler) ->
						TRGuis.createConfigPanel(syncManager1, syncHandler, this.getPos(), panel.getArea(),
								ioComponent.getManager(),
								getFacing,
								Arrays.asList(
								new SlotPosition(SlotAction.INPUT, 56, 17, 0, 0),
								new SlotPosition(SlotAction.INPUT, 56, 53, 0, 1),
								new SlotPosition(SlotAction.OUTPUT, 116, 35, 1, 2)), null, null));
		TRGuis.addConfigPanel(panel, panelHandler);

		panel.child(IKey.lang("tile.techreborn.iron_furnace.name")
				.asWidget()
				.align(Alignment.TopCenter)
				.top(7));

		panel.bindPlayerInventory();
		panel.child(new ProgressWidget()
				.size(24)
				.pos(80, 34)
				.texture(GuiTextures.PROGRESS_ARROW, 20)
				.value(recipeComponent.getProgress()))
				.child(new FurnaceFuelWidget()
						.pos(58, 37)
						.value(recipeComponent.getFuel()));
		panel.child(new ItemSlot().pos(56, 17)
						.slot(new ModularSlot(ioComponent.getInputs(), 0)
								.slotGroup("input")
								.changeListener(this)))
				.child(new ItemSlot().pos(56, 53)
						.slot(new ModularSlot(ioComponent.getInputs(), 1)
						.slotGroup("fuel")
						.filter(item -> TileEntityFurnace.getItemBurnTime(item) != 0)))
				.child(new ItemSlot().pos(116, 35)
						.slot(new ModularSlot(ioComponent.getOutputs(), 0)
								.accessibility(false, true)));
		return panel;
	}

	@Override
	public void onChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (world.isRemote || init) return;
		if (onlyAmountChanged) {
			refreshSlot(0);
			return;
		}
		refreshSlot(0);
		markDirty();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
}
