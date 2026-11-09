package com.sabrepenguin.techreborn.capability;

import com.sabrepenguin.techreborn.capability.stackhandler.SlotAction;

public interface ISlotChange {
    boolean rotateSlot(int handlerIndex, int slot);
    SlotAction getSlotAction(int slot);
    SlotAction getHandlerAction(int slot);
}
