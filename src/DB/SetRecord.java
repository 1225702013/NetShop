package DB;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import lib.Record;

public class SetRecord {

	public boolean addRecord(Record record){
		String sql="insert into shopping (custom_no,good_name,good_gold,good_num,date) values (?,?,?,?,?)";
		LinkDb addsql = new LinkDb();
		Connection db = addsql.getConn();
		try {
			PreparedStatement Pmt = (PreparedStatement) db.prepareStatement(sql);
			Pmt.setString(1, record.getRecordNo());
			Pmt.setString(2, record.getGoodName());
			Pmt.setDouble(3, record.getGoodGold());
			Pmt.setInt(4, record.getGoodNum());
			Pmt.setDate(5, record.getRecordDate());
			Pmt.executeUpdate();
			Pmt.close();
			db.close();
			return true;	 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
}
