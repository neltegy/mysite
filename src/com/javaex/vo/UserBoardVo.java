package com.javaex.vo;

public class UserBoardVo {
	private int userno;
	private String name;
	private String email;
	private String password;
	private int boardno;
	private String title;
	private String content;
	private String reg_date;
	private int hit;
	private int user_no;
	
	public UserBoardVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBoardVo(int userno, String name, String email, String password, int boardno, String title,
			String content, String reg_date, int hit, int user_no) {
		super();
		this.userno = userno;
		this.name = name;
		this.email = email;
		this.password = password;
		this.boardno = boardno;
		this.title = title;
		this.content = content;
		this.reg_date = reg_date;
		this.hit = hit;
		this.user_no = user_no;
	}

	public int getUserno() {
		return userno;
	}

	public void setUserno(int userno) {
		this.userno = userno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getBoardno() {
		return boardno;
	}

	public void setBoardno(int boardno) {
		this.boardno = boardno;
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
		return "UserBoardVo [userno=" + userno + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", boardno=" + boardno + ", title=" + title + ", content=" + content + ", reg_date=" + reg_date
				+ ", hit=" + hit + ", user_no=" + user_no + "]";
	}
	
	

}
