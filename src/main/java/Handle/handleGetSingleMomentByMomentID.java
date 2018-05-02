package Handle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import DataBase.DB;
import Reply.Moments;
import Reply.SingleMoment;

public class handleGetSingleMomentByMomentID {
	public static Moments handle(int momentID) {
		Moments moments = new Moments();
		moments.contents=new LinkedList<SingleMoment>();
		try {
		Connection conn = DB.getConn();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM Lyle.moments WHERE momentid = ? ORDER BY datetime DESC ");
		pst.setInt(1, momentID);
		
		
		ResultSet rs=pst.executeQuery();
		
		while(rs.next()) {
			int momentid = rs.getInt("momentid");
			String location = rs.getString("location");
			String datetime = rs.getString("datetime");
			int userid = rs.getInt("userid");
			String avatar = rs.getString("avatar");
			String nickname =rs.getString("nickname");
			String pic = rs.getString("pic");
			String content = rs.getString("content");
			SingleMoment newMoment = new SingleMoment(momentid,location,datetime,userid,avatar,nickname,pic,content);
			moments.contents.add(newMoment);
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return moments;
		
		
	}
}
