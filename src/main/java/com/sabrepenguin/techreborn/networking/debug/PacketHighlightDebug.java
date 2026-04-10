package com.sabrepenguin.techreborn.networking.debug;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHighlightDebug implements IMessage {

	public PacketHighlightDebug() {}

	private BlockPos pos;
	private int index;
	private boolean linked;

	public PacketHighlightDebug(BlockPos pos, int index, boolean linked) {
		this.pos = pos;
		this.index = index;
		this.linked = linked;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = BlockPos.fromLong(buf.readLong());
		this.index = buf.readInt();
		this.linked = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.pos.toLong());
		buf.writeInt(this.index);
		buf.writeBoolean(this.linked);
	}

	public static class PacketHighlightDebugMessageHandler implements IMessageHandler<PacketHighlightDebug, IMessage> {

		@Override
		public IMessage onMessage(PacketHighlightDebug message, MessageContext ctx) {
			ClientDebugCache.update(message.pos, message.index, message.linked);
			return null;
		}
	}
}
