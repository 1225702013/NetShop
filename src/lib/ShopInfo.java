package lib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.LinkDb;

public class ShopInfo {

	public boolean getShopInfo(Shop shop) {
		
		String sql = "select * from good where no = ?";
		LinkDb show = new LinkDb();
		Connection db = show.getConn();
		try {
			PreparedStatement pstm = (PreparedStatement)db.prepareStatement(sql);
			pstm.setString(1, shop.getShopNo());
			ResultSet rs = pstm.executeQuery();
			if(rs.next()) {
				
				shop.setShopNo(rs.getString("no"));
				shop.setShopName(rs.getString("name"));
				shop.setShopPrice(rs.getDouble("price"));
				shop.setShopNum(rs.getInt("num"));
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
