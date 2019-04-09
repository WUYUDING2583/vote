package zjut.vote.person;

import java.io.Serializable;

public class Admin  implements Serializable{
	private String no;
	private String password;
	
	public Admin() {}
	
	public void setNo(String no) {this.no=no;}
	public void setPassword(String password) {this.password=password;}
	
	public String getNo() {return no;}
	public String getPassword() {return password;}
}
