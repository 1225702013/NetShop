package main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import DB.*;
import lib.User;
public class SignUp {
	Scanner cin = new Scanner(System.in);
	boolean LoginUp(User user){
		
		System.out.println("��ӭ���û�ע��");
		
		//ע��
		SetUser add =new SetUser();
		//User user = new User();
		//user.setUserNo(this.setUserNo()); ;
		//user.setUserPassword( this.setPassword(user));
		//user.setUserName(this.setUserName());
		//user.setUserGold(0);
		//System.out.println(user.getUserNo());
		//��ʼд�����ݿ�
		if(add.addUser(user)) {
			return true;
		}
		return false;
		
	}
	//�����û��˺�
	String setUserNo() {		
		while(true) {
			System.out.println("�������û���(ֻ������������ĸ�����):");
			String no = cin.nextLine();
			boolean isDigit = true;
			if(no.length()<6==true) {
				System.out.println("�û���̫�̣�");
				continue;
			}
			for(int  tmp = 0; tmp <no.length();tmp++) {
				if(Character.isDigit(no.charAt(tmp))==false&&Character.isLetter(no.charAt(tmp))==false) {
					isDigit = false;
					
				}
			}
			if(isDigit) {
				SetUser signup = new SetUser();
				if(signup.isExist(no)) {
					System.out.println("���û����Ѿ���ռ��!");
				}
				else
					return no;
			}
			else {
				System.out.println("�û��������Ƿ��ַ���");
			}
		}
	}
	
	String setPassword(User user) {
		int flag = 1;
		while(flag==1) {
			System.out.println("�������û�����:");
			String passwordOne = new String();
			passwordOne = cin.next();
			System.out.println("��ȷ���û�����:");
			String passwordTwo = new String();
			passwordTwo = cin.next();
			if(passwordTwo.compareTo(passwordOne)!=0) {
				System.out.println("�����������벻һ�£�");
			}
			else {
				user.setUserPassword(passwordOne);;
				return user.getUserPassword();
			}
		}
		return null;
	}
	
	String  setUserName() {
		System.out.println("���Լ�ȡһ���ǳ�(���س�����):");
		String user_name = cin.next();
		if(user_name.length()==0)
			 user_name = new String("δ����");
		return user_name;
	}
	
}
