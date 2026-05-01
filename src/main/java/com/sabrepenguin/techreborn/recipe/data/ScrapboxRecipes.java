package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.blocks.fluids.TRFluids;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Dust;
import com.sabrepenguin.techreborn.items.materials.Gem;
import com.sabrepenguin.techreborn.items.materials.Nugget;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.List;

public class ScrapboxRecipes {
	public static final List<ItemStack> REGISTRY = RegistryHandler.instance().getScrapboxRegistry();

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		REGISTRY.add(new ItemStack(Items.DIAMOND));
		REGISTRY.add(new ItemStack(Items.STICK));
		REGISTRY.add(new ItemStack(Items.COAL));
		REGISTRY.add(new ItemStack(Items.APPLE));
		REGISTRY.add(new ItemStack(Items.BAKED_POTATO));
		REGISTRY.add(new ItemStack(Items.BLAZE_POWDER));
		REGISTRY.add(new ItemStack(Items.WHEAT));
		REGISTRY.add(new ItemStack(Items.CARROT));
		REGISTRY.add(new ItemStack(Items.BOAT));
		REGISTRY.add(new ItemStack(Items.ACACIA_BOAT));
		REGISTRY.add(new ItemStack(Items.BIRCH_BOAT));
		REGISTRY.add(new ItemStack(Items.DARK_OAK_BOAT));
		REGISTRY.add(new ItemStack(Items.JUNGLE_BOAT));
		REGISTRY.add(new ItemStack(Items.SPRUCE_BOAT));
		REGISTRY.add(new ItemStack(Items.BLAZE_ROD));
		REGISTRY.add(new ItemStack(Items.COMPASS));
		REGISTRY.add(new ItemStack(Items.MAP));
		REGISTRY.add(new ItemStack(Items.LEATHER_LEGGINGS));
		REGISTRY.add(new ItemStack(Items.BOW));
		REGISTRY.add(new ItemStack(Items.COOKED_CHICKEN));
		REGISTRY.add(new ItemStack(Items.CAKE));
		REGISTRY.add(new ItemStack(Items.ACACIA_DOOR));
		REGISTRY.add(new ItemStack(Items.DARK_OAK_DOOR));
		REGISTRY.add(new ItemStack(Items.BIRCH_DOOR));
		REGISTRY.add(new ItemStack(Items.JUNGLE_DOOR));
		REGISTRY.add(new ItemStack(Items.OAK_DOOR));
		REGISTRY.add(new ItemStack(Items.SPRUCE_DOOR));
		REGISTRY.add(new ItemStack(Items.WOODEN_AXE));
		REGISTRY.add(new ItemStack(Items.WOODEN_HOE));
		REGISTRY.add(new ItemStack(Items.WOODEN_PICKAXE));
		REGISTRY.add(new ItemStack(Items.WOODEN_SHOVEL));
		REGISTRY.add(new ItemStack(Items.WOODEN_SWORD));
		REGISTRY.add(new ItemStack(Items.BED));
		REGISTRY.add(new ItemStack(Items.SKULL, 1, 0));
		REGISTRY.add(new ItemStack(Items.SKULL, 1, 2));
		REGISTRY.add(new ItemStack(Items.SKULL, 1, 4));
//		for (int i = 0; i < StackWIPHandler.devHeads.size(); i++)
//			REGISTRY.add(StackWIPHandler.devHeads.get(i));
		REGISTRY.add(new ItemStack(Items.GLOWSTONE_DUST));
		REGISTRY.add(new ItemStack(Items.STRING));
		REGISTRY.add(new ItemStack(Items.MINECART));
		REGISTRY.add(new ItemStack(Items.CHEST_MINECART));
		REGISTRY.add(new ItemStack(Items.HOPPER_MINECART));
		REGISTRY.add(new ItemStack(Items.PRISMARINE_SHARD));
		REGISTRY.add(new ItemStack(Items.SHEARS));
		REGISTRY.add(new ItemStack(Items.EXPERIENCE_BOTTLE));
		REGISTRY.add(new ItemStack(Items.BONE));
		REGISTRY.add(new ItemStack(Items.BOWL));
		REGISTRY.add(new ItemStack(Items.BRICK));
		REGISTRY.add(new ItemStack(Items.FISHING_ROD));
		REGISTRY.add(new ItemStack(Items.BOOK));
		REGISTRY.add(new ItemStack(Items.PAPER));
		REGISTRY.add(new ItemStack(Items.SUGAR));
		REGISTRY.add(new ItemStack(Items.REEDS));
		REGISTRY.add(new ItemStack(Items.SPIDER_EYE));
		REGISTRY.add(new ItemStack(Items.SLIME_BALL));
		REGISTRY.add(new ItemStack(Items.ROTTEN_FLESH));
		REGISTRY.add(new ItemStack(Items.SIGN));
		REGISTRY.add(new ItemStack(Items.WRITABLE_BOOK));
		REGISTRY.add(new ItemStack(Items.COOKED_BEEF));
		REGISTRY.add(new ItemStack(Items.NAME_TAG));
		REGISTRY.add(new ItemStack(Items.SADDLE));
		REGISTRY.add(new ItemStack(Items.REDSTONE));
		REGISTRY.add(new ItemStack(Items.GUNPOWDER));
		REGISTRY.add(new ItemStack(Items.RABBIT_HIDE));
		REGISTRY.add(new ItemStack(Items.RABBIT_FOOT));
		REGISTRY.add(new ItemStack(Items.APPLE));
		REGISTRY.add(new ItemStack(Items.GOLDEN_APPLE));
		REGISTRY.add(new ItemStack(Items.GOLD_NUGGET));
		REGISTRY.add(new ItemStack(Items.SHULKER_SHELL));


		REGISTRY.add(new ItemStack(TRItems.cell));
		REGISTRY.add(TRItems.cell.getCellWithFluid(FluidRegistry.getFluid("water")));
		REGISTRY.add(TRItems.cell.getCellWithFluid(TRFluids.COMPRESSED_AIR));
		REGISTRY.add(new ItemStack(TRItems.part, 1, Part.PartMeta.sap.metadata()));
		REGISTRY.add(new ItemStack(TRItems.part, 1, Part.PartMeta.rubber.metadata()));

		REGISTRY.add(new ItemStack(Blocks.TRAPDOOR));
		REGISTRY.add(new ItemStack(Blocks.STONE_BUTTON));
		REGISTRY.add(new ItemStack(Blocks.WOODEN_BUTTON));
		REGISTRY.add(new ItemStack(Blocks.ACACIA_FENCE));
		REGISTRY.add(new ItemStack(Blocks.ACACIA_FENCE_GATE));
		REGISTRY.add(new ItemStack(Blocks.BIRCH_FENCE));
		REGISTRY.add(new ItemStack(Blocks.BIRCH_FENCE_GATE));
		REGISTRY.add(new ItemStack(Blocks.DARK_OAK_FENCE));
		REGISTRY.add(new ItemStack(Blocks.DARK_OAK_FENCE_GATE));
		REGISTRY.add(new ItemStack(Blocks.JUNGLE_FENCE));
		REGISTRY.add(new ItemStack(Blocks.JUNGLE_FENCE_GATE));
		REGISTRY.add(new ItemStack(Blocks.NETHER_BRICK_FENCE));
		REGISTRY.add(new ItemStack(Blocks.OAK_FENCE));
		REGISTRY.add(new ItemStack(Blocks.OAK_FENCE_GATE));
		REGISTRY.add(new ItemStack(Blocks.SPRUCE_FENCE));
		REGISTRY.add(new ItemStack(Blocks.SPRUCE_FENCE_GATE));
		REGISTRY.add(new ItemStack(Blocks.BRICK_BLOCK));
		REGISTRY.add(new ItemStack(Blocks.CRAFTING_TABLE));
		REGISTRY.add(new ItemStack(Blocks.PUMPKIN));
		REGISTRY.add(new ItemStack(Blocks.NETHERRACK));
		REGISTRY.add(new ItemStack(Blocks.GRASS));
		REGISTRY.add(new ItemStack(Blocks.DIRT, 1, 0));
		REGISTRY.add(new ItemStack(Blocks.DIRT, 1, 1));
		REGISTRY.add(new ItemStack(Blocks.SAND, 1, 0));
		REGISTRY.add(new ItemStack(Blocks.SAND, 1, 1));
		REGISTRY.add(new ItemStack(Blocks.GLOWSTONE));
		REGISTRY.add(new ItemStack(Blocks.GRAVEL));
		REGISTRY.add(new ItemStack(Blocks.HARDENED_CLAY));
		REGISTRY.add(new ItemStack(Blocks.GLASS));
		REGISTRY.add(new ItemStack(Blocks.GLASS_PANE));
		REGISTRY.add(new ItemStack(Blocks.CACTUS));
		REGISTRY.add(new ItemStack(Blocks.TALLGRASS, 1, 0));
		REGISTRY.add(new ItemStack(Blocks.TALLGRASS, 1, 1));
		REGISTRY.add(new ItemStack(Blocks.DEADBUSH));
		REGISTRY.add(new ItemStack(Blocks.CHEST));
		REGISTRY.add(new ItemStack(Blocks.TNT));
		REGISTRY.add(new ItemStack(Blocks.RAIL));
		REGISTRY.add(new ItemStack(Blocks.DETECTOR_RAIL));
		REGISTRY.add(new ItemStack(Blocks.GOLDEN_RAIL));
		REGISTRY.add(new ItemStack(Blocks.ACTIVATOR_RAIL));
		REGISTRY.add(new ItemStack(Blocks.YELLOW_FLOWER));
		REGISTRY.add(new ItemStack(Blocks.RED_FLOWER, 1, 0));
		REGISTRY.add(new ItemStack(Blocks.RED_FLOWER, 1, 1));
		REGISTRY.add(new ItemStack(Blocks.RED_FLOWER, 1, 2));
		REGISTRY.add(new ItemStack(Blocks.RED_FLOWER, 1, 3));
		REGISTRY.add(new ItemStack(Blocks.RED_FLOWER, 1, 4));
		REGISTRY.add(new ItemStack(Blocks.RED_FLOWER, 1, 5));
		REGISTRY.add(new ItemStack(Blocks.RED_FLOWER, 1, 6));
		REGISTRY.add(new ItemStack(Blocks.RED_FLOWER, 1, 7));
		REGISTRY.add(new ItemStack(Blocks.RED_FLOWER, 1, 8));
		REGISTRY.add(new ItemStack(Blocks.BROWN_MUSHROOM));
		REGISTRY.add(new ItemStack(Blocks.RED_MUSHROOM));
		REGISTRY.add(new ItemStack(Blocks.BROWN_MUSHROOM_BLOCK));
		REGISTRY.add(new ItemStack(Blocks.RED_MUSHROOM_BLOCK));
		REGISTRY.add(new ItemStack(Blocks.SAPLING, 1, 0));
		REGISTRY.add(new ItemStack(Blocks.SAPLING, 1, 1));
		REGISTRY.add(new ItemStack(Blocks.SAPLING, 1, 2));
		REGISTRY.add(new ItemStack(Blocks.SAPLING, 1, 3));
		REGISTRY.add(new ItemStack(Blocks.SAPLING, 1, 4));
		REGISTRY.add(new ItemStack(Blocks.SAPLING, 1, 5));
		REGISTRY.add(new ItemStack(Blocks.LEAVES, 1, 0));
		REGISTRY.add(new ItemStack(Blocks.LEAVES, 1, 1));
		REGISTRY.add(new ItemStack(Blocks.LEAVES, 1, 2));
		REGISTRY.add(new ItemStack(Blocks.LEAVES, 1, 3));
		REGISTRY.add(new ItemStack(Blocks.LEAVES2, 1, 0));
		REGISTRY.add(new ItemStack(Blocks.LEAVES2, 1, 1));

		REGISTRY.add(new ItemStack(TRBlocks.rubber_sapling));
		for (Dust.MetaDust dust: Dust.MetaDust.values()) {
			REGISTRY.add(new ItemStack(TRItems.dust, 1, dust.metadata()));
		}

		REGISTRY.add(new ItemStack(TRBlocks.rubber_sapling));
		for (Nugget.NuggetMeta nugget: Nugget.NuggetMeta.values()) {
			REGISTRY.add(new ItemStack(TRItems.nuggets, 1, nugget.metadata()));
		}

		REGISTRY.add(new ItemStack(TRBlocks.rubber_sapling));
		for (Gem.GemMeta gem: Gem.GemMeta.values()) {
			REGISTRY.add(new ItemStack(TRItems.gem, 1, gem.metadata()));
		}

		registerDyeable(Items.DYE);
		registerDyeable(Blocks.WOOL);
		registerDyeable(Blocks.CARPET);
		registerDyeable(Blocks.STAINED_GLASS);
		registerDyeable(Blocks.STAINED_GLASS_PANE);
		registerDyeable(Blocks.STAINED_HARDENED_CLAY);
	}

	static void registerDyeable(Item item) {
		for (int i = 0; i < 16; i++)
			REGISTRY.add(new ItemStack(item, 1, i));
	}

	static void registerDyeable(Block block) {
		registerDyeable(Item.getItemFromBlock(block));
	}
}
