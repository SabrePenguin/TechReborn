package com.sabrepenguin.techreborn.util;

public interface INonStandardLocation {
    default String getPrefix() {
        return "";
    }
    default String getPostfix() {
        return "";
    }
}
