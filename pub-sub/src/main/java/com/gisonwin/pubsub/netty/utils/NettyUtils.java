package com.gisonwin.pubsub.netty.utils;


import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/8/29 13:41
 */
public class NettyUtils {
    public static final String SEPARATOR_COMMA = ",";


    /***
     * int value to byte array.
     *
     * @param intVal
     * @return
     */
    public static byte[] intToBytes(int intVal) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (intVal >>> (24 - i * 8));
        }
        return b;
    }

    /***
     * short value to byte array
     *
     * @param shortVal
     * @return
     */
    public static byte[] shortToBytes(short shortVal) {
        byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            b[i] = (byte) (shortVal >>> (i * 8));
        }
        return b;
    }

    /***
     *
     * @param b
     * @return
     */
    public static short bytesToShort(byte[] b) {
        return (short) (((b[1] << 8) | b[0] & 0xff));
    }

    /**
     * @param source
     * @return
     */
    public static String string2Hex(String source) {
        return string2Hex(source, StandardCharsets.UTF_8);
    }

    /**
     * @param source
     * @param charset
     * @return
     */
    public static String string2Hex(String source, Charset charset) {
        return bytes2hex(source.getBytes(charset));
    }

    /***
     * 将数组转为16进制字符串。
     *
     * @param bytes
     * @return
     */
    public static String bytes2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1) {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }
        return sb.toString();
    }

    /*
     * 将16进制的字符串装换为对应的byte数组，例如"A5000C5A81000000000000000000010E90AA" 转换为对应的数组形式
     *
     * @param hexString
     *
     * @return 转换后的数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /***
     * 对数组数据进行异或处理.
     *
     * @param data
     * @return
     */
    public static byte getXOR(byte[] data) {
        byte temp = data[0];
        for (int i = 1; i < data.length; i++) {
            temp ^= data[i];
        }
        return temp;
    }

    /**
     * byte数组转换为二进制字符串,每个字节以","隔开
     **/
    public static String byteArrToBinStr(byte[] bytes) {
        StringJoiner joiner = new StringJoiner(SEPARATOR_COMMA);
        for (byte b : bytes) {
            joiner.add(Long.toString(b & 0xff, 2));
        }
        return joiner.toString().trim();

    }

    /**
     * 二进制字符串转换为byte数组,每个字节以","隔开
     **/
    public static byte[] binStrToByteArr(String binStr) {
        String[] temp = binStr.split(SEPARATOR_COMMA);
        byte[] b = new byte[temp.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = Long.valueOf(temp[i], 2).byteValue();
        }
        return b;
    }

    /***
     * 将int value转换为32位的16进制字符串(模拟生成32位的UUID).
     *
     * @param value
     * @return
     */
    public static String longValueTo32HexString(long value) {
        String hex = Long.toHexString(value);
        String format = String.format("%32s", hex);
        format = format.replaceAll(" ", "0");
        return format;
    }

    /***
     * 将int value转换为32位的16进制字符串(模拟生成32位的UUID).
     *
     * @param value
     * @return
     */
    public static String intValueTo32HexString2(int value) {
        String hex = Integer.toHexString(value);
        int length = hex.length();
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32 - length; i++) {
            sb.append("0");
        }
        sb.append(hex);
        return sb.toString().trim();
    }

    /***
     * 将int value转换为32位的16进制字符串(模拟生成32位的UUID).
     *
     * @param value
     * @return
     */
    public static String intValueTo32HexString(int value) {
        String hex = Integer.toHexString(value);
        String format = String.format("%32s", hex);
        format = format.replaceAll(" ", "0");
        return format;
    }

    /***
     * 32位16进制数据转为int值.
     *
     * @param value
     * @return
     */
    public static int hexStringToInt(String value) {
        BigInteger bigInteger = new BigInteger(value, 16);
        return bigInteger.intValue();
    }

    /***
     * 32位16进制数据转为Long型.
     *
     * @param
     * @return
     */
    public static long hexStringToLong(String value) {
        BigInteger bigInteger = new BigInteger(value, 16);
        return bigInteger.longValue();
    }


    /**
     * 正则表达式执行逻辑
     *
     * @param condStr    校验串
     * @param patternStr 匹配标准串
     * @return 结果值
     */
    public static Matcher runRegularExpressions(String condStr, String patternStr) {
        // 编译正则表达式
        Pattern pattern = Pattern.compile(patternStr);
        // 开始匹配
        Matcher matcher = pattern.matcher(condStr);
        return matcher;
    }

    /**
     * 从条件字符串获取关联参数
     *
     * @param condStr 输入处理字符串
     */
    public static List<String> initcondExpress(String condStr) {
        List<String> list = new ArrayList<String>();
        // 字符串匹配规则
        String patternStr = "([a-zA-Z][a-zA-Z0-9._-]*)";
        // 正则执行
        Matcher splitMatcher = runRegularExpressions(condStr, patternStr);
        while (splitMatcher.find()) {
            String paramName = splitMatcher.group();
            list.add(paramName);
        }
        return list;
    }

}
