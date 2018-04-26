package Reply;

import java.util.List;

public class SingleMoment {
	 //文字
	public String content;
    //图片
    public String pic;
    public int momentid;
    public String location;
    public String datetime;
    public int userid;
    public String avatar;
    public String nickname;
 
    public SingleMoment(int momentid,String location,String datetime,int userid, String avatar, String nickname, String pic, String content) {
    		this.momentid=momentid;
    		this.location=location;
    		this.datetime=datetime;
    		this.userid=userid;
    		this.avatar=avatar;
    		this.nickname=nickname;
    		this.pic=pic;
    		this.content=content;
    	
    }
}
