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

public class PacketAutoConfig implements IMessage {

	public PacketAutoConfig() {}

	private BlockPos pos;
	private int index;
	private boolean input;

	public PacketAutoConfig(BlockPos pos, int index, boolean input) {
		this.pos = pos;
		this.index = index;
		this.input = input;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = BlockPos.fromLong(buf.readLong());
		this.index = buf.readInt();
		this.input = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.pos.toLong());
		buf.writeInt(this.index);
		buf.writeBoolean(this.input);
	}

	public static class PacketAutoConfigMessageHandler implements IMessageHandler<PacketAutoConfig, IMessage> {

		@Override
		public IMessage onMessage(PacketAutoConfig message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			WorldServer world = player.getServerWorld();
			world.addScheduledTask(() -> {
				if (world.isBlockLoaded(message.pos)) {
					TileEntity te = world.getTileEntity(message.pos);
					if (te instanceof ISideConfigTE sidedConfig) {
						sidedConfig.swapSlot(message.index, message.input);
					}
				}
			});

			return null;
		}
	}
}
