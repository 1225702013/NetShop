package main;

import java.util.Scanner;

import lib.Admin;
import java.sql.*;

import DB.LinkDb;
public class AdminIn {
	Scanner cin = new Scanner(System.in);
	Admin admin = new Admin();
	
	AdminIn(){
		
		System.out.println("��ӭ��½��̨����ϵͳ");
		System.out.print("���������Ա�˺�:");
		admin.setAdminNo(cin.next());
		System.out.print("���������Ա����:");
		admin.setAdminPassword(cin.next());
		
		//��ѯ�û��˺��Ƿ����
		if(SearchAdminNo()) {
			NetShopMenu shop = new NetShopMenu()	;
			String select;
			do {
				System.out.println("��ӭ"+admin.getAdminName()+"��½!");
				this.showUserMenu();
				select = cin.next();	
				
			}while(new Backstage().BackstageMain(select, admin));
		}
		else{
			System.out.println("�û�������");
		}
		
	}
	
	void showUserMenu() {
		System.out.println("\n\n\n*************************************");
		System.out.println("*        һ�������Ʒ                                                   *");
		System.out.println("*        ����ɾ����Ʒ                                                   *");
		System.out.println("*        �����޸���Ʒ                                                    *");
		System.out.println("*        �ġ��鿴��Ʒ�б�                                             *");
		System.out.println("*        �塢�鿴�û��б�                                             *");
		System.out.println("*        ����ɾ���û�                                                    *");
		System.out.println("*        �ߡ��˳�                                                           *");
		System.out.println("*************************************");
		System.out.print(">>>��ѡ��:");
	}
	
	
	
	boolean SearchAdminNo() {
		
		LinkDb search = new LinkDb();
		String sql = "select * from admin_table where no =?";
		Connection db = search.getConn();
		
		try {
			
			PreparedStatement Pmt = (PreparedStatement) db.prepareStatement(sql);
			Pmt.setString(1, admin.getAdminNo());
			ResultSet rs = Pmt.executeQuery();
			
            if(rs.next()) {
					String getPassword = new String("000");
					
					getPassword = rs.getString("password");
					if(getPassword.compareTo(admin.getAdminPassword())!=0) {
						System.out.println("�������");
						return false;
					}
					else {
						admin.setAdminNo(rs.getString("no"));
						admin.setAdminName(rs.getString("name"));
						admin.setAdminPassword(rs.getString("password"));
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
