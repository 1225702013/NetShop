package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import DB.NetGoods;
import DB.SetUser;
import lib.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


public class MainMenuUI extends JFrame {
	
	JPanel contentPane;
	JTextField search;
	JTabbedPane table;
	JFrame addFrame;




	MainMenuUI(Admin admin) {


        setVisible(true);
        //addFrame.setVisible(false);
        this.setTitle("��̨����ϵͳ����" + admin.getAdminName());
        contentPane = new JPanel(); //ָ������
        setContentPane(contentPane);//���� contentPane ����  
        contentPane.setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�����û��ڴ˴����Ϸ��� "close" ʱĬ��ִ�еĲ�����
        setBounds(500, 150, 700, 700);

        JPanel GoodManager = new JPanel();
        JPanel UserManager = new JPanel();
        UserPanel(UserManager);
        GoodPanel(GoodManager);

        // ����ѡ����
        final JTabbedPane tabbedPane = new JTabbedPane();
        // ������ 1 ��ѡ�
        tabbedPane.addTab("�û�����", UserManager);
        // ������ 2 ��ѡ�
        tabbedPane.addTab("��Ʒ����", GoodManager);
        // ����Ĭ��ѡ�е�ѡ�
        tabbedPane.setSelectedIndex(0);
        setContentPane(tabbedPane);
        setVisible(true);
    }


    /****************
     * �û�����*************/
    void UserPanel(JPanel UserManager){


        Box searBox = Box.createHorizontalBox();
        Box TopBox = Box.createVerticalBox();
        Box changeBox = Box.createVerticalBox();


        //��ʼ���������
        JTextField search = new JTextField(20);
        JButton btn_search = new JButton("����");
        JLabel searchLaber = new JLabel("�����û��˺�������");

        //�����������
        searBox.add(searchLaber);
        searBox.add(btn_search);
        searBox.add(search);
        searBox.add(Box.createVerticalStrut(20));
        UserManager.add(searBox);
        //��ʼ���˺�
        JLabel no_lab = new JLabel("�˺�");
        JTextField no_text = new JTextField(10);
        no_text.setEditable(false);
        JLabel name_lab = new JLabel("����");
        JTextField name_text = new JTextField(10);
        //�����˺ź�����
        Box v1Box = Box.createHorizontalBox();
        v1Box.add(no_lab);
        v1Box.add(no_text);
        v1Box.add(name_lab);
        v1Box.add(name_text);
        v1Box.add(Box.createHorizontalStrut(20));
        v1Box.add(Box.createVerticalStrut(20));

        //��ʼ���������
        JLabel pwd_lab = new JLabel("����");
        JTextField pwd_text = new JTextField(10);
        JLabel gold_lab = new JLabel("���");
        JTextField gold_text = new JTextField(10);
        //������������
        Box v2Box = Box.createHorizontalBox();
        v2Box.add(pwd_lab);
        v2Box.add(pwd_text);
        v2Box.add(gold_lab);
        v2Box.add(gold_text);
        v2Box.add(Box.createHorizontalStrut(20));
        v2Box.add(Box.createVerticalStrut(20));
        //����޸İ�ť
        JButton btn_change = new JButton("�޸�");
        changeBox.add(v1Box);
        changeBox.add(v2Box);
        changeBox.add(btn_change);
        changeBox.add(Box.createVerticalStrut(20));

        //�޸������붥�����
        TopBox.add(changeBox);
        TopBox.add(Box.createVerticalStrut(50));


        JButton all = new JButton("�鿴ȫ��");
        JButton delete = new JButton("ɾ��");

        Box secBox = Box.createHorizontalBox();
        //TopBox.add(all);
        //TopBox.add(delete);
        secBox.add(all);
        secBox.add(new JPanel());
        secBox.add(delete);

        TopBox.add(secBox);

        UserManager.add(TopBox);

        //UserBox.add(Box.createVerticalStrut(50));


        //JTable ��ʼ��
        String user_table[][] = new String[50][4];
        String coustm[] = new String[]{"�˻�", "����", "����", "���"};


        JPanel ButtomPanel = new JPanel();


        //JTable��ʼ��
        Vector vData = new Vector();
        Vector vName = new Vector();
        vName.add("�˻�");
        vName.add("����");
        vName.add("����");
        vName.add("���");
        DefaultTableModel model = new DefaultTableModel(vData, vName);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        UserManager.add(scroll);


        //�����¼�
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setUserNo(search.getText());
                SetUser sear = new SetUser();
                System.out.println(user.getUserNo());
                if (!sear.isExist(user.getUserNo())) {
                    JOptionPane.showMessageDialog(UserManager, "�û������ڣ�", "����", JOptionPane.ERROR_MESSAGE);
                } else {
                    new UserInfo().getUserInfo(user);
                    Vector vRow = new Vector();
                    Vector vData= new Vector();
                    vRow.add(user.getUserNo());
                    vRow.add(user.getUserName());
                    vRow.add(user.getUserPassword());
                    vRow.add(user.getUserGold());
                    vData.add(vRow.clone());
                    DefaultTableModel model = new DefaultTableModel(vData, vName);
                    table.setModel(model);
                    table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    table.setRowSelectionInterval(0,0);

                    no_text.setText(user.getUserNo());
                    name_text.setText(user.getUserName());
                    pwd_text.setText(user.getUserPassword());
                    gold_text.setText(Double.toString(user.getUserGold()));
                }

            }
        });

        //�鿴ȫ��
        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //vData.add(.clone());
                DefaultTableModel model = new DefaultTableModel(new SetUser().selectUser(0), vName);
                table.setModel(model);
                //table.setEnabled(false);
                table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


            }
        });

        //�޸��¼�
        btn_change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                User user = new User();
                user.setUserNo(no_text.getText());
                user.setUserName(name_text.getText());
                user.setUserGold(Double.valueOf(gold_text.getText()));
                user.setUserPassword(pwd_text.getText());
                new SetUser().updateUser(user);
                JOptionPane.showMessageDialog(UserManager, "�û�" + no_text.getText() + "����Ϣ�޸ĳɹ�", "�ɹ�", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        //ɾ���¼�
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setUserNo(table.getValueAt(table.getSelectedRow(),0).toString());
                new SetUser().removeUser(user);
                JOptionPane.showMessageDialog(UserManager,"�û�"+user.getUserNo()+"ɾ���ɹ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
            }
        });

    }
    /**
     * ��Ʒ����*/
    void GoodPanel(JPanel GoodManager){
        Box searBox = Box.createHorizontalBox();
        Box TopBox = Box.createVerticalBox();
        Box changeBox = Box.createVerticalBox();


        //��ʼ���������
        JTextField search = new JTextField(20);
        JButton btn_search = new JButton("����");
        JLabel searchLaber = new JLabel("�����û��˺�������");

        //�����������
        searBox.add(searchLaber);
        searBox.add(btn_search);
        searBox.add(search);
        searBox.add(Box.createVerticalStrut(20));
        GoodManager.add(searBox);
        //��ʼ���˺�
        JLabel no_lab = new JLabel("ID");
        JTextField no_text = new JTextField(10);
        no_text.setEditable(false);
        JLabel name_lab = new JLabel("����");
        JTextField name_text = new JTextField(10);
        //�����˺ź�����
        Box v1Box = Box.createHorizontalBox();
        v1Box.add(no_lab);
        v1Box.add(no_text);
        v1Box.add(name_lab);
        v1Box.add(name_text);
        v1Box.add(Box.createHorizontalStrut(20));
        v1Box.add(Box.createVerticalStrut(20));

        //��ʼ���������
        JLabel pwd_lab = new JLabel("�۸�");
        JTextField pwd_text = new JTextField(10);
        JLabel gold_lab = new JLabel("����");
        JTextField gold_text = new JTextField(10);
        //������������
        Box v2Box = Box.createHorizontalBox();
        v2Box.add(pwd_lab);
        v2Box.add(pwd_text);
        v2Box.add(gold_lab);
        v2Box.add(gold_text);
        v2Box.add(Box.createHorizontalStrut(20));
        v2Box.add(Box.createVerticalStrut(20));
        //����޸İ�ť
        JButton btn_change = new JButton("�޸�");
        changeBox.add(v1Box);
        changeBox.add(v2Box);
        changeBox.add(btn_change);
        changeBox.add(Box.createVerticalStrut(20));

        //�޸������붥�����
        TopBox.add(changeBox);
        TopBox.add(Box.createVerticalStrut(50));


        JButton all = new JButton("�鿴ȫ��");
        JButton add = new JButton("�����Ʒ");
        JButton delete = new JButton("ɾ��");

        Box secBox = Box.createHorizontalBox();
        secBox.add(all);
        secBox.add(add);
        secBox.add(delete);

        TopBox.add(secBox);

        GoodManager.add(TopBox);

        //UserBox.add(Box.createVerticalStrut(50));


        //JTable ��ʼ��
        String user_table[][] = new String[50][4];


        JPanel ButtomPanel = new JPanel();


        //JTable��ʼ��
        Vector vData = new Vector();
        Vector vName = new Vector();
        vName.add("ID");
        vName.add("����");
        vName.add("�۸�");
        vName.add("����");
        DefaultTableModel model = new DefaultTableModel(vData, vName);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        GoodManager.add(scroll);


        //�����¼�
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shop user = new Shop();
                user.setShopNo(search.getText());
                NetGoods sear = new NetGoods();
               // System.out.println(user.getUserNo());
                if (!sear.isExist(user.getShopNo())) {
                    JOptionPane.showMessageDialog(GoodManager, "��Ʒ�����ڣ�", "����", JOptionPane.ERROR_MESSAGE);
                } else {
                    new ShopInfo().getShopInfo(user);
                    Vector vRow = new Vector();
                    Vector vData = new Vector();
                    vRow.add(user.getShopNo());
                    vRow.add(user.getShopName());
                    vRow.add(user.getShopPrice());
                    vRow.add(user.getShopNum());
                    vData.add(vRow.clone());
                    DefaultTableModel model = new DefaultTableModel(vData, vName);
                    table.setModel(model);
                    table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    table.setRowSelectionInterval(0,0);

                    no_text.setText(user.getShopNo());
                    name_text.setText(user.getShopName());
                    pwd_text.setText(Double.toString(user.getShopPrice()));
                    gold_text.setText(Integer.toString(user.getShopNum()));
                }

            }
        });

        //�鿴ȫ��
        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //vData.add(.clone());
                DefaultTableModel model = new DefaultTableModel(new NetGoods().selectGoods(0), vName);
                table.setModel(model);
                table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



            }
        });

        //�޸��¼�
        btn_change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Shop user = new Shop();
                user.setShopNo(no_text.getText());
                user.setShopName(name_text.getText());
                user.setShopNum(Integer.valueOf(gold_text.getText()));
                user.setShopPrice(Double.valueOf(pwd_text.getText()));
                new NetGoods().updateGood(user);
                JOptionPane.showMessageDialog(GoodManager, "��Ʒ" + no_text.getText() + "����Ϣ�޸ĳɹ�", "�ɹ�", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        //ɾ���¼�
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shop shop = new Shop();
                shop.setShopNo(table.getValueAt(table.getSelectedRow(),0).toString());
                new NetGoods().removeGood(shop);
                JOptionPane.showMessageDialog(GoodManager,"��Ʒ"+shop.getShopNo()+"ɾ���ɹ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGood();
            }
        });

    }


    void addGood(){

        JDialog addFrame = new JDialog(this,"�����Ʒ",true);
        addFrame.setTitle("�����Ʒ");
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

        JLabel name = new JLabel("����");
        JTextField name_text = new JTextField(10);

        b1.add(no);
        b1.add(no_text);
        b1.add(name);
        b1.add(name_text);

        JLabel price = new JLabel("�۸�");
        JTextField price_text = new JTextField(10);
        JLabel num = new JLabel("����");
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
        b3.add(yes);
        addFrame.add(b3);



        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shop shop = new Shop();
                shop.setShopNo(no_text.getText());
                shop.setShopName(name_text.getText());
                shop.setShopPrice(Double.valueOf(price_text.getText()));
                shop.setShopNum(Integer.valueOf(num_text.getText()));
                //System.out.println(shop.getShopNo());
                new NetGoods().addGood(shop);
                JOptionPane.showMessageDialog(addFrame,"��ӳɹ���","�ɹ�",JOptionPane.PLAIN_MESSAGE);

            }
        });
        addFrame.setVisible(true);

    }



	
	
	
}
