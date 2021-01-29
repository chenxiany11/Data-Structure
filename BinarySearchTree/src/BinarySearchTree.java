import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 
 * Implementation of most of the Set interface operations using a Binary Search
 * Tree
 *
 * @author Matt Boutell and <<< Shuai Yuan >>>.
 * @param <T>
 */

public class BinarySearchTree<T extends Comparable<T>> {
	private BinaryNode root;
	private final BinaryNode NULL_NODE = new BinaryNode();
	private int count1;

	public BinarySearchTree() {
		count1 = 0;
		this.root = this.NULL_NODE; // NULL_NODE;
	}

	// manual tests
	void setRoot(BinaryNode n) {
		this.root = n;
	}

	public int size() {
		return this.root.size();
	}

	// call isEmpty method
	public boolean isEmpty() {
		return this.root == NULL_NODE;
	}

	//	call the height method
	public int height() {
		return this.root.height();
	}

	// call the containNonBST method
	public boolean containsNonBST(T item) {
		return this.root.containsNonBST(item);
	}
	
	// call the toArrayList method
	public ArrayList<T> toArrayList() {
		return this.root.toArrayList();
	}

	// call the toArray method
	public Object[] toArray() {
		return this.root.toArray();
	}

	// call the toString method
	public String toString() {
		return this.root.toArrayList().toString();
	}

	// call the contain method
	public boolean contains(T i) {
		return this.root.containsNonBST(i);
	}
	
	// call the arraylist iterator
	public Iterator<T> inefficientIterator() {
		return new arrayListIterator();
	}
	
	// call the inorder iterator
	public Iterator<Integer> iterator() {
		Inorder temp = new Inorder(this.root);
		return temp;
	}
	
	// call the prorder iterator
	public Iterator<Integer> preOrderIterator() {
		return new Preorder(this.root);
	}
	
	// call the insert medthod
	public boolean insert(T item) {
		if (item == null) throw new IllegalArgumentException();
		count1++;
		Boolean check = new Boolean(false);
		this.root = this.root.insert(item, check);
		return check.value;

	}
	
	// call the remove
	public boolean remove(T i) {
		if (i == null) throw new IllegalArgumentException();
		count1++;
		Boolean check = new Boolean(false);
		this.root = this.root.remove(i, check);
		return check.value;
	}
	
	/*************************************************************************
	 * BinaryNode class
	 * Not private class, because we need access for manual testing.
	 *************************************************************************/
	class BinaryNode {
		private T data;
		private BinaryNode left;
		private BinaryNode right;

		public BinaryNode() {
			this.data = null;
			this.left = null;
			this.right = null;
		}

		public BinaryNode(T element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		/*
		 * For manual testing
		 */
		public void setLeft(BinaryNode left) {
			this.left = left;
		}

		public void setRight(BinaryNode right) {
			this.right = right;
		}

		// return the size of BST
		public int size() {
			if (this == NULL_NODE)
				return 0;
			return 1 + this.left.size() + this.right.size();
		}

		// return the height of BST
		public int height() {
			if (this == NULL_NODE)
				return -1;
			return 1 + Math.max(this.left.height(), this.right.height());
		}

		// check item is in BST or not
		public boolean containsNonBST(T item) {
			if (this == NULL_NODE)
				return false;
			return this.data.equals(item) || this.left.containsNonBST(item) || this.right.containsNonBST(item);
		}

		// convert BST to arraylist
		public ArrayList<T> toArrayList() {
			ArrayList<T> a = new ArrayList<T>();
			if (this == NULL_NODE)
				return a;
			a.addAll(this.left.toArrayList());
			a.add(this.data);
			a.addAll(this.right.toArrayList());
			return a;
		}

		// insert an element into the tree
		public BinaryNode insert(T item, Boolean check) {
			if (this == NULL_NODE) {
				check.value = true;
				return new BinaryNode(item);
			}
			int comp = item.compareTo(this.data);
			if (comp < 0)
				this.left = this.left.insert(item, check);
			else if (comp > 0)
				this.right = this.right.insert(item, check);
			else
				check.value = false;
			return this;
		}

		// convert BST to array
		public Object[] toArray() {
			Object[] a = this.toArrayList().toArray();
			return a;
		}

		// find the max value in the tree by going down to the right
		public BinaryNode max(BinaryNode temp) {
			BinaryNode m = temp;
			while (m.right != NULL_NODE)
				m = m.right;
			return m;
		}

		// remove an element from the tree
		public BinarySearchTree<T>.BinaryNode remove(T item, Boolean check) {
			if (!this.containsNonBST(item)) {
				check.value = false;
				return NULL_NODE;
			}
			int comp = item.compareTo(this.data);
			if (comp < 0)
				this.left = this.left.remove(item, check);
			else if (comp > 0)
				this.right = this.right.remove(item, check);
			else {
				check.value = true;
				if (this.right == NULL_NODE)
					return this.left;
				else if (this.left == NULL_NODE)
					return this.right;
				else {
					BinaryNode m = max(this.left);
					this.data = m.data;
					this.left = this.left.remove(m.data, check);
					check.value = true;
					return this;
				}
			}
			return this;
		}
	}
	
	/*************************************************************************
	 *  Contain a boolean 
	 *  Determined whether insert or remove was called or not
	 *************************************************************************/
	private class Boolean {
		private boolean value;

		public Boolean(boolean val) {
			this.value = val;
		}
	}
	
	/*************************************************************************
	 * iterators
	 *************************************************************************/
	// arraylist iterator
	private class arrayListIterator implements Iterator<T> {
		private ArrayList<T> a;
		int index;

		private arrayListIterator() {
			a = BinarySearchTree.this.root.toArrayList();
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < a.size();
		}

		@Override
		public T next() {
			if (this.hasNext()) return a.get(index++);
			else throw new NoSuchElementException();
		}
	}

	// inorder iterator
	public class Inorder implements Iterator<Integer> {
		Stack<BinaryNode> stack;
		boolean isCalled;
		int count2;
		BinaryNode root;
		T temp;

		public Inorder(BinaryNode root) {
			count2 = count1;

			isCalled = false;
			this.root = root;
			stack = new Stack<BinaryNode>();
			while (root != NULL_NODE) {
				stack.push(root);
				root = root.left;
			}

		}

		public boolean hasNext() {
			return !stack.isEmpty();
		}
		// remove method in the iterator
		public void remove() {
			if (!isCalled) {
				throw new IllegalStateException();
			}
			Boolean check = new Boolean(false);
			this.root.remove(temp, check);
			isCalled = false;
		}

		public Integer next() {
			if (!hasNext()) throw new NoSuchElementException();
		
			if (count1 != count2) throw new ConcurrentModificationException();
			
			BinaryNode node = stack.pop();
			T result = node.data;
			temp = result;
			if (node.right != NULL_NODE) {
				node = node.right;
				while (node != NULL_NODE) {
					stack.push(node);
					node = node.left;
				}
			}
			isCalled = true;
			return (Integer) result;
		}
	}
	
	// preorder iterator
	public class Preorder implements Iterator<Integer> {
		Stack<BinaryNode> prestack;
		int count3;
		T temp;
		BinaryNode root;
		boolean isCalled;

		public Preorder(BinaryNode root) {
			count3 = count1;
			isCalled = false;
			prestack = new Stack<BinaryNode>();
			if (root != NULL_NODE) {
				prestack.push(root);
			}
			this.root = root;
		}

		public boolean hasNext() {
			return !prestack.isEmpty();
		}

		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			if (count1 != count3) {
				throw new ConcurrentModificationException();
			}
			BinaryNode result = prestack.pop();
			if (result.right != NULL_NODE)
				prestack.push(result.right);
			if (result.left != NULL_NODE)
				prestack.push(result.left);
			temp = result.data;
			isCalled = true;
			return (Integer) result.data;
		}
		// remove method in the iterator
		public void remove() {
			if (!isCalled) {
				throw new IllegalStateException();
			}
			Boolean check = new Boolean(false);
			this.root.remove(temp, check);
			isCalled = false;
		}

	}
	

}
