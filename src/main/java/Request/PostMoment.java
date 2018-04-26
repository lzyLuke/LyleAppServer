package Request;

public class PostMoment {
		//文字内容
		public String content;
	    //图片URL
	    public String pic;
	    //发送地
	    public String location;
	    //发送时间
	    public String datetime;
	    //发送者的ID
	    public int userid;
	    //发送者头像的url
	    public String avatar;
	    //发送者名字
	    public String nickname;
	 
	    public PostMoment(String location,String datetime,int userid, String avatar, String nickname, String pic, String content) {
	    		this.location=location;
	    		this.datetime=datetime;
	    		this.userid=userid;
	    		this.avatar=avatar;
	    		this.nickname=nickname;
	    		this.pic=pic;
	    		this.content=content;
	    	
	    }
	    
	    public String toString() {
	    		return
	    				"\nlocation:"+location+
	    				"\ndatetime:"+datetime+
	    				"\nuserid:"+String.valueOf(userid)+
	    				"\navatar:"+avatar+
	    				"\nnickname:"+nickname+
	    				"\npic:"+pic+
	    				"\ncontent:"+content;
	    	
	    }
	    
}
