public class RedBlackTree<T extends Comparable<T>> {
	public static enum Color {
		BLACK, RED
	};

	private class Node {
		public Color color;
		public T data;

		public Node leftChild;
		public Node parent;
		public Node rightChild;

		public Node(T data, Node parent) {
			this.data = data;
			this.parent = parent;
			this.color = Color.RED;
		}

		public String toString() {
			return (leftChild == null ? "" : leftChild.toString() + ", ")
					+ data.toString()
					+ (rightChild == null ? "" : ", " + rightChild.toString());
		}
	}

	private Node root;

	public void add(T e) {
		if (root == null) {
			root = new Node(e, null);
			root.color = Color.BLACK;
			return;
		}

		Node cur = root;
		while (true) {
			if (cur.data.compareTo(e) > 0) {
				if (cur.leftChild == null) {
					cur.leftChild = new Node(e, cur);
					checkColor(cur.leftChild);
					return;
				} else
					cur = cur.leftChild;
			} else {
				if (cur.rightChild == null) {
					cur.rightChild = new Node(e, cur);
					checkColor(cur.rightChild);
					return;
				} else
					cur = cur.rightChild;
			}
		}
	}

	public boolean contains(T e) {
		Node cur = root;
		while (cur != null) {
			if (cur.data.compareTo(e) == 0)
				return true;
			if (cur.data.compareTo(e) > 0)
				cur = cur.leftChild;
			else
				cur = cur.rightChild;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj.toString());
	}

	@Override
	public String toString() {
		return "[" + root.toString() + "]";
	}

	private void checkColor(Node n) {
		if (n.parent == null) {
			n.color = Color.BLACK;
			return;
		}

		if (n.parent.color == Color.BLACK)
			return;

		if (uncle(n) != null && uncle(n).color == Color.RED) {
			n.parent.parent.color = Color.RED;
			n.parent.color = Color.BLACK;
			uncle(n).color = Color.BLACK;
			checkColor(n.parent.parent);
			return;
		}

		if (n.parent.rightChild == n && n.parent.parent.leftChild == n.parent) {
			leftRotate(n.parent);
			n = n.leftChild;
		} else if (n.parent.leftChild == n
				&& n.parent.parent.rightChild == n.parent) {
			rightRotate(n.parent);
			n = n.rightChild;
		}

		n.parent.color = Color.BLACK;
		n.parent.parent.color = Color.RED;
		if (n.parent.leftChild == n && n.parent.parent.leftChild == n.parent)
			rightRotate(n.parent.parent);
		else
			leftRotate(n.parent.parent);
	}

	private void leftRotate(Node n) {
		Node rc = n.rightChild;
		if (n.parent == null)
			root = rc;
		else {
			if (n.parent.rightChild == n)
				n.parent.rightChild = rc;
			else
				n.parent.leftChild = rc;
		}
		rc.parent = n.parent;
		n.rightChild = rc.leftChild;
		rc.leftChild = n;
	}

	private void rightRotate(Node n) {
		Node lc = n.leftChild;
		if (n.parent == null)
			root = lc;
		else {
			if (n.parent.rightChild == n)
				n.parent.rightChild = lc;
			else
				n.parent.leftChild = lc;
		}
		lc.parent = n.parent;
		n.leftChild = lc.rightChild;
		lc.rightChild = n;
	}

	private Node uncle(Node n) {
		if (n.parent == null || n.parent.parent == null)
			return null;

		if (n.parent.parent.leftChild == n.parent)
			return n.parent.parent.rightChild;
		else
			return n.parent.parent.leftChild;
	}
}
