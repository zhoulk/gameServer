package zhoulk.tool.convert;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * Created by zlk on 2018/4/17.
 */
public class ConvertUtils {

    /* Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
            * @param src byte[] hall
    * @return hex string
    */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
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
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * ByteBuf 转 字符串
     * @param in
     * @param len
     * @return
     */
    public static String ByteBufToString(ByteBuf in, int len){
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        int realLen = 0;
        for(int i=0; i<bytes.length; i+=2){
            byte tmp = bytes[i];
            bytes[i] = bytes[i+1];
            bytes[i+1] = tmp;
            if(bytes[i] == 0 && bytes[i+1] == 0){
                realLen = i;
                break;
            }
        }

        byte[] _realBytes = new byte[realLen];
        System.arraycopy(bytes,0,_realBytes,0,realLen);

        String res = "";
        try {
            res = new String(_realBytes, "UTF-16");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 字符串 转 bytes
     * @return
     */
    public static byte[] StringToBytes(String str, int len){
        byte[] bytes = new byte[len];

        for(int i=0 ; i<bytes.length; i++){
            bytes[i] = 0;
        }

        if(str == null){
            str = "";
        }

        try {
            byte[] tmpbytes = str.getBytes("UTF-16");

            for(int i=0; i<tmpbytes.length && i<len; i+=2){
                byte tmp = tmpbytes[i];
                tmpbytes[i] = tmpbytes[i+1];
                tmpbytes[i+1] = tmp;
            }

            System.arraycopy(tmpbytes, 0, bytes, 0, tmpbytes.length);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
