package Client;

import DataStruct.Head;

public class test {
    public static void main(String[] args) {
//        DataStruct.Packet p= new DataStruct.Packet();
//
//
//        // p.setTimeStamp(DataStruct.Packet.Create_TS());
//        DataStruct.Head h= new DataStruct.Head("0000","1111","0","0","1","1","1","0","0","0","128位","0");
//        p.setHead(h);
//        System.out.println(p);
        System.out.println(Head.zero(128));
    }
}
