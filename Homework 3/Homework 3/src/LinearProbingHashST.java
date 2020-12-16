import java.util.Random;

public class LinearProbingHashST implements Table {
	private int N;
	private String doc_name;
	private int doc_name_i;
	private int M = 16;
	private String[] keys;
	private Term[] vals;
	
	private Term[] top_10 = new Term[10];
	private BST<Double, Term> all_words = new BST<Double, Term>();
	
	public LinearProbingHashST(int N, String n) {
		keys = new String[M];
		vals = new Term[M];
		doc_name_i = N;
		doc_name = n;
	}
	
	public LinearProbingHashST(String n, int N, int m) {
		M = m;
		keys = new String[M];
		vals = new Term[M];
		doc_name_i = N;
		doc_name = n;
	}
	
	
	public int getDNI() {
		return doc_name_i;
	}
	
	public Object getRoot() {
		return null;
	}
	
	public String[] getKeys() {
		return keys;
	}
	
	public String get_data() {
		String data = "";
		for(int i = 0; i < keys.length; i++) {
			if(keys[i] != null) {
				data += keys[i] + ", " + vals[i].get_name() + ", "+ vals[i].get_frequency() + ", " + vals[i].get_tfidf() + "\n";
			}
		}
		
		return data;
	}
	
	private int hash(String key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}
	
	private void resize(int m) {
		LinearProbingHashST hold = new LinearProbingHashST(doc_name, doc_name_i , m);
		for(int i = 0; i < M; i++) {
			if(keys[i] != null) {
				hold.put(keys[i], vals[i]);
			}
		}
		
		keys = hold.keys;
		vals = hold.vals;
		M = hold.M;
		
	}
	
	
	public void put(String key) {
		if(N >= M/2) resize(2*M);
		int i;
		for(i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if(keys[i].equals(key)) {
				vals[i].increment();
				return;
			}
		}
		keys[i] = key;
		vals[i] = new Term(doc_name_i, key, doc_name);
		N++;
		
	}

	
	public void put(String key, Term val) {
		if(N >= M/2) resize(2*M);
		int i;
		for(i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if(keys[i].equals(key)) {
				vals[i].increment();
				return;
			}
		}
		keys[i] = key;
		vals[i] = val;
		N++;
		
	}
	
	public Term get(String key) {
		key = key.toLowerCase();
		for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if(keys[i].equals(key)) return vals[i];
		}
		
		return null;
		
	}
	
	public boolean contains(String key) {
		key = key.toLowerCase();
		for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if(keys[i].equals(key)) return true;
		}
		
		return false;
		
	}
	
	public void merge(Table d, int max) {
		for(String key : d.getKeys()) {
			if(key != null) {
				boolean found = false;
				for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
					if(keys[i].equals(key)) {
						vals[i].combine(d.get(key));
						found = true;
					}
				}
				if(!found) put(key, new Term(key, "All", max, d.getDNI(), d.get(key).get_frequency()));
			}

		}
	}
	
    public static double log2(double N) 
    { 
        return (double)(Math.log(N) / Math.log(2)); 
    } 
	
	public void calculate(Document[] all) {
		for(String key : keys) {
			if(key != null) {
				int key_freq_across_all_docs = get(key).get_frequency();
				// natural log is taken
				double idf = log2( 1 + ( (double) all.length / (double) key_freq_across_all_docs) );
				for(Document doc : all) {
					doc.tf_idf(key, idf);
				}
				this.tf_idf(key, idf);
			}

		}
	}
	
	public String[] sample() {
		int sz = N;
		int range = sz/10;
		Random rand = new Random();
		String[] smpl = new String[range];
		for(int i = 0; i < range; i++) {
			int index = rand.nextInt(sz - 1) + 1;
			while(keys[index] == null) {
				index = rand.nextInt(sz - 1) + 1;
			}
			smpl[i] = keys[index];
		}
		
		
		return smpl;
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
		for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if(keys[i].equals(key)) {
				vals[i].tf_idf(idf);
				all_words.put(vals[i].get_tfidf(), vals[i]);
			}
		}
	}
	
	public int[] get_search(String key) {
		key = key.toLowerCase();
		for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if(keys[i].equals(key)) return vals[i].get_search();
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
