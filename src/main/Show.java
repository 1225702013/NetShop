package main;

public class Show {
	
	void showMain(){
		System.out.println("\n\n\n*************************************");
		System.out.println("*        欢迎使用青软没有界面的网上商城                     *");
		System.out.println("*        一、注册                                                           *");
		System.out.println("*        二、登录                                                           *");
		System.out.println("*        三、管理员登录                                                 *");
		System.out.println("*        四、退出                                                           *");
		System.out.println("*************************************");
		System.out.println(">>>请选择:");
		
		
	}
	boolean SelectMenu(String select) {
		
		switch(select) {
		
		case "一":
		case "1":{
			new SignUp();
			break;
		}
			
		case "2":
		case "二":{
			new SignIn();
			break;
		}
			
		case "3":
		case "三":{
			//管理员登录
			new AdminIn();
			break;
		}
			
		case "4":
		case "四":{
			return false;
		}
			
			
		
		}
		return true;
		
	}
	

}
