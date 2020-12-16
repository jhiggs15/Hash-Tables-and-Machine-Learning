import java.io.File;

public class Main {

	public static void main(String args[]) {
		File folder = new File("Collection");
		String[] file_names = folder.list();
		Collection docs = new Collection(file_names.length);
		
		for(String name : file_names) {
			docs.insert(new Document(name.substring(0, name.length()-4) ,"./Collection/" + name));
			System.out.println(name + " is complete");
		}
		
		//docs.export();
		
		System.out.println("----------------------------------------");
		
		
		docs.search("beetles'");
		
		
		
		
		
	}
}
