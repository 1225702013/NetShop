package DB;

import java.sql.*;

public class LinkDb  {
	 

public static Connection getConn(){

           // 驱动程序名
           String driver = "com.mysql.jdbc.Driver";

           // URL指向要访问的数据库名scutcs
           String url = "jdbc:mysql://127.0.0.1:3306/shop";

           // MySQL配置时的用户名
           String user = "root"; 

           // MySQL配置时的密码
           String password = "123456";
           Connection conn = null;
           try { 
            // 加载驱动程序
            Class.forName(driver);

            // 连续数据库
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