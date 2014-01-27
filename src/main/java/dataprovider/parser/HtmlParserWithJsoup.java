package dataprovider.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParserWithJsoup {
	
	private List<Map<String, String>> valueList;
	private HtmlExpression expressions;
	private String filePath;
	
	/**
	 * config file
	 * @param file
	 */
	public HtmlParserWithJsoup(String file){
		this.filePath = file;
		expressions = HtmlExpression.readFromFile(filePath);
	}
	public void parseFromContent(String content){
		Document doc = Jsoup.parse(content);
		parse(doc);
	}
	public void parseFromFile(String html, String charset){
		Document doc;
		try {
			doc = Jsoup.parse(new File(html), charset);
			parse(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void parseFromUrl(String url){
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			parse(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void parse(Document doc){
		valueList = new ArrayList<Map<String, String>>();
		Elements tables = doc.select(expressions.getFrame());
		for(Element table : tables){
			Map<String, String> valueMap = new HashMap<String, String>();
			for(Expression expression : expressions.getExpressions()){
				String key = expression.getName();
				if(key.equalsIgnoreCase(HtmlExpression.Frame)){
					continue;
				}
				String value = expression.getPath();
				int location = expression.getLocation();
				Elements element = table.select(value);
				if(element != null){
					String htmlValue = "";
					if(location == 0){
						htmlValue = table.select(value).text();
					}else{
						if(element.size() >= location){
							htmlValue = element.get(location-1).text();
						}
					}
					valueMap.put(key, htmlValue);
				}
			}
			valueList.add(valueMap);
		}
	}
	
	public void show(){
		for(Map<String, String> valueMap : valueList){
			debugMap(valueMap);
		}
	}
	public void debugMap(Map<String, String> map ){
		for(Entry<String, String> entry : map.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			
			System.out.println(key+" -- "+value);
		}
		System.out.println("---------------------"+map.getClass().getSimpleName()+"-----------------------");
	}
	public List<Map<String, String>> getValueList() {
		return valueList;
	}
	public void setValueList(List<Map<String, String>> valueList) {
		this.valueList = valueList;
	}
}
