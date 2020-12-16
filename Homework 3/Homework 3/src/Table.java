
public interface Table {
	
	public String get_data();
	
	public void put(String key);
	
	public Term get(String key);
	
	public boolean contains(String key);
	public void merge(Table d, int max);
	
	public String[] getKeys();
	public Object getRoot();
	public int getDNI();
	
	// maybe use indexing with stack? so not to double search
	public void calculate(Document[] all);
	
	public Term[] top10();
	
	public void tf_idf(String key, double idf);
	
	public int[] get_search(String key);
	
	public String[] sample();
	

}
