package dataprovider.crawler;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class CrawlerFor51Job {

	static String username = "";
	static String password = "";
	static String vipname = "";
	
	private HttpClient client;
	
	public CrawlerFor51Job(){
		init();
	}
	public void init(){
		client = new DefaultHttpClient();
	}
	private void login(){
		String formUrl = "http://ehire.51job.com/MainLogin.aspx";
		HttpPost post = new HttpPost();
	}
	

	public static void main(String[] args) {
		
	}
}
