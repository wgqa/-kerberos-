package Client;

import DataStruct.Authenticator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    public final static String clientID = "1111";
    public final static  String clientIP = "192.168.43.174";
    private final static String ASID = "0001";
    public final static String TGSID = "0010"; //因为connection中要用，所以改成public
    public final static String SERVERID = "0011";
    private final String SeverID = "0011";
    public static InetAddress  localAdress = null;
    public static void getLocalAddress(){
        try {
            localAdress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    //public static String ClientIP= localAdress.toString();
    public static String userName = "sjz"; //实际上应该从登录界面获取
    public static String ASIP ="192.168.43.233";
    public static String TGSIP ="192.168.43.140";
    public static String ServerIP = "192.168.43.140";

    //email 在WELCOME中进行的操作，我是放在connnection中进行的

    public static DataStruct.Authenticator generateAuth(String ID,String AD,String k){

        String timestamp = DataStruct.Packet.createTimestamp();
        DataStruct.Authenticator a= new Authenticator(ID,AD,StringToBinary(timestamp));
        System.out.println(a);
        //String cipher = Des.DES.encrypt(a.AuthOutput(), k);
        //加密后放入a中
        a.setClientID("");
        a.setClientIP("");
        a.setTimeStamp("");
       // char M[] = cipher.toCharArray();

       /* for(int i = 0;i<cipher.length();i++)
        {
            if(i<4) {
                a.setClientID(a.getClientID()+M[i]);
            }
            else if(i<40) {
                a.setClientIP(a.getClientIP()+M[i]);
            }
            else {
                a.setTimeStamp(a.getTimeStamp()+M[i]);
            }
        }*/
        return a;
    }

    public static DataStruct.Packet clientToAS(String clientID, String TGSID){
        DataStruct.Packet p= new DataStruct.Packet();

        p.setclientID(clientID);
        p.setRequestID(TGSID);
       // p.setTimeStamp(DataStruct.Packet.Create_TS());
        DataStruct.Head h= new DataStruct.Head(ASID,clientID,"0","0","1","1","1","0","0","0","","");
        p.setHead(h);
        //写在packet中吧
        //SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");//自定义时间戳格式，时间戳要只包含数字不包含符号
        //p.setTimeStamp(date.format(new Date()));
        //p.setTimeStamp(p.createTimestamp());//改成DataStruct.Packet.createTimestamp()更好一点
        p.setTimeStamp(DataStruct.Packet.createTimestamp());
        return p;
    }

    public static DataStruct.Packet clientToTGS(String clientID, String serverID,DataStruct.Ticket ticketTGS, DataStruct.Authenticator authTGS){
        DataStruct.Packet p= new DataStruct.Packet();
        p.setRequestID(SERVERID); //ServerIP 报告中的IP应该改为ID，

        //其他部分是报文头，下面这两个部分是报文内容
        p.setTicket(ticketTGS);     // TGS 密码加密过的TGS，在as中加密的，此处不做处理
        p.setAuth(authTGS);         //将AS收到的包用   Client.generateAuth(p.getID(),clientIP,p2.getSessionKey())
        // authTGS就是用 CT_SK加密过的name（id），ip，timestamp

        //拓展位
        String tic = Integer.toString((p.getTicket().ticketOutput().length())); //tickettgs加密后的长度 TGT的长度
        //tic TGT的长度
        tic = supplement(16, StringToBinary(tic));

        DataStruct.Head h= new DataStruct.Head(TGSID,clientID,"0","0","0","1","0","0","1","1","","");//number,securityCode,tic);
        p.setHead(h);
        return p;
    }

    public static DataStruct.Packet clientToServer(String clientID,DataStruct.Ticket ticketServer, DataStruct.Authenticator authServer) {
        DataStruct.Packet p= new DataStruct.Packet();

        p.setTicket(ticketServer); //Server密码加密 ST
        p.setAuth(authServer);      //CS_SK加密 包括ID IP timestamp ST有效时间
        //Client.generateAuth(p3.getHead().getDestID(),clientIP,p3.getSessionKey())
        //p.setTimeStamp(p.createTimestamp());
        p.setTimeStamp(DataStruct.Packet.createTimestamp());



        //String tic = Integer.toString((p.getTicket().ticketOutput().length())); //tickettgs加密后的长度
        //tic = supplement(16, StringToBinary(tic));
        //String securityCode = DataStruct.Head.zero(128);
        DataStruct.Head h= new DataStruct.Head(TGSID,clientID,"0","0","0","0","0","0","1","1","","");//number,securityCode,tic);
        p.setHead(h);

        return p;
    }

    //先在完成包的初步解析，并将其分类，然后调用子方法求出剩下的部分
    public static DataStruct.Packet packetAnalyse(String message){
       //sort部分

        System.out.println("-----开始解析包-----");
        DataStruct.Packet p = new DataStruct.Packet();
        int headLength = p.getHead().headOutput().length();

        char M[] = message.toCharArray();
        String s[] = new String[headLength];
        for(int i = 0;i<headLength;i++)
        {
            s[i] = String.valueOf(M[i]);
        }

        String pack = message.replaceFirst(p.getHead().headOutput(), "");

       return p;
   }

    //将packet转换成二进制流数据 即01字符串
   //转换成二进制不是把数据包转换成二进制
   //而是把时间戳和生命周期这种非二进制数字改成二进制
    //其他属性本身就可以用二进制表示
    //将用户名改为16位二进制？ 在前面加0
    public static String supplement(int n,String str){
        if(n>str.length()) {
            int sl=str.length();//string原长度
            for(int i=0;i<(n-sl);i++) {
                str="0"+str;
            }
        }
        return str;
    }

    public static String StringToBinary(String string)//需要自己修改
    {
        int length = string.length();
        char M[] = string.toCharArray();
        //System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
        int M1[] = new int[M.length];
        String tmp = new String();

        String s ="";  //进行二进制的累加
        for(int i=0;i<M.length;i++)
        {
            M1[i] = M[i]-'\0'; //每一位都是int了，现在开始转换二进制

            tmp = supplement(8, Integer.toBinaryString(M1[i]));


            //每一位都转成了二进制
            s = s + tmp; //加入string中
        }
        return s;
    }

    public static String packetToBinary(DataStruct.Packet p)
    {
        String s = new String();
        String packetToSend = "";
        String lt = p.getLifeTime();
        String ts = p.getTimeStamp();


        if(p.getHead().getExistTimeStamp().equals("1")) {
            s = StringToBinary(p.getTimeStamp());
            p.setTimeStamp(s);
        }

        if(p.getHead().getExistLifeTime().equals("1")) {
            s = StringToBinary(p.getLifeTime());
            p.setLifeTime(s);
        }

        packetToSend = p.getHead().headOutput()+p.packageOutput();
        p.setLifeTime(lt);
        p.setTimeStamp(ts);
        return packetToSend;
    }

    //注册
    public static DataStruct.Packet signin2(String id,String pw){
        DataStruct.Packet p= new DataStruct.Packet();


        p.setclientID(supplement(16,id));
        p.setRequestID(StringToBinary(pw));
//		p.setLifeTime();
        //有问题




        DataStruct.Head h= new DataStruct.Head(ASID,"0000","1","1","1","1","0","0","0","0","","");
        p.setHead(h);

        return p;
    }
}

//调用try-catch时需要将其将其放在方法中，然后调用该方法，类中不可以直接进行逻辑操作，只可以实例化对象或者定义初始化变量。
//client负责封装和解析包 connection负责收发