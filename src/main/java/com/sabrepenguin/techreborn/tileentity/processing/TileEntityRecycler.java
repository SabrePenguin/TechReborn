package com.sabrepenguin.techreborn.tileentity.processing;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.utils.Alignment;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ProgressWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.cleanroommc.modularui.widgets.slot.ModularSlot;
import com.cleanroommc.modularui.widgets.slot.SlotGroup;
import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.gui.LargeItemSlot;
import com.sabrepenguin.techreborn.gui.PowerDisplayWidget;
import com.sabrepenguin.techreborn.gui.SlotPosition;
import com.sabrepenguin.techreborn.gui.TRGuis;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.jei.TRRecipePlugin;
import com.sabrepenguin.techreborn.tileentity.IOManager;
import com.sabrepenguin.techreborn.tileentity.components.EnergyComponent;
import com.sabrepenguin.techreborn.tileentity.components.ItemIOComponent;
import com.sabrepenguin.techreborn.tileentity.components.specialized.RandomRecipeComponent;
import com.sabrepenguin.techreborn.tileentity.components.UpgradeComponent;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityRecycler extends TileEntity implements IGuiHolder<PosGuiData>, ITickable, ISideConfigTE {

	protected final ItemIOComponent ioComponent;
	protected final EnergyComponent energyComponent;
	protected final UpgradeComponent upgradeComponent;
	protected final RandomRecipeComponent recipeComponent;

	@SuppressWarnings("ConstantConditions")
	protected static ItemStack recipeOutput = new ItemStack(TRItems.part, 1, Part.PartMeta.scrap.metadata());

	public TileEntityRecycler() {
		this.ioComponent = new ItemIOComponent(this, 1, 1, true, this::refreshSlot);
		this.energyComponent = new EnergyComponent(TechRebornConfig.machineConfig.recycler.maxEnergy,
				TechRebornConfig.machineConfig.recycler.maxInput, TechRebornConfig.machineConfig.recycler.maxInput, ioComponent.getBatterySlot());
		this.recipeComponent = new RandomRecipeComponent(ioComponent.getInputs(), ioComponent.getInternalOutputs(), energyComponent.getEnergyStorage(),
				this::updateState, recipeOutput, TechRebornConfig.machineConfig.recycler.scrapChance); //TODO
		this.upgradeComponent = new UpgradeComponent(TechRebornConfig.machineConfig.recycler.maxEnergy, energyComponent, recipeComponent);
	}

	protected void refreshSlot(int slot) {
		recipeComponent.informRefreshRecipe();
	}

	private void updateState(boolean state) {
		IBlockState currentState = world.getBlockState(pos);
		world.setBlockState(pos, currentState.withProperty(BlockHorizontalMachine.ACTIVE, state), 3);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| capability == CapabilityEnergy.ENERGY
				|| super.hasCapability(capability, facing);
	}

	@Override
	public @Nullable <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == null) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getInventory());
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(ioComponent.getManager().getSide(facing));
		} else if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(energyComponent.getEnergyStorage());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		ioComponent.readFromNBT(compound);
		energyComponent.readFromNBT(compound);
		upgradeComponent.readFromNBT(compound);
		recipeComponent.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ioComponent.writeToNBT(compound);
		energyComponent.writeToNBT(compound);
		upgradeComponent.writeToNBT(compound);
		recipeComponent.writeToNBT(compound);
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
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.getNbtCompound());
		recipeComponent.onUpdatePacket(world, pos, pkt.getNbtCompound());
	}

	// ITickable
	@Override
	public void update() {
		if (world.isRemote)
			return;
		boolean updated = ioComponent.update() | energyComponent.update() | upgradeComponent.update() | recipeComponent.update();

		if (updated)
			markDirty();
	}

	@Override
	public void rotateSlot(int sideIndex, int handlerIndex, int localSlotIndex) {
		if (ioComponent.getManager().getSide(sideIndex).rotateSlot(localSlotIndex)) {
			updateTE();
		}
	}

	@Override
	public void swapSlot(int index, boolean input) {
		ioComponent.getManager().swapIndex(index, input);
		updateTE();
	}

	@Override
	public void updateTE() {
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}

	@Override
	public IOManager getManager() {
		return ioComponent.getManager(); //TODO
	}

	public void onInputChange(ItemStack newItem, boolean onlyAmountChanged, boolean client, boolean init) {
		if (client || init) return;
		if (onlyAmountChanged) {
			refreshSlot(0);
			return;
		}
		refreshSlot(0);
		markDirty();
	}

	// IGuiHolder
	@Override
	public ModularPanel buildUI(PosGuiData data, PanelSyncManager syncManager, UISettings settings) {
		String lang = "tile.techreborn.recycler.name";
		ModularPanel panel = ModularPanel.defaultPanel(IKey.lang(lang).get());
		syncManager.registerSlotGroup(new SlotGroup("inputs", 2, 100, true))
				.registerSlotGroup(new SlotGroup("upgrades", 4, 50, true))
				.registerSlotGroup(new SlotGroup("power", 1, 50, true));

		Supplier<EnumFacing> getFacing = () -> getWorld().getBlockState(getPos()).getValue(BlockHorizontal.FACING);
		TRGuis.setupConfigPanel(panel, syncManager, getPos(), ioComponent.getManager(), getFacing,
				new SlotPosition(SlotAction.INPUT, 55, 39, 0, 0),
				SlotPosition.BigSlot(SlotAction.OUTPUT, 101, 39, 1, 0),
				new SlotPosition(SlotAction.BIDIRECTIONAL, 7, 59, 2, 0)
		);

		panel.bindPlayerInventory()
				.child(IKey.lang(lang)
						.asWidget()
						.align(Alignment.TopCenter)
						.top(7))
				.child(new ProgressWidget()
						.size(20)
						.pos(75, 39)
						.texture(GuiTextures.PROGRESS_ARROW, 20)
						.value(recipeComponent.getProgress()))
				.child(TRGuis.createJeiButton(TRRecipePlugin.RECYCLER_UID)
						.pos(75, 41).size(20, 15))
				.child(PowerDisplayWidget.fromEnergyStorage(syncManager, energyComponent.getEnergyStorage())
						.pos(9, 6));

		panel.child(new ItemSlot().pos(55, 39)
						.slot(new ModularSlot(ioComponent.getInputs(), 0)
								.slotGroup("inputs")
								.changeListener(this::onInputChange)
								.filter(InventoryUtils.IS_UPGRADE.negate())))
				.child(new LargeItemSlot().pos(101, 39)
						.slot(new ModularSlot(ioComponent.getOutputs(), 0)
								.accessibility(false, true))
						.size(24))
				.child(new ItemSlot().pos(7, 59)
						.slot(new ModularSlot(ioComponent.getBatterySlot(), 0)
								.slotGroup("power")
								.filter(InventoryUtils.IS_POWER_ITEM)));

		panel.child(TRGuis.createUpdateTab(upgradeComponent.getUpgrades(), "upgrades", upgradeComponent::onUpgradeChange).leftRelOffset(1f, 1));
		return panel;
	}
}
