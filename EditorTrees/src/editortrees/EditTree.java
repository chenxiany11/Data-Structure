package editortrees;
/**
 * Each piece of text will be represented as a height-balanced tree with rank. 
 * Each node contains a single character. 
 * EditTree is not an AVL tree in the traditional sense
 * since the order of nodes reflects their position within the tree's text 
 * rather than an alphabetical ordering of the characters in the tree.
 * @author Shuai Yuan
 * 
 */
import java.util.Stack;

// A height-balanced binary tree with rank that could be the basis for a text editor.

public class EditTree {

	private Node root;
	private int rotationCount;
	
	/////////////////////////////////////////////////Constructors//////////////////////////////////
	/**
	 * MILESTONE 1
	 * Construct an empty tree
	 */
	public EditTree() {
		this.root = Node.NULL_NODE;
		this.rotationCount = 0;
	}

	/**
	 * MILESTONE 1
	 * Construct a single-node tree whose element is ch
	 * 
	 * @param ch
	 */
	public EditTree(char ch) {
		this.root = new Node(ch);
		this.rotationCount = 0;
	}

	/**
	 * MILESTONE 2
	 * Make this tree be a copy of e, with all new nodes, but the same shape and
	 * contents.
	 * 
	 * @param tree
	 */
	public EditTree(EditTree tree) {
		if(tree.root != Node.NULL_NODE) {
			this.root = new Node(tree.root.element);
			this.root.balance = tree.root.balance;
			this.root.rank = tree.root.rank;
			this.rotationCount = tree.rotationCount;
			this.root.copy(tree.root);
		} else {
			this.root = Node.NULL_NODE;
			this.rotationCount = 0;
		}
	}

	/**
	 * MILESTONE 3
	 * Create an EditTree whose toString is s. This can be done in O(N) time,
	 * where N is the size of the tree (note that repeatedly calling insert() would be
	 * O(N log N), so you need to find a more efficient way to do this.
	 * 
	 * @param s
	 */
	public EditTree(String s) {
		int index = s.length()/2;
		this.root = new Node(s.charAt(index));	
		String l = s.substring(0, index);
		String r = s.substring(index + 1);
		root.rank = l.length();	
		double temp1 = Math.floor(Math.log(l.length()) / Math.log(2));
		double temp2 = Math.floor(Math.log(r.length()) / Math.log(2));
		if(temp1 > temp2) this.root.balance = Node.Code.LEFT;
		else if(temp1 < temp2)
			this.root.balance = Node.Code.RIGHT;	
		else
			this.root.balance = Node.Code.SAME;
		
		this.root.left = new Node();
		this.root.right = new Node();
		root.left.constuctor(l);
		root.right.constuctor(r);
	}
	
	
	/**
	 * MILESTONE 1
	 * returns the total number of rotations done in this tree since it was
	 * created. A double rotation counts as two.
	 *
	 * @return number of rotations since this tree was created.
	 */
	public int totalRotationCount() {
		return this.rotationCount; // replace by a real calculation.
	}

	/**
	 * MILESTONE 1
	 * return the string produced by an inorder traversal of this tree
	 */
	@Override
	public String toString() {
		return this.root.toString(); // replace by a real calculation.

	}

	/**
	 * MILESTONE 1
	 * This one asks for more info from each node. You can write it like 
	 * the arraylist-based toString() method from the
	 * BinarySearchTree assignment. However, the output isn't just the elements, 
	 * but the elements, ranks, and balance codes. Former CSSE230 students recommended
	 * that this method, while making it harder to pass tests initially, saves
	 * them time later since it catches weird errors that occur when you don't
	 * update ranks and balance codes correctly.
	 * For the tree with root b and children a and c, it should return the string:
	 * [b1=, a0=, c0=]
	 * There are many more examples in the unit tests.
	 * 
	 * @return The string of elements, ranks, and balance codes, given in
	 *         a pre-order traversal of the tree.
	 */
	public String toDebugString() {
		if (this.root.toDebugString().length() <= 2) return "[]";
		return "[" + this.root.toDebugString().substring(0, this.root.toDebugString().length() - 2) + "]";
	}

	/**
	 * MILESTONE 1
	 * @param ch
	 *            character to add to the end of this tree.
	 */
	public void add(char ch) {
		// Notes:
		// 1. Please document chunks of code as you go. Why are you doing what
		// you are doing? Comments written after the code is finalized tend to
		// be useless, since they just say WHAT the code does, line by line,
		// rather than WHY the code was written like that. Six months from now,
		// it's the reasoning behind doing what you did that will be valuable to
		// you!
		// 2. Unit tests are cumulative, and many things are based on add(), so
		// make sure that you get this one correct.
		if (this.root == Node.NULL_NODE) this.root = new Node(ch);
		else addWrap(this.root, ch);
	}
	
	public boolean addWrap(Node node, char ch){ 
		if (node == Node.NULL_NODE) return false;
		boolean check = false;
		if (node.right == Node.NULL_NODE) {
			node.right = new Node(ch);
			switch (node.balance) {
			case LEFT :
				node.balance = Node.Code.SAME;
				return true;
			case SAME :
				node.balance = Node.Code.RIGHT;
				break;
			}
			return false;
		} 
		check = addWrap(node.right, ch);
		if(!check) {
			switch (node.balance) {
			case LEFT :
				node.balance = Node.Code.SAME;
				return true;
			case RIGHT :
				node.singleLeftRotation();
				this.rotationCount++;
				return true;
			case SAME :
				node.balance = Node.Code.RIGHT;
				return false;
			}
		}
		return true;
	}

	/**
	 * MILESTONE 1
	 * @param ch
	 *            character to add
	 * @param pos
	 *            character added in this inorder position
	 * @throws IndexOutOfBoundsException
	 *            if pos is negative or too large for this tree
	 */
	public void add(char ch, int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos > this.root.size()) throw new IndexOutOfBoundsException();
		if (this.root == Node.NULL_NODE) this.root = new Node(ch);
		else addWrap (this.root, ch, pos);
	}
	public boolean addWrap(Node node, char c, int pos) {
		if (node == Node.NULL_NODE) return false;
		boolean check = false;
		if (pos <= node.rank) {
			node.rank++;
			if (node.left == Node.NULL_NODE) {
				node.left = new Node(c);
				switch (node.balance) {
				case RIGHT :
					node.balance = Node.Code.SAME;
					return true;
				case SAME :
					node.balance = Node.Code.LEFT;
					break;
				}
				return false;
			}
			check = addWrap(node.left, c, pos);
			if(!check) {
				switch (node.balance) {
				case LEFT :
					if (node.left.balance == Node.Code.RIGHT) {
						this.rotationCount++;
						node.left.singleLeftRotation();
					}
					this.rotationCount++;
					node.singleRightRotation();
					return true;
				case RIGHT :
					node.balance = Node.Code.SAME;
					return true;
				case SAME :
					node.balance = Node.Code.LEFT;
					return false;
				}
			}
		} 
		else {
			if (node.right == Node.NULL_NODE) {
				node.right = new Node(c);
				switch (node.balance) {
				case LEFT :
					node.balance = Node.Code.SAME;
					return true;
				case SAME :
					node.balance = Node.Code.RIGHT;
					break;
				}
				return false;
			}
			check = addWrap(node.right, c, pos - (node.rank + 1));	
			if(!check) {
				switch (node.balance) {
				case LEFT :
					node.balance = Node.Code.SAME;
					return true;
				case RIGHT :
					if (node.right.balance == Node.Code.LEFT) {
						this.rotationCount++;
						node.right.singleRightRotation();
					}
					this.rotationCount++;
					node.singleLeftRotation();
					return true;
				case SAME :
					node.balance = Node.Code.RIGHT;
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * MILESTONE 1
	 * @param pos
	 *            position in the tree
	 * @return the character at that position
	 * @throws IndexOutOfBoundsException
	 */
	public char get(int pos) throws IndexOutOfBoundsException {
		if (this.root == Node.NULL_NODE) throw new IndexOutOfBoundsException();
		return this.root.getpos(pos);
	}

	/**
	 * MILESTONE 1
	 * @return the height of this tree
	 */
	public int height() {
		if (this.root == Node.NULL_NODE) return -1;
		return this.root.height(); // replace by a real calculation.
	}

	/**
	 * MILESTONE 2
	 * @return the number of nodes in this tree, 
	 *         not counting the NULL_NODE if you have one.
	 */
	public int size() {
		return this.root.size(); // replace by a real calculation.
	}
	///////////////////////////////Milestone 2////////////////////////////////////
	/**
	 * MILESTONE 2
	 * @param pos
	 *            position of character to delete from this tree
	 * @return the character that is deleted
	 * @throws IndexOutOfBoundsException
	 */
	public char delete(int pos) throws IndexOutOfBoundsException {
		// Implementation requirement:
		// When deleting a node with two children, you normally replace the
		// node to be deleted with either its in-order successor or predecessor.
		// The tests assume assume that you will replace it with the
		// *successor*.
		char c;
		if (pos < 0 || pos >= this.root.size()) throw new IndexOutOfBoundsException();	
		if (this.root.right == Node.NULL_NODE && this.root.left != Node.NULL_NODE) {
			if (pos == 0) {
				c = this.root.left.element;
				this.root.left = Node.NULL_NODE;
				return c;
			} 
			c = this.root.element;
			this.root = this.root.left;
			return c;
		} 
		else if (this.root.isLeaf()) {
			c = this.root.element;
			this.root = Node.NULL_NODE;
			return c;
		}
		Node.Dwrap d = root.delete(pos);
		this.rotationCount += d.count;
		return d.deleted;
	}
///////////////////////////////////////////////Milestone 3///////////////////////////////////////////
	/**
	 * MILESTONE 3, EASY
	 * This method operates in O(length*log N), where N is the size of this
	 * tree.
	 * 
	 * @param pos
	 *            location of the beginning of the string to retrieve
	 * @param length
	 *            length of the string to retrieve
	 * @return string of length that starts in position pos
	 * @throws IndexOutOfBoundsException
	 *             unless both pos and pos+length-1 are legitimate indexes
	 *             within this tree.
	 */
	public String get(int pos, int length) throws IndexOutOfBoundsException {
		String s =  new String();
		if (pos > this.size() - 1 || pos < 0 || pos + length > this.size()) throw new IndexOutOfBoundsException();
		for(int i = pos; i < pos + length; i++){
			s += this.root.get(i).element;
		}
		return s;
	}

	/**
	 * MILESTONE 3, MEDIUM - SEE THE PAPER REFERENCED IN THE SPEC FOR ALGORITHM!
	 * Append (in time proportional to the log of the size of the larger tree)
	 * the contents of the other tree to this one. Other should be made empty
	 * after this operation.
	 * 
	 * @param tree
	 * @throws IllegalArgumentException
	 *             if this == other
	 */
	public void concatenate(EditTree tree) throws IllegalArgumentException {
		char c = '\u0000';
		if(this == tree) throw new IllegalArgumentException();
		if (tree.root == Node.NULL_NODE) return;
		if (this.root == Node.NULL_NODE) {
			this.root = tree.root;
			tree.root = Node.NULL_NODE;
			return;
		}
		if (tree.root.isLeaf()) {
			this.add(tree.root.element);
			tree.root = Node.NULL_NODE;
			return;
		}
		if (this.height() >= tree.height()) c = tree.delete(0);
		else c = this.delete(this.size() - 1);
		con(this, new Node(c), tree);
		tree.root = Node.NULL_NODE;
	}
	
	public void con(EditTree tree1, Node node, EditTree tree2){
		Node n = tree1.root;
		Node parent = Node.NULL_NODE;
		int h;	
		int h1 = tree1.height();
		int h2 = tree2.height();
		if(h1 < h2){
			n = tree2.root;
			h = h2;
			while(h - h1 >= 1){
				if(n.balance == Node.Code.RIGHT) h =- 2;
				else h =- 1;
				parent = n;
				n = n.left;
			}
			node.right = n;
			node.left = tree1.root;
			if(h == h2) node.balance = Node.Code.SAME;
			else node.balance = Node.Code.RIGHT;
			if(parent != Node.NULL_NODE) parent.left = node;
			tree1.root = tree2.root;
			tree1.root.rotate(true, node, tree2.size());		
		}
		else{
			n = tree1.root;
			h = h1;
			while(h - h2 >= 1){
				if(n.balance == Node.Code.LEFT)h =- 2;
				else h =- 1;
				parent = n;
				n = n.right;
			}
			node.left = n;
			node.right = tree2.root;
			if(h == h2) node.balance = Node.Code.SAME;
			else node.balance = Node.Code.LEFT;
			if(parent != Node.NULL_NODE) parent.right = node;
			else tree1.root = node;
			node.rank = n.size();
			tree1.root.rotate(false, node, tree2.size());
		}
	}

	/**
	 * MILESTONE 3: DIFFICULT
	 * This operation must be done in time proportional to the height of this
	 * tree.
	 * 
	 * @param pos
	 *            where to split this tree
	 * @return a new tree containing all of the elements of this tree whose
	 *         positions are >= position. Their nodes are removed from this
	 *         tree.
	 * @throws IndexOutOfBoundsException
	 */
	public EditTree split(int pos) throws IndexOutOfBoundsException {
		EditTree tree = new EditTree();
		if (pos == 0) {
			tree.root = this.root;
			this.root = Node.NULL_NODE;
			return tree;
		}	
		pos--;	
		Stack<Node> stack = new Stack<Node>(); 
		Node temp = this.root;
		int i = this.root.rank + 1;
		while(true){
			if(temp.rank > pos){
				stack.push(temp);
				temp = temp.left;
			}
			else if(temp.rank < pos){
				stack.push(temp);
				pos -= (temp.rank + 1);
				temp = temp.right;
			}
			else{
				stack.push(temp);
				break;
			}
		}
		tree = splitTree(stack);	
		return tree;
	}
	
	public EditTree splitTree(Stack<Node> stack){
		Node node = stack.pop();
		Node child;
		EditTree t1 = new EditTree();
		EditTree t2 = new EditTree();
		Node l = node.left;
		Node r = node.right;
		t2.root = l;
		t1.root = r;
		t2.add(node.element);
		while(!stack.isEmpty()){
			child = node;
			node = stack.pop();
			if (child == node.right) {
				EditTree left = new EditTree();
				left.root = node.left;
				con(left, node, t2);
				t2 = left;
			} else {
				EditTree right = new EditTree();
				right.root = node.right;
				con(t1, node, right);
			}
		}
		this.root = t2.root;
		return t1;
	}

	/**
	 * MILESTONE 3: JUST READ IT FOR USE OF SPLIT/CONCATENATE
	 * This method is provided for you, and should not need to be changed. If
	 * split() and concatenate() are O(log N) operations as required, delete
	 * should also be O(log N)
	 * 
	 * @param start
	 *            position of beginning of string to delete
	 * 
	 * @param length
	 *            length of string to delete
	 * @return an EditTree containing the deleted string
	 * @throws IndexOutOfBoundsException
	 *             unless both start and start+length-1 are in range for this
	 *             tree.
	 */
	public EditTree delete(int start, int length)
			throws IndexOutOfBoundsException {
		if (start < 0 || start + length >= this.size())
			throw new IndexOutOfBoundsException(
					(start < 0) ? "negative first argument to delete"
							: "delete range extends past end of string");
		EditTree t2 = this.split(start);
		EditTree t3 = t2.split(length);
		this.concatenate(t3);
		return t2;
	}

	/**
	 * MILESTONE 3
	 * Don't worry if you can't do this one efficiently.
	 * 
	 * @param s
	 *            the string to look for
	 * @return the position in this tree of the first occurrence of s; -1 if s
	 *         does not occur
	 */
	public int find(String s) {
		return find(s, 0);
	}

	/**
	 * MILESTONE 3
	 * @param s
	 *            the string to search for
	 * @param pos
	 *            the position in the tree to begin the search
	 * @return the position in this tree of the first occurrence of s that does
	 *         not occur before position pos; -1 if s does not occur
	 */
	public int find(String s, int pos) {
		if (s.equals("")) return 0;
		String str = this.toString();
		char[] array = str.toCharArray();
		int index = 0;
		for(int i = pos; i < array.length; i++) {
			if (s.charAt(index) == array[i]) index++;
			else {
				i -= index;
				index = 0;
			}
			if (index == s.length()) return i - (index - 1);
		}
		return -1;
	}

	/**
	 * @return The root of this tree.
	 */
	public Node getRoot() {
		return this.root;
	}
}
