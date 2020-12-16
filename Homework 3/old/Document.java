import java.io.*; 

public class Document {
	private String doc_name;
	private LinearProbingHashST analysis;
	private int wc;
	
	public Document() {
		doc_name = "All";
		wc = 0;
		analysis = new LinearProbingHashST(doc_name);
	}
	
	public String get_Name() {
		return doc_name;
	}
	
	public Term get(String key) {
		return analysis.get(key);
	}
	
	public Document(String name, String path, int name_i) {
		wc = 0;
		doc_name = name;
		try(BufferedReader r = new BufferedReader(new FileReader(path))){
			analysis = new LinearProbingHashST(name_i ,name);
			String line;
			int ind = 1;
			while( (line = r.readLine()) != null ) {
				String word = "";
				//ind++;
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
	
	
	public void merge(Document d) {
		this.wc += d.wc;
		this.analysis.merge(d.analysis);
		
	}
	
	public boolean contains(String key) {
		return analysis.contains(key);
		
	}
	

}
