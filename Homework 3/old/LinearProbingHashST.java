
public class LinearProbingHashST {
	private int N;
	private int M = 16;
	private String[] keys;
	private Term[] vals;
	private String doc_name;
	
	
	public LinearProbingHashST(String n, int m) {
		M = m;
		keys = new String[M];
		vals = new Term[M];
		doc_name = n;
	}
	
	
	public LinearProbingHashST(int name_i, String name) {
		keys = new String[M];
		vals = new Term[M];
		doc_name = name;
		
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
		LinearProbingHashST hold = new LinearProbingHashST(doc_name, m);
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
		vals[i] = new Term(key, doc_name);
		N++;
		
	}
	
	public void put(String key, double idf) {
		if(N >= M/2) resize(2*M);
		int i;
		for(i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if(keys[i].equals(key)) {
				vals[i].increment();
				return;
			}
		}
		keys[i] = key;
		vals[i] = new Term(key, doc_name);
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
	
	public void merge(LinearProbingHashST d) {
		for(String key : d.keys) {
			if(key != null) {
				boolean found = false;
				for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
					if(keys[i].equals(key)) {
						vals[i].combine(d.get(key));
						found = true;
					}
				}
				if(!found) put(key, new Term(key, "All"));
			}

		}
	}
	
	// maybe use indexing with stack? so not to double search
	public void calculate(Document[] all) {
		for(String key : keys) {
			if(key != null) {
				int count = 0;
				for(Document doc : all) {
					if(doc.contains(key)) count++;
				}
				double idf = Math.log((double) all.length/ (double) count);
				for(Document doc : all) {
					doc.tf_idf(key, idf);
				}
				this.tf_idf(key, idf);
				count = 0;
			}

		}
	}
	
	public void tf_idf(String key, double idf) {
		key = key.toLowerCase();
		for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if(keys[i].equals(key)) vals[i].tf_idf(idf);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
