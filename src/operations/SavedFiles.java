package operations;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.naming.Context;


public class SavedFiles {
	private static ArrayList<StoredFile> FILES = new ArrayList<>();
	
	public static ArrayList<StoredFile> getSavedFiles() {
		return FILES;
	}
	
	// Adds StoredFile to SavedFiles
	public static void addFile(StoredFile file) {
		FILES.add(file);
		try {
			FileOutputStream fos = new FileOutputStream("saved.ser");
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(FILES);
			os.close();
			
		} catch (IOException e) {
			System.out.println("Could not save");
			e.printStackTrace();
		}

	}
	
	// Retrieves list of files that were uploaded on launch of application
	public static void launch() {
		try {
			FileInputStream fis = new FileInputStream("saved.ser");
			ObjectInputStream is = new ObjectInputStream(fis);
			FILES = (ArrayList<StoredFile>) is.readObject();
			is.close();
			System.out.println(FILES);
			DownloadFile dl = new DownloadFile(FILES.get(0).getFileName(), FILES.get(0).getId(), FILES.get(0).getKey());
			dl.writeToFile();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Could not find saved file");
		}
	}
	
	public static void main(String[] args) {
		launch();
	}
}