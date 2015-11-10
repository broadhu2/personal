package trees;

import java.util.List;
import java.util.Map.Entry;

/**
 * Binary Search Trees (BSTs) keep their keys in sorted order so that lookup and 
 * other operations can use the principle of binary search for O(logn) time complexity.
 * This interface outlines the necessary operations for the BST data structure 
 * (find, insert, and remove) as well as some other helpful methods.
 * 
 * @author Josh Broadhurst
 *
 * @param <K> A comparable key type for tree nodes.
 * @param <V> A value type for tree nodes.
 */
public interface BinarySearchTree<K extends Comparable<? super K>, V> {
	
	/**
	 * Performs lookup operation for binary search tree.
	 * @param key The key to search.
	 * @return The matching value for key.
	 */
	public V find(K key);
	
	/**
	 * Performs insert operation for binary search tree.
	 * @param key The key to insert.
	 * @param value The corresponding value to insert.
	 */
	public void insert(K key, V value);
	
	/**
	 * Performs delete operation for binary search tree.
	 * @param key The key to remove.
	 * @return The value of the key removed.
	 */
	public V remove(K key);
	
	/**
	 * Modifies the value of an existing entry in the tree.
	 * This function assumes newValue is a valid V type.
	 * @param key The key to update.
	 * @param newValue The new value to assign.
	 */
	public void modifyValue(K key, V newValue);
	
	/**
	 * Checks if the tree contains given key.
	 * @param key
	 * @return true if the tree contains key; false otherwise.
	 */
	public boolean contains(K key);
	
	/**
	 * Clears all entries from the tree by setting root to null.
	 */
	public void clear();
	
	/**
	 * Checks if the tree is empty.
	 * @return true if tree is empty; false otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Returns the number of entry nodes in the tree.
	 * @return The number of entries in the tree.
	 */
	public int size();
	
	/**
	 * Returns the height of the tree defined as the longest path from
	 * root to leaf (measured in edges). Empty tree has height -1.
	 * @return The height of the tree.
	 */
	public int height();
	
	/**
	 * Prints out an ASCII drawing of the tree to System.out.
	 */
	public void print();
	
	/**
	 * Retrieves a level-order (sorted) list of keys contained in the tree.
	 * @return A list of keys contained in the tree.
	 */
	public List<K> keys();
	
	/**
	 * Retrieves a list of values contained in the tree sorted by key.
	 * @return A list of values contained in the tree.
	 */
	public List<V> values();
	
	/**
	 * Retrieves a list of key-value entries contained in the tree sorted by key.
	 * @return A list of values contained in the tree.
	 */
	public List<Entry<K,V>> entries();
	
	/**
	 * Returns the greatest key strictly less than the given key, or null if 
	 * there is no such key.
	 * @param key
	 * @return
	 */
	public K lowerKey(K key);
	
	/**
	 * Returns a key-value entry associated with the greatest key strictly less
	 * than the given key, or null if there is no such key.
	 * @param key
	 * @return
	 */
	public Entry<K,V> lowerEntry(K key);
	
	/**
	 * Returns the least key strictly greater than the given key, or null if 
	 * there is no such key.
	 * @param key
	 * @return
	 */
	public K higherKey(K key);
	
	/**
	 * Returns a key-value mapping associated with the least key strictly greater 
	 * than the given key, or null if there is no such key.
	 * @param key
	 * @return
	 */
	public Entry<K,V> higherEntry(K key);
	
	/**
	 * Finds the minimum key in the tree.
	 * @return The key with minimum rank.
	 */
	public K minKey();
	
	/**
	 * Finds the maximum key in the tree.
	 * @return The key with maximum rank.
	 */
	public K maxKey();
	
	/**
	 * Finds the minimum entry in the tree.
	 * @return The entry with minimum key.
	 */
	public Entry<K,V> minEntry();
	
	/**
	 * Finds the maximum entry in the tree.
	 * @return The entry with maximum key.
	 */
	public Entry<K,V> maxEntry();
	
}
