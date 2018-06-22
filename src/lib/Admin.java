package lib;

public class Admin {

	String AdminNo;
	String AdminName;
	String AdminPassword;
	
	
	public void setAdminNo(String no) {
		this.AdminNo = no;
	}
	public void setAdminName(String name) {
		this.AdminName = name;
	}

	public void setAdminPassword(String password) {
		this.AdminPassword = password;
	}
	
	
	public String getAdminNo() {
		return AdminNo;
	}
	public String getAdminName() {
		return AdminName;
	}	
	public String getAdminPassword() {
		return AdminPassword;
	}
}
