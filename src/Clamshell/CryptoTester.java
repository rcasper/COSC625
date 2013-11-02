package Clamshell;

public class CryptoTester {

    private static final String delimeter = "   "; // entry data delimeter
    
    public static void main(String argv[])
    {
        String sname = "tester1";
        String uname = "andrewpramsey";
        String pass = "four4scor0eand7eveny3eS6go";
        String masterPass = "password";
        String cipher = "";
        
        Entry e = new Entry(sname,uname,pass);
        
        // RC4 cipher demonstration
        // 
        RC4 rc = new RC4();
        cipher = rc.cipher(byteArrayToHexString(sname.getBytes()),byteArrayToHexString(masterPass.getBytes()));
        //cipher = rc.cipher(byteArrayToHexString((e.getServiceName() + delimeter + e.getUserName() + delimeter + e.getPassword()).getBytes()), byteArrayToHexString(masterPass.getBytes()));
        System.out.println("RC4 encryption:\n"+cipher);
        cipher = rc.cipher(cipher, byteArrayToHexString(masterPass.getBytes()));
        System.out.println("RC4 decryption:\n"+cipher);
        System.out.println("RC4 decryption & convert:\n"+(new String(hexStringToByteArray(cipher)))+"\n");
        
        // AES 256 demonstration
        // For AES 256, the key length must be 64 hex chars long (64 * 4 = 256 bits)
        // Each plaintext segment must be 32 hex characters long
        Yaes aes = new Yaes("00112233445566778899aabbccddeeff00112233445566778899aabbccddeeff");
        System.out.println("Randomly-generated IV:\n"+e.IV);
        cipher = aes.encryptString("00112233445566778899aabbccddeeff", e.IV);
        System.out.println("AES encryption:\n"+cipher);
        cipher = aes.decryptString(cipher, e.IV);
        System.out.println("AES decryption:\n"+cipher);

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
    
}
