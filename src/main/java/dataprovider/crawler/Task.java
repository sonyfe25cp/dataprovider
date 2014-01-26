package dataprovider.crawler;

public abstract class Task {

	private String beginUrl;
	private int cursor;
	
	public String getNextUrl(){
		cursor ++;
		return generateUrl(beginUrl, cursor);
	}
	
	public String getBeginUrl() {
		return beginUrl;
	}

	public void setBeginUrl(String beginUrl) {
		this.beginUrl = beginUrl;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public abstract String generateUrl(String beginUrl, int cursor);
}
