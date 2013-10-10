package Clamshell;

/**
 * RC4 Stream Cipher COSC 625 Fall 2013
 *
 * @author Andrew Ramsey
 * @version 1.4
 */
public class RC4 {

    private int[] key, plain, s, cipher;
    
    public RC4(String k) {
        key = s2ia(k);
        initialization();
        generateStream();
    }

    public String encryption(String pstr, String keyStr) {
        
        pstr = pstr.trim();
        //System.out.println("pstr: " + pstr + " length: "+pstr.length());
        if(pstr.length()%2 != 0){
            System.out.println("Invalid plaintext length\nPlaintext is odd length!");
            System.exit(-1);
        }
        keyStr = keyStr.trim();
        key = s2ia(keyStr);
        print("key: ", key);
        int[][] plainBin = new int[pstr.length()/2][8];
        plain = new int[pstr.length()/2];
        int ctr = 0;
        for (int i = 0; i < pstr.length(); i+=2){
            plainBin[ctr] = s2ia(h2b(pstr.substring(i, i+2)));
            //print("plainBinary ["+ctr+"]: ",plainBin[ctr]);
            plain[ctr] = bin2int(plainBin[ctr]);
            ctr++;
        }
        //print("plaintext: ", ia2s(plain));
        return ia2s(plain);
    }
    
    public String decryption(String input) {
        String secondline = "";
        plain = cipher;
        initialization();
        generateStream();
            
        for (int i = 0; i < cipher.length; i++) {
            secondline += b2h(ia2s(int2bin(cipher[i])));
        }
        System.out.println("\ndecrypted:\nplaintext: " + secondline);
        return secondline;
    }

    private void generateStream() {
        int i = 0;
        int j = 0;
        cipher = new int[plain.length];
        for (int q = 0; q < plain.length; ++q) {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            //swap em, again
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            int t = (s[i] + s[j]) % 256;
            int k = s[t];
            int[] kbin = int2bin(k);
            int[] pbin = int2bin(plain[q]);
            //print("k int: "+k+", k binary: ", kbin);
            //print("plain int: "+plain[q]+", p binary: ", pbin);
            cipher[q] = bin2int(xorHelper(kbin, pbin));
        }
        //print("cipher as integer: ", cipher);
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
            //swap em
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
        //print("s after init: ", s);
    }

    
    
    //*****UTILITIES************************************************************
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
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String[] binary = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
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
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String[] binary = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
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
            temper[i] = Integer.parseInt(inp.substring(i, i + 1));
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
