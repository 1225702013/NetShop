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

        super("�����н���������̳�");
        setBounds(500,150,400,200);

        Box main = Box.createVerticalBox();
        JButton LoginIn = new JButton("��¼");
        JButton LoginUp = new JButton("ע��");
        JButton admin = new JButton("����Ա��¼");
        JLabel no = new JLabel("�˺�");
        JLabel pwd = new JLabel("����");
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
                        JOptionPane.showMessageDialog(main,"�������","����",JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(main,"�˺Ų����ڣ�","����",JOptionPane.ERROR_MESSAGE);
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

        JDialog addFrame = new JDialog(this,"ע��",true);
        //addFrame.setTitle("�����Ʒ");
        addFrame.setSize(400, 150);
        // ���öԻ����С���ɸı�
        addFrame.setResizable(false);
        addFrame.setLocationRelativeTo(null);

        Box b1 = Box.createHorizontalBox();
        b1.add(Box.createHorizontalStrut(20));
        Box b2 = Box.createHorizontalBox();
        Box b3 = Box.createVerticalBox();


        JLabel no = new JLabel("ID");
        JTextField no_text = new JTextField(10);

        JLabel name = new JLabel("�ǳ�");
        JTextField name_text = new JTextField(10);

        b1.add(no);
        b1.add(no_text);
        b1.add(name);
        b1.add(name_text);

        JLabel price = new JLabel("����");
        JTextField price_text = new JTextField(10);
        JLabel num = new JLabel("ȷ������");
        JTextField num_text = new JTextField(10);

        b2.add(Box.createHorizontalStrut(20));
        b2.add(price);
        b2.add(price_text);
        b2.add(num);
        b2.add(num_text);

        JButton yes = new JButton("ȷ��");

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
                    JOptionPane.showMessageDialog(addFrame,"�˺��ѱ�ռ�ã�","����",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if(price_text.getText().equals(num_text.getText())){

                        User user = new User();
                        user.setUserNo(no_text.getText());
                        user.setUserName(name_text.getText());
                        user.setUserPassword(price_text.getText());
                        user.setUserGold(0);
                        new SetUser().addUser(user);
                        JOptionPane.showMessageDialog(addFrame,"��ӭ"+name_text.getText(),"ע��ɹ�",JOptionPane.PLAIN_MESSAGE);
                        setVisible(false);

                    }else{
                        JOptionPane.showMessageDialog(addFrame,"�������벻һ��","����",JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });


        addFrame.setVisible(true);
    }
}
