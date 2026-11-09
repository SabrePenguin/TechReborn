package com.sabrepenguin.techreborn.tileentity.multis;

import com.sabrepenguin.techreborn.blocks.machines.BlockHorizontalMachine;
import com.sabrepenguin.techreborn.blocks.machines.HorizontalMachineBlock;
import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.capability.stackhandler.SideConfigStackHandler;
import com.sabrepenguin.techreborn.recipe.registries.ITRFluidRegistry;
import com.sabrepenguin.techreborn.tileentity.IOManager;
import com.sabrepenguin.techreborn.tileentity.components.EnergyComponent;
import com.sabrepenguin.techreborn.tileentity.components.FluidIOComponent;
import com.sabrepenguin.techreborn.tileentity.components.FluidRecipeComponent;
import com.sabrepenguin.techreborn.tileentity.components.ItemIOComponent;
import com.sabrepenguin.techreborn.tileentity.components.MultiblockCheckerComponent;
import com.sabrepenguin.techreborn.tileentity.components.SidedConfigComponent;
import com.sabrepenguin.techreborn.tileentity.components.UpgradeComponent;
import mcp.MethodsReturnNonnullByDefault;
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

//TODO: See about merging with GenericMulti somehow
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEntityGenericFluidMulti extends TileEntity implements ITickable, ISideConfigTE {

	protected final ItemIOComponent ioComponent;
	protected final FluidIOComponent fluidIoComponent;
	protected final EnergyComponent energyComponent;
	protected final UpgradeComponent upgradeComponent;
	protected final FluidRecipeComponent recipeComponent;
	protected final SidedConfigComponent configComponent;
	protected final SidedConfigComponent<IOManager, SideConfigStackHandler> configComponent;
	protected final MultiblockCheckerComponent multiblockComponent;

	public TileEntityGenericFluidMulti(int inputSize, int outputSize, int inputTanks, int inputCapacity, int outputTanks,
                                       int outputCapacity, int feCapacity, int maxInput, ITRFluidRegistry registry, MultiblockStructure[] structure, boolean async) {
		ioComponent = new ItemIOComponent.ItemIOComponentBuilder(this)
				.inputSize(inputSize)
				.outputSize(outputSize)
				.hasEnergySlot()
				.onSlotChangedAction(this::refreshSlot)
				.fluidTanksSize(inputTanks + outputTanks)
				.build();
		fluidIoComponent = new FluidIOComponent.FluidIOComponentBuilder(this)
				.input(inputTanks, inputCapacity)
				.output(outputTanks, outputCapacity)
				.handlers(ioComponent.getFluidItemInputs(), ioComponent.getFluidItemOutputs())
				.build();
		energyComponent = new EnergyComponent(feCapacity, maxInput, maxInput, ioComponent.getBatterySlot()).setUnextractable();
		recipeComponent = new FluidRecipeComponent(registry, ioComponent.getInputs(), ioComponent.getInternalOutputs(),
				fluidIoComponent.getInput(), fluidIoComponent.getOutput(),
				energyComponent.getEnergyStorage(), this::updateState);
		upgradeComponent = new UpgradeComponent(feCapacity, energyComponent, recipeComponent);
		configComponent = new SidedConfigComponent(ioComponent, this::updateTE);
		configComponent = new SidedConfigComponent<>(ioComponent.getManager(), this::updateTE);
		multiblockComponent = new MultiblockCheckerComponent(this, structure, async);
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
		if (oldState.getBlock() == newState.getBlock()) {
			if (oldState.getValue(BlockHorizontalMachine.FACING) != newState.getValue(BlockHorizontalMachine.FACING)) {
				multiblockComponent.setStructureRotation(newState.getValue(BlockHorizontalMachine.FACING));
			}
		}
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
		NBTTagCompound fluidCompound = compound.getCompoundTag("fluid");
		fluidIoComponent.readFromNBT(fluidCompound);
		energyComponent.readFromNBT(compound);
		upgradeComponent.readFromNBT(compound);
		recipeComponent.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ioComponent.writeToNBT(compound);
		NBTTagCompound fluidCompound = new NBTTagCompound();
		fluidIoComponent.writeToNBT(fluidCompound);
		compound.setTag("fluid", fluidCompound);
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

	@Override
	public void update() {
		if (world.isRemote) return;

		multiblockComponent.validate();

		boolean updated;

		if (multiblockComponent.isValid()) {
			updated = ioComponent.update()
					| energyComponent.update() | upgradeComponent.update() | recipeComponent.update() | fluidIoComponent.update();
		} else {
			updated = false;
		}

		if (updated) {
			this.markDirty();
		}
	}

	@Override
	public SidedConfigComponent<IOManager, SideConfigStackHandler> getSidedConfigComponent() {
		return configComponent;
	}

	@Override
	public void updateTE() {
		markDirty();
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}

	@Override
	public IOManager getManager() {
		return ioComponent.getManager();
	}

	@Override
	public void onLoad() {
		multiblockComponent.setStructureRotation(world.getBlockState(pos).getValue(HorizontalMachineBlock.FACING));
		super.onLoad();
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
}
