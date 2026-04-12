package com.sabrepenguin.techreborn.tileentity.cable;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityCable extends TileEntity {
	private NetworkCable network;

	@Override
	public void onLoad() {
		if (world.isRemote)
			return;
		for(EnumFacing facing: EnumFacing.values()) {
			BlockPos offset = pos.offset(facing);
			if (world.isBlockLoaded(offset)) {
				TileEntity te = world.getTileEntity(offset);
				if (te instanceof TileEntityCable cable && !te.isInvalid()) {
					if (cable.isPartOfNetwork()) {
						if (this.network == null) {
							this.network = cable.network;
							this.network.addToNetwork(this.pos);
						} else if (this.network != cable.network) {
							NetworkCable small;
							NetworkCable large;
							if (this.network.networkSize() <= cable.network.networkSize()) {
								small = this.network;
								large = cable.network;
							} else {
								small = cable.network;
								large = this.network;
							}
							large.mergeNetwork(world, small);
							this.network = large;
							this.network.addToNetwork(pos);
						}
					}
				}
			}
		}
		if (this.network == null) {
			this.network = new NetworkCable(world, this.pos);
		}
		scanEndpoints();
		super.onLoad();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (!world.isRemote && network != null) {
			network.removeFromNetwork(pos);
		}
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (!world.isRemote && network != null) {
			network.removeFromNetwork(pos);
		}
	}

	public boolean isPowered() {
		return isPartOfNetwork() && network.isPowered();
	}

	public boolean isPartOfNetwork() {
		return network != null;
	}

	public void setNetworkSilent(NetworkCable network) {
		this.network = network;
		this.network.addToNetwork(pos);
	}

	public void scanEndpoints() {
		if (world.isRemote || network == null) return;
		for (EnumFacing facing: EnumFacing.values()) {
			BlockPos offset = pos.offset(facing);
			if (world.isBlockLoaded(offset)) {
				TileEntity te = world.getTileEntity(offset);
				if (te != null && !(te instanceof TileEntityCable) && !te.isInvalid() && te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
					network.addPointToNetwork(offset, facing);
				} else {
					network.removePointFromNetwork(offset, facing);
				}
			}
		}
	}

	public boolean isNeighborInNetwork(BlockPos pos) {
		if (world.isBlockLoaded(pos)) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityCable cable) {
				return cable.network == this.network;
			}
		}
		return false;
	}

	public NetworkCable getNetwork() {
		return this.network;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return super.hasCapability(capability, facing);
	}

	@Override
	public @Nullable <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return super.getCapability(capability, facing);
	}


}
