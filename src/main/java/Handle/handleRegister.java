package Handle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DataBase.DB;
import Reply.UserInfo;

public class handleRegister {
	public static boolean handle(String account, String password, String nickname) {
		
		try {
		Connection conn = DB.getConn();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM Lyle.user WHERE userName=?");
		pst.setString(1, account);
		ResultSet rs=pst.executeQuery();
		if(rs.next()) 
		return false;
		
		
		
		pst = conn.prepareStatement("INSERT INTO Lyle.user"
				+ "("+
				"username,"+
				"password,"+
				"nickname,"+
				"avatar,"+
				"cover)"+
				"VALUES"+
				"(?,?,?,?,?);"
				);
		
		
		pst.setString(1, account);
		pst.setString(2, password);
		pst.setString(3, nickname);
		pst.setString(4,"DefaultURL");
		pst.setString(5, "DefalutURL");
		
		pst.execute();	
		
		
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}			
		return true;
	}
}
