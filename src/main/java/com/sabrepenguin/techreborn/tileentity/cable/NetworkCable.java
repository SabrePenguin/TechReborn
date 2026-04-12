package com.sabrepenguin.techreborn.tileentity.cable;

import com.github.bsideup.jabel.Desugar;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.energy.CapabilityEnergy;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class NetworkCable {
	private final Set<BlockPos> tileEntities = new HashSet<>();
	private final Map<BlockPos, Set<EnumFacing>> endpoints = new HashMap<>();
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

	public void addPointToNetwork(BlockPos pos, EnumFacing facing) {
		endpoints.computeIfAbsent(pos, k -> EnumSet.noneOf(EnumFacing.class)).add(facing);
	}

	public void removePointFromNetwork(BlockPos pos, EnumFacing facing) {
		if (endpoints.containsKey(pos)) {
			endpoints.get(pos).remove(facing);
			if (endpoints.get(pos).isEmpty()) {
				endpoints.remove(pos);
			}
		}
	}

	public Map<BlockPos, Set<EnumFacing>> getEndpoints() {
		return endpoints;
	}

	public Set<BlockPos> getCables() {
		return tileEntities;
	}

	public void mergeNetwork(World world, NetworkCable network) {
		for (Map.Entry<BlockPos, Set<EnumFacing>> entry: network.endpoints.entrySet()) {
			for (EnumFacing facing: entry.getValue()) {
				this.addPointToNetwork(entry.getKey(), facing);
			}
		}
		network.endpoints.clear();

		for (BlockPos pos: network.tileEntities) {
			addToNetwork(pos);
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityCable cable && !te.isInvalid()) {
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
		List<Set<BlockPos>> subs = new ArrayList<>();
		List<Pair<Set<BlockPos>, Map<BlockPos, Set<EnumFacing>>>> subgraphs = new ArrayList<>();
		while (!unvisited.isEmpty()) {
			BlockPos pos = unvisited.iterator().next();
			Pair<Set<BlockPos>, Map<BlockPos, Set<EnumFacing>>> current;
			Queue<BlockPos> queue = new ArrayDeque<>();
			Set<BlockPos> currentGraph = new HashSet<>();
			Map<BlockPos, Set<EnumFacing>> endpoints = new HashMap<>();

			queue.add(pos);
			currentGraph.add(pos);
			unvisited.remove(pos);

			while (!queue.isEmpty()) {
				BlockPos currentPos = queue.remove();
				for (EnumFacing facing: EnumFacing.values()) {
					BlockPos offset = currentPos.offset(facing);
					if (world.isBlockLoaded(offset)) {
						TileEntity te = world.getTileEntity(offset);
						if (te instanceof TileEntityCable) {
							if (unvisited.contains(offset)) {
								currentGraph.add(offset);
								queue.add(offset);
								unvisited.remove(offset);
							}
						} else if (te != null && !te.isInvalid() && te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
							endpoints.computeIfAbsent(offset, k -> EnumSet.noneOf(EnumFacing.class)).add(facing);
						}
					}

				}
			}
			current = Pair.of(currentGraph, endpoints);
			subgraphs.add(current);
		}
		if (subgraphs.size() > 1) {
			var primary = subgraphs.get(0);
			this.tileEntities.clear();
			this.centerX = 0;
			this.centerY = 0;
			this.centerZ = 0;
			for (BlockPos pos: primary.getLeft()) {
				addToNetwork(pos);
			}
			for (Map.Entry<BlockPos, Set<EnumFacing>> entry: primary.getRight().entrySet()) {
				for (EnumFacing face: entry.getValue()) {
					addPointToNetwork(entry.getKey(), face);
				}
			}

			for (int i = 1; i < subgraphs.size(); i++) {
				var severedData = subgraphs.get(i);
				NetworkCable networkCable = new NetworkCable(world);
				for (Map.Entry<BlockPos, Set<EnumFacing>> entry: severedData.getRight().entrySet()) {
					for (EnumFacing face: entry.getValue()) {
						networkCable.addPointToNetwork(entry.getKey(), face);
					}
				}
				for (BlockPos pos: severedData.getLeft()) {
					networkCable.addToNetwork(pos);
					if (world.isBlockLoaded(pos)) {
						TileEntity te = world.getTileEntity(pos);
						if (te instanceof TileEntityCable cable && !te.isInvalid()) {
							cable.setNetworkSilent(networkCable);
						}
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
