package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

public class TRSword extends ItemSword implements INonStandardLocation {
    private final String repairOre;
    public TRSword(ToolMaterial material, String repairOre) {
        super(material);
        this.setRegistryName(Tags.MODID, material.name().toLowerCase() + "sword");
        this.repairOre = repairOre;
        this.setTranslationKey(material.name().toLowerCase() + "Sword");
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
