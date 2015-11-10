package trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

public class AVLTreeDemo {
	
	public static final int NODES = 10;
	
	public static List<Integer> randomList() {
		List<Integer> list = new ArrayList<Integer>(NODES);
		for (int i = 0; i < NODES; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		return list;
	}
	
	public static void testEmptyTree() {
		BinarySearchTree<Integer, Integer> emptyTree = new AVLTree<Integer, Integer>();
		emptyTree.print();
		assert emptyTree.isEmpty();
		assert emptyTree.size() == 0;
		assert emptyTree.height() == -1;
		assert emptyTree.contains(0) == false;
		assert emptyTree.find(0) == null;
		assert emptyTree.remove(0) == null;
	}
	
	public static void testInsert(BinarySearchTree<Integer, Integer> tree) {
		List<Integer> list = randomList();
		for (int elem : list) {
			tree.insert(elem, elem);
			tree.print();
			System.out.println("balanced? " + (tree.isBalanced() ? "yes" : "no"));
		}		
	}
	
	public static void testFind(BinarySearchTree<Integer, Integer> tree) {
		for (int i = 0; i < NODES; i++) {
			assert tree.find(i) != null;
			int value = tree.find(i);
			System.out.println("Found node with key " + i + ". Its value is " + value);
		}
	}
	
	public static void testRemove(BinarySearchTree<Integer, Integer> tree) {
		for (int i = 0; i < NODES; i++) {
			assert tree.remove(i) != null;
			int value = tree.remove(i);
			System.out.println("Removed node with key " + i + ". Its value was " + value);
			tree.print();
		}
	}
	
	public static void testOthers() {
		BinarySearchTree<Integer, Integer> tree = new AVLTree<Integer, Integer>();
		List<Integer> list = randomList();
		
		System.out.print("Inserting ");
		for (int i : list) {
			System.out.print("(" + i + "," + 2*i + ") ");
			tree.insert(i, 2*i);
		}
		System.out.println("to tree");
		
		tree.print();
		System.out.println("Size of tree is " + tree.size());
		System.out.println("Height of tree is " + tree.height());
		
		List<Integer> keys = tree.keys();
		List<Integer> values = tree.values();
		List<Entry<Integer, Integer>> entries = tree.entries();
		System.out.println("Keys for this tree: " + keys);
		System.out.println("Values for this tree: " + values);
		System.out.println("Key-Value entries for this tree: " + entries);
		
		List<List<Integer>> levelOrderKeys = tree.levelOrderKeys();
		List<List<Entry<Integer, Integer>>> levelOrderEntries = tree.levelOrderEntries();
		System.out.println("Level-order key groupings for this tree: " + levelOrderKeys);
		System.out.println("Level-order key-value entries for this tree: " + levelOrderEntries);
		System.out.println();
		
		int middle = NODES / 2;
		System.out.println("Next lower key to " + middle + " is " + tree.lowerKey(middle));
		System.out.println("Next higher key to " + middle + " is " + tree.higherKey(middle));
		System.out.println("Min key is " + tree.minKey());
		System.out.println("Max key is " + tree.maxKey());
		
	}
	
	public static void testCopyConstructor() {
		AVLTree<Integer, Integer> originalTree = new AVLTree<Integer, Integer>();
		List<Integer> list = randomList();
		
		for (int elem : list) {
			originalTree.insert(elem, elem);
		}
		
		System.out.println("Original Tree:");
		originalTree.print();
		
		AVLTree<Integer, Integer> treeCopy = new AVLTree<Integer, Integer>(originalTree);
		System.out.println("Copied Tree:");
		treeCopy.print();
		
		System.out.println("Removing min and max from copied tree...");
		treeCopy.remove(treeCopy.minKey());
		treeCopy.remove(treeCopy.maxKey());
		
		System.out.println("Copied Tree:");
		treeCopy.print();
		
		System.out.println("Original Tree:");
		originalTree.print();
	}

	public static void main(String[] args) {
		System.out.println("Testing empty tree...");
		testEmptyTree();
		System.out.println();
		
		BinarySearchTree<Integer, Integer> tree = new AVLTree<Integer, Integer>();
		
		System.out.println("Testing insert operation...");
		testInsert(tree);
		System.out.println();
		
		System.out.println("Testing find operation...");
		testFind(tree);
		System.out.println();
		
		System.out.println("Testing remove operation...");
		testRemove(tree);
		System.out.println();
		
		System.out.println("Testing other operations...");
		testOthers();
		System.out.println();
		
		System.out.println("Testing copy constructor...");
		testCopyConstructor();
		System.out.println();
	}

}
