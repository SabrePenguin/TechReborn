package com.sabrepenguin.techreborn.proxy;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.armor.ItemCloak;
import com.sabrepenguin.techreborn.tileentity.multis.MultiblockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Tags.MODID, value = Side.CLIENT)
public class ClientEventHandler {
	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void renderPlayer(RenderPlayerEvent.Pre event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack chestSlot = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

		if (!chestSlot.isEmpty() && chestSlot.getItem() == TRItems.cloakingdevice) {
			if (ItemCloak.active(chestSlot))
				event.setCanceled(true);
		}
	}

	//TODO: Reorganize
	private static BlockPos pos;
	private static BlockPos upperBound;
	private static MultiblockStructure structure;
	private static final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

	public static void setRenderData(MultiblockStructure struct, BlockPos position) {
		structure = struct;
		pos = new BlockPos(
				position.getX() - struct.controllerOffset().getX(),
				position.getY() - struct.controllerOffset().getY(),
				position.getZ() - struct.controllerOffset().getZ());
		upperBound = new BlockPos(
				pos.getX() + structure.blocks()[0][0].length,
				pos.getY() + structure.blocks().length,
				pos.getZ() + structure.blocks()[0].length);
	}

	@SubscribeEvent
	public static void onRenderWorldLast(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.world == null || mc.player == null || pos == null || structure == null) return;

		EntityLivingBase viewer;
		if (mc.getRenderViewEntity() instanceof EntityLivingBase entityLivingBase) {
			viewer = entityLivingBase;
		} else {
			viewer = mc.player;
		}
		if (!viewer.world.isAreaLoaded(pos, upperBound)) {
			return;
		}

		int posX = pos.getX();
		int posY = pos.getY();
		int posZ = pos.getZ();

		float pt = event.getPartialTicks();
		double camX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * pt;
		double camY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * pt;
		double camZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * pt;

		double distance = Math.sqrt(Math.pow(camX - posX, 2) + Math.pow(camY - posY, 2) + Math.pow(camZ - posZ, 2));

		if (distance > 50) {
			resetRender();
			return;
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.translate(-camX, -camY, -camZ);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		BlockRendererDispatcher renderer = mc.getBlockRendererDispatcher();

		GlStateManager.pushMatrix();
		GlStateManager.translate(posX, posY, posZ);
		GlStateManager.color(1, 1, 1, 0.5f);

		boolean notFilled = false;
		for (int y = 0; y < structure.blocks().length; y++) {
			if (notFilled) {
				break;
			}
			for (int z = 0; z < structure.blocks()[0].length; z++) {
				for (int x = 0; x < structure.blocks()[0][0].length; x++) {
					IBlockState state = structure.palette().get(structure.blocks()[y][z][x]);
					mutableBlockPos.setPos(posX + x, posY + y, posZ + z);
					if (state != null) {
						if (viewer.world.getBlockState(mutableBlockPos) != state) {
							GlStateManager.pushMatrix();
							GlStateManager.translate(x + 0.125f, y + 0.125f, z + 1 - 0.125);
							GlStateManager.scale(0.75f, 0.75f, 0.75f);
							renderer.renderBlockBrightness(state, 1);
							GlStateManager.popMatrix();
							notFilled = true;
						}
					}
				}
			}
		}
		if (!notFilled) {
			resetRender();
		}

		GlStateManager.popMatrix();

		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	private static void resetRender() {
		structure = null;
		pos = null;
		upperBound = null;
	}
}
