package dataprovider.parser;


public interface HtmlParserInterface {
	public void parseFromContent(String content);
	public void parseFromFile(String html, String charset);
	public void parseFromUrl(String url);
	public String outputJson();
}
