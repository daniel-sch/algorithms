public class AvlTree<T extends Comparable<T>> {

	private class Node {
		public T data;

		public int height;

		public Node leftChild;
		public Node parent;
		public Node rightChild;
		public Node(T data, Node parent) {
			this.data = data;
			this.parent = parent;
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
			return;
		}

		Node cur = root;
		while (true) {
			if (cur.data.compareTo(e) > 0) {
				if (cur.leftChild == null) {
					cur.leftChild = new Node(e, cur);
					rebalance(cur);
					return;
				} else
					cur = cur.leftChild;
			} else {
				if (cur.rightChild == null) {
					cur.rightChild = new Node(e, cur);
					rebalance(cur);
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

	private int balanceFactor(Node n) {
		return (n.rightChild == null ? -1 : n.rightChild.height)
				- (n.leftChild == null ? -1 : n.leftChild.height);
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

	private void rebalance(Node n) {
		while (n != null) {
			int balancing = balanceFactor(n);
			if (balancing == 0)
				return;
			if (balancing < -1) {
				if (n.leftChild.rightChild == null
						|| n.leftChild.leftChild.height > n.leftChild.rightChild.height) {
					rightRotate(n);
				} else {
					leftRotate(n.leftChild);
					rightRotate(n);
				}
			} else if (balancing > 1) {
				if (n.rightChild.leftChild == null
						|| n.rightChild.rightChild.height > n.rightChild.leftChild.height) {
					leftRotate(n);
				} else {
					rightRotate(n.rightChild);
					leftRotate(n);
				}
			}
			n.height = Math.max(
					(n.leftChild == null ? -1 : n.leftChild.height),
					(n.rightChild == null ? -1 : n.rightChild.height)) + 1;
			n = n.parent;
		}
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
}
