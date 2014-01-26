package dataprovider.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HtmlExpression{
	
	private List<Expression> expressions;
	public final static String Frame = "frame"; 
	
	public static HtmlExpression readFromFile(String file){
		HtmlExpression htmlExpression = new HtmlExpression();
		try {
			List<Expression> expressions = null;
			expressions = Expression.readFromFile(file);
			htmlExpression.setExpressions(expressions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlExpression;
	}
	
	public String getFrame(){
		String path = "";
		for(Expression expression : expressions){
			if(expression.getName().equalsIgnoreCase(Frame)){
				path = expression.getPath();
			}
		}
		return path;
	}

	public List<Expression> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}
	
}
class Expression {
	
	private String name;
	private String path;
	private int location;
	
	public static List<Expression> readFromFile(String file) throws IOException{
		List<Expression> expressions = new ArrayList<Expression>();
		BufferedReader br = new BufferedReader(new FileReader(new File(file)));
		String line = br.readLine();
		while(line != null){
			if(line.contains(" ")){
				String value = line.substring(line.indexOf(" "));
				String name = line.replace(value, "");
				int location = 0;
				if(value.contains(":eq")){
					String eq = value.substring(value.indexOf(":"));
					value = value.replace(eq, "");
					String num = eq.replace(":eq(", "");
					num = num.replace(")", ""	);
					location = Integer.parseInt(num);
				}
				Expression expression = new Expression(name, value, location);
				expressions.add(expression);
			}
			line = br.readLine();
		}
		br.close();
		return expressions;
	}
	
	public Expression(String name, String path, int location) {
		super();
		this.name = name;
		this.path = path;
		this.location = location;
	}
	
	public String toString(){
		return "name: "+ name +", path: "+ path +", location: "+location;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	
	

}
