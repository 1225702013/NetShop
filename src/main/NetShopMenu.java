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
					System.out.println("����ȫ����Ʒ");
				}
				else {
				System.out.println("���û�û�й������Ʒ��");
				}
				return true;
			}
		case "2":{
				NetGoods good = new NetGoods();
				if(good.selectGoods()) {
					System.out.println("ѡ����Ҫ�������Ʒ(������Ʒ��Ź���):");
					Scanner cin = new Scanner(System.in);
					String usershopping = cin.nextLine();
					System.out.println("ѡ����Ҫ���������:");
					int shopnum = cin.nextInt();
					if(shopping(usershopping, shopnum, user)) {
						System.out.println("����ɹ������Ϊ"+user.getUserGold());
					}else {
						System.out.println("����ʧ�ܣ�");
					}
				}else {
					System.out.println("�̳ǻ�û����Ʒ������ϵ����Ա��ӣ�");
				}
				break;
			}
		case "3" :{
			if(setGold(user)) {
				System.out.println("��ֵ�ɹ�����ǰ��"+user.getUserGold());
			}else {
				System.out.println("��ֵʧ�ܣ�");
			}
			break;
			}
		case "4":return false;
		
		}
		return true;
	}
	
	//��ȡ�û��������Ʒ
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
			System.out.println("*�û��˺�****��Ʒ����****��������*************");
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
	//������Ʒ
	boolean shopping (String usershopping,int shopnum,User user) {
		Shop shop = new Shop();
		shop.setShopNo(usershopping);

		
		ShopInfo shop_info = new ShopInfo();
		if(shop_info.getShopInfo(shop)) {
			if(user.getUserGold()-(shop.getShopPrice()*shopnum)>=0) {
				
				if(shop.getShopNum()>=shopnum) {
					System.out.println("���ڹ���....");
					//�۳���Ʒ����
					user.setUserGold(user.getUserGold()-(shop.getShopPrice()*shopnum));
					shop.setShopNum(shop.getShopNum()-shopnum);
					//��ӽ��׼�¼
					Record record = new Record();
					record.setRecordNo(user.getUserNo());
					record.setGoodName(shop.getShopName());
					record.setGoodNum(shopnum);
					record.setGoodGold(shop.getShopPrice()*shopnum);
					//�������ݿ�
					 NetGoods update = new NetGoods();
					 update.updateGood(shop);
					 SetUser set = new SetUser();
					 set.updateUser(user);
					 SetRecord setrecord = new SetRecord();
					 setrecord.addRecord(record);
					 return true;
				}else {
					System.out.println("��Ʒ�������㣡");
				}
				
			}else {
				System.out.println("�������㣡��ǰ��\t"+user.getUserGold());
			}
		}
		return false;
	}
	//��ֵ
	boolean setGold(User user) {
		
		System.out.println("���ĵ�ǰ��"+user.getUserGold());
		System.out.print("��������Ҫ��ֵ�Ķ��:");
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
