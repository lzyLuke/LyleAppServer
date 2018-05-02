package Handle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import DataBase.DB;
import Reply.*;

import Request.*;
public class handleFreshComment {
	public static Comments handle(CommentFreshQuery q) {
		Comments comments = new Comments();
		comments.contents=new LinkedList<SingleComment>();
		try {
		Connection conn = DB.getConn();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM Lyle.comments WHERE momentid = ? ORDER BY datetime ASC  ");
		pst.setInt(1, q.momentid);;
		
		ResultSet rs=pst.executeQuery();
		
		while(rs.next()) {
			int momentid = rs.getInt("momentid");
			int authorid = rs.getInt("authorid");
			String nickname =rs.getString("nickname");
			String content = rs.getString("content");
			int momentauthorid = rs.getInt("momentauthorid");
			int receiverid = rs.getInt("receiverid");
			String receivernickname = rs.getString("receivernickname");
			String datetime = rs.getString("datetime");
			SingleComment newCommnet = new SingleComment(  authorid, nickname,  momentid, content,datetime,receiverid,receivernickname,momentauthorid);
			comments.contents.add(newCommnet);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return comments;
	}
}
