package editortrees;

// A node in a height-balanced binary tree with rank.
// Except for the NULL_NODE (if you choose to use one), one node cannot
// belong to two different trees.

public class Node {
	
	enum Code {
		SAME, LEFT, RIGHT;
		// Used in the displayer and debug string
		public String toString() {
			switch (this) {
				case LEFT:
					return "/";
				case SAME:
					return "=";
				case RIGHT:
					return "\\";
				default:
					throw new IllegalStateException();
			}
		}
	}
	///////////////////////////////////Constructor//////////////////////////////
	public Node() {
		this.left = NULL_NODE;
		this.right = NULL_NODE;
	}
	
	public Node(char ch) {
		this.left = NULL_NODE;
		this.right = NULL_NODE;
		this.balance = Code.SAME;
		this.rank = 0;
		this.element = ch;
		this.check = false;
	}
	
	// The fields would normally be private, but for the purposes of this class, 
	// we want to be able to test the results of the algorithms in addition to the
	// "publicly visible" effects
	public final static Node NULL_NODE = new Node();
	private boolean check;
	char element;            
	Node left, right; // subtrees
	int rank;         // inorder position of this node within its own subtree.
	Code balance; 
	// Node parent;  // You may want this field.
	// Feel free to add other fields that you find useful

	// You will probably want to add several other methods

	// For the following methods, you should fill in the details so that they work correctly
	// get the height of the tree
	public int height() {
		if (this == NULL_NODE) return -1;
		if (this == NULL_NODE || this.right == NULL_NODE && this.left == NULL_NODE) return 0;
		if (this.balance == Node.Code.LEFT) return 1 + this.left.height();
		return 1 + this.right.height();
	}
	
	// get the size of the tree
	public int size() {
		if (this == NULL_NODE) return 0;
		return this.rank + this.right.size() + 1;
	}
	
	// deletion wrapper
	public class Dwrap {
		public boolean balanced;
		public char deleted;
		public int count;
		
		public Dwrap() {
			this.balanced = false;
			this.deleted = '\u0000';
			this.count = 0;
		}

	}

	// deletion
	public Dwrap delete(int pos) {
		boolean check = false;
		Dwrap result = new Dwrap();
		if (pos < rank) {
			this.rank--;
			result = left.delete(pos);
			if (left.element == result.deleted) {
				if (left.left == NULL_NODE || left.right == NULL_NODE) {
					if (left.isLeaf()) left = NULL_NODE;
					else if (left.right == NULL_NODE) left = left.left;
					else if (left.left == NULL_NODE) left = left.right;
				}
			}
			if (!result.balanced && !this.check) result.balanced = this.rotation(true, result);
			this.check = false;

		} else if (pos > rank) {
			result = right.delete(pos - (1 + rank));
			// does right have a null child?
			if (right.element == result.deleted) {
				if (right.left == NULL_NODE || right.right == NULL_NODE) {
					if (right.isLeaf()) {
						right = NULL_NODE;
					} else if (right.right == NULL_NODE) {
						right = right.left;
					} else if (right.left == NULL_NODE) {
						right = right.right;
					}
				}
			}
			if (!result.balanced && !this.check) {
				result.balanced = this.rotation(false, result);
			}
			this.check = false;
		} else if (this.rank == pos) {
			result.deleted = this.element;
			if (this.right != NULL_NODE) {
				this.check = true;
				Dwrap tempWrapper = this.delete(pos + 1);
				this.element = tempWrapper.deleted;
				result.count = tempWrapper.count;
				result.balanced = tempWrapper.balanced;
				if (!result.balanced && !this.check) {
					result.balanced = this.rotation(false, result);
				}
			}
		}
		return result;
	}

	// check the node is lead or not
	public boolean isLeaf() {
		return this.right == NULL_NODE && this.left == NULL_NODE;
	}

	public char getpos(int pos) {
		if (pos == this.rank) return this.element;
		if (pos < this.rank) {
			if (this.left == NULL_NODE) throw new IndexOutOfBoundsException();
			return this.left.getpos(pos);
		}
		if (this.right == NULL_NODE) throw new IndexOutOfBoundsException();
		return this.right.getpos(pos - this.rank - 1);
	}
	
	// update balance code and rotate
	private boolean rotation(boolean check, Dwrap d) {
		if (check) {
			switch (this.balance) {
			case LEFT:
				this.balance = Code.SAME;
				return false;
			case RIGHT:
				if (this.right.balance == Code.LEFT) {
					d.count++;
					this.right.singleRightRotation();
				}
				d.count++;
				this.singleLeftRotation();
				int l = this.left.height();
				int r = this.right.height();

				if (l > r) {
					this.balance = Node.Code.LEFT;
					return true;
				}
				if (l < r) {
					this.balance = Node.Code.RIGHT;
					return true;
				}
				if (l == r)
					this.balance = Node.Code.SAME;
				return false;
			case SAME:
				this.balance = Code.RIGHT;
				return true;
			}
		} 
		else {
			switch (this.balance) {
			case LEFT:
				if (this.left.balance == Code.RIGHT) {
					d.count++;
					this.left.singleLeftRotation();
				}
				d.count++;
				this.singleRightRotation();
				int leftHeight = this.left.height();
				int rightHeight = this.right.height();

				if (leftHeight > rightHeight) {
					this.balance = Node.Code.LEFT;
					return true;
				}
				if (leftHeight < rightHeight) {
					this.balance = Node.Code.RIGHT;
					return true;
				}
				if (leftHeight == rightHeight)
					this.balance = Node.Code.SAME;
				return false;
			case RIGHT:
				this.balance = Code.SAME;
				return false;
			case SAME:
				this.balance = Code.LEFT;
				return true;
			}
		}
		return false;
	}

	// single rotate right
	public void singleRightRotation() {
		char temp = this.element;
		this.element = this.left.element;
		Node newRight = new Node(temp);
		newRight.rank = this.rank - (this.left.rank + 1);
		this.rank = this.left.rank;
		newRight.right = this.right;
		newRight.left = this.left.right;
		this.right = newRight;
		this.left = this.left.left;
		if (this.left != Node.NULL_NODE) {
			if (this.left.left.height() > this.left.right.height()) this.left.balance = Node.Code.LEFT;
			else if (this.left.left.height() < this.left.right.height()) this.left.balance = Node.Code.RIGHT;
			else if (this.left.left.height() == this.left.right.height()) this.left.balance = Node.Code.SAME;
		}
		this.balance = Code.SAME;
		if (this.right != Node.NULL_NODE) {
			if (this.right.left.height() > this.right.right.height()) this.right.balance = Node.Code.LEFT;
			else if (this.right.left.height() < this.right.right.height()) this.right.balance = Node.Code.RIGHT;
			else this.right.balance = Node.Code.SAME;
		}
	}

	// single rotate left
	public void singleLeftRotation() {
		char temp = this.element;
		this.element = this.right.element;
		Node newLeft = new Node(temp);
		newLeft.rank = this.rank;
		this.rank = this.rank + this.right.rank + 1;
		newLeft.left = this.left;
		newLeft.right = this.right.left;
		this.left = newLeft;
		this.right = this.right.right;
		if (this.right != Node.NULL_NODE) {
			if (this.right.left.height() > this.right.right.height()) this.right.balance = Node.Code.LEFT;
			else if (this.right.left.height() < this.right.right.height()) this.right.balance = Node.Code.RIGHT;
			else this.right.balance = Node.Code.SAME;
		}
		this.balance = Code.SAME;
		if (this.left != Node.NULL_NODE) {
			if (this.left.left.height() > this.left.right.height()) this.left.balance = Node.Code.LEFT;
			else if (this.left.left.height() < this.left.right.height()) this.left.balance = Node.Code.RIGHT;
			else this.left.balance = Node.Code.SAME;
		}
	}

	// convert the tree to string
	public String toString() {
		if (this == NULL_NODE) return "";
		StringBuilder s = new StringBuilder();
		s.append(this.left.toString());
		s.append(this.element);
		s.append(this.right.toString());
		return s.toString();
	}

	// copy the tree
	public void copy(Node node) {
		if (node.left != NULL_NODE) {
			this.left = new Node(node.left.element);
			this.left.rank = node.left.rank;
			this.left.balance = node.left.balance;
			this.left.copy(node.left);
		}
		if (node.right != NULL_NODE) {
			this.right = new Node(node.right.element);
			this.right.rank = node.right.rank;
			this.right.balance = node.right.balance;
			this.right.copy(node.right);
		}
	}

	// convert the tree to debug string
	public String toDebugString() {
		if (this == NULL_NODE) return "";
		StringBuilder s = new StringBuilder();
		s.append(this.element + "" + this.rank + "" + this.balance + ", ");
		s.append(this.left.toDebugString());
		s.append(this.right.toDebugString());
		return s.toString();
	}

	// get the node using position
	public Node get(int pos) {
		if (this.rank == pos) return this;
		if (this.rank < pos) return this.right.get(pos - (rank + 1));	
		return this.left.get(pos);
	}

	//////////////////////////////////mile 3/////////////////////////////////////
	public void constuctor(String s) {
		int index = s.length() / 2;
		if (s.length() == 1) {
			this.element = s.charAt(0);
			this.balance = Node.Code.SAME;
			this.rank = 0;
			return;
		}
		this.element = s.charAt(index);
		String l = s.substring(0, index);
		String r = s.substring(index + 1);
		this.rank = l.length();
		if (Math.floor(Math.log(l.length()) / Math.log(2)) > Math
				.floor(Math.log(r.length()) / Math.log(2))) this.balance = Node.Code.LEFT;
		else if (Math.floor(Math.log(l.length()) / Math.log(2)) < Math
				.floor(Math.log(r.length()) / Math.log(2))) this.balance = Node.Code.RIGHT;
		else this.balance = Node.Code.SAME;
		if (l.length() != 0) {
			this.left = new Node();
			this.left.constuctor(l);
		}
		if (r.length() != 0) {
			this.right = new Node();
			this.right.constuctor(r);
		}

	}

	public boolean rotate(boolean check, Node node, int i) {
		if (this == node) {
			this.rank = this.left.size();
			return false;
		}
		if (check) {
			this.rank += i + 1;
			boolean balanced = this.left.rotate(check, node, i);
			if (!balanced) {
				switch (this.balance) {
				case LEFT:
					this.singleRightRotation();
					return true;
				case RIGHT:
					this.balance = Code.SAME;
					return true;
				case SAME:
					this.balance = Code.LEFT;
					return false;
				}
			}
		} else {
			boolean balanced = this.right.rotate(check, node, i);
			if (!balanced) {
				switch (this.balance) {
				case LEFT:
					this.balance = Code.SAME;
					return true;
				case RIGHT:
					this.singleLeftRotation();
					return true;
				case SAME:
					this.balance = Code.RIGHT;
					return false;
				}
			}
		}
		return false;
	}



}