package com.sabrepenguin.techreborn.tileentity.cable;

import com.github.bsideup.jabel.Desugar;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.ref.WeakReference;
import java.util.*;

public class NetworkCable {
	private final Set<BlockPos> tileEntities = new HashSet<>();

	private final Map<BlockPos, Set<EnumFacing>> endpoints = new HashMap<>();
	private final List<NetworkEndpoint> endpointList = new ArrayList<>();

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

		if (world.isBlockLoaded(pos)) {
			TileEntity te = world.getTileEntity(pos);
			if (te != null && !te.isInvalid() && te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
				IEnergyStorage storage = te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
				if (storage != null) {
					endpointList.add(new NetworkEndpoint(te, storage, facing));
				}
			}
		}
	}

	public void removePointFromNetwork(BlockPos pos, EnumFacing facing) {
		if (endpoints.containsKey(pos)) {
			endpoints.get(pos).remove(facing);
			if (endpoints.get(pos).isEmpty()) {
				endpoints.remove(pos);
			}
			endpointList.removeIf(endpoint -> {
				TileEntity te = endpoint.teRef.get();
				return te != null && te.getPos().equals(pos) && endpoint.facing == facing;
			});
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
		network.endpointList.clear();
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
		if (world.isRemote || endpointList.isEmpty())
			return;
		List<IEnergyStorage> currentSources = new ArrayList<>();
		List<IEnergyStorage> currentSinks = new ArrayList<>();

		Iterator<NetworkEndpoint> iterator = endpointList.iterator();
		while (iterator.hasNext()) {
			NetworkEndpoint endpoint = iterator.next();
			TileEntity te = endpoint.teRef.get();
			if (te == null || te.isInvalid() || !world.isBlockLoaded(te.getPos())) {
				iterator.remove();
				continue;
			}
			if (endpoint.capability.canExtract()) {
				currentSources.add(endpoint.capability);
			}
			if (endpoint.capability.canReceive()) {
				currentSinks.add(endpoint.capability);
			}
		}
		if (currentSinks.isEmpty() || currentSources.isEmpty())
			return;

		long totalDemand = 0;
		for (int i = 0; i < currentSinks.size(); i++) {
			totalDemand += currentSinks.get(i).receiveEnergy(Integer.MAX_VALUE, true);
		}
		if (totalDemand == 0)
			return;

		long totalProduction = 0;
		for (int i = 0; i < currentSources.size(); i++) {
			long needed = totalDemand - totalProduction;
			if (needed <= 0) break;
			int extractable = needed >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) needed;
			totalProduction += currentSources.get(i).extractEnergy(extractable, false);
		}

		if (totalProduction == 0) {
			powered = false;
			return;
		} else {
			powered = true;
		}

		long remainingToDistribute = totalProduction;
		for (int i = 0; i < currentSinks.size(); i++) {
			if (remainingToDistribute <= 0) break;
			int insertAmount = remainingToDistribute > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) remainingToDistribute;
			int accepted = currentSinks.get(i).receiveEnergy(insertAmount, false);
			remainingToDistribute -= accepted;
		}
	}

	private void recalculateNetwork() {
		if (tileEntities.isEmpty() || world.isRemote)
			return;
		Set<BlockPos> unvisited = new HashSet<>(tileEntities);
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
			this.endpointList.clear();
			this.endpoints.clear();
			this.centerX = 0;
			this.centerY = 0;
			this.centerZ = 0;
			buildNetwork(this, world, primary.getLeft(), primary.getRight());

			for (int i = 1; i < subgraphs.size(); i++) {
				var severedData = subgraphs.get(i);
				NetworkCable networkCable = new NetworkCable(world);
				buildNetwork(networkCable, world, severedData.getLeft(), severedData.getRight());
			}
		}
	}

	private static void buildNetwork(NetworkCable network, World world, Set<BlockPos> left, Map<BlockPos, Set<EnumFacing>> right) {
		for (BlockPos pos: left) {
			network.addToNetwork(pos);
			if (world.isBlockLoaded(pos)) {
				TileEntity te = world.getTileEntity(pos);
				if (te instanceof TileEntityCable cable && !te.isInvalid()) {
					cable.setNetworkSilent(network);
				}
			}
		}
		for (Map.Entry<BlockPos, Set<EnumFacing>> entry: right.entrySet()) {
			for (EnumFacing face: entry.getValue()) {
				network.addPointToNetwork(entry.getKey(), face);
			}
		}
	}

	public int networkSize() {
		return tileEntities.size();
	}

	public boolean isPowered() {
		return powered;
	}

	@Desugar
	public record NetworkEndpoint(WeakReference<TileEntity> teRef, IEnergyStorage capability, EnumFacing facing) {
		public NetworkEndpoint(TileEntity te, IEnergyStorage storage, EnumFacing facing) {
			this(new WeakReference<>(te), storage, facing);
		}
	}
}
