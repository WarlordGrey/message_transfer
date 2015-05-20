package ua.cn.stu.messagetransfer.work;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by WarlordGrey on 19.05.2015.
 */
public class Md5 {
    public static String generateMd5(String key) {
        StringBuffer hexStr = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                int c = (int) hash[i];
                if (c < 0) {
                    c += 256;
                }
                hexStr.append(Integer.toHexString((c & 0xf0) >> 4));
                hexStr.append(Integer.toHexString(c & 0x0f));
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("md5 не поддерживается");
        }
        return hexStr.toString();
    }

}
