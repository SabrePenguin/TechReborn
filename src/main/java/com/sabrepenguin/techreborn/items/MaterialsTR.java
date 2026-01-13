package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;


public class MaterialsTR {
    public static Item.ToolMaterial BRONZE_TOOL = EnumHelper.addToolMaterial("BRONZE", 2, 375, 6.0F, 2.25F, 8);
    public static Item.ToolMaterial RUBY_TOOL = EnumHelper.addToolMaterial("RUBY", 2, 320, 6.2F, 2.7F, 10);
    public static Item.ToolMaterial SAPPHIRE_TOOL = EnumHelper.addToolMaterial("SAPPHIRE", 2, 620, 5.0F, 2F, 8);
    public static Item.ToolMaterial PERIDOT_TOOL = EnumHelper.addToolMaterial("PERIDOT", 2, 400, 7.0F, 2.4F, 16);

    public static ItemArmor.ArmorMaterial BRONZE_ARMOR = EnumHelper.addArmorMaterial("BRONZE", Tags.MODID + ":bronze", 17, new int[] {2, 5, 6, 3}, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f);
    public static ItemArmor.ArmorMaterial RUBY_ARMOR = EnumHelper.addArmorMaterial("RUBY", Tags.MODID + ":ruby", 16, new int[] {2, 5, 7, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f);
    public static ItemArmor.ArmorMaterial SAPPHIRE_ARMOR = EnumHelper.addArmorMaterial("SAPPHIRE", Tags.MODID + ":sapphire", 19, new int[] {4, 4, 4, 4}, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f);
    public static ItemArmor.ArmorMaterial PERIDOT_ARMOR = EnumHelper.addArmorMaterial("PERIDOT", Tags.MODID + ":peridot", 17, new int[] {2, 3, 8, 3}, 16, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f);
    public static ItemArmor.ArmorMaterial CLOAKING_ARMOR = EnumHelper.addArmorMaterial("CLOAKING", Tags.MODID + ":cloaking", 5, new int[] {1, 2, 3, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f);
}
