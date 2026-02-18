package com.sabrepenguin.techreborn.gui;

import com.github.bsideup.jabel.Desugar;
import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;

@Desugar
public record SlotPosition(SlotAction action, int x, int y, int handlerSlot, int slot) {
}
