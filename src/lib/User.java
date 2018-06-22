package lib;

public class User {
	String UserNo;
	String UserName;
	String UserPassword;
	double UserGold;
	
	
	public void setUserNo(String no) {
		this.UserNo = no;
	}
	public void setUserName(String name) {
		this.UserName = name;
	}
	public void setUserGold(double gold) {
		this.UserGold = gold;
	}
	public void setUserPassword(String password) {
		this.UserPassword = password;
	}
	
	
	public String getUserNo() {
		return UserNo;
	}
	public String getUserName() {
		return UserName;
	}	
	public double getUserGold() {
		return UserGold;
	}
	public String getUserPassword() {
		return UserPassword;
	}
	
}
