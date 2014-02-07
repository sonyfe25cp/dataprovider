package dataprovider.parser;

import org.junit.Test;

import dataprovider.parser.job.ContentParserFor51;

public class HtmlParserWithJsoupTest {
	
	@Test
	public void testParseCVFrom51(){
		String html = "/Users/omar/data/sh/01/3621401.html";
		ContentParserFor51 hpwj = new ContentParserFor51();
		hpwj.parseFromFile(html, "UTF-8");
		String json = hpwj.outputJson();
		System.out.println(json);
		
		String html2 = "/Users/omar/data/sz/03/5964703.html";
		ContentParserFor51 hpwj2 = new ContentParserFor51();
		hpwj2.parseFromFile(html2, "UTF-8");
		String json2 = hpwj2.outputJson();
		System.out.println(json2);
	}
	
	
	@Test
	public void testParseDetails(){
		String html = "src/test/resources/zhilian-details.html";
		String regex = "src/test/resources/zhilian-details";
		HtmlParserWithJsoup hpwj = new HtmlParserWithJsoup(regex);
		hpwj.parseFromFile(html, "UTF-8");
		hpwj.show();
	}
	
	
	@Test
	public void testParseFromFile() {
		String html = "src/test/resources/zhilian.html";
		String regex = "src/test/resources/zhilian";
		HtmlParserWithJsoup hpwj = new HtmlParserWithJsoup(regex);
		hpwj.parseFromFile(html, "UTF-8");
		hpwj.show();
	}
	@Test
	public void testParseFromUrl() {
		String url = "http://sou.zhaopin.com/jobs/searchresult.ashx?jl=%E5%8C%97%E4%BA%AC&sm=0&p=1";
		String regex = "src/test/resources/zhilian";
		HtmlParserWithJsoup hpwj = new HtmlParserWithJsoup(regex);
		hpwj.parseFromUrl(url);
		hpwj.show();
	}
	
	@Test
	public void testParseOnlyCity(){
		String html = "src/test/resources/city.html";
		String regex = "src/test/resources/city";
		HtmlParserWithJsoup hpwj = new HtmlParserWithJsoup(regex);
		hpwj.parseFromFile(html,"UTF-8");
		hpwj.show();
	}

}
