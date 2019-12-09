package com.jieyun.common.resoreces.trading.es.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * MD5加密
 * @author huike
 * @Date 2019-12-03
 */
public class Md5Utils {

    private static final String MD5 = "MD5";
    private static final String DEFAULT_ENCODING = "UTF-8";

    public Md5Utils() {
    }

    public static String md5(String input) {
        return md5((String)input, 1);
    }

    public static String md5(String input, int iterations) {
        try {
            return EncodeUtils.encodeHex(DigestUtils.digest(input.getBytes("UTF-8"), "MD5", (byte[])null, iterations));
        } catch (UnsupportedEncodingException var3) {
            return "";
        }
    }

    public static byte[] md5(byte[] input) {
        return md5((byte[])input, 1);
    }

    public static byte[] md5(byte[] input, int iterations) {
        return DigestUtils.digest(input, "MD5", (byte[])null, iterations);
    }

    public static byte[] md5(InputStream input) throws IOException {
        return DigestUtils.digest(input, "MD5");
    }

    /**
     * 签名字符串
     *
     * @param mysign
     *            参数加密结果
     * @param sign
     *            签名结果
     * @return 签名结果
     */
    public static boolean verify(String mysign, String sign) {
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }
}
