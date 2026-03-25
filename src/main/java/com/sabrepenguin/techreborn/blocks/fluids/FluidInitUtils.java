package com.sabrepenguin.techreborn.blocks.fluids;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.*;

public class FluidInitUtils {

	public static Fluid createFluid(String name, String resourceLocation) {
		ResourceLocation location = new ResourceLocation(Tags.MODID, "blocks/fluids/" + resourceLocation + "_flowing");
		Fluid fluid = new Fluid(name, location, location);
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		return fluid;
	}

	public static BlockFluidBase registerClassicFluid(Fluid fluid, String fluidName) {
		BlockFluidBase block = new BlockFluidClassic(fluid, Material.WATER);
		block.setRegistryName(Tags.MODID, fluidName);
		return block;
	}
}
