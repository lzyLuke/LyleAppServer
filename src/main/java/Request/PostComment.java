package Request;


public class PostComment {
	public int momentid;
	public String nickname;
	public int authorid;
	public String content;
	public String datetime;
	public int receiverid;
	public String receivernickname;
	public int momentauthorid;
	
	public PostComment(int momentid,String nickname,int authorid, String content,String datetime,int momentauthorid) {
		this.momentid=momentid;
		this.authorid=authorid;
		this.nickname=nickname;
		this.content=content;
		this.datetime=datetime;
		this.momentauthorid=momentauthorid;
	}
	
	public PostComment(int momentid,String nickname,int authorid,String content,String datetime,int momentauthorid,int receiverid, String receivernickname) {
		this.momentid=momentid;
		this.authorid=authorid;
		this.nickname=nickname;
		this.content=content;
		this.datetime=datetime;
		this.receiverid=receiverid;
		this.receivernickname=receivernickname;
		this.momentauthorid=momentauthorid;
	}
	
	
}
