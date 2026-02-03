package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ItemWrench extends Item implements INonStandardLocation {
    public ItemWrench() {
        super();
        this.setRegistryName(Tags.MODID, "wrench");
        this.setTranslationKey(Tags.MODID + ".wrench");
        this.setFull3D();
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
    }

    @Override
    public String getPrefix() {
        return "tool/";
    }

    @Override
    public @NotNull EnumActionResult onItemUseFirst(@NotNull EntityPlayer player, World world, @NotNull BlockPos pos, @NotNull EnumFacing side, float hitX, float hitY, float hitZ, @NotNull EnumHand hand) {
        final Block b = world.getBlockState(pos).getBlock();
        if (b != null && !player.isSneaking()) {
            if (world.isRemote) {
                return EnumActionResult.SUCCESS;
            }
            return b.rotateBlock(world, pos, side) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
        }
        return EnumActionResult.PASS;
    }
}
