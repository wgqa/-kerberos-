package DataStruct;

public class Ticket {
    private String sessionKey;
    private String ID;       //发送请求的ID
    private String IP;       //发送请求的IP
    private String requestID; //被请求方的ID
    private String timeStamp; //时间戳
    private String lifeTime;//ticket有效期


    public Ticket(String sessionKey, String ID, String IP, String requestID, String timeStamp, String lifeTime) {
        this.sessionKey = sessionKey;
        this.ID = ID;
        this.IP = IP;
        this.requestID = requestID;
        this.timeStamp = timeStamp;
        this.lifeTime = lifeTime;
    }

    public Ticket() {
        this.sessionKey = "";
        this.ID = "";
        this.IP = "";
        this.requestID = "";
        this.timeStamp = "";
        this.lifeTime = "";
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "sessionKey='" + sessionKey + '\'' +
                ", ID='" + ID + '\'' +
                ", IP='" + IP + '\'' +
                ", requestID='" + requestID + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", lifeTime='" + lifeTime + '\'' +
                '}';
    }

    public String ticketOutput() {
        return  sessionKey + ID + IP + requestID + timeStamp + lifeTime;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIP(String IP) {
        this.IP = IP;
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

    public String getSessionKey() {
        return sessionKey;
    }

    public String getID() {
        return ID;
    }

    public String getIP() {
        return IP;
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
}
