package lib;

import java.sql.*;

public class Record {

	String record_no;
	String good_name;
	double good_gold;
	int good_num;
	java.util.Date date;
	Date sql_date;
	
	public void setRecordNo(String no) {
		this.record_no = no;
	}
	
	public void setGoodName(String name) {
		this.good_name = name;
	}
	
	public void setGoodGold(double gold) {
		this.good_gold = gold;
	}
	
	public void setGoodNum(int num) {
		this.good_num = num;
	}
	
	public String getRecordNo() {
		return this.record_no;
	}
	
	public String getGoodName() {
		return this.good_name;
	}
	
	public double getGoodGold() {
		return this.good_gold;
	}
	
	public int getGoodNum() {
		return this.good_num;
	}
	
	public Date getRecordDate() {
		
		date = new java.util.Date();
		sql_date = new Date(date.getTime());
		return sql_date;
	}
	
	
}
