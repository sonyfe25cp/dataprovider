package dataprovider.crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

public class SimpleCrawler {
	
	protected List<Task> tasks;
	private HttpClient client;
	
	public SimpleCrawler(){
		tasks = new ArrayList<Task>();
		ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager();
		client = new DefaultHttpClient(connManager);
	}
	public void crawl(){
		for(Task task : tasks){
			Thread thread = new Thread(new CrawlWorkerForZhiLian(client, task));
			thread.start();
		}
	}
}
