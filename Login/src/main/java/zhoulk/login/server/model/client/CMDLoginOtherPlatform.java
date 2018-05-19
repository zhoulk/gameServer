package zhoulk.login.server.model.client;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import zhoulk.tool.convert.ConvertUtils;
import zhoulk.tool.model.CMDContent;

/**
 * Created by zlk on 2018/4/19.
 */
public class CMDLoginOtherPlatform extends CMDContent {
    @Override
    protected void decode0(ByteBuf in) {
        wModuleID = in.readUnsignedShortLE();
        dwPlazaVersion = in.readUnsignedIntLE();
        cbDeviceType = in.readUnsignedByte();

        cbGender = in.readUnsignedByte();
        cbPlatformID = in.readUnsignedByte();

        szUserUin = ConvertUtils.ByteBufToString(in, 66);
        szNickName = ConvertUtils.ByteBufToString(in, 64);
        szCompellation = ConvertUtils.ByteBufToString(in, 32);

        //连接信息
        szMachineID = ConvertUtils.ByteBufToString(in, 66);                      //机器标识 string(33)
        szMobilePhone = ConvertUtils.ByteBufToString(in, 24);                   //电话号码 string(12)
        szHeadUrl = ConvertUtils.ByteBufToString(in, 512);                    //头像URL string(256)

        longitude = in.readLongLE();
        latitude = in.readLongLE();
        isSimulator = in.readUnsignedByte();            // 0 正常  1 模拟
        networkType = in.readUnsignedByte();            // 0 wifi  1 4G  2 未知
        batteryLevel = in.readUnsignedByte();           //电量
    }

    @Override
    protected ByteBuf encode0() {
        ByteBuf buf = Unpooled.buffer(len());

        buf.writeShortLE(wModuleID);
        buf.writeIntLE((int)dwPlazaVersion);
        buf.writeByte(cbDeviceType);

        buf.writeByte(cbGender);
        buf.writeByte(cbPlatformID);

        buf.writeBytes(ConvertUtils.StringToBytes(szUserUin, 66));
        buf.writeBytes(ConvertUtils.StringToBytes(szNickName, 64));
        buf.writeBytes(ConvertUtils.StringToBytes(szCompellation, 32));

        //连接信息
        buf.writeBytes(ConvertUtils.StringToBytes(szMachineID, 66));
        buf.writeBytes(ConvertUtils.StringToBytes(szMobilePhone, 24));
        buf.writeBytes(ConvertUtils.StringToBytes(szHeadUrl, 512));

        buf.writeLongLE(longitude);
        buf.writeLongLE(latitude);
        buf.writeByte(isSimulator);
        buf.writeByte(networkType);
        buf.writeByte(batteryLevel);

        return buf;
    }

    @Override
    public int len() {
        return 784;
    }

    //系统信息
    private int wModuleID;                         //模块标识  ushort  2
    private long dwPlazaVersion;                   //广场版本  UInt32  6
    private short cbDeviceType;                       //设备类型     7

    //登录信息
    private short cbGender;                          //用户性别    8
    private short cbPlatformID;                      //平台编号    9

    private String szUserUin;                       //用户Uin string(33)  75
    private String szNickName;                      //用户昵称 string(32) 139
    private String szCompellation;                   //真实名字 string(16) 171

    //连接信息
    private String szMachineID;                      //机器标识 string(33) 237
    private String szMobilePhone;                    //电话号码 string(12) 261
    private String szHeadUrl;                    //头像URL string(256)   773

    private long longitude;               // 777
    private long latitude;                // 781
    private short isSimulator;            // 0 正常  1 模拟   782
    private short networkType;            // 0 wifi  1 4G  2 未知  783
    private short batteryLevel;           //电量  784

    public void setwModuleID(int wModuleID) {
        this.wModuleID = wModuleID;
    }

    public void setDwPlazaVersion(long dwPlazaVersion) {
        this.dwPlazaVersion = dwPlazaVersion;
    }

    public void setCbDeviceType(short cbDeviceType) {
        this.cbDeviceType = cbDeviceType;
    }

    public void setCbGender(short cbGender) {
        this.cbGender = cbGender;
    }

    public void setCbPlatformID(short cbPlatformID) {
        this.cbPlatformID = cbPlatformID;
    }

    public void setSzUserUin(String szUserUin) {
        this.szUserUin = szUserUin;
    }

    public void setSzNickName(String szNickName) {
        this.szNickName = szNickName;
    }

    public void setSzCompellation(String szCompellation) {
        this.szCompellation = szCompellation;
    }

    public void setSzMachineID(String szMachineID) {
        this.szMachineID = szMachineID;
    }

    public void setSzMobilePhone(String szMobilePhone) {
        this.szMobilePhone = szMobilePhone;
    }

    public void setSzHeadUrl(String szHeadUrl) {
        this.szHeadUrl = szHeadUrl;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public void setIsSimulator(byte isSimulator) {
        this.isSimulator = isSimulator;
    }

    public void setNetworkType(byte networkType) {
        this.networkType = networkType;
    }

    public void setBatteryLevel(byte batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public int getwModuleID() {
        return wModuleID;
    }

    public long getDwPlazaVersion() {
        return dwPlazaVersion;
    }

    public short getCbDeviceType() {
        return cbDeviceType;
    }

    public short getCbGender() {
        return cbGender;
    }

    public short getCbPlatformID() {
        return cbPlatformID;
    }

    public String getSzUserUin() {
        return szUserUin;
    }

    public String getSzNickName() {
        return szNickName;
    }

    public String getSzCompellation() {
        return szCompellation;
    }

    public String getSzMachineID() {
        return szMachineID;
    }

    public String getSzMobilePhone() {
        return szMobilePhone;
    }

    public String getSzHeadUrl() {
        return szHeadUrl;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public short getIsSimulator() {
        return isSimulator;
    }

    public short getNetworkType() {
        return networkType;
    }

    public short getBatteryLevel() {
        return batteryLevel;
    }

    @Override
    public String toString() {
        return "CMDLoginOtherPlatform{" +
                "wModuleID=" + wModuleID +
                ", dwPlazaVersion=" + dwPlazaVersion +
                ", cbDeviceType=" + cbDeviceType +
                ", cbGender=" + cbGender +
                ", cbPlatformID=" + cbPlatformID +
                ", szUserUin='" + szUserUin + '\'' +
                ", szNickName='" + szNickName + '\'' +
                ", szCompellation='" + szCompellation + '\'' +
                ", szMachineID='" + szMachineID + '\'' +
                ", szMobilePhone='" + szMobilePhone + '\'' +
                ", szHeadUrl='" + szHeadUrl + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", isSimulator=" + isSimulator +
                ", networkType=" + networkType +
                ", batteryLevel=" + batteryLevel +
                '}';
    }
}
