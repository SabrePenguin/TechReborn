package com.sabrepenguin.techreborn.tests;

import com.gtnewhorizons.horizonqa.api.GameTestAssertException;
import com.gtnewhorizons.horizonqa.api.GameTestHelper;
import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.tileentity.common.TileEntityIOManager;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TechRebornHelper {

	private final GameTestHelper helper;
	private final World world;

	public TechRebornHelper(GameTestHelper helper) {
		this.helper = helper;
		world = helper.getWorld();
	}

	public void insertPower(BlockPos pos, int power) {
		TileEntity entity = helper.assertTileEntityPresent(pos);
		TEEnergyStorage storage = getTREnergyStorage(entity, pos);
		storage.internalReceive(power, false);
	}

	public boolean itemOutputEquals(BlockPos pos, ItemStack stack, int slot) {
		TileEntity entity = helper.assertTileEntityPresent(pos);
		IItemHandler handler = getItemHandler(entity, pos);
		ItemStack output = handler.getStackInSlot(slot);
		if (output.getTagCompound() != null && stack.getTagCompound() != null) {
			return output.isItemEqual(stack) && output.getTagCompound().equals(stack.getTagCompound());
		}
		return output.isItemEqual(stack);
	}

	public boolean itemExists(BlockPos pos, int slot) {
		TileEntity entity = helper.assertTileEntityPresent(pos);
		IItemHandler handler = getItemHandler(entity, pos);
		ItemStack output = handler.getStackInSlot(slot);
		return !output.isEmpty();
	}

	private IItemHandler getItemHandler(TileEntity entity, BlockPos pos) {
		return getItemHandler(entity, pos, null);
	}

	private IItemHandler getItemHandler(TileEntity entity, BlockPos pos, @Nullable EnumFacing facing) {
		if (!entity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
			throw new GameTestAssertException("Expected IItemHandler", helper.absolute(pos));
		}
		return entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
	}

	private TEEnergyStorage getTREnergyStorage(TileEntity entity, BlockPos pos) {
		if (!entity.hasCapability(CapabilityEnergy.ENERGY, null))
			throw new GameTestAssertException("Expected default CapabilityEnergy", helper.absolute(pos));
		IEnergyStorage storage = entity.getCapability(CapabilityEnergy.ENERGY, null);
		if (storage instanceof TEEnergyStorage teStorage)
			return teStorage;
		else {
			assert storage != null;
			throw new GameTestAssertException("Expected TEEnergyStorage, got" + storage.getClass().getSimpleName(), helper.absolute(pos));
		}
	}

	@SuppressWarnings("ConstantConditions")
	public static ItemStack getFluidWithCount(Fluid fluid, int count) {
		ItemStack f = TRItems.cell.getCellWithFluid(fluid);
		f.setCount(count);
		return f;
	}

	private static void changeSide(World world, BlockPos pos, EnumFacing facing, int handlerIndex, int index) {
		TileEntity entity = world.getTileEntity(pos);
		if (entity == null) {
			return;
		}
		if (!(entity instanceof TileEntityIOManager ioManager)) {
			return;
		}

		ioManager.rotateSlot(facing.getIndex(), handlerIndex, index);
	}

	public enum TechRebornTileEntityHelper {
		ALLOY_SMELTER(2, 1),
		IRON_FURNACE(2, 1);

		final int inputIndex;
		final int outputIndex;
		final int batteryIndex;

		TechRebornTileEntityHelper(int inputSize, int outputSize) {
			this.inputIndex = 0;
			this.outputIndex = inputSize;
			this.batteryIndex = inputSize + outputSize;
		}

		public void changeInputSide(GameTestHelper helper, World world, BlockPos pos, EnumFacing facing, int index) {
			pos = helper.absolute(pos);
			changeSide(world, pos, facing, this.inputIndex, index);
		}
	}
}
