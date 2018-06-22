package ui;

import DB.LinkDb;
import DB.NetGoods;
import DB.SetRecord;
import DB.SetUser;
import lib.Record;
import lib.Shop;
import lib.ShopInfo;
import lib.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ShopUI extends JFrame {

    JPanel contentPane;

    ShopUI(User user){

        super("�����̳ǡ�������"+user.getUserName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�����û��ڴ˴����Ϸ��� "close" ʱĬ��ִ�еĲ�����
        setBounds(500, 150, 550, 550);
        contentPane = new JPanel(); //ָ������
        setContentPane(contentPane);//���� contentPane ����
        contentPane.setLayout(null);


        JLabel name = new JLabel("��ӭ"+user.getUserName()+"��");
        name.setFont(new Font("΢���ź�",Font.BOLD,20));
        name.setBounds(29,0,100,100);

        JLabel gold = new JLabel("���:"+user.getUserGold());
        gold.setBounds(200,0,100,50);
        gold.setFont(new Font("����",Font.BOLD,18));
        gold.setForeground(Color.red);

        JButton getgold =new JButton("��ֵ");     //��ť����
        getgold.setBounds(330, 0, 154, 53);


        JButton Rec =new JButton("�鿴�ҹ������Ʒ");     //��ť����
        Rec.setBounds(29, 73, 154, 43);


        //JTable��ʼ��
        Vector vData = new Vector();
        Vector vName = new Vector();
        vName.add("ID");
        vName.add("����");
        vName.add("�۸�");
        vName.add("����");
        DefaultTableModel model = new DefaultTableModel(new NetGoods().selectGoods(0), vName);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(29,173,300,300);

        JLabel tips = new JLabel("ѡ����Ʒ�����������Թ���");
        tips.setBounds(350,173,200,50);
        JTextField num = new JTextField();
        num.setBounds(350,220,100,30);
        JButton buy = new JButton("����");
        buy.setBounds(350,270,100,30);

        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shop shop = new Shop();
                shop.setShopNo(table.getValueAt(table.getSelectedRow(),0).toString());
                shopping(shop.getShopNo(),Integer.valueOf(num.getText()),user);
                Vector vData = new Vector();
                DefaultTableModel model = new DefaultTableModel(new NetGoods().selectGoods(0), vName);
                table.setModel(model);
                JOptionPane.showMessageDialog(contentPane,"����ɹ���","����ɹ�",JOptionPane.PLAIN_MESSAGE);
                gold.setText("���:"+user.getUserGold());
            }
        });
        getgold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("�����ֵ���");
                user.setUserGold(user.getUserGold()+Double.valueOf(inputValue));
                new SetUser().updateUser(user);
                gold.setText("���:"+user.getUserGold());
            }
        });
        Rec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyRecord(user);
            }
        });



        contentPane.add(scroll);
        contentPane.add(name);
        contentPane.add(gold);
        contentPane.add(Rec);
        contentPane.add(getgold);
        contentPane.add(tips);
        contentPane.add(num);
        contentPane.add(buy);


        setVisible(true);

    }

    boolean shopping (String usershopping,int shopnum,User user) {
        Shop shop = new Shop();
        shop.setShopNo(usershopping);


        ShopInfo shop_info = new ShopInfo();
        if(shop_info.getShopInfo(shop)) {
            if(user.getUserGold()-(shop.getShopPrice()*shopnum)>=0) {

                if(shop.getShopNum()>=shopnum) {
                    //System.out.println("���ڹ���....");
                    //�۳���Ʒ����
                    user.setUserGold(user.getUserGold()-(shop.getShopPrice()*shopnum));
                    shop.setShopNum(shop.getShopNum()-shopnum);
                    //��ӽ��׼�¼
                    Record record = new Record();
                    record.setRecordNo(user.getUserNo());
                    record.setGoodName(shop.getShopName());
                    record.setGoodNum(shopnum);
                    record.setGoodGold(shop.getShopPrice()*shopnum);
                    //�������ݿ�
                    NetGoods update = new NetGoods();
                    update.updateGood(shop);
                    SetUser set = new SetUser();
                    set.updateUser(user);
                    SetRecord setrecord = new SetRecord();
                    setrecord.addRecord(record);
                    return true;
                }else {
                    //System.out.println("��Ʒ�������㣡");
                    JOptionPane.showMessageDialog(contentPane,"��Ʒ��������","����ʧ��",JOptionPane.ERROR_MESSAGE);
                }

            }else {
                JOptionPane.showMessageDialog(contentPane,"���㣡","����ʧ��",JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    void MyRecord( User user ){

        JDialog addFrame = new JDialog(this,user.getUserName()+"�������Ʒ��",true);
        addFrame.setSize(400, 300);
        // ���öԻ����С���ɸı�
        addFrame.setResizable(false);
        addFrame.setLocationRelativeTo(null);

        //JTable��ʼ��
        Vector vData = new Vector();
        Vector vName = new Vector();
        vName.add("ID");
        vName.add("����");
        vName.add("�۸�");
        vName.add("����");
        vName.add("����");
        DefaultTableModel model = new DefaultTableModel(getUserGoods(user.getUserNo()), vName);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(29,173,400,400);
        addFrame.add(scroll);
        addFrame.setVisible(true);

    }


    //��ȡ�û��������Ʒ
    Vector getUserGoods(String user) {

        Vector vData = new Vector();


        String sql = "select * from shopping where custom_no = ?";
        LinkDb show = new LinkDb();
        Connection db = show.getConn();
        try {
            PreparedStatement pstm = (PreparedStatement)db.prepareStatement(sql);
            pstm.setString(1, user);
            ResultSet rs = pstm.executeQuery();
            if(!rs.next()) {
                return null;
            }
            rs.previous();

            while(rs.next()) {
                Vector vRow = new Vector();
                vRow.add(rs.getString("custom_no"));
                vRow.add(rs.getString("good_name"));
                vRow.add(rs.getString("good_gold"));
                vRow.add(rs.getInt("good_num"));
                vRow.add(rs.getString("date"));
                vData.add(vRow.clone());
            }

            pstm.close();
            db.close();
            return vData;

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return null;
        }
    }

}
