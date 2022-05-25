//package Client.UI;
//
//import DataStruct.Packet;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import Client.Client;
//
//import static Client.Connection.*;
//
//public class MainWindow extends JFrame {
//
//    private JPanel contentPane;  //私有成员
//    private JTextField username2;
//    private JTextField password2;//加2是防重名
//    private JTextArea messageOnScreen;
//    //写在类中的是成员，其他地方能用，写在构造函数中的是一次性的
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    MainWindow frame = new MainWindow();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    //构造函数，在这里写主窗口的参数设置
//    public MainWindow(){
//        setTitle("主窗口");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置默认的退出方式
//        setBounds(400, 250, 855, 475);//设置frame大小
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
//        //用户名标签
//        JLabel username = new JLabel("用户名\uFF1A");
//        username.setBounds(43, 167, 72, 18);
//        username.setFont(new Font("仿宋", Font.PLAIN, 18));
//        //密码标签
//        JLabel password = new JLabel("\u5BC6\u7801\uFF1A");
//        password.setBounds(43, 267, 72, 18);
//        password.setFont(new Font("仿宋", Font.PLAIN, 18));
//
//        //JTextField写入文本框 JTextarea 显示文本框
//        username2 =new JTextField();
//        username2.setBounds(129, 164, 176, 24);
//        username2.setFont(new Font("仿宋", Font.PLAIN, 18));
//        username2.setColumns(10);
//        password2 = new JTextField();
//        password2.setBounds(129, 264, 176, 24);
//        password2.setFont(new Font("仿宋", Font.PLAIN, 18));
//        password2.setColumns(10);
//
//        //显示报文
//        JScrollPane scrollPane = new JScrollPane();
//        scrollPane.setBounds(437, 48, 366, 358);
//        messageOnScreen = new JTextArea();
//        messageOnScreen.setLineWrap(true);
//        messageOnScreen.setEditable(false);
//        messageOnScreen.setForeground(Color.GRAY);
//        messageOnScreen.setFont(new Font("仿宋", Font.PLAIN, 18));
//        messageOnScreen.setColumns(10);
//
//
//        //实现注册和登录按钮
//        JButton signup =new JButton("注册");
//        signup.setBounds(296, 98, 72, 27);
//        signup.setFont(new Font("仿宋", Font.PLAIN, 18));
//        signup.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//                SIGNUP frame1=new SIGNUP();
//                frame1.setVisible(true);
//            }
//        });
//        //注册会关闭主窗口怎么解决
//
//        JButton login = new JButton("登录");
//        login.setBounds(296, 367, 72, 27);
//        login.setFont(new Font("仿宋", Font.PLAIN, 18));
//        login.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                Packet p = new Packet();
//                //p = Client.signin2(username2.getText(),password2.getText());
//                //
//                String message="";
//                //这里的逻辑应该如何组织
//                //登录的逻辑  Kerberos的整体流程是做登录的 使用用户名连接AS
//                //注册的逻辑
//                if(connectToAS(message,password2.getText())){
//                    messageOnScreen.setText("成功连接到AS");
//                    messageOnScreen.setText("发送给AS的包"+message);
//                    if(connectToTGS(message,p)){
//                        messageOnScreen.setText("成功连接到TGS");
//                        messageOnScreen.setText("发送给TGS的包"+message);
//                        if(connectToServer(message,p)){
//                            messageOnScreen.setText("成功连接到Server");
//                            messageOnScreen.setText("发送给SERVER的包"+message);
//                        }
//                    }
//                }
//
//
//            }
//        });
//
//        //不显示是因为没有把这些组件加到contentPane中
//        contentPane.setLayout(null);
//        contentPane.add(username);
//        contentPane.add(password);
//        contentPane.add(username2);
//        contentPane.add(password2);
//        contentPane.add(signup);
//        contentPane.add(login);
//        contentPane.add(scrollPane);
//        contentPane.add(messageOnScreen);
//
//    }
//}
