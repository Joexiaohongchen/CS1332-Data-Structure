import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of a binary search tree.
 *
 * @author Xiaohong Chen
 * @userid xchen671
 * @GTID 903392332
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            for (T element : data) {
                add(element);
            }
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            root = add(data, root);
        }
    }

    /**
     * add helper method
     * @param data the data added
     * @param node the root of the tree
     *
     * @return node the node was added
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        if (node == null) {
            node = new BSTNode<>(data);
            size++;
            return node;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                node.setLeft(add(data, node.getLeft()));
            } else if (data.compareTo(node.getData()) == 0) {
                return node;
            } else {
                node.setRight(add(data, node.getRight()));
            }
        }
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else {
            BSTNode<T> dummy = new BSTNode<T>(data);
            root = remove(root, dummy, data);
            size--;
            return dummy.getData();
        }
    }

    /**
     * There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * @param data the data to be searched
     * @param node the node that traversal of the tree
     * @param dummy the node that store the value
     * @return the node removed from the tree.
     */

    private BSTNode<T> remove(BSTNode<T> node, BSTNode<T> dummy, T data) {
        if (node == null) {
            throw NoSuchElementException("Data is not exist at this tree.");
        } else if (data.equals(node.getData())) {
            dummy.setData(node.getData());
            if (node.getRight() == null && node.getLeft() == null) {
                return null;
            } else if (node.getRight() == null && node.getLeft() != null) {
                return node.getLeft();
            } else if (node.getRight() != null && node.getLeft() == null) {
                return node.getRight();
            } else {
                BSTNode<T> sucDummy = new BSTNode<>(data);
                node.setRight(successor(node.getRight(), sucDummy));
                node.setData(sucDummy.getData());
            }
        } else {
            if (data.compareTo(node.getData()) < 0) {
                node.setLeft(remove(node.getLeft(), dummy, data));
            } else {
                node.setRight(remove(node.getRight(), dummy, data));
            }
        }
        return node;
    }

    /**
     * successor is the smallest node that greater than the input value
     * @param sucDummy the node that store the node's data
     * @param node the node that traversal of the tree
     *
     * @return node the smalled value stored at node
     */
    private BSTNode<T> successor(BSTNode<T> node, BSTNode<T> sucDummy) {
        if (node.getLeft() == null) {
            sucDummy.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(successor(node.getLeft(), sucDummy));
        }
        return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        return get(data, root).getData();
    }

    /**
     * get's helper method.
     *
     * @param data the data to be searched
     * @param node the node that traversal of the tree
     * @throws java.util.NoSuchElementException if the data is not found
     * @return node that expected data stored in the node
     */
    private BSTNode<T> get(T data, BSTNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("The data is not found in tree");
        } else if (data.compareTo(node.getData()) < 0) {
            return get(data, node.getLeft());
        } else if (data.compareTo(node.getData()) > 0) {
            return get(data, node.getRight());
        } else {
            return node;
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        } else if (root == null) {
            return false;
        } else {
            return search(data, root);
        }
    }

    /**
     * search the data exist at node or not
     * @param data the data to be searched
     * @param node the node that traversal of the tree
     *
     * @return false if there is no such data else return true
     */
    private boolean search(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        } else {
            if (data.equals(node.getData())) {
                return true;
            } else if (data.compareTo(node.getData()) < 0) {
                return search(data, node.getLeft());
            } else {
                return search(data, node.getRight());
            }
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        preOrder(root, list);
        return  list;
    }

    /**
     * preOrder traversal of the tree
     * @param node a node that would be through the tree
     * @param list node's data added to list
     */
    private void preOrder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preOrder(node.getLeft(), list);
            preOrder(node.getRight(), list);
        }
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        inOrder(root, list);
        return list;
    }

    /**
     * inOrder traversal of the tree
     * @param node a node that would be through the tree
     * @param list node's data added to list
     */
    private void inOrder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inOrder(node.getLeft(), list);
            list.add(node.getData());
            inOrder(node.getRight(), list);
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        postOrder(root, list);
        return list;
    }

    /**
     * postorder traversal of the tree
     * @param node a node that would be through the tree
     * @param list node's data added to list
     */
    private void postOrder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postOrder(node.getLeft(), list);
            postOrder(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        if (root == null) {//if it is the empty tree, then return empty tree.
            return new LinkedList<>();
        }
        List<T> levels = new LinkedList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> node = q.remove();
            levels.add(node.getData());
            if (node.getLeft() != null) {
                q.add(node.getLeft());
            }
            if (node.getRight() != null) {
                q.add(node.getRight());
            }
        }
        return levels;
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * height of helper method
     * @return the height of the root of the tree, -1 if the tree is empty
     * @param h a node that would be through the tree
     */
    private int height(BSTNode<T> h) {
        if (h == null) {
            return -1;
        } else {
            return 1 + Math.max(height(h.getLeft()), height(h.getRight()));
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}