package zhoulk.hall.server.model.client;

import io.netty.buffer.ByteBuf;
import zhoulk.tool.convert.ConvertUtils;
import zhoulk.tool.model.CMDContent;

/**
 * Created by zlk on 2018/4/25.
 */
public class CMDLoginMobile extends CMDContent {
    @Override
    protected void decode0(ByteBuf in) {
        wGameID = in.readUnsignedShortLE();
        dwProcessVersion = in.readUnsignedIntLE();

        cbDeviceType = in.readUnsignedByte();
        wBehaviorFlags = in.readUnsignedShortLE();
        wPageTableCount = in.readUnsignedShortLE();

        dwUserID = in.readUnsignedIntLE();
        szDynamicPassword = ConvertUtils.ByteBufToString(in, 66);
        szMachineID = ConvertUtils.ByteBufToString(in, 66);
        longitude = in.readUnsignedIntLE();
        latitude = in.readUnsignedIntLE();
        city = ConvertUtils.ByteBufToString(in, 40);
    }

    @Override
    protected ByteBuf encode0() {
        return null;
    }

    @Override
    public int len() {
        return 203;
    }

    //版本信息
    private int wGameID; // ushort wGameID;                           //游戏标识   2
    private long dwProcessVersion; // uint dwProcessVersion;                 //进程版本   6

    //桌子区域
    private short cbDeviceType; // byte cbDeviceType;                       //设备类型  7
    private int wBehaviorFlags; // ushort wBehaviorFlags;                     //行为标识  9
    private int wPageTableCount; // ushort wPageTableCount;                    //分页桌数  11

    //登录信息
    private long dwUserID; // uint dwUserID;                         //用户 I D   15
    private String szDynamicPassword; //string(33) szDynamicPassword;               //登录密码  66  81
    private String szMachineID; // string(33) szMachineID;      //机器标识  66  147
    private long longitude; // uint longitude;   155
    private long latitude; // uint latitude;     163
    private String city; // string(20) city;     //所在地  40  203

    public int getwGameID() {
        return wGameID;
    }

    public void setwGameID(int wGameID) {
        this.wGameID = wGameID;
    }

    public long getDwProcessVersion() {
        return dwProcessVersion;
    }

    public void setDwProcessVersion(long dwProcessVersion) {
        this.dwProcessVersion = dwProcessVersion;
    }

    public short getCbDeviceType() {
        return cbDeviceType;
    }

    public void setCbDeviceType(short cbDeviceType) {
        this.cbDeviceType = cbDeviceType;
    }

    public int getwBehaviorFlags() {
        return wBehaviorFlags;
    }

    public void setwBehaviorFlags(int wBehaviorFlags) {
        this.wBehaviorFlags = wBehaviorFlags;
    }

    public int getwPageTableCount() {
        return wPageTableCount;
    }

    public void setwPageTableCount(int wPageTableCount) {
        this.wPageTableCount = wPageTableCount;
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

    public String getSzMachineID() {
        return szMachineID;
    }

    public void setSzMachineID(String szMachineID) {
        this.szMachineID = szMachineID;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "CMDLoginMobile{" +
                "wGameID=" + wGameID +
                ", dwProcessVersion=" + dwProcessVersion +
                ", cbDeviceType=" + cbDeviceType +
                ", wBehaviorFlags=" + wBehaviorFlags +
                ", wPageTableCount=" + wPageTableCount +
                ", dwUserID=" + dwUserID +
                ", szDynamicPassword='" + szDynamicPassword + '\'' +
                ", szMachineID='" + szMachineID + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", city='" + city + '\'' +
                '}';
    }
}
