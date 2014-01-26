package dataprovider.crawler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;

import dataprovider.model.Job;
import dataprovider.parser.HtmlParserWithJsoup;

public class CrawlWorkerForZhiLian extends CrawlWorker{

	public CrawlWorkerForZhiLian(HttpClient client, Task task) {
		super(client, task);
	}
	HtmlParserWithJsoup hpwj = new HtmlParserWithJsoup("src/main/resources/zhilian");
	@Override
	public void work(String html, String url) {
		try {
			FileUtils.writeStringToFile(new File(urlToFileName(url)), html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		hpwj.parseFromContent(html);
		hpwj.show();
		List<Map<String, String>> mapList = hpwj.getValueList();
		List<Job> jobs = Job.fromList(mapList);
		DateConsumer.add(jobs);
	}

	public String urlToFileName(String url){
		String city = url.substring(url.indexOf("="), url.indexOf("&"));
		String num = url.substring(url.lastIndexOf("="));
		return "src/test/resources/pages/"+city+num;
	}
}
