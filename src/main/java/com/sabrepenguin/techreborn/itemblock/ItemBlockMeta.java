package com.sabrepenguin.techreborn.itemblock;

import com.sabrepenguin.techreborn.blocks.BlockBase;
import com.sabrepenguin.techreborn.blocks.IMetaBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemBlockMeta extends ItemBlock {
    public ItemBlockMeta(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public @NotNull String getTranslationKey(@NotNull ItemStack stack) {
        if (this.block instanceof IMetaBlock base) {
            int meta = stack.getMetadata();
            if (meta < 0 || meta >= base.getListMetadata().size())
                meta = 0;
            return this.block.getTranslationKey() + "." + base.getListMetadata().get(meta).name();
        }
        return super.getTranslationKey(stack);
    }
}
