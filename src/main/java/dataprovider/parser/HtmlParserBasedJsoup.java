package dataprovider.parser;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import dataprovider.exception.SomethingUnknownException;

public abstract class HtmlParserBasedJsoup implements HtmlParserInterface{
	protected Document doc;
	private String garbagePath;
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
			if(garbagePath!=null){
				try {
					File garbage = new File(garbagePath);
					if(!garbage.exists()){
						garbage.mkdirs();
					}
					FileUtils.copyFileToDirectory(html, garbage);
					html.delete();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
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
	public void parse(Document document) throws SomethingUnknownException{
		this.doc = document;
		parseDocument();
		autoClear();
	}
	public void autoClear(){
		doc = null;
	}
	public abstract void parseDocument() throws SomethingUnknownException;
	public abstract String outputJson();
	public String getGarbagePath() {
		return garbagePath;
	}
	public void setGarbagePath(String garbagePath) {
		this.garbagePath = garbagePath;
	}
	
}
