import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.ceil;
/**
 * Your implementation of an AVL Tree.
 *
 * @author JhaDeya Rhymes
 * @userid jrhymes7
 * @GTID 903588553
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data is null.");
        }
        size = 0;
        for (T input : data) {
            add(input);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        root = addHelper(data, root);
    }
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            node = new AVLNode<>(data);
            node.setHeight(1);
            node.setBalanceFactor(0);
            size++;
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        }
        if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(data, node.getRight()));
        }
        height(node);
        balanceFactor(node);

        node = balance(node);
        return node;
    }
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            node = rotateLeft(node);
        } else if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        }
        return node;
    }
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> tmp = node.getLeft();
        node.setLeft(tmp.getRight());
        tmp.setRight(node);
        height(node);
        balanceFactor(node);
        height(tmp);
        balanceFactor(tmp);
        return tmp;
    }
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> tmp = node.getRight();
        node.setRight(tmp.getLeft());
        tmp.setLeft(node);
        height(node);
        balanceFactor(node);
        height(tmp);
        balanceFactor(tmp);
        return tmp;
    }
    private void balanceFactor(AVLNode<T> node) {
        if (node.getLeft() != null && node.getRight() != null) {
            node.setBalanceFactor(node.getLeft().getHeight()
                    - node.getRight().getHeight());
        } else if (node.getLeft() != null) {
            node.setBalanceFactor(1 + node.getLeft().getHeight());
        } else if (node.getRight() != null) {
            node.setBalanceFactor(-1 - node.getRight().getHeight());
        } else {
            node.setBalanceFactor(0);
        }
    }
    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(data, root, removed);
        size--;
        return removed.getData();
    }
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("Data is not found.");
        }
        int tmp = data.compareTo(node.getData());
        if (tmp < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), removed));
        } else if (tmp > 0) {
            node.setRight(removeHelper(data, node.getRight(), removed));
        } else {
            removed.setData(node.getData());
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                AVLNode<T> child = new AVLNode<>(null);
                node.setRight(successorHelper(node.getRight(), child));
                node.setData(child.getData());
            }
        }
        height(node);
        balanceFactor(node);
        return balance(node);
    }
    private AVLNode<T> successorHelper(AVLNode<T> node, AVLNode<T> child) {
        if (node.getLeft() == null) {
            child.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(successorHelper(node.getLeft(), child));
        height(node);
        balanceFactor(node);
        return balance(node);
    }
    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        return getHelper(data, root);
    }
    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("Data is not found.");
        }
        int tmp = data.compareTo(node.getData());
        if (tmp > 0) {
            return getHelper(data, node.getRight());
        } else if (tmp < 0) {
            return getHelper(data, node.getLeft());
        } else {
            return node.getData();
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Finds and retrieves the median data of the passed in AVL.
     *
     * This method will not need to traverse the entire tree to
     * function properly, so you should only traverse enough branches of the tree
     * necessary to find the median data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *
     * findMedian() should return 40
     *
     * @throws NoSuchElementException if the tree is empty or contains an even number of data
     * @return the median data of the AVL
     */
    public T findMedian() {
        if (root == null) {
            throw new NoSuchElementException("Tree is empty.");
        } else if (size % 2 == 0) {
            throw new NoSuchElementException("There is no median, even number of data present.");
        }
        List<T> list = new ArrayList<>();
        traverse(list, root);
        T median = null;
        for (int i = 0; i < list.size(); i++) {
            if (i == (ceil(list.size() / 2))) {
                median = list.get(i);
            }
        }
        return median;
    }
    private void traverse(List<T> list, AVLNode<T> root) {
        if (root != null) {
            traverse(list, root.getLeft());
            list.add(root.getData());
            traverse(list, root.getRight());
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelp(root);
    }
    private void height(AVLNode<T> node) {
        if (node == null) {
            return;
        }
        int heightLeft = -1;
        int heightRight = -1;
        if (node.getLeft() != null) {
            heightLeft = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            heightRight = node.getRight().getHeight();
        }
        node.setHeight(1 + Math.max(heightLeft, heightRight));
    }
    private int heightHelp(AVLNode<T> node) {
        if (size == 0 | root == null) {
            return -1;
        } else {
            return node.getHeight();
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}