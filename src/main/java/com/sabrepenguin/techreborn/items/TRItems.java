package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.IBurnable;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.itemblock.IMetaMaterial;
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

    public static final Item ingot = null;
    public static final Item dust = null;
    public static final Item smalldust = null;
    public static final Item nuggets = null;
    public static final Item gem = null;
    public static final Item part = null;
    public static final Item plates = null;
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

        return items;
    }

    public static List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        Collections.addAll(allItems,
                TRItems.dust,
                TRItems.gem,
                TRItems.ingot,
                TRItems.nuggets,
                TRItems.part,
                TRItems.plates,
                TRItems.smalldust,
                TRItems.uumatter,

                TRItems.bronzeaxe,
                TRItems.bronzehoe,
                TRItems.bronzepickaxe,
                TRItems.bronzespade,
                TRItems.bronzesword,
                TRItems.bronzehelmet,
				TRItems.bronzechestplate,
				TRItems.bronzeleggings,
				TRItems.bronzeboots,

                TRItems.rubyaxe,
                TRItems.rubyhoe,
                TRItems.rubypickaxe,
                TRItems.rubyspade,
                TRItems.rubysword,
				TRItems.rubyhelmet,
				TRItems.rubychestplate,
				TRItems.rubyleggings,
				TRItems.rubyboots,

                TRItems.peridotaxe,
                TRItems.peridothoe,
                TRItems.peridotpickaxe,
                TRItems.peridotspade,
                TRItems.peridotsword,
				TRItems.peridothelmet,
				TRItems.peridotchestplate,
				TRItems.peridotleggings,
				TRItems.peridotboots,

                TRItems.sapphireaxe,
                TRItems.sapphirehoe,
                TRItems.sapphirepickaxe,
                TRItems.sapphirespade,
                TRItems.sapphiresword,
				TRItems.sapphirehelmet,
				TRItems.sapphirechestplate,
				TRItems.sapphireleggings,
				TRItems.sapphireboots,

				TRItems.cloakingdevice,
                TRItems.wrench,
				TRItems.treetap
        );
        return allItems;
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
        for (Block block: TRBlocks.getAllBlocks()) {
			if (block instanceof IMetaMaterial meta) {
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
			} else if (block instanceof IBurnable burnable) {
				registry.register(new ItemBlockBurnable(block, burnable).setRegistryName(block.getRegistryName()));
			}
        }
		{
			ItemSlab slab = new ItemSlab(TRBlocks.rubber_plank_slab, TRBlocks.rubber_plank_slab, TRBlocks.rubber_plank_double_slab);
			registry.register(slab.setRegistryName(TRBlocks.rubber_plank_double_slab.getRegistryName()));
			registry.register(new ItemBlock(TRBlocks.rubber_log).setRegistryName(TRBlocks.rubber_log.getRegistryName()));
			registry.register(new ItemBlock(TRBlocks.rubber_planks).setRegistryName(TRBlocks.rubber_planks.getRegistryName()));
			registry.register(new ItemBlock(TRBlocks.rubber_plank_stair).setRegistryName(TRBlocks.rubber_plank_stair.getRegistryName()));
		}
		{
			registry.register(new ItemBlock(TRBlocks.iron_furnace).setRegistryName(TRBlocks.iron_furnace.getRegistryName()));
			registry.register(new ItemBlock(TRBlocks.iron_alloy_furnace).setRegistryName(TRBlocks.iron_alloy_furnace.getRegistryName()));
		}
		OreHandler.initOres();
    }
}
