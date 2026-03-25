package com.sabrepenguin.techreborn.blocks.fluids;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = Tags.MODID)
@GameRegistry.ObjectHolder(Tags.MODID)
public class TRFluidBlocks {
	// TODO: Write fixer to translate from techreborn:techreborn_techreborn.<fluid>
	public static final BlockFluidClassic BERYLLIUM = null;
	public static final BlockFluidClassic CALCIUM = null;
	public static final BlockFluidClassic CALCIUM_CARBONATE = null;
	public static final BlockFluidClassic CHLORITE = null;
	public static final BlockFluidClassic DEUTERIUM = null;
	public static final BlockFluidClassic GLYCERYL = null;
	public static final BlockFluidClassic HELIUM = null;
	public static final BlockFluidClassic HELIUM3 = null;
	public static final BlockFluidClassic HELIUM_PLASMA = null;
	public static final BlockFluidClassic HYDROGEN = null;
	public static final BlockFluidClassic LITHIUM = null;
	public static final BlockFluidClassic MERCURY = null;
	public static final BlockFluidClassic METHANE = null;
	public static final BlockFluidClassic NITROCOAL_FUEL = null;
	public static final BlockFluidClassic NITROFUEL = null;
	public static final BlockFluidClassic NITROGEN = null;
	public static final BlockFluidClassic NITROGEN_DIOXIDE = null;
	public static final BlockFluidClassic POTASSIUM = null;
	public static final BlockFluidClassic SILICON = null;
	public static final BlockFluidClassic SODIUM = null;
	public static final BlockFluidClassic SODIUM_PERSULFATE = null;
	public static final BlockFluidClassic TRITIUM = null;
	public static final BlockFluidClassic WOLFRAMIUM = null;
	public static final BlockFluidClassic CARBON = null;
	public static final BlockFluidClassic CARBON_FIBER = null;
	public static final BlockFluidClassic NITROCARBON = null;
	public static final BlockFluidClassic SULFUR = null;
	public static final BlockFluidClassic SODIUM_SULFIDE = null;
	public static final BlockFluidClassic DIESEL = null;
	public static final BlockFluidClassic NITRO_DIESEL = null;
	public static final BlockFluidClassic OIL = null;
	public static final BlockFluidClassic SULFURIC_ACID = null;
	public static final BlockFluidClassic COMPRESSED_AIR = null;
	public static final BlockFluidClassic ELECTROLYZED_WATER = null;
	public static final BlockFluidClassic BIOFUEL = null;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		final BlockFluidBase[] fluids = {
				FluidInitUtils.registerClassicFluid(TRFluids.BERYLLIUM, "beryllium"),
				FluidInitUtils.registerClassicFluid(TRFluids.CALCIUM, "calcium"),
				FluidInitUtils.registerClassicFluid(TRFluids.CALCIUM_CARBONATE, "calcium_carbonate"),
				FluidInitUtils.registerClassicFluid(TRFluids.CHLORITE, "chlorite"),
				FluidInitUtils.registerClassicFluid(TRFluids.DEUTERIUM, "deuterium"),
				FluidInitUtils.registerClassicFluid(TRFluids.GLYCERYL, "glyceryl"),
				FluidInitUtils.registerClassicFluid(TRFluids.HELIUM, "helium"),
				FluidInitUtils.registerClassicFluid(TRFluids.HELIUM3, "helium3"),
				FluidInitUtils.registerClassicFluid(TRFluids.HELIUM_PLASMA, "helium_plasma"),
				FluidInitUtils.registerClassicFluid(TRFluids.HYDROGEN, "hydrogen"),
				FluidInitUtils.registerClassicFluid(TRFluids.LITHIUM, "lithium"),
				FluidInitUtils.registerClassicFluid(TRFluids.MERCURY, "mercury"),
				FluidInitUtils.registerClassicFluid(TRFluids.METHANE, "methane"),
				FluidInitUtils.registerClassicFluid(TRFluids.NITROCOAL_FUEL, "nitrocoal_fuel"),
				FluidInitUtils.registerClassicFluid(TRFluids.NITROFUEL, "nitrofuel"),
				FluidInitUtils.registerClassicFluid(TRFluids.NITROGEN, "nitrogen"),
				FluidInitUtils.registerClassicFluid(TRFluids.NITROGEN_DIOXIDE, "nitrogen_dioxide"),
				FluidInitUtils.registerClassicFluid(TRFluids.POTASSIUM, "potassium"),
				FluidInitUtils.registerClassicFluid(TRFluids.SILICON, "silicon"),
				FluidInitUtils.registerClassicFluid(TRFluids.SODIUM, "sodium"),
				FluidInitUtils.registerClassicFluid(TRFluids.SODIUM_PERSULFATE, "sodium_persulfate"),
				FluidInitUtils.registerClassicFluid(TRFluids.TRITIUM, "tritium"),
				FluidInitUtils.registerClassicFluid(TRFluids.WOLFRAMIUM, "wolframium"),
				FluidInitUtils.registerClassicFluid(TRFluids.CARBON, "carbon"),
				FluidInitUtils.registerClassicFluid(TRFluids.CARBON_FIBER, "carbon_fiber"),
				FluidInitUtils.registerClassicFluid(TRFluids.NITROCARBON, "nitrocarbon"),
				FluidInitUtils.registerClassicFluid(TRFluids.SULFUR, "sulfur"),
				FluidInitUtils.registerClassicFluid(TRFluids.SODIUM_SULFIDE, "sodium_sulfide"),
				FluidInitUtils.registerClassicFluid(TRFluids.DIESEL, "diesel"),
				FluidInitUtils.registerClassicFluid(TRFluids.NITRO_DIESEL, "nitro_diesel"),
				FluidInitUtils.registerClassicFluid(TRFluids.OIL, "oil"),
				FluidInitUtils.registerClassicFluid(TRFluids.SULFURIC_ACID, "sulfuric_acid"),
				FluidInitUtils.registerClassicFluid(TRFluids.COMPRESSED_AIR, "compressed_air"),
				FluidInitUtils.registerClassicFluid(TRFluids.ELECTROLYZED_WATER, "electrolyzed_water"),
				FluidInitUtils.registerClassicFluid(TRFluids.BIOFUEL, "biofuel"),
		};
		for (BlockFluidBase fluid: fluids) {
			event.getRegistry().register(fluid);
		}
		TRFluids.registerFluids();
	}
}
