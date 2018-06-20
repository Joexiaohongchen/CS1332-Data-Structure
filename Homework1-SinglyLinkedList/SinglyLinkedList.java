/**
 * Your implementation of a non-circular singly linked list with a tail pointer.
 *
 * @author Xiaohong Chen
 * @userid xchen671
 * @GTID 903392332
 * @version 1.0
 */
public class SinglyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;
    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        } else if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index should less than size"
                    + "and greater than 0");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            LinkedListNode<T> curNode = head;
            for (int i = 0; i < index - 1; i++) {
                curNode = curNode.getNext();
            }
            LinkedListNode<T> newNode = new LinkedListNode<T>(data,
                    curNode.getNext());
            curNode.setNext(newNode);
            size++;
        }
    }
    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        head = new LinkedListNode<>(data, head);
        if (size == 0) {
            tail = head;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToBack(T data) {
        LinkedListNode<T> newNode = new LinkedListNode<T>(data, null);
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        } else if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index should be greater than"
                    + "0 and less than size");
        } else if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return  removeFromBack();
        } else {
            LinkedListNode<T> curNode = head;
            for (int i = 0; i < index - 1; i++) {
                curNode = curNode.getNext();
            }
            T element = curNode.getNext().getData();
            curNode.setNext(curNode.getNext().getNext());
            size--;
            return element;
        }
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        }
        T element = head.getData();
        head = head.getNext();
        size--;
        if (size == 0) {
            tail = null;
        }
        return element;
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        } else if (size == 1) {
            T removedElement = head.getData();
            head = null;
            tail = null;
            size--;
            return removedElement;
        }
        LinkedListNode<T> curNode = head;
        LinkedListNode<T> prevNode = null;
        while (curNode.getNext() != null) {
            prevNode = curNode;
            curNode = curNode.getNext();
        }
        T element = curNode.getData();
        prevNode.setNext(curNode.getNext());
        tail = prevNode;
        size--;
        return element;
    }

    /**
     * Returns the index of the first occurrence of the passed in data in the
     * list or -1 if it is not in the list.
     *
     * If data is in the head, should be O(1). In all other cases, O(n).
     *
     * @param data the data to search for
     * @throws java.lang.IllegalArgumentException if data is null
     * @return the index of the first occurrence or -1 if not in the list
     */
    public int indexOf(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        } else if (isEmpty()) {
            return -1;
        } else if (data.equals(head.getData())) {
            return 0;
        }
        int count = 0;
        LinkedListNode<T> curNode = head;
        for (int i = 0; i < size - 1; i++) {
            curNode = curNode.getNext();
            count++;
            if (data.equals(curNode.getData())) {
                return count;
            }
        }
        return -1;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting the head and tail should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        LinkedListNode<T> curNode = head;
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index should be between the"
                    + "size");
        } else if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        }
        for (int i = 0; i < index; i++) {
            curNode = curNode.getNext();
        }
        return curNode.getData();
    }
    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order
     */
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        LinkedListNode<T> curNode = head;
        for (int i = 0; i < size; i++) {
            arr[i] = (Comparable) curNode.getData();
            curNode = curNode.getNext();
        }
        return arr;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list of all data and resets the size.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * Runs in O(1) for all cases.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked list
     */
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    public LinkedListNode<T> recursiveRemoveDuplicates(LinkedListNode<T> curNode) {
        if (curNode == null) {
            return null;
        } else {
            curNode.setNext(recursiveRemoveDuplicates(curNode.getNext()));
            if (curNode.getNext() != null && curNode.getData() == curNode.getNext().getData()) {
                size--;
                return curNode;
            }
        }
        return  curNode;
    }

    public LinkedListNode<T> recursiveRemove(LinkedListNode<T> curNode, T data) {
        if (curNode == null) {
            return null;
        } else if (curNode.getData() == data) {
                return curNode.getNext();
        } else {
            curNode.setNext(recursiveRemove(curNode.getNext(), data));
        }
        return curNode;
    }
}