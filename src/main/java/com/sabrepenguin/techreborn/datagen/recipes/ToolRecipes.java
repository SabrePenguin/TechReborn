package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.datagen.builders.ShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.conditions.ConfigCondition;
import com.sabrepenguin.techreborn.items.ItemUpgrade;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.DustSmall;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;

public class ToolRecipes {
	public static void gear(File file) {
		tools(file);
		armor(file);
	}

	@SuppressWarnings("ConstantConditions")
	public static void tools(File file) {
		File tools = new File(file, "tools");
		new ShapedBuilder<>()
				.name("wrench")
				.pattern("BNB")
				.pattern("NBN")
				.pattern(" B ")
				.define('B', "ingotBronze")
				.define('N', "nuggetBronze")
				.withResult(new ItemStack(TRItems.wrench))
				.save(tools);
		new ShapedBuilder<>()
				.name("tree_tap")
				.pattern(" S ")
				.pattern("PPP")
				.pattern("P  ")
				.define('S', "stickWood")
				.define('P', "plankWood")
				.withResult(new ItemStack(TRItems.treetap))
				.save(tools);
		new ShapedBuilder<>()
				.name("electric_tree_tap")
				.pattern("TB")
				.define('T', new ItemStack(TRItems.treetap))
				.define('B', "reBattery")
				.withResult(new ItemStack(TRItems.electrictreetap))
				.save(tools);
		new ShapedBuilder<>()
				.name("nanosaber")
				.pattern("DC ")
				.pattern("DC ")
				.pattern("GLG")
				.define('D', "plateDiamond")
				.define('C', "plateCarbon")
				.define('G', new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.glowstone.metadata()))
				.define('L', "lapotronCrystal")
				.withResult(new ItemStack(TRItems.nanosaber))
				.save(tools);
		ItemStack rockCutter = new ItemStack(TRItems.rockcutter);
		rockCutter.addEnchantment(Enchantments.SILK_TOUCH, 1);
		new ShapedBuilder<>()
				.name("rock_cutter")
				.pattern("DT ")
				.pattern("DT ")
				.pattern("DCB")
				.define('D', "dustDiamond")
				.define('T', "ingotTitanium")
				.define('C', "circuitBasic")
				.define('B', "reBattery")
				.withResult(rockCutter)
				.save(tools);
		ItemStack overclock = new ItemStack(TRItems.upgrades, 1, ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata());
		new ShapedBuilder<>()
				.name("steel_drill")
				.pattern(" S ")
				.pattern("SCS")
				.pattern("SBS")
				.define('S', "ingotSteel")
				.define('C', "circuitBasic")
				.define('B', "reBattery")
				.withResult(new ItemStack(TRItems.steel_drill))
				.save(tools);
		new ShapedBuilder<>()
				.name("diamond_drill")
				.pattern(" D ")
				.pattern("DCD")
				.pattern("TST")
				.define('D', "gemDiamond")
				.define('C', "circuitAdvanced")
				.define('T', "ingotTitanium")
				.define('S', new ItemStack(TRItems.steel_drill, 1, OreDictionary.WILDCARD_VALUE))
				.withResult(new ItemStack(TRItems.diamonddrill))
				.save(tools);
		new ShapedBuilder<>()
				.name("advanced_drill")
				.pattern(" I ")
				.pattern("NCN")
				.pattern("OAO")
				.define('I', "plateIridiumAlloy")
				.define('N', "nuggetIridium")
				.define('C', "circuitMaster")
				.define('O', overclock)
				.define('A', new ItemStack(TRItems.diamonddrill, 1, OreDictionary.WILDCARD_VALUE))
				.withResult(new ItemStack(TRItems.advanceddrill))
				.save(tools);
		new ShapedBuilder<>()
				.name("steel_chainsaw")
				.pattern(" SS")
				.pattern("SCS")
				.pattern("BS ")
				.define('S', "ingotSteel")
				.define('C', "circuitBasic")
				.define('B', "reBattery")
				.withResult(new ItemStack(TRItems.steel_chainsaw))
				.save(tools);
		new ShapedBuilder<>()
				.name("diamond_chainsaw")
				.pattern(" DD")
				.pattern("TCD")
				.pattern("ST ")
				.define('D', "gemDiamond")
				.define('T', "ingotTitanium")
				.define('C', "circuitAdvanced")
				.define('S', new ItemStack(TRItems.steel_chainsaw, 1, OreDictionary.WILDCARD_VALUE))
				.withResult(new ItemStack(TRItems.diamondchainsaw))
				.save(tools);
		new ShapedBuilder<>()
				.name("advanced_chainsaw")
				.pattern(" NI")
				.pattern("OCN")
				.pattern("DO ")
				.define('N', "nuggetIridium")
				.define('I', "plateIridiumAlloy")
				.define('O', overclock)
				.define('C', "circuitMaster")
				.define('D', new ItemStack(TRItems.diamondchainsaw, 1, OreDictionary.WILDCARD_VALUE))
				.withResult(new ItemStack(TRItems.advancedchainsaw))
				.save(tools);
		new ShapedBuilder<>()
				.name("steel_jackhammer")
				.pattern("SBS")
				.pattern("SCS")
				.pattern(" S ")
				.define('S', "ingotSteel")
				.define('B', "reBattery")
				.define('C', "circuitBasic")
				.withResult(new ItemStack(TRItems.steeljackhammer))
				.save(tools);
		new ShapedBuilder<>()
				.name("diamond_jackhammer")
				.pattern("DSD")
				.pattern("TCT")
				.pattern(" D ")
				.define('D', "gemDiamond")
				.define('S', new ItemStack(TRItems.steeljackhammer, 1, OreDictionary.WILDCARD_VALUE))
				.define('T', "ingotTitanium")
				.define('C', "circuitAdvanced")
				.withResult(new ItemStack(TRItems.diamondjackhammer))
				.save(tools);
		new ShapedBuilder<>()
				.name("advanced_jackhammer")
				.pattern("NDN")
				.pattern("OCO")
				.pattern(" I ")
				.define('N', "nuggetIridium")
				.define('D', new ItemStack(TRItems.diamondjackhammer, 1, OreDictionary.WILDCARD_VALUE))
				.define('O', overclock)
				.define('C', "circuitMaster")
				.define('I', "plateIridiumAlloy")
				.withResult(new ItemStack(TRItems.advanced_jackhammer))
				.save(tools);
	}

	@SuppressWarnings("ConstantConditions")
	public static void armor(File file) {
		File armor = new File(file, "armor");
		new ShapedBuilder<>()
				.name("cloak")
				.pattern("CIC")
				.pattern("IOI")
				.pattern("CIC")
				.define('C', "ingotChrome")
				.define('I', "plateIridiumAlloy")
				.define('O', new ItemStack(TRItems.lapotronicorb))
				.withResult(new ItemStack(TRItems.cloakingdevice))
				.save(armor);
		new ShapedBuilder<>()
				.name("orb_pack")
				.pattern("FOF")
				.pattern("SPS")
				.pattern("FIF")
				.define('F', "circuitMaster")
				.define('O', new ItemStack(TRItems.lapotronicorb))
				.define('S', "craftingSuperconductor")
				.define('P', new ItemStack(TRItems.lithiumbatpack))
				.define('I', "ingotIridium")
				.withResult(new ItemStack(TRItems.lapotronpack))
				.save(armor);
		new ShapedBuilder<>()
				.name("lithium_bat_pack")
				.pattern("BCB")
				.pattern("BPB")
				.pattern("B B")
				.define('B', new ItemStack(TRItems.lithiumbattery))
				.define('C', "circuitAdvanced")
				.define('P', "plateAluminum")
				.withResult(new ItemStack(TRItems.lapotronpack))
				.save(armor);
		armorList(armor, "ruby", "gemRuby", TRItems.rubyhelmet, TRItems.rubychestplate,
				TRItems.rubyleggings, TRItems.rubyboots, TRItems.rubysword, TRItems.rubypickaxe,
				TRItems.rubyaxe, TRItems.rubyspade, TRItems.rubyhoe);
		armorList(armor, "sapphire", "gemSapphire", TRItems.sapphirehelmet, TRItems.sapphirechestplate,
				TRItems.sapphireleggings, TRItems.sapphireboots, TRItems.sapphiresword, TRItems.sapphirepickaxe,
				TRItems.sapphireaxe, TRItems.sapphirespade, TRItems.sapphirehoe);
		armorList(armor, "peridot", "gemPeridot", TRItems.peridothelmet, TRItems.peridotchestplate,
				TRItems.peridotleggings, TRItems.peridotboots, TRItems.peridotsword, TRItems.peridotpickaxe,
				TRItems.peridotaxe, TRItems.peridotspade, TRItems.peridothoe);
		armorList(armor, "bronze", "ingotBronze", TRItems.bronzehelmet, TRItems.bronzechestplate,
				TRItems.bronzeleggings, TRItems.bronzeboots, TRItems.bronzesword, TRItems.bronzepickaxe,
				TRItems.bronzeaxe, TRItems.bronzespade, TRItems.bronzehoe);
	}

	private static void armorList(File file, String name, String oreName,
								  Item helmet, Item chestplate, Item leggings, Item boots,
								  Item sword, Item pickaxe, Item axe, Item spade, Item hoe) {
		ItemStack stick = new ItemStack(Items.STICK);
		new ShapedBuilder<>()
				.name(name + "_helmet")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("HHH")
				.pattern("H H")
				.define('H', oreName)
				.withResult(new ItemStack(helmet))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_chestplate")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("H H")
				.pattern("HHH")
				.pattern("HHH")
				.define('H', oreName)
				.withResult(new ItemStack(chestplate))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_leggings")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("HHH")
				.pattern("H H")
				.pattern("H H")
				.define('H', oreName)
				.withResult(new ItemStack(leggings))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_boots")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("H H")
				.pattern("H H")
				.define('H', oreName)
				.withResult(new ItemStack(boots))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_sword")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("H")
				.pattern("H")
				.pattern("S")
				.define('H', oreName)
				.define('S', stick)
				.withResult(new ItemStack(sword))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_pickaxe")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("HHH")
				.pattern(" S ")
				.pattern(" S ")
				.define('H', oreName)
				.define('S', stick)
				.withResult(new ItemStack(pickaxe))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_axe")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("HH")
				.pattern("SH")
				.pattern("S ")
				.define('H', oreName)
				.define('S', stick)
				.withResult(new ItemStack(axe))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_axe_mirrored")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("HH")
				.pattern("HS")
				.pattern(" S")
				.define('H', oreName)
				.define('S', stick)
				.withResult(new ItemStack(axe))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_shovel")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("H")
				.pattern("S")
				.pattern("S")
				.define('H', oreName)
				.define('S', stick)
				.withResult(new ItemStack(spade))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_hoe")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("HH")
				.pattern("S ")
				.pattern("S ")
				.define('H', oreName)
				.define('S', stick)
				.withResult(new ItemStack(hoe))
				.save(file);
		new ShapedBuilder<>()
				.name(name + "_hoe_mirrored")
				.withCondition(new ConfigCondition("itemconfig.loadarmor"))
				.pattern("HH")
				.pattern(" S")
				.pattern(" S")
				.define('H', oreName)
				.define('S', stick)
				.withResult(new ItemStack(hoe))
				.save(file);
	}
}
