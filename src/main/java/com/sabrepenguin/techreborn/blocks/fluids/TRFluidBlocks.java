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
	public static final BlockFluidClassic CALCIUMCARBONATE = null;
	public static final BlockFluidClassic CHLORITE = null;
	public static final BlockFluidClassic DEUTERIUM = null;
	public static final BlockFluidClassic GLYCERYL = null;
	public static final BlockFluidClassic HELIUM = null;
	public static final BlockFluidClassic HELIUM3 = null;
	public static final BlockFluidClassic HELIUMPLASMA = null;
	public static final BlockFluidClassic HYDROGEN = null;
	public static final BlockFluidClassic LITHIUM = null;
	public static final BlockFluidClassic MERCURY = null;
	public static final BlockFluidClassic METHANE = null;
	public static final BlockFluidClassic NITROCOAL_FUEL = null;
	public static final BlockFluidClassic NITROFUEL = null;
	public static final BlockFluidClassic NITROGEN = null;
	public static final BlockFluidClassic NITROGENDIOXIDE = null;
	public static final BlockFluidClassic POTASSIUM = null;
	public static final BlockFluidClassic SILICON = null;
	public static final BlockFluidClassic SODIUM = null;
	public static final BlockFluidClassic SODIUMPERSULFATE = null;
	public static final BlockFluidClassic TRITIUM = null;
	public static final BlockFluidClassic WOLFRAMIUM = null;
	public static final BlockFluidClassic CARBON = null;
	public static final BlockFluidClassic CARBON_FIBER = null;
	public static final BlockFluidClassic NITRO_CARBON = null;
	public static final BlockFluidClassic SULFUR = null;
	public static final BlockFluidClassic SODIUM_SULFATE = null;
	public static final BlockFluidClassic DIESEL = null;
	public static final BlockFluidClassic NITRO_DIESEL = null;
	public static final BlockFluidClassic OIL = null;
	public static final BlockFluidClassic SULFURIC_ACID = null;
	public static final BlockFluidClassic COMPRESSED_AIR = null;
	public static final BlockFluidClassic ELECTROLYZED_WATER = null;
	public static final BlockFluidClassic BIO_FUEL = null;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		final BlockFluidBase[] fluids = {
				FluidInitUtils.registerClassicFluid(TRFluids.BERYLLIUM, "beryllium"),
				FluidInitUtils.registerClassicFluid(TRFluids.CALCIUM, "calcium"),
				FluidInitUtils.registerClassicFluid(TRFluids.CALCIUM_CARBONATE, "calciumcarbonate"),
				FluidInitUtils.registerClassicFluid(TRFluids.CHLORITE, "chlorite"),
				FluidInitUtils.registerClassicFluid(TRFluids.DEUTERIUM, "deuterium"),
				FluidInitUtils.registerClassicFluid(TRFluids.GLYCERYL, "glyceryl"),
				FluidInitUtils.registerClassicFluid(TRFluids.HELIUM, "helium"),
				FluidInitUtils.registerClassicFluid(TRFluids.HELIUM3, "helium3"),
				FluidInitUtils.registerClassicFluid(TRFluids.HELIUM_PLASMA, "heliumplasma"),
		};
		for (BlockFluidBase fluid: fluids) {
			event.getRegistry().register(fluid);
		}
		TRFluids.registerFluids();
	}
}
