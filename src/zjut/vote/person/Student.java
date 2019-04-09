package zjut.vote.person;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable{
	private String name;
	private String password;
	private String college;
	private String no;
	private String firstVoteInCollege;
	private String firstVoteInSport;
	private int secondVote;
	private ArrayList<String> secondVoteNo;
	private int isChange;
	
	public Student() {}
	
	public void setSecondVote(int secondVote) {this.secondVote=secondVote;}
	public void setFirstVoteInCollege(String firstVoteInCollege) {this.firstVoteInCollege=firstVoteInCollege;}
	public void setFirstVoteInSport(String firstVoteInSport) {this.firstVoteInSport=firstVoteInSport;}
	public void setNo(String no) {this.no=no;}
	public void setName(String name) {this.name=name;}
	public void setPassword(String password) {this.password=password;}
	public void setCollege(String college) {this.college=college;}
	public void setSecondVoteNo(ArrayList<String> secondVoteNo) {this.secondVoteNo=secondVoteNo;}
	public void setIsChange(int isChange) {this.isChange=isChange;}
	
	public int getIsChange() {return isChange;}
	public int getSecondVote() {return secondVote;}
	public ArrayList<String> getSecondVoteNo() {return secondVoteNo;}
	public String getName() {return name;}
	public String getPassword() {return password;}
	public String getCollege() {return college;}
	public String getNo() {return no;}
	public String getFirstVoteInCollege() {return firstVoteInCollege;}
	public String getFirstVoteInSport() {return firstVoteInSport;}
}
