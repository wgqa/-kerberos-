package DataStruct;



public class Head {
    private String destID;   //目的ID
    private String sourceID;   //源ID
    private String existLogin; //是否是注册（如果是注册，则seeeionkey是keyc，ID是账号，后面是密码）
    private String existSessionKey;  //是否存在sessionKey
    private String existClientID;    //是否存在请求方ID
    private String existRequstID;   //是否存在被请求方ID
    private String existTimeStamp;        //是否存在时间戳
    private String existLifeTime;  //  是否存在生存期限
    private String existTicket;    //是否存在票据
    private String existAuthenticator;   //是否存在认证


    private String securityCode;   //验证码
    private String expend;   //扩展位 AS的expand懂了 是ticket的长度

    public static String zero(int a) {//这个zero函数是干什么的		返回长度为a的全0字符串
        String s="";
        for(int i=0;i<a;i++) {
            s+="0";
        }
        return s;
    }
    //这种构造函数叫啥来着


    public Head(String destID, String sourceID, String existLogin, String existSessionKey, String existClientID, String existRequstID, String existTimeStamp, String existLifeTime, String existTicket, String existAuthenticator, String securityCode, String expend) {
        this.destID = destID;
        this.sourceID = sourceID;
        this.existLogin = existLogin;
        this.existSessionKey = existSessionKey;
        this.existClientID = existClientID;
        this.existRequstID = existRequstID;
        this.existTimeStamp = existTimeStamp;
        this.existLifeTime = existLifeTime;
        this.existTicket = existTicket;
        this.existAuthenticator = existAuthenticator;
        this.securityCode = securityCode;
        this.expend = expend;
    }

    public Head() {
        destID = "0000";
        sourceID = "0000";
        existLogin = "0";
        existSessionKey = "0";
        existClientID = "0";
        existRequstID = "0";
        existTimeStamp = "0";
        existLifeTime = "0";
        existTicket = "0";
        existAuthenticator = "0";
        securityCode = zero(128);
        expend = "0000000000000000";
    }

    @Override
    public String toString() {
        return "Head{" +
                "destID='" + destID + '\'' +
                ", sourceID='" + sourceID + '\'' +
                ", existLogin='" + existLogin + '\'' +
                ", existSessionKey='" + existSessionKey + '\'' +
                ", existClientID='" + existClientID + '\'' +
                ", existRequstID='" + existRequstID + '\'' +
                ", existTimeStamp='" + existTimeStamp + '\'' +
                ", existLifeTime='" + existLifeTime + '\'' +
                ", existTicket='" + existTicket + '\'' +
                ", existAuthenticator='" + existAuthenticator + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", expend='" + expend + '\'' +
                '}';
    }



    public String headOutput() {
        return destID + sourceID + existLogin + existSessionKey + existClientID + existRequstID + existTimeStamp + existLifeTime
                + existTicket + existAuthenticator+securityCode+expend;
    }

    public void setDestID(String destID) {
        this.destID = destID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public void setExistLogin(String existLogin) {
        this.existLogin = existLogin;
    }

    public void setExistSessionKey(String existSessionKey) {
        this.existSessionKey = existSessionKey;
    }

    public void setExistID(String existClientID) {
        this.existClientID = existClientID;
    }

    public void setExistRequstID(String existRequstID) {
        this.existRequstID = existRequstID;
    }

    public void setExistTimeStamp(String existTS) {
        this.existTimeStamp = existTimeStamp;
    }

    public void setExistLifeTime(String existLifeTime) {
        this.existLifeTime = existLifeTime;
    }

    public void setExistTicket(String existTicket) {
        this.existTicket = existTicket;
    }

    public void setExistAuthenticator(String existAuthenticator) {
        this.existAuthenticator = existAuthenticator;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public void setExpend(String expend) {
        this.expend = expend;
    }

    public String getDestID() {
        return destID;
    }

    public String getSourceID() {
        return sourceID;
    }

    public String getExistLogin() {
        return existLogin;
    }

    public String getExistSessionKey() {
        return existSessionKey;
    }

    public String getExistClientID() {
        return existClientID;
    }

    public String getExistRequstID() {
        return existRequstID;
    }

    public String getExistTimeStamp() {
        return existTimeStamp;
    }

    public String getExistLifeTime() {
        return existLifeTime;
    }

    public String getExistTicket() {
        return existTicket;
    }

    public String getExistAuthenticator() {
        return existAuthenticator;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getExpend() {
        return expend;
    }
}
