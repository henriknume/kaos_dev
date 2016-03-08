
package kaos.core;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
/**
 *
 * @author Davidf
 * 
 * Class used for protection of password: will hash password together with an
 * random generated salt String. 
 */
public class PasswordProtection {
    
    /*
    * generates random salt..
    * uses ' SHA algoritm ' ..
    */
    public static String getSalt()throws NoSuchAlgorithmException{  
        SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");  
        byte [] salt = new byte[32];
        rnd.nextBytes(salt);
        return convertToHex(salt);   // convert to hex format and return
    }
    /*
    *  hashes password + salt ...
    */
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException{ 
        String passwordSalted = password + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(passwordSalted.getBytes());
        byte byteData[] = md.digest();
        return convertToHex(byteData);
    }
    /*
    * converts data to hex format...
    */
    private static String convertToHex(byte [] hexByte) {   
        String hex = "";
        StringBuilder hexStringBuffert = new StringBuilder();
        for (int i=0;i<hexByte.length;i++) {
            hex=Integer.toHexString(0xff & hexByte[i]);
            if(hex.length()==1) hexStringBuffert.append('0');
            hexStringBuffert.append(hex);
        }
        return hexStringBuffert.toString();
     }
    public static boolean checkPassword(String passClient, String passDB)throws NoSuchAlgorithmException{
        String pass = passDB.substring(0, 64);
        String salt = passDB.substring(64,passDB.length());
            if(hashPassword(passClient,salt).equals(passDB)){
                return true; // ok -> login
            }
            return false; // no entry
    }
}
