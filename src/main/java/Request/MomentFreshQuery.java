package Request;

public class MomentFreshQuery {
	public String latestupdate;
	public int userid;
	public String location;
	
	public MomentFreshQuery(int userid,String location,String latestupdate) {
		this.latestupdate=latestupdate;
		this.userid=userid;
		this.location=location;
	}
	
	
}
