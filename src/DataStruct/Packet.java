package DataStruct;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Packet {

    //packet 把所有报文中所含的数据类型都包括了，对应的填充数据，没有的项置空
    private Head head;      //头部  全是二进制01
    private String sessionKey;    //二进制
    private String clientID;        //发送请求的ID
    private String requestID;  //被请求方的ID
    private String timeStamp;  //时间戳
    private String lifeTime;    //ticket有效期
    private Ticket Ticket;    //被请求方的票据
    private Authenticator Auth; //请求方的确认信息
    //使用MD5对包的除了头以外的部分进行加密

    public Packet(Head head, String sessionKey, String ID, String requestID, String timeStamp, String lifeTime, DataStruct.Ticket ticket, Authenticator auth) {
        this.head = head;
        this.sessionKey = sessionKey;
        this.clientID = clientID;
        this.requestID = requestID;
        this.timeStamp = timeStamp;
        this.lifeTime = lifeTime;
        Ticket = ticket;
        Auth = auth;
    }

    //无参构造函数
    public Packet() {
        this.head = new Head();
        this.sessionKey = "";
        this.clientID = "";
        this.requestID = "";
        this.timeStamp = "";
        this.lifeTime = "";
        Ticket = new Ticket();
        Auth = new Authenticator();
    }

    // toString 是展示在ui上的，output是封装到包里发送的
    @Override
    public String toString() {
        return "Packet{" +
                "head=" + head +
                ", sessionKey='" + sessionKey + '\'' +
                ", clientID='" + clientID + '\'' +
                ", requestID='" + requestID + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", lifeTime='" + lifeTime + '\'' +
                ", Ticket=" + Ticket +
                ", Auth=" + Auth +
                '}';
    }

    public String packageOutput() {
        return sessionKey + clientID + requestID + timeStamp + lifeTime + Ticket.ticketOutput() + Auth.AuthOutput();
    }

    public static String createTimestamp(){  //加不加static的区别是：
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");//自定义时间戳格式，时间戳要只包含数字不包含符号
        return(date.format(new Date()));
    }
    //static的作用是，将属性或者方法修饰为该类的共享成员，即这个属性或方法是该类实例化的所有对象之间共享的，不为某个实例对象所有，static属性或方法是存储在内存的公共区，一个类中，一个static变量只会有一个内存空间，虽然有多个类实例，但这些类实例中的这个static变量会共享同一个内存空间。
    //生成时间戳与实例无关，应该用static
    public static String createLifeTime(int time){
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");//自定义时间戳格式，时间戳要只包含数字不包含符号
        Date now = new Date();
        Date afterDate = new Date(now .getTime() + time*60000);
        return(date.format(new Date()));
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public void setclientID(String ID) {
        this.clientID = ID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setLifeTime(String lifeTime) {
        this.lifeTime = lifeTime;
    }

    public void setTicket(DataStruct.Ticket ticket) {
        Ticket = ticket;
    }

    public void setAuth(Authenticator auth) {
        Auth = auth;
    }

    public Head getHead() {
        return head;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getclientID() {
        return clientID;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getLifeTime() {
        return lifeTime;
    }

    public DataStruct.Ticket getTicket() {
        return Ticket;
    }

    public Authenticator getAuth() {
        return Auth;
    }
}
