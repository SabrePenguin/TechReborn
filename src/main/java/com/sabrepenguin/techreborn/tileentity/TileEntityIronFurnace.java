package com.sabrepenguin.techreborn.tileentity;

import com.sabrepenguin.techreborn.blocks.machines.IronFurnace;
import com.sabrepenguin.techreborn.capability.stackhandler.ChangedItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.LimitedItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.RestrictedItemStackHandler;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileEntityIronFurnace extends TileEntity implements ITickable, IChangedTileEntity { //, IWorldNameable {

	private final ItemStackHandler inventory = new ChangedItemStackHandler(3, this);
	private final RestrictedItemStackHandler input = new RestrictedItemStackHandler(inventory, 0);
	private final RestrictedItemStackHandler fuel = new RestrictedItemStackHandler(inventory, 1);
	private final LimitedItemStackHandler output =
			new LimitedItemStackHandler(new RestrictedItemStackHandler(inventory, 2), SlotType.OUTPUT); // We love decorators :)

	private String customName;

	private int burnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;

	private ItemStack cachedResult = ItemStack.EMPTY;
	private ItemStack lastInput = ItemStack.EMPTY;

	public boolean isBurning() {
		return this.burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(TileEntityIronFurnace inventory)
	{
		return inventory.getBurnTime() > 0;
	}

	private boolean canSmelt() {
		ItemStack output = inventory.getStackInSlot(2);
		if (output.isEmpty()) return true;
		if (!output.isItemEqual(cachedResult)) return false;
		int res = output.getCount() + cachedResult.getCount();
		return res <= inventory.getSlotLimit(2) && res <= output.getMaxStackSize();
	}

	private void smeltItem() {
		if (canSmelt()) {
			ItemStack output = inventory.getStackInSlot(2);
			if (output.isEmpty()) inventory.setStackInSlot(2, cachedResult.copy());
			else if (output.isItemEqual(cachedResult)) inventory.insertItem(2, cachedResult.copy(), false);
			inventory.extractItem(0, 1, false);
		}
	}

	private int getDefaultTotalCookTime() {
		return 160;
	}

	@Override
	public @Nullable ITextComponent getDisplayName() {
		if (hasCustomName()) {
			return new TextComponentString(customName);
		}
		return new TextComponentTranslation("tile.techreborn.iron_furnace.name");
	}

	private boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}

	@Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("BurnTime", this.burnTime);
		compound.setInteger("CookTime", this.cookTime);
		compound.setInteger("CookTimeTotal", this.totalCookTime);
		compound.setTag("inventory", inventory.serializeNBT());
		if (hasCustomName())
			compound.setString("CustomName", this.customName);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		cachedResult = FurnaceRecipes.instance().getSmeltingResult(inventory.getStackInSlot(0));
		if (!fuel.getStackInSlot(0).isEmpty())
			this.currentItemBurnTime = (int)(TileEntityFurnace.getItemBurnTime(fuel.getStackInSlot(0)) * 1.25);
		if (hasCustomName())
			this.customName = compound.getString("CustomName");
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public @Nullable <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == null) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
			} else if (facing == EnumFacing.UP) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(input);
			} else if (facing == EnumFacing.DOWN) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(output);
			} else {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(fuel);
			}
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
		boolean isBurning = this.isBurning();
		if (isBurning) {
			this.burnTime--;
		}
		boolean markDirty = false;

		if (!world.isRemote) {
			ItemStack input = inventory.getStackInSlot(0);
			ItemStack result = cachedResult;
			if (result.isEmpty()) return;
			ItemStack fuel = inventory.getStackInSlot(1);
			if (this.isBurning() || (!fuel.isEmpty() && !input.isEmpty())) {
				if (!this.isBurning() && this.canSmelt()) {
					this.burnTime = (int)(TileEntityFurnace.getItemBurnTime(fuel)*1.25);
					this.currentItemBurnTime = this.burnTime;
					if (this.isBurning()) {
						markDirty = true;
						if (!fuel.isEmpty()) {
							Item item = fuel.getItem();
							fuel.shrink(1);
							ItemStack container = item.getContainerItem(fuel);
							if (!container.isEmpty()) {
								inventory.insertItem(1, container, false);
							}
						}
					}
				}
				if (this.isBurning() && this.canSmelt()) {
					cookTime++;
					if (cookTime >= totalCookTime) {
						cookTime = 0;
						totalCookTime = getDefaultTotalCookTime();
						smeltItem();
						markDirty = true;
					}
				}
				else {
					cookTime = 0;
				}
			}
			else if (!this.isBurning() && this.cookTime > 0) {
				cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
			}
			if (isBurning != this.isBurning()) {
				markDirty = true;
				IronFurnace.setState(isBurning(), world, pos);
			}
		}
		if (markDirty) markDirty();
    }

	@Override
	public void onInputChanged(int slot, ItemStack stack) {
		markDirty();
		if (slot == 0) {
			if (!stack.isEmpty()) {
				ItemStack copy = stack.copy();
				copy.setCount(1);
				if (!ItemStack.areItemsEqual(copy, lastInput)) {
					lastInput = copy;
					cachedResult = FurnaceRecipes.instance().getSmeltingResult(stack);
					totalCookTime = getDefaultTotalCookTime();
					cookTime = 0;
				}
			}
		}
	}

	@Override
	public boolean shouldRefresh(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState oldState, @NotNull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	// We love setters and getters

	public int getBurnTime() {
		return burnTime;
	}

	public int getCurrentBurnTime() {
		return currentItemBurnTime;
	}

	public int getTotalCookTime() {
		return totalCookTime;
	}

	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	public void setTotalCookTime(int totalCookTime) {
		this.totalCookTime = totalCookTime;
	}

	public void setCurrentBurnTime(int currentBurnTime) {
		this.currentItemBurnTime = currentBurnTime;
	}
}
