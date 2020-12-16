import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



public class Collection {
	final boolean BST = true;
	final boolean LPH = false;
	
	private Document[] c;
	int N, max;
	Document total;
	boolean type;
	
	public Collection(boolean type, String folder_name) {
		File folder = new File("Collection");
		String[] file_names = folder.list();
		int size = file_names.length;
		c = new Document[size];
		max = size;
		N = 0;
		total = new Document(type);
		this.type = type;
		int index = 0;
		for(String name : file_names) {
			insert(new Document(name.substring(0, name.length()-4) ,"./" + folder_name +"/" + name, index, type));
			index = index + 1;
			System.out.println(name + " is complete");
		}
		
	}
	
	public Collection(boolean type, String folder_name, boolean is_test) {
		File folder = new File("Collection");
		String[] file_names = folder.list();
		int size = file_names.length;
		c = new Document[size];
		max = size;
		N = 0;
		total = new Document(type);
		this.type = type;
		int index = 0;
		for(String name : file_names) {
			insert(new Document(name.substring(0, name.length()-4) ,"./" + folder_name +"/" + name, index, type));
			index = index + 1;
		}
		
	}
	
	public void insert(Document d) {
		c[N++] = d;
		total.merge(d, max, type);
		if(N == max) {
			total.calculate(c);
		}
	}
	
	public String[] sample() {
		return total.sample();
	}
	
	public void search(String key) {
		System.out.println("\nSearching for \"" + key + "\"...");
		int[] docs = total.get_search(key);
		if(docs == null) System.out.println(key + " was not found.");
		else {
			for(int i = 0; i < docs.length; i++) {
				int k = docs[i];
				if(k == 0 && i != 0) break;
				Term val = c[k].get(key);
				String s =  "|-----------------------------------------|\n";
				s += "* " + c[k].get_Name();
				s +=  "\n*	  Frequency: " + val.get_frequency();
				s +=  "\n*	  Tf-Idf   : " + val.get_tfidf();
				s +=  "\n|-----------------------------------------|";
				System.out.println(s);
			}
			Term val = total.get(key);
			String s =  "|-----------------------------------------|\n";
			s += "* " + total.get_Name();
			s +=  "\n*	  Frequency: " + val.get_frequency();
			s +=  "\n*	  Tf-Idf   : " + val.get_tfidf();
			s +=  "\n|-----------------------------------------|";
			System.out.println(s);
		}
	}
	
	public void search_nt(String key) {
		int[] docs = total.get_search(key);
		if(docs == null) System.out.println(key + " was not found.");
	}
	
	public void top10(Document doc) {
		Term[] top_10 = doc.top10();
	
		System.out.println("\nTop 10 for Document: " + doc.get_Name() + "...");
		
		for(int i = 0; i < 10; i++) {
			String s =  "|-----------------------------------------|\n";
			s += "* " + (i+1) + ". " + top_10[i].get_word();
			s +=  "\n*	  Tf-Idf   : " + top_10[i].get_tfidf();
			s +=  "\n|-----------------------------------------|";
			System.out.println(s);
			s = "";
		}
	}
	
	public void top10(int index) {
		if(index >= max) {
			System.out.println("Please enter valid index...");
		}
		else {
			Document doc = c[index]; 
			top10(doc);
		}

	}
	
	public void export() {
		String header = "Word, Document Name, Frequency, tf_idf\n";
		byte head[] = header.getBytes();
		for(Document d : c) {
			String s = d.get_Name() + ".csv";
			try{
				OutputStream out = new FileOutputStream(s);
				out.write(head);
				String output = d.get_data();
				byte data[] = output.getBytes();
				out.write(data);
				out.close();
			}
			catch(IOException x){
				System.out.println("ERROR CREATING FILE");
				System.err.println(x);
				
			}
		}
		
		try{
			OutputStream out = new FileOutputStream("All.csv");
			out.write(head);
			String output = total.get_data();
			byte data[] = output.getBytes();
			out.write(data);
			out.close();
		}
		catch(IOException x){
			System.out.println("ERROR CREATING FILE");
			System.err.println(x);
			
		}

		
	}

}
