package dataprovider.parser;

import org.junit.Test;

import dataprovider.parser.HtmlParserWithJsoup;

public class HtmlParserWithJsoupTest {
	
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
