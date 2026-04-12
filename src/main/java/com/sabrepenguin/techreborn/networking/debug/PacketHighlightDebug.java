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
	private int positionLen;
	private Set<BlockPos> endpoints;
	private int endpointLen;

	public PacketHighlightDebug(Set<BlockPos> positions, Set<BlockPos> endpoints) {
		this.positions = positions;
		this.positionLen = positions.size();
		this.endpoints = endpoints;
		this.endpointLen = endpoints.size();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.positionLen = buf.readInt();
		this.positions = new HashSet<>();
		for (int i = 0; i < positionLen; i++) {
			positions.add(BlockPos.fromLong(buf.readLong()));
		}
		this.endpointLen = buf.readInt();
		this.endpoints = new HashSet<>();
		for (int i = 0; i < endpointLen; i++) {
			endpoints.add(BlockPos.fromLong(buf.readLong()));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.positionLen);
		for (BlockPos pos: this.positions) {
			buf.writeLong(pos.toLong());
		}
		buf.writeInt(this.endpointLen);
		for (BlockPos pos: this.endpoints) {
			buf.writeLong(pos.toLong());
		}
	}

	public static class PacketHighlightDebugMessageHandler implements IMessageHandler<PacketHighlightDebug, IMessage> {

		@Override
		public IMessage onMessage(PacketHighlightDebug message, MessageContext ctx) {
			ClientDebugCache.update(message.positions, message.endpoints);
			return null;
		}
	}
}
