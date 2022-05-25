//package AS;
//
//import java.io.*;
//import java.lang.reflect.Array;
//import java.net.InetAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.*;
//
//public class AS {
//    private static int Number = -1; //包的初始编号
//    private final int MAXNUMBER = 0; //包的最大编号
//    private final static String ASID = "0001"; //ASID
//    private final static String[] KEYTGS = {"865703290362069039664574527025207637385565054952203601297173618790874525723","3889"}; //TGS公钥
//    private final static String[] Kc = {"3096589494327972966542767555645488415857410521298179560751893624567975523927775168085739664949238616280271893353946263715523651672294362843822766996968340714023382235747900221065977","3889"}; //client公钥
//
//    /*
//    发送消息
//     */
//    public static boolean send(Socket socket, String message) throws IOException{
//        OutputStream os=null;
//        try {
//
//            os = socket.getOutputStream();
//            os.write(message.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally{
//            os.flush();
//            socket.shutdownOutput();
//        }
//
//        return true;
//    }
//    /*
//    接收消息
//     */
//    public static String receive(Socket s) throws IOException{
//        String ssss="";
//        InputStream is=null;
//        try {
//
//            is = s.getInputStream();
//            //4.对获取的输入流进行的操作
//            byte [] b = new byte[20];
//            int len;
//            while((len = is.read(b))!=-1){
//                String str = new String(b,0,len);
//                ssss+=str;
//            }
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }finally{
//            s.shutdownInput();
//        }
//        System.out.println("收到的包："+ssss);
//        return ssss;
//    }
//    public static DataStruct.Packet packAnalyse(String message) {
//
//        DataStruct.Packet p = new DataStruct.Packet();
//
//        int headLength = p.getHead().headOutput().length();
//        char M[] = message.toCharArray();
//        System.out.println(Arrays.toString(M)+"\n");
//
//
//        String s[] = new String[headLength];
//        for (int i = 0; i < headLength; i++) {
//            s[i] = String.valueOf(M[i]);
//        }
//        System.out.println(Arrays.toString(s)+"\n");
//        p.setHead(new DataStruct.Head(s[0] + s[1] + s[2] + s[3], s[4] + s[5] + s[6] + s[7], s[8], s[9], s[10],
//                s[11], s[12], s[13], s[14], s[15]));
//
//        DataStruct.Packet p2 = new DataStruct.Packet();
//        p2.setHead(new DataStruct.Head("0000", "1111", "2", "3","3",
//                "4", "5", "6","7", "8"));
//        //System.out.println(p.getHead());
//        System.out.println(p2);
//        String pack = message.replaceFirst(p.getHead().headOutput(), "");
//
//        System.out.println("分析的包："+ p.toString());
//        return p;
//    }
//    /**
//     * 生成TicketTGS
//     * 按 Kc,tgs|| IDc|| ADc|| IDtgs|| TS2|| Lifetime2 格式封装 Ticket
//     * 用 Ktgs 加密
//     * 以字符串形式返回
//     * @param  p 解析的包中的内容
//     * @param  k Ktgs
//     * @return 返回加密后的Ticket
//     */
//    /*public static DataStruct.Ticket generateTicketTGS(DataStruct.Packet p,InetAddress inetAddress){
//        System.out.println("-----正在生成Ticket-----");
//        String lifetime = DataStruct.Packet.Create_lifeTime(2);
//        String clientIP = ipToBinary(inetAddress);
//        DataStruct.Ticket t = new Ticket(generateKeyCtgs(), p.getHead().getSourceID(), clientIP,
//                p.getRequestID(), DataStruct.Packet.Create_TS(), lifetime);
//        System.out.println("生成的Ticket："+t);
//
//        return t;
//    }*/
//    public static String ipToBinary(InetAddress ip)
//    {
////		String s = "";
////		char M[] = ip.toString().toCharArray();
//
////		for(int i = 1 ;i<M.length;i++)
////			s += M[i];
//
//        byte[] b=ip.getAddress();
////        System.out.println(b[0]);
////        System.out.println(b[1]);
////        System.out.println(b[2]);
//        //  s = s+b[0];
////        System.out.println(s);
//        long l= b[0]<<24L & 0xff000000L|
//                b[1]<<16L & 0xff0000L  |
//                b[2]<<8L  &  0xff00L   |
//                b[3]<<0L  &  0xffL ;
//
//        return Integer.toBinaryString((int)l);
//    }
//    /**
//     * 打包数据
//     * 形成  KeyCtgs||IDtgs||时间戳||生存周期||Tickettgs 包
//     * 加密的密文以包形式返回
//     * @param clientID client的ID
//     * @param IDtgs tgs的ID
//     * @param TicketTgs 加密后的tgs票据
//     * @return
//     */
//    /*public static DataStruct.Packet packData(String clientID,String IDtgs,DataStruct.Ticket TicketTgs){
//        DataStruct.Packet p = new DataStruct.Packet();;
//
//        p.setSessionKey(TicketTgs.getSessionKey());
//        p.setRequestID(IDtgs);
//        p.setTimeStamp(DataStruct.Packet.Create_TS());
//        p.setLifeTime(TicketTgs.getLifeTime());
//        p.setTicket(TicketTgs);
//
//        if(Number > 16)
//        {
//            Number = -1;
//        }
//        Number++;
//        String number = supplement(4, Integer.toBinaryString(Number));
//        String securityCode = DataStruct.Head.zero(128);
//
//        DataStruct.Head h= new DataStruct.Head(clientID,ASID,"0","1","0","1","1","1","1","0",number,securityCode,"0000000000000000");
//
//        p.setHead(h);
//
//        return p;
//    }*/
//    public static void main(String[] args) throws IOException {
//        //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
//        ServerSocket serverSocket = new ServerSocket(1233);
//        InetAddress address = InetAddress.getLocalHost();
//        String ip = address.getHostAddress();
//        Socket socket = null;
//        //2.调用accept()等待客户端连接
//        System.out.println("~~~AS服务端已就绪，等待客户端接入~，服务端ip地址: " + "192.198.43.233");
//        socket = serverSocket.accept();
//        //3.连接后获取输入流，读取客户端信息
//        InputStream is=null;
//        InputStreamReader isr=null;
//        BufferedReader br=null;
//        OutputStream os=null;
//        PrintWriter pw=null;
//        is = socket.getInputStream();     //获取输入流
//        isr = new InputStreamReader(is,"UTF-8");
//        br = new BufferedReader(isr);
//        String info = null;
//        String message2 =null;
//        while((info=br.readLine())!=null){//循环读取客户端的信息
//            System.out.println("客户端发送过来的信息" + info);
//            message2=info;
//            System.out.println(message2);
//
//        }
//        info=br.readLine();
//        socket.shutdownInput();//关闭输入流
//        socket.close();
//
//        packAnalyse(message2);
//    }

//}
package AS;

        import Client.Client;
        import DataStruct.Head;
        import DataStruct.Packet;
        import DataStruct.Ticket;
        import jdk.swing.interop.SwingInterOpUtils;

        import java.io.*;
        import java.lang.reflect.Array;
        import java.net.InetAddress;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.*;

        import static Client.Client.TGSID;
        import static Client.Client.clientToAS;

public class AS {

    private final static String ASID = "0001"; //ASID
    private final static String[] KEYTGS = {"865703290362069039664574527025207637385565054952203601297173618790874525723","3889"}; //TGS公钥
    private final static String[] Kc = {"3096589494327972966542767555645488415857410521298179560751893624567975523927775168085739664949238616280271893353946263715523651672294362843822766996968340714023382235747900221065977","3889"}; //client公钥

    /*
    发送消息
     */
    public static boolean send(Socket socket, String message) throws IOException{
        OutputStream os=null;
        try {

            os = socket.getOutputStream();
            os.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            os.flush();
            socket.shutdownOutput();
        }

        return true;
    }
    /*
    接收消息
     */
    public static String receive(Socket s) throws IOException{
        String ssss="";
        InputStream is=null;
        try {

            is = s.getInputStream();
            //4.对获取的输入流进行的操作
            byte [] b = new byte[20];
            int len;
            while((len = is.read(b))!=-1){
                String str = new String(b,0,len);
                ssss+=str;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            s.shutdownInput();
        }
        System.out.println("收到的包："+ssss);
        return ssss;
    }
    public static DataStruct.Packet packAnalyse(String message) {

        DataStruct.Packet p = new DataStruct.Packet();

        int headLength = p.getHead().headOutput().length();
        char M[] = message.toCharArray();//


        String s[] = new String[headLength];
        for (int i = 0; i < headLength; i++) {
            s[i] = String.valueOf(M[i]);
        }
//        //p.setHead(new DataStruct.Head(s[0] + s[1] + s[2] + s[3], s[4] + s[5] + s[6] + s[7], "login"+s[8],"sessionkey"+ s[9],"clientid"+ s[10],
//             //  "requestid"+ s[11], "ts"+s[12],"lifetime"+ s[13], "ticket"+s[14], "auth"+s[15],"",""));
//        p.setHead(new DataStruct.Head(s[0] + s[1] + s[2] + s[3], s[4] + s[5] + s[6] + s[7], s[8],s[9], s[10],
//                 s[11], s[12],s[13], s[14], s[15],"",""));
//        /*Head部分全部解析*/
        p.setHead(new DataStruct.Head( s[0]+s[1]+s[2]+s[3] , s[4]+s[5]+s[6]+s[7] , s[8] , s[9] , s[10] ,
                s[11] , s[12] , s[13] , s[14], s[15],  "",s[144]+s[145]+s[146]+s[147]+s[148]+s[149]+s[150]+s[151]+s[152]+s[153]+s[154]+s[155]+s[156]+s[157]+s[158]+s[159]));
        for(int n = 16 ; n < 144 ; n++) {
            p.getHead().setSecurityCode(p.getHead().getSecurityCode()+s[n]);//把securitycode填入包
        }

        String pack = message.replaceFirst(p.getHead().headOutput(), "");
        char M2[]=pack.toCharArray();
        String c[]=new String[pack.length()];
        for (int i = 0; i < pack.length(); i++) {
            c[i] = String.valueOf(M2[i]);
        }
        System.out.println("pack的内容为"+pack);
        System.out.println("c的内容是"+c);
        p.setclientID(c[0]+c[1]+c[2]+c[3]);
        p.setRequestID(c[4]+c[5]+c[6]+c[7]);
        p.setTimeStamp(c[8]+c[9]+c[10]+c[11]+c[12]+c[13]+c[14]+c[15]+c[16]
                +c[17]+c[18]+c[19]+c[20]+c[21]);
        System.out.println("解析的packet"+p.toString());


        return p;
    }
    /**
     * 生成会话密钥 K(c,tgs)函数
     * 随机生成会话密钥以字符串形式返回
     * @return  K(c,tgs)密钥
     */
    public static String generateKeyCtgs(){
        String skey = new String();
        for(int i= 0 ;i < 64;i++)
        {
            int x = (int)(Math.random()*2);
            if(x == 2) {
                x = (int)(Math.random()*2);
            }
            skey = skey + Integer.toBinaryString(x);
        }
        return skey;
    }
    /**
     * 生成TicketTGS
     * 按 Kc,tgs|| IDc|| ADc|| IDtgs|| TS2|| Lifetime2 格式封装 Ticket
     * 用 Ktgs 加密
     * 以字符串形式返回

     */
    public static Ticket generateTicketTGS(DataStruct.Packet p,InetAddress inetAddress){  //TGT
        System.out.println("-----正在生成Ticket-----");
        String lifetime = DataStruct.Packet.createLifeTime(2);
        String clientIP = ipToBinary(inetAddress);
        Ticket t = new Ticket(generateKeyCtgs(), p.getHead().getSourceID(), clientIP,
                p.getRequestID(), DataStruct.Packet.createTimestamp(), lifetime);
        System.out.println("生成的Ticket："+t);

        return t;
    }
    public static String ipToBinary(InetAddress ip)
    {
//		String s = "";
//		char M[] = ip.toString().toCharArray();

//		for(int i = 1 ;i<M.length;i++)
//			s += M[i];

        byte[] b=ip.getAddress();
//        System.out.println(b[0]);
//        System.out.println(b[1]);
//        System.out.println(b[2]);
        //  s = s+b[0];
//        System.out.println(s);
        long l= b[0]<<24L & 0xff000000L|
                b[1]<<16L & 0xff0000L  |
                b[2]<<8L  &  0xff00L   |
                b[3]<<0L  &  0xffL ;

        return Integer.toBinaryString((int)l);
    }

    /**
     * 把str补齐到n位，高位写0
     * @param n
     * @param str 要补齐的字符串
     * @return
     */
    public static String supplement(int n,String str){
        if(n>str.length()) {
            int sl=str.length();//string原长度
            for(int i=0;i<(n-sl);i++) {
                str="0"+str;
            }
        }
        return str;
    }
    /**
     * 打包数据
     * 形成  KeyCtgs||IDtgs||时间戳||生存周期||Tickettgs 包
     * 加密的密文以包形式返回
     * @param clientID client的ID
     * @param IDtgs tgs的ID
     * @param TicketTgs 加密后的tgs票据
     * @return
     */
    public static DataStruct.Packet packData(String clientID, String IDtgs, Ticket TicketTgs){
        DataStruct.Packet p = new DataStruct.Packet();;

        String securityCode = DataStruct.Head.zero(128);
        //发送给client的包 注入数据过程
        /*
        *   private Head head;      //头部  全是二进制01
            private String sessionKey;    //二进制
            private String clientID;        //发送请求的ID
            private String requestID;  //被请求方的ID
            private String timeStamp;  //时间戳
            private String lifeTime;    //ticket有效期
            private Ticket Ticket;    //被请求方的票据
            private Authenticator Auth; //请求方的确认信息*/
        int ticketLength=TicketTgs.ticketOutput().length();
        System.out.println(ticketLength);
        String ticketLengthBinary = Integer.toBinaryString(ticketLength);
        System.out.println(ticketLengthBinary);
        String ticketLengthSuppliment = supplement(16,ticketLengthBinary);
        System.out.println(ticketLengthSuppliment);
        DataStruct.Head h= new DataStruct.Head(clientID,ASID,"0","1","0","1","1","1","1","0", Head.zero(128),ticketLengthSuppliment);
        p.setHead(h);
        p.setSessionKey(TicketTgs.getSessionKey());
        p.setclientID(clientID);                                                        //没有clientID
        p.setRequestID(IDtgs);
        p.setTimeStamp(DataStruct.Packet.createTimestamp());
        p.setLifeTime(TicketTgs.getLifeTime());
        p.setTicket(TicketTgs);
        System.out.println(p);

        return p;
    }

    public static void main(String[] args) throws IOException {
        //DBconncet.connect();
        //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket = new ServerSocket(1233);
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        Socket socket = null;
        //2.调用accept()等待客户端连接
        System.out.println("~~~AS服务端已就绪，等待客户端接入~，服务端ip地址: " + "192.198.43.233");
        socket = serverSocket.accept();
        //3.连接后获取输入流，读取客户端信息
        InputStream is=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        OutputStream os=null;
        PrintWriter pw=null;
        is = socket.getInputStream();     //获取输入流
        isr = new InputStreamReader(is,"UTF-8");
        br = new BufferedReader(isr);
        String info = null;
        String message2 =null;
        while((info=br.readLine())!=null){//循环读取客户端的信息
            System.out.println("客户端发送过来的信息" + info);
            message2=info;
            System.out.println("接收到的报文为"+message2);

        }
        info=br.readLine();
        socket.shutdownInput();//关闭输入流

        Packet packetFromClient=packAnalyse(message2);
        System.out.println("packet from client"+packetFromClient.toString());
        //发送
        // 建立连接后获得输出流
        OutputStream outputStream = socket.getOutputStream();

        //生成ticket
        Ticket tgs = generateTicketTGS(packetFromClient,address);
        //System.out.println("tgs"+tgs);
        //System.out.println("?"+packetFromClient.getclientID());//duide
        Packet packetToClient = packData(packetFromClient.getclientID(),TGSID,tgs);

       // System.out.println("packet to client"+packetToClient);
        //String message = packetToAS.toString()+"\n"+Client.packetToBinary(packetToAS);
        String messageToClient = packetToClient.getHead().headOutput()+packetToClient.packageOutput();
        System.out.println("message to client"+messageToClient);
        socket.getOutputStream().write(messageToClient.getBytes("UTF-8"));

        //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
        socket.shutdownOutput();
        socket.close();


    }

}
