package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockFusionCoil extends Block implements INonStandardLocation {
	public BlockFusionCoil() {
		super(Material.IRON);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "fusion_coil");
		this.setTranslationKey(Tags.MODID + ".fusion_coil");
		this.setHardness(2.0f);
		this.setSoundType(SoundType.METAL);
	}

	@Override
	public String getPrefix() {
		return "machines/generators/";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add("Right click Fusion Control computer to auto place");
	}
}
