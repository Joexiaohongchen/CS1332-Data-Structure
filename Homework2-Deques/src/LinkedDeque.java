import java.util.NoSuchElementException;

/**
 * Your implementation of a linked deque.
 *
 * @author Xiaohong Chen
 * @userid xchen671
 * @GTID 903392332
 * @version 1.0
 */
public class LinkedDeque<T> {
    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        head = new LinkedNode<T>(data, null, head);
        if (head.getNext() != null) {
            head.getNext().setPrevious(head);
        }
        if (size == 0) {
            tail = head;
        }
        size++;
    }

    /**
     * Adds the data to the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        LinkedNode<T> newCur =
                new LinkedNode<T>(data, tail, null);
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (size == 0) {
            head = newCur;
            tail = newCur;
            newCur.setPrevious(null);
        } else {
            tail.setNext(newCur);
            tail = tail.getNext();
        }
        tail = newCur;
        size++;
    }

    /**
     * Removes the data at the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty"
                    + " There is no element can be removed");
        } else if (head == tail) {
            T element = head.getData();
            head = null;
            tail = null;
            size--;
            return element;
        } else {
            head.getNext().setPrevious(null);
        }
        T element = head.getData();
        head = head.getNext();
        size--;
        return element;
    }

    /**
     * Removes the data at the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty"
                    + " There is no element can be removed");
        } else if (head == tail) {
            T element = head.getData();
            head = null;
            tail = null;
            size--;
            return element;
        } else {
            tail.getPrevious().setNext(null);
        }
        LinkedNode<T> temp = tail;
        tail = tail.getPrevious();
        temp.setPrevious(null);
        size--;
        return temp.getData();
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
     * Returns the head node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}