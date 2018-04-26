package Request;

public class CommentFreshQuery {

	public int momentid;
	public String datetime;
	public int userid;
	
	//其实只要momentID就行了。。。。
	public CommentFreshQuery(int momentid,int userid,String datetime) {
		this.momentid=momentid;
		this.userid=userid;
		this.datetime=datetime;
	}
}
