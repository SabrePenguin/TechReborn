package com.sabrepenguin.techreborn.networking.debug;

import com.sabrepenguin.techreborn.networking.TechRebornPacketHandler;
import com.sabrepenguin.techreborn.tileentity.cable.TileEntityCable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerHighlightDebug implements IMessage {
	private BlockPos origin;
	private BlockPos neighbor;
	private int index;

	public PacketServerHighlightDebug() {}
	public PacketServerHighlightDebug(BlockPos origin, EnumFacing facing) {
		this.origin = origin;
		this.neighbor = origin.offset(facing);
		this.index = facing.getIndex();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.origin = BlockPos.fromLong(buf.readLong());
		this.neighbor = BlockPos.fromLong(buf.readLong());
		this.index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.origin.toLong());
		buf.writeLong(this.neighbor.toLong());
		buf.writeInt(this.index);
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
						boolean isLinked = cable.isNeighborInNetwork(message.neighbor);
						TechRebornPacketHandler.INSTANCE.sendTo(
								new PacketHighlightDebug(message.origin, message.index, isLinked),
								player
						);
					}
				}
			});
			return null;
		}
	}
}