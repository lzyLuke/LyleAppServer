package Request;


public class PostComment {
	public int momentid;
	public String nickname;
	public int authorid;
	public String content;
	public String datetime;
	
	public PostComment(int momentid,String nickname,int authorid, String content,String datetime) {
		this.momentid=momentid;
		this.authorid=authorid;
		this.nickname=nickname;
		this.content=content;
		this.datetime=datetime;
	}
	
	
}
