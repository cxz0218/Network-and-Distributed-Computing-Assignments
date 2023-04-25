package server;

import java.security.MessageDigest;
import java.util.Objects;

/**
 * This is a util class for encryption.
 */
public class Encoder {

    /**
     * To encode a given string by {@code SHA-1};
     * @param str String to encode.
     * @return String encoded in {@code SHA-1}
     */
    public static String encode(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuilder hex = new StringBuilder();
            String shaHex = "";
            for (byte b : digest) {
                shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hex.append(0);
                }
                hex.append(shaHex);
            }
            return hex.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * To match two strings.
     * @param origin The origin string before encryption.
     * @param target The target string after encryption.
     * @return {@code true} if they match, {@code false} if not.
     */
    public static boolean match(String origin, String target){
        try {
            return Objects.equals(encode(origin), target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
