package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemCell extends Item {

	private final ItemStack EMPTY_CELL = new ItemStack(this);

	public ItemCell() {
		setRegistryName(Tags.MODID, "cell");
		setTranslationKey(Tags.MODID + ".cell");
		setCreativeTab(TechReborn.RESOURCE_TAB);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab != TechReborn.RESOURCE_TAB) return;
		items.add(EMPTY_CELL);
		FluidRegistry.getRegisteredFluids().forEach((name, fluid) -> {
			ItemStack ret = new ItemStack(this);
			IFluidHandler handler = new FluidHandlerItemStack(ret, 1000);
			handler.fill(new FluidStack(fluid, 1000), true);
			items.add(ret);
		});
	}

	public ItemStack getCellWithFluid(Fluid fluid) {
		ItemStack ret = new ItemStack(this);
		IFluidHandler handler = new FluidHandlerItemStack(ret, 1000);
		handler.fill(new FluidStack(fluid, 1000), true);
		return ret;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		FluidStack fluidStack = FluidUtil.getFluidContained(stack);
		if (fluidStack == null) {
			return super.getItemStackDisplayName(stack);
		}
		String fluidName = fluidStack.getLocalizedName();
		return new TextComponentTranslation("item.techreborn.cell.fluid.name").getFormattedText().replaceAll("\\$fluid\\$", fluidName);
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new FluidHandlerItemStack(stack, Fluid.BUCKET_VOLUME);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack held = playerIn.getHeldItem(handIn);
		boolean useLiquids = FluidUtil.getFluidContained(held) == null;
		RayTraceResult target = rayTrace(worldIn, playerIn, useLiquids);
		if (target == null || target.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult<>(EnumActionResult.PASS, held);
		}

		ItemStack resultContainer = held.copy();
		resultContainer.setCount(1);
		if (useLiquids) {
			FluidActionResult pickUpFluid = FluidUtil.tryPickUpFluid(resultContainer, playerIn, worldIn, target.getBlockPos(), target.sideHit);
			if (pickUpFluid.isSuccess()) {
				ItemHandlerHelper.giveItemToPlayer(playerIn, pickUpFluid.result);
				if (!playerIn.capabilities.isCreativeMode) {
					held.shrink(1);
				}
				return new ActionResult<>(EnumActionResult.SUCCESS, held);
			}
		} else {
			FluidActionResult placeFluid = FluidUtil.tryPlaceFluid(playerIn, worldIn, target.getBlockPos().offset(target.sideHit),
					resultContainer, FluidUtil.getFluidContained(resultContainer));
			if (placeFluid.isSuccess()) {
				ItemStack result = placeFluid.result;
				if (result.hasTagCompound() && result.getTagCompound().isEmpty()) {
					result.setTagCompound(null);
				}
				ItemHandlerHelper.giveItemToPlayer(playerIn, result);
				if (!playerIn.capabilities.isCreativeMode) {
					held.shrink(1);
				}
				return new ActionResult<>(EnumActionResult.SUCCESS, held);
			}
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@SuppressWarnings("ConstantConditions")
	public static boolean isItemEqual(@Nullable ItemStack stack1, @Nullable ItemStack stack2) {
		if (stack1 == null || stack2 == null || stack1.isEmpty() || stack2.isEmpty())
			return false;
		if (!stack1.hasTagCompound() || !stack2.hasTagCompound())
			return false;
		return stack1.getTagCompound().equals(stack2.getTagCompound());
	}
}
