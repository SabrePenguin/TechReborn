package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.blocks.fluids.TRFluids;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Dust;
import com.sabrepenguin.techreborn.items.materials.Gem;
import com.sabrepenguin.techreborn.items.materials.Nugget;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.recipe.ChanceRecipe;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrapboxRecipes {
	public static final List<ItemStack> OUTPUTS = RegistryHandler.instance().getScrapboxRecipes();
	public static final ITRRegistry REGISTRY = RegistryHandler.instance().getScrapboxRegistry();

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		OUTPUTS.add(new ItemStack(Items.DIAMOND));
		OUTPUTS.add(new ItemStack(Items.STICK));
		OUTPUTS.add(new ItemStack(Items.COAL));
		OUTPUTS.add(new ItemStack(Items.APPLE));
		OUTPUTS.add(new ItemStack(Items.BAKED_POTATO));
		OUTPUTS.add(new ItemStack(Items.BLAZE_POWDER));
		OUTPUTS.add(new ItemStack(Items.WHEAT));
		OUTPUTS.add(new ItemStack(Items.CARROT));
		OUTPUTS.add(new ItemStack(Items.BOAT));
		OUTPUTS.add(new ItemStack(Items.ACACIA_BOAT));
		OUTPUTS.add(new ItemStack(Items.BIRCH_BOAT));
		OUTPUTS.add(new ItemStack(Items.DARK_OAK_BOAT));
		OUTPUTS.add(new ItemStack(Items.JUNGLE_BOAT));
		OUTPUTS.add(new ItemStack(Items.SPRUCE_BOAT));
		OUTPUTS.add(new ItemStack(Items.BLAZE_ROD));
		OUTPUTS.add(new ItemStack(Items.COMPASS));
		OUTPUTS.add(new ItemStack(Items.MAP));
		OUTPUTS.add(new ItemStack(Items.LEATHER_LEGGINGS));
		OUTPUTS.add(new ItemStack(Items.BOW));
		OUTPUTS.add(new ItemStack(Items.COOKED_CHICKEN));
		OUTPUTS.add(new ItemStack(Items.CAKE));
		OUTPUTS.add(new ItemStack(Items.ACACIA_DOOR));
		OUTPUTS.add(new ItemStack(Items.DARK_OAK_DOOR));
		OUTPUTS.add(new ItemStack(Items.BIRCH_DOOR));
		OUTPUTS.add(new ItemStack(Items.JUNGLE_DOOR));
		OUTPUTS.add(new ItemStack(Items.OAK_DOOR));
		OUTPUTS.add(new ItemStack(Items.SPRUCE_DOOR));
		OUTPUTS.add(new ItemStack(Items.WOODEN_AXE));
		OUTPUTS.add(new ItemStack(Items.WOODEN_HOE));
		OUTPUTS.add(new ItemStack(Items.WOODEN_PICKAXE));
		OUTPUTS.add(new ItemStack(Items.WOODEN_SHOVEL));
		OUTPUTS.add(new ItemStack(Items.WOODEN_SWORD));
		OUTPUTS.add(new ItemStack(Items.BED));
		OUTPUTS.add(new ItemStack(Items.SKULL, 1, 0));
		OUTPUTS.add(new ItemStack(Items.SKULL, 1, 2));
		OUTPUTS.add(new ItemStack(Items.SKULL, 1, 4));
//		for (int i = 0; i < StackWIPHandler.devHeads.size(); i++)
//			REGISTRY.add(StackWIPHandler.devHeads.get(i));
		OUTPUTS.add(new ItemStack(Items.GLOWSTONE_DUST));
		OUTPUTS.add(new ItemStack(Items.STRING));
		OUTPUTS.add(new ItemStack(Items.MINECART));
		OUTPUTS.add(new ItemStack(Items.CHEST_MINECART));
		OUTPUTS.add(new ItemStack(Items.HOPPER_MINECART));
		OUTPUTS.add(new ItemStack(Items.PRISMARINE_SHARD));
		OUTPUTS.add(new ItemStack(Items.SHEARS));
		OUTPUTS.add(new ItemStack(Items.EXPERIENCE_BOTTLE));
		OUTPUTS.add(new ItemStack(Items.BONE));
		OUTPUTS.add(new ItemStack(Items.BOWL));
		OUTPUTS.add(new ItemStack(Items.BRICK));
		OUTPUTS.add(new ItemStack(Items.FISHING_ROD));
		OUTPUTS.add(new ItemStack(Items.BOOK));
		OUTPUTS.add(new ItemStack(Items.PAPER));
		OUTPUTS.add(new ItemStack(Items.SUGAR));
		OUTPUTS.add(new ItemStack(Items.REEDS));
		OUTPUTS.add(new ItemStack(Items.SPIDER_EYE));
		OUTPUTS.add(new ItemStack(Items.SLIME_BALL));
		OUTPUTS.add(new ItemStack(Items.ROTTEN_FLESH));
		OUTPUTS.add(new ItemStack(Items.SIGN));
		OUTPUTS.add(new ItemStack(Items.WRITABLE_BOOK));
		OUTPUTS.add(new ItemStack(Items.COOKED_BEEF));
		OUTPUTS.add(new ItemStack(Items.NAME_TAG));
		OUTPUTS.add(new ItemStack(Items.SADDLE));
		OUTPUTS.add(new ItemStack(Items.REDSTONE));
		OUTPUTS.add(new ItemStack(Items.GUNPOWDER));
		OUTPUTS.add(new ItemStack(Items.RABBIT_HIDE));
		OUTPUTS.add(new ItemStack(Items.RABBIT_FOOT));
		OUTPUTS.add(new ItemStack(Items.APPLE));
		OUTPUTS.add(new ItemStack(Items.GOLDEN_APPLE));
		OUTPUTS.add(new ItemStack(Items.GOLD_NUGGET));
		OUTPUTS.add(new ItemStack(Items.SHULKER_SHELL));


		OUTPUTS.add(new ItemStack(TRItems.cell));
		OUTPUTS.add(TRItems.cell.getCellWithFluid(FluidRegistry.getFluid("water")));
		OUTPUTS.add(TRItems.cell.getCellWithFluid(TRFluids.COMPRESSED_AIR));
		OUTPUTS.add(new ItemStack(TRItems.part, 1, Part.PartMeta.sap.metadata()));
		OUTPUTS.add(new ItemStack(TRItems.part, 1, Part.PartMeta.rubber.metadata()));

		OUTPUTS.add(new ItemStack(Blocks.TRAPDOOR));
		OUTPUTS.add(new ItemStack(Blocks.STONE_BUTTON));
		OUTPUTS.add(new ItemStack(Blocks.WOODEN_BUTTON));
		OUTPUTS.add(new ItemStack(Blocks.ACACIA_FENCE));
		OUTPUTS.add(new ItemStack(Blocks.ACACIA_FENCE_GATE));
		OUTPUTS.add(new ItemStack(Blocks.BIRCH_FENCE));
		OUTPUTS.add(new ItemStack(Blocks.BIRCH_FENCE_GATE));
		OUTPUTS.add(new ItemStack(Blocks.DARK_OAK_FENCE));
		OUTPUTS.add(new ItemStack(Blocks.DARK_OAK_FENCE_GATE));
		OUTPUTS.add(new ItemStack(Blocks.JUNGLE_FENCE));
		OUTPUTS.add(new ItemStack(Blocks.JUNGLE_FENCE_GATE));
		OUTPUTS.add(new ItemStack(Blocks.NETHER_BRICK_FENCE));
		OUTPUTS.add(new ItemStack(Blocks.OAK_FENCE));
		OUTPUTS.add(new ItemStack(Blocks.OAK_FENCE_GATE));
		OUTPUTS.add(new ItemStack(Blocks.SPRUCE_FENCE));
		OUTPUTS.add(new ItemStack(Blocks.SPRUCE_FENCE_GATE));
		OUTPUTS.add(new ItemStack(Blocks.BRICK_BLOCK));
		OUTPUTS.add(new ItemStack(Blocks.CRAFTING_TABLE));
		OUTPUTS.add(new ItemStack(Blocks.PUMPKIN));
		OUTPUTS.add(new ItemStack(Blocks.NETHERRACK));
		OUTPUTS.add(new ItemStack(Blocks.GRASS));
		OUTPUTS.add(new ItemStack(Blocks.DIRT, 1, 0));
		OUTPUTS.add(new ItemStack(Blocks.DIRT, 1, 1));
		OUTPUTS.add(new ItemStack(Blocks.SAND, 1, 0));
		OUTPUTS.add(new ItemStack(Blocks.SAND, 1, 1));
		OUTPUTS.add(new ItemStack(Blocks.GLOWSTONE));
		OUTPUTS.add(new ItemStack(Blocks.GRAVEL));
		OUTPUTS.add(new ItemStack(Blocks.HARDENED_CLAY));
		OUTPUTS.add(new ItemStack(Blocks.GLASS));
		OUTPUTS.add(new ItemStack(Blocks.GLASS_PANE));
		OUTPUTS.add(new ItemStack(Blocks.CACTUS));
		OUTPUTS.add(new ItemStack(Blocks.TALLGRASS, 1, 0));
		OUTPUTS.add(new ItemStack(Blocks.TALLGRASS, 1, 1));
		OUTPUTS.add(new ItemStack(Blocks.DEADBUSH));
		OUTPUTS.add(new ItemStack(Blocks.CHEST));
		OUTPUTS.add(new ItemStack(Blocks.TNT));
		OUTPUTS.add(new ItemStack(Blocks.RAIL));
		OUTPUTS.add(new ItemStack(Blocks.DETECTOR_RAIL));
		OUTPUTS.add(new ItemStack(Blocks.GOLDEN_RAIL));
		OUTPUTS.add(new ItemStack(Blocks.ACTIVATOR_RAIL));
		OUTPUTS.add(new ItemStack(Blocks.YELLOW_FLOWER));
		OUTPUTS.add(new ItemStack(Blocks.RED_FLOWER, 1, 0));
		OUTPUTS.add(new ItemStack(Blocks.RED_FLOWER, 1, 1));
		OUTPUTS.add(new ItemStack(Blocks.RED_FLOWER, 1, 2));
		OUTPUTS.add(new ItemStack(Blocks.RED_FLOWER, 1, 3));
		OUTPUTS.add(new ItemStack(Blocks.RED_FLOWER, 1, 4));
		OUTPUTS.add(new ItemStack(Blocks.RED_FLOWER, 1, 5));
		OUTPUTS.add(new ItemStack(Blocks.RED_FLOWER, 1, 6));
		OUTPUTS.add(new ItemStack(Blocks.RED_FLOWER, 1, 7));
		OUTPUTS.add(new ItemStack(Blocks.RED_FLOWER, 1, 8));
		OUTPUTS.add(new ItemStack(Blocks.BROWN_MUSHROOM));
		OUTPUTS.add(new ItemStack(Blocks.RED_MUSHROOM));
		OUTPUTS.add(new ItemStack(Blocks.BROWN_MUSHROOM_BLOCK));
		OUTPUTS.add(new ItemStack(Blocks.RED_MUSHROOM_BLOCK));
		OUTPUTS.add(new ItemStack(Blocks.SAPLING, 1, 0));
		OUTPUTS.add(new ItemStack(Blocks.SAPLING, 1, 1));
		OUTPUTS.add(new ItemStack(Blocks.SAPLING, 1, 2));
		OUTPUTS.add(new ItemStack(Blocks.SAPLING, 1, 3));
		OUTPUTS.add(new ItemStack(Blocks.SAPLING, 1, 4));
		OUTPUTS.add(new ItemStack(Blocks.SAPLING, 1, 5));
		OUTPUTS.add(new ItemStack(Blocks.LEAVES, 1, 0));
		OUTPUTS.add(new ItemStack(Blocks.LEAVES, 1, 1));
		OUTPUTS.add(new ItemStack(Blocks.LEAVES, 1, 2));
		OUTPUTS.add(new ItemStack(Blocks.LEAVES, 1, 3));
		OUTPUTS.add(new ItemStack(Blocks.LEAVES2, 1, 0));
		OUTPUTS.add(new ItemStack(Blocks.LEAVES2, 1, 1));

		OUTPUTS.add(new ItemStack(TRBlocks.rubber_sapling));
		for (Dust.MetaDust dust: Dust.MetaDust.values()) {
			OUTPUTS.add(new ItemStack(TRItems.dust, 1, dust.metadata()));
		}

		OUTPUTS.add(new ItemStack(TRBlocks.rubber_sapling));
		for (Nugget.NuggetMeta nugget: Nugget.NuggetMeta.values()) {
			OUTPUTS.add(new ItemStack(TRItems.nuggets, 1, nugget.metadata()));
		}

		OUTPUTS.add(new ItemStack(TRBlocks.rubber_sapling));
		for (Gem.GemMeta gem: Gem.GemMeta.values()) {
			OUTPUTS.add(new ItemStack(TRItems.gem, 1, gem.metadata()));
		}

		registerDyeable(Items.DYE);
		registerDyeable(Blocks.WOOL);
		registerDyeable(Blocks.CARPET);
		registerDyeable(Blocks.STAINED_GLASS);
		registerDyeable(Blocks.STAINED_GLASS_PANE);
		registerDyeable(Blocks.STAINED_HARDENED_CLAY);

		REGISTRY.addRecipe(new ChanceRecipe(Arrays.asList(new CountedIngredient(TRItems.scrapbox, 1, 0)),
				OUTPUTS, 20, 8));
	}

	static void registerDyeable(Item item) {
		for (int i = 0; i < 16; i++)
			OUTPUTS.add(new ItemStack(item, 1, i));
	}

	static void registerDyeable(Block block) {
		registerDyeable(Item.getItemFromBlock(block));
	}
}
