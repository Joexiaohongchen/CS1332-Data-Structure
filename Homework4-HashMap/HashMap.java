import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Xiaohong Chen
 * @userid xchen671
 * @GTID 903392332
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * At the start of the method, you should check to see if the array
     * violates the max load factor. For example, let's say the array is of
     * length 5 and the current size is 3 (LF = 0.6). For this example, assume
     * that no elements are removed in between steps. If a non-duplicate key is
     * added, the size will increase to 4 (LF = 0.8), but the resize shouldn't
     * occur until the next put operation.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if ((value == null) || (key == null)) {
            throw new IllegalArgumentException("Cannot insert null data"
                    + " into data structure.");
        }
        MapEntry<K, V> entry = new MapEntry(key, value);
        double load = (double) size / table.length;
        if (load > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int hashCode = Math.abs(entry.getKey().hashCode() % table.length);
        MapEntry<K, V> currentEntry = table[hashCode];
        int first = -1;
        if (currentEntry == null) {
            table[hashCode] = entry;
            size++;
            return null;
        } else {
            while (currentEntry != null) {
                if (currentEntry.isRemoved()) {
                    if (currentEntry.getKey().equals(key)) {
                        if (first < 0) {
                            first = hashCode;
                            table[first] = entry;
                            size++;
                        } else {
                            table[first] = entry;
                            size++;
                        }
                    } else {
                        if (first < 0) {
                            first = hashCode;
                        }
                        hashCode = (hashCode + 1) % table.length;
                        currentEntry = table[hashCode];
                    }
                } else {
                    if (currentEntry.getKey().equals(key)) {
                        V oldVal = currentEntry.getValue();
                        currentEntry.setValue(entry.getValue());
                        return oldVal;
                    } else {
                        hashCode = (hashCode + 1) % table.length;
                        currentEntry = table[hashCode];
                    }
                }
                if (currentEntry == null) {
                    if (first >= 0) {
                        table[first] = entry;
                        size++;
                    } else {
                        table[hashCode] = entry;
                        size++;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key could"
                    + " not be found");
        }
        int hashCode = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> curEntry = table[hashCode];
        if (curEntry == null) {
            throw new java.util.NoSuchElementException("Key could not"
                    + " be found");
        } else {
            while (curEntry != null) {
                if (curEntry.isRemoved()) {
                    if (curEntry.getKey().equals(key)) {
                        throw new java.util.NoSuchElementException("Key could"
                                + " not be found");
                    } else {
                        hashCode = (hashCode + 1) % table.length;
                        curEntry = table[hashCode];
                    }
                } else {
                    if (curEntry.getKey().equals(key)) {
                        V removedElement = curEntry.getValue();
                        curEntry.setRemoved(true);
                        size--;
                        return removedElement;
                    } else {
                        hashCode = (hashCode + 1) % table.length;
                        curEntry = table[hashCode];
                    }
                }
            }
        }
        throw new java.util.NoSuchElementException("Key could not"
                + "be found");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key could"
                    + " not be found");
        }
        int hashCode = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> currEntry = table[hashCode];
        if (currEntry == null) {
            throw new java.util.NoSuchElementException("Key could"
                    + " not be found");
        } else {
            while (currEntry != null) {
                if (currEntry.getKey().equals(key)) {
                    if (currEntry.isRemoved()) {
                        throw new java.util.NoSuchElementException("Key could"
                                + " not be found");
                    } else {
                        return currEntry.getValue();
                    }
                } else {
                    hashCode = (hashCode + 1) % table.length;
                    currEntry = table[hashCode];
                }
            }
        }
        throw new java.util.NoSuchElementException("Key could"
                + " not be found");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */

    public boolean containsKey(K key) {
        try {
            get(key);
        } catch (java.util.NoSuchElementException exception) {
            return false;
        }
        return true;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                if (entry.getKey() != null && !entry.isRemoved()) {
                    keys.add(entry.getKey());
                }
            }
        }
        return keys;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     *
     * You should iterate over the table in order of increasing index and add 
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                values.add(entry.getValue());
            }
        }
        return values;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is non-positive or less than
     * the number of items in the hash map.
     */
    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new IllegalArgumentException("Length was not positive or"
                    + " was less than the size of the hash map");
        }
        MapEntry<K, V>[] newEntry = new MapEntry[length];
        int index = 0;
        while (index < table.length) {
            MapEntry<K, V> curEntry = table[index];
            if (curEntry != null) {
                if (!curEntry.isRemoved()) {
                    int hashCode = Math.abs(curEntry.getKey().hashCode()
                            % length);
                    if (newEntry[hashCode] == null) {
                        newEntry[hashCode] = curEntry;
                    } else {
                        hashCode = (hashCode + 1) % table.length;
                        MapEntry<K, V> toAdd = newEntry[hashCode];
                        while (toAdd != null) {
                            hashCode = (hashCode + 1) % table.length;
                            toAdd = newEntry[hashCode];
                        }
                        if (newEntry[hashCode] == null) {
                            newEntry[hashCode] = curEntry;
                        }
                    }
                }
                index++;
            } else {
                index++;
            }
        }
        table = newEntry;
    }

    /**
     * Clears the table and resets it to the default length.
     */
    public void clear() {
        size = 0;
        table = new MapEntry[INITIAL_CAPACITY];

    }
    
    /**
     * Returns the number of elements in the map.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}