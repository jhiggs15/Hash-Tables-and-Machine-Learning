import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main {

	public static void main(String args[]) {
		final boolean BST = true;
		final boolean LPH = false;
		
		boolean type = LPH;
		
		Collection docs = new Collection(type, "Collection");
		//docs.export();
		
		System.out.println("----------------------------------------");
		docs.search("this");	
		System.out.println("----------------------------------------");
		docs.top10(0);
		
		System.out.println("----------------------------------------");
		
		type = BST;
		docs = new Collection(type, "Collection");
		//docs.export();
		
		System.out.println("----------------------------------------");
		docs.search("shoe");	
		System.out.println("----------------------------------------");
		docs.top10(0);
		
		// run tests
		/*
		String header = "Type, Key, Time\n";
		byte head[] = header.getBytes();
		
		String header2 = "Type, Time\n";
		byte head2[] = header2.getBytes();
		int test_count = 20;
		StopWatch watch = new StopWatch();
		
		try{
			OutputStream out = new FileOutputStream("./BST_search.csv");
			out.write(head);
			OutputStream out2 = new FileOutputStream("./BST_construction.csv");
			out2.write(head2);
			for(int i = 0; i < test_count; i++) {
				watch.start();
				Collection col = new Collection(BST, "Collection", true);
				long end = watch.stop();
				String to_write = "BST" + ", " + end + "\n";
				byte data[] = to_write.getBytes();
				out2.write(data);
				
				String[] keys = col.sample();
				for(int j = 0; j < keys.length; j++) {
					String key = keys[j];
					watch.start();
					col.search_nt(key);
					end = watch.stop();
					
					String to_write2 = "BST" + ", " + key + ", " + end + "\n";
					data = to_write2.getBytes();
					out.write(data);

				}

			}
			out.close();
			out2.close();
		}
		catch(IOException x){
			System.out.println("ERROR CREATING FILE");
			System.err.println(x);
			
		}	
		
		try{
			OutputStream out = new FileOutputStream("./LPH_search.csv");
			out.write(head);
			OutputStream out2 = new FileOutputStream("./LPH_construction.csv");
			out2.write(head2);
			for(int i = 0; i < test_count; i++) {
				watch.start();
				Collection col = new Collection(LPH, "Collection", true);
				long end = watch.stop();
				String to_write = "LPH" + ", " + end + "\n";
				byte data[] = to_write.getBytes();
				out2.write(data);
				
				String[] keys = col.sample();
				for(int j = 0; j < keys.length; j++) {
					String key = keys[j];
					watch.start();
					col.search_nt(key);
					end = watch.stop();
					
					String to_write2 = "LPH" + ", " + key + ", " + end + "\n";
					data = to_write2.getBytes();
					out.write(data);

				}

			}
			out.close();
			out2.close();
		}
		catch(IOException x){
			System.out.println("ERROR CREATING FILE");
			System.err.println(x);
			
		}
		*/
	}
	
}
