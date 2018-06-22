package DB;

import java.awt.desktop.SystemSleepEvent;
import java.sql.*;
import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;

import lib.User;

public class SetUser {

	//添加用户
	public boolean addUser(User user){
		String sql="insert into user (no,name,password,gold) values (?,?,?,?)";
		LinkDb addsql = new LinkDb();
		Connection db = addsql.getConn();
		try {
			PreparedStatement Pmt = (PreparedStatement) db.prepareStatement(sql);
			Pmt.setString(1, user.getUserNo());
			Pmt.setString(2, user.getUserName());
			Pmt.setString(3, user.getUserPassword());
			Pmt.setDouble(4, user.getUserGold());
			Pmt.executeUpdate();
			Pmt.close();
			
			db.close();
			return true;
			
			 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	//更新用户
	public boolean updateUser(User user) {
		
		String sql = "UPDATE user SET name = ?,password = ?, gold =?  WHERE no = ?";
		LinkDb add = new LinkDb();
		Connection db = add.getConn();
		try {
			PreparedStatement pstm = (PreparedStatement) db.prepareStatement(sql);
			pstm.setString(1, user.getUserName());
			pstm.setString(2, user.getUserPassword());
			pstm.setDouble(3, user.getUserGold());
			pstm.setString(4, user.getUserNo());
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
	//删除用户
	public boolean removeUser(User user) {
		
		String sql = "DELETE FROM user WHERE no= ?";
		LinkDb add = new LinkDb();
		Connection db = add.getConn();
		try {
			PreparedStatement pstm = (PreparedStatement) db.prepareStatement(sql);
			pstm.setString(1, user.getUserNo());
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
	//判断用户状态
	public boolean isExist(String user) {
		LinkDb search = new LinkDb();
		String sql = "select * from user where no =?";
		Connection db = search.getConn();
		try {
			
			PreparedStatement Pmt = (PreparedStatement) db.prepareStatement(sql);
			Pmt.setString(1, user);
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
	//查看全部用户
	public boolean selectUser(){
		
		String sql = "select * from user";
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
			System.out.println("*用户ID********用户名称****密码******余额************");
			while(rs.next()) {
				System.out.println("*"+rs.getString("no")+"\t\t\t"+rs.getString("name")+"\t"+rs.getString("password")+"\t"+rs.getDouble("gold"));
			}
			System.out.println("*****************************************");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		return true;
		
	}

	//查看全部用户
	public Vector selectUser(int i){


		Vector vData = new Vector();
		String sql = "select * from user";
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
				vRow.add(rs.getString("password"));
				vRow.add(Double.toString(rs.getDouble("gold")));
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
