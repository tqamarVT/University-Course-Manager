/**
 * 
 */


import java.util.EmptyStackException;

/**
 * This class is an implementation of the StackADT interface.
 * 
 * @author Taimoor Qamar (tqamar)
 * @version 2019.07.15
 * @param <T>
 *            generic object
 *
 */
public class ArrayBasedStack<T> implements StackADT<T> {
    // FIELDS
    private T[] stackArray; // the base of the stack we are implementing
    private int size; // The number of elements in the stack
    private int capacity; // The length of the array "stackArray"


    /**
     * The non-default constructor for this class which
     * initializes the field.
     * 
     * @param capacity
     *            indicates the size of the underlying array
     */
    @SuppressWarnings("unchecked") // prevents Eclipse from displaying a warning
                                   // about the type cast being potentially
                                   // unsafe.
    public ArrayBasedStack(int capacity) {

        stackArray = (T[])new Object[capacity];
        this.size = 0;
        this.capacity = capacity;

    }


    /**
     * The default constructor for this class
     */
    public ArrayBasedStack() {
        this(100);
    }


    /**
     * Checks if the stack is empty
     * 
     * @return boolean indicating if stack is empty
     */
    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }


    /**
     * Returns the object located at the top of the stack.
     * 
     * @throws EmptyStackException
     *             when stack is empty
     * @return The object located at the top of the stack
     */
    @Override
    public T peek() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        return (this.stackArray[this.size - 1]);
    }


    /**
     * Removes the object located at the top of the stack and returns it
     * 
     * @throws EmptyStackException
     *             when stack is empty
     * @return the object located at the top of the stack
     */
    @Override
    public T pop() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        @SuppressWarnings("unchecked")
        T temp = (T)new Object();
        temp = this.peek();
        this.size--;
        return temp;
    }


    /**
     * Adds a new object to the top of the stack.
     * 
     * @param item
     *            the item to be added to the top of the stack
     */
    @Override
    public void push(T item) {
        if (this.size == this.capacity) {
            this.expandCapacity();
        }
        this.stackArray[this.size] = item;
        this.size++;

    }


    /**
     * Checks to see if the stack contains the item.
     * 
     * @param item
     *            the item to be searched for in the stack
     * @return a boolean indicating if the item is located in the stack
     */
    @Override
    public boolean contains(T item) {
        Boolean found = false;
        if (this.isEmpty()) {
            return found;
        }
        for (T each : this.stackArray) {
            if (each == null) {
                continue;
            }
            else if (each.equals(item)) {
                found = true;
            }
            else {
                continue;
            }
        }
        return found;
    }


    /**
     * returns the size of the stack
     * 
     * @return the size of the stack
     */
    @Override
    public int size() {
        return this.size;
    }


    /**
     * Clears the stack of all items
     */
    @Override
    public void clear() {
        @SuppressWarnings("unchecked")
        T[] newArray = (T[])new Object[capacity];
        this.stackArray = newArray;
        this.size = 0;

    }


    /**
     * Returns an array with a copy of each element in the stack with the top
     * of the stack being the last element
     * 
     * @return The array representation of the stack.
     */
    @Override
    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] copy = (T[])new Object[this.size()];
        for (int i = 0; i < this.size(); i++) {
            copy[i] = this.stackArray[i];
        }
        return copy;
    }


    /**
     * Expands the capacity of the stack by doubling its current capacity.
     */
    private void expandCapacity() {

        @SuppressWarnings("unchecked")
        T[] newArray = (T[])new Object[this.capacity * 2];

        for (int i = 0; i < this.capacity; i++) {
            newArray[i] = this.stackArray[i];
        }

        this.stackArray = newArray;
        this.capacity *= 2;
    }


    /**
     * Returns the string representation of the stack.
     * 
     * [] (if the stack is empty)
     * [bottom, item, ..., item, top] (if the stack contains items)
     * 
     * @return the string representation of the stack.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');

        boolean firstItem = true;
        for (int i = 0; i < this.size(); i++) {
            if (!firstItem) {
                builder.append(", ");
            }
            else {
                firstItem = false;
            }

            // String.valueOf will print null or the toString of the item
            builder.append(String.valueOf(this.stackArray[i]));
        }
        builder.append(']');
        return builder.toString();
    }


    /**
     * Two stacks are equal iff they both have the same size and contain the
     * same elements in the same order.
     *
     * @param other
     *            the other object to compare to this
     *
     * @return {@code true}, if the stacks are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (this.getClass().equals(other.getClass())) {
            ArrayBasedStack<?> otherStack = (ArrayBasedStack<?>)other;
            if (this.size() != otherStack.size()) {
                return false;
            }
            Object[] otherArray = otherStack.toArray();
            for (int i = 0; i < this.size(); i++) {
                if (!(this.stackArray[i].equals(otherArray[i]))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
