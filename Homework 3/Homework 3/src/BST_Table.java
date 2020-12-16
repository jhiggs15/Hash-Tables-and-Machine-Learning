import java.util.Random;
public class BST_Table implements Table{
	private int N;
	private String doc_name;
	private int doc_name_i;
	private Term[] top_10 = new Term[10];
	private BST<Double, Term> all_words = new BST<Double, Term>();
	
	private Node root;
	
	private class Node {
		private String key;
		private Term val;
		private Node l, r;
		private int N;
		
		public Node(String key, Term val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}
		
		public String toString() {
			return key + " ";
		}
	}
	
		
	private int size() {
		return size(root);
	}
	
	private int size(Node x) {
		if(x == null) return 0;
		else return x.N;
	}
	
	public void put(String key, Term val) {}
	
	public void put(String key) {
		root = put(root, key);
	}
	
	private Node put(Node x, String key) {
		if(x == null) return new Node(key, new Term(doc_name_i, key, doc_name), 1);
		int cmp = key.compareTo(x.key);
		if(cmp < 0) x.l = put(x.l, key);
		else if(cmp > 0) x.r = put(x.r, key);
		else x.val.increment();
		x.N = size(x.l) + size(x.r) + 1;
		return x;
	}
	
	private Node put(Node x, String key, Term val) {
		if(x == null) return new Node(key, val, 1);
		int cmp = key.compareTo(x.key);
		if(cmp < 0) x.l = put(x.l, key);
		else if(cmp > 0) x.r = put(x.r, key);
		else x.val.increment();
		x.N = size(x.l) + size(x.r) + 1;
		return x;
	}
	
	public void put(String key, Term val, int max, Table d) {
		root = put(root, key, val, max, d);
	}
	
	private Node put(Node x, String key, Term val, int max, Table d) {
		if(x == null) return new Node(key, new Term(key, "All", max, d.getDNI(), d.get(key).get_frequency()), 1);
		int cmp = key.compareTo(x.key);
		if(cmp < 0) x.l = put(x.l, key, val, max, d);
		else if(cmp > 0) x.r = put(x.r, key, val, max, d);
		else x.val.combine(d.get(key));
		x.N = size(x.l) + size(x.r) + 1;
		return x;
	}
	
	public BST_Table(int N, String n) {
		doc_name_i = N;
		doc_name = n;
	}
	
	public Term get(String key) {
		return get(root, key);
	}
	
	private Term get(Node x, String key) {
		if(x == null) return null;
		int cmp = key.compareTo(x.key);
		if(cmp < 0) return get(x.l, key);
		if(cmp > 0) return get(x.r, key);
		else return x.val;
	}
	
	public boolean contains(String key) {
		if(get(key) != null) return true;
		return false;
	}
	
	public Node getRoot() {
		return root;
	}
	
	public void merge(Table d, int max) {
		this.merge((Node) d.getRoot(), max, d);
	}
	
	private void merge(Node a, int max, Table d) {
		put(a.key, a.val, max, d);
		if(a.l != null) merge(a.l, max, d);
		if(a.r != null) merge(a.r, max, d);
		
	}
	
	public String[] getKeys() {
		return null;
	}
	public int getDNI() {
		return doc_name_i; 
	}
	
	public String get_data() {
		return get_data(root);
	}
	
	private String get_data(Node x) {
		if(x == null) return "";
		return x.key + ", " + x.val.get_name() + ", "+ x.val.get_frequency() + ", " + x.val.get_tfidf() + "\n" + get_data(x.l) + get_data(x.r);
	}
	
	
	public void calculate(Document[] all) {
		calc(root, all);
	}
	
    public static double log2(double N) 
    { 
        return (double)(Math.log(N) / Math.log(2)); 
    } 
	
	private void calc(Node x, Document[] all) {
		if(x != null) {
			int key_freq_across_all_docs = get(x.key).get_frequency();
			double idf = log2( 1 + ( (double) all.length / (double) key_freq_across_all_docs) );
			for(Document doc : all) {
				doc.tf_idf(x.key, idf);
			}
			this.tf_idf(x.key, idf);
			calc(x.l, all);
			calc(x.r, all);
		}
	}
	
	
	public Term[] top10() {
		if(this.top_10[0] == null) {
			Term[] top_10 = new Term[10];
			int N = 0;
			for(int i = 0; i < 10; i++) {
				top_10[N++] = all_words.delete_max();
			}
			
			return top_10;
		}
		else return top_10;

	}
	
	public void tf_idf(String key, double idf) {
		key = key.toLowerCase();
		Term t = get(key);
		if(t != null) {
			get(key).tf_idf(idf);
			t = get(key);
			all_words.put(t.get_tfidf(), t);
		}

	}
	
	public int[] get_search(String key) {
		key = key.toLowerCase();
		Term t = get(key);
		if(t == null) return null;
		return get(key).get_search();
	}
	
	public String select(int k) {
		return select(root, k).key;
	}
	
	public Node select(Node x, int k) {
		if(x == null) return null;
		int t = size(x.l);
		if(t > k) return select(x.l, k);
		else if(t < k) return select(x.r,k-t-1);
		else return x;
	}
	
	public String[] sample() {
		int sz = size();
		int range = sz/10;
		Random rand = new Random();
		String[] smpl = new String[range];
		for(int i = 0; i < range; i++) {
			smpl[i] = select(rand.nextInt(sz - 1) + 1);
		}
		
		return smpl;
	}
	


}
