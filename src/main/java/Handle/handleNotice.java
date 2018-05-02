package Handle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import DataBase.DB;
import Reply.Comments;
import Reply.Notices;
import Reply.SingleComment;
import Reply.SingleNotice;
import Request.CommentFreshQuery;

public class handleNotice {
	public static Notices handle(int userid) throws Exception{
			
			
			LinkedList<SingleNotice> ans = new LinkedList<SingleNotice>();
		
			Connection conn = DB.getConn();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM Lyle.comments WHERE ( receiverid = ? AND receiverreadstatus = 0 ) OR ( momentauthorid = ? AND authorid != ? AND momentauthorreadstatus  = 0    )  ORDER BY datetime ASC  ");
			pst.setInt(1, userid);
			pst.setInt(2 , userid);
			pst.setInt(3 , userid);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				
				String nickname = rs.getString("nickname");
				int authorid = rs.getInt("authorid");
				int momentid = rs.getInt("momentid");
				SingleNotice newNotice = new SingleNotice(momentid,authorid, nickname);
				ans.add(newNotice);
			}
		
			
			
			return new Notices(ans);
		
		
	}
}
