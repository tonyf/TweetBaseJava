package operations;

import java.io.Serializable;
import java.security.Key;

public class StoredFile implements Serializable {
	private long headId;
	private String fileName;
	private Key key;
	
	public StoredFile(String fileName, long headId, Key key) {
		this.fileName = fileName;
		this.headId = headId;
		this.key = key;
	}
	
	protected long getId() {
		return headId;
	}
	
	protected String getFileName(){
		return fileName;
	}
	
	public String toString() {
		return (fileName + ": " + headId);
	}
	
	protected Key getKey() {
		return key;
	}
	

}
