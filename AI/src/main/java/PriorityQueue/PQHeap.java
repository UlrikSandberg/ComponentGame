/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PriorityQueue;

import ai.Node;

/**
 *
 * @author oskar
 */
public class PQHeap {
    private Node[] nodes; //The array containing the heap
    private int heapSize; //How many elements are currently in the heap (indexes beyond this size are null)

    //Constructor creates the array to the specified size
    public PQHeap(int size){
        this.nodes = new Node[size];
        heapSize = -1; //Starts at -1, as the insert increases by one, and it has to start at zero
    }

    public boolean isEmpty(){
        if (this.heapSize < 0) return true;
        return false;
    }

    public int getHeapSize(){
        return this.heapSize + 1;
    }



    /**
     * Extracts first element in the heap (the root which is the smallest element)
     * Sets the first element to the furthest leaf. This is to maintain the min heap structure,
     * which is as an almost complete binary tree (that is, each node in the hierarchy is filled,
     * except perhaps the leafs on the last level.
     * Sets the index for the last element, now in the first position, to null (no longer part
     * of the heap).
     * decreases heapSize by one.
     * Calls heapify on the first element (which is in the wrong position, as it was taken from the
     * end of the heap) to maintain the heap structure
     *
     * @return the extracted first element, which is the smallest in the heap.
     */

    public Node extractMin() {
        Node min = nodes[0];
        nodes[0] = nodes[heapSize];
        nodes[heapSize] = null;
        heapSize--;
        minHeapify(0);
        return min;
    }

    public void remove(Node node){
        Node toBeRemoved = node;
        for(int i = 0; i <= this.heapSize; i++){
            if(toBeRemoved == this.nodes[i]){
                this.nodes[i] = this.nodes[this.heapSize];
                this.nodes[this.heapSize] = null;
                heapSize--;
                minHeapify(i);
            }
        }
    }


    public boolean contains(Node node){
        for(int i = 0; i <= this.heapSize; i++){
            if(this.nodes[i] == node) return true;
        }
        return false;
    }

    /**
     * Increases the heapsize (not the same as array.length), which is the amount of actual elements in the heap -1,
     * (as the heap starts at index 0).
     * Sets the array[heapSize] to the element that is to be inserted.
     * Continuously exchanges the inserted value with it parent, if the parent key value
     * is larger than the inserted Element.
     * @param e: Element to be inserted in the heap
     */

    public void insert(Node e) {
        heapSize++;
        int i = heapSize;
        nodes[i] = e;
        while(parent(i) != i && nodes[parent(i)].getFinalCost() > nodes[i].getFinalCost()){
            exchange(i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Evaluates whether the left child and right child keys are smaller than the element
     * being heapified. If it is, it is exchanged with the smaller one, and heapify is called
     * recursively. The base case (IE, when the recursion ends), is when the elements that
     * is being heapified keys is neither bigger than its right or left child.
     * @param i: Which index in the heap that is to be heapified
     */
    public void minHeapify(int i){
        int left = left(i);
        int right = right(i);
        int smallest = -1;

        if (left <= heapSize && nodes[left].getFinalCost() < nodes[i].getFinalCost()) {
            smallest = left;
        } else {
            smallest = i;
        }

        if (right <= heapSize && nodes[right].getFinalCost() < nodes[smallest].getFinalCost()) {
            smallest = right;
        }
        if (smallest != i) {

            exchange(i, smallest);
            minHeapify(smallest);
        }
    }

    private void exchange(int index_1, int index_2){
        Node temp = nodes[index_1];
        nodes[index_1] = nodes[index_2];
        nodes[index_2] = temp;
    }

    /**
     * Checks if i % 2 == 1, as the array starts at 0.
     * Example: 0 has children 1 and 2. 1 has children 3 and 4.
     * 3 % 2 = 1 -> return 3/2 = (int)1.5 = 1
     * 4 % 2 = 2 -> return (4 - 1) / 2 = (int)1.5 = 1
     * This also shows how the heap is a almost complete binary tree,
     * as every spot smaller or equal to heapsize, is filled.
     * @param i: which position parent is to be found
     * @return the parent index in the heap
     */
    private int parent(int i){
        if (i % 2 == 1) {
            return i / 2;
        }

        return (i - 1) / 2;
    }

    /**
     * As per the example described above in the parent method, gives the position
     * of a parents left child.
     * @param i: which positions left child is to be found.
     * @return i's lefts child index.
     */
    private int left(int i){
        return 2 * i + 1;
    }
    /**
     * As per the example described above in the parent method, gives the position
     * of a parents right child.
     * @param i: which positions right child is to be found.
     * @return i's rights child index.
     */
    private int right(int i){
        return 2 * i + 2;
    }

}
