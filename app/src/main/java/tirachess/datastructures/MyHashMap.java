package tirachess.datastructures;

import java.util.Objects;

/**
 * A custom HashMap class.
 */
public class MyHashMap<K, V> {
    private Entry<K, V>[] entries;
    private int capacity;
    private int size = 0;

    /**
     * Constructor that creates the instance of the class. Default capacity of the
     * initial array is 32.
     */
    @SuppressWarnings("unchecked")
    public MyHashMap() {
        this.capacity = 32;
        System.out.println(this.capacity);
        this.entries = new Entry[this.capacity];
    }

    /**
     * Constructor that creates the instance of the class. The capacity is determined
     * by the given parameter. 
     * 
     * @param capacity int that will the the size of the initial array.
     */
    @SuppressWarnings("unchecked")
    public MyHashMap(int capacity) {
        this.capacity = capacity;
        this.entries = new Entry[this.capacity];
    }

    /**
     * Adds a new Entry to the HashMap. If the count of Entries in the HashMap has 
     * reached 75% of its capacity, then the array is extended. 
     * 
     * @param key The key that will be used to search for the object.
     * @param value The value that will be stored to the HashMap.
     */
    public void put(K key, V value) {
        if (size >= 0.75 * this.capacity) {
            this.extend();
        }

        Entry<K, V> entry = new Entry<>(key, value, null);
        int index = getHash(key) % this.capacity;

        Entry<K, V> current = this.entries[index];
        if (current == null) {
            this.entries[index] = entry;
            this.size++;
        } else {
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            if (current.key.equals(key)) {
                current.value = value;
            } else {
                current.next = entry;
                this.size++;
            }
        }
    }
    
    /**
     * Gives the value at the given key. 
     * 
     * @param key The key that will be searched.
     * @return V value that was stored behind the key. 
     */
    public V get(K key) {
        Entry<K, V> entry = this.entries[getHash(key) % this.capacity];
        while (entry != null) {
            if (key == entry.key) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * Returns the count of entries that have been stored in the HashMap.
     * 
     * @return int the count of entries in the HashMap.
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns a boolean value on whether the HashMap is empty.
     * 
     * @return boolean Return true if the HashMap is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a hash of the given key.
     * 
     * @param key the given key.
     * @return int Returns the hash of the key.
     */
    private int getHash(K key) {
        return key.hashCode();
    }
    
    /**
     * Extends the length of the inner array by two to keep the efficiency of
     * the HashMap.
     */
    @SuppressWarnings("unchecked")
    private void extend() {
        this.size = 0;
        this.capacity *= 2;
        Entry<K, V>[] oldEntries = this.entries;
        this.entries = new Entry[this.capacity];
        for (Entry<K, V> entry : oldEntries) {
            while (entry != null) {
                this.put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    /**
     * A class that stores key and value pairs. This is used to store entries
     * to the HashMap. 
     */
    static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        /**
         * Constructor that creates the instance of the Entry class.
         * 
         * @param key the key of the to-be-stored element.
         * @param value the element itself that is stored.
         * @param next the next value in the linked list. 
         */
        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Return the key.
         * 
         * @return K key.
         */
        public K getKey() {
            return key;
        }

        /**
         * Return the element/value.
         * 
         * @return V value.
         */
        public V getValue() {
            return value;
        }

        /**
         * Returns a boolean on whether the given object is the same as this.
         * 
         * @param object The object that this object is compared with. 
         * @return boolean Returns true if the object is same as this.
         */
        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof Entry) {
                Entry entry = (Entry) object;
                return key.equals(entry.getKey()) && value.equals(entry.getValue());
            }
            return false;
        }

        /**
         * Returns a hashcode of the Entry object.
         * 
         * @return int returns the hashcode of the object.
         */
        @Override
        public int hashCode() {
            return Objects.hash(this.key, this.value);
        }

        /**
         * Returns the string representation of the object.
         * 
         * @return String Returns the string representation.
         */
        @Override
        public String toString() {
            return "{" + key + ", " + value + "}";
        }

    }
}

