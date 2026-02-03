package com.sabrepenguin.techreborn.blocks.machines;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.gui.GUI;
import com.sabrepenguin.techreborn.tileentity.ISetWorldNameable;
import com.sabrepenguin.techreborn.tileentity.tier0.TileEntityIronFurnace;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IronFurnace extends Block implements INonStandardLocation {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool BURNING = PropertyBool.create("active");

    public IronFurnace() {
        super(Material.IRON);
		setCreativeTab(TechReborn.RESOURCE_TAB);
        setRegistryName(Tags.MODID, "iron_furnace");
        setTranslationKey(Tags.MODID + ".iron_furnace");
        setHardness(2.0f);
        setSoundType(SoundType.METAL);
        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BURNING);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(TechReborn.instance, GUI.IRON_FURNACE.getId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote)
        {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());
            IBlockState east = worldIn.getBlockState(pos.east());
            EnumFacing face = state.getValue(FACING);

            if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock())
            {
                face = EnumFacing.SOUTH;
            }
            else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock())
            {
                face = EnumFacing.NORTH;
            }
            else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock())
            {
                face = EnumFacing.EAST;
            }
            else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock())
            {
                face = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
        }
    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (active) {
            worldIn.setBlockState(pos, TRBlocks.iron_furnace.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, true), 3);
        } else {
            worldIn.setBlockState(pos, TRBlocks.iron_furnace.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, false), 3);
        }
        if (tileEntity != null) {
            tileEntity.validate();
            worldIn.setTileEntity(pos, tileEntity);
        }
    }

    @Override
    public @Nullable TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityIronFurnace();
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		if (stack.hasDisplayName()) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof ISetWorldNameable named) {
				named.setCustomName(stack.getDisplayName());
			}
		}
    }

    @Override
    public void breakBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
            InventoryUtils.spawnDroppedItems(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), worldIn, pos);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public @NotNull IBlockState withRotation(@NotNull IBlockState state, @NotNull Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        return super.rotateBlock(world, pos, axis);
    }


    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.byIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public String getPrefix() {
        return "machines/tier0/";
    }

    @Override
    public IProperty<?>[] getProperties() {
        return new IProperty[] {FACING, BURNING};
    }

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}
