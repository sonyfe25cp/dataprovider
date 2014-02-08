package dataprovider.parser;

import java.io.File;


public interface HtmlParserInterface {
	public void parseFromContent(String content);
	public void parseFromFilePath(String htmlPath, String charset);
	public void parseFromFile(File html, String charset);
	public void parseFromUrl(String url);
	public String outputJson();
}
