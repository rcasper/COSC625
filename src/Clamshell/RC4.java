package Clamshell;

/**
 * RC4 Stream Cipher 
 * Adapted for Modularity
 * COSC 625 Fall 2013
 *
 * @author Andrew Ramsey
 * @version 1.8
 */
public class RC4 {
    
    private int[] key, plain, s, cipher;
    
    public RC4() {
        
    }
    
    /**
     * Perform RC4 Stream Cipher
     * @param pstr
     * @param keyStr
     * @return String - ciphertext
     */
    public String cipher(String pstr, String keyStr) {
        // assume that everything is being parsed and is hex
        plain = new int[pstr.length()/2];
        int ctr = 0;
        for (int i = 0; i < pstr.length(); i+=2){
            plain[ctr] = Integer.parseInt(pstr.substring(i, i+2),16);
            ctr++;
        }
        key = s2ia(keyStr);
        initialization();
        generateStream();
        
        String ciphertext = "";
        for (int i = 0; i < cipher.length; i++) {
            ciphertext += b2h(ia2s(int2bin(cipher[i])));
        }
        return ciphertext;
    }

    private void generateStream() {
        int i = 0;
        int j = 0;
        cipher = new int[plain.length];
        for (int q = 0; q < plain.length; ++q) {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            int t = (s[i] + s[j]) % 256;
            int k = s[t];
            int[] kbin = int2bin(k);
            int[] pbin = int2bin(plain[q]);
            cipher[q] = bin2int(xorHelper(kbin, pbin));
        }
    }

    private void initialization() {
        s = new int[256];
        int[] t = new int[256];
        int j = 0;
        for (int i = 0; i < 256; ++i) {//initialize
            s[i] = i;
            t[i] = key[i % key.length];
        }
        for (int i = 0; i < 256; ++i) {//permute
            j = (j + s[i] + t[i]) % 256;
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
    }
    
    //*****UTILITIES************************************************************
    
    private String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    private String[] binary = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
    
    private int[] int2bin(int inp){
        int[] binc = {128,64,32,16,8,4,2,1};
        int[] outp = new int[8];
        for(int i = 0; i < 8; ++i){
            if(inp >= binc[i]){
                inp = inp - binc[i];
                outp[i] = 1;
            }
            else{
                outp[i] = 0;
            }
        }
        return outp;
    }
    
    private int bin2int(int[] inp){
        int co = 0;
        int[] binc = {128,64,32,16,8,4,2,1};
        for(int i = 0; i < 8; ++i){
            co = co + (inp[i] * binc[i]);
        }
        return co;        
    }
    
    private String h2b(String inp){

        String result = "";
        for (int i = 0; i < inp.length(); ++i) {
            char temp = inp.charAt(i);
            String temp2 = "" + temp + "";
            for (int j = 0; j < hex.length; j++) {
                if (temp2.equalsIgnoreCase(hex[j])) {
                    result = result + binary[j];
                }
            }
        }
        return result;
    }

    private String b2h(String inp){
        
        String result1 = ""; String result2 = "";
            String temp1 = inp.substring(0, 4);
            String temp2 = inp.substring(4, 8);
            for (int j = 0; j < hex.length; j++) {
                if (temp1.equalsIgnoreCase(binary[j])) {
                    result1 = hex[j];
                }
                if (temp2.equalsIgnoreCase(binary[j])) {
                    result2 = hex[j];
                }
            }
        return (""+result1+result2);
    }

    private String ia2s(int[] inp) {
        String ret = "";
        for (int i = 0; i < inp.length; ++i) {
            ret = ret + inp[i];
        }
        return ret;
    }

    private int[] s2ia(String inp) {
        int[] temper = new int[inp.length()];
        for (int i = 0; i < inp.length(); i++) {
            temper[i] = Integer.parseInt(inp.substring(i, i + 1),16);
        }
        return temper;
    }

    private int[] sa2ia(String[] inp) {
        int[] temper = new int[inp.length];
        for (int i = 0; i < inp.length; i++) {
            temper[i] = Integer.parseInt(inp[i]);
        }
        return temper;
    }
    
    private int[] xorHelper(int[] s, int[] k) {
        int[] xor = new int[8];
        for (int i = 0; i < 8; ++i) {
            xor[i] = s[i] ^ k[i];
        }
        return xor;
    }
    
    private void print(String a, int[] b) {
        System.out.print(a);
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i] + " ");
        }
        System.out.println();
    }
    
}
