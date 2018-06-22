package main;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import DB.LinkDb;
import DB.NetGoods;
import DB.SetRecord;
import DB.SetUser;
import lib.Record;
import lib.Shop;
import lib.ShopInfo;
import lib.User;

public class NetShopMenu {
	
	public NetShopMenu() {
		// TODO Auto-generated constructor stub
	}


	public boolean shopMain(String select,User user){
		
		switch(select) {
		case "1":{
				if(getUserGoods(user.getUserNo())) {
					System.out.println("这是全部商品");
				}
				else {
				System.out.println("该用户没有购买过商品！");
				}
				return true;
			}
		case "2":{
				NetGoods good = new NetGoods();
				if(good.selectGoods()) {
					System.out.println("选择您要购买的商品(输入商品编号购买):");
					Scanner cin = new Scanner(System.in);
					String usershopping = cin.nextLine();
					System.out.println("选择您要购买的数量:");
					int shopnum = cin.nextInt();
					if(shopping(usershopping, shopnum, user)) {
						System.out.println("购买成功！余额为"+user.getUserGold());
					}else {
						System.out.println("购买失败！");
					}
				}else {
					System.out.println("商城还没有商品，请联系管理员添加！");
				}
				break;
			}
		case "3" :{
			if(setGold(user)) {
				System.out.println("充值成功，当前余额："+user.getUserGold());
			}else {
				System.out.println("充值失败！");
			}
			break;
			}
		case "4":return false;
		
		}
		return true;
	}
	
	//获取用户购买的商品
	boolean getUserGoods(String user) {
		
		String sql = "select * from shopping where custom_no = ?";
		LinkDb show = new LinkDb();
		Connection db = show.getConn();
		try {
			PreparedStatement pstm = (PreparedStatement)db.prepareStatement(sql);
			pstm.setString(1, user);
			ResultSet rs = pstm.executeQuery();
			if(!rs.next()) {
				return false;
			}
			rs.previous();
			System.out.println("***************************************");
			System.out.println("*用户账号****商品名称****购买日期*************");
			while(rs.next()) {
				System.out.println("*"+rs.getString("custom_no")+"\t"+rs.getString("good_name")+"\t"+rs.getString("date")+"*");
			}
			System.out.println("***************************************");
			pstm.close();
			db.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	//购买商品
	boolean shopping (String usershopping,int shopnum,User user) {
		Shop shop = new Shop();
		shop.setShopNo(usershopping);

		
		ShopInfo shop_info = new ShopInfo();
		if(shop_info.getShopInfo(shop)) {
			if(user.getUserGold()-(shop.getShopPrice()*shopnum)>=0) {
				
				if(shop.getShopNum()>=shopnum) {
					System.out.println("正在购买....");
					//扣除商品与金额
					user.setUserGold(user.getUserGold()-(shop.getShopPrice()*shopnum));
					shop.setShopNum(shop.getShopNum()-shopnum);
					//添加交易记录
					Record record = new Record();
					record.setRecordNo(user.getUserNo());
					record.setGoodName(shop.getShopName());
					record.setGoodNum(shopnum);
					record.setGoodGold(shop.getShopPrice()*shopnum);
					//更新数据库
					 NetGoods update = new NetGoods();
					 update.updateGood(shop);
					 SetUser set = new SetUser();
					 set.updateUser(user);
					 SetRecord setrecord = new SetRecord();
					 setrecord.addRecord(record);
					 return true;
				}else {
					System.out.println("商品数量不足！");
				}
				
			}else {
				System.out.println("您的余额不足！当前余额：\t"+user.getUserGold());
			}
		}
		return false;
	}
	//充值
	boolean setGold(User user) {
		
		System.out.println("您的当前余额："+user.getUserGold());
		System.out.print("请输入您要充值的额度:");
		Scanner cin = new Scanner(System.in);
		user.setUserGold(user.getUserGold()+cin.nextDouble());
		SetUser setgold = new SetUser();
		if(setgold.updateUser(user)) {
			
			return true;
		}else {
			
			return false;
		}
		
		
	}
	
	
}
