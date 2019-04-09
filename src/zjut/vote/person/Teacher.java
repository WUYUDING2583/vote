package zjut.vote.person;


import java.io.Serializable;

public class Teacher implements Serializable{
	private String no;
	private String name;
	private String sex;
	private String birth;
	private String college;
	private String course;
	private String introduction;
	private String picture;
	private int firstVote;
	private int secondVote;
	private int isF;
	
	public Teacher() {}
	
	public void setIsF(int isF) {this.isF=isF;}
	public void setFirstVote(int firstVote) {this.firstVote=firstVote;}
	public void setSecondVote(int secondVote) {this.secondVote=secondVote;}
	public void setNo(String no) {this.no=no;}
	public void setName(String name) {this.name=name;}
	public void setSex(String sex) {this.sex=sex;}
	public void setBirth(String birth) {this.birth=birth;}
	public void setCollege(String college) {this.college=college;}
	public void setIntroduction(String introduction) {this.introduction=introduction;}
	public void setPicture(String picture) {this.picture=picture;}
	public void setCourse(String course) {this.course=course;}
	
	public int getIsF() {return isF;}
	public String getNo() {return no;}
	public String getName() {return name;}
	public String getSex() {return sex;}
	public String getBirth() {return birth;}
	public String getCollege() {return college;}
	public String getCourse() {return course;}
	public String getIntroduction() {return introduction;}
	public String getPicture() {return picture;}
	public int getSecondVote() {return secondVote;}
	public int getFirstVote() {return firstVote;}
}
