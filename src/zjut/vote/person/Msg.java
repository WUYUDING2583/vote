package zjut.vote.person;

import java.io.Serializable;
import java.util.ArrayList;

public class Msg implements Serializable{
	private String code;
	private String message;
	private String type;
	private Student student;
	private Teacher teacher;
	private int num;
	private ArrayList<Student> stuList;
	private ArrayList<Teacher> teaList;
	
	public Msg() {}
	
	public void setType(String type) {this.type=type;}
	public void setCode(String code) {this.code=code;}
	public void setMessage(String message) {this.message=message;}
	public void setStudent(Student student) {this.student=student;}
	public void setTeacher(Teacher teacher) {this.teacher=teacher;}
	public void setStuList(ArrayList<Student> stuList) {this.stuList=stuList;}
	public void setTeaList(ArrayList<Teacher> teaList) {this.teaList=teaList;}
	public void setNum(int num) {this.num=num;}
	
	public int getNum() {return num;}
	public String getType() {return type;}
	public String getCode() {return code;}
	public String getMessage() {return message;}
	public Student getStudent() {return student;}
	public Teacher getTeacher() {return teacher;}
	public ArrayList<Student> getStuList(){return stuList;}
	public ArrayList<Teacher> getTeaList(){return teaList;}
}
