package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import com.sabrepenguin.techreborn.tileentity.cable.TileEntityCable;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.handlers.ModSounds;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.energy.CapabilityEnergy;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockCable extends Block implements INonStandardLocation, IMetaInformation {
	public static final PropertyEnum<CableEnum> TYPE = PropertyEnum.create("type", CableEnum.class);
	private static final PropertyBool SOUTH = PropertyBool.create("south");
	private static final PropertyBool NORTH = PropertyBool.create("north");
	private static final PropertyBool EAST = PropertyBool.create("east");
	private static final PropertyBool WEST = PropertyBool.create("west");
	private static final PropertyBool DOWN = PropertyBool.create("down");
	private static final PropertyBool UP = PropertyBool.create("up");

	private static final AxisAlignedBB[] THICK_BOUNDING_BOXES = generateBoundingBoxes(false);
	private static final AxisAlignedBB[] THIN_BOUNDING_BOXES = generateBoundingBoxes(true);

	public BlockCable() {
		super(Material.ROCK);
		this.setHardness(1.0f);
		this.setResistance(8f);
		this.setRegistryName(Tags.MODID, "cable");
		this.setTranslationKey(Tags.MODID + ".cable");
		this.setDefaultState(this.getDefaultState().withProperty(TYPE, CableEnum.COPPER)
				.withProperty(SOUTH, false).withProperty(NORTH, false)
				.withProperty(EAST, false).withProperty(WEST, false)
				.withProperty(UP, false).withProperty(DOWN, false));
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public @Nullable TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCable();
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return super.getPickBlock(state, target, world, pos, player);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE, NORTH, SOUTH, EAST, WEST, UP, DOWN);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, CableEnum.META_MAP.get(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).metadata;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).metadata;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (CableEnum cable: CableEnum.values()) {
			items.add(new ItemStack(this, 1, cable.metadata));
		}
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(CableEnum.values()).map(cable -> Pair.of(cable.getName(), cable.metadata)).collect(Collectors.toList());
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(UP, canConnect(worldIn, pos, EnumFacing.UP))
				.withProperty(DOWN, canConnect(worldIn, pos, EnumFacing.DOWN))
				.withProperty(SOUTH, canConnect(worldIn, pos, EnumFacing.SOUTH))
				.withProperty(NORTH, canConnect(worldIn, pos, EnumFacing.NORTH))
				.withProperty(EAST, canConnect(worldIn, pos, EnumFacing.EAST))
				.withProperty(WEST, canConnect(worldIn, pos, EnumFacing.WEST));
	}

	private boolean canConnect(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos offset = pos.offset(facing);
		if (world.getBlockState(offset).getBlock() == this)
			return true;
		TileEntity te = world instanceof ChunkCache chunkCache ? chunkCache.getTileEntity(offset, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(offset);
		return te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = state.getActualState(source, pos);
		if (CableEnum.isThin(state.getValue(TYPE))) {
			return THIN_BOUNDING_BOXES[getCollisionBox(state)];
		}
		return THICK_BOUNDING_BOXES[getCollisionBox(state)];
	}

	private static int getCollisionBox(IBlockState state) {
		int i = 0;
		i |= state.getValue(DOWN) ? 1 : 0;
		i |= state.getValue(UP) ? 2 : 0;
		i |= state.getValue(NORTH) ? 4 : 0;
		i |= state.getValue(SOUTH) ? 8 : 0;
		i |= state.getValue(EAST) ? 16 : 0;
		i |= state.getValue(WEST) ? 32 : 0;
		return i;
	}

	private static int nonCombinedCollisionBox(EnumFacing facing) {
		return switch (facing) {
			case DOWN -> 1;
			case UP -> 2;
			case NORTH -> 4;
			case SOUTH -> 8;
			case EAST -> 16;
			case WEST -> 32;
		};
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (!isActualState) {
			state = state.getActualState(worldIn, pos);
		}
		AxisAlignedBB[] target = CableEnum.isThin(state.getValue(TYPE)) ? THIN_BOUNDING_BOXES : THICK_BOUNDING_BOXES;
		addIntersectingBox(pos, entityBox, collidingBoxes, target[0]);
		if (state.getValue(DOWN))
			addIntersectingBox(pos, entityBox, collidingBoxes, target[nonCombinedCollisionBox(EnumFacing.DOWN)]);
		if (state.getValue(UP))
			addIntersectingBox(pos, entityBox, collidingBoxes, target[nonCombinedCollisionBox(EnumFacing.UP)]);
		if (state.getValue(NORTH))
			addIntersectingBox(pos, entityBox, collidingBoxes, target[nonCombinedCollisionBox(EnumFacing.NORTH)]);
		if (state.getValue(SOUTH))
			addIntersectingBox(pos, entityBox, collidingBoxes, target[nonCombinedCollisionBox(EnumFacing.SOUTH)]);
		if (state.getValue(EAST))
			addIntersectingBox(pos, entityBox, collidingBoxes, target[nonCombinedCollisionBox(EnumFacing.EAST)]);
		if (state.getValue(WEST))
			addIntersectingBox(pos, entityBox, collidingBoxes, target[nonCombinedCollisionBox(EnumFacing.WEST)]);
	}

	private void addIntersectingBox(BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, AxisAlignedBB block) {
		AxisAlignedBB offsetBox = block.offset(pos);
		if (offsetBox.intersects(entityBox)) {
			collidingBoxes.add(offsetBox);
		}
	}

	private static AxisAlignedBB[] generateBoundingBoxes(boolean thin) {
		AxisAlignedBB[] boxes = new AxisAlignedBB[64];
		double min = 0.375;
		double max = 0.625;
		if (!thin) {
			min = (1/16D)*5;
			max = (1/16D)*11;
		}
		AxisAlignedBB core = new AxisAlignedBB(min, min, min, max, max, max);
		boxes[0] = core;
		for (int i = 1; i < 64; i++) {
			AxisAlignedBB box = core;
			if ((i & 1) != 0) box = box.union(createAABB(EnumFacing.DOWN, thin));
			if ((i & 2) != 0) box = box.union(createAABB(EnumFacing.UP, thin));
			if ((i & 4) != 0) box = box.union(createAABB(EnumFacing.NORTH, thin));
			if ((i & 8) != 0) box = box.union(createAABB(EnumFacing.SOUTH, thin));
			if ((i & 16) != 0) box = box.union(createAABB(EnumFacing.EAST, thin));
			if ((i & 32) != 0) box = box.union(createAABB(EnumFacing.WEST, thin));
			boxes[i] = box;
		}
		return boxes;
	}

	private static AxisAlignedBB createAABB(EnumFacing facing, boolean isThin) {
		double min = 0.375;
		double max = 0.625;
		if (!isThin) {
			min = (1/16D)*5;
			max = (1/16D)*11;
		}
		return switch (facing) {
			case DOWN -> new AxisAlignedBB(min, 0.0, min, max, max, max);
			case UP -> new AxisAlignedBB(min, min, min, max, 1.0, max);
			case NORTH -> new AxisAlignedBB(min, min, 0.0, max, max, max);
			case SOUTH -> new AxisAlignedBB(min, min, min, max, max, 1.0);
			case EAST -> new AxisAlignedBB(min, min, min, 1.0, max, max);
			case WEST -> new AxisAlignedBB(0.0, min, min, max, max, max);
		};
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		super.onNeighborChange(world, pos, neighbor);
		if (world instanceof World realWorld && !realWorld.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityCable cable) {
				cable.scanEndpoints();
			}
		}
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollision(worldIn, pos, state, entityIn);
		if (state.getValue(TYPE).damage && entityIn instanceof EntityLivingBase) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityCable cable) {
				if (cable.isPowered()) {
					if (TechRebornConfig.misc.cable.electrocutionDamage) {
						if (state.getValue(TYPE) == CableEnum.HV) {
							entityIn.setFire(1);
						}
						entityIn.attackEntityFrom(new DamageSource("shock"), 1f);
					}
					if (TechRebornConfig.misc.cable.electrocutionSound) {
						worldIn.playSound(null, entityIn.posX, entityIn.posY, entityIn.posZ, ModSounds.CABLE_SHOCK, SoundCategory.BLOCKS, 0.6f, 1f);
					}
					if (TechRebornConfig.misc.cable.electrocutionParticles) {
						worldIn.spawnParticle(EnumParticleTypes.CRIT, entityIn.posX, entityIn.posY, entityIn.posZ, 0, 0, 0);
					}
				}
			}
		}
	}

	@Override
	public boolean hasResourceLocation() {
		return true;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Tags.MODID, "cable_inv");
	}

	@Override
	public String getName(ItemStack stack) {
		return CableEnum.META_MAP.get(stack.getMetadata()).getName();
	}

	public enum CableEnum implements IStringSerializable {
		COPPER(0, true),
		TIN(1, true),
		GOLD(2, true),
		HV(3, true),
		GLASSFIBER(4),
		INSULATEDCOPPER(5),
		INSULATEDGOLD(6),
		INSULATEDHV(7),
		SUPERCONDUCTOR(8);

		final int metadata;
		final boolean damage;

		static final Int2ObjectMap<CableEnum> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for(CableEnum e: values()) META_MAP.put(e.metadata, e);
			META_MAP.defaultReturnValue(COPPER);
		}

		CableEnum(int metadata) {
			this(metadata, false);
		}

		CableEnum(int metadata, boolean damage) {
			this.metadata = metadata;
			this.damage = damage;
		}

		public int metadata() {
			return metadata;
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}

		public static boolean isThin(CableEnum cable) {
			return cable == COPPER || cable == TIN || cable == GOLD || cable == HV || cable == GLASSFIBER;
		}
	}
}
