package com.sabrepenguin.techreborn.gui;


import com.sabrepenguin.techreborn.container.ContainerIronFurnace;
import com.sabrepenguin.techreborn.tileentity.TileEntityIronFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiIronFurnace extends GuiContainer {
	private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");
	private final InventoryPlayer player;
	private final TileEntityIronFurnace furnace;

	public GuiIronFurnace(InventoryPlayer player, TileEntityIronFurnace furnace) {
		super(new ContainerIronFurnace(player, furnace));

		this.player = player;
		this.furnace = furnace;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String tileName = this.furnace.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2), 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		drawDefaultBackground();
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		if (TileEntityIronFurnace.isBurning(furnace)) {
			int k = this.getBurnLeftScaled(13);
			drawTexturedModalRect(guiLeft + 56, guiTop + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		int l = this.getCookProgressScaled(24);
		drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, l + 1, 16);
	}

	private int getBurnLeftScaled(int pixels) {
		int i = this.furnace.getCurrentBurnTime();
		if (i == 0) i = 200;
		return this.furnace.getBurnTime() * pixels / i;
	}

	private int getCookProgressScaled(int pixels) {
		int i = this.furnace.getCookTime();
		int j = this.furnace.getTotalCookTime();
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
