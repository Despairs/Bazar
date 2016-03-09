package org.investsoft.bazar.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * Created by Despairs on 06.03.16.
 */
public class SecurityUtils {

    public static SecureRandom random = new SecureRandom();

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    static {
        try {
            File URANDOM_FILE = new File("/dev/urandom");
            FileInputStream sUrandomIn = new FileInputStream(URANDOM_FILE);
            byte[] buffer = new byte[1024];
            sUrandomIn.read(buffer);
            sUrandomIn.close();
            random.setSeed(buffer);
        } catch (Exception e) {
            Log.e("BAZAR", e.getMessage());
        }
    }

    public static String MD5(String md5) {
        if (md5 == null) {
            return null;
        }
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] computeSHA256(byte[] convertme, int offset, int len) {
        byte[] ret = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(convertme, offset, len);
            ret = md.digest();
        } catch (Exception e) {
            Log.e("BAZAR", e.getMessage());
        }
        return ret;
    }

    public static String generatePasscodeHash(String passcode, boolean updateSalt) {
        String hash = null;
        try {
            if (updateSalt) {
                UserConfig.passcodeSalt = new byte[16];
                random.nextBytes(UserConfig.passcodeSalt);
            }
            byte[] passcodeBytes = passcode.getBytes("UTF-8");
            byte[] bytes = new byte[32 + passcodeBytes.length];
            System.arraycopy(UserConfig.passcodeSalt, 0, bytes, 0, 16);
            System.arraycopy(passcodeBytes, 0, bytes, 16, passcodeBytes.length);
            System.arraycopy(UserConfig.passcodeSalt, 0, bytes, passcodeBytes.length + 16, 16);
            hash = SecurityUtils.bytesToHex(SecurityUtils.computeSHA256(bytes, 0, bytes.length));
        } catch (Exception ex) {
            Log.e("BAZAR", ex.getMessage());
        }
        return hash;
    }
}
