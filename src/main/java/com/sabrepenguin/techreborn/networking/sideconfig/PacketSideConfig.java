package com.sabrepenguin.techreborn.networking.sideconfig;

import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSideConfig implements IMessage {

	public PacketSideConfig() {}

	private BlockPos pos;
	private int side;
	private int handlerIndex;
	private int handlerSlot;

	public PacketSideConfig(BlockPos pos, int side, int handlerIndex, int handlerSlot) {
		this.pos = pos;
		this.side = side;
		this.handlerIndex = handlerIndex;
		this.handlerSlot = handlerSlot;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = BlockPos.fromLong(buf.readLong());
		this.side = buf.readInt();
		this.handlerIndex = buf.readInt();
		this.handlerSlot = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.pos.toLong());
		buf.writeInt(this.side);
		buf.writeInt(this.handlerIndex);
		buf.writeInt(this.handlerSlot);
	}

	public static class PacketSideConfigMessageHandler implements IMessageHandler<PacketSideConfig, IMessage> {

		@Override
		public IMessage onMessage(PacketSideConfig message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			WorldServer world = player.getServerWorld();
			world.addScheduledTask(() -> {
				if (world.isBlockLoaded(message.pos)) {
					TileEntity te = world.getTileEntity(message.pos);
					if (te instanceof ISideConfigTE sidedConfig) {
						sidedConfig.rotateSlot(message.side, message.handlerIndex, message.handlerSlot);
					}
				}
			});

			return null;
		}
	}
}
