package zhoulk.hall.server.model.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import zhoulk.tool.convert.ConvertUtils;
import zhoulk.tool.model.CMDContent;

/**
 * Created by zlk on 2018/4/26.
 */
public class CMDUserEnter extends CMDContent {

    @Override
    protected void decode0(ByteBuf in) {

    }

    @Override
    protected ByteBuf encode0() {
        ByteBuf buf = Unpooled.buffer(len());
        buf.writeIntLE((int) dwGameID);
        buf.writeIntLE((int) dwUserID);
        buf.writeShortLE(wFaceID);
        buf.writeIntLE((int) dwCustomID);
        buf.writeByte(cbGender);
        buf.writeByte(cbMemberOrder);
        buf.writeShortLE(wTableID);
        buf.writeShortLE(wChairID);
        buf.writeByte(cbUserStatus);
        buf.writeLongLE(lScore);
        buf.writeIntLE((int) dwWinCount);
        buf.writeIntLE((int) dwLostCount);
        buf.writeIntLE((int) dwDrawCount);
        buf.writeIntLE((int) dwFleeCount);
        buf.writeIntLE((int) dwExperience);
        buf.writeBytes(ConvertUtils.StringToBytes(szNickname, 64));
        buf.writeBytes(ConvertUtils.StringToBytes(szHeadUrl, 512));
        buf.writeIntLE((int) szIPAddress);
        buf.writeIntLE(dwCredit);
        buf.writeBytes(ConvertUtils.StringToBytes(registerTime, 22));
        buf.writeBytes(ConvertUtils.StringToBytes(city, 40));
        return buf;
    }

    @Override
    public int len() {
        return 695;
    }

    //用户属性
    private long dwGameID; // UInt32 dwGameID;                         //游戏 I D   4
    private long dwUserID; // UInt32 dwUserID;                         //用户 I D   8
    //头像信息
    private int wFaceID; // ushort wFaceID;                           //头像索引   10
    private long dwCustomID; // UInt32 dwCustomID;                        //自定标识   14
    //用户属性
    private short cbGender; // byte cbGender;                          //用户性别    15
    private short cbMemberOrder; // byte cbMemberOrder;                     //会员等级   16
    //用户状态
    private int wTableID; // ushort wTableID;                          //桌子索引   18
    private int wChairID; // ushort wChairID;                          //椅子索引    20
    private short cbUserStatus; // byte cbUserStatus;                        //用户状态   21
    //积分信息
    private long lScore; //long lScore;                               //用户分数   29
    //游戏信息
    private long dwWinCount; //UInt32 dwWinCount;                       //胜利盘数   33
    private long dwLostCount; // UInt32 dwLostCount;                      //失败盘数  37
    private long dwDrawCount; // UInt32 dwDrawCount;                      //和局盘数  41
    private long dwFleeCount; // UInt32 dwFleeCount;                      //逃跑盘数  45
    private long dwExperience; // UInt32 dwExperience;                     //用户经验  49
    private String szNickname; // string[32] szNickname;                    //昵称   64   113
    private String szHeadUrl; // string[256] szHeadUrl;                    //头像URL 512   625
    private long szIPAddress; // uint szIPAddress;                    //ip address   629
    private int  dwCredit;                       //用户信用值    633
    private String registerTime; // string[11] registerTime;                 //注册时间 11leng  22  655
    private String city; // string[20] city;                         //城市 20leng 40   695

    public long getDwGameID() {
        return dwGameID;
    }

    public void setDwGameID(long dwGameID) {
        this.dwGameID = dwGameID;
    }

    public long getDwUserID() {
        return dwUserID;
    }

    public void setDwUserID(long dwUserID) {
        this.dwUserID = dwUserID;
    }

    public int getwFaceID() {
        return wFaceID;
    }

    public void setwFaceID(int wFaceID) {
        this.wFaceID = wFaceID;
    }

    public long getDwCustomID() {
        return dwCustomID;
    }

    public void setDwCustomID(long dwCustomID) {
        this.dwCustomID = dwCustomID;
    }

    public short getCbGender() {
        return cbGender;
    }

    public void setCbGender(short cbGender) {
        this.cbGender = cbGender;
    }

    public short getCbMemberOrder() {
        return cbMemberOrder;
    }

    public void setCbMemberOrder(short cbMemberOrder) {
        this.cbMemberOrder = cbMemberOrder;
    }

    public int getwTableID() {
        return wTableID;
    }

    public void setwTableID(int wTableID) {
        this.wTableID = wTableID;
    }

    public int getwChairID() {
        return wChairID;
    }

    public void setwChairID(int wChairID) {
        this.wChairID = wChairID;
    }

    public short getCbUserStatus() {
        return cbUserStatus;
    }

    public void setCbUserStatus(short cbUserStatus) {
        this.cbUserStatus = cbUserStatus;
    }

    public long getlScore() {
        return lScore;
    }

    public void setlScore(long lScore) {
        this.lScore = lScore;
    }

    public long getDwWinCount() {
        return dwWinCount;
    }

    public void setDwWinCount(long dwWinCount) {
        this.dwWinCount = dwWinCount;
    }

    public long getDwLostCount() {
        return dwLostCount;
    }

    public void setDwLostCount(long dwLostCount) {
        this.dwLostCount = dwLostCount;
    }

    public long getDwDrawCount() {
        return dwDrawCount;
    }

    public void setDwDrawCount(long dwDrawCount) {
        this.dwDrawCount = dwDrawCount;
    }

    public long getDwFleeCount() {
        return dwFleeCount;
    }

    public void setDwFleeCount(long dwFleeCount) {
        this.dwFleeCount = dwFleeCount;
    }

    public long getDwExperience() {
        return dwExperience;
    }

    public void setDwExperience(long dwExperience) {
        this.dwExperience = dwExperience;
    }

    public String getSzNickname() {
        return szNickname;
    }

    public void setSzNickname(String szNickname) {
        this.szNickname = szNickname;
    }

    public String getSzHeadUrl() {
        return szHeadUrl;
    }

    public void setSzHeadUrl(String szHeadUrl) {
        this.szHeadUrl = szHeadUrl;
    }

    public long getSzIPAddress() {
        return szIPAddress;
    }

    public void setSzIPAddress(long szIPAddress) {
        this.szIPAddress = szIPAddress;
    }

    public int getDwCredit() {
        return dwCredit;
    }

    public void setDwCredit(int dwCredit) {
        this.dwCredit = dwCredit;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "CMDUserEnter{" +
                "dwGameID=" + dwGameID +
                ", dwUserID=" + dwUserID +
                ", wFaceID=" + wFaceID +
                ", dwCustomID=" + dwCustomID +
                ", cbGender=" + cbGender +
                ", cbMemberOrder=" + cbMemberOrder +
                ", wTableID=" + wTableID +
                ", wChairID=" + wChairID +
                ", cbUserStatus=" + cbUserStatus +
                ", lScore=" + lScore +
                ", dwWinCount=" + dwWinCount +
                ", dwLostCount=" + dwLostCount +
                ", dwDrawCount=" + dwDrawCount +
                ", dwFleeCount=" + dwFleeCount +
                ", dwExperience=" + dwExperience +
                ", szNickname='" + szNickname + '\'' +
                ", szHeadUrl='" + szHeadUrl + '\'' +
                ", szIPAddress=" + szIPAddress +
                ", dwCredit=" + dwCredit +
                ", registerTime='" + registerTime + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
