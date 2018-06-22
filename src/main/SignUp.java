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
		
		System.out.println("欢迎新用户注册");
		
		//注册
		SetUser add =new SetUser();
		//User user = new User();
		//user.setUserNo(this.setUserNo()); ;
		//user.setUserPassword( this.setPassword(user));
		//user.setUserName(this.setUserName());
		//user.setUserGold(0);
		//System.out.println(user.getUserNo());
		//开始写入数据库
		if(add.addUser(user)) {
			return true;
		}
		return false;
		
	}
	//设置用户账号
	String setUserNo() {		
		while(true) {
			System.out.println("请输入用户名(只能是数字与字母的组合):");
			String no = cin.nextLine();
			boolean isDigit = true;
			if(no.length()<6==true) {
				System.out.println("用户名太短！");
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
					System.out.println("该用户名已经被占用!");
				}
				else
					return no;
			}
			else {
				System.out.println("用户名包含非法字符！");
			}
		}
	}
	
	String setPassword(User user) {
		int flag = 1;
		while(flag==1) {
			System.out.println("请输入用户密码:");
			String passwordOne = new String();
			passwordOne = cin.next();
			System.out.println("请确认用户密码:");
			String passwordTwo = new String();
			passwordTwo = cin.next();
			if(passwordTwo.compareTo(passwordOne)!=0) {
				System.out.println("两次密码输入不一致！");
			}
			else {
				user.setUserPassword(passwordOne);;
				return user.getUserPassword();
			}
		}
		return null;
	}
	
	String  setUserName() {
		System.out.println("给自己取一个昵称(按回车跳过):");
		String user_name = cin.next();
		if(user_name.length()==0)
			 user_name = new String("未命名");
		return user_name;
	}
	
}
