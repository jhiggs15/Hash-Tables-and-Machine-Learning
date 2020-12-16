import java.io.*; 

public class Document {
	final boolean BST = true;
	final boolean LPH = false;
	
	private String doc_name;
	private Table analysis;
	private int wc;
	
	public Document(boolean type) {
		doc_name = "All";
		wc = 0;
		if(type == LPH) analysis = new LinearProbingHashST(-1, doc_name);
		else analysis = new BST_Table(-1, doc_name);
	}
	
	public String[] sample() {
		return analysis.sample();
	}
	
	public String get_Name() {
		return doc_name;
	}
	
	public Term get(String key) {
		return analysis.get(key);
		
	}
	
	public Document(String name,  String path, int name_i, boolean type) {
		wc = 0;
		doc_name = name;
		try(BufferedReader r = new BufferedReader(new FileReader(path))){
			if(type == LPH) analysis = new LinearProbingHashST(name_i, name);
			else analysis = new BST_Table(name_i, name);
			
			String line;
			while( (line = r.readLine()) != null ) {
				String word = "";
				for(int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if(isLetter(c)) {
						if(word == "" && c == '\'');
						else word += c;
					}
					else {
						int size = word.length();
						if(size != 0 && !word.equals("\'")) {
							if(word.charAt(size-1) == '\'') {
								word = word.substring(0, size-1);
							}
							
							wc++;
							analysis.put(word.toLowerCase());
							
							word = "";
						}
						
					}
					
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: IO Exception");
			e.printStackTrace();
		}
		
		
	}
	
	public void tf_idf(String key, double idf) {
		analysis.tf_idf(key, idf);
		
	}
	
	public void calculate(Document[] all) {
		this.analysis.calculate(all);
	}
	
	public String get_data() {
		return analysis.get_data();
	}
	
	
	private boolean isLetter(char c) {
		if((c >= 97 && c <= 122) || (c >= 65 && c <= 90) || c == '\'') return true;
		return false;
	}
	
	
	public void merge(Document d, int max, boolean type) {
		this.wc += d.wc;
		if(type == LPH) {
			this.analysis.merge(d.analysis, max);
		}
		else {
			this.analysis.merge(d.analysis, max);
		}
		
		
		
	}
	
	public int[] get_search(String key) {
		return analysis.get_search(key);
	}
	
	public Term[] top10() {
		return analysis.top10();
	}
	
	public boolean contains(String key) {
		return analysis.contains(key);
		
	}
	

}
