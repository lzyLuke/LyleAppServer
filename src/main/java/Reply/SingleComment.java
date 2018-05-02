package Reply;

public class SingleComment {
	
	public String nickname;
	public int authorid;
	public String content;
	public int momentid;
	//newly added
	public String datetime;
	public int receiverid;
	public String receivernickname;
	public int momentauthorid;
	
	
	
	
	public SingleComment( int authorid, String nickname, int momentid, String content,String datetime,int receiverid,String receivernickname,int momentauthorid) {
		this.authorid=authorid;
		this.nickname=nickname;
		this.momentid=momentid;
		this.content=content;
		this.receivernickname=receivernickname;
		this.momentauthorid=momentauthorid;
		this.receiverid=receiverid;
	}
	
}
