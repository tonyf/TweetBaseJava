package operations;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

import twitter4j.Twitter;


public class UploadFile {
	private static final String ALGO = "AES";
	private static final byte[] keyValue = 
	        new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
	'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
	
	private File uploadFile;
	private String fileName;
	private Key key;
	
	
	public UploadFile(String filePath) {
		uploadFile = new File(filePath);
		fileName = uploadFile.getName();
		System.out.println(fileName);
	}
	
	public UploadFile(File file) {
		uploadFile = file;
		fileName = uploadFile.getName();
		System.out.println(fileName);
	}
	
	public long post() {
		TwitterPoster tp = new TwitterPoster(getString());
		long id = tp.post();
		SavedFiles.addFile(new StoredFile(fileName, id, key));
		return id;
	}
	

	public String getString() {
		byte[] bytes = getBytes();
        String encodedString = null;
        try {
			encodedString = new String(bytes, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Could not encode");
		}
        return encodedString;
	}
	
	private byte[] getBytes() {
        byte[] bytes = new byte[(int) uploadFile.length()];
        try {
        	FileInputStream inputStream = new FileInputStream(uploadFile);
        	inputStream.read(bytes);
        	inputStream.close();
        } catch(Exception e){
        	e.printStackTrace();
        }
        try {
        	key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            bytes = c.doFinal(bytes);
        }
        catch (Exception e) {
        	System.out.println("Cannot encrypt");
        	e.printStackTrace();
        }
		return bytes;
	}
	
	private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public static void main(String[] args) {
		TwitterSetup.setup();
		UploadFile file = new UploadFile("/Users/tonyfrancis/Desktop/lorem.txt");
		System.out.println(file.getString());
		System.out.println(file.post());
	}
	
}
