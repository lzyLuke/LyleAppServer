package Server;
import static spark.Spark.*;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.google.gson.Gson;

import Book.loginBook;
import Handle.*;
import Request.*;
import Security.*;
import Utils.Session;
import spark.Spark;
import Reply.*;
public class myServer {
	Gson gson = new Gson();
	public static void main(String[] args) {
		myServer myserver= new myServer(8080);
		
		myserver.run();
	}
	
	public myServer(int port){

		port(port);
		Spark.staticFiles.externalLocation("src/main/resources/public/");
		
		
	
	}

	
	public void run() {
		//http//:localhost:8080/firstConnect?publicKey=###################
		//第一次尝试连接，返回一个服务器用客户端所给的公钥加密的AES钥匙，这样客户端用自己的私钥解开就可以使用AES加密传输了。
		get("/firstConnect", (request, response) -> {
			response.header("Content-Type", "application/json");
			int sessionID = Session.getLatestSessionID();
			String encryptedAESKEY = handleFirstConnection.handle(request.queryParams("publicKey"),sessionID);
			response.status(200);
			return new firstReply(encryptedAESKEY, sessionID);
		}, gson::toJson);
		
		//http//:localhost:8080/login?sessionID=#######
		/*PostJSON:
		 * LoginIN:{
		 * account:"XXXXX"
		 * password:"XXXXX"
		 * }
		*/
		//登录
		post("/login", (request, response) -> {
			response.header("Content-Type", "application/json");
			int sessionID = Integer.parseInt( request.queryParams("sessionID") );
			System.out.println("接收到登录请求。。。");
			LoginIn loginBundle = gson.fromJson(request.body(), LoginIn.class);
			System.out.println("用户名:" +loginBundle.account);
			
			String password = AES.Decrypt(loginBundle.password, loginBook.getInstance().getAesKey(sessionID));
			System.out.println("密码:" +password);
			
			UserInfo user= handleLogin.handle(loginBundle.account,password);
			
			if(user!=null) {
			response.status(200);
			return user;}
			else {
			response.status(403);
			return null;
			}
		}, gson::toJson);
		
		//注册
		//http//:localhost:8080/register?sessionID=#######
				/*Register:
				 * LoginIN:{
				 * account:"XXXXX"
				 * password:"XXXXX"
				 * nickname:"XXXXX"
				 * }
				*/
		post("/register", (request, response) -> {
			response.header("Content-Type", "application/json");
			int sessionID = Integer.parseInt( request.queryParams("sessionID") );
			System.out.println("接收到注册请求。。。");
			RegisterRequest registerBundle = gson.fromJson(request.body(), RegisterRequest.class);
			System.out.println("用户名:" +registerBundle.account);
			
			String password = AES.Decrypt(registerBundle.password, loginBook.getInstance().getAesKey(sessionID));
			System.out.println("密码:" +password);
			
	
			
			if(handleRegister.handle(registerBundle.account,password,registerBundle.nickname)) {
			response.status(200);
			return "Register OK";}
			else {
			response.status(403);
			return "Register Fail";
			}
		}, gson::toJson);
		
		post("/postMoment", (request, response) -> {
			response.header("Content-Type", "application/json");
			int sessionID = Integer.parseInt( request.queryParams("sessionID") );
			System.out.println("接收到发送Moment请求。。。");
			PostMoment bundle = gson.fromJson(request.body(), PostMoment.class);
			
			if(handlePostMoment.handle(bundle)) {
			response.status(200);
			System.out.println("成功接收Moment");
			return "PostMoment OK";}
			else {
			response.status(403);
			return "PostMoment Fail";
			}
		}, gson::toJson);
		
		
		post("/postComment", (request, response) -> {
			response.header("Content-Type", "application/json");
			int sessionID = Integer.parseInt( request.queryParams("sessionID") );
			System.out.println("接收到发送Comment请求。。。");
			PostComment bundle = gson.fromJson(request.body(), PostComment.class);
			
			if(handlePostComment.handle(bundle)) {
			response.status(200);
			System.out.println("成功接收Comment");
			return "PostMoment OK";}
			else {
			response.status(403);
			return "PostMoment Fail";
			}
		}, gson::toJson);
		
		
		
		post("/freshMoments", (request, response) -> {
			Moments moments=null;
			try {
			response.header("Content-Type", "application/json");
			int sessionID = Integer.parseInt( request.queryParams("sessionID") );
			System.out.println("接收到更新Moments请求。。。");
			MomentFreshQuery bundle = gson.fromJson(request.body(), MomentFreshQuery.class);
			moments = handleFreshMoment.handle(bundle);
			response.status(200);
			}catch(Exception e) {
				e.printStackTrace();
				response.status(403);
			}
			return moments;
			
		}, gson::toJson);
		
		post("/freshComments", (request, response) -> {
			Comments comments=null;
			try {
			response.header("Content-Type", "application/json");
			int sessionID = Integer.parseInt( request.queryParams("sessionID") );
			System.out.println("接收到更新Comments请求。。。");
			CommentFreshQuery bundle = gson.fromJson(request.body(), CommentFreshQuery.class);
			comments = handleFreshComment.handle(bundle);
			response.status(200);
			}catch(Exception e) {
				e.printStackTrace();
				response.status(403);
			}
			return comments;
			
		}, gson::toJson);
		
		
		post("/uploadPicture/avatar", (request, response) -> {
			String uuid ="Default";
			String prefix;
			try {
			response.header("Content-Type", "application/json");
			int sessionID = Integer.parseInt( request.queryParams("sessionID") );
			System.out.println("接收到上传图片请求。。。");
			request.bodyAsBytes();
			prefix=request.contentType().substring(6);
				byte[] bt = request.bodyAsBytes();
				uuid = UUID.randomUUID().toString().replace("-", "");
	            OutputStream os = new FileOutputStream("src/main/resources/public/avatar/"+uuid+"."+prefix);  
	            BufferedOutputStream bos = new BufferedOutputStream(os);  
	             bos.write(bt);
	         
	           
	            bos.close();  
	            os.close();  
	 
			
			response.status(200);
			}catch(Exception e) {
				e.printStackTrace();
				response.status(403);
				return "Fail";
			}
			return "http://localhost:8080/avatar/"+uuid+".jpg";
			
		},gson::toJson);
		
		post("/uploadPicture/cover", (request, response) -> {
			String uuid ="Default";
			String prefix;
			try {
		
			response.header("Content-Type", "application/json");
			prefix = request.contentType().substring(6);
			int sessionID = Integer.parseInt( request.queryParams("sessionID") );
			System.out.println("接收到上传图片请求。。。");
			request.bodyAsBytes();
		
				byte[] bt = request.bodyAsBytes();
				uuid = UUID.randomUUID().toString().replace("-", "");
	            OutputStream os = new FileOutputStream("src/main/resources/public/cover/"+uuid+"."+prefix);  
	            BufferedOutputStream bos = new BufferedOutputStream(os);  
	             bos.write(bt);
	         
	           
	            bos.close();  
	            os.close();  
	 
			
			response.status(200);
			}catch(Exception e) {
				e.printStackTrace();
				response.status(403);
				return "Fail";
			}
			return "http://localhost:8080/cover/"+uuid+".jpg";
			
		},gson::toJson);
		
		post("/uploadPicture/moment", (request, response) -> {
		
			String uuid ="Default";
			String prefix;
			try {
			response.header("Content-Type", "application/json");
			prefix = request.contentType().substring(6);
			int sessionID = Integer.parseInt( request.queryParams("sessionID") );
			System.out.println("接收到上传图片请求。。。");
			request.bodyAsBytes();
		
				byte[] bt = request.bodyAsBytes();
				uuid = UUID.randomUUID().toString().replace("-", "");
	            OutputStream os = new FileOutputStream("src/main/resources/public/moment/"+uuid+"."+prefix);  
	            BufferedOutputStream bos = new BufferedOutputStream(os);  
	             bos.write(bt);
	         
	           
	            bos.close();  
	            os.close();  
	 
			
			response.status(200);
			}catch(Exception e) {
				e.printStackTrace();
				response.status(403);
				return "Fail";
			}
			return "http://localhost:8080/moment/"+uuid+".jpg";
			
		},gson::toJson);
		
		/*
		get("/moment:name", (request, response) -> {
			
			try {
			String filename = request.params(":name");
			File newfile = new File("src/main/resources/public/moment"+filename);
			response.header("Content-Type", "application/json");
			//String prefix = request.contentType().substring(6);
			System.out.println("接收到获取服务器图片请求。。。");
			response.
	      
			response.status(200);
			}catch(Exception e) {
				e.printStackTrace();
				response.status(403);
				response.header("Content-Type", "application/json");
				return "Fail";
			}
			return response.raw();
			
		});
		*/
		
		
	}
		
		
		
		
	
	
	
}
