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
		
		System.out.println("��ӭ��½�����̳�");
		System.out.print("�������û��˺�:");
		user.setUserNo(cin.next());
		System.out.print("�������û�����:");
		user.setUserPassword(cin.next());
		
		//��ѯ�û��˺��Ƿ����
		if(SearchUserNo()) {
			System.out.println("��ӭ�û�"+user.getUserName()+"!");
			NetShopMenu shop = new NetShopMenu()	;
			String select;
			do {
				this.showUserMenu();
				select = cin.next();	
				
			}while(shop.shopMain(select, user));
		}
		else{
			System.out.println("�û�������");
		}
		
	}
	
	void showUserMenu() {
		System.out.println("\n\n\n*************************************");
		System.out.println("*        һ���鿴�ҹ������Ʒ                                      *");
		System.out.println("*        �����鿴�����̳�                                             *");
		System.out.println("*        ������ֵ                                                           *");
		System.out.println("*        �ġ����ص�¼ǰ����                                          *");
		System.out.println("*************************************");
		System.out.print(">>>��ѡ��:");
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
						System.out.println("�������");
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
