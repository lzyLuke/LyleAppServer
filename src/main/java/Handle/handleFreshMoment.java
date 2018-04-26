package Handle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import DataBase.DB;
import Reply.Moments;
import Reply.SingleMoment;
import Reply.UserInfo;
import Request.MomentFreshQuery;

public class handleFreshMoment {
	public static Moments handle(MomentFreshQuery q) {
		Moments moments = new Moments();
		moments.contents=new LinkedList<SingleMoment>();
		try {
		Connection conn = DB.getConn();
		int nums=4;
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM Lyle.Moments WHERE datetime < ? AND location = ? ORDER BY datetime DESC LIMIT ? ");
		pst.setString(1, q.latestupdate);
		pst.setString(2, q.location);
		pst.setInt(3, nums);
		
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
