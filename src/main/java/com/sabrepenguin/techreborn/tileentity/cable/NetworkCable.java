package com.sabrepenguin.techreborn.tileentity.cable;

import com.github.bsideup.jabel.Desugar;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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

	private final long maxTransfer;
	private final int maxIntTransfer;

	private boolean powered = false;
	private final World world;

	public NetworkCable(World world, long maxTransfer) {
		this.world = world;
		this.maxTransfer = maxTransfer;
		maxIntTransfer = (int) Math.min(Integer.MAX_VALUE, maxTransfer);
		NetworkCableManager.registerNetwork(world, this);
	}

	public NetworkCable(World world, long maxTransfer, BlockPos pos) {
		this(world, maxTransfer);
		addToNetwork(pos);
	}

	public void addToNetwork(BlockPos pos) {
		tileEntities.add(pos);
	}

	public void removeFromNetwork(BlockPos pos) {
		if (tileEntities.remove(pos)) {
			if (tileEntities.isEmpty()) {
				NetworkCableManager.removeNetwork(world, this);
			} else {
				byte count = 0;
				for (EnumFacing facing: EnumFacing.values()) {
					BlockPos offset = pos.offset(facing);
					if (tileEntities.contains(offset) || endpoints.containsKey(offset)) {
						count++;
					}
					if (count > 1) {
						recalculateNetwork();
						break;
					}
				}
			}
		}
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

	@SuppressWarnings("ForLoopReplaceableByForEach")
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
			totalDemand += currentSinks.get(i).receiveEnergy(maxIntTransfer, true);
		}
		if (totalDemand == 0)
			return;

		long totalProduction = 0;
		for (int i = 0; i < currentSources.size(); i++) {
			long needed = totalDemand - totalProduction;
			if (needed <= 0) break;
			int extractable = (int) Math.min(maxIntTransfer, needed);
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
			int insertAmount = (int) Math.min(remainingToDistribute, maxIntTransfer);
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
		if (!subgraphs.isEmpty()) {
			var primary = subgraphs.get(0);
			this.tileEntities.clear();
			this.endpointList.clear();
			this.endpoints.clear();
			buildNetwork(this, world, primary.getLeft(), primary.getRight());

			for (int i = 1; i < subgraphs.size(); i++) {
				var severedData = subgraphs.get(i);
				NetworkCable networkCable = new NetworkCable(world, maxTransfer);
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
