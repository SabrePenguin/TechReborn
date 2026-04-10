package com.sabrepenguin.techreborn.tileentity.cable;

import com.sabrepenguin.techreborn.networking.TechRebornPacketHandler;
import com.sabrepenguin.techreborn.networking.debug.ClientDebugCache;
import com.sabrepenguin.techreborn.networking.debug.PacketServerHighlightDebug;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class HandlerCable {
	@SubscribeEvent
	public static void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
		RayTraceResult result = event.getTarget();
		World world = event.getPlayer().world;
		if (!world.isRemote)
			return;
		if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos target = result.getBlockPos();
			TileEntity te = world.getTileEntity(target);
			if (!(te instanceof TileEntityCable))
				return;
			Set<BlockPos> positions = new HashSet<>();
			positions.add(target);
			for (EnumFacing facing: EnumFacing.values()) {
				BlockPos offset = target.offset(facing);
				if (!world.isBlockLoaded(offset))
					continue;
				Boolean linked = ClientDebugCache.isLinked(target, facing);
				if (linked == null) {
					TechRebornPacketHandler.INSTANCE.sendToServer(new PacketServerHighlightDebug(target, facing));
				} else if (linked) {
					positions.add(offset);
				}
			}
			renderLinkedHighlights(positions);
		}
	}

	private static void renderLinkedHighlights(Collection<BlockPos> positions) {
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();

		double x = rm.viewerPosX;
		double y = rm.viewerPosY;
		double z = rm.viewerPosZ;

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.disableTexture2D();
		GlStateManager.glLineWidth(6.0f);

		for (BlockPos pos: positions) {
			AxisAlignedBB box = new AxisAlignedBB(pos).offset(-x, -y, -z);
			RenderGlobal.drawSelectionBoundingBox(box, 1.0f, 0.0f, 0.0f, 0.4f);
		}

		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
}
