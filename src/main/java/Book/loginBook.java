package Book;

import java.io.ObjectOutputStream.PutField;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class loginBook {
	private static loginBook instance=new loginBook();
	private ConcurrentHashMap<Integer, String> aeskeyBook;
	private loginBook() {
		
		aeskeyBook = new ConcurrentHashMap<Integer,String>();

	}
	
	public static loginBook getInstance() {
		
		return instance;
	}
	
	
	public void putAesKey(int sessionID,String aesKey) {
		aeskeyBook.put(sessionID,aesKey);
	}
	
	public String getAesKey(int sessionID) {
		return aeskeyBook.get(sessionID);
	}
	

	
}
