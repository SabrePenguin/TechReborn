package com.sabrepenguin.techreborn.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

public class ChatUtils {
	public static final int ACTIVE_TOOL = 0;

	private static final int OFFSET = 8929;

	public static void sendUpdatingMessage(ITextComponent message, int id) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(message, OFFSET + id);
	}
}
