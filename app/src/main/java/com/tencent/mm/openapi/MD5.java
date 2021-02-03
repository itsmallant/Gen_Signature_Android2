package com.tencent.mm.openapi;

import java.security.MessageDigest;

public final class MD5 {
    private MD5() {
    }

    public static final String getMessageDigest(byte[] buffer) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            char[] str = new char[(buffer.length * 2)];
            int k = 0;
            for (byte byte0 : mdTemp.digest()) {
                int k2 = k + 1;
                str[k] = hexDigits[(byte0 >>> 4) & 15];
                k = k2 + 1;
                str[k2] = hexDigits[byte0 & 15];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static final byte[] getRawDigest(byte[] buffer) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            return mdTemp.digest();
        } catch (Exception e) {
            return null;
        }
    }
}