package DES;

public class test {
    public static void main(String[] args) {
        String sKey = "0011000100110010001100110011010000110101001101100011011100111000";
        String s="001111";
        s=DES.encrypt(s,sKey);
        System.out.println(s);
        s=DES.decrypt(s,sKey);
        System.out.println(s);
    }
}
