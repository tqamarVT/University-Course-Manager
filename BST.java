import java.util.Iterator;

/**
 * This class represents a Binary Search Tree, a container class used to hold
 * data. This code was created/used in 2114 - Taimoor Qamar.
 * *
 * 
 * @author Taimoor Qamar
 * @version 2019.08.13
 * 
 * @param <K>
 *            key
 * @param <V>
 *            Value
 */
public class BST<K extends Comparable<K>, V extends Comparable<V>> {

    // FIELDS
    private Node<K, V> root;
    private int size;


    /**
     * Constructor method for the BST class.
     */
    public BST() {
        this.root = null;
        this.size = 0;
    }
    // ~ Public methods ........................................................


    // ----------------------------------------------------------
    /**
     * Insert into the tree.
     *
     * @param key
     *            the key of the node to insert.
     * @param value
     *            the value of the node to insert.
     * @throws Exception
     *             if x is already present.
     */
    public void insert(K key, V value) {

        root = insert(key, value, root);

    }


    // ----------------------------------------------------------
    /**
     * Remove the specified value from the tree.
     *
     * @param key
     *            the key of the node to remove.
     * @param value
     *            the value held by the node.
     * 
     * 
     */
    public void remove(K key, V value) {
        root = remove(key, value, root);
    }


    // ----------------------------------------------------------
    /**
     * Find the smallest item in the tree.
     *
     * @return The smallest item, or null if the tree is empty.
     */
    public V findMin() {
        return valueAt(findMin(root));
    }


    // ----------------------------------------------------------
    /**
     * Find the largest item in the tree.
     *
     * @return The largest item in the tree, or null if the tree is empty.
     */
    public V findMax() {
        return valueAt(findMax(root));
    }


    // ----------------------------------------------------------
    /**
     * Find an item in the tree.
     *
     * @param key
     *            the key of node to search for.
     * @return the matching item or null if not found.
     */
    public V find(K key) {
        return valueAt(find(key, root));
    }


    // ----------------------------------------------------------
    /**
     * Find an item in the tree.
     *
     * @param key
     *            the key of node to search for.
     * @param value
     *            the value of the node to search for.
     * @return the matching item or null if not found.
     */
    public V findByValue(K key, V value) {
        return valueAt(findByValue(key, value, root));
    }


    // ----------------------------------------------------------
    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }


    // ----------------------------------------------------------
    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return (root == null);
    }


    /**
     * Fill in later
     */
    public void inOrderTraversal() {
        int level = 0;
        inOrderTraversal(root, level);
        System.out.print("Size = " + this.size + "\n");
    }


    /**
     * Gets the number of elements in this BST
     * 
     * @return the size
     */
    public int getSize() {
        return size;
    }


    /**
     * This method creates and returns an iterator object.
     *
     * @return new Iterator object
     */
    public Iterator<V> iterator() {
        return new BstIterator<V>();
    }


    // ----------------------------------------------------------
    /**
     * Internal method to get element value stored in a tree node, with safe
     * handling of null nodes.
     *
     * @param node
     *            the node.
     * @return the element field or null if node is null.
     */
    private V valueAt(Node<K, V> node) {
        return (node == null) ? null : node.getValue();
    }


    // ----------------------------------------------------------
    /**
     * Internal method to insert a value into a subtree.
     *
     * @param key
     *            the key of the node to insert.
     * @param value
     *            the value of the node to insert.
     * @param node
     *            the node that roots the subtree which could be inserted into.
     * @return the new root of the subtree.
     * @throws IllegalArgumentException
     *             if node with key and value is already present.
     */
    private Node<K, V> insert(K key, V value, Node<K, V> node) {
        if (node == null) {
            this.size++;
            return new Node<K, V>(key, value);
        }
        else if (key.compareTo(node.getKey()) < 0) {
            node.setLeft(insert(key, value, node.getLeft()));
        }
        else if (key.compareTo(node.getKey()) > 0) {
            node.setRight(insert(key, value, node.getRight()));
        }
        else {
            if (value.compareTo(node.getValue()) < 0) {
                node.setLeft(insert(key, value, node.getLeft()));
            }
            else if (value.compareTo(node.getValue()) > 0) {
                node.setRight(insert(key, value, node.getRight()));
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        return node;
    }


    // ----------------------------------------------------------
    /**
     * Internal method to remove a specified item from a subtree.
     *
     * @param key
     *            the key of the node to remove.
     * @param value
     *            the value of the node to remove.
     * @param node
     *            the node that roots the subtree from which a node could
     *            be removed.
     * @return the new root of the subtree.
     * 
     */
    private Node<K, V> remove(K key, V value, Node<K, V> node) {
        // This local variable will contain the new root of the subtree,
        // if the root needs to change.
        Node<K, V> result = node;

        // If there's no more subtree to examine
        if (node == null) {
            return result;
        }
        // if key should be to the left of the root
        if (key.compareTo(node.getKey()) < 0) {
            node.setLeft(remove(key, value, node.getLeft()));
        }
        // if key should be to the right of the root
        else if (key.compareTo(node.getKey()) > 0) {
            node.setRight(remove(key, value, node.getRight()));
        }
        // If key is on the current node
        else {
            // if value should be to the left of the root
            if (value.compareTo(node.getValue()) < 0) {
                node.setLeft(remove(key, value, node.getLeft()));
            }
            // if value should be to the right of the root
            else if (value.compareTo(node.getValue()) > 0) {
                node.setRight(remove(key, value, node.getRight()));
            }
            // if value is on the current node
            else {
                if (node.getLeft() != null && node.getRight() != null) {

                    V tempVal = this.findMax(node.getLeft()).getValue();
                    K tempKey = this.findMax(node.getLeft()).getKey();
                    this.remove(tempKey, tempVal, node);
                    node.setValue(tempVal);
                    node.setKey(tempKey);
                }

                // If there is only one child on the left
                else if (node.getLeft() != null) {
                    result = node.getLeft();
                    this.size--;
                }
                // If there is only one child on the right
                else {
                    result = node.getRight();
                    this.size--;
                }
            }
        }

        return result;

    }


    // ----------------------------------------------------------
    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param node
     *            the node that roots the tree.
     * @return node containing the smallest item.
     */
    private Node<K, V> findMin(Node<K, V> node) {
        if (node == null) {
            return node;
        }
        else if (node.getLeft() == null) {
            return node;
        }
        else {
            return findMin(node.getLeft());
        }
    }


    // ----------------------------------------------------------
    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param node
     *            the node that roots the tree.
     * @return node containing the largest item.
     */
    private Node<K, V> findMax(Node<K, V> node) {
        if (node == null) {
            return node;
        }
        else if (node.getRight() == null) {
            return node;
        }
        else {
            return findMax(node.getRight());
        }
    }


    // ----------------------------------------------------------
    /**
     * Internal method to find an item in a subtree.
     *
     * @param key
     *            the key of the node to search for.
     * @param node
     *            the node that roots the subtree in which to search.
     * @return node containing the matched item.
     */
    private Node<K, V> find(K key, Node<K, V> node) {
        if (node == null) {
            return null; // Not found
        }
        else if (key.compareTo(node.getKey()) < 0) {
            // Search in the left subtree
            return find(key, node.getLeft());
        }
        else if (key.compareTo(node.getKey()) > 0) {
            // Search in the right subtree
            return find(key, node.getRight());
        }
        else {
            return node; // Match
        }
    }


    // ----------------------------------------------------------
    /**
     * Internal method to find an item in a subtree.
     *
     * @param key
     *            the key of the node to search for.
     * @param node
     *            the node that roots the subtree in which to search.
     * @return node containing the matched item.
     */
    private Node<K, V> findByValue(K key, V value, Node<K, V> node) {
        if (node == null) {
            return null; // Not found
        }
        else if (key.compareTo(node.getKey()) < 0) {
            // Search in the left subtree
            return findByValue(key, value, node.getLeft());
        }
        else if (key.compareTo(node.getKey()) > 0) {
            // Search in the right subtree
            return findByValue(key, value, node.getRight());
        }
        // If key is found, compare values
        else {
            if (value.compareTo(node.getValue()) < 0) {
                // Search in the left subtree
                return findByValue(key, value, node.getLeft());
            }
            else if (value.compareTo(node.getValue()) > 0) {
                // Search in the right subtree
                return findByValue(key, value, node.getRight());
            }
            else {
                return node; // Match
            }
        }
    }


    /**
     * This method processes an in-order traversal of the BST and executes the
     * element in the nodes toString command as it traverses. It also prints out
     * the level at which the element's node is in the BST.
     * 
     * @param node
     *            The node from which to begin the next step in the traversal.
     * @param level
     *            the level at which the node being passed is located.
     */
    private void inOrderTraversal(Node<K, V> node, int level) {
        if (node == null) {
            return;
        }

        inOrderTraversal(node.getLeft(), level + 1);
        System.out.print(node.getKey().toString() + ", ");
        System.out.print(node.getValue().toString());
        System.out.print(", at level " + level);
        System.out.print("\n");
        inOrderTraversal(node.getRight(), level + 1);
    }


    /**
     * This helper class represents an in-order traversing iterator used to
     * iterate through this BST.
     * 
     * @author Taimoor Qamar
     *
     * @param <E>
     *            Data type
     */
    private class BstIterator<E> implements Iterator<V> {
        // FIELDS
        private ArrayBasedStack<Node<K, V>> nodeStack;


        /**
         * Creates a BstIterator object.
         */
        public BstIterator() {
            if (root != null) {
                nodeStack = new ArrayBasedStack<Node<K, V>>();
                this.goLeftFrom(root);
            }
            else {
                nodeStack = new ArrayBasedStack<Node<K, V>>();
            }
        }


        @Override
        public boolean hasNext() {
            return (!nodeStack.isEmpty());
        }


        @Override
        public V next() {
            if (this.hasNext()) {
                Node<K, V> current = nodeStack.peek();
                nodeStack.pop();
                if (current.getRight() != null) {
                    this.goLeftFrom(current.getRight());
                }
                return current.getValue();
            }

            else {
                return null;
            }
        }


        /**
         * This method traverses leftward through the subtree rooted by the node
         * passed in the parameter until the end of the subtree, and adds each
         * node to a stack along the way.
         * 
         * @param node
         */
        private void goLeftFrom(Node<K, V> node) {
            while (node != null) {
                nodeStack.push(node);
                node = node.getLeft();
            }
        }
    }
}
