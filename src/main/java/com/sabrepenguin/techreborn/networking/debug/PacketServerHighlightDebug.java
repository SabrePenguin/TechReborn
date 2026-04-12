package com.sabrepenguin.techreborn.networking.debug;

import com.sabrepenguin.techreborn.networking.TechRebornPacketHandler;
import com.sabrepenguin.techreborn.tileentity.cable.TileEntityCable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Set;

public class PacketServerHighlightDebug implements IMessage {
	private BlockPos origin;

	public PacketServerHighlightDebug() {}

	public PacketServerHighlightDebug(BlockPos origin) {
		this.origin = origin;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.origin = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.origin.toLong());
	}

	public static class PacketServerHandler implements IMessageHandler<PacketServerHighlightDebug, IMessage> {
		@Override
		public IMessage onMessage(PacketServerHighlightDebug message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
				EntityPlayerMP player = ctx.getServerHandler().player;
				WorldServer world = player.getServerWorld();
				if (player.getDistanceSq(message.origin) < 64.0D) {
					TileEntity te = world.getTileEntity(message.origin);
					if (te instanceof TileEntityCable cable) {
						Set<BlockPos> cables = cable.getNetwork().getCables();
						TechRebornPacketHandler.INSTANCE.sendTo(new PacketHighlightDebug(cables), player);
					}
				}
			});
			return null;
		}
	}
}