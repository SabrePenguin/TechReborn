package com.sabrepenguin.techreborn.tileentity.cable;

import com.sabrepenguin.techreborn.blocks.BlockCable;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
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
		IBlockState thisState = world.getBlockState(pos);
		BlockCable.CableEnum type = thisState.getValue(BlockCable.TYPE);
		for(EnumFacing facing: EnumFacing.values()) {
			BlockPos offset = pos.offset(facing);
			if (world.isBlockLoaded(offset)) {
				TileEntity te = world.getTileEntity(offset);
				if (te instanceof TileEntityCable cable && !te.isInvalid()) {
					IBlockState neighborState = world.getBlockState(offset);
					if (type == neighborState.getValue(BlockCable.TYPE)) {
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
		}
		if (this.network == null) {
			this.network = new NetworkCable(world, type.getTransferRate() , this.pos);
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
