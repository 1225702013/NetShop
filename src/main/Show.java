package main;

public class Show {
	
	void showMain(){
		System.out.println("\n\n\n*************************************");
		System.out.println("*        ��ӭʹ������û�н���������̳�                     *");
		System.out.println("*        һ��ע��                                                           *");
		System.out.println("*        ������¼                                                           *");
		System.out.println("*        ��������Ա��¼                                                 *");
		System.out.println("*        �ġ��˳�                                                           *");
		System.out.println("*************************************");
		System.out.println(">>>��ѡ��:");
		
		
	}
	boolean SelectMenu(String select) {
		
		switch(select) {
		
		case "һ":
		case "1":{
			new SignUp();
			break;
		}
			
		case "2":
		case "��":{
			new SignIn();
			break;
		}
			
		case "3":
		case "��":{
			//����Ա��¼
			new AdminIn();
			break;
		}
			
		case "4":
		case "��":{
			return false;
		}
			
			
		
		}
		return true;
		
	}
	

}
