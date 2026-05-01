package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

//TODO: Add TR's useless coordinate tracking
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemFrequencyTransmitter extends Item implements INonStandardLocation {
	public ItemFrequencyTransmitter() {
		ItemHelper.registerUnstackable(this, "frequencytransmitter");

		this.addPropertyOverride(new ResourceLocation("techreborn:coords"), (stack, worldIn, entityIn) ->  {
			if (!stack.isEmpty() && stack.hasTagCompound()) {
				if (stack.getTagCompound().hasKey("x") && stack.getTagCompound().hasKey("y")
				&& stack.getTagCompound().hasKey("z") && stack.getTagCompound().hasKey("dim"))
					return 1;
			}
			return 0;
		});
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Tech Reborn has no use for this item. The coordinate storage will be added... eventually");
	}

	@Override
	public String getPrefix() {
		return "tool";
	}

	@Override
	public boolean hasResourceLocation() {
		return true;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Tags.MODID, "frequency_transmitter");
	}
}
