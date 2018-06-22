package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

import lib.Shop;



public class NetGoods {
	int DELETE_ERRO = 0;
	int ADD_ERRO = -1;
	//查看全部货物
	public boolean selectGoods(){
		
		String sql = "select * from good";
		LinkDb show = new LinkDb();
		Connection db = show.getConn();
		try {
			PreparedStatement pstm = (PreparedStatement)db.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if(!rs.next()) {
				return false;
			}
			rs.previous();
			System.out.println("*****************************************");
			System.out.println("*商品ID****商品名称****价格****商品数量*********");
			while(rs.next()) {
				System.out.println("*"+rs.getString("no")+"\t"+rs.getString("name")+"\t"+rs.getDouble("price")+"元\t\t"+rs.getInt("num")+"\t\t    *");
			}
			System.out.println("*****************************************");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		return true;
		
	}
	//添加货物
	public boolean addGood(Shop shop){
		
		
		String sql = "insert into good (no,name,price,num) values (?,?,?,?)";
		LinkDb add = new LinkDb();
		Connection db = add.getConn();
		try {
			PreparedStatement pstm = (PreparedStatement)db.prepareStatement(sql);
			pstm.setString(1, shop.getShopNo());
			pstm.setString(2, shop.getShopName());
			pstm.setDouble(3, shop.getShopPrice());
			pstm.setInt(4, shop.getShopNum());
			pstm.executeUpdate();
			pstm.close();
			db.close();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//更新货物
	public boolean updateGood(Shop shop) {

		String sql = "UPDATE good SET name = ?, price =? , num = ? WHERE no = ?";
		LinkDb add = new LinkDb();
		Connection db = add.getConn();
		try {
			PreparedStatement pstm = db.prepareStatement(sql);
			pstm.setString(1, shop.getShopName());
			pstm.setDouble(2, shop.getShopPrice());
			pstm.setInt(3, shop.getShopNum());
			pstm.setString(4, shop.getShopNo());
			pstm.executeUpdate();
			pstm.close();
			db.close();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	//删除货物
	public boolean removeGood(Shop shop) {
		
		String sql = "DELETE FROM good WHERE no= ?";
		LinkDb add = new LinkDb();
		Connection db = add.getConn();
		try {
			PreparedStatement pstm = db.prepareStatement(sql);
			pstm.setString(1, shop.getShopNo());
			pstm.executeUpdate();
			pstm.close();
			db.close();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	//判断货物状态
	public boolean isExist(String shop) {
		LinkDb search = new LinkDb();
		String sql = "select * from good where no =?";
		Connection db = search.getConn();
		try {
			
			PreparedStatement Pmt = (PreparedStatement) db.prepareStatement(sql);
			Pmt.setString(1, shop);
			ResultSet rs = Pmt.executeQuery();
			
            if(rs.next()) {
            		
            	return true;
            	
				}            	
          
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return false;
	}

	//查看全部商品
	public Vector selectGoods(int i){


		Vector vData = new Vector();
		String sql = "select * from good";
		LinkDb show = new LinkDb();
		Connection db = show.getConn();
		try {
			PreparedStatement pstm = (PreparedStatement)db.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			if(!rs.next()) {
				return null;
			}
			rs.previous();

			while(rs.next()) {
				Vector vRow = new Vector();
				System.out.print(rs.getString("no"));
				vRow.add(rs.getString("no"));
				vRow.add(rs.getString("name"));
				vRow.add(rs.getDouble("price"));
				vRow.add(rs.getInt("num"));
				//System.out.println(vRow.toString());
				vData.add(vRow.clone());
				i++;
			}


		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		return vData;

	}

}
