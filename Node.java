
/**
 * 
 * @author Taimoor Qamar
 * @version 2019.09.03
 *          This class represents the node used to implement the Binary Search
 *          Tree.
 * @param <K>
 *            The Key for the data element contained in the node.
 * @param <V>
 *            The data element contained in the node.
 * 
 */
class Node<K, V> {
    // FIELDS
    private V value;
    private K key;
    private Node<K, V> left;
    private Node<K, V> right;


    /**
     * Constructor for the Node class.
     * 
     * @param key
     *            The key of the element to be stored in the node.
     * @param value
     *            The element to be stored in the node.
     * 
     */
    Node(K key, V value) {
        this.value = value;
        this.key = key;
        this.left = null;
        this.right = null;
    }


    /**
     * Getter method for the value.
     * 
     * @return the element
     */
    public V getValue() {
        return value;
    }


    /**
     * Getter method for the key.
     * 
     * @return the key
     */
    public K getKey() {
        return key;
    }


    /**
     * Setter method for the key
     * 
     * @param key
     *            the key value to set the field key variable to.
     */
    public void setKey(K key) {
        this.key = key;
    }


    /**
     * Setter method for the element.
     * 
     * @param value
     *            the new data value to set
     */
    public void setValue(V value) {
        this.value = value;
    }


    /**
     * Getter method for the left child.
     * 
     * @return a reference to the left child.
     */
    public Node<K, V> getLeft() {
        return this.left;
    }


    /**
     * Setter method for the left child.
     * 
     * @param node
     *            the node to set the left field variable to.
     */
    public void setLeft(Node<K, V> node) {
        left = node;
    }


    /**
     * Getter method for the right child.
     * 
     * @return a reference to the right child.
     */
    public Node<K, V> getRight() {
        return this.right;
    }


    /**
     * Setter method for the right child.
     * 
     * @param node
     *            the node to set the right field variable to.
     */
    public void setRight(Node<K, V> node) {
        right = node;
    }
}
