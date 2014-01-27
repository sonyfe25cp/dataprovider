package dataprovider.crawler;

import junit.framework.TestCase;

import org.junit.Test;

public class SimpleCrawlerForZhiLianTest extends TestCase{

	@Test
	public void test() {
		CrawlerForZhiLian cfz = new CrawlerForZhiLian();
		cfz.crawl();
		
	}

}
