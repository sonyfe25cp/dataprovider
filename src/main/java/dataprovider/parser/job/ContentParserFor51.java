package dataprovider.parser.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dataprovider.parser.HtmlParserBasedJsoup;

public class ContentParserFor51 extends HtmlParserBasedJsoup{

	private List<Element> tables;
	private Map<TableTitleFor51, Element> tablesWithLabel;
	private List<String> partJsons;
	
	public ContentParserFor51() {
		super();
	}
	public void clear(){
		partJsons = new ArrayList<String>();
		tables = new ArrayList<Element>();
		tablesWithLabel = new HashMap<TableTitleFor51, Element>();
	}
	
	public void parseDocument(){
		//0.先清空再解析
		clear();
		//1.预处理，获取各种table
		preprocessing();
		if(tables == null || tables.size() == 0){
			System.out.println("该简历无法解析.");
			return;
		}
		//2.判断不同table的归属
		tableJudge();
		//3.根据不同的table归属解析其正文
		tableDetect();
		//4.聚合最终结果
		outputJson();
	}
	private void tableJudge(){
		for(Element table : tables){
			String text = table.text();
			if(text.contains("居住地")||text.contains("户口")){
				tablesWithLabel.put(TableTitleFor51.BasicInformation, table);
			}else if(text.contains("最近工作")){
				tablesWithLabel.put(TableTitleFor51.LastestWork, table);
			}else if(text.contains("最高学历")){
				tablesWithLabel.put(TableTitleFor51.LastestEducation, table);
			}else if(text.contains("目前年薪")||text.contains("基本工资")){
				tablesWithLabel.put(TableTitleFor51.CurrentSalary, table);
			}else if(text.contains("自我评价")){
				tablesWithLabel.put(TableTitleFor51.SelfEvaluation, table);
			}else if(text.contains("期望月薪") || text.contains("到岗时间") ){
				tablesWithLabel.put(TableTitleFor51.DesiredPosition, table);
			}else{
				Element td = table.parent();
				Element tr = td.parent();
				int index = tr.siblingIndex();
				Element tbody = tr.parent();
				Elements nodes = tbody.getElementsByIndexEquals(index-1);//若preprocessing改变，这地方也要改变
				if(nodes.size() > 0){
					Element nameNode = nodes.get(1);
					String nameText = nameNode.text();
//					System.out.println("nameNode : " + nameText);
					if(nameText.equals("工作经验")){
						tablesWithLabel.put(TableTitleFor51.WorkExpriences, table);
					}else if(nameText.equals("教育经历")){
						tablesWithLabel.put(TableTitleFor51.Education, table);
					}else if(nameText.equals("培训经历")){
						tablesWithLabel.put(TableTitleFor51.TrainExpriences, table);
					}else if(nameText.equals("语言能力")){
						tablesWithLabel.put(TableTitleFor51.Language, table);
					}else if(nameText.equals("项目经验")){
						tablesWithLabel.put(TableTitleFor51.ProjectExpriences, table);
					}else{
						System.out.println("没探测出来这是啥内容....=.=");
						System.out.println("************************");
						System.out.println(text);
						System.out.println("************************");
					}
				}
			}
		}
	}
	void basicInformationDetect(Element table){
		String categoryName= "基本信息";
		Elements trs = table.select("tr");
		Element basicTr = trs.get(0);
		String[] infos = basicTr.text().split("\\|");
		List<String> jsonArray = new  ArrayList<String>();
		for(String tmp : infos){
			if(tmp.equals("年")){
				jsonArray.add(json("经验年限",tmp));
			}else if(tmp.equals("男") || tmp.equals("女") || tmp.equals("保密")){
				jsonArray.add(json("性别",tmp));
			}else if(tmp.equals("岁")){
				if(tmp.contains("（") && tmp.contains("）")){
					String birthday = tmp.substring(tmp.indexOf("（"), tmp.indexOf("）"));
					String age = tmp.substring(0, tmp.indexOf("岁"));
					jsonArray.add(json("年龄",age	));
					jsonArray.add(json("出生年月", birthday));
				}
			}else if(tmp.contains("婚")){
				jsonArray.add(json("婚姻状况",tmp));
			}else if(tmp.contains("cm")){
				jsonArray.add(json("身高",tmp));
			}
		}
		Element otherTr = trs.get(1);
		Elements others = otherTr.select("td");
		if(others.size() > 0){
			int i = 1 ; 
			String tmpText = "";
			for(Element e : others){
				String value = e.text().replace("　", "");
				if(i % 2 != 0 ){
					tmpText = value;
				}else{
					jsonArray.add(json(tmpText, value));
				}
				i ++;
			}
		}
		String partJson = json(categoryName, jsonArray);
		partJsons.add(partJson);
	}
	private void lastestWorkDetect(Element table){
		String categoryName = "最近工作";
		List<String> jsonArray = new ArrayList<String>();
		for(Element tr : table.select("tr")){
			Elements tds = tr.select("td");
			if(tds.size() == 1){
				Element td = tds.get(0);
				Element tmp = td.select("span").get(1);
				if(tmp!=null){
					String range= tmp.text();
					jsonArray.add(json("工作时间", range));
				}
			}else{
				if(tds.size() > 0){
					int i = 1 ; 
					String tmpText = "";
					for(Element e : tds){
						String value = e.text().replace("　", "").replace("：", "").replace("[", "").replace("]", "");
						if(i % 2 != 0 ){
							tmpText = value;
						}else{
							jsonArray.add(json(tmpText, value));
						}
						i ++;
					}
				}
			}
		}
		String partJson = json(categoryName, jsonArray);
		partJsons.add(partJson);
	}
	private void lastestEducation(Element table){
		String categoryName = "最高学历";
		List<String> jsonArray = new ArrayList<String>();
		for(Element tr : table.select("tr")){
			Elements tds = tr.select("td");
			if(tds.size() == 1){
			}else{
				if(tds.size() > 0){
					int i = 1 ; 
					String tmpText = "";
					for(Element e : tds){
						String value = e.text().replace("　", "").replace("：", "").replace("[", "").replace("]", "");
						if(i % 2 != 0 ){
							tmpText = value;
						}else{
							jsonArray.add(json(tmpText, value));
						}
						i ++;
					}
				}
			}
		}
		String partJson = json(categoryName, jsonArray);
		partJsons.add(partJson);
	}
	private void currentSalaryDetect(Element table){
		String categoryName = "当前工资";
		List<String> jsonArray = new ArrayList<String>();
		for(Element tr : table.select("tr")){
			Elements tds = tr.select("td");
			if(tds.size() == 1){
			}else{
				if(tds.size() > 0){
					int i = 1 ; 
					String tmpText = "";
					for(Element e : tds){
						String value = e.text().replace("　", "").replace("：", "").replace("[", "").replace("]", "");
						if(value.length() > 0){
							if(i % 2 != 0 ){
								tmpText = value;
							}else{
								jsonArray.add(json(tmpText, value));
							}
							i ++;
						}
					}
				}
			}
		}
		String partJson = json(categoryName, jsonArray);
		partJsons.add(partJson);
	}
	private void selfEvaluationDetect(Element table){
		String categoryName = "自我评价";
		String s = "";
		for(Element td : table.select("td")){
			String text = td.text();
			if(text.trim().length() > 0){
				if(!text.equals("自我评价")){
					s += text;
				}
			}
		}
		partJsons.add(json(categoryName, s));
	}
	private void desiredPositionDetect(Element table){
		String categoryName = "求职意向";
		List<String> jsonArray = new ArrayList<String>();
		for(Element td : table.select("td")){
			String raw = td.text().replace("\"", "");
			String[] raws = raw.split("：");
			jsonArray.add(json(raws[0], raws[1]));
		}
		String partJson = json(categoryName, jsonArray);
		partJsons.add(partJson);
	}
	private void educationDetect(Element table){
		String categoryName = "教育经历";
		List<String> jsonArray = new ArrayList<String>();
		for(Element tr : table.select("tr")){
			Elements tds = tr.select("td");
			if(tds.size() == 4){
				String range = tds.get(0).text();
				jsonArray.add(json("学习时间", range));
				String university = tds.get(1).text();
				jsonArray.add(json("学校", university));
				String pro = tds.get(2).text();
				jsonArray.add(json("专业", pro));
				String level = tds.get(3).text();
				jsonArray.add(json("学历", level));
			}
		}
		String partJson = json(categoryName, jsonArray);
		partJsons.add(partJson);
	}
	private void tableDetect(){
		for(Entry<TableTitleFor51, Element> entry : tablesWithLabel.entrySet()){
			TableTitleFor51 key = entry.getKey();
			Element table = entry.getValue();
			switch(key){
			case BasicInformation:
				basicInformationDetect(table);
				break;
			case LastestWork:
				lastestWorkDetect(table);
				break;
			case LastestEducation:
				lastestEducation(table);
				break;
			case CurrentSalary:
				currentSalaryDetect(table);
				break;
			case SelfEvaluation:
				selfEvaluationDetect(table);
				break;
			case DesiredPosition:
				desiredPositionDetect(table);
				break;
			case WorkExpriences:
				break;
			case Education:
				educationDetect(table);
				break;
			case TrainExpriences:
				break;
			case ProjectExpriences:
				break;
			case Language:
				break;
			}
		}
		
	}
	public List<Element> preprocessing(){
		Elements root = doc.select("#divResume");
		for (Element tr: root.select("tr")) {//去掉无用tr
			if (tr.text().isEmpty()) {
				tr.remove();
			}
		}
		for(Element table: root.select("table")) {
			if (table.select("table").size() == 1) {
				if(table.text().trim().length() > 0){
					tables.add(table);
				}
			}
		}
		return tables;
	}
	@Override
	public String outputJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		int index = 0;
		for(String part : partJsons){
			sb.append(part);
			index++;
			if(index != partJsons.size()){
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}
	private String json(String name, String value){
		return "\""+name+"\":\""+value+"\"";
	}
	private String json(String name, List<String> jsonArray){
		StringBuilder sb = new StringBuilder();
		sb.append("\""+ name +"\":");
		sb.append("{");
		int index = 0;
		for(String tmp : jsonArray){
			sb.append(tmp);
			index ++;
			if(index != jsonArray.size()){
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}
}
