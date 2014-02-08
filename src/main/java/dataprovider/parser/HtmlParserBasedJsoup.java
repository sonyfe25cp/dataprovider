package dataprovider.parser;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import dataprovider.exception.SomethingUnknownException;

public abstract class HtmlParserBasedJsoup implements HtmlParserInterface{
	protected Document doc;
	public void parseFromContent(String content){
		doc = Jsoup.parse(content);
		parse(doc);
	}
	public void parseFromFile(File html, String charset){
		try {
			doc = Jsoup.parse(html, charset);
			parse(doc);
		}catch(SomethingUnknownException e){
			System.out.println("部分内容没有解析成功: path: "+html.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void parseFromFilePath(String htmlPath, String charset){
		File file = new File(htmlPath);
		parseFromFile(file, charset);
	}
	public void parseFromUrl(String url){
		try {
			doc = Jsoup.connect(url).get();
			parse(doc);
		}catch(SomethingUnknownException e){
			System.out.println("部分内容没有解析成功: url: "+url);
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
	public abstract void parseDocument() throws SomethingUnknownException;
	public abstract String outputJson();
}
