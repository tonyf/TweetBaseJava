package operations;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class DownloadFile {
	private static final String ALGO = "AES";
	private String pathname;
	private String tweetString;
	private Key key;
	
	
	public DownloadFile(String pathname, long headId, Key key) {
		this.pathname = pathname;
		this.key = key;
		TwitterDownloader download = new TwitterDownloader(headId);
		tweetString = download.getTweets();
		System.out.println(tweetString);
		writeToFile();
	}
	
	public void writeToFile() {
		byte[] bytes = stringToBytes();
		FileOutputStream fileOutput;
		try {
			Cipher c = Cipher.getInstance(ALGO);
	        c.init(Cipher.DECRYPT_MODE, key);
	        bytes = c.doFinal(bytes);
			fileOutput = new FileOutputStream("/Users/tonyfrancis/Documents/" + pathname);
			fileOutput.write(bytes);
			fileOutput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Cannot write");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException 
				| IllegalBlockSizeException | BadPaddingException e) {
			System.out.println("Could not decrypt");
			e.printStackTrace();
		} 
		
	}
	
	private byte[] stringToBytes() {
		byte[] bytes = null;
		try {
			bytes = tweetString.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Cannot encode");
		}
		return bytes;
	}
	
}
