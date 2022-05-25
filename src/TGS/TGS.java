//package TGS;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.Socket;
//
//public class TGS {
//    /*
//    发送包
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
//分析包
// */
//    public DataStruct.Packet packAnalyse(String message){
//        System.out.println("-----开始解析包-----");
//        DataStruct.Package p = new DataStruct.Package();
//        DataStruct.Ticket ticket = new Ticket();
//        DataStruct.Authenticator auth = new Authenticator();
//
//        //分离出头部
//        int headLength = p.getHead().headOutput().length();
//        char M[] = message.toCharArray();
//        String s[] = new String[headLength];
//        for(int i = 0;i<headLength;i++)
//        {
//            s[i] = String.valueOf(M[i]);
//        }
//        p.setHead(new DataStruct.Head( s[0]+s[1]+s[2]+s[3] , s[4]+s[5]+s[6]+s[7] , s[8] , s[9] , s[10] ,
//                s[11] , s[12] , s[13] , s[14], s[15], s[16]+s[17]+s[18]+s[19] ,  "",
//                s[148]+s[149]+s[150]+s[151]+s[152]+s[153]+s[154]+s[155]+s[156]+s[157]+s[158]+s[159]+s[160]+s[161]+s[162]+s[163] ));
//        for(int n = 20 ; n < 148 ; n++) {
//            p.getHead().setSecurityCode(p.getHead().getSecurityCode()+s[n]);
//        }
//        System.out.println(p.getHead());
//        String pack = message.replaceFirst(p.getHead().headOutput(), "");
//
//        //验证消息验证码
//        if(DataStruct.Head.MD5(pack).equals(p.getHead().getSecurityCode())) {
//
//            //分离出包内容和Ticket和Auth
//            String messageT = "" , messageA = "";
//            int tic = Integer.parseInt(BinaryToString(p.getHead().getExpend()));//加密后tic的长度
//            for(int i = headLength;i<message.length();i++)
//            {
//                if(i<headLength+4)
//                    p.setRequestID(p.getRequestID()+M[i]);
//                else if(i<headLength+4+tic){
//                    messageT = messageT + M[i];
//                }
//                else {
//                    messageA = messageA + M[i];
//                }
//
//            }
//
//            //Ticket解密 用tgs私钥
//            RSA.rsa rsa = new RSA.rsa();
//            System.out.println("密文:"+messageT);
//            messageT = rsa.decrypt(messageT, SELFKEY);
//            System.out.println("明文:"+messageT);
//            char T[] = messageT.toCharArray();
//            //解Ticket
//            for(int i = 0;i < messageT.length();i++)
//            {
//                if(i < 64)
//                    ticket.setSessionKey(ticket.getSessionKey()+T[i]);
//                else if(i<68)
//                    ticket.setID(ticket.getID()+T[i]);
//                else if(i<100)
//                    ticket.setIP(ticket.getIP()+T[i]);
//                else if(i<104)
//                    ticket.setRequestID(ticket.getRequestID()+T[i]);
//                else if(i<104+56)
//                    ticket.setTimeStamp(ticket.getTimeStamp()+T[i]);
//                else if(i<104+56+56)
//                    ticket.setLifeTime(ticket.getLifeTime()+T[i]);
//                else {
//                    System.err.println("分析发现TGS收到的package长度有误，请检查！！");
//                    System.exit(0);
//                }
//            }
//
//            //Auth解密，用Ticket中的sessionkey解密
//            messageA = DES.decrypt(messageA, ticket.getSessionKey());
//            char A[] = messageA.toCharArray();
//            for(int i = 0;i < messageA.length();i++)
//            {
//                if(i<4)
//                    auth.setClientID(auth.getClientID()+A[i]);
//                else if(i<36)
//                    auth.setClientIP(auth.getClientIP()+A[i]);
//                else if(i<36+56)
//                    auth.setTimeStamp(auth.getTimeStamp()+A[i]);
//                else {
//                    System.err.println("分析发现TGS收到的package长度有误，请检查！！");
//                    System.exit(0);
//                }
//            }
//            ticket.setTimeStamp(BinaryToString(ticket.getTimeStamp()));
//            ticket.setLifeTime(BinaryToString(ticket.getLifeTime()));
//            auth.setTimeStamp(BinaryToString(auth.getTimeStamp()));
//            p.setTicket(ticket);
//            p.setAuth(auth);
//
//            System.out.println("分析的包："+ p);
//            return p;
//        }
//        else {
//            System.err.println("TGS收到的包有误");
//            return null;
//        }
//    }
//
//
//}
