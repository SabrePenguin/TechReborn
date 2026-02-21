package com.sabrepenguin.techreborn.tileentity.tier0;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.IOnSlotChanged;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.IronFurnace;
import com.sabrepenguin.techreborn.capability.stackhandler.*;
import com.sabrepenguin.techreborn.gui.FurnaceFuelWidget;
import com.sabrepenguin.techreborn.tileentity.ISetWorldNameable;
import mcp.MethodsReturnNonnullByDefault;
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
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityIronFurnace extends TileEntity implements ITickable, ISetWorldNameable, IOnSlotChanged, IGuiHolder<PosGuiData> {

	private final ItemStackHandler inventory = new ItemStackHandler(3) {
		@Override
		protected void onContentsChanged(int slot) {
			if (slot == 0)
				refreshResult = true;
			markDirty();
		}
	};
	private final RestrictedItemStackHandler input = new RestrictedItemStackHandler(inventory, 0);
	private final RestrictedItemStackHandler fuel = new RestrictedItemStackHandler(inventory, 1);
	private final LimitedItemStackHandler output =
			new LimitedItemStackHandler(new RestrictedItemStackHandler(inventory, 2), SlotAction.OUTPUT);

	private String customName;

	private int burnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;

	private ItemStack cachedResult = ItemStack.EMPTY;
	private boolean refreshResult = false;

	public boolean isBurning() {
		return this.burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(TileEntityIronFurnace inventory)
	{
		return inventory.getBurnTime() > 0;
	}

	private void refreshRecipe(ItemStack in) {
		refreshResult = false;
		ItemStack output = FurnaceRecipes.instance().getSmeltingResult(in);
		if (cachedResult.isItemEqual(output))
			return;
		cookTime = 0;
		totalCookTime = getDefaultTotalCookTime();
		cachedResult = output;
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
	public ITextComponent getDisplayName() {
		if (hasCustomName()) {
			return new TextComponentString(customName);
		}
		return new TextComponentTranslation("tile.techreborn.iron_furnace.name");
	}

	@Override
	public boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}

	@Override
	public String getName() {
		return customName;
	}

	@Override
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
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
		if (compound.hasKey("CustomName"))
			this.customName = compound.getString("CustomName");
        super.readFromNBT(compound);
    }

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
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
		if (this.world.isRemote)
			return;
		boolean isBurning = this.isBurning();
		if (isBurning) {
			this.burnTime--;
		}
		boolean markDirty = false;

		ItemStack inputItemstack = input.getStackInSlot(0);
		if (refreshResult)
			refreshRecipe(inputItemstack);
		ItemStack result = cachedResult;
		if (result.isEmpty()) return;
		ItemStack fuel = inventory.getStackInSlot(1);
		if (this.isBurning() || (!fuel.isEmpty() && !inputItemstack.isEmpty())) {
			if (!this.isBurning() && this.canSmelt()) {
				this.burnTime = (int)(TileEntityFurnace.getItemBurnTime(fuel)*1.25);
				this.currentItemBurnTime = this.burnTime;
				if (this.isBurning()) {
					markDirty = true;
					if (!fuel.isEmpty()) {
						ItemStack realFuel = inventory.extractItem(1, 1, false);
						Item item = realFuel.getItem();
						ItemStack container = item.getContainerItem(realFuel);
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
		if (markDirty) markDirty();
    }


	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		ModularPanel panel = ModularPanel.defaultPanel("iron_furnace");
		syncManager.registerSlotGroup(new SlotGroup("input", 1))
				.registerSlotGroup(new SlotGroup("fuel", 1, 120, true));
		panel.bindPlayerInventory();
		panel.child(new ProgressWidget()
				.size(24)
				.pos(80, 34)
				.texture(GuiTextures.PROGRESS_ARROW, 20)
				.value(new DoubleSyncValue(() -> (double) this.cookTime / this.totalCookTime,
						value -> this.cookTime = (int) (value * totalCookTime))))
				.child(new FurnaceFuelWidget()
						.pos(58, 37)
						.value(new DoubleSyncValue(() -> (double) this.burnTime / this.currentItemBurnTime,
								value -> this.burnTime = (int) (value * this.currentItemBurnTime))));
		panel.child(new ItemSlot().pos(56, 17)
						.slot(new ModularSlot(input, 0)
								.slotGroup("input")
								.changeListener(this)))
				.child(new ItemSlot().pos(56, 53)
						.slot(new ModularSlot(fuel, 0)
						.slotGroup("fuel")
						.filter(item -> TileEntityFurnace.getItemBurnTime(item) != 0)))
				.child(new ItemSlot().pos(116, 35)
						.slot(new ModularSlot(output, 0)
								.accessibility(false, true)));
		return panel;
	}

	@Override
	public void onChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (world.isRemote || init) return;
		if (onlyAmountChanged) return;
		refreshResult = true;
		markDirty();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
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
