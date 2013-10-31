package Clamshell;

public class CryptoTester {
    
    private static char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    
    public static void main(String argv[])
    {
        
        String cipher = "";
        Entry e = new Entry("00112233445566778899aabbccddeeff","00112233445566778899aabbccddeeff","00112233445566778899aabbccddeeff");
        
        // RC4 cipher demonstration
        // 
        RC4 rc = new RC4();
        cipher = rc.cipher(e.getServiceName()+e.getPassword(), e.getPassword()+e.getServiceName());
        System.out.println("RC4 encryption:\n"+cipher);
        cipher = rc.cipher(cipher, e.getPassword());
        System.out.println("RC4 decryption:\n"+cipher+"\n");
        
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
    
    /**
     * Ensure password + IV are correct length of Hex characters
     * @param pw - password supplied 
     * @return String
     */
    private String[] padOrTruncate(String key, String IV)
    {
        String ret[] = new String[2];
        String k = key;
        String i = IV;
        // ensure key length
        if (k.length() < 64)
        {
            while (k.length() < 64){
                k += (0 + ""); // pad with zeros  
            }
        }
        else if (k.length() > 64)
        {
            k = k.substring(0, 64); // truncate
        }
        // ensure init vector length
        if (i.length() < 32)
        {
            while (i.length() < 32){
                i += (0 + ""); // pad with zeros  
            }
        }
        else if (i.length() > 32)
        {
            i = i.substring(0, 32); // truncate
        }
        ret[0] = k;
        ret[1] = i;
        return ret;
    }
}
