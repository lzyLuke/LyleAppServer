package Reply;

import Security.AES;

public class UserInfo {

	private String username;
    private String nickname;
    private String avatar;//url
    private String cover;//url
    private int userid;//Integer

    public UserInfo(int userid,String username,String nickname, String avatar,String cover) {
	    	this.userid=userid;
	    	this.username=username;
	    	this.nickname=nickname;
	    	this.avatar=avatar;
	    	this.cover=cover;
    }

    public UserInfo() {
 
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='"  +
                ", nick='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

}
