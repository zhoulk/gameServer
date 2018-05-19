package zhoulk.data.server.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import zhoulk.tool.convert.ConvertUtils;
import zhoulk.tool.model.CMDContent;

/**
 * Created by zlk on 2018/4/22.
 */
public class CMDUserInfoQueryFail extends CMDContent {

    private String uid; //string(33) 用户标识符，微信登录 unionId，游客登录 ceshi+[0-1000]  66
    private long lResultCode; // uint 错误代码   70
    private String szDescribeString; // String(128) 描述消息  326

    @Override
    protected void decode0(ByteBuf in) {
        uid = ConvertUtils.ByteBufToString(in, 66);
        lResultCode = in.readUnsignedIntLE();
        szDescribeString = ConvertUtils.ByteBufToString(in, 256);
    }

    @Override
    protected ByteBuf encode0() {
        ByteBuf buf = Unpooled.buffer(len());
        byte[] uidBytes = ConvertUtils.StringToBytes(uid, 66);
        buf.writeBytes(uidBytes);
        buf.writeLongLE(lResultCode);
        byte[] desBytes = ConvertUtils.StringToBytes(szDescribeString, 256);
        buf.writeBytes(desBytes);
        return buf;
    }

    @Override
    public int len() {
        return 326;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getlResultCode() {
        return lResultCode;
    }

    public void setlResultCode(long lResultCode) {
        this.lResultCode = lResultCode;
    }

    public String getSzDescribeString() {
        return szDescribeString;
    }

    public void setSzDescribeString(String szDescribeString) {
        this.szDescribeString = szDescribeString;
    }

    @Override
    public String toString() {
        return "CMDUserInfoQueryFail{" +
                "uid='" + uid + '\'' +
                ", lResultCode=" + lResultCode +
                ", szDescribeString='" + szDescribeString + '\'' +
                '}';
    }
}
