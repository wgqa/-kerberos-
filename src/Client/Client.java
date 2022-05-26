package Client;

import DataStruct.Authenticator;
import DataStruct.Head;
import DataStruct.Ticket;
import jdk.swing.interop.SwingInterOpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    public final static String clientID = "clid";
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



    public static String StringToBinary(String string)
    {
        char M[] = string.toCharArray();
        //System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
        int M1[] = new int[M.length];
        String tmp = new String();

        String s ="";  //进行二进制的累加
        for(int i=0;i<M.length;i++)
        {
            if (Character.isDigit(M[i])){  // 判断是否是数字
                M1[i] = Integer.parseInt(String.valueOf(M[i]));
            }
            else {
                System.err.println("String转Binary出错，并不是数字");
            }

            tmp = supplement(4, Integer.toBinaryString(M1[i]));
            //每一位都转成了二进制
            s = s + tmp; //加入string中
        }
        return s;
    }
//Packet{head=Head{destID='tgid', sourceID='clid', existLogin='0', existSessionKey='0', existClientID='0', existRequstID='1', existTS='0', existLifeTime='0', existTicket='1', existAuthenticator='1', securityCode='00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', expend='0000000010000100'}, sessionKey='', clientID='', requestID='0011', timeStamp='', lifeTime='', Ticket=Ticket{sessionKey='1100011110111010011001101000011100000100010001001011110110000001', ID='wqre', IP='11000000101010000101001100000001', requestID='tgid', timeStamp='20220523143426', lifeTime='20220523143426'}, Auth=Authenticator{clientID='clid', clientIP='11000000101010000011100000000001', timeStamp='20220523143219'}}
    //email 在WELCOME中进行的操作，我是放在connnection中进行的
    public static String BinaryToString(String string)
    {
        int length = string.length();
        if(length%4 != 0) {
            System.err.println("Client二进制转十进制时，二进制长度有误");
        }
        char C[] = string.toCharArray();
        String M[] = new String[length/4];
        for(int i=0;i<M.length;i++){
            M[i] = "";
        }
        //System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
        int M1[] = new int[length/4];
        String s ="";  //进行二进制的累加
        for(int i1=0;i1<length;i1++)
        {
            M[i1/4] = M[i1/4]+C[i1];
            if(i1%4 == 3) {
                M1[i1/4] = Integer.parseInt(M[i1/4],2);
                s = s + M1[i1/4]; //加入string中
            }
        }
        return s;
    }
    public static DataStruct.Authenticator generateAuth(String ID,String IP,String k){

        //String timestamp = DataStruct.Packet.createTimestamp();//这里为什么会错
        DataStruct.Authenticator a= new Authenticator(ID,IP,StringToBinary(DataStruct.Packet.createTimestamp()));
        System.out.println("auth的内容"+a);
        //String cipher = Des.DES.encrypt(a.AuthOutput(), k);
        //加密后放入a中
//        a.setClientID("");
//        a.setClientIP("");
//        a.setTimeStamp("");
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

    public static DataStruct.Packet clientToAS(String clientID, String TGSID){
        DataStruct.Packet p= new DataStruct.Packet();
        /*
        private Head head;      //头部  全是二进制01
        private String sessionKey;    //0
        private String clientID;
        private String requestID;  //被请求方的ID
        private String timeStamp;  //时间戳
        private String lifeTime;    //ticket有效期
        private Ticket Ticket;    //被请求方的票据
        private Authenticator Auth; //请求方的确认信息
        * */
        DataStruct.Head h= new DataStruct.Head(ASID,clientID,"0","0","1","1","1","0","0","0", Head.zero(128),Head.zero(16));

        p.setHead(h);
        p.setclientID(clientID);
        p.setRequestID(TGSID);
        p.setTimeStamp(StringToBinary(DataStruct.Packet.createTimestamp()));

        //写在packet中吧
        //SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");//自定义时间戳格式，时间戳要只包含数字不包含符号
        //p.setTimeStamp(date.format(new Date()));
        //p.setTimeStamp(p.createTimestamp());//改成DataStruct.Packet.createTimestamp()更好一点
        return p;
    }

    public static DataStruct.Packet clientToTGS(String clientID, String serverID,DataStruct.Ticket ticketTGS, DataStruct.Authenticator authTGS){
        /*
        private Head head;      //头部  全是二进制01
        private String sessionKey;    //二进制
        private String clientID;        //发送请求的ID
        private String requestID;  //被请求方的ID
        private String timeStamp;  //时间戳
        private String lifeTime;    //ticket有效期
        private Ticket Ticket;    //被请求方的票据
        private Authenticator Auth; //请求方的确认信息*/

        DataStruct.Packet p= new DataStruct.Packet();
        // authTGS就是用 CT_SK加密过的name（id），ip，timestamp
        //先setticket才能获得拓展位的大小
        p.setTicket(ticketTGS);     // 从AS中获得的 TGS 密码加密过的TGS，在as中加密的，此处不做处理
        //拓展位
        String ticketLength = Integer.toBinaryString(p.getTicket().ticketOutput().length());//tickettgs加密后的长度 TGT的长度
        System.out.println("xjz"+ticketLength);
        ticketLength = supplement(16, ticketLength);
        System.out.println("tic 加长后"+ticketLength);
        DataStruct.Head h= new DataStruct.Head(TGSID,clientID,"0","0","0","1","0","0","1","1",Head.zero(128),ticketLength);//number,securityCode,tic);
        p.setHead(h);
        p.setRequestID(SERVERID); //ServerIP 报告中的IP应该改为ID，
        //不需要timestamp和lifetime
        //其他部分是报文头，下面这两个部分是报文内容

        p.setAuth(authTGS);         //将AS收到的包用   Client.generateAuth(p.getID(),clientIP,p2.getSessionKey())
        System.out.println(authTGS.toString());
        return p;
    }

    public static DataStruct.Packet clientToServer(String clientID,DataStruct.Ticket ticketServer, DataStruct.Authenticator authServer) {
        DataStruct.Packet p= new DataStruct.Packet();

        p.setTicket(ticketServer); //Server密码加密 ST 从tgs获取，没有处理
        p.setAuth(authServer);      //CS_SK加密 包括ID IP timestamp ST有效时间   auth从tgs中解析的包的中获取
        //Client.generateAuth(p3.getHead().getDestID(),clientIP,p3.getSessionKey())
        //p.setTimeStamp(p.createTimestamp());
        p.setTimeStamp(StringToBinary(DataStruct.Packet.createTimestamp()));



        //String tic = Integer.toString((p.getTicket().ticketOutput().length())); //tickettgs加密后的长度
        //tic = supplement(16, StringToBinary(tic));
        //String securityCode = DataStruct.Head.zero(128);
        String ticketLength = Integer.toBinaryString(p.getTicket().ticketOutput().length());//tickettgs加密后的长度 TGT的长度
        ticketLength = supplement(16, ticketLength);
        DataStruct.Head h= new DataStruct.Head(TGSID,clientID,"0","0","0","0","0","0","1","1",Head.zero(128),ticketLength);//number,securityCode,tic);
        p.setHead(h);

        return p;
    }

    //先在完成包的初步解析，并将其分类，然后调用子方法求出剩下的部分
    public static DataStruct.Packet packetAnalyse(String message){
        System.out.println("-----开始解析包-----");

        DataStruct.Packet p = new DataStruct.Packet();

        //System.out.println("头错了吗？"+p.getHead().toString());
        int headLength = p.getHead().headOutput().length();

        char M[] = message.toCharArray();
        String s[] = new String[headLength];
        //System.out.println("message?"+message);
        for (int i = 0; i < headLength; i++) {
            s[i] = String.valueOf(M[i]);
        }

        /*Head部分全部解析*/
        p.setHead(new DataStruct.Head( s[0]+s[1]+s[2]+s[3] , s[4]+s[5]+s[6]+s[7] , s[8] , s[9] , s[10] ,
                s[11] , s[12] , s[13] , s[14], s[15],  "",s[144]+s[145]+s[146]+s[147]+s[148]+s[149]+s[150]+s[151]+s[152]+s[153]+s[154]+s[155]+s[156]+s[157]+s[158]+s[159]));
        for(int n = 16 ; n < 144 ; n++) {
            p.getHead().setSecurityCode(p.getHead().getSecurityCode()+s[n]);//把securitycode填入包
        }

        //System.out.println("p解析了头"+p.toString());
        System.out.println("message的长度"+message.length());
        System.out.println("空指针在那？");
        System.out.println("head的内容是："+p.getHead().toString());
        String pack = message.replaceFirst(p.getHead().headOutput(), "");
        System.out.println("pack的内容是"+pack);
        //System.out.println("pack"+pack); //对的
        //通过MD5验证是否被修改
            if(p.getHead().equals(null)){
                System.out.println("head是null");
            }
            if(p.getHead().getExistLifeTime().equals("1")&&p.getHead().getExistTicket().equals("1")) {
                //说明是AS发的
                //package解密 用client私钥
                //String m = message.replaceFirst(p.getHead().headOutput(),"");
                System.out.println("pack长度"+pack.length());
                //System.out.println("m的长度"+m.length());
                //包去掉了头



                char C[] = pack.toCharArray();
                //System.out.println("c的内容"+C[0]+C[1]+C[2]);

                //解包
				/*
				* 	private String sessionKey;    64
					private String ID;        //发送请求的ID 4
					private String requestID;  //被请求方的ID 4
					private String timeStamp;  //时间戳
					private String lifeTime;    //ticket有效期
					private Ticket Ticket;    //被请求方的票据
					private Authenticator Auth; //请求方的确认信息
					* */
                //System.out.println(p.getHead().getExpend());
                //错System.out.println(BinaryToString(p.getHead().getExpend()));
                int tic = Integer.parseInt(p.getHead().getExpend(),2);//获取拓展位的内容 AS发的拓展位是？发的是ticket的长度
                //System.out.println("tic长度"+tic); √
                DataStruct.Ticket ticket = new Ticket();
                for(int i = 0;i<pack.length();i++)
                {	//68（64packet的sessionkey+4位requestID)+56+56 + 64 +
                    if(i<64)  //分位段解析内容  AS发的包有64位的sessionkey
                        p.setSessionKey(p.getSessionKey()+C[i]);//可以去掉get，因为本来就是空的
                    else if(i<64+4) //四位requestID
                        p.setclientID(p.getclientID()+C[i]);
                    else if(i<64+4+4) //四位requestID
                        p.setRequestID(p.getRequestID()+C[i]);
                    else if(i<64+4+4+56)//56位timestamp
                        p.setTimeStamp(p.getTimeStamp()+C[i]);
                    else if(i<64+4+4+56+56){//56位的lifetime
                        p.setLifeTime(p.getLifeTime()+C[i]);
                        //System.out.println("这里的i是？"+i);
                    }
                    else if(i<64+4+4+56+56+tic+2){//ticket部分
                        //System.out.println("zhelide id"+ i);
                        if(i<68+4+56+56+64)  //ticket部分首先是64位的sessionkey
                            ticket.setSessionKey(ticket.getSessionKey()+C[i]);
                        else if(i<68+4+56+56+64+4)//ticket中的id
                            ticket.setID(ticket.getID()+C[i]);
                        else if(i<68+4+56+56+64+4+32) //32位IP
                            ticket.setIP(ticket.getIP()+C[i]);
                        else if(i<68+4+56+56+64+4+32+4) //
                            ticket.setRequestID(ticket.getRequestID()+C[i]);
                        else if(i<68+4+56+56+64+4+32+4+56)
                            ticket.setTimeStamp(ticket.getTimeStamp()+C[i]);
                        else
                            ticket.setLifeTime(ticket.getLifeTime()+C[i]);
                    }
                    else {
                        System.out.println("这里的i是多少？xja"+i);
                        System.out.println("报错时解析了多少"+p.toString());
                        System.err.println("分析发现AS发过来的package长度有误，请检查！！");
                        System.exit(0);
                    }
                }
                p.setTicket(ticket);



            }
            else if(p.getHead().getExistTicket().equals("1")){
                System.out.println("解析的是TGS的包");
                //说明是TGS发的
                //package解密 用Ktgs，c 传参进来k

/*              从tgs中得到的报文包括：
                private Head head;      //有
                private String sessionKey;    //有
                private String clientID;        //无
                private String requestID;  //无
                private String timeStamp;  //有
                private String lifeTime;    //ticket有效期
                private Ticket Ticket;    //有 加密后的v的票据
                private Authenticator Auth; //请求方的确认信息 没有 ， client 发给tgs的包有，需要解析，tgs发给client的包不包含
*/
                //说明是TGS发的
                //package解密 用Ktgs，c 传参进来k
                String m = message.replaceFirst(p.getHead().headOutput(),"");
                //System.out.println("tgs解析里的m:"+m);这里是对的
                //message = DES.decrypt(m, k);

                char C[] = m.toCharArray();
//                for(int k=0;k<C.length;k++){
//                    System.out.println("c["+k+"]="+C[k]);
//                }
                //解包
                int tic = Integer.parseInt(p.getHead().getExpend(),2);
                DataStruct.Ticket ticket = new Ticket();
                for(int i = 0;i<m.length();i++)
                {	//68（64packet的sessionkey+4位requestID)+56+56 + 64 +
                    if(i<64)  //分位段解析内容  AS发的包有64位的sessionkey
                        p.setSessionKey(p.getSessionKey()+C[i]);//可以去掉get，因为本来就是空的
                    else if(i<64+56)//56位timestamp
                        p.setTimeStamp(p.getTimeStamp()+C[i]);
                    else if(i<64+56+tic+1){//ticket部分
                        if(i<64+56+64)  //ticket部分首先是64位的sessionkey
                            ticket.setSessionKey(ticket.getSessionKey()+C[i]);
                        else if(i<64+56+64+4)//ticket中的id
                            ticket.setID(ticket.getID()+C[i]);
                        else if(i<64+56+64+4+32) //32位IP
                            ticket.setIP(ticket.getIP()+C[i]);
                        else if(i<64+56+64+4+32+4) //
                            ticket.setRequestID(ticket.getRequestID()+C[i]);
                        else if(i<64+56+64+4+32+4+56)
                            ticket.setTimeStamp(ticket.getTimeStamp()+C[i]);
                        else
                            ticket.setLifeTime(ticket.getLifeTime()+C[i]);
                    }
                    else {
                        System.out.println("报错时解析了多少"+p.toString());
                        System.err.println("分析发现TGS发过来的package长度有误，请检查！！");
                        System.exit(0);
                    }
                }
                p.setTicket(ticket);


            }
            else if(p.getHead().getExistTS().equals("1")){
                //说明是V发的
                //package解密 用Kv，c 传参进来k//没有要解析的东西

            }
            System.out.println("分析的包："+ p);
            return p ;
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



//    public static String packetToBinary(DataStruct.Packet p)
//    {
//        String s = new String();
//        String packetToSend = "";
//        String lt = p.getLifeTime();
//        String ts = p.getTimeStamp();
//
//
//        if(p.getHead().getExistTS().equals("1")) {
//            s = StringToBinary(p.getTimeStamp());
//            p.setTimeStamp(s);
//        }
//
//        if(p.getHead().getExistLifeTime().equals("1")) {
//            s = StringToBinary(p.getLifeTime());
//            p.setLifeTime(s);
//        }
//
//        packetToSend = p.getHead().headOutput()+p.packageOutput();
//        p.setLifeTime(lt);
//        p.setTimeStamp(ts);
//        return packetToSend;
//    }

    //注册
    public static DataStruct.Packet signup2(String id,String pw){
        DataStruct.Packet p= new DataStruct.Packet();


        p.setclientID(supplement(16,id));
        p.setRequestID(StringToBinary(pw));
//		p.setLifeTime();
        //有问题




        DataStruct.Head h= new DataStruct.Head(ASID,"0000","1","1","1","1","0","0","0","0","","");
        p.setHead(h);

        return p;
    }


    //send
    public static boolean send(String messageToServic, Socket socket) throws IOException {
        OutputStream os=null;
        try {
            os = socket.getOutputStream();
            os.write(messageToServic.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            socket.shutdownOutput();
            os.flush();
        }

        return true;
    }
    //receive
    public static String receive(Socket socket){
        InputStream is=null;
        String messageFomeService = "";
        try {
            is = socket.getInputStream();
            //4.对获取的输入流进行的操作

            byte [] b = new byte[20];
            int len;
            while((len = is.read(b))!= -1){
                String str = new String(b,0,len);
                messageFomeService+=str;
            }
            System.out.println("收到："+messageFomeService);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //5.关闭相应的流以及Socket,ServerSocket的对象

            try {
                socket.shutdownInput();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return messageFomeService;
    }

}

//调用try-catch时需要将其将其放在方法中，然后调用该方法，类中不可以直接进行逻辑操作，只可以实例化对象或者定义初始化变量。
//client负责封装和解析包 connection负责收发