package Reply;

public class SingleComment {
	
	public String nickname;
	public int authorid;
	public String content;
	public int momentid;
	
	public SingleComment( int authorid, String nickname, int momentid, String content) {
		this.authorid=authorid;
		this.nickname=nickname;
		this.momentid=momentid;
		this.content=content;
	}
	
}
