
public class Term {
	
	private String word, doc_name;
	private int doc_name_i;
	private int frequency;
	private double tf_idf;
	private int doc_names[];
	private int N = 0;
	
	public Term(String w, String name, int max) {
		word = w;
		frequency = 1;
		doc_name = name;
		doc_names = new int[max];
	}
	
	public Term(int name_i,String w, String name) {
		word = w;
		frequency = 1;
		doc_name = name;
		doc_name_i = name_i;
	}
	
	public Term(String w, String name, int max, int name_i, int f) {
		word = w;
		frequency = f;
		doc_name = name;
		doc_names = new int[max];
		doc_names[N++] = name_i;
	}
	
	public int get_frequency() {
		return frequency;
	}
	
	public String get_word() {
		return word;
	}
	
	public double get_tfidf() {
		return tf_idf;
	}
	
	public String get_name() {
		return doc_name;
	}
	
	public void tf_idf(double idf) {
		tf_idf = frequency * idf;
	}
	
	public int[] get_search() {
		return doc_names;
	}
	
	public void increment() {
		frequency += 1;
	}
	
	public void combine(Term t) {
		frequency += t.frequency;
		doc_names[N++] = t.doc_name_i;
		
	}
	
	public String toString() {
		return "word \"" + word + "\"" +" has " + frequency + " occurences" + " in " + doc_name; 
	}
}
