package com.sabrepenguin.techreborn.tileentity.processing;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.IPanelHandler;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.integration.jei.ModularUIJeiPlugin;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.IntSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.IOnSlotChanged;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.energy.TEEnergyStorage;
import com.sabrepenguin.techreborn.capability.stackhandler.*;
import com.sabrepenguin.techreborn.gui.PowerDisplayWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.tileentity.ISetWorldNameable;
import com.sabrepenguin.techreborn.tileentity.MachineIOManager;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityElectricFurnace extends TileEntity implements ISetWorldNameable, ITickable, IGuiHolder<PosGuiData>, IOnSlotChanged, ISideConfigTE {
	private final ItemStackHandler inventory;
	private final RestrictedItemStackHandler input;
	private final RestrictedItemStackHandler battery;
	private final LimitedItemStackHandler output;
	private final RestrictedItemStackHandler upgrades;
	private final TEEnergyStorage energyStorage;

	private final MachineIOManager ioManager;

	private String customName;

	private ItemStack cachedResult = ItemStack.EMPTY;
	private boolean refreshResult = false;
	private int processTime = 0;
	private int totalProcessTime = 200;
	private boolean isActive = false;
	private int energyCost = 24;

	public TileEntityElectricFurnace() {
		inventory = new StackLimitedItemStackHandler(7, Arrays.asList(Pair.of(3, 64), Pair.of(4, 1))) {
			@Override
			protected void onContentsChanged(int slot) {
				if (slot == 0) {
					refreshResult = true;
				}
			}
		};
		input = new RestrictedItemStackHandler(inventory, 0);
		output = new LimitedItemStackHandler(new RestrictedItemStackHandler(inventory, 1), SlotAction.OUTPUT).setFilter(stack -> stack.getItem() == TRItems.upgrades);
		battery = new RestrictedItemStackHandler(inventory, 2);
		upgrades = new RestrictedItemStackHandler(inventory, 3, 7);
		energyStorage = new TEEnergyStorage(4000, 128, energyCost);
		energyStorage.setCanExtract(false);
		SideConfig inputConfig = new SideConfig(input, SlotAction.INPUT);
		SideConfig outputConfig = new SideConfig(output, SlotAction.OUTPUT);
		SideConfig batteryConfig = new SideConfig(battery, SlotAction.BIDIRECTIONAL);
		ioManager = new MachineIOManager(inputConfig, outputConfig, batteryConfig);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		if (compound.hasKey("energy"))
			energyStorage.setEnergy(compound.getInteger("energy"));
		if (compound.hasKey("CustomName"))
			this.customName = compound.getString("CustomName");
		ioManager.readFromNBT(compound.getCompoundTag("IOManager"));
		processTime = compound.getInteger("processed");
		isActive = compound.getBoolean("active");
		cachedResult = FurnaceRecipes.instance().getSmeltingResult(input.getStackInSlot(0));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("energy", energyStorage.getEnergyStored());
		compound.setInteger("processed", processTime);
		compound.setBoolean("isActive", isActive);
		if (hasCustomName())
			compound.setString("CustomName", customName);
		compound.setTag("IOManager", ioManager.writeToNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public @Nullable SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 3, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		boolean previousState = isActive;
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.getNbtCompound());
		if (world != null && this.world.isRemote && previousState != isActive) {
			world.markBlockRangeForRenderUpdate(pos, pos);
		}
	}

	@Override
	public void rotateSlot(int sideIndex, int handlerIndex, int localSlotIndex) {
		if (ioManager.getSide(sideIndex).rotateSlot(handlerIndex, localSlotIndex)) {
			updateTE();
		}
	}

	@Override
	public void swapSlot(int index, boolean input) {
		ioManager.swapIndex(index, input);
		updateTE();
	}

	@Override
	public void updateTE() {
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}

	@Override
	public MachineIOManager getManager() {
		return this.ioManager;
	}

	private void refreshCachedRecipe(ItemStack in) {
		refreshResult = false;
		ItemStack output = FurnaceRecipes.instance().getSmeltingResult(in);
		if (ItemStack.areItemStacksEqual(output, cachedResult))
			return;
		processTime = 0;
		cachedResult = output;
	}

	private boolean canProcess() {
		ItemStack out = output.getStackInSlot(0);
		if (out.isEmpty()) return true;
		if (!out.isItemEqual(cachedResult)) return false;
		int res = out.getCount() + cachedResult.getCount();
		return res <= output.getSlotLimit(0) && res <= out.getMaxStackSize();
	}

	private void processItem() {
		ItemStack currentOutput = output.getStackInSlot(0);
		if (currentOutput.isEmpty()) inventory.setStackInSlot(1, cachedResult.copy()); // Unfortunate, but output will not let us set the stack
		else if (currentOutput.isItemEqual(cachedResult)) inventory.insertItem(1, cachedResult.copy(), false);
		input.extractItem(0, 1, false);
	}

	@Override
	public void update() {
		if (this.world.isRemote)
			return;
		boolean isDirty = false;
		if (ioManager.performTransfer(world, pos)) {
			isDirty = true;
		}
		ItemStack ingredient = input.getStackInSlot(0);
		if (refreshResult) {
			refreshCachedRecipe(ingredient);
		}
		boolean active = false;
		if (!cachedResult.isEmpty() && this.canProcess()) {
			if (energyStorage.internalExtract(energyCost, true) == energyCost) {
				energyStorage.internalExtract(energyCost, false);
				processTime++;
				active = true;
				if (processTime >= totalProcessTime) {
					processItem();
					processTime = 0;
					isDirty = true;
				}
			}
		} else if (processTime > 0) {
			processTime = 0;
			isDirty = true;
		}

		if (active != isActive) {
			isActive = active;
			updateState(active);
			isDirty = true;
		}
		if (isDirty)
			markDirty();
	}

	private void updateState(boolean state) {
		IBlockState currentState = world.getBlockState(pos);
		world.setBlockState(pos, currentState.withProperty(BlockHorizontalMachine.ACTIVE, state), 3);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY
				|| capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| super.hasCapability(capability, facing);
	}

	@Override
	public @Nullable <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == null) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioManager.getSide(facing));
		} else if (capability == CapabilityEnergy.ENERGY) {

			return CapabilityEnergy.ENERGY.cast(energyStorage);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		ModularPanel panel = ModularPanel.defaultPanel("electric_furnace");
		syncManager.registerSlotGroup(new SlotGroup("inputs", 1, 1, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		IPanelHandler panelHandler = syncManager.syncedPanel("config", true,
				(syncManager1, syncHandler) ->
						TRGuis.createConfigPanel(syncManager1, syncHandler, this.getPos(), panel.getArea(),
								this.ioManager,
								getFacing,
								new SlotPosition(SlotAction.INPUT, 55, 45, 0, 0),
								new SlotPosition(SlotAction.OUTPUT, 101, 45, 1, 0),
								new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 0)));
		TRGuis.addConfigPanel(panel, panelHandler);

		if (hasCustomName()) {
			panel.child(IKey.str(getName())
					.asWidget()
					.align(Alignment.TopCenter)
					.top(7));
		} else {
			panel.child(IKey.lang("tile.techreborn.electric_furnace.name")
					.asWidget()
					.align(Alignment.TopCenter)
					.top(7));
		}
		panel.bindPlayerInventory();
		panel.child(new ProgressWidget()
				.size(20)
				.leftRelOffset(0.5f, -1)
				.topRelOffset(0.3f, 2)
				.texture(GuiTextures.PROGRESS_ARROW, 20)
				.value(new DoubleSyncValue(() -> (double) processTime / totalProcessTime,
						value -> processTime = (int) (value * totalProcessTime)))
		);
		panel.child(new ButtonWidget<>()
				.size(20, 15)
				.pos(77, 47)
				.tooltip(tooltip -> {
					tooltip.addLine(IKey.str("Open JEI"));
				})
				.invisible()
				.onMousePressed(mouseButton -> {
					if (ModularUIJeiPlugin.getRuntime() != null) {
						ModularUIJeiPlugin.getRuntime().getRecipesGui().showCategories(Collections.singletonList("minecraft.smelting"));
						return true;
					}
					return false;
				}));
		panel.child(new ItemSlot().pos(55, 45)
						.slot(new ModularSlot(input, 0)
								.slotGroup("inputs")
								.changeListener(this)))
				.child(new ItemSlot().pos(101, 45)
						.slot(new ModularSlot(output, 0)
								.accessibility(false, true)))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(battery, 0)));
		panel.child(new PowerDisplayWidget()
				.pos(9, 6)
				.energyHandler(this.energyStorage)
				.value(new IntSyncValue(energyStorage::getEnergyStored)));
		panel.child(TRGuis.createUpdateTab(upgrades, "upgrades").leftRelOffset(1f, 1));
		return panel;
	}

	@Override
	public void onChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (world.isRemote || init) return;
		if (onlyAmountChanged) {
			refreshResult = false;
			return;
		}
		refreshResult = true;
		markDirty();
	}

	@Override
	public ITextComponent getDisplayName() {
		if (hasCustomName()) {
			return new TextComponentString(customName);
		}
		return new TextComponentTranslation("tile.techreborn.electric_furnace.name");
	}

	@Override
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public String getName() {
		return customName;
	}

	@Override
	public boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}
}
