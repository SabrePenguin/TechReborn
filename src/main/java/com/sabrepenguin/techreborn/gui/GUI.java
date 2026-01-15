package com.sabrepenguin.techreborn.gui;

public enum GUI {
    IRON_FURNACE(0);

    public int id;
    GUI(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
