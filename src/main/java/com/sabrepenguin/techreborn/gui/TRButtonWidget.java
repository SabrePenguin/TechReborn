package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.widgets.ButtonWidget;

public class TRButtonWidget<W extends ButtonWidget<W>> extends ButtonWidget<W> {
	@Override
	public boolean canClickThrough() {
		return false;
	}
}
