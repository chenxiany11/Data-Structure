/**
 * More Binary Tree practice problems. This problem creates BSTs of type
 * Integer: 1. Neither problem makes use of the BST ordering property; I just
 * found insert() to be a convenient way to build trees for testing. 2. I used
 * Integer instead of T since the makeTree method sets the data value of each
 * node to be a depth, which is an Integer.
 * 
 * @author Matt Boutell and <<<YOUR NAME HERE>>>.
 * @param <T>
 */

/*
 * TO DO: 0 You are to implement the methods below. Use recursion!
 */
public class BinarySearchTree {

	private BinaryNode root;

	private final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinarySearchTree() {
		root = NULL_NODE;
	}

	/**
	 * This constructor creates a full tree of Integers, where the value of each
	 * node is just the depth of that node in the tree.
	 * 
	 * @param maxDepth
	 *            The depth of the leaves in the tree.
	 */
	public BinarySearchTree(int maxDepth) {
		// TO DO: 1 Write this.
		// Hint: You may find it easier if your recursive helper method is
		// outside of the BinaryNode class.
		if (maxDepth == -1) {
			this.root = NULL_NODE;
			return;
		}
		if (maxDepth == 0) {
			this.root = new BinaryNode(0);
		} else {
			this.root = new BinaryNode(0);
			this.node(this.root, 1, maxDepth + 1);
		}
	}
	
	public BinaryNode node(BinaryNode temp, int data, int max) {
		if (data == max) return temp;
		temp.left = new BinaryNode(data);
		temp.left = node(temp.left, data + 1, max);
		temp.right = new BinaryNode(data);
		temp.right = node(temp.right, data + 1, max);
		return temp;

	}

	public int getSumOfHeights() {
		// TO DO. 2 Write this.
		// Can you do it in O(n) time instead of O(n log n) by avoiding repeated
		// calls to height()?
		return this.root.getSumOfHeights(this.root);
	}

	// These are here for testing.
	public void insert(Integer e) {
		root = root.insert(e);
	}

	/**
	 * @return A string showing an in-order traversal of nodes with extra
	 *         brackets so that the structure of the tree can be determined.
	 */
	public String toStructuredString() {
		return root.toStructuredString();
	}

	// /////////////// BinaryNode
	public class BinaryNode {

		public Integer data;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(Integer element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		public int getSumOfHeights(BinaryNode temp) {
			if (temp == NULL_NODE) return 0;
			return getSumOfHeights(temp.left) + temp.getheight() + getSumOfHeights(temp.right);
		}

		private int getheight() {
			if (this == NULL_NODE) return -1;
			return 1 + Math.max(this.left.getheight(), this.right.getheight());
		}

		public BinaryNode insert(Integer e) {
			if (this == NULL_NODE) {
				return new BinaryNode(e);
			} else if (e.compareTo(data) < 0) {
				left = left.insert(e);
			} else if (e.compareTo(data) > 0) {
				right = right.insert(e);
			} else {
				// do nothing
			}
			return this;
		}

		public String toStructuredString() {
			if (this == NULL_NODE) {
				return "";
			}
			return "[" + left.toStructuredString() + this.data
					+ right.toStructuredString() + "]";
		}

	}
}