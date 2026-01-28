package com.sabrepenguin.techreborn.tileentity.tier0;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.drawable.UITexture;
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
import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.machines.BlockIronAlloyFurnace;
import com.sabrepenguin.techreborn.capability.stackhandler.*;
import com.sabrepenguin.techreborn.recipe.AlloyRecipe;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import com.sabrepenguin.techreborn.tileentity.IChangedTileEntity;
import com.sabrepenguin.techreborn.tileentity.ISetWorldNameable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;


public class TileEntityIronAlloyFurnace extends TileEntity implements ITickable, ISetWorldNameable, IChangedTileEntity, IOnSlotChanged, IGuiHolder<PosGuiData> {

	private final ItemStackHandler inventory;
	private final RestrictedItemStackHandler input;
	private final RestrictedItemStackHandler fuel;
	private final LimitedItemStackHandler output;

	private int totalCookTime = 200;

	private String customName;

	private int cookTime;
	private int burnTime;
	private int totalBurnTime;

	private AlloyRecipe cachedRecipe = null;
	private boolean shouldChange = false;

	public TileEntityIronAlloyFurnace() {
		inventory = new ChangedItemStackHandler(4, this);
		input = new RestrictedItemStackHandler(inventory, 0, 2);
		fuel = new RestrictedItemStackHandler(inventory, 2);
		output = new LimitedItemStackHandler(new RestrictedItemStackHandler(inventory, 3), SlotType.OUTPUT);
	}

	@Override
	public @NotNull ITextComponent getDisplayName() {
		if (hasCustomName()) {
			return new TextComponentString(customName);
		}
		return new TextComponentTranslation("tile.techreborn.iron_alloy_furnace.name");
	}

	@Override
	public @NotNull String getName() {
		return customName;
	}

	@Override
	public boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}

	@Override
	public void setCustomName(String name) {
		this.customName = name;
	}

	public int getItemBurnTime(ItemStack stack) {
		return stack.isEmpty() ? 0 :
				(int) (TileEntityFurnace.getItemBurnTime(stack) * 1.25);
	}

	private boolean isBurning() {
		return burnTime > 0;
	}

	private void refreshRecipe() {
		shouldChange = false;
		if (cachedRecipe != null) {
			if (RecipeUtils.checkRecipeValid(input, cachedRecipe.getInputs()))
				return;
		}
		AlloyRecipe newRecipe = RegistryHandler.instance().getAlloyRegistry().getRecipe(input);
		if (cachedRecipe != newRecipe) {
			cachedRecipe = newRecipe;
			cookTime = 0;
		}
	}

	private boolean canProcess() {
		if (cachedRecipe == null)
			return false;
		ItemStack output = this.output.getStackInSlot(0);
		ItemStack recipeOutput = cachedRecipe.getOutput();
		if (output.isEmpty()) return true;
		if (!output.isItemEqual(recipeOutput)) return false;
		if (!RecipeUtils.checkRecipeValid(input, cachedRecipe.getInputs())) return false;
		int res = output.getCount() + recipeOutput.getCount();
		return res <= this.output.getSlotLimit(0) && res <= output.getMaxStackSize();
	}

	private void processItem() {
		ItemStack output = this.output.getStackInSlot(0);
		if (output.isEmpty() || output.isItemEqual(cachedRecipe.getOutput().copy()))
			this.inventory.insertItem(3, cachedRecipe.getOutput().copy(), false);
		RecipeUtils.applyRecipeToHandler(input, cachedRecipe.getInputs());
	}

	@Override
	public boolean shouldRefresh(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState oldState, @NotNull IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void update() {
		if (this.world.isRemote)
			return;
		if (shouldChange)
			refreshRecipe();
		boolean wasBurning = isBurning();
		if (wasBurning)
			burnTime--;
		boolean markDirty = false;
		ItemStack inputLeft = input.getStackInSlot(0);
		ItemStack inputRight = input.getStackInSlot(1);
		if (!inputLeft.isEmpty() && !inputRight.isEmpty()) {
			if (!isBurning() && canProcess()) {
				ItemStack fuel = this.fuel.getStackInSlot(0);
				this.burnTime = (int) (TileEntityFurnace.getItemBurnTime(fuel) * 1.25);
				this.totalBurnTime = this.burnTime;
				if (this.isBurning()) {
					markDirty = true;
					ItemStack realFuel = this.fuel.extractItem(0, 1, false);
					Item item = realFuel.getItem();
					ItemStack container = item.getContainerItem(realFuel);
					if (!container.isEmpty()) {
						this.fuel.insertItem(0, container, false);
					}
				}
			}
			if (isBurning() && canProcess()) {
				cookTime++;
				if (cookTime >= totalCookTime) {
					cookTime = 0;
					processItem();
					markDirty = true;
				}
			} else {
				cookTime = 0;
			}
		}
		else if (!this.isBurning() && this.cookTime > 0) {
			cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
		}
		if (wasBurning != this.isBurning()) {
			markDirty = true;
		}
		// Last stage (always)
		updateDelayedState();
		if (markDirty) markDirty();
	}

	private void updateState() {
		BlockIronAlloyFurnace.setState(isBurning(), world, pos);
	}

	private void updateDelayedState() {
		if (world.getTotalWorldTime() % 20 == 0) {
			updateState();
		}
	}

	@Override
	public void onChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (world.isRemote || init) return;
		shouldChange = true;
		markDirty();
	}

	@Override
	public void onSlotCountChanged(int slot) {
//		markDirty();
//		if (slot == 0 || slot == 1) {
//			shouldChange = true;
//		}
	}

	@Override
	public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
		compound.setInteger("BurnTime", this.burnTime);
		compound.setInteger("TotalBurnTime", this.totalBurnTime);
		compound.setInteger("CookTime", this.cookTime);
		compound.setTag("inventory", inventory.serializeNBT());
		if (hasCustomName())
			compound.setString("CustomName", this.customName);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(@NotNull NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		this.burnTime = compound.getInteger("BurnTime");
		this.totalBurnTime = compound.getInteger("TotalBurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.cachedRecipe = RegistryHandler.instance().getAlloyRegistry().getRecipe(Arrays.asList(
				input.getStackInSlot(0),
				input.getStackInSlot(1)
		));
		if (compound.hasKey("CustomName"))
			this.customName = compound.getString("CustomName");
		super.readFromNBT(compound);
	}

	@Override
	public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability || super.hasCapability(capability, facing);
	}

	@Override
	public @Nullable <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
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
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		ModularPanel panel = ModularPanel.defaultPanel("iron_alloy_furnace");
		syncManager.registerSlotGroup("inputs", 2)
				.registerSlotGroup(new SlotGroup("fuel", 1, 120, true));
		panel.bindPlayerInventory();
		panel.child(new ProgressWidget()
				.size(20)
				.leftRelOffset(0.5f, 3)
				.topRelOffset(0.25f, -2)
				.texture(GuiTextures.PROGRESS_ARROW, 20)
				.value(new DoubleSyncValue(() -> (double) this.cookTime / this.totalCookTime, value -> this.cookTime = (int) (value * this.totalCookTime)))
		);
		panel.child(new ProgressWidget()
				.size(14)
				.pos(58, 37)
				.direction(ProgressWidget.Direction.UP)
				.texture(UITexture.builder().location(Tags.MODID, "gui/fire").imageSize(14, 28).canApplyTheme().build(), 14)
				.value(new DoubleSyncValue(() -> (double) this.burnTime / this.totalBurnTime, value -> this.burnTime = (int) (value * this.totalBurnTime)))
		);
		panel.child(new ItemSlot().pos(47, 17)
						.slot(new ModularSlot(input, 0)
								.slotGroup("inputs")
								.changeListener(this)))
				.child(new ItemSlot().pos(65, 17)
						.slot(new ModularSlot(input, 1)
								.slotGroup("inputs")
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
}
