package tirachess.datastructures;

/**
 * A custom ArrayList class.
 */
public class MyArrayList<E> {
    private int size;
    private Object[] elements;

    /**
     * Constructor that creates the instance of the class. Default size of the
     * initial array is 8.
     */
    public MyArrayList() {
        this.size = 0;
        this.elements = new Object[8];
    }

    /**
     * Adds a new element to the ArrayList. If the inner array isn't large enough,
     * it will extend it.
     * 
     * @param e The element that will be added to the ArrayList.
     */
    public void add(E e) {
        if (this.size == this.elements.length) {
            this.expand();
        }
        this.elements[this.size++] = e;
    }

    /**
     * Gives the element at the given index. Throws IndexOutOfBoundsException when
     * the index is out of bounds.
     * 
     * @param index The index of the position.
     * @return Position Returns the position at the given index.
     */
    @SuppressWarnings("unchecked")
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        }
        return (E) this.elements[index];
    }

    /**
     * Returns a boolean value on whether the ArrayList is empty.
     * 
     * @return boolean Return true if the ArrayList is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Empties the ArrayList and sets the inner array back to length of 8.
     */
    public void clear() {
        this.size = 0;
        this.elements = new Object[8];
    }

    /**
     * Gives the amount of positions in the ArrayList.
     * 
     * @return int Returns an integer representing the amount of positions.
     */
    public int size() {
        return this.size;
    }

    /**
     *  This method tells whether the given object in the ArrayList.
     * 
     * @param o The object to find from the ArrayList.
     * @return boolean Return true if the ArrayList contains the given object.
     */
    public boolean contains(Object o) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Expands the length of the inner array by two-fold to make room for new
     * positions.
     */
    private void expand() {
        Object[] newArray = new Object[this.elements.length * 2];
        for (int i = 0; i < this.elements.length; i++) {
            newArray[i] = this.elements[i];
        }
        this.elements = newArray;
    }

}
