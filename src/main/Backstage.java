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
					System.out.println("��ӳɹ���");
				}
				else {
				System.out.println("���ʧ�ܣ�");
				}
				return true;
			}
		case "2":{
				removeGood() ;
				break;
			}
		case "3" :{
			if(updateGood()) {
				System.out.println("�޸ĳɹ���");
			}else {
				System.out.println("�޸�ʧ�ܣ�");
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
	
	//�����Ʒ
	boolean AddGoods() {
		
		Shop shop = new Shop();
		Scanner cin = new Scanner(System.in);
		System.out.print("����Ҫ��ӵ���Ʒ���");
		shop.setShopNo(cin.nextLine());
		if(new NetGoods().isExist(shop.getShopNo())) {
			System.out.println("�ñ���Ѵ��ڣ�");
			return false;
		}
		System.out.print("����Ҫ��ӵ���Ʒ����");
		shop.setShopName(cin.nextLine());
		System.out.print("����Ҫ��ӵ���Ʒ�۸�");
		shop.setShopPrice(cin.nextDouble());
		System.out.print("����Ҫ��ӵ���Ʒ���� ");
		shop.setShopNum(cin.nextInt());
		if(new NetGoods().addGood(shop)) {
			return true;
		}
		return false;
	}
	//ɾ����Ʒ
	boolean removeGood () {
		Scanner cin = new Scanner(System.in);
		Shop shop = new Shop();
		NetGoods remove = new NetGoods();
		System.out.print("������Ҫɾ������Ʒ���:");
		shop.setShopNo(cin.nextLine());
		if(remove.isExist(shop.getShopNo())) {
			System.out.println("����Ҫɾ������Ʒ���£�");
			new ShopInfo().getShopInfo(shop);
			System.out.println(shop.getShopNo()+"��\t\t|"+shop.getShopName()+"\t\t|"+shop.getShopPrice()+"Ԫ\t\t|"+shop.getShopNum()+"��");
			System.out.print("ȷ��ɾ����?(Y/N):");
			String is = cin.nextLine();
			if(is.compareTo("Y")!=0&&is.compareTo("y")!=0) {
				System.out.println("����ȡ��");
				return false;
			}
			System.out.println("����ɾ��...");
			if(remove.removeGood(shop)) {
				System.out.println("ɾ���ɹ���");
				return true;
			}else {
				System.out.println("ɾ��ʧ�ܣ�");
			}
			
		}else {
			System.out.println("��Ʒ�����ڣ�");
		}
		return false;
		
	}
	//�޸���Ʒ
	boolean updateGood() {
		Scanner cin = new Scanner(System.in);
		Shop shop = new Shop();
		System.out.println("����Ҫ�޸ĵ���Ʒ��ţ�");
		shop.setShopNo(cin.nextLine());
		NetGoods update = new NetGoods();
		if(update.isExist(shop.getShopNo())) {
			System.out.print("����Ʒ��Ϣ����:");
			new ShopInfo().getShopInfo(shop);
			System.out.println(shop.getShopNo()+"��\t\t|"+shop.getShopName()+"\t\t|"+shop.getShopPrice()+"Ԫ\t\t|"+shop.getShopNum()+"��");
			System.out.println("����Ҫ�޸ĵ���Ʒ���ƣ�");
			shop.setShopName(cin.nextLine());
			System.out.println("����Ҫ�޸ĵ���Ʒ�۸�");
			shop.setShopPrice(cin.nextDouble());
			System.out.println("����Ҫ�޸ĵ���Ʒ������");
			shop.setShopNum(cin.nextInt());
			if(update.updateGood(shop)) {
				return true;
			}
		}
		return false;
	}
	//ɾ���û�
	boolean removeUser () {
		Scanner cin = new Scanner(System.in);
		User user = new User();
		SetUser remove = new SetUser();
		System.out.print("������Ҫɾ�����û��˺�:");
		user.setUserNo(cin.nextLine());
		if(remove.isExist(user.getUserNo())) {
			System.out.println("����Ҫɾ�����û����£�");
			new UserInfo().getUserInfo(user);
			System.out.println(user.getUserNo()+"\t\t|"+user.getUserName()+"\t\t|"+user.getUserPassword()+"\t\t|"+user.getUserGold()+"Ԫ");
			/*System.out.print("ȷ��ɾ����?(Y/N):");
			String is = cin.nextLine();
			System.out.println(is);
			if(is!="Y"&&is!="y") {
				System.out.println("����ȡ��");
				return false;
			}*/
			System.out.println("����ɾ��...");
			if(remove.removeUser(user)) {
				System.out.println("ɾ���ɹ���");
				return true;
			}else {
				System.out.println("ɾ��ʧ�ܣ�");
			}
			
		}else {
			System.out.println("�û������ڣ�");
		}
		return false;
		
	}
	
}
