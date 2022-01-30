/**
 * 
 * @author Taimoor Qamar
 * @version 2019.26.09
 * 
 *          This class is used for storage of student and section data.
 * 
 * @param <T>
 *            Generic type.
 */
public class StudentIndex<T> {

    // FIELDS
    private T[] array; // The base array used to hold student/section
                       // data.
    private int size; // The number of non-null elements currently in the array.
    private int arrEnd; // The total number of elements currently in the array,
                        // used to append a new entry to the end of the array.
    private int capacity; // The current max capacity of the array.


    /**
     * The default constructor for this class.
     */
    @SuppressWarnings("unchecked") // Prevents Eclipse from displaying a warning
                                   // about the type cast being unsafe.
    public StudentIndex() {
        array = (T[])new Object[20];
        arrEnd = 0;
        size = 0;
        capacity = 20;

    }


    // ----------------------------------------------------------
    /**
     * Pull an object from the given index.
     * 
     * @param index
     *            The index from which to return the object.
     * @return the object located at the index.
     */
    public T getAt(int index) {
        if (array[index] == null) {
            return null;
        }
        else {
            return array[index];
        }
    }


    // ----------------------------------------------------------
    /**
     * Add an element to the end of the array.
     * 
     * @param element
     *            the element to add to the array.
     */
    public void add(T element) {
        if (arrEnd == capacity) {
            this.expandCapacity();
        }
        array[arrEnd] = element;
        arrEnd++;
        size++;
    }


    // ----------------------------------------------------------
    /**
     * Remove and return an element from the given index.
     * 
     * @param index
     *            The index of the location of the object to remove.
     * @return the object removed from the array.
     */
    public T remove(int index) {
        if (array[index] == null) {
            return null;
        }
        else {
            T ret = array[index];
            array[index] = null;
            size--;
            return ret;
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the current size of the array.
     * 
     * @return the size of the array.
     */
    public int getSize() {
        return size;
    }


    // ----------------------------------------------------------
    /**
     * Returns the current capacity of the array.
     * 
     * @return the current max capacity of the array.
     */
    public int getCapacity() {
        return capacity;
    }


    // ----------------------------------------------------------
    /**
     * Expands the capacity of the studentArray by doubling its current
     * capacity.
     */
    private void expandCapacity() {

        @SuppressWarnings("unchecked")
        T[] newArray = (T[])new Object[this.capacity * 2];

        for (int i = 0; i < capacity; i++) {
            newArray[i] = array[i];
        }

        array = newArray;
        capacity *= 2;
    }

}
