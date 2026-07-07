package com.sabrepenguin.techreborn.tileentity.tier0;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.gui.FurnaceFuelWidget;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.tileentity.MachineIOManager;
import com.sabrepenguin.techreborn.tileentity.components.ItemIOComponent;
import com.sabrepenguin.techreborn.tileentity.components.SidedConfigComponent;
import com.sabrepenguin.techreborn.tileentity.components.specialized.FuelRecipeComponent;
import com.sabrepenguin.techreborn.util.ObjIntPredicate;
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

public class TileEntityIronAlloyFurnace extends TileEntity implements ITickable, IGuiHolder<PosGuiData>, ISideConfigTE {

	protected final ItemIOComponent ioComponent;
	protected final FuelRecipeComponent recipeComponent;
	protected final SidedConfigComponent configComponent;

	public TileEntityIronAlloyFurnace() {
		ioComponent = new ItemIOComponent(this, 3, 1, false, this::refreshSlot)
				.addInputFilter(ObjIntPredicate.fuel(2));
		recipeComponent = new FuelRecipeComponent(RegistryHandler.instance().getAlloyRegistry(), ioComponent.getInputs(), ioComponent.getInternalOutputs(), 2, this::updateState);
		configComponent = new SidedConfigComponent(ioComponent, this::updateTE);
	}

	protected void refreshSlot(int slot) {
		recipeComponent.informRefreshRecipe();
	}

	private void updateState(boolean state) {
		IBlockState currentState = world.getBlockState(pos);
		world.setBlockState(pos, currentState.withProperty(BlockHorizontalMachine.ACTIVE, state), 3);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| super.hasCapability(capability, facing);
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
	public void readFromNBT(NBTTagCompound compound) {
		ioComponent.readFromNBT(compound);
		recipeComponent.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ioComponent.writeToNBT(compound);
		recipeComponent.writeToNBT(compound);
		return super.writeToNBT(compound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public @Nullable SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 3, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.getNbtCompound());
		recipeComponent.onUpdatePacket(world, pos, pkt.getNbtCompound());
	}


	@Override
	public void update() {
		if (world.isRemote) return;

		boolean updated = ioComponent.update() | recipeComponent.update();

		if (updated) {
			this.markDirty();
		}
	}

	@Override
	public MachineIOManager getManager() {
		return null;
	}

	@Override
	public void updateTE() {
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}

	@Override
	public SidedConfigComponent getSidedConfigComponent() {
		return configComponent;
	}

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.iron_alloy_furnace.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup("inputs", 2)
				.registerSlotGroup(new SlotGroup("fuel", 1, 120, true));
		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7));
		panel.child(new ProgressWidget()
						.size(20)
						.leftRelOffset(0.5f, 3)
						.topRelOffset(0.25f, -2)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(recipeComponent.getProgress()))
				.child(TRGuis.createJeiButton("tile.techreborn.alloy_smelter.name")
						.leftRelOffset(0.5f, 3)
						.topRelOffset(0.25f, -1)
						.size(20, 15))
				.child(new FurnaceFuelWidget()
						.pos(58, 37)
						.value(recipeComponent.getFuel()));
		panel.child(new ItemSlot().pos(47, 17)
						.slot(new ModularSlot(ioComponent.getInputs(), 0)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)))
				.child(new ItemSlot().pos(65, 17)
						.slot(new ModularSlot(ioComponent.getInputs(), 1)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)))
				.child(new ItemSlot().pos(56, 53)
						.slot(new ModularSlot(ioComponent.getInputs(), 2)
								.slotGroup("fuel")
								.filter(item -> TileEntityFurnace.getItemBurnTime(item) != 0)))
				.child(new ItemSlot().pos(116, 35)
						.slot(new ModularSlot(ioComponent.getOutputs(), 0)
								.accessibility(false, true)));

		return panel;
	}

	public void onInputChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (client || init) return;
		if (onlyAmountChanged) {
			refreshSlot(0);
			return;
		}
		refreshSlot(0);
		markDirty();
	}
}
