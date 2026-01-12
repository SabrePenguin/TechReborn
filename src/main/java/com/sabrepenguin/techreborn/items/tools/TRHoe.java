package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

public class TRHoe extends ItemHoe implements INonStandardLocation {
    private String repairOre;
    public TRHoe(ToolMaterial material, String repairOre) {
        super(material);
        this.setRegistryName(Tags.MODID, material.name().toLowerCase() + "hoe");
        this.repairOre = repairOre;
        this.setTranslationKey(material.name().toLowerCase() + "Hoe");
        setCreativeTab(TechReborn.RESOURCE_TAB);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, @NotNull ItemStack repair) {
        if (toRepair.getItem() == this && !repair.isEmpty()) {
            for (int id: OreDictionary.getOreIDs(repair)) {
                if (OreDictionary.getOreName(id).equals(repairOre)) {
                    return true;
                }
            }
        }
        return super.getIsRepairable(toRepair, repair);
    }

    @Override
    public String getPrefix() {
        return "tool/";
    }
}
