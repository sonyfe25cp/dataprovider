package dataprovider.crawler;

public class TaskForZhiLian extends Task{

	@Override
	public String generateUrl(String beginUrl, int cursor) {
		if(cursor < 91)//最多90页
			return beginUrl + cursor;
		else{
			return "";
		}
	}
	
	

}
