package com.javaex.vo;

public class BoardVo {
	private int no;
	private String title;
	private String content;
	private String reg_date;
	private int hit;
	private int user_no;
	
	public BoardVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardVo(int no, String title, String content, String reg_date, int hit, int user_no) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.reg_date = reg_date;
		this.hit = hit;
		this.user_no = user_no;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
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
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", reg_date=" + reg_date + ", hit="
				+ hit + ", user_no=" + user_no + "]";
	}
	
	
}
