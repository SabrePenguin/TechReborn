package com.sabrepenguin.techreborn.tileentity.components;

import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.tileentity.multis.MultiblockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultiblockCheckerComponent implements MachineComponent {
	private static final Random rand = new Random();

	private final TileEntity parent;
	private final MultiblockStructure[] structures;
	private final BlockPos.MutableBlockPos checkedPos;
	private MultiblockStructure structure;
	private IBlockState[][][] states;
	private final boolean async;

	private int running;
	private boolean isRunningValidation;
	protected AtomicBoolean isValid;

	public MultiblockCheckerComponent(TileEntity parent, MultiblockStructure[] structures, boolean async) {
		this.structures = structures;
		this.parent = parent;
		this.structure = null;
		this.states = null;
		running = rand.nextInt(40);
		isRunningValidation = false;
		this.async = async;
		isValid = new AtomicBoolean(false);
		this.checkedPos = new BlockPos.MutableBlockPos();
	}

	public void setStructureRotation(EnumFacing horizontalFacing) {
		structure = structures[horizontalFacing.getHorizontalIndex()];
		states = structure.getSizedArray();
	}

	public void validate() {
		if (running++ >= 40 && !isRunningValidation) {
			running = 0;
			if (async) {
				runValidation();
			}
		}
	}

	private void runValidation() {
		isRunningValidation = true;
		BlockPos pos = parent.getPos();
		World world = parent.getWorld();
		int xOrigin = pos.getX() - structure.controllerOffset().getX();
		int yOrigin = pos.getY() - structure.controllerOffset().getY();
		int zOrigin = pos.getZ() - structure.controllerOffset().getZ();
		for (int x = 0; x < states[0][0].length; x++) {
			for (int z = 0; z < states[0].length; z++) {
				checkedPos.setPos(xOrigin + x, 0, zOrigin + z);
				for (int y = 0; y < states.length; y++) {
					if (!world.isBlockLoaded(checkedPos) && isValid.get()) {
						states[y][z][x] = null;
						continue;
					}
					if (structure.blocks()[y][z][x] == ' ') {
						states[y][z][x] = null;
						continue;
					}
					checkedPos.setY(yOrigin + y);
					states[y][z][x] = world.getBlockState(checkedPos);
				}
			}
		}
		CompletableFuture.supplyAsync(() -> {
			for (int y = 0; y < states.length; y++) {
				for (int z = 0; z < states[0].length; z++) {
					for (int x = 0; x < states[0][0].length; x++) {
						if (!structure.statesEqual(structure.blocks()[y][z][x], states[y][z][x])) {
							return false;
						}
					}
				}
			}
			return true;
		}).thenAcceptAsync(isValid -> {
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			server.addScheduledTask(() -> {
				this.isValid.set(isValid);
				isRunningValidation = false;
			});
		});
	}

	public boolean isValid() {
		return isValid.get();
	}
}
