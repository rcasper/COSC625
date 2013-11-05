package Clamshell;

import java.util.Random;
/**
 * Entry object for storing secure password information
 * COSC 625 Fall 2013
 * @author Andrew Ramsey
 * @author Ryan Kasprzyk
 */
public class Entry {
    
    public String sName, uName, pw, IV;

    public Entry(String s_name, String u_name, String pass) {
        sName = s_name;
        uName = u_name;
        pw = pass;
        byte[] bIV = new byte[16];
        Random rand = new Random();
        rand.nextBytes(bIV);
        IV = getHexString(bIV).toUpperCase();
    }
    
    public Entry(String s_name, String u_name, String iv, String pass) {
        sName = s_name;
        uName = u_name;
        IV = iv;
        pw = pass;
    }
    
    private String getHexString(byte[] b){
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public void setServiceName(String s_name) {
        sName = s_name;
    }

    public void setUserName(String u_name) {
        uName = u_name;
    }

    public void setPassword(String pass) {
        pw = pass;
    }

    public String getServiceName() {
        return sName;
    }

    public String getUserName() {
        return uName;
    }

    public String getPassword() {
        return pw;
    }
}
