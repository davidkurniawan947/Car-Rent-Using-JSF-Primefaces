/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author louiv
 */
public class PasswordHasher {
    
    
    public byte[] getSHA(String input) throws NoSuchAlgorithmException{
        MessageDigest digestor = MessageDigest.getInstance("SHA-256");
        
        return digestor.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    
    public String toHexString(byte[] hash){
        BigInteger number = new BigInteger(1, hash);
        
        StringBuilder hexString = new StringBuilder(number.toString(16));
        
        while (hexString.length() < 64){
            hexString.insert(0, '0');
        }
        
        return hexString.toString();
    }
    
    /*
    public static void main(String[] args){
        try {
            
            String pass = "hasher1";
            
            System.out.println(toHexString(getSHA(pass)));
            
            String validator = "hasher";
            
            System.out.println(toHexString(getSHA(validator)));
            
            
            List<String> passwordList = new ArrayList<String>();
            passwordList.add("abcd");
            passwordList.add("rikky");
            passwordList.add("udin");
            passwordList.add("viee");
            passwordList.add("abcde");
            passwordList.add("test2");
            passwordList.add("sasha");
            passwordList.add("test3");
            passwordList.add("sasha2");
            
            
            passwordList.add("damar");
            passwordList.add("rikky");
            passwordList.add("sasha");
            
            
            for (int i = 0; i < passwordList.size(); i++){
                System.out.println(passwordList.get(i) + ", Hashed Password: " + toHexString(getSHA(passwordList.get(i))));
            }
            
        } catch (Exception e){
            System.out.println("Lho he?");
        }
    }
    */
    
}
