package main;



import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import DB.LinkDb;
import DB.NetGoods;
import DB.SetUser;
import lib.Admin;
import lib.Shop;
import lib.ShopInfo;
import lib.User;
import lib.UserInfo;
public class Backstage {
	
	public Backstage() {
		// TODO Auto-generated constructor stub
	}


	public boolean BackstageMain(String select,Admin admin){
		
		switch(select) {
		case "1":{
				if(AddGoods()) {
					System.out.println("添加成功！");
				}
				else {
				System.out.println("添加失败！");
				}
				return true;
			}
		case "2":{
				removeGood() ;
				break;
			}
		case "3" :{
			if(updateGood()) {
				System.out.println("修改成功！");
			}else {
				System.out.println("修改失败！");
			}
			break;
			}
		case "4":new NetGoods().selectGoods();break;
		case "5":new SetUser().selectUser();break;
		case "6":removeUser();break;
		case "7":return false;
		}
		return true;
	}
	
	//添加商品
	boolean AddGoods() {
		
		Shop shop = new Shop();
		Scanner cin = new Scanner(System.in);
		System.out.print("输入要添加的商品编号");
		shop.setShopNo(cin.nextLine());
		if(new NetGoods().isExist(shop.getShopNo())) {
			System.out.println("该编号已存在！");
			return false;
		}
		System.out.print("输入要添加的商品名称");
		shop.setShopName(cin.nextLine());
		System.out.print("输入要添加的商品价格");
		shop.setShopPrice(cin.nextDouble());
		System.out.print("输入要添加的商品数量 ");
		shop.setShopNum(cin.nextInt());
		if(new NetGoods().addGood(shop)) {
			return true;
		}
		return false;
	}
	//删除商品
	boolean removeGood () {
		Scanner cin = new Scanner(System.in);
		Shop shop = new Shop();
		NetGoods remove = new NetGoods();
		System.out.print("输入想要删除的商品编号:");
		shop.setShopNo(cin.nextLine());
		if(remove.isExist(shop.getShopNo())) {
			System.out.println("您将要删除的商品如下：");
			new ShopInfo().getShopInfo(shop);
			System.out.println(shop.getShopNo()+"号\t\t|"+shop.getShopName()+"\t\t|"+shop.getShopPrice()+"元\t\t|"+shop.getShopNum()+"份");
			System.out.print("确认删除吗?(Y/N):");
			String is = cin.nextLine();
			if(is.compareTo("Y")!=0&&is.compareTo("y")!=0) {
				System.out.println("操作取消");
				return false;
			}
			System.out.println("正在删除...");
			if(remove.removeGood(shop)) {
				System.out.println("删除成功！");
				return true;
			}else {
				System.out.println("删除失败！");
			}
			
		}else {
			System.out.println("商品不存在！");
		}
		return false;
		
	}
	//修改商品
	boolean updateGood() {
		Scanner cin = new Scanner(System.in);
		Shop shop = new Shop();
		System.out.println("输入要修改的商品编号：");
		shop.setShopNo(cin.nextLine());
		NetGoods update = new NetGoods();
		if(update.isExist(shop.getShopNo())) {
			System.out.print("该商品信息如下:");
			new ShopInfo().getShopInfo(shop);
			System.out.println(shop.getShopNo()+"号\t\t|"+shop.getShopName()+"\t\t|"+shop.getShopPrice()+"元\t\t|"+shop.getShopNum()+"份");
			System.out.println("输入要修改的商品名称：");
			shop.setShopName(cin.nextLine());
			System.out.println("输入要修改的商品价格：");
			shop.setShopPrice(cin.nextDouble());
			System.out.println("输入要修改的商品数量：");
			shop.setShopNum(cin.nextInt());
			if(update.updateGood(shop)) {
				return true;
			}
		}
		return false;
	}
	//删除用户
	boolean removeUser () {
		Scanner cin = new Scanner(System.in);
		User user = new User();
		SetUser remove = new SetUser();
		System.out.print("输入想要删除的用户账号:");
		user.setUserNo(cin.nextLine());
		if(remove.isExist(user.getUserNo())) {
			System.out.println("您将要删除的用户如下：");
			new UserInfo().getUserInfo(user);
			System.out.println(user.getUserNo()+"\t\t|"+user.getUserName()+"\t\t|"+user.getUserPassword()+"\t\t|"+user.getUserGold()+"元");
			/*System.out.print("确认删除吗?(Y/N):");
			String is = cin.nextLine();
			System.out.println(is);
			if(is!="Y"&&is!="y") {
				System.out.println("操作取消");
				return false;
			}*/
			System.out.println("正在删除...");
			if(remove.removeUser(user)) {
				System.out.println("删除成功！");
				return true;
			}else {
				System.out.println("删除失败！");
			}
			
		}else {
			System.out.println("用户不存在！");
		}
		return false;
		
	}
	
}
