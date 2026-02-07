package com.sabrepenguin.techreborn.blocks.machines.energy;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockCreativeSolarPanel extends Block implements INonStandardLocation {
	public BlockCreativeSolarPanel() {
		super(Material.IRON);
		this.setHardness(2.0f);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "creative_solar_panel");
		this.setTranslationKey(Tags.MODID + ".creative_solar_panel");
	}

	@Override
	public String getPrefix() {
		return "machines/generators";
	}
}
