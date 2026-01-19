package com.sabrepenguin.techreborn.gui;

import com.sabrepenguin.techreborn.container.ContainerIronFurnace;
import com.sabrepenguin.techreborn.tileentity.tier0.TileEntityIronFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.jetbrains.annotations.Nullable;

public class GuiHandler implements IGuiHandler {
	@Override
	public @Nullable Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GUI.IRON_FURNACE.getId()) return new GuiIronFurnace(player.inventory, (TileEntityIronFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public @Nullable Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == GUI.IRON_FURNACE.getId()) return new ContainerIronFurnace(player.inventory, (TileEntityIronFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}
}
