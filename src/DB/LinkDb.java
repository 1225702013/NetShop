package DB;

import java.sql.*;

public class LinkDb  {
	 

public static Connection getConn(){

           // ����������
           String driver = "com.mysql.jdbc.Driver";

           // URLָ��Ҫ���ʵ����ݿ���scutcs
           String url = "jdbc:mysql://127.0.0.1:3306/shop";

           // MySQL����ʱ���û���
           String user = "root"; 

           // MySQL����ʱ������
           String password = "123456";
           Connection conn = null;
           try { 
            // ������������
            Class.forName(driver);

            // �������ݿ�
            conn = DriverManager.getConnection(url, user, password);

            if(!conn.isClosed()) ;
             //System.out.println("Succeeded connecting to the Database!");

            //conn.close();

           } catch(ClassNotFoundException e) {


            System.out.println("Sorry,can`t find the Driver!"); 
            e.printStackTrace();


           } catch(SQLException e) {


            e.printStackTrace();


           } catch(Exception e) {


            e.printStackTrace();


           }
           return conn;
	}

}