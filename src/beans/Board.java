package beans;

public class Board {

	private String num;
	private String id;
	private String title;
	private String content;
	private String date;
	private String hits;
	
	public Board() {
		
	}
	public Board(String str) {
		System.out.println("////////////////////////////////////"+str+"���� ��ü���� �õ�");
	}
	
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}
	
	
	
	
}
