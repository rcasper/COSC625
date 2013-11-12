package Clamshell;

public class CryptoTester {

    private static final String delimeter = "   "; // entry data delimeter
    
    public static void main(String argv[])
    {
        System.out.println(byteToString(hexStringToByteArray("616161616161afd16161616161616161")));
        AES aes = new AES("F1C391E72531812FF1C391E72531812F");
        String enc = aes.encrypt("616161616161afd16161616161616161");
        String dec = aes.decrypt(enc);
        
        System.out.println(byteToString(hexStringToByteArray(dec)));
        /*
        String masterPass = "7061737300000000706173730000000070617373000000007061737300000000";
        String cipher = "";
        
        // AES 256 demonstration
        // For AES 256, the key length must be 64 hex chars long (64 * 4 = 256 bits)
        // Each plaintext segment must be 32 hex characters long
        Yaes aes = new Yaes(masterPass);
        //System.out.println("Randomly-generated IV:\n"+e.IV);
        cipher = aes.encryptString("616161616161afd16161616161616161", "F1C391E72531812FF1C391E72531812F");
        System.out.println("AES encryption:\n"+cipher);
        cipher = aes.decryptString(cipher, "F1C391E72531812FF1C391E72531812F");
        System.out.println("AES decryption:\n"+cipher);*/

    }
    
    private static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
    
    private static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
    // convert back to plaintext
    public static String byteToString(byte[] in){
        String output = "";
        for(int a = 0;a < in.length;a++)
        {
            output += (char)in[a];
        }
        return output;
    }
    // convert plaintext string to hexadecimal (length is always x2 plaintext)
    private static String stringToHex(String s){
        
        StringBuilder buf = new StringBuilder(200);
        for (char ch : s.toCharArray()) {
            buf.append(String.format("%02x", (int) ch));
        }
        return buf.toString();
        //return byteArrayToHexString(s.getBytes());
    }
    
}
