import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Collection {
	private Document[] c;
	int N, max;
	Document total;
	
	public Collection(int size) {
		c = new Document[size];
		max = size;
		N = 0;
		total = new Document();
	}
	
	public void insert(Document d) {
		c[N++] = d;
		total.merge(d);
		if(N == max) {
			total.calculate(c);
		}
	}
	
	public void search(String key) {
		System.out.println("Searching for \"" + key + "\"...");
		for(Document d : c) {
			if(d.contains(key)) {
				Term hold = d.get(key);
				String s = "Document: " + d.get_Name() + ": Found!";
				s +=  "\n	  Frequency: " + hold.get_frequency();
				s +=  "\n	  Tf-Idf: " + hold.get_tfidf();
				System.out.println(s);
			}
			else {
				String s =  "Document: " + d.get_Name() + " the key " + key + " was not found";
				System.out.println(s);
			}
		}
	}
	
	public void top10(Document doc) {
		
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
	
	/*
	// CHANGE BECAUSE U MULTIPLY TF AND IDFs
	// maybeh change to be better in terms of privacy
	public void calculate() {
		Stack<Integer> st = new Stack<Integer>(); 
		
		for(String key : total.getAnal().getKeys()) {
			if(key != null) {
				int contains = 0;
				for (int i = 0; i < N; i++) {
					if(c[i].contains(key)) {
						contains++;
						st.push(i);
					}
				}
				
				double tf_idf = Math.log(contains / N);
				
				while(!st.isEmpty()) {
					c[st.pop()].setTf_idf(key, tf_idf);
				}
				
				total.setTf_idf(key, tf_idf);
			}

		}
	}
	*/

}
