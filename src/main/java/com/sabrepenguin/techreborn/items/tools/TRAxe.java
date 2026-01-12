package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

public class TRAxe extends ItemAxe implements INonStandardLocation {
    private String repairOre;
    public TRAxe(ToolMaterial material, String repairOre) {
        super(material, material.getAttackDamage() + 5.75F, (material.getAttackDamage() + 6.75F) * -0.344444F);
        this.setRegistryName(Tags.MODID, material.name().toLowerCase() + "axe");
        this.repairOre = repairOre;
        this.setTranslationKey(material.name().toLowerCase() + "Axe");
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
