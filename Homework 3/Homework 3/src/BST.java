
public class BST<Key extends Comparable<Key>, Value> {
	private Node root;
	
	private class Node {
		private Key key;
		private Stack<Value> val;
		private Node l, r;
		private int N;
		
		public Node(Key key, Value val) {//, int N) {
			this.key = key;
			this.val = new Stack<Value>();
			this.val.push(val);
			//this.N = N;
		}
		
		public String toString() {
			return key + " ";
		}
	}
		
		public int size() {
			return size(root);
		}
		
		public int size(Node x) {
			if(x == null) return 0;
			else return x.N;
		}
		
		public void put(Key key, Value val) {
			root = put(root, key, val);
		}
		
		private Node put(Node x, Key key, Value val) {
			if(x == null) return new Node(key, val);
			int cmp = key.compareTo(x.key);
			if(cmp < 0) x.l = put(x.l, key, val);
			else if(cmp > 0) x.r = put(x.r, key, val);
			else x.val.push(val);
			
			//x.N = size(x.l) + size(x.r) + 1;
			return x;
		}
		
		public Value delete_max() {
			Node parent = root;
			Node cur = root;
			
			if(cur == null) return null;
			while(cur.r != null) {
				parent = cur;
				cur = cur.r;
			}
			Node max = cur;
			Value v = max.val.pop();
			if(max.val.isEmpty()) parent.r = cur.l;
			
			return v;
		}
		
		
	

}
