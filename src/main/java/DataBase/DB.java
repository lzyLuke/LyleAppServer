package DataBase;

import java.io.IOException ;
import java.io.InputStream ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.sql.SQLException ;
import java.util.Properties ;
import org.apache.commons.dbcp2.BasicDataSource;

import Reply.UserInfo;

public class DB {
	protected static Connection conn=null ;
	// 创建数据库连接对象 ( 数据源 )
	private static BasicDataSource dataSource=new BasicDataSource();
	// 配置数据源
	static 
	{
		DataSourceConfig();
	}
	/**
	 * 设置 dataSource各属性值
	 */
	private static void DataSourceConfig()
	{	
	    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); //数据库驱动
            dataSource.setUsername("root");  //用户名
            dataSource.setPassword("l2312178");  //密码
            dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/Lyle?useUnicode=true&characterEncoding=utf-8&useSSL=false");  //连接url
            dataSource.setInitialSize(10); // 初始的连接数；  
            dataSource.setMaxTotal(100);  //最大连接数
            dataSource.setMaxIdle(80);  // 设置最大空闲连接
            dataSource.setMaxWaitMillis(6000);  // 设置最大等待时间
            dataSource.setMinIdle(10);  // 设置最小空闲连接
	}
	/**
	 * 获得连接对象
	 *
	 * @return
	 */
	public static Connection getConn() throws SQLException
	{

		return dataSource.getConnection(); 
		
	}
	
	public static void main(String[] args) {
		try {
		Connection conn =getConn();
		PreparedStatement pst = conn.prepareStatement("SELECT * FROM Lyle.user WHERE username=?");
		pst.setString(1, "Luke");
		ResultSet rs=pst.executeQuery();
		while(rs.next()) {
		System.out.println(rs.getInt("userid"));
		System.out.println(rs.getString("username"));
		System.out.println(rs.getString("nickname"));
		System.out.println(rs.getString("cover"));
		}
		rs.close();
		pst.close();
		conn.close();
		}catch(Exception e) {
			System.out.println("23");
			e.printStackTrace();
		}
		
		
		try {
			Connection conn =getConn();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM Lyle.user WHERE username=?");
			pst.setString(1, "Luke");
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
			System.out.println(rs.getInt("userid"));
			System.out.println(rs.getString("username"));
			System.out.println(rs.getString("nickname"));
			System.out.println(rs.getString("cover"));
			}
			rs.close();
			pst.close();
			conn.close();
			}catch(Exception e) {
				System.out.println("23");
				e.printStackTrace();
			}
	}
}