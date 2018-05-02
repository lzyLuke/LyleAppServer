package Handle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import DataBase.DB;
import Reply.Comments;
import Reply.SingleComment;

public class handleVisit {
	public static boolean handle(int momentid, int userid) {
		try {
		Connection conn = DB.getConn();
		PreparedStatement pst = conn.prepareStatement("UPDATE Lyle.comments\n" + 
				"SET\n" + 
				"momentauthorreadstatus = 1\n" + 
				"WHERE momentid = ? AND momentauthorid = ? AND momentauthorreadstatus = 0;");
		pst.setInt(1, momentid);
		pst.setInt(2, userid);
		
		pst.executeUpdate();
	
		
		PreparedStatement pst2 = conn.prepareStatement("UPDATE Lyle.comments\n" + 
				"SET\n" + 
				"receiverreadstatus = 1\n" + 
				"WHERE momentid = ? AND receiverid = ? AND receiverreadstatus = 0;");
		pst2.setInt(1, momentid);
		pst2.setInt(2, userid);
		pst2.executeUpdate();
		
		}catch(Exception e) {
			e.printStackTrace();
			return false;
			
		}
		
		return true;
		
		
		
		
		
	}
}
