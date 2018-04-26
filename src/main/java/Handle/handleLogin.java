package Handle;
import java.sql.*;
import Reply.UserInfo;
import Book.loginBook;
import DataBase.DB;
public class handleLogin {
	public static UserInfo handle(String account,String password) {
		UserInfo userinfo=null;
		try {
		Connection conn = DB.getConn();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM Lyle.user WHERE userName=?");
		pst.setString(1, account);
		ResultSet rs=pst.executeQuery();
		if(rs.next()) {
		String correctPassword = rs.getString("password");
		
		if(correctPassword.equals(password)) 
		userinfo = new UserInfo(rs.getInt("userid"), rs.getString("username"), rs.getString("nickname"), rs.getString("avatar"),rs.getString("cover"));
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return userinfo;
	}
}
