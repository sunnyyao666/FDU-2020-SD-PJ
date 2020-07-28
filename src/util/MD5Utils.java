package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static ThreadLocal<MD5Utils> md5ToolThreadLocal = new ThreadLocal<>();

    private MD5Utils() {
    }

    public static MD5Utils getInstance() {
        if (md5ToolThreadLocal.get() == null) {
            md5ToolThreadLocal.set(new MD5Utils());
        }
        return md5ToolThreadLocal.get();
    }

    public String getMd5(String source) {
        try {
            //1.获取MessageDigest对象
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] bytes = source.getBytes();
            //在MD5算法中，得到的目标字节数组的长度固定为16
            byte[] targetBytes = digest.digest(bytes);
            //3.声明字符数组
            char[] characters = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            //4.遍历targetBytes
            StringBuilder builder = new StringBuilder();
            for (byte b : targetBytes) {
                //5.取出b的高四位的值
                int high = (b >> 4) & 15;
                //6.取出b的低四位的值
                int low = b & 15;
                char highChar = characters[high];
                char lowChar = characters[low];
                builder.append(highChar).append(lowChar);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
