package dataprovider.crawler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import dataprovider.utils.CharsetUtils;

public class CrawlerForZhiLian extends SimpleCrawler{

	
	public CrawlerForZhiLian (){
		init();
	}
	public void init(){
		generateTasks();
	}
	private String basicUrl = "http://sou.zhaopin.com/jobs/searchresult.ashx?jl=%E5%8C%97%E4%BA%AC&sm=0&sg=d6b2663cf1c14d2b952b90bf677fdc7c&p=";
	private void generateTasks(){
		List<String> cities = readCityFromFile("src/test/resources/city.data");
		for(String city : cities){
			TaskForZhiLian tzl = new TaskForZhiLian();
			tzl.setBeginUrl(newUrl(basicUrl, city));
			tzl.setCursor(0);
			tasks.add(tzl);
		}
	}
	private String newUrl(String basicUrl, String city){
		String begin = basicUrl.substring(0, basicUrl.indexOf("="));
		String end = basicUrl.substring(basicUrl.indexOf("&"));
		String utf8 = CharsetUtils.toUtf8(city);
		String url = begin+"="+utf8+end;
		return url;
	}
	
	private List<String> readCityFromFile(String cityFile){
		try {
			return FileUtils.readLines(new File(cityFile));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args){
		CrawlerForZhiLian cfz = new CrawlerForZhiLian();
		cfz.crawl();
	}
}
