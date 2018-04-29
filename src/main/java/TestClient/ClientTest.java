package TestClient;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import Reply.*;
import Request.*;
import com.google.gson.Gson;

import Security.AES;
import Security.RSA;
import Security.RSAUtil;
public class ClientTest {
	String mainURL;
	String realAES;
	String publicKey;
	String privateKey;
	int sessionid;
	Gson gson = new Gson();
	public static void main(String[] args) {
		try {
		ClientTest test = new ClientTest("http://47.106.14.199:8080");
		test.firstConnect();
		//test.login();
		//test.uploadMomentPicture();
		//test.uploadCoverPicture();
		//test.uploadAvatarPicture();
		//test.freshMoments();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ClientTest(String url) {
		mainURL=url;
		String[] keys = RSAUtil.generateRSAKeyPairStringArray();
        publicKey = keys[0];
        privateKey = keys[1];
        System.out.println("Public: \n\r" + publicKey);
        System.out.println("Private： \n\r" + privateKey);
        
        
        
	}
	
	
	//TESTOK
	public void firstConnect() throws Exception{
		URL url = new URL(mainURL + "/firstConnect?publicKey="+publicKey);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		System.out.println("正在尝试首次登录。。。GET\n" + url);
		
		BufferedReader r = new BufferedReader(new
		InputStreamReader(connection.getInputStream()));
		//dumpResponse(r);
		try {
		if (connection.getResponseCode() == 200) {
			firstReply rbundle;
			rbundle = gson.fromJson(r, firstReply.class);
			realAES = RSA.decode(rbundle.encryptedAESkey, privateKey);
			System.out.println("realAES:"+realAES);
			sessionid=rbundle.sessionid;
			System.out.println("sessionid:"+String.valueOf(sessionid));
		} else {
			System.out.println("服务器拒绝!!!!!");
		}
		}catch(Exception e) {
			System.out.println("RSA解密错误");
		}
		
	}
	
	//OK
	public void login() throws Exception {
		URL url = new URL(mainURL + "/login?sessionID="+String.valueOf(sessionid));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		System.out.println("POST正在尝试登录。。。URL:" + url+"\n");
		connection.setDoOutput(true); // send a POST message
		connection.setRequestMethod("POST");
		
		// transfer into Json and put it into a stream.
		PrintWriter w = new PrintWriter(connection.getOutputStream());
		LoginIn postBundle = new LoginIn("LukeAccount", AES.Encrypt("123456", realAES));
		System.out.println("Post内容：");
		System.out.println(postBundle);
		System.out.println("=========================");
		w.println(gson.toJson(postBundle, LoginIn.class));
		w.flush();
		
		// ready to receive
		if (connection.getResponseCode() == 200) {
		BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		UserInfo rbundle = null;
		rbundle = gson.fromJson(r, UserInfo.class);
		System.out.println(rbundle);
		
		//这里接受到的UserInfoclass ——> rbundle包含了用户的各种信息，可以用于程序。
		}
		else {
			System.out.println("服务器拒绝登录!!!!!");
		}
		
		
	}
	//OK
	public void register() throws Exception{
		URL url = new URL(mainURL + "/register?sessionID="+String.valueOf(sessionid));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		System.out.println("正在尝试注册。。。URL:" + url+"\n");
		connection.setDoOutput(true); // send a POST message
		connection.setRequestMethod("POST");
		
		// transfer into Json and put it into a stream.
		PrintWriter w = new PrintWriter(connection.getOutputStream());
		RegisterRequest postBundle = new RegisterRequest("NEWSIGNIN", AES.Encrypt("123456", realAES),"alibaba");
		System.out.println("Post内容：");
		System.out.println(gson.toJson(postBundle, RegisterRequest.class));
		System.out.println("=========================");
		w.println(gson.toJson(postBundle, RegisterRequest.class));
		w.flush();
		
		// ready to receive
		if (connection.getResponseCode() == 200) {
		System.out.println("注册成功");
		}
		else {
			System.out.println("服务器拒绝注册!!!!!");
		}
		
		
		
	}
	
	public void postMoment() throws Exception{
		URL url = new URL(mainURL + "/postMoment?sessionID="+String.valueOf(sessionid));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		System.out.println("正在尝试发送Moment。。。URL:" + url+"\n");
		connection.setDoOutput(true); // send a POST message
		connection.setRequestMethod("POST");
		
		// transfer into Json and put it into a stream.
		PrintWriter w = new PrintWriter(connection.getOutputStream());
		PostMoment postBundle = new PostMoment(
				"武汉",
				"2018/4/19 17:21:18",
				2, 
				"www.baidu.com", 
				"Luke", 
				"www.pic.com", 
				"啊啊啊啊啊啊啊啊啊啊啊！");
		System.out.println("Post内容：");
		System.out.println(gson.toJson(postBundle, PostMoment.class));
		System.out.println("=========================");
		w.println(gson.toJson(postBundle, PostMoment.class));
		w.flush();
		
		// ready to receive
		if (connection.getResponseCode() == 200) {
		System.out.println("发送Moment成功");
		}
		else {
			System.out.println("发送Moment失败!!!!!");
		}
		
		
		
	}
	
	public void postComment() throws Exception{
		URL url = new URL(mainURL + "/postComment?sessionID="+String.valueOf(sessionid));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		System.out.println("正在尝试发送Moment。。。URL:" + url+"\n");
		connection.setDoOutput(true); // send a POST message
		connection.setRequestMethod("POST");
		
		// transfer into Json and put it into a stream.
		PrintWriter w = new PrintWriter(connection.getOutputStream());
		PostComment postBundle = new PostComment(
				2,"Luke",1,"略略略","2015-02-02 03-03-00"
				);
		
		System.out.println("Post内容：");
		System.out.println(gson.toJson(postBundle, 
				PostComment.class));
		System.out.println("=========================");
		w.println(gson.toJson(postBundle, 
				PostComment.class));
		w.flush();
		
		// ready to receive
		if (connection.getResponseCode() == 200) {
		System.out.println("发送Moment成功");
		}
		else {
		System.out.println("发送Moment失败!!!!!");
		}
		
		
		
	}
	
	public void freshMoments() throws Exception{
		URL url = new URL(mainURL + "/freshMoments?sessionID="+String.valueOf(sessionid));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		System.out.println("正在尝试更新Moment。。。URL:" + url+"\n");
		connection.setDoOutput(true); // send a POST message
		connection.setRequestMethod("POST");
		
		// transfer into Json and put it into a stream.
		PrintWriter w = new PrintWriter(connection.getOutputStream());
		MomentFreshQuery postBundle = new MomentFreshQuery(
				2,"武汉","2018-05-05 10:10:10"
				);
		
		System.out.println("Post内容：");
		System.out.println(gson.toJson(postBundle, 
				MomentFreshQuery.class));
		System.out.println("=========================");
		w.println(gson.toJson(postBundle, 
				MomentFreshQuery.class));
		w.flush();
		
		// ready to receive
		if (connection.getResponseCode() == 200) {
		System.out.println("更新Moment成功");
		BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		Moments rbundle = null;
		rbundle = gson.fromJson(r, Moments.class);
		System.out.println("从服务器接收到的Moment更新：");
		System.out.println(gson.toJson(rbundle,Moments.class));
		
		}
		else {
		System.out.println("更新Moment失败!!!!!");
		}
		
		
		
	}
	
	public void freshComments() throws Exception{
		URL url = new URL(mainURL + "/freshComments?sessionID="+String.valueOf(sessionid));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "application/json");
		System.out.println("正在尝试更新Comment。。。URL:" + url+"\n");
		connection.setDoOutput(true); // send a POST message
		connection.setRequestMethod("POST");
		
		// transfer into Json and put it into a stream.
		PrintWriter w = new PrintWriter(connection.getOutputStream());
		CommentFreshQuery postBundle = new CommentFreshQuery(
				1,1,"2015-02-06 13-13-13"
				);
		
		System.out.println("Post内容：");
		System.out.println(gson.toJson(postBundle, 
				CommentFreshQuery.class));
		System.out.println("=========================");
		w.println(gson.toJson(postBundle, 
				CommentFreshQuery.class));
		w.flush();
		
		// ready to receive
		if (connection.getResponseCode() == 200) {
		System.out.println("更新Comment成功");
		BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		Comments rbundle = null;
		rbundle = gson.fromJson(r, Comments.class);
		System.out.println("从服务器接收到的Comment更新：");
		System.out.println(gson.toJson(rbundle,Comments.class));
		
		}
		else {
		System.out.println("更新Comment失败!!!!!");
		}
		
		
		
	}
	
	public void uploadAvatarPicture() throws Exception{
		
		 //文件位置所在地
		File file = new File("test2.jpg");
		String prefix=file.getName().substring(file.getName().lastIndexOf(".")+1);
        InputStream is = new FileInputStream(file);  
        BufferedInputStream bis = new BufferedInputStream(is);  
		
		URL url = new URL(mainURL + "/uploadPicture/avatar?sessionID="+String.valueOf(sessionid));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type",  "image/"+prefix);
		System.out.println("正在尝试上传一张图片。。。URL:" + url+"\n");
		connection.setDoOutput(true); 
		connection.setRequestMethod("POST");
		
		//try里面拿到输出流，输出端就是服务器端  
		BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream()); 
              
         
              
            byte[] buf= new byte[1024];  
            int length = 0;  
            length = bis.read(buf);  
            while(length!=-1) {  
                bos.write(buf, 0, length);  
                length = bis.read(buf);  
            }  
            bis.close();  
            is.close();  
            bos.close();  
		
		// ready to receive
		if (connection.getResponseCode() == 200) {
		System.out.println("上传头像图片成功");
		BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String returnedUrl = gson.fromJson(r, String.class);
		System.out.println(returnedUrl);
		}
		else {
		System.out.println("上传头像图片失败");
		}
	}
	
	public void uploadCoverPicture() throws Exception{
		
		 //文件目录
		File file = new File("test2.jpg");
		String prefix=file.getName().substring(file.getName().lastIndexOf(".")+1);
        InputStream is = new FileInputStream(file);  
        
        BufferedInputStream bis = new BufferedInputStream(is);  
		
		URL url = new URL(mainURL + "/uploadPicture/cover?sessionID="+String.valueOf(sessionid));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type",  "image/"+prefix);
		System.out.println("正在尝试上传一张图片。。。URL:" + url+"\n");
		connection.setDoOutput(true); 
		connection.setRequestMethod("POST");
		
		//try里面拿到输出流，输出端就是服务器端  
			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
              
              
            byte[] buf= new byte[1024];  
            int length = 0;  
            length = bis.read(buf);  
            while(length!=-1) {  
                bos.write(buf, 0, length);  
                length = bis.read(buf);  
            }  
            bis.close();  
            is.close();  
            bos.close();  
          
		
		
		// ready to receive
		if (connection.getResponseCode() == 200) {
		System.out.println("上传封面图片成功");
		BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String returnedUrl = gson.fromJson(r, String.class);
		System.out.println(returnedUrl);
		}
		else {
		System.out.println("上传封面图片失败");
		}
	}
	
	public void uploadMomentPicture() throws Exception{
		 //文件目录
		File file = new File("test2.jpg");
		String prefix=file.getName().substring(file.getName().lastIndexOf(".")+1);
        InputStream is = new FileInputStream(file);  
        
        BufferedInputStream bis = new BufferedInputStream(is);  
		
		URL url = new URL(mainURL + "/uploadPicture/moment?sessionID="+String.valueOf(sessionid));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Content-Type", "image/"+prefix);
		System.out.println("正在尝试上传一张图片。。。URL:" + url+"\n");
		connection.setDoOutput(true); 
		connection.setRequestMethod("POST");
		
		//try里面拿到输出流，输出端就是服务器端  
        try (BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream())) {  
              
        
              
            byte[] buf= new byte[1024];  
            int length = 0;  
            length = bis.read(buf);  
            while(length!=-1) {  
                bos.write(buf, 0, length);  
                length = bis.read(buf);  
            }  
            bis.close();  
            is.close();  
            bos.close();  
        }  
		
		
		// ready to receive
		if (connection.getResponseCode() == 200) {
		System.out.println("上传动态图片成功");
		BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String returnedUrl = gson.fromJson(r, String.class);
		System.out.println(returnedUrl);
		}
		else {
		System.out.println("上传动态图片失败");
		}
	}
	public void dumpResponse(BufferedReader r) throws Exception {

		for (;;) {
			String l = r.readLine();
			if (l == null)
				break;
			System.out.println("Read: " + l);
		}
	}

}
