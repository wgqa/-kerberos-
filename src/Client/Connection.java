package Client;


//client 在socket连接中也一直作为socket client存在，工作流程为
//先与as服务器建立socket连接，工作完成后断开和as的socket连接。再与
//TGS服务器建立socket连接。断开连接，再建立与应用服务器的socket连接。
//三次建立socket连接的过程中，仅有IP和端口号不同吧应该？其他工作由通讯接受的报文的不同来区别


import DataStruct.Packet;

import java.io.*;

/*
 *  1. client与AS建立连接，client发送报文，等待一定时间，接收报文，结束。
 *
 *
 *
 * */
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static Client.Client.*;

public class Connection {

    public static void main(String args[]) throws Exception {
        Packet p= new Packet();

//        connectToAS();
//
//        connectToTGS(p);
//
//       connectToServer(p);
    }

    //通用收发工具
    public static  boolean send(String host, int port, String message){
        try {

            // 与服务端建立连接
            Socket socket = new Socket(host, port);

            // 建立连接后获得输出流
            OutputStream outputStream = socket.getOutputStream();



            outputStream.write(message.getBytes("UTF-8"));


            socket.shutdownOutput();


            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String receive(Socket socket){
        return null;
    };

    public static boolean connectToAS(String message, String userName){
        try {
            // 要连接的服务端IP地址和端口
            String host = ASIP;
            int port = 1233;
            // 与服务端建立连接
            Socket socket = new Socket("192.168.43.233", port);
           // Socket socket = new Socket(host, port);
            // 建立连接后获得输出流
            OutputStream outputStream = socket.getOutputStream();


            Packet packetToAS = clientToAS(userName, TGSID);
            //String message = packetToAS.toString()+"\n"+Client.packetToBinary(packetToAS);
            message =Client.packetToBinary(packetToAS);
            socket.getOutputStream().write(message.getBytes("UTF-8"));

            //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
            socket.shutdownOutput();

            /*  在此期间AS服务器对收到的报文进行验证，如果验证成功就会发送回数据包 */
            /*  设置一个最长等待时间，如果超出时间仍没有收到回复，则当作认证失败，报错 */

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len,"UTF-8"));
            }
            System.out.println("get message from server: " + sb);  // 这里后期要改成显示在客户端上

            // tostring方法用来展示在屏幕上
            //发送的包是 packetToBinary

            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean connectToTGS(String message,Packet packetFromAS){
        try {
            // 要连接的服务端IP地址和端口
            String host = TGSIP;
            int port = 1234;
            // 与服务端建立连接
            Socket socket = new Socket(host, port);
            // 建立连接后获得输出流
            OutputStream outputStream = socket.getOutputStream();


            Packet packetToTGS = clientToTGS(clientID, SERVERID,packetFromAS.getTicket(),Client.generateAuth(clientID,clientIP,packetFromAS.getSessionKey()));
            message = packetToTGS.toString()+"\n"+Client.packetToBinary(packetToTGS);
            socket.getOutputStream().write(message.getBytes("UTF-8"));



            socket.shutdownOutput();

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len,"UTF-8"));
            }
            System.out.println("get message from server: " + sb);

            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  true;
    }

    public  static boolean connectToServer(String message,Packet packetFromTGS){
        try {
            // 要连接的服务端IP地址和端口
            String host = ServerIP;
            int port = 1235;
            // 与服务端建立连接
            Socket socket = new Socket("192.168.43.140", port);
            // Socket socket = new Socket(host, port);
            // 建立连接后获得输出流
            OutputStream outputStream = socket.getOutputStream();

            //String clientID,DataStruct.Ticket ticketServer, DataStruct.Authenticator authServer
            Packet packetToServer = clientToServer(clientID, packetFromTGS.getTicket(),Client.generateAuth(clientID,clientIP,packetFromTGS.getSessionKey()));
            //String message = packetToAS.toString()+"\n"+Client.packetToBinary(packetToAS);
            message =Client.packetToBinary(packetToServer);
            socket.getOutputStream().write(message.getBytes("UTF-8"));

            //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
            socket.shutdownOutput();

            /*  在此期间AS服务器对收到的报文进行验证，如果验证成功就会发送回数据包 */
            /*  设置一个最长等待时间，如果超出时间仍没有收到回复，则当作认证失败，报错 */

            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len,"UTF-8"));
            }
            System.out.println("get message from server: " + sb);  // 这里后期要改成显示在客户端上

            // tostring方法用来展示在屏幕上
            //发送的包是 packetToBinary

            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}

/* log
* 首先进行了与server的测试，普通的socket通讯可以正常实现
* 出现unhandled exception错误 使用 try catch （ctrl+alt+t）
* */

  /*  Packet{
        head=Head{
            destID='0001',
            sourceID='1111',
            existLogin='0',
            existSessionKey='0',
            existID='1',
            existRequstID='1',
            existTS='1',
            existLifeTime='0',
            existTicket='0',
            existAuthenticator='0' },
        sessionKey='',
        clientID='1111',
        requestID='0010',
        imeStamp='',
        lifeTime='',
        Ticket=Ticket{
            sessionKey='',
            ID='',
            IP='',
            requestID='',
            timeStamp='',
            lifeTime=''},
        Auth=Authenticator{
            clientID='',
            clientIP='',
            timeStamp=''}
    }*/