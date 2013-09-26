package Crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

/**
 * AES Encryption from scratch.
 * @author Andrew Ramsey
 * @version over 9000!
 */
public class AES {
    public final int p = 2;
    public int[] sbox = {1,1,0,0,0,1,1,0};
    public int[] invsbox = {1,0,1,0,0,0,0,0};
    public int[] zeros = {0,0,0,0,0,0,0,0};
    public int[] m;
    public int[] rcConst = {1};
    public int[][] aesMatrix, invAesMatrix; //constants or initialized
    
    public String key, plaintext;
    public String[][] state, keystate;
    public String[][] mixcol, invmixcol;
    public String[][][] keyExpansion;
    
    public boolean firstIteration = true;
    
    /*public static void main(String[] args) {
        AES rijndael = new AES();
    }*/
    
    public AES(){
        inputFile();
        formState();
        System.out.println("state:");
        print(state);
        generateAesMatrix();
        System.out.println("key:");
        print(keystate);
        keyExpansion();
        aesRoutine();
    }
    
    public void inputFile() {
        String[] input = new String[3];
        try {
            BufferedReader inp = new BufferedReader(new FileReader("src/AES/input.txt"));
            for (int i = 0; i < 3; i++) {
                input[i] = inp.readLine();
            }
        } catch (IOException e) {
            System.err.println("Could not read file");
            System.exit(-1);
        }
        key = new String();
        plaintext = new String();
        key = input[1].trim();
        plaintext = input[2].trim();
        String polyString = input[0].replaceAll("\\s", "");
        m = stringToIntArray(polyString);
        //print("m(x): ",m);
        //System.out.println("key: "+key);
        //System.out.println("plaintext: "+plaintext+"\n");     
    }
    
    public void formState(){
        state = new String[4][4];
        keystate = new String[4][4];
        int st = 0;
        for (int i=0; i < 4; i+=1){
            for (int j=0; j<4; ++j){
                String tempman = new String();
                tempman = plaintext.substring(st, st+2);
                state[i][j] = tempman;
                tempman = key.substring(st, st+2);
                keystate[i][j] = tempman;
                st+=2;
            }
        }
    }
    
    public void aesRoutine(){
        String[][] aesState = state;
        aesState = addRoundKey(aesState, keyExpansion[0]);     
        System.out.println("Encryption:");
        for (int i = 1; i <= 10; ++i) {
            aesState = sbox(aesState);
            aesState = shiftRows(aesState);
            if (i < 10) {
                aesState = mixColumns(aesState);
            }
            aesState = addRoundKey(aesState, keyExpansion[i]);
            System.out.println(i);
            print(aesState);
        }
        String[][] cipherText = aesState;
        aesState = addRoundKey(aesState, keyExpansion[10]);
        System.out.println("\nDecryption:");
        for (int i = 10; i >= 1; --i) {
            aesState = invShiftRows(aesState);
            aesState = invsbox(aesState);
            aesState = addRoundKey(aesState, keyExpansion[i-1]);
            if (i > 1) {
                aesState = invMixColumns(aesState);
            }
            System.out.println(i);
            print(aesState);
        }
        String[][] decryptedText = aesState;
        // write outputs to file
        try {
            File file = new File("output.txt");
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String firstline = new String();
            String secondline = new String();
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    firstline += cipherText[i][j];
                    secondline += decryptedText[i][j];
                }
            }
            bw.write(firstline);
            bw.newLine();
            bw.write(secondline);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void keyExpansion(){//perform for all 10 rounds (44 words)
        int[][][] wordKey = new int[44][4][8];
        int[][] w0 = {h(keystate[0][0]),h(keystate[0][1]),h(keystate[0][2]),h(keystate[0][3])};
        int[][] w1 = {h(keystate[1][0]),h(keystate[1][1]),h(keystate[1][2]),h(keystate[1][3])};
        int[][] w2 = {h(keystate[2][0]),h(keystate[2][1]),h(keystate[2][2]),h(keystate[2][3])};
        int[][] w3 = {h(keystate[3][0]),h(keystate[3][1]),h(keystate[3][2]),h(keystate[3][3])};
        wordKey[0] = w0;
        wordKey[1] = w1;
        wordKey[2] = w2;
        wordKey[3] = w3;
        //System.out.println("Begin key expansion:\n");
        for (int i = 4; i < 44; ++i) {
            int[][] temp = wordKey[i - 1].clone();
            if (i % 4 == 0) {
                temp = g(temp);
            }
            int[][] wordXor = new int[4][8];
            for (int j = 0; j < 4; ++j) {
                wordXor[j] = xorHelper(wordKey[i-4][j], temp[j]);   
            }
            wordKey[i] = wordXor;
        }
        //convert 44 words into 11 4x4 hex (string) matricies
        keyExpansion = new String[11][4][4];
        for (int i = 0; i < 11; ++i) {
            String[][] temper = new String[4][4];
            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    temper[j][k] = h2(wordKey[(4 * i) + j][k]);
                }
            }
            keyExpansion[i] = temper;
            //System.out.println("\nKey expansion round "+i+":");
            //print(keyExpansion[i]);
        }
    }
    
    public int[][] g(int[][] word){
        int[][] wprime = new int[4][8];
        int[] tempWord = word[0];
        word[0] = word[1]; 
        word[1] = word[2]; 
        word[2] = word[3];
        word[3] = tempWord;//end of bit shift
        for (int j = 0; j < 4; ++j) {
            int[] multInv = word[j];
            if (!isZero(multInv)) {
                multInv = EEAP(multInv, m)[0];
            } else {
                multInv = zeros;
            }
            multInv = normer(multInv);
            wprime[j] = affineTrans(multInv);
        }
        if (firstIteration == false) {//test to ensure it is not the first iteration
            rcConst = multiplyMod(new int[]{1, 0}, rcConst);
        }else if(firstIteration==true){
            firstIteration = false;
        }
        wprime[0] = xorHelper(normer(rcConst), normer(wprime[0]));
        return wprime;
    }
    
    public String[][] addRoundKey(String[][] state, String[][] key){
        String [][] rk = new String[4][4];
        for(int i=0; i < 4; ++i){
            for(int j=0; j<4; ++j){
                rk[i][j] = h2(xorHelper(h(state[i][j]),h(key[i][j])));
            }
        }
        return rk;
    }
    
    public String[][] invMixColumns(String[][] state){
        String[][] mixed = new String[4][4];
        String s0 = new String();
        String s1 = new String();
        String s2 = new String();
        String s3 = new String();
        for(int i = 0; i<4; ++i){
        s0 = h2(normer(addMod(addMod(addMod(multiplyMod(h(invmixcol[0][0]),h(state[i][0])), multiplyMod(h(invmixcol[1][0]),h(state[i][1]))),multiplyMod(h(invmixcol[2][0]),h(state[i][2]))),multiplyMod(h(invmixcol[3][0]),h(state[i][3])))));
        s1 = h2(normer(addMod(addMod(addMod(multiplyMod(h(invmixcol[0][1]),h(state[i][0])), multiplyMod(h(invmixcol[1][1]),h(state[i][1]))),multiplyMod(h(invmixcol[2][1]),h(state[i][2]))),multiplyMod(h(invmixcol[3][1]),h(state[i][3])))));
        s2 = h2(normer(addMod(addMod(addMod(multiplyMod(h(invmixcol[0][2]),h(state[i][0])), multiplyMod(h(invmixcol[1][2]),h(state[i][1]))),multiplyMod(h(invmixcol[2][2]),h(state[i][2]))),multiplyMod(h(invmixcol[3][2]),h(state[i][3])))));
        s3 = h2(normer(addMod(addMod(addMod(multiplyMod(h(invmixcol[0][3]),h(state[i][0])), multiplyMod(h(invmixcol[1][3]),h(state[i][1]))),multiplyMod(h(invmixcol[2][3]),h(state[i][2]))),multiplyMod(h(invmixcol[3][3]),h(state[i][3])))));
        mixed[i] = new String[]{s0, s1, s2, s3};    
        }
        System.out.println("Inverse Mix Columns: ");
        print(mixed);
        return mixed;
    }
    
    public String[][] mixColumns(String[][] state){
        String[][] mixed = new String[4][4];
        String s0 = new String();
        String s1 = new String();
        String s2 = new String();
        String s3 = new String();
        
        for(int i = 0; i<4; ++i){//rows 0 - 3, for i = 0 to 3 columns
        s0 = h2(normer(addMod(addMod(addMod(multiplyMod(h(mixcol[0][0]),h(state[i][0])), multiplyMod(h(mixcol[1][0]),h(state[i][1]))),multiplyMod(h(mixcol[2][0]),h(state[i][2]))),multiplyMod(h(mixcol[3][0]),h(state[i][3])))));
        s1 = h2(normer(addMod(addMod(addMod(multiplyMod(h(mixcol[0][1]),h(state[i][0])), multiplyMod(h(mixcol[1][1]),h(state[i][1]))),multiplyMod(h(mixcol[2][1]),h(state[i][2]))),multiplyMod(h(mixcol[3][1]),h(state[i][3])))));
        s2 = h2(normer(addMod(addMod(addMod(multiplyMod(h(mixcol[0][2]),h(state[i][0])), multiplyMod(h(mixcol[1][2]),h(state[i][1]))),multiplyMod(h(mixcol[2][2]),h(state[i][2]))),multiplyMod(h(mixcol[3][2]),h(state[i][3])))));
        s3 = h2(normer(addMod(addMod(addMod(multiplyMod(h(mixcol[0][3]),h(state[i][0])), multiplyMod(h(mixcol[1][3]),h(state[i][1]))),multiplyMod(h(mixcol[2][3]),h(state[i][2]))),multiplyMod(h(mixcol[3][3]),h(state[i][3])))));
        mixed[i] = new String[]{s0, s1, s2, s3};// per word
        } // muhahaha
        System.out.println("Mix Columns: ");
        print(mixed);
        return mixed;
    }
        
    public String[][] invsbox(String[][] state) {
        String[][] boxState = new String[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                int[] multInv = h(state[i][j]);
                multInv = invAffineTrans(normer(multInv));
                if (!isZero(multInv)) {
                    multInv = EEAP(multInv, m)[0];
                } else {
                    multInv = zeros;
                }
                boxState[i][j] = h2(normer(multInv));
            }
        }
        System.out.println("Inverse S-Box transformed state:");
        print(boxState);
        System.out.println();
        return boxState;
    }

    public int[] invAffineTrans(int[] inp){
        int[] bvalue = new int[8];
        int rowValue;
        for(int i=0; i<8; ++i){//MSB last, LSB first. Multiply inp in reverse order to match row->column transformation
            rowValue = (invAesMatrix[i][0]*inp[7])^(invAesMatrix[i][1]*inp[6])^(invAesMatrix[i][2]*inp[5])^(invAesMatrix[i][3]*inp[4])^(invAesMatrix[i][4]*inp[3])^(invAesMatrix[i][5]*inp[2])^(invAesMatrix[i][6]*inp[1])^(invAesMatrix[i][7]*inp[0])^invsbox[i];
            bvalue[i] = rowValue;
        }
        bvalue = reverseIntArray(bvalue);//invert array for column to row conversion
        return bvalue;
    }
    
    public String[][] sbox(String[][] state) {
        String[][] boxState = new String[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                int[] multInv = h(state[i][j]);
                if (!isZero(multInv)) {
                    multInv = EEAP(multInv, m)[0];
                } else {
                    multInv = zeros;
                }
                multInv = normer(multInv);
                boxState[i][j] = h2(affineTrans(multInv));
            }
        }
        //System.out.println("S-Box transformed state:");
        //print(boxState);
        System.out.println();
        return boxState;
    }

    public int[] affineTrans(int[] inp){
        int[] bvalue = new int[8];
        int rowValue;
        for(int i=0; i<8; ++i){//MSB last, LSB first. Multiply inp in reverse order to match row->column transformation
            rowValue = (aesMatrix[i][0]*inp[7])^(aesMatrix[i][1]*inp[6])^(aesMatrix[i][2]*inp[5])^(aesMatrix[i][3]*inp[4])^(aesMatrix[i][4]*inp[3])^(aesMatrix[i][5]*inp[2])^(aesMatrix[i][6]*inp[1])^(aesMatrix[i][7]*inp[0])^sbox[i];
            bvalue[i] = rowValue;
        }
        bvalue = reverseIntArray(bvalue);//invert array for column to row conversion
        return bvalue;
    }
    
    public String[][] invShiftRows(String[][] state){
        String[][] shifted = new String[4][4];
        // [column],[row]
        shifted[0][0] = state[0][0];
        shifted[1][0] = state[1][0];
        shifted[2][0] = state[2][0];
        shifted[3][0] = state[3][0];
        
        shifted[0][1] = state[3][1];
        shifted[1][1] = state[0][1];
        shifted[2][1] = state[1][1];
        shifted[3][1] = state[2][1];
        
        shifted[0][2] = state[2][2];
        shifted[1][2] = state[3][2];
        shifted[2][2] = state[0][2];
        shifted[3][2] = state[1][2];
        
        shifted[0][3] = state[1][3];
        shifted[1][3] = state[2][3];
        shifted[2][3] = state[3][3];
        shifted[3][3] = state[0][3];
        System.out.println("Inverse Shifted rows: ");
        print(shifted);
        return shifted;
    }
    
    public String[][] shiftRows(String[][] state){
        String[][] shifted = new String[4][4];
        // [column],[row]
        shifted[0][0] = state[0][0];
        shifted[1][0] = state[1][0];
        shifted[2][0] = state[2][0];
        shifted[3][0] = state[3][0];
        
        shifted[0][1] = state[1][1];
        shifted[1][1] = state[2][1];
        shifted[2][1] = state[3][1];
        shifted[3][1] = state[0][1];
        
        shifted[0][2] = state[2][2];
        shifted[1][2] = state[3][2];
        shifted[2][2] = state[0][2];
        shifted[3][2] = state[1][2];
        
        shifted[0][3] = state[3][3];
        shifted[1][3] = state[0][3];
        shifted[2][3] = state[1][3];
        shifted[3][3] = state[2][3];
        //System.out.println("Shifted rows: ");
        //print(shifted);
        return shifted;
    }
    
    public void generateAesMatrix() {  
        aesMatrix = new int[8][8];
        aesMatrix[0] = new int[]{1,0,0,0,1,1,1,1};
        aesMatrix[1] = new int[]{1,1,0,0,0,1,1,1};
        aesMatrix[2] = new int[]{1,1,1,0,0,0,1,1};
        aesMatrix[3] = new int[]{1,1,1,1,0,0,0,1};
        aesMatrix[4] = new int[]{1,1,1,1,1,0,0,0};
        aesMatrix[5] = new int[]{0,1,1,1,1,1,0,0};
        aesMatrix[6] = new int[]{0,0,1,1,1,1,1,0};
        aesMatrix[7] = new int[]{0,0,0,1,1,1,1,1};
        invAesMatrix = new int[8][8];
        invAesMatrix[0] = new int[]{0,0,1,0,0,1,0,1};
        invAesMatrix[1] = new int[]{1,0,0,1,0,0,1,0};
        invAesMatrix[2] = new int[]{0,1,0,0,1,0,0,1};
        invAesMatrix[3] = new int[]{1,0,1,0,0,1,0,0};
        invAesMatrix[4] = new int[]{0,1,0,1,0,0,1,0};
        invAesMatrix[5] = new int[]{0,0,1,0,1,0,0,1};
        invAesMatrix[6] = new int[]{1,0,0,1,0,1,0,0};
        invAesMatrix[7] = new int[]{0,1,0,0,1,0,1,0};
        mixcol = new String[4][4];
        mixcol[0] = new String[]{"02","01","01","03"};
        mixcol[1] = new String[]{"03","02","01","01"};
        mixcol[2] = new String[]{"01","03","02","01"};
        mixcol[3] = new String[]{"01","01","03","02"};
        invmixcol = new String[4][4];
        invmixcol[0] = new String[]{"0e","09","0d","0b"};
        invmixcol[1] = new String[]{"0b","0e","09","0d"};
        invmixcol[2] = new String[]{"0d","0b","0e","09"};
        invmixcol[3] = new String[]{"09","0d","0b","0e"};
    }
        
    //*****UTILITIES************************************************************
    
    public int[] h(String inp) {//conversion helper
        return stringToIntArray(stringHexToBin(inp));
    }
    
    public String h2(int[] inp) {//conversion helper
        return stringBinToHex(intToString(inp));
    }
    
    // perform xor operation in linear series
    public int[] xorHelper(int[] s, int[] k) {
        int[] xor = new int[8];
        for (int i = 0; i < 8; ++i) {
            xor[i] = s[i] ^ k[i]; 
        }
        return xor;
    }
    
    public String intToString(int[] inp){
        String ret = "";
        for(int i=0; i<inp.length; ++i){
            ret = ret + inp[i];
        }
        return ret;
    }
    
    public int[] stringToIntArray(String inp){
        int[] temper = new int[inp.length()];
        for (int i = 0; i < inp.length(); i++) {
            temper[i] = Integer.parseInt(inp.substring(i, i+1));
        }
        return temper;
    }
    
    public String stringHexToBin(String inp){
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

    public String stringBinToHex(String inp){
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
        
    public void print(String[][] inp) {
        for (int i = 0; i < 4; i += 1) {
            for (int j = 0; j < 4; ++j) {
                System.out.print(inp[j][i]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void print(String[] gg) {
        for (int j = 0; j < 4; ++j) {
            System.out.print(gg[j]);
        }
        System.out.println();
    }
    
    public void print(String a, int[][] b) {
        System.out.println(a);
        for (int i = 0; i < b.length; i++) {
            print("",b[i]);
        }
        System.out.println();
    }
    
    public void print(String a, int[] b) {
        System.out.print(a);
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i]);
        }
        System.out.println();
    }
        
    public int[] normer(int[] inp) {
        int[] ret = new int[8];
        int temper = inp.length - 1;
        for (int i = 7; i >= (8 - inp.length); i--) {
            ret[i] = inp[temper];
            temper--;
        }//normalize array size by inserting in reverse order
        return ret;
    }
    
    public int[] reverseIntArray(int[] inp){
        int[] temp = new int[8];
        for(int i = 0; i<inp.length; i++){
            temp[7-i] = inp[i];
        }
        return temp;
    }
    
    //******** GF2 Subroutines and Math ****************************************
    
    public int[][] normalize(int[] f, int[] g){
        f = trim(f);
        g = trim(g);
        int max = Math.max(f.length, g.length);
        int[] temper1 = new int[max];
        int[] temper2 = new int[max];
        int tcountf = f.length-1;
        int tcountg = g.length-1;
        for(int i = max-1; tcountf >=0 ; i--){
            temper1[i] = f[tcountf];
            tcountf--;
        }
        for (int i = max-1; tcountg >=0; i--) {
            temper2[i] = g[tcountg];
            tcountg--;
        }
        int[][] ret = new int[2][];
        ret[0] = temper1;
        ret[1] = temper2;
        return ret;
    }
    
    public int[] add(int[] f, int[] g) {
        int[][] normer = normalize(f,g);
        f = normer[0];
        g = normer[1];
        int[] add = new int[f.length];
        for (int i = 0; i < f.length; i++) {
            add[i] = f[i] + g[i];
        }
        int[] ret = add;
        return ret;
    }

    public int[] addMod(int[] f, int[] g) {
        int[][] normer = normalize(f,g);
        f = normer[0];
        g = normer[1];
        int[] add = new int[f.length];
        for (int i = 0; i < f.length; i++) {
            add[i] = f[i] + g[i];
        }
        int[] ret = PLDA(add,m)[1];
        return ret;
    }

    public int[] subtract(int[] f, int[] g) {
        int[][] normer = normalize(f,g);
        f = normer[0];
        g = normer[1];
        int[] sub = new int[f.length];
        for (int i = 0; i < f.length; i++) {
            sub[i] = (f[i] - g[i]);
        }
        return sub;
    }
    
    public int[] subtractMod(int[] f, int[] g) {
        int[][] normer = normalize(f,g);
        f = normer[0];
        g = normer[1];
        int[] sub = new int[f.length];
        for (int i = 0; i < f.length; i++) {
            sub[i] = f[i] - g[i];
        }
        int[] ret = PLDA(sub,m)[1];
        return ret;
    }
    
    public int[] multiply(int[] a, int[] b){
        if (isZero(a) || isZero(b)) {
            int[] result = {0};
            return result;
        } else {
            int[] result = new int[(a.length + b.length) - 1];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    result[i + j] += a[i] * b[j];
                }
            }
            return trim(result);
        }
    }
    
    public int[] multiplyMod(int[] a, int[] b) {
        if (isZero(a) || isZero(b)) {
            int[] result = {0};
            return result;
        } else {
            int[] result = new int[(a.length + b.length) - 1];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    result[i + j] += a[i] * b[j];
                }
            }
            return PLDA(trim(result),m)[1];
        }
    }
    
    public int[] divide(int[] a, int[] b){
        int[] multiplicativeInverse = EEAP(b, m)[0];
        int[] ret = multiplyMod(a, multiplicativeInverse);
        return ret;
    }
    
    public int[][] PLDA(int[] n, int[] d) {
        n = mod(n);
        d = mod(d);
        int[] qx = zeros.clone();
        int[] rx = n;
        rx = trim(rx);
        d = trim(d);
        while(!isZero(rx) && ((rx.length-1)>= (d.length-1))){
            int degreeIndex = (rx.length-1)-(d.length-1)+1;
            int[] tx = new int[degreeIndex];
            tx[0] = rx[getLeadIndex(rx)]*((((EEA(d[getLeadIndex(d)],p)[0])%p)+p)%p);
            qx = add(qx,tx);
            rx = subtract(rx,multiply(tx,d));
            qx = mod(qx);
            rx = mod(rx);
            rx = trim(rx);
            qx = trim(qx);
        }
        int[][] ret = new int[2][];
        ret[0] = qx;
        ret[1] = rx;
        return ret;
    }
    
    public int[][] EEAP(int[] a, int[] b) {
        a = mod(a);
        b = mod(b);
        if (isZero(b)) {
            int[][] R = new int[2][];
            a = trim(a);
            int[] u = {((((EEA(a[getLeadIndex(a)],p)[0]) % p) + p) % p)};
            int[] v = zeros.clone();
            R[0] = u;
            R[1] = v;
            return R;
        } else {
            int[][] Q = PLDA(a, b);
            int[] qx = Q[0];
            int[] rx = Q[1];
            int[][] RR = EEAP(b,rx);
            RR[1] = mod(RR[1]);
            int[] t2 = mod(subtractMod(RR[0],multiplyMod(qx,RR[1])));
            int[][] ret = new int[2][];
            ret[0] = RR[1];
            ret[1] = t2;
            return ret;
        }
    }

    public int getLeadIndex(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] != 0) {
                return i;
            }
        }
        return 0;
    }

    public int[] EEA(int a, int b) {
        if (b == 0) {
            return new int[]{1, 0};
        } else {
            int q = a / b;
            int r = a % b;
            int[] R = EEA(b, r);
            return new int[]{R[1], R[0] - q * R[1]};
        }
    }

    public int[] mod(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = (a[i] % p + p) % p;
        }
        return a;
    }
    // let the power of the Trim heal you....
    public int[] trim(int[] a) {
        if (!isZero(a)) {
            int front_zeros = 0;
            for (int i = 0; i < a.length; i++) {
                if (a[i] == 0) {
                    front_zeros++;
                } else {
                    break;
                }
            }// find number of beginning zeros in array
            int[] trimmed = new int[a.length - front_zeros];
            for (int i = 0; i < a.length - front_zeros; i++) {
                trimmed[i] = a[i + front_zeros];
            }
            return trimmed;
        } else { // confirmed that already all zeros
            return new int[] {0}; // return all zeros
        }
    }
    // test if array is all zeros
    public boolean isZero(int[] a) {
        boolean isZero = true;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != 0) {
                isZero = false;
                break;
            }
        }
        return isZero;
    }
}