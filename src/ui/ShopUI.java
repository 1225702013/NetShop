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

        super("网上商城————"+user.getUserName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置用户在此窗体上发起 "close" 时默认执行的操作。
        setBounds(500, 150, 550, 550);
        contentPane = new JPanel(); //指定容器
        setContentPane(contentPane);//设置 contentPane 属性
        contentPane.setLayout(null);


        JLabel name = new JLabel("欢迎"+user.getUserName()+"！");
        name.setFont(new Font("微软雅黑",Font.BOLD,20));
        name.setBounds(29,0,100,100);

        JLabel gold = new JLabel("余额:"+user.getUserGold());
        gold.setBounds(200,0,100,50);
        gold.setFont(new Font("宋体",Font.BOLD,18));
        gold.setForeground(Color.red);

        JButton getgold =new JButton("充值");     //按钮定义
        getgold.setBounds(330, 0, 154, 53);


        JButton Rec =new JButton("查看我购买的商品");     //按钮定义
        Rec.setBounds(29, 73, 154, 43);


        //JTable初始化
        Vector vData = new Vector();
        Vector vName = new Vector();
        vName.add("ID");
        vName.add("名称");
        vName.add("价格");
        vName.add("数量");
        DefaultTableModel model = new DefaultTableModel(new NetGoods().selectGoods(0), vName);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(29,173,300,300);

        JLabel tips = new JLabel("选中商品，输入数量以购买");
        tips.setBounds(350,173,200,50);
        JTextField num = new JTextField();
        num.setBounds(350,220,100,30);
        JButton buy = new JButton("购买");
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
                JOptionPane.showMessageDialog(contentPane,"购买成功！","购买成功",JOptionPane.PLAIN_MESSAGE);
                gold.setText("余额:"+user.getUserGold());
            }
        });
        getgold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("输入充值金额");
                user.setUserGold(user.getUserGold()+Double.valueOf(inputValue));
                new SetUser().updateUser(user);
                gold.setText("余额:"+user.getUserGold());
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
                    //System.out.println("正在购买....");
                    //扣除商品与金额
                    user.setUserGold(user.getUserGold()-(shop.getShopPrice()*shopnum));
                    shop.setShopNum(shop.getShopNum()-shopnum);
                    //添加交易记录
                    Record record = new Record();
                    record.setRecordNo(user.getUserNo());
                    record.setGoodName(shop.getShopName());
                    record.setGoodNum(shopnum);
                    record.setGoodGold(shop.getShopPrice()*shopnum);
                    //更新数据库
                    NetGoods update = new NetGoods();
                    update.updateGood(shop);
                    SetUser set = new SetUser();
                    set.updateUser(user);
                    SetRecord setrecord = new SetRecord();
                    setrecord.addRecord(record);
                    return true;
                }else {
                    //System.out.println("商品数量不足！");
                    JOptionPane.showMessageDialog(contentPane,"商品数量不足","购买失败",JOptionPane.ERROR_MESSAGE);
                }

            }else {
                JOptionPane.showMessageDialog(contentPane,"余额不足！","购买失败",JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    void MyRecord( User user ){

        JDialog addFrame = new JDialog(this,user.getUserName()+"购买的商品：",true);
        addFrame.setSize(400, 300);
        // 设置对话框大小不可改变
        addFrame.setResizable(false);
        addFrame.setLocationRelativeTo(null);

        //JTable初始化
        Vector vData = new Vector();
        Vector vName = new Vector();
        vName.add("ID");
        vName.add("名称");
        vName.add("价格");
        vName.add("数量");
        vName.add("日期");
        DefaultTableModel model = new DefaultTableModel(getUserGoods(user.getUserNo()), vName);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(29,173,400,400);
        addFrame.add(scroll);
        addFrame.setVisible(true);

    }


    //获取用户购买的商品
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
