package dataprovider.parser;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class HtmlParserBasedJsoup implements HtmlParserInterface{
	protected Document doc;
	public void parseFromContent(String content){
		doc = Jsoup.parse(content);
		parse(doc);
	}
	public void parseFromFile(String html, String charset){
		try {
			doc = Jsoup.parse(new File(html), charset);
			parse(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void parseFromUrl(String url){
		try {
			doc = Jsoup.connect(url).get();
			parse(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void parse(Document document){
		this.doc = document;
		parseDocument();
		autoClear();
	}
	public void autoClear(){
		doc = null;
	}
	public abstract void parseDocument();
	public abstract String outputJson();
}
