package com.project1;

import java.util.ArrayList;

public class MinHeap {
    private final ArrayList<Integer> heap;

    public MinHeap() {
        heap = new ArrayList<Integer>();
    }

    /**
     * inserts an int into the heap in the correct spot in the ArrayList
     * @param value the int to be inserted
     */
    public void insert(int value) {
        // TODO
        return;
    }

    /** 
     * removes the minimum integer. this function should run in O(logn), where n is the number of values in the min heap.
     * @return the minimum integer in the ArrayList
     */
    public Integer extractMin() {
        // TODO
        return 0;
    }

    /**
     * takes an index and shifts the element at that index up the heap until it is 
     * in the correct position, i.e. both of its children are greater than it
     * @param index
     */
    private void siftUp(int index) {
        // TODO
        return;
    }

    /**
     * takes an index and shifts the element at that index down the heap until it is 
     * in the correct position, i.e. both of its children are greater than it
     * @param index
     */
    private void siftDown(int index) {
        // TODO
        return;
    }

    /**
     * returns the integer at the top of the heap without removing it
     * @return the integer at the top of the heap
     */
    public Integer peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    /**
     * Checks if the ArrayList is a min heap, i.e. for every index i, the children 
     * at index 2 * i and 2 * i + 1 are greater than or equal to the value at index i.
     * @return true if the ArrayList heap is actually a min heap, else false
     */
    public boolean isMinHeap() {
        // not required but recommended for testing
        return true;
    }
    
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
