package com.example.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Bag<E> implements Container<E> {

    private final ArrayList<E> items;

    public Bag() {
        this.items = new ArrayList<>();
    }

    public Bag(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Initial capacity must be non-negative: " + initialCapacity);
        this.items = new ArrayList<>(initialCapacity);
    }

    @Override
    public void add(E item) {
        items.add(item);
    }

    @Override
    public boolean remove(E item) {
        return items.remove(item);
    }

    @Override
    public boolean contains(E item) {
        return items.contains(item);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return items.iterator();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action, "Action must not be null");
        items.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return items.spliterator();
    }
}