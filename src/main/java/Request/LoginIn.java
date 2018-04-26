package Request;

public class LoginIn {
	public String account;
	public String password;
	public LoginIn(String account,String password) {
		this.account=account;
		this.password=password;
	}
	
	public String toString() {
		
		return "account:"+account+"\n"+"password:"+password+"\nsessionid:";
	}
}
