package com.sabrepenguin.techreborn.util;

@FunctionalInterface
public interface ObjIntPredicate<T> {
	boolean test(T t, int value);
}