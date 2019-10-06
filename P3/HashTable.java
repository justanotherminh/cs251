import java.io.PrintStream;
import java.util.*;

/**
 * Generic Chaining Hashtable
 * @param <K> Key type
 * @param <V> Value type
 */
public class HashTable<K, V> {

    /**
     * Auxiliary class for HashTable
     * @param <F> Key type
     * @param <S> Value type
     */
    public class Pair<F, S> {
        public F key;
        public S value;

        /**
         * Construct a key value Pair
         * @throws NullPointerException if key is null
         * @param key key
         * @param value value
         */
        public Pair(F key, S value) {
            if(key == null) {
                throw new NullPointerException("Key cannot be null");
            }
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object other) {
            if(! (other instanceof Pair)) {
                return false;
            }
            @SuppressWarnings("unchecked")
            Pair<?, ?> po = (Pair) other;
            // Hack here. We only compare key value
            // so that we can use it to call List.contains
            return key.equals(po.key);
        }
    }

    private static final int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271};

    private LinkedList<Pair<K, V>>[] table;
    private int size;
    private int capacity;

    /**
     * Initialize an array of length capacity,
     * then initialize the table with empty Lists at each index.
     * @param capacity Initial capacity of the table.
     */
    public HashTable(int capacity) {
        this.table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            this.table[i] = new LinkedList<>();
        }
        this.size = 0;
        this.capacity = capacity;
    }

    /**
     * Hash function for Strings
     * @param str string to be hashed
     * @return hashCode of string
     */
    private static int stringHash (String str) {
        int hash = 5381;
        for (int i : str.toCharArray()) {
            hash = ((hash << 5) + hash) + i;
            if (hash < 0) {
                hash += Integer.MAX_VALUE;
            }
        }
        return hash;
    }

    /**
     * Get the hash code of key.
     * You may design any hashCode function you like.
     * @param key key
     * @return hashCode of key
     */
    private int hashCode(K key) {
        int hashCode;
        if (key instanceof String) {
            hashCode = stringHash((String) key);
        } else {
            hashCode = key.hashCode();
        }
        int modulo = hashCode % this.capacity;
        modulo = modulo < 0 ? modulo + this.capacity : modulo;
        return modulo;
    }

    /**
     * Returns the value of the key if found, return null otherwise.
     * @param key key
     * @throws NullPointerException if key is null
     * @return Returns the value of the key if found. Otherwise, return null.
     */
    public V get(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        LinkedList<Pair<K, V>> list = this.table[hashCode(key)];
        for (Pair<K, V> current : list) {
            if (current.key.equals(key)) {
                return current.value;
            }
        }
        return null;
    }

    /**
     * Adds the (key, value) pair to the table.
     * If key already presents in the hashtable, overwrite the original value.
     * Resize the hashtable if the load factor becomes greater than 0.5 after inserting this (key, value) pair.
     * @throws NullPointerException if key or value is null
     * @param key key
     * @param value value
     * @return the previous value of the specified key in this hashtable, or null if it did not have one
     */
    public V put(K key, V value) throws NullPointerException {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        V result = remove(key);
        LinkedList<Pair<K, V>> list = this.table[hashCode(key)];
        list.add(new Pair<>(key, value));
        this.size += 1;
        if (this.size * 2 > this.capacity) {
            resize();
        }
        return result;
    }

    /**
     * Resize the hashtable if the load factor becomes greater than 0.5 after inserting this (key, value) pair.
     * We hard code a list of prime number for you to use. You can assume you will never run out of prime number to use.
     */
    private void resize() {
        for (int i : primes) {
            if (i > this.capacity * 2) {
                this.capacity = i;
                break;
            }
        }
        LinkedList<Pair<K, V>>[] oldTable = this.table;
        this.table = new LinkedList[this.capacity];
        for (int i = 0; i < this.capacity; i++) {
            this.table[i] = new LinkedList<>();
        }
        this.size = 0;
        for (LinkedList<Pair<K, V>> list : oldTable) {
            for (Pair<K, V> item : list) {
                put(item.key, item.value);
            }
        }
    }

    /**
     * Removes the key from the table, if it's there.
     * @throws NullPointerException if the key is null
     * @param key key
     * @return Return the value of the key if key exists in the hashtable, return null otherwise
     */
    public V remove(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        LinkedList<Pair<K, V>> list = this.table[hashCode(key)];
        for (Pair<K, V> current : list) {
            if (current.key.equals(key)) {
                list.remove(current);
                this.size -= 1;
                return current.value;
            }
        }
        return null;
    }

    /**
     * Returns whether the key is in the hashtable.
     * @throws NullPointerException if key is null
     * @param key key
     * @return Return true if the key is in the hashtable. Return false otherwise.
     */
    public boolean containsKey(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        return get(key) != null;
    }

    /**
     * Returns the number of (key, value) pairs in the table.
     * @return size of hashtable
     */
    public int size() {
        return this.size;
    }

    /**
     * Replaces the value for the specified key only if it is currently mapped to some value.
     * @throws NullPointerException if key is null
     * @param key key
     * @param value value
     * @return Return the previous value associated with the specified key, or null if there was no mapping for the key.
     */
    public V replace(K key, V value) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        if (!containsKey(key)) {
            return null;
        }
        return put(key, value);
    }

    /**
     * Return the chain length at index in this hashtable.
     * @param index index of the chain to get length of
     * @return chain length at index in this hashtable.
     */
    public int getCollision(int index) {
        return this.table[index].size();
    }

    /**
     * Read in an input file and write output to output stream
     * Scanner in starts from the beginning of the file
     * @param capacity the initial capacity of hash table.
     * @param in input Scanner
     * @param output output PrintStream
     */
    public static void generateOutput(int capacity, Scanner in, PrintStream output) {
        in.nextLine();
        int lines = Integer.parseInt(in.nextLine());

        StringBuilder out = new StringBuilder();
        HashTable<String, Integer> table = new HashTable<>(capacity);

        while (in.hasNextLine()) {
            String[] command = in.nextLine().split(" ");
            Integer value;
            switch (command[0]) {
                case "p":
                    value = table.put(command[1], Integer.parseInt(command[2]));
                    if (value != null) {
                        out.append(value);
                        out.append("\n");
                    } else {
                        out.append("null\n");
                    }
                    break;
                case "g":
                    value = table.get(command[1]);
                    if (value != null) {
                        out.append(value);
                        out.append("\n");
                    } else {
                        out.append("null\n");
                    }
                    break;
                case "r":
                    value = table.replace(command[1], Integer.parseInt(command[2]));
                    if (value != null) {
                        out.append(value);
                        out.append("\n");
                    } else {
                        out.append("null\n");
                    }
                    break;
                case "d":
                    value = table.remove(command[1]);
                    if (value != null) {
                        out.append(value);
                        out.append("\n");
                    } else {
                        out.append("null\n");
                    }
                    break;
                case "s":
                    out.append(table.size());
                    out.append("\n");
                    break;
                case "c":
                    if (table.containsKey(command[1])) {
                        out.append("1\n");
                    } else {
                        out.append("0\n");
                    }
                    break;
                case "m":
                    out.append(table.getCollision(Integer.parseInt(command[1])));
                    out.append("\n");
                    break;
            }
        }
        out.deleteCharAt(out.length() - 1);
        output.print(out);
    }
}
