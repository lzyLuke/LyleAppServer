package Utils;

public class Session {
	
	private static int session=0;
	synchronized public static int getLatestSessionID() {
		session++;
		return session;
	}
}
