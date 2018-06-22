package lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.LinkDb;

public class UserInfo {
	
	public boolean getUserInfo(User user){
		
		String sql = "select * from user where no = ?";
		LinkDb show = new LinkDb();
		Connection db = show.getConn();
		try {
			PreparedStatement pstm = (PreparedStatement)db.prepareStatement(sql);
			pstm.setString(1, user.getUserNo());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				
				user.setUserNo(rs.getString("no"));
				user.setUserName(rs.getString("name"));
				user.setUserPassword(rs.getString("password"));
				user.setUserGold(rs.getDouble("gold"));
				pstm.close();
				db.close();
				return true;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}	
		return false;
	}
	
	
}
