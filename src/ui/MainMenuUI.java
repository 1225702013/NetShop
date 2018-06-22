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
        this.setTitle("后台管理系统——" + admin.getAdminName());
        contentPane = new JPanel(); //指定容器
        setContentPane(contentPane);//设置 contentPane 属性  
        contentPane.setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置用户在此窗体上发起 "close" 时默认执行的操作。
        setBounds(500, 150, 700, 700);

        JPanel GoodManager = new JPanel();
        JPanel UserManager = new JPanel();
        UserPanel(UserManager);
        GoodPanel(GoodManager);

        // 创建选项卡面板
        final JTabbedPane tabbedPane = new JTabbedPane();
        // 创建第 1 个选项卡
        tabbedPane.addTab("用户管理", UserManager);
        // 创建第 2 个选项卡
        tabbedPane.addTab("商品管理", GoodManager);
        // 设置默认选中的选项卡
        tabbedPane.setSelectedIndex(0);
        setContentPane(tabbedPane);
        setVisible(true);
    }


    /****************
     * 用户界面*************/
    void UserPanel(JPanel UserManager){


        Box searBox = Box.createHorizontalBox();
        Box TopBox = Box.createVerticalBox();
        Box changeBox = Box.createVerticalBox();


        //初始化搜索面板
        JTextField search = new JTextField(20);
        JButton btn_search = new JButton("搜索");
        JLabel searchLaber = new JLabel("输入用户账号以搜索");

        //整合搜索面板
        searBox.add(searchLaber);
        searBox.add(btn_search);
        searBox.add(search);
        searBox.add(Box.createVerticalStrut(20));
        UserManager.add(searBox);
        //初始化账号
        JLabel no_lab = new JLabel("账号");
        JTextField no_text = new JTextField(10);
        no_text.setEditable(false);
        JLabel name_lab = new JLabel("名称");
        JTextField name_text = new JTextField(10);
        //整合账号和名称
        Box v1Box = Box.createHorizontalBox();
        v1Box.add(no_lab);
        v1Box.add(no_text);
        v1Box.add(name_lab);
        v1Box.add(name_text);
        v1Box.add(Box.createHorizontalStrut(20));
        v1Box.add(Box.createVerticalStrut(20));

        //初始化密码余额
        JLabel pwd_lab = new JLabel("密码");
        JTextField pwd_text = new JTextField(10);
        JLabel gold_lab = new JLabel("余额");
        JTextField gold_text = new JTextField(10);
        //整合密码和余额
        Box v2Box = Box.createHorizontalBox();
        v2Box.add(pwd_lab);
        v2Box.add(pwd_text);
        v2Box.add(gold_lab);
        v2Box.add(gold_text);
        v2Box.add(Box.createHorizontalStrut(20));
        v2Box.add(Box.createVerticalStrut(20));
        //添加修改按钮
        JButton btn_change = new JButton("修改");
        changeBox.add(v1Box);
        changeBox.add(v2Box);
        changeBox.add(btn_change);
        changeBox.add(Box.createVerticalStrut(20));

        //修改面板加入顶部面板
        TopBox.add(changeBox);
        TopBox.add(Box.createVerticalStrut(50));


        JButton all = new JButton("查看全部");
        JButton delete = new JButton("删除");

        Box secBox = Box.createHorizontalBox();
        //TopBox.add(all);
        //TopBox.add(delete);
        secBox.add(all);
        secBox.add(new JPanel());
        secBox.add(delete);

        TopBox.add(secBox);

        UserManager.add(TopBox);

        //UserBox.add(Box.createVerticalStrut(50));


        //JTable 初始化
        String user_table[][] = new String[50][4];
        String coustm[] = new String[]{"账户", "名称", "密码", "余额"};


        JPanel ButtomPanel = new JPanel();


        //JTable初始化
        Vector vData = new Vector();
        Vector vName = new Vector();
        vName.add("账户");
        vName.add("姓名");
        vName.add("密码");
        vName.add("余额");
        DefaultTableModel model = new DefaultTableModel(vData, vName);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        UserManager.add(scroll);


        //搜索事件
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setUserNo(search.getText());
                SetUser sear = new SetUser();
                System.out.println(user.getUserNo());
                if (!sear.isExist(user.getUserNo())) {
                    JOptionPane.showMessageDialog(UserManager, "用户不存在！", "错误", JOptionPane.ERROR_MESSAGE);
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

        //查看全部
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

        //修改事件
        btn_change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                User user = new User();
                user.setUserNo(no_text.getText());
                user.setUserName(name_text.getText());
                user.setUserGold(Double.valueOf(gold_text.getText()));
                user.setUserPassword(pwd_text.getText());
                new SetUser().updateUser(user);
                JOptionPane.showMessageDialog(UserManager, "用户" + no_text.getText() + "的信息修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        //删除事件
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setUserNo(table.getValueAt(table.getSelectedRow(),0).toString());
                new SetUser().removeUser(user);
                JOptionPane.showMessageDialog(UserManager,"用户"+user.getUserNo()+"删除成功","提示",JOptionPane.PLAIN_MESSAGE);
            }
        });

    }
    /**
     * 商品界面*/
    void GoodPanel(JPanel GoodManager){
        Box searBox = Box.createHorizontalBox();
        Box TopBox = Box.createVerticalBox();
        Box changeBox = Box.createVerticalBox();


        //初始化搜索面板
        JTextField search = new JTextField(20);
        JButton btn_search = new JButton("搜索");
        JLabel searchLaber = new JLabel("输入用户账号以搜索");

        //整合搜索面板
        searBox.add(searchLaber);
        searBox.add(btn_search);
        searBox.add(search);
        searBox.add(Box.createVerticalStrut(20));
        GoodManager.add(searBox);
        //初始化账号
        JLabel no_lab = new JLabel("ID");
        JTextField no_text = new JTextField(10);
        no_text.setEditable(false);
        JLabel name_lab = new JLabel("名称");
        JTextField name_text = new JTextField(10);
        //整合账号和名称
        Box v1Box = Box.createHorizontalBox();
        v1Box.add(no_lab);
        v1Box.add(no_text);
        v1Box.add(name_lab);
        v1Box.add(name_text);
        v1Box.add(Box.createHorizontalStrut(20));
        v1Box.add(Box.createVerticalStrut(20));

        //初始化密码余额
        JLabel pwd_lab = new JLabel("价格");
        JTextField pwd_text = new JTextField(10);
        JLabel gold_lab = new JLabel("数量");
        JTextField gold_text = new JTextField(10);
        //整合密码和余额
        Box v2Box = Box.createHorizontalBox();
        v2Box.add(pwd_lab);
        v2Box.add(pwd_text);
        v2Box.add(gold_lab);
        v2Box.add(gold_text);
        v2Box.add(Box.createHorizontalStrut(20));
        v2Box.add(Box.createVerticalStrut(20));
        //添加修改按钮
        JButton btn_change = new JButton("修改");
        changeBox.add(v1Box);
        changeBox.add(v2Box);
        changeBox.add(btn_change);
        changeBox.add(Box.createVerticalStrut(20));

        //修改面板加入顶部面板
        TopBox.add(changeBox);
        TopBox.add(Box.createVerticalStrut(50));


        JButton all = new JButton("查看全部");
        JButton add = new JButton("添加商品");
        JButton delete = new JButton("删除");

        Box secBox = Box.createHorizontalBox();
        secBox.add(all);
        secBox.add(add);
        secBox.add(delete);

        TopBox.add(secBox);

        GoodManager.add(TopBox);

        //UserBox.add(Box.createVerticalStrut(50));


        //JTable 初始化
        String user_table[][] = new String[50][4];


        JPanel ButtomPanel = new JPanel();


        //JTable初始化
        Vector vData = new Vector();
        Vector vName = new Vector();
        vName.add("ID");
        vName.add("名称");
        vName.add("价格");
        vName.add("数量");
        DefaultTableModel model = new DefaultTableModel(vData, vName);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        GoodManager.add(scroll);


        //搜索事件
        btn_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shop user = new Shop();
                user.setShopNo(search.getText());
                NetGoods sear = new NetGoods();
               // System.out.println(user.getUserNo());
                if (!sear.isExist(user.getShopNo())) {
                    JOptionPane.showMessageDialog(GoodManager, "商品不存在！", "错误", JOptionPane.ERROR_MESSAGE);
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

        //查看全部
        all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //vData.add(.clone());
                DefaultTableModel model = new DefaultTableModel(new NetGoods().selectGoods(0), vName);
                table.setModel(model);
                table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



            }
        });

        //修改事件
        btn_change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Shop user = new Shop();
                user.setShopNo(no_text.getText());
                user.setShopName(name_text.getText());
                user.setShopNum(Integer.valueOf(gold_text.getText()));
                user.setShopPrice(Double.valueOf(pwd_text.getText()));
                new NetGoods().updateGood(user);
                JOptionPane.showMessageDialog(GoodManager, "商品" + no_text.getText() + "的信息修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        //删除事件
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shop shop = new Shop();
                shop.setShopNo(table.getValueAt(table.getSelectedRow(),0).toString());
                new NetGoods().removeGood(shop);
                JOptionPane.showMessageDialog(GoodManager,"商品"+shop.getShopNo()+"删除成功","提示",JOptionPane.PLAIN_MESSAGE);
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

        JDialog addFrame = new JDialog(this,"添加商品",true);
        addFrame.setTitle("添加商品");
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

        JLabel name = new JLabel("名称");
        JTextField name_text = new JTextField(10);

        b1.add(no);
        b1.add(no_text);
        b1.add(name);
        b1.add(name_text);

        JLabel price = new JLabel("价格");
        JTextField price_text = new JTextField(10);
        JLabel num = new JLabel("数量");
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
                JOptionPane.showMessageDialog(addFrame,"添加成功！","成功",JOptionPane.PLAIN_MESSAGE);

            }
        });
        addFrame.setVisible(true);

    }



	
	
	
}
