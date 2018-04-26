package Handle;

import Request.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DataBase.DB;
import Reply.*;
public class handlePostMoment {
	public static boolean handle(PostMoment pm) {
		try {
			Connection conn = DB.getConn();		
			PreparedStatement pst = conn.prepareStatement("INSERT INTO Lyle.moments\n" + 
					"("+
					"location,\n" + 
					"datetime,\n" + 
					"userid,\n" + 
					"avatar,\n" + 
					"nickname,\n" + 
					"pic,\n" + 
					"content)\n" + 
					"VALUES\n" + 
					"(?,?,?,?,?,?,?);"
					);
			
			
			pst.setString(1, pm.location);
			pst.setString(2, pm.datetime);
			pst.setInt(3, pm.userid);
			pst.setString(4,pm.avatar);
			pst.setString(5, pm.nickname);
			pst.setString(6, pm.pic);
			pst.setString(7, pm.content);
			
			pst.execute();	
			
			
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}			
			return true;
		
		
	}
}
