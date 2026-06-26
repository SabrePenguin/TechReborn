package com.sabrepenguin.techreborn.tileentity.components;

import com.cleanroommc.modularui.value.sync.BooleanSyncValue;
import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import com.sabrepenguin.techreborn.util.ItemStackWrapper;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.List;
import java.util.function.Consumer;

public class LockableRecipeComponent extends RecipeComponent {
	protected final TileEntity parent;
	protected boolean locked;
	protected boolean rebalance;
	protected final Object2ObjectMap<ItemStackWrapper, IntList> balanceCache;

	public LockableRecipeComponent(TileEntity parent, ITRRegistry registry, IItemHandlerModifiable inputs, IItemHandlerModifiable internalOutput, TEEnergyStorage storage, Consumer<Boolean> stateUpdate) {
		super(registry, inputs, internalOutput, storage, stateUpdate);
		locked = false;
		this.parent = parent;
		rebalance = false;
		balanceCache = new Object2ObjectOpenHashMap<>(9);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("locked")) {
			locked = compound.getBoolean("locked");
		}
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("locked", locked);
		return super.writeToNBT(compound);
	}

	@Override
	public boolean update() {
		if (locked) {
			if (rebalance) {
				rebalance = false;
				InventoryUtils.setBalanceCache(inputs, balanceCache);
				InventoryUtils.balanceHandler(inputs, balanceCache);
			}
		}
		return super.update();
	}

	@Override
	protected boolean canProcess() {
		if (locked) {
			for (int i = 0; i < inputs.getSlots(); i++) {
				ItemStack stack = inputs.getStackInSlot(i);
				if (stack.isEmpty())
					continue;
				if (stack.getCount() == 1)
					return false;
			}
		}
		return super.canProcess();
	}

	@Override
	protected void processItem() {
		ItemStack outputItem = internalOutputs.getStackInSlot(0);
		ItemStack cachedOutput = cachedRecipe.getOutput().get(0);
		if (outputItem.isEmpty()) {
			internalOutputs.insertItem(0, cachedOutput.copy(), false);
		}
		else if (outputItem.isItemEqual(cachedOutput)) {
			ItemStack outputCopy = cachedOutput.copy();
			ItemStack remainder = internalOutputs.insertItem(0, outputCopy, true);
			if (remainder.isEmpty()) {
				internalOutputs.insertItem(0, outputCopy, false);
			} else {
				// Not enough room in output
				return;
			}
		} else {
			// Output does not match
			return;
		}
		List<ItemStack> leftovers = RecipeUtils.applyRecipeToHandler(inputs, cachedRecipe.getInputs());
		if (!leftovers.isEmpty()) {
			BlockPos parentPos = parent.getPos();
			World parentWorld = parent.getWorld();
			BlockPos pos = parentPos.offset(parentWorld.getBlockState(parentPos).getValue(BlockHorizontal.FACING));
			for (ItemStack stack : leftovers) {
				InventoryHelper.spawnItemStack(parentWorld, pos.getX(), pos.getY(), pos.getZ(), stack);
			}
		}
		super.processItem();
	}

	@Override
	public void informRefreshRecipe() {
		informRebalance();
		super.informRefreshRecipe();
	}

	private void informRebalance() {
		rebalance = true;
	}

	public BooleanSyncValue getLocked() {
		return new BooleanSyncValue(() -> locked, val -> {
			parent.markDirty();
			locked = val;
			if (locked) {
				informRebalance(); // TODO better, still not great
			}
		});
	}
}
