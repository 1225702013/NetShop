package main;

import java.util.Scanner;

import lib.Shop;
import lib.User;
import java.sql.*;

import DB.LinkDb;
public class SignIn {
	Scanner cin = new Scanner(System.in);
	User user = new User();
	
	SignIn(){
		
		System.out.println("欢迎登陆网上商城");
		System.out.print("请输入用户账号:");
		user.setUserNo(cin.next());
		System.out.print("请输入用户密码:");
		user.setUserPassword(cin.next());
		
		//查询用户账号是否存在
		if(SearchUserNo()) {
			System.out.println("欢迎用户"+user.getUserName()+"!");
			NetShopMenu shop = new NetShopMenu()	;
			String select;
			do {
				this.showUserMenu();
				select = cin.next();	
				
			}while(shop.shopMain(select, user));
		}
		else{
			System.out.println("用户不存在");
		}
		
	}
	
	void showUserMenu() {
		System.out.println("\n\n\n*************************************");
		System.out.println("*        一、查看我购买的商品                                      *");
		System.out.println("*        二、查看网上商城                                             *");
		System.out.println("*        三、充值                                                           *");
		System.out.println("*        四、返回登录前界面                                          *");
		System.out.println("*************************************");
		System.out.print(">>>请选择:");
	}
	
	
	
	boolean SearchUserNo() {
		
		LinkDb search = new LinkDb();
		String sql = "select * from user where no =?";
		Connection db = search.getConn();
		
		try {
			
			PreparedStatement Pmt = (PreparedStatement) db.prepareStatement(sql);
			Pmt.setString(1, user.getUserNo());
			ResultSet rs = Pmt.executeQuery();
			
            if(rs.next()) {
					String getPassword = new String("000");
					
					getPassword = rs.getString("password");
					if(getPassword.compareTo(user.getUserPassword())!=0) {
						System.out.println("密码错误！");
						return false;
					}
					else {
						user.setUserNo(rs.getString("no"));
						user.setUserName(rs.getString("name"));
						user.setUserGold(rs.getDouble("gold"));
						user.setUserPassword(rs.getString("password"));
						return true;
					}
					
				}
            Pmt.close();
            db.close();
          
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	

}
