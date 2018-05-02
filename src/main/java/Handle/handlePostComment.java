package Handle;

import java.sql.Connection;
import java.sql.PreparedStatement;

import DataBase.DB;
import Request.PostComment;
import Request.PostMoment;

public class handlePostComment {
	public static boolean handle(PostComment pc) {
		try {
			Connection conn = DB.getConn();	
			
			if(pc.receivernickname==null) {
			PreparedStatement pst = conn.prepareStatement(
					"INSERT INTO Lyle.comments (authorid,nickname,momentid,content,datetime,momentauthorid)\n"
					+ 
					"VALUES\n" + 
					"(?,?,?,?,?,?);"
					);
			
			
			pst.setInt(1, pc.authorid);
			pst.setString(2, pc.nickname);
			pst.setInt(3, pc.momentid);
			pst.setString(4,pc.content);
			pst.setString(5,pc.datetime);
			pst.setInt(6, pc.momentauthorid);
			pst.execute();	
			}else {
				PreparedStatement pst = conn.prepareStatement(
						"INSERT INTO Lyle.comments (authorid,nickname,momentid,content,datetime,momentauthorid,receiverid,receivernickname)\n"
						+ 
						"VALUES\n" + 
						"(?,?,?,?,?,?,?,?);"
						);
				
				pst.setInt(1, pc.authorid);
				pst.setString(2, pc.nickname);
				pst.setInt(3, pc.momentid);
				pst.setString(4,pc.content);
				pst.setString(5,pc.datetime);
				pst.setInt(6, pc.momentauthorid);
				pst.setInt(7, pc.receiverid);
				pst.setString(8, pc.receivernickname);
				pst.execute();	
				
			}
			
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}			
			return true;
		
		
	}
}
