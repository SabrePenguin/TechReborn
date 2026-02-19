package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.IBurnable;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import com.sabrepenguin.techreborn.itemblock.ItemBlockBurnable;
import com.sabrepenguin.techreborn.itemblock.ItemBlockEnum;
import com.sabrepenguin.techreborn.items.armor.ItemCloak;
import com.sabrepenguin.techreborn.items.armor.TRArmor;
import com.sabrepenguin.techreborn.items.materials.*;
import com.sabrepenguin.techreborn.items.tools.*;
import com.sabrepenguin.techreborn.util.ExtraStringUtils;
import com.sabrepenguin.techreborn.util.handlers.OreHandler;
import net.minecraft.block.Block;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = Tags.MODID)
@GameRegistry.ObjectHolder(Tags.MODID)
public class TRItems {

    public static final MaterialItem ingot = null;
    public static final MaterialItem dust = null;
    public static final MaterialItem smalldust = null;
    public static final MaterialItem nuggets = null;
    public static final MaterialItem gem = null;
    public static final MaterialItem part = null;
    public static final MaterialItem plates = null;
    public static final Item uumatter = null;

    public static final TRAxe bronzeaxe = null;
    public static final TRHoe bronzehoe = null;
    public static final TRPickaxe bronzepickaxe = null;
    public static final TRSpade bronzespade = null;
    public static final TRSword bronzesword = null;
    public static final TRArmor bronzehelmet = null;
    public static final TRArmor bronzechestplate = null;
    public static final TRArmor bronzeleggings = null;
    public static final TRArmor bronzeboots = null;

    public static final TRAxe rubyaxe = null;
    public static final TRHoe rubyhoe = null;
    public static final TRPickaxe rubypickaxe = null;
    public static final TRSpade rubyspade = null;
    public static final TRSword rubysword = null;
	public static final TRArmor rubyhelmet = null;
	public static final TRArmor rubychestplate = null;
	public static final TRArmor rubyleggings = null;
	public static final TRArmor rubyboots = null;

    public static final TRAxe sapphireaxe = null;
    public static final TRHoe sapphirehoe = null;
    public static final TRPickaxe sapphirepickaxe = null;
    public static final TRSpade sapphirespade = null;
    public static final TRSword sapphiresword = null;
	public static final TRArmor sapphirehelmet = null;
	public static final TRArmor sapphirechestplate = null;
	public static final TRArmor sapphireleggings = null;
	public static final TRArmor sapphireboots = null;

    public static final TRAxe peridotaxe = null;
    public static final TRHoe peridothoe = null;
    public static final TRPickaxe peridotpickaxe = null;
    public static final TRSpade peridotspade = null;
    public static final TRSword peridotsword = null;
	public static final TRArmor peridothelmet = null;
	public static final TRArmor peridotchestplate = null;
	public static final TRArmor peridotleggings = null;
	public static final TRArmor peridotboots = null;

	public static final ItemCloak cloakingdevice = null;
    public static final ItemWrench wrench = null;
	public static final TreeTap treetap = null;
	public static final ItemUpgrade upgrades = null;

    public static List<Item> getItems() {
        final List<Item> items = new ArrayList<>(
            Arrays.asList(
                new MaterialItem("dust", "dust", new Dust(), "items/materials/", ""),
				new MaterialItem("gem", "gem", new Gem(), "items/materials/", ""),
                new MaterialItem("ingot", "ingot", new Ingot(), "items/materials/", ""),
                new MaterialItem("nuggets", "nuggets", new Nugget(), "items/materials/", ""),
                new MaterialItem("part", "part", new Part(), "items/materials/", ""),
                new MaterialItem("plates", "plate", new Plate(), "items/materials/", ""),
				new MaterialItem("smalldust", "dustsmall", new DustSmall(), "items/materials/", ""),
                new TechRebornItem("uumatter", "part.uuMatter", "misc/")
            )
        );
        items.add(new TRAxe(MaterialsTR.BRONZE_TOOL, "ingotBronze"));
        items.add(new TRHoe(MaterialsTR.BRONZE_TOOL, "ingotBronze"));
        items.add(new TRPickaxe(MaterialsTR.BRONZE_TOOL, "ingotBronze"));
        items.add(new TRSpade(MaterialsTR.BRONZE_TOOL, "ingotBronze"));
        items.add(new TRSword(MaterialsTR.BRONZE_TOOL, "ingotBronze"));
        items.add(new TRArmor("bronze", MaterialsTR.BRONZE_ARMOR, EntityEquipmentSlot.HEAD, "ingotBronze"));
        items.add(new TRArmor("bronze", MaterialsTR.BRONZE_ARMOR, EntityEquipmentSlot.CHEST, "ingotBronze"));
        items.add(new TRArmor("bronze", MaterialsTR.BRONZE_ARMOR, EntityEquipmentSlot.LEGS, "ingotBronze"));
        items.add(new TRArmor("bronze", MaterialsTR.BRONZE_ARMOR, EntityEquipmentSlot.FEET, "ingotBronze"));

        items.add(new TRAxe(MaterialsTR.PERIDOT_TOOL, "gemPeridot"));
        items.add(new TRHoe(MaterialsTR.PERIDOT_TOOL, "gemPeridot"));
        items.add(new TRPickaxe(MaterialsTR.PERIDOT_TOOL, "gemPeridot"));
        items.add(new TRSpade(MaterialsTR.PERIDOT_TOOL, "gemPeridot"));
        items.add(new TRSword(MaterialsTR.PERIDOT_TOOL, "gemPeridot"));
		items.add(new TRArmor("peridot", MaterialsTR.PERIDOT_ARMOR, EntityEquipmentSlot.HEAD, "gemPeridot"));
		items.add(new TRArmor("peridot", MaterialsTR.PERIDOT_ARMOR, EntityEquipmentSlot.CHEST, "gemPeridot"));
		items.add(new TRArmor("peridot", MaterialsTR.PERIDOT_ARMOR, EntityEquipmentSlot.LEGS, "gemPeridot"));
		items.add(new TRArmor("peridot", MaterialsTR.PERIDOT_ARMOR, EntityEquipmentSlot.FEET, "gemPeridot"));

        items.add(new TRAxe(MaterialsTR.RUBY_TOOL, "gemRuby"));
        items.add(new TRHoe(MaterialsTR.RUBY_TOOL, "gemRuby"));
        items.add(new TRPickaxe(MaterialsTR.RUBY_TOOL, "gemRuby"));
        items.add(new TRSpade(MaterialsTR.RUBY_TOOL, "gemRuby"));
        items.add(new TRSword(MaterialsTR.RUBY_TOOL, "gemRuby"));
		items.add(new TRArmor("ruby", MaterialsTR.RUBY_ARMOR, EntityEquipmentSlot.HEAD, "gemRuby"));
		items.add(new TRArmor("ruby", MaterialsTR.RUBY_ARMOR, EntityEquipmentSlot.CHEST, "gemRuby"));
		items.add(new TRArmor("ruby", MaterialsTR.RUBY_ARMOR, EntityEquipmentSlot.LEGS, "gemRuby"));
		items.add(new TRArmor("ruby", MaterialsTR.RUBY_ARMOR, EntityEquipmentSlot.FEET, "gemRuby"));

        items.add(new TRAxe(MaterialsTR.SAPPHIRE_TOOL, "gemSapphire"));
        items.add(new TRHoe(MaterialsTR.SAPPHIRE_TOOL, "gemSapphire"));
        items.add(new TRPickaxe(MaterialsTR.SAPPHIRE_TOOL, "gemSapphire"));
        items.add(new TRSpade(MaterialsTR.SAPPHIRE_TOOL, "gemSapphire"));
        items.add(new TRSword(MaterialsTR.SAPPHIRE_TOOL, "gemSapphire"));
		items.add(new TRArmor("sapphire", MaterialsTR.SAPPHIRE_ARMOR, EntityEquipmentSlot.HEAD, "gemSapphire"));
		items.add(new TRArmor("sapphire", MaterialsTR.SAPPHIRE_ARMOR, EntityEquipmentSlot.CHEST, "gemSapphire"));
		items.add(new TRArmor("sapphire", MaterialsTR.SAPPHIRE_ARMOR, EntityEquipmentSlot.LEGS, "gemSapphire"));
		items.add(new TRArmor("sapphire", MaterialsTR.SAPPHIRE_ARMOR, EntityEquipmentSlot.FEET, "gemSapphire"));

		items.add(new ItemCloak(MaterialsTR.CLOAKING_ARMOR));
        items.add(new ItemWrench());
		items.add(new TreeTap());

		items.add(new ItemUpgrade());

        return items;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        for (Item item: TRItems.getItems()) {
            registry.register(item);
            if (item instanceof MaterialItem materialItem) {
                materialItem.registerOredict();
            }
        }
		registerNormalItems(registry);
		OreHandler.initOres();
    }

	@SuppressWarnings("ConstantConditions")
	private static void registerNormalItems(IForgeRegistry<Item> registry) {
		registerMetaBlock(registry, TRBlocks.storage);
		registerMetaBlock(registry, TRBlocks.storage2);
		registerMetaBlock(registry, TRBlocks.ore);
		registerMetaBlock(registry, TRBlocks.ore2);
		registerMetaBlock(registry, TRBlocks.machine_frame);
		registerMetaBlock(registry, TRBlocks.machine_casing);
		{
			ItemSlab slab = new ItemSlab(TRBlocks.rubber_plank_slab, TRBlocks.rubber_plank_slab, TRBlocks.rubber_plank_double_slab);
			registry.register(slab.setRegistryName(TRBlocks.rubber_plank_double_slab.getRegistryName()));
		}
		registerBurnable(registry, TRBlocks.rubber_leaves);
		registerBurnable(registry, TRBlocks.rubber_sapling);
		register(registry, TRBlocks.rubber_log);
		register(registry, TRBlocks.rubber_planks);
		register(registry, TRBlocks.rubber_plank_stair);
		register(registry, TRBlocks.iron_furnace);
		register(registry, TRBlocks.iron_alloy_furnace);
		register(registry, TRBlocks.reinforced_glass);
		register(registry, TRBlocks.alarm);
		register(registry, TRBlocks.computercube);
		register(registry, TRBlocks.refined_iron_fence);
		register(registry, TRBlocks.fusion_coil);
		register(registry, TRBlocks.fusion_control_computer);
		register(registry, TRBlocks.nuke);
		register(registry, TRBlocks.lv_transformer);
		register(registry, TRBlocks.mv_transformer);
		register(registry, TRBlocks.hv_transformer);
		register(registry, TRBlocks.ev_transformer);
		register(registry, TRBlocks.quantum_chest);
		register(registry, TRBlocks.quantum_tank);
		register(registry, TRBlocks.creative_quantum_tank);
		register(registry, TRBlocks.creative_quantum_chest);
		register(registry, TRBlocks.chunk_loader);
		register(registry, TRBlocks.fluid_replicator);
		register(registry, TRBlocks.matter_fabricator);
		register(registry, TRBlocks.charge_o_mat);
		register(registry, TRBlocks.chemical_reactor);
		register(registry, TRBlocks.digital_chest);
		register(registry, TRBlocks.distillation_tower);
		register(registry, TRBlocks.implosion_compressor);
		register(registry, TRBlocks.industrial_blast_furnace);
		register(registry, TRBlocks.industrial_centrifuge);
		register(registry, TRBlocks.industrial_grinder);
		register(registry, TRBlocks.industrial_sawmill);
		register(registry, TRBlocks.vacuum_freezer);
		register(registry, TRBlocks.auto_crafting_table);
		register(registry, TRBlocks.electric_furnace);
		registerMetaBlock(registry, TRBlocks.player_detector);
		register(registry, TRBlocks.pump);
		register(registry, TRBlocks.rolling_machine);
		register(registry, TRBlocks.scrapboxinator);
		register(registry, TRBlocks.low_voltage_su);
		register(registry, TRBlocks.medium_voltage_su);
		register(registry, TRBlocks.high_voltage_su);
		register(registry, TRBlocks.adjustable_su); //TODO: ItemBlock adjustment
		register(registry, TRBlocks.interdimensional_su);
		register(registry, TRBlocks.lapotronic_su);
		register(registry, TRBlocks.lsu_storage);

		register(registry, TRBlocks.alloy_smelter);
		register(registry, TRBlocks.assembling_machine);
		register(registry, TRBlocks.compressor);
		register(registry, TRBlocks.extractor);
		register(registry, TRBlocks.grinder);
		register(registry, TRBlocks.plate_bending_machine);
		register(registry, TRBlocks.recycler);
		register(registry, TRBlocks.solid_canning_machine);
		register(registry, TRBlocks.wire_mill);
		register(registry, TRBlocks.lamp_incandescent);
		register(registry, TRBlocks.lamp_led);

		register(registry, TRBlocks.creative_solar_panel);
		register(registry, TRBlocks.diesel_generator);
		register(registry, TRBlocks.dragon_egg_syphon);
		register(registry, TRBlocks.gas_turbine);
		register(registry, TRBlocks.lightning_rod);
		register(registry, TRBlocks.magic_energy_absorber);
		register(registry, TRBlocks.magic_energy_converter);
		register(registry, TRBlocks.plasma_generator);
		register(registry, TRBlocks.semi_fluid_generator);
		register(registry, TRBlocks.solid_fuel_generator);
		register(registry, TRBlocks.thermal_generator);
		register(registry, TRBlocks.water_mill);
		register(registry, TRBlocks.wind_mill);
		registerMetaBlock(registry, TRBlocks.solar_panel);
//		registerMetaBlock(registry, TRBlocks.cable);
	}

	@SuppressWarnings("ConstantConditions")
	private static void register(IForgeRegistry<Item> registry, Block block) {
		registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SuppressWarnings("ConstantConditions")
	private static void registerBurnable(IForgeRegistry<Item> registry, Block block) {
		if (!(block instanceof IBurnable burnable))
			throw new RuntimeException("Block " + block.getRegistryName() +
					" was passed in to register as a burnable, but does not implement IBurnable");
		registry.register(new ItemBlockBurnable(block, burnable).setRegistryName(block.getRegistryName()));
	}

	@SuppressWarnings("ConstantConditions")
	private static void registerMetaBlock(IForgeRegistry<Item> registry, Block block) {
		if (!(block instanceof IMetaInformation meta))
			throw new RuntimeException("Block " + block.getRegistryName() +
					" was passed in to register with metadata, but does not implement IMetaInformation");
		Item itemBlock = new ItemBlockEnum(block).setRegistryName(block.getRegistryName());
		registry.register(itemBlock);
		for (Pair<String, Integer> metadata : meta.getMeta()) {
			String oredict = meta.getOreDict();
			if (meta.hasNonStandardOreDict()) {
				String[] ores = meta.getNonStandardOreDict(metadata.getRight());
				for (String ore : ores) {
					OreDictionary.registerOre(
							ore,
							new ItemStack(itemBlock, 1, metadata.getRight())
					);
				}
			} else if (!oredict.isEmpty()) {
				OreDictionary.registerOre(
						oredict + ExtraStringUtils.capitalizeByUnderscore(metadata.getLeft()),
						new ItemStack(itemBlock, 1, metadata.getRight()));
			}
		}
	}
}
