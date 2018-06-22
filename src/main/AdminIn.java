package main;

import java.util.Scanner;

import lib.Admin;
import java.sql.*;

import DB.LinkDb;
public class AdminIn {
	Scanner cin = new Scanner(System.in);
	Admin admin = new Admin();
	
	AdminIn(){
		
		System.out.println("欢迎登陆后台管理系统");
		System.out.print("请输入管理员账号:");
		admin.setAdminNo(cin.next());
		System.out.print("请输入管理员密码:");
		admin.setAdminPassword(cin.next());
		
		//查询用户账号是否存在
		if(SearchAdminNo()) {
			NetShopMenu shop = new NetShopMenu()	;
			String select;
			do {
				System.out.println("欢迎"+admin.getAdminName()+"登陆!");
				this.showUserMenu();
				select = cin.next();	
				
			}while(new Backstage().BackstageMain(select, admin));
		}
		else{
			System.out.println("用户不存在");
		}
		
	}
	
	void showUserMenu() {
		System.out.println("\n\n\n*************************************");
		System.out.println("*        一、添加商品                                                   *");
		System.out.println("*        二、删除商品                                                   *");
		System.out.println("*        三、修改商品                                                    *");
		System.out.println("*        四、查看商品列表                                             *");
		System.out.println("*        五、查看用户列表                                             *");
		System.out.println("*        六、删除用户                                                    *");
		System.out.println("*        七、退出                                                           *");
		System.out.println("*************************************");
		System.out.print(">>>请选择:");
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
						System.out.println("密码错误！");
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
