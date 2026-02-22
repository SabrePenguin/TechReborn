package com.sabrepenguin.techreborn.capability.stackhandler;

public enum SlotAction {
	DISABLED(0),
	INPUT(1),
	OUTPUT(2),
	BIDIRECTIONAL(3);

	final byte index;

	SlotAction(int index) {
		this.index = (byte) index;
	}

	public static SlotAction getByIndex(byte index) {
		return switch (index) {
			case 0 -> DISABLED;
			case 1 -> INPUT;
			case 2 -> OUTPUT;
			default -> throw new IllegalStateException("Unexpected value: " + index);
		};
	}

	public byte getByte() {
		return this.index;
	}

	/**
	 * Rotates the passed in SlotAction based on what this object is.
	 * <br>
	 * WARNING: THIS SHOULD BE CALLED ON HANDLERS THAT DO NOT CHANGE. DISABLED IS NOT MEANT TO BE CALLED ON.
	 * @param action The *target* to change.
	 * @return The rotated action
	 */
	public SlotAction rotate(SlotAction action) {
		if (this == INPUT) {
			return action == INPUT ? DISABLED : INPUT;
		} else if (this == OUTPUT) {
			return action == OUTPUT ? DISABLED : OUTPUT;
		} else if (this == BIDIRECTIONAL){
			if (action == INPUT) {
				return OUTPUT;
			} else if (action == OUTPUT) {
				return DISABLED;
			}
			return INPUT;
		}
		throw new IllegalArgumentException("SlotAction DISABLED was the action passed in as the base state for rotation.");
	}
}