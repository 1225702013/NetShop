package ui;

import DB.SetUser;
import lib.User;
import lib.UserInfo;
import main.SignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectUI extends JFrame {

    public SelectUI(){

        super("青软有界面的网上商城");
        setBounds(500,150,400,200);

        Box main = Box.createVerticalBox();
        JButton LoginIn = new JButton("登录");
        JButton LoginUp = new JButton("注册");
        JButton admin = new JButton("管理员登录");
        JLabel no = new JLabel("账号");
        JLabel pwd = new JLabel("密码");
        JTextField no_text = new JTextField(10);
        no_text.setBounds(69, 57, 30, 15);
        JTextField pwd_text = new JTextField(10);

         Box b1 = Box.createHorizontalBox();
         b1.add(Box.createVerticalStrut(20));
         b1.add(Box.createHorizontalStrut(20));
         b1.add(no);
         b1.add(no_text);
         b1.add(new JPanel());

        Box b2 = Box.createHorizontalBox();
        b2.add(Box.createVerticalStrut(20));
        b2.add(Box.createHorizontalStrut(20));
        b2.add(pwd);
        b2.add(pwd_text);
        b2.add(new JPanel());

        Box b3 = Box.createHorizontalBox();
        b3.add(Box.createVerticalStrut(20));
        b3.add(Box.createHorizontalStrut(20));
        b3.add(LoginIn);
        b3.add(new JPanel());
        b3.add(LoginUp);
        b3.add(new JPanel());

        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                new UI();

            }
        });

        LoginIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                if(new SetUser().isExist(no_text.getText())){
                    user.setUserNo(no_text.getText());
                    new UserInfo().getUserInfo(user);
                    if(pwd_text.getText().equals(user.getUserPassword())){
                        setVisible(false);
                        new ShopUI(user);
                    }
                    else {
                        JOptionPane.showMessageDialog(main,"密码错误","错误",JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(main,"账号不存在！","错误",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        LoginUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingUpDialog();
            }
        });
        main.add(admin);
        main.add(new JPanel());
        main.add(b1);
        main.add(new JPanel());
        main.add(b2);
        main.add(new JPanel());
        main.add(b3);
        add(main);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    void SingUpDialog(){

        JDialog addFrame = new JDialog(this,"注册",true);
        //addFrame.setTitle("添加商品");
        addFrame.setSize(400, 150);
        // 设置对话框大小不可改变
        addFrame.setResizable(false);
        addFrame.setLocationRelativeTo(null);

        Box b1 = Box.createHorizontalBox();
        b1.add(Box.createHorizontalStrut(20));
        Box b2 = Box.createHorizontalBox();
        Box b3 = Box.createVerticalBox();


        JLabel no = new JLabel("ID");
        JTextField no_text = new JTextField(10);

        JLabel name = new JLabel("昵称");
        JTextField name_text = new JTextField(10);

        b1.add(no);
        b1.add(no_text);
        b1.add(name);
        b1.add(name_text);

        JLabel price = new JLabel("密码");
        JTextField price_text = new JTextField(10);
        JLabel num = new JLabel("确认密码");
        JTextField num_text = new JTextField(10);

        b2.add(Box.createHorizontalStrut(20));
        b2.add(price);
        b2.add(price_text);
        b2.add(num);
        b2.add(num_text);

        JButton yes = new JButton("确定");

        b3.add(Box.createHorizontalStrut(20));
        b3.add(Box.createVerticalStrut(30));
        b3.add(b1);
        b3.add(b2);
        b3.add(new JPanel());
        b3.add(yes);
        addFrame.add(b3);

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SetUser up = new SetUser();
                if(up.isExist(no_text.getText())){
                    JOptionPane.showMessageDialog(addFrame,"账号已被占用！","错误",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if(price_text.getText().equals(num_text.getText())){

                        User user = new User();
                        user.setUserNo(no_text.getText());
                        user.setUserName(name_text.getText());
                        user.setUserPassword(price_text.getText());
                        user.setUserGold(0);
                        new SetUser().addUser(user);
                        JOptionPane.showMessageDialog(addFrame,"欢迎"+name_text.getText(),"注册成功",JOptionPane.PLAIN_MESSAGE);
                        setVisible(false);

                    }else{
                        JOptionPane.showMessageDialog(addFrame,"两次密码不一致","错误",JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });


        addFrame.setVisible(true);
    }
}
