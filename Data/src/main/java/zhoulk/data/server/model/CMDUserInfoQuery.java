package zhoulk.data.server.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import zhoulk.tool.convert.ConvertUtils;
import zhoulk.tool.model.CMDContent;

/**
 * Created by zlk on 2018/4/22.
 */
public class CMDUserInfoQuery extends CMDContent{

    @Override
    public void decode0(ByteBuf in) {
        szUserUin = ConvertUtils.ByteBufToString(in, 66);
        dwUserID = in.readUnsignedIntLE();
        szDynamicPassword = ConvertUtils.ByteBufToString(in, 66);
    }

    @Override
    public ByteBuf encode0() {
        ByteBuf buf = Unpooled.buffer(len());
        byte[] uinBytes = ConvertUtils.StringToBytes(szUserUin, 66);
        buf.writeBytes(uinBytes);
        buf.writeIntLE((int)dwUserID);
        buf.writeBytes(ConvertUtils.StringToBytes(szDynamicPassword, 66));
        return buf;
    }

    @Override
    public int len() {
        return 136;
    }

    //系统信息
    private String szUserUin;                       //用户Uin string(33)  66

    private long dwUserID; // uint dwUserID;                         //用户 I D   70
    private String szDynamicPassword; //string(33) szDynamicPassword;               //登录密码  66  136

    public String getSzUserUin() {
        return szUserUin;
    }

    public void setSzUserUin(String szUserUin) {
        this.szUserUin = szUserUin;
    }

    public long getDwUserID() {
        return dwUserID;
    }

    public void setDwUserID(long dwUserID) {
        this.dwUserID = dwUserID;
    }

    public String getSzDynamicPassword() {
        return szDynamicPassword;
    }

    public void setSzDynamicPassword(String szDynamicPassword) {
        this.szDynamicPassword = szDynamicPassword;
    }

    @Override
    public String toString() {
        return "CMDUserInfoQuery{" +
                "szUserUin='" + szUserUin + '\'' +
                ", dwUserID=" + dwUserID +
                ", szDynamicPassword='" + szDynamicPassword + '\'' +
                '}';
    }
}
