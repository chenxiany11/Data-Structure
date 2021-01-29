package buildtree;

import java.util.Stack;

/**
 * 
 * @author Matt Boutell and <<<Shuai Yuan>>>.
 * @param <T>
 */

public class BinaryTree {

	private BinaryNode root;

	private final BinaryNode NULL_NODE = new BinaryNode(null);

	public BinaryTree() {
		root = NULL_NODE;
	}

	/**
	 * Constructs a tree (any tree of characters, not just a BST) with the given
	 * values and number of children, given in a pre-order traversal order. See
	 * the HW spec for more details.
	 * 
	 * @param chars
	 *            One char per node.
	 * @param children
	 *            L,R, 2, or 0.
	 */
	public BinaryTree(String chars, String children) {
		// TO DO: Implement this constructor. You may not add any other fields to
		// the BinaryTree class, but you may add local variables and helper
		// methods if you like.
		Stack<BinaryNode> temp = new Stack<BinaryNode>();
		if (chars.equals("") || children.equals("")) this.root=NULL_NODE;
		else {
			for (int i = chars.length() - 1; i >= 0; i--) {
				BinaryNode n = new BinaryNode(chars.charAt(i));
				if (children.charAt(i) == '0') temp.push(n);
				
				if (children.charAt(i) == 'L') {
					BinaryNode a = temp.pop();
					n.left = a;
					temp.push(n);
				}
				
				if (children.charAt(i) == 'R') {
					BinaryNode a = temp.pop();
					n.right = a;
					temp.push(n);
				}
				
				if (children.charAt(i) == '2') {
					BinaryNode a = temp.pop();
					BinaryNode c = temp.pop();
					n.right = c;
					n.left = a;
					temp.push(n);
				}
			}
			this.root = temp.pop();
		}
	}

	/**
	 * In-order traversal of the characters
	 */
	@Override
	public String toString() {
		return root.toString();
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

		public Character data;
		public BinaryNode left;
		public BinaryNode right;

		public BinaryNode(Character element) {
			this.data = element;
			this.left = NULL_NODE;
			this.right = NULL_NODE;
		}

		@Override
		public String toString() {
			if (this == NULL_NODE) {
				return "";
			}
			return left.toString() + data + right.toString();
		}

		public String toStructuredString() {
			if (this == NULL_NODE) {
				return "";
			}
			return "(" + left.toStructuredString() + this.data
					+ right.toStructuredString() + ")";
		}

	}
}