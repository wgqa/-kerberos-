//package Client.UI;
//
//import Client.Client;
//import Client.Connection;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.net.Socket;
//
//public class SIGNUP extends JFrame {
//    private JPanel contentPane;//返回键
//    private JTextField username2;
//    private JTextField password2;
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    SIGNUP frame1 = new SIGNUP();
//                    frame1.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    public SIGNUP(){
//        setTitle("注册页面");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(650, 400, 428, 284);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
//        contentPane.setLayout(null);//不使用布局管理器
//
//        //用户名标签
//        JLabel username = new JLabel("用户名:");
//        username.setBounds(72, 55, 72, 18);
//        username.setFont(new Font("仿宋", Font.PLAIN, 18));
//        //密码标签
//        JLabel password = new JLabel("密码:");
//        password.setBounds(72, 131, 72, 18);
//        password.setFont(new Font("仿宋", Font.PLAIN, 18));
//
//        //JTextField写入文本框 JTextarea 显示文本框
//        username2 =new JTextField();
//        username2.setBounds(156, 55, 176, 24);
//        username2.setFont(new Font("仿宋", Font.PLAIN, 18));
//        username2.setColumns(10);
//        password2 = new JTextField();
//        password2.setBounds(156, 131, 176, 24);
//        password2.setFont(new Font("仿宋", Font.PLAIN, 18));
//        password2.setColumns(10);
//
//        //注册按钮
//        JButton signup = new JButton("注册");
//        signup.setFont(new Font("仿宋", Font.PLAIN, 18));
//        signup.setBounds(219, 193, 72, 27);
//        signup.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {      		  //注册按钮S
//                String s = "";
//                s = sign_up();
//                if(s == "1") {
//                    System.err.println("用户名和密码不能为空，注册失败！");
//                    JOptionPane.showMessageDialog(null,"用户名和密码不能为空，注册失败！","弹出窗口",JOptionPane.INFORMATION_MESSAGE);
//
//                }
//                else if((s.equals("2")) ) {
//                    System.err.println("该id已被注册！");
//                    JOptionPane.showMessageDialog(null,"该用户名已被注册！","弹出窗口",JOptionPane.INFORMATION_MESSAGE);
//                }
//                else if(s.equals("")) {
//                    System.err.println("注册失败！");
//                    JOptionPane.showMessageDialog(null,"注册失败！","弹出窗口",JOptionPane.INFORMATION_MESSAGE);
//                }
//                else{
//                    System.out.println("注册成功！");
//                    JOptionPane.showMessageDialog(null,"注册成功！","弹出窗口",JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
//        //返回按钮
//        JButton back = new JButton("返回");
//        back.setFont(new Font("仿宋", Font.PLAIN, 18));
//        back.setBounds(324, 193, 72, 27);
//
//
//
//        contentPane.setLayout(null);
//        contentPane.add(username);
//        contentPane.add(password);
//        contentPane.add(username2);
//        contentPane.add(password2);
//        contentPane.add(signup);
//        contentPane.add(back);
//
//    }
//
////    public String  sign_up(){
////
////        if(!(username2.getText().equals("") || password2.getText().equals(""))) {
////
////            DataStruct.Packet p = Client.signup2(username2.getText(),password2.getText());
////            String send = p.getHead().headOutput()+p.packageOutput();
////            String re = null;
////            //发给AS
////            try {
////                Socket socket = new Socket(Client.ASIP,5555);
////                if(Connection.send(Client.ASIP,1233,send)) {
////                    re = Connection.receive(socket);
////                    socket.close();
////                }
////            } catch (IOException e1) {
////                // TODO Auto-generated catch block
////                e1.printStackTrace();
////            }
////            return re;
////        }
////        else return "1";
////    }
//
//}
