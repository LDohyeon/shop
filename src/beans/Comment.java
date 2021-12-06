package beans;

public class Comment {
	 
	private String id;
	private String comment;
	private char comment_delete;
	private String create_date;
	private String update_date;
	private String delete_date;
	private int comment_num;
	private int pk;
	private int comment_order;
	private int ccomment_order;
	
	private int count;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getId() {
		return id;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public char getComment_delete() {
		return comment_delete;
	}
	public void setComment_delete(char comment_delete) {
		this.comment_delete = comment_delete;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(String delete_date) {
		this.delete_date = delete_date;
	}
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public int getComment_order() {
		return comment_order;
	}
	public void setComment_order(int comment_order) {
		this.comment_order = comment_order;
	}
	public int getCcomment_order() {
		return ccomment_order;
	}
	public void setCcomment_order(int ccomment_order) {
		this.ccomment_order = ccomment_order;
	}


}
