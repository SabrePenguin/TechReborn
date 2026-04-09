package com.sabrepenguin.techreborn.tileentity.cable;

import com.sabrepenguin.techreborn.config.TechRebornConfig;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.*;

public class NetworkCable {
	private final Set<BlockPos> tileEntities = new HashSet<>();
	private boolean powered = false;
	private long centerX = 0;
	private long centerY = 0;
	private long centerZ = 0;
	private final World world;

	public NetworkCable(World world) {
		this.world = world;
		NetworkCableManager.registerNetwork(world, this);
	}

	public NetworkCable(World world, BlockPos pos) {
		this(world);
		addToNetwork(pos);
	}

	public void addToNetwork(TileEntityCable cable) {
		addToNetwork(cable.getPos());
	}

	public void addToNetwork(BlockPos pos) {
		if (tileEntities.add(pos)) {
			centerX += pos.getX();
			centerY += pos.getY();
			centerZ += pos.getZ();
		}
	}

	public void removeFromNetwork(TileEntityCable cable) {
		removeFromNetwork(cable.getPos());
	}

	public void removeFromNetwork(BlockPos pos) {
		if (tileEntities.remove(pos)) {
			centerX -= pos.getX();
			centerY -= pos.getY();
			centerZ -= pos.getZ();
			if (tileEntities.isEmpty()) {
				NetworkCableManager.removeNetwork(world, this);
			} else {
				byte count = 0;
				for (EnumFacing facing: EnumFacing.values()) {
					BlockPos offset = pos.offset(facing);
					if (world.isBlockLoaded(offset)) {
						if (world.getTileEntity(pos.offset(facing)) instanceof TileEntityCable) {
							count++;
						}
					}
					if (count > 1) {
						recalculateNetwork();
						break;
					}
				}
			}
		}
	}

	private Vec3d center() {
		int size = tileEntities.size();
		if (size == 0) return Vec3d.ZERO;
		return new Vec3d((double) centerX / size, (double) centerY / size, (double) centerZ / size);
	}

	public void addPointToNetwork(TileEntity entity, EnumFacing facing) {

	}

	public void mergeNetwork(World world, NetworkCable network) {
		for (BlockPos pos: network.tileEntities) {
			addToNetwork(pos);
			TileEntity entity = world.getTileEntity(pos);
			if (entity instanceof TileEntityCable cable) {
				cable.setNetworkSilent(this);
			}
		}
		network.tileEntities.clear();
		NetworkCableManager.removeNetwork(world, network);
	}

	public void tick() {
		if (TechRebornConfig.misc.cable.debugNetwork && world.getTotalWorldTime() % 10 == 0) {
			if (world instanceof WorldServer server) {
				Vec3d center = center();
				server.spawnParticle(
						EnumParticleTypes.END_ROD,
						center.x + 0.5,
						center.y + 0.5,
						center.z + 0.5,
						1,
						0,
						0,
						0.0D,
						0
				);
			}
		}
	}

	private void recalculateNetwork() {
		if (tileEntities.isEmpty() || world.isRemote)
			return;
		Set<BlockPos> unvisited = new HashSet<>(tileEntities);
		List<Set<BlockPos>> subgraphs = new ArrayList<>();

		while (!unvisited.isEmpty()) {
			BlockPos pos = unvisited.iterator().next();
			Set<BlockPos> currentGraph = new HashSet<>();
			Queue<BlockPos> queue = new ArrayDeque<>();

			queue.add(pos);
			currentGraph.add(pos);
			unvisited.remove(pos);
			while (!queue.isEmpty()) {
				BlockPos currentPos = queue.remove();
				for (EnumFacing facing: EnumFacing.values()) {
					BlockPos offset = currentPos.offset(facing);
					if (unvisited.contains(offset)) {
						currentGraph.add(offset);
						queue.add(offset);
						unvisited.remove(offset);
					}
				}
			}
			subgraphs.add(currentGraph);
		}
		if (subgraphs.size() > 1) {
			Set<BlockPos> primary = subgraphs.get(0);
			this.tileEntities.clear();
			this.centerX = 0;
			this.centerY = 0;
			this.centerZ = 0;
			for (BlockPos pos: primary) {
				addToNetwork(pos);
			}
			for (int i = 1; i < subgraphs.size(); i++) {
				Set<BlockPos> severed = subgraphs.get(i);
				NetworkCable networkCable = new NetworkCable(world);
				for (BlockPos pos: severed) {
					networkCable.addToNetwork(pos);
					TileEntity te = world.getTileEntity(pos);
					if (te instanceof TileEntityCable cable) {
						cable.setNetworkSilent(networkCable);
					}
				}
			}
		}
	}

	public int networkSize() {
		return tileEntities.size();
	}

	public boolean isPowered() {
		return powered;
	}
}
