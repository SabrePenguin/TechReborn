package com.sabrepenguin.techreborn.networking.debug;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashSet;
import java.util.Set;

public class PacketHighlightDebug implements IMessage {

	public PacketHighlightDebug() {}

	private Set<BlockPos> positions;
	private int len;

	public PacketHighlightDebug(Set<BlockPos> positions) {
		this.positions = positions;
		this.len = positions.size();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.len = buf.readInt();
		this.positions = new HashSet<>();
		for (int i = 0; i < len; i++) {
			positions.add(BlockPos.fromLong(buf.readLong()));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.len);
		for (BlockPos pos: this.positions) {
			buf.writeLong(pos.toLong());
		}
	}

	public static class PacketHighlightDebugMessageHandler implements IMessageHandler<PacketHighlightDebug, IMessage> {

		@Override
		public IMessage onMessage(PacketHighlightDebug message, MessageContext ctx) {
			ClientDebugCache.update(message.positions);
			return null;
		}
	}
}
