package trees;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import java.util.Queue;

public class AVLTree<K extends Comparable<? super K>, V> implements BinarySearchTree<K,V> {
	private Node<K,V> root;
	private int elements;
	private int height;
	
	// balance factor constants
	private static final int L_HEAVY = 2;
	private static final int LL_HEAVY = 1;
	private static final int R_HEAVY = -2;
	private static final int RR_HEAVY = -1;
	
	// max char count for each node used by print()
	private static final int CHARS_PER_NODE = 4;
	
	public AVLTree() {
		root = null;
		elements = 0;
		height = -1;
	}
	
	// copy constructor
	public AVLTree(final AVLTree<K,V> other) {
		root = copy(other.root);
		elements = other.elements;
		height = other.height;
	}
	
	public V find(K key) {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		} else if (!contains(key)) {
			System.err.println("Error: tree does not contain key " + key + ".");
			return null;
		}
		return find(root, key).value;
	}
	
	public void insert(K key, V value) {
		if (contains(key)) {
			System.err.println("Error: key " + key + " already exists.");
			return;
		}
		root = insert(root, key, value);
		elements++;
		height = height(root);
	}
	
	public V remove(K key) {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		} else if (!contains(key)) {
			System.err.println("Error: tree does not contain key " + key + ".");
			return null;
		}
		
		// retrieve the old value before removing (O(logn) + O(logn))
		V oldVal = find(root, key).value;
		root = remove(root, key);
		height = height(root);
		elements--;
		return oldVal;
	}
	
	public void modifyValue(K key, V newValue) {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return;
		} else if (!contains(key)) {
			System.err.println("Error: tree does not contain key " + key + ".");
			return;
		}
		modifyValue(root, key, newValue);
	}
	
	public boolean contains(K key) {
		return contains(root, key);
	}
	
	public void clear() {
		root = null;
		elements = 0;
		height = -1;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public int size() {
		return elements;
	}
	
	public int height() {
		return height;
	}
	
	public void print() {
		if (isEmpty()) {
			System.out.println("(empty)");
			return;
		}
		
		// max width for complete tree at leaf level is CHARS_PER_NODE * 2^h - 1
		int outputWidth = (CHARS_PER_NODE << height) - 1;
		// one line for node values, one line for / \ branches per level
		int outputHeight = 2 * height + 1;
		
		// initialize output with spaces
		String spaces = new String(new char[outputWidth]).replace('\0', ' ');
		List<StringBuilder> output = new ArrayList<StringBuilder>(outputHeight);
		for (int i = 0; i < outputHeight; i++) {
			StringBuilder filler = new StringBuilder(spaces);
			output.add(filler);
		}
		
		// recursive helper function to print the tree
		print(root, output, 0, 0, outputWidth);
		
		for (StringBuilder sb : output) {
			System.out.println(sb.toString());
		}
	}
	
	public List<K> keys() {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		}
		
		Queue<Node<K,V>> queue = new LinkedList<Node<K,V>>();
		List<K> keys = new ArrayList<K>();
		queue.add(root);
		
		// level-order traversal
		while (!queue.isEmpty()) {
			Node<K,V> node = queue.remove();
			keys.add(node.key);
			if (node.hasLeft()) {
				queue.add(node.left);
			}
			if (node.hasRight()) {
				queue.add(node.right);
			}
		}
		
		return keys;
	}
	
	public List<V> values() {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		}
		
		// level-order traversal
		Queue<Node<K,V>> queue = new LinkedList<Node<K,V>>();
		List<V> values = new ArrayList<V>();
		queue.add(root);
		
		while (!queue.isEmpty()) {
			Node<K,V> node = queue.remove();
			values.add(node.value);
			if (node.hasLeft()) {
				queue.add(node.left);
			}
			if (node.hasRight()) {
				queue.add(node.right);
			}
		}
		
		return values;
	}
	
	public List<Entry<K,V>> entries() {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		}
		
		Queue<Node<K,V>> queue = new LinkedList<Node<K,V>>();
		List<Entry<K,V>> entries = new ArrayList<Entry<K,V>>();
		queue.add(root);
		
		// level-order traversal
		while (!queue.isEmpty()) {
			Node<K,V> node = queue.remove();
			entries.add(new AbstractMap.SimpleEntry<K,V>(node.key, node.value));
			if (node.hasLeft()) {
				queue.add(node.left);
			}
			if (node.hasRight()) {
				queue.add(node.right);
			}
		}
		
		return entries;
	}
	
	public K lowerKey(K key) {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		} else if (!contains(key)) {
			System.err.println("Error: tree does not contain key " + key + ".");
			return null;
		}
		
		Node<K,V> node = find(root, key);
		if (node.hasLeft()) {
			return node.left.key;
		} else {
			return null;
		}
	}
	
	public Entry<K,V> lowerEntry(K key) {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		} else if (!contains(key)) {
			System.err.println("Error: tree does not contain key " + key + ".");
			return null;
		}
		
		Node<K,V> node = find(root, key);
		if (node.hasLeft()) {
			return new AbstractMap.SimpleEntry<K,V>(node.left.key, node.left.value);
		} else {
			return null;
		}
	}
	
	public K higherKey(K key) {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		} else if (!contains(key)) {
			System.err.println("Error: tree does not contain key " + key + ".");
			return null;
		}
		
		Node<K,V> node = find(root, key);
		if (node.hasRight()) {
			return node.right.key;
		} else {
			return null;
		}
	}
	
	public Entry<K,V> higherEntry(K key) {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		} else if (!contains(key)) {
			System.err.println("Error: tree does not contain key " + key + ".");
			return null;
		}
		
		Node<K,V> node = find(root, key);
		if (node.hasRight()) {
			return new AbstractMap.SimpleEntry<K,V>(node.right.key, node.right.value);
		} else {
			return null;
		}
	}
	
	public K minKey() {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		}
		return findMin(root).key;
	}
	
	public K maxKey() {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		}
		return findMax(root).key;
	}
	
	public Entry<K,V> minEntry() {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		}
		Node<K,V> min = findMin(root);
		return new AbstractMap.SimpleEntry<K,V>(min.key, min.value);
	}
	
	public Entry<K,V> maxEntry() {
		if (isEmpty()) {
			System.err.println("Error: tree is empty.");
			return null;
		}
		Node<K,V> max = findMax(root);
		return new AbstractMap.SimpleEntry<K,V>(max.key, max.value);
	}

	/* Private Methods */
	
	private Node<K,V> copy(final Node<K,V> node) {
		if (node == null) {
			return null;
		}
		
		// make a deep copy of the other tree
		Node<K,V> nodeCopy = new Node<K,V>(node.key, node.value);
		nodeCopy.left  = copy(node.left);
		nodeCopy.right = copy(node.right);
		nodeCopy.height = Math.max(height(node.left), height(node.right)) + 1;
		return nodeCopy;
	}

	private Node<K,V> find(Node<K,V> node, K key) {
		if (key.compareTo(node.key) == 0) {
			return node;
		} else if (key.compareTo(node.key) < 0) {
			return find(node.left, key);
		} else {
			return find(node.right, key);
		}
	}
	
	private Node<K,V> insert(Node<K,V> node, K key, V value) {
		if (node == null) {
			return new Node<K, V>(key, value);
		} else if (key.compareTo(node.key) < 0) {
			node.left = insert(node.left, key, value);
		} else if (key.compareTo(node.key) > 0) {
			node.right = insert(node.right, key, value); 
		} else {
			/* ignore duplicate keys */
		}
		return balance(node);
	}
	
	private Node<K,V> remove(Node<K,V> node, K key) {
		if (node == null) {
			return null;
		} else if (key.compareTo(node.key) < 0) {
			node.left = remove(node.left, key);
		} else if (key.compareTo(node.key) > 0) {
			node.right = remove(node.right, key);
		} else {
			// found node to delete
			if (node.hasBoth()) {
				Node<K,V> successor = findMin(node.right);
				node.key = successor.key;
				node.value = successor.value;
				node.right = remove(node.right, node.key);
			} else if (node.hasLeft()) {
				node = node.left;
			} else if (node.hasRight()) {
				node = node.right;
			} else {
				// node to delete is a leaf
				node = null;
			}
		}
		return balance(node);
	}
	
	private void modifyValue(Node<K,V> node, K key, V newValue) {
		if (key.compareTo(node.key) == 0) {
			node.value = newValue;
		} else if (key.compareTo(node.key) < 0) {
			modifyValue(node.left, key, newValue);
		} else {
			modifyValue(node.right, key, newValue);
		}
	}
	
	/*
	 * This method keeps tree height-balanced according to
	 * the AVL tree data structure constraints.
	 */
	private Node<K,V> balance(Node<K,V> node) {
		if (node == null) {
			return null;
		}
		
		if (height(node.left) - height(node.right) == L_HEAVY) {
			if (height(node.left.left) - height(node.left.right) == LL_HEAVY) {
				/*         o
				 *        /
				 *       o
				 *      /
				 *     o
				 */
				node = rotateRight(node);
			} else {
				/*       o
				 *      /
				 *     o
				 *      \
				 *       o
				 */
				node = rotateLeftRight(node);
			}
		} else if (height(node.left) - height(node.right) == R_HEAVY) {
			if (height(node.right.left) - height(node.right.right) == RR_HEAVY) {
				/*     o
				 *      \
				 *       o
				 *        \
				 *         o
				 */
				node = rotateLeft(node);
			} else {
				/*     o
				 *      \
				 *       o
				 *      /
				 *     o
				 */
				node = rotateRightLeft(node);
			}
		}
		
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return node;
	}
	
	private Node<K,V> rotateLeft(Node<K,V> node) {
		if (node.hasRight()) {		
			Node<K,V> pivot = node.right;
			node.right = pivot.left;
			pivot.left = node;
			node.height = Math.max(height(node.left), height(node.right)) + 1;
			pivot.height = Math.max(height(pivot.left), height(pivot.right)) + 1;
			return pivot;
		}
		return node;
	}
	
	private Node<K,V> rotateRight(Node<K,V> node) {
		if (node.hasLeft()) {
			Node<K,V> pivot = node.left;
			node.left = pivot.right;
			pivot.right = node;
			node.height = Math.max(height(node.left), height(node.right)) + 1;
			pivot.height = Math.max(height(pivot.left), height(pivot.right)) + 1;
			return pivot;
		}
		return node;
	}
	
	private Node<K,V> rotateLeftRight(Node<K,V> node) {
		node.left = rotateLeft(node.left);
		return rotateRight(node);
	}
	
	private Node<K,V> rotateRightLeft(Node<K,V> node) {
		node.right = rotateRight(node.right);
		return rotateLeft(node);
	}
	
	private int height(Node<K,V> node) {
		if (node == null) {
			return -1;
		}
		return node.height;
	}
	
	private boolean contains(Node<K,V> node, K key) {
		if (node == null) {
			return false;
		} else if (key.compareTo(node.key) == 0) {
			return true;
		} else if (key.compareTo(node.key) < 0) {
			return contains(node.left, key);
		} else {
			return contains(node.right, key);
		}
	}
	
	private Node<K,V> findMin(Node<K,V> node) {
		if (node == null) {
			return null;
		} else if (node.left == null) {
			return node;
		}
		return findMin(node.left);
	}
	
	private Node<K,V> findMax(Node<K,V> node) {
		if (node == null) {
			return null;
		} else if (node.right == null) {
			return node;
		}
		return findMax(node.right);
	}
	
	private void print(Node<K,V> node, List<StringBuilder> output, int left, int top, int width) {
		// positional variables for drawing output
		int center = left + width / 2;
		int leftCenter = left + (width / 2) / 2;
		int rightCenter = center + (width / 2) / 2 + 1;
		
		String nodeStr = node.key.toString();		
		if (nodeStr.length() > CHARS_PER_NODE - 1) {
			System.err.println("Error: printing can only handle entries with "
					+ (CHARS_PER_NODE - 1) + " digits or less");
			return;
		} else if (nodeStr.length() == 1) {
			output.get(top).setCharAt(center, nodeStr.charAt(0));
		} else {
			for (int i = 0; i < nodeStr.length(); i++) {
				output.get(top).setCharAt(center - 1 + i, nodeStr.charAt(i));
			}
		}
		
		// print left child
		if (node.hasLeft()) {
			for (int i = leftCenter + 2; i < center - 1; i++) {
				output.get(top).setCharAt(i, '_');
			}
			output.get(top + 1).setCharAt(leftCenter + 1, '/');
			print(node.left, output, left, top + 2, width / 2);
		}
		
		// print right child
		if (node.hasRight()) {
			for (int i = center + 2; i < rightCenter - 1; i++) {
				output.get(top).setCharAt(i, '_');
			}
			output.get(top + 1).setCharAt(rightCenter - 1, '\\');
			print(node.right, output, center + 1, top + 2, width / 2);
		}
	}
	
	/*
	 * The internal node class for this tree. Each node
	 * stores a unique key, value, and height for the subtree 
	 * rooted at that node, as well as references to left and 
	 * right children.
	 */
	private static class Node<K extends Comparable<? super K>, V> {
		private K key;
		private V value;
		private Node<K, V> left;
		private Node<K, V> right;
		private int height;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			left = null;
			right = null;
			height = 0;
		}
		
		public boolean hasLeft() {
			return left != null;
		}
		
		public boolean hasRight() {
			return right != null;
		}
		
		public boolean hasBoth() {
			return hasLeft() && hasRight();
		}
	}
	
}
