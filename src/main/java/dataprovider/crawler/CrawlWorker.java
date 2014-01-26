package dataprovider.crawler;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public abstract class CrawlWorker implements Runnable {
	private Task task;
	private HttpClient client;
	public CrawlWorker(HttpClient client, Task task) {
		this.task = task;
		this.client = client;
	}

	public void run() {
		String url = task.getNextUrl();
		System.out.println(url);
		if(url.length()>0){
			String html = get(url);
			try {
				work(html, url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public abstract void work(String html, String url) throws Exception;

	public String get(String url) {
		HttpGet get = new HttpGet(url);
		String html = "";
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				html = IOUtils.toString(response.getEntity().getContent());
			}
		}catch(java.lang.IllegalStateException e){
			System.out.println("error: "+url);
		}catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return html;
	}

}
