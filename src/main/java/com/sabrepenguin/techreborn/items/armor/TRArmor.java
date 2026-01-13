package com.sabrepenguin.techreborn.items.armor;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class TRArmor extends ItemArmor implements INonStandardLocation {

    private final String repairOre;
    public TRArmor(String registry, ArmorMaterial material, EntityEquipmentSlot equipmentSlot, String repairOre) {
        super(material, 0, equipmentSlot);
        String name = switch (equipmentSlot) {
            case FEET -> "boots";
            case HEAD -> "helmet";
            case LEGS -> "leggings";
            case CHEST -> "chestplate";
            default -> "";
        };
        this.setRegistryName(Tags.MODID, registry.toLowerCase() + name);
        this.setTranslationKey(registry.toLowerCase() + StringUtils.capitalize(name));
        this.repairOre = repairOre;
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
