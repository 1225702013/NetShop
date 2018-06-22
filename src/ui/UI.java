package ui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import DB.LinkDb;
import lib.Admin;

public class UI extends JFrame {
	
	
	
	JTextField te_no;
	 JTextField te_pwd;
	 JPanel contentPane;
	 Frame[]  MainFrame;
	public UI(){
		super("后台管理系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置用户在此窗体上发起 "close" 时默认执行的操作。  
        setBounds(500, 150, 450, 250);  
        contentPane = new JPanel(); //指定容器  
        setContentPane(contentPane);//设置 contentPane 属性  
        contentPane.setLayout(null);  
                  
        MainFrame = getFrames();
        
        
        JButton SignIn =new JButton("登录");     //按钮定义  
        SignIn.setBounds(169, 153, 94, 23);
         contentPane.add(SignIn);

        SignIn.addActionListener(new SignInActionListener());
        
          
         JLabel lbr_no = new JLabel("账号");  
         lbr_no.setBounds(69, 57, 30, 15);    //setBounds(int x,int y,int width,int height)  
        contentPane.add(lbr_no);  
          
        te_no = new JTextField();  
        te_no.setBounds(115, 54, 242, 21);  
        contentPane.add(te_no);  
        te_no.setColumns(10);    // 设置此 TextField 中的列数，然后验证布局。  

        JLabel lbr_pwd = new JLabel("密码");  
        lbr_pwd.setBounds(69, 107, 30, 15);    //setBounds(int x,int y,int width,int height)  
        contentPane.add(lbr_pwd);  
          
        te_pwd = new JTextField();  
        te_pwd.setBounds(115, 104, 242, 21);  
        contentPane.add(te_pwd);  
        te_pwd
        .setColumns(10); // 设置此 TextField 中的列数，然后验证布局。  
          
        this.setVisible(true);
	}
	
	
	class     SignInActionListener   implements ActionListener{

		

		@Override
		public void actionPerformed(ActionEvent e) {
			String no = te_no.getText();
			String pwd = te_pwd.getText();
			Admin admin = new Admin();
			admin.setAdminNo(no);
			admin.setAdminPassword(pwd);
			
			if(SearchAdminNo(admin)) {
				
				JOptionPane.showMessageDialog(rootPane, admin.getAdminName()+"成功登录", "提示", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				new MainMenuUI(admin);
				
			}else {
				JOptionPane.showMessageDialog(rootPane, "密码错误！", "错误提示", JOptionPane.INFORMATION_MESSAGE);
				
			}
			
		}
		
		boolean SearchAdminNo(Admin admin) {
			
			LinkDb search = new LinkDb();
			String sql = "select * from admin_table where no =?";
			Connection db = search.getConn();
			
			try {
				
				PreparedStatement Pmt = (PreparedStatement) db.prepareStatement(sql);
				Pmt.setString(1, admin.getAdminNo());
				ResultSet rs = Pmt.executeQuery();
				
	            if(rs.next()) {
						String getPassword = new String("000");
						
						getPassword = rs.getString("password");
						if(getPassword.compareTo(admin.getAdminPassword())!=0) {
							
							
							
							return false;
						}
						else {
							admin.setAdminNo(rs.getString("no"));
							admin.setAdminName(rs.getString("name"));
							admin.setAdminPassword(rs.getString("password"));
							return true;
						}
						
					}
	            Pmt.close();
	            db.close();
	          
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;
		}
		
		
	}
	
	
}
