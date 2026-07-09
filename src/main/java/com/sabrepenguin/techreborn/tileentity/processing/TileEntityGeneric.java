package com.sabrepenguin.techreborn.tileentity.processing;

import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.tileentity.IOManager;
import com.sabrepenguin.techreborn.tileentity.components.EnergyComponent;
import com.sabrepenguin.techreborn.tileentity.components.ItemIOComponent;
import com.sabrepenguin.techreborn.tileentity.components.SidedConfigComponent;
import com.sabrepenguin.techreborn.tileentity.components.StandardRecipeComponent;
import com.sabrepenguin.techreborn.tileentity.components.UpgradeComponent;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityGeneric extends TileEntity implements ITickable, ISideConfigTE {

	protected final ItemIOComponent ioComponent;
	protected final EnergyComponent energyComponent;
	protected final UpgradeComponent upgradeComponent;
	protected final StandardRecipeComponent recipeComponent;
	protected final SidedConfigComponent configComponent;

	public TileEntityGeneric(int inputSize, int outputSize, int feCapacity, int maxInput, ITRRegistry registry) {
		ioComponent = new ItemIOComponent(this, inputSize, outputSize, true, this::refreshSlot);
		energyComponent = new EnergyComponent(feCapacity, maxInput, maxInput, ioComponent.getBatterySlot()).setUnextractable();
		recipeComponent = new StandardRecipeComponent(registry, ioComponent.getInputs(), ioComponent.getInternalOutputs(), energyComponent.getEnergyStorage(), this::updateState);
		upgradeComponent = new UpgradeComponent(feCapacity, energyComponent, recipeComponent);
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
				|| capability == CapabilityEnergy.ENERGY
				|| super.hasCapability(capability, facing);
	}

	@Override
	public @Nullable <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == null) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getInventory());
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getManager().getSide(facing));
		} else if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energyComponent.getEnergyStorage());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		ioComponent.readFromNBT(compound);
		energyComponent.readFromNBT(compound);
		upgradeComponent.readFromNBT(compound);
		recipeComponent.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ioComponent.writeToNBT(compound);
		energyComponent.writeToNBT(compound);
		upgradeComponent.writeToNBT(compound);
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

		boolean updated = ioComponent.update() | energyComponent.update() | upgradeComponent.update() | recipeComponent.update();

		if (updated) {
			this.markDirty();
		}
	}

	@Override
	public SidedConfigComponent getSidedConfigComponent() {
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
