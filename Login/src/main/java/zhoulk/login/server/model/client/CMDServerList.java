package zhoulk.login.server.model.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import zhoulk.tool.convert.ConvertUtils;
import zhoulk.tool.model.CMDContent;

/**
 * Created by zlk on 2018/4/24.
 */
public class CMDServerList extends CMDContent {
    @Override
    protected void decode0(ByteBuf in) {

    }

    @Override
    protected ByteBuf encode0() {
        ByteBuf buf = Unpooled.buffer(len());
        buf.writeShortLE(wKindID);
        buf.writeShortLE(wNodeID);
        buf.writeShortLE(wSortID);
        buf.writeShortLE(wServerID);
        buf.writeShortLE(wServerKind);
        buf.writeShortLE(wServerType);
        buf.writeShortLE(wServerPort);
        buf.writeLongLE(lCellScore);
        buf.writeLongLE(lEnterScore);
        buf.writeIntLE((int)dwServerRule);
        buf.writeIntLE((int)dwOnLineCount);
        buf.writeIntLE((int)dwAndroidCount);
        buf.writeIntLE((int)dwFullCount);
        buf.writeBytes(ConvertUtils.StringToBytes(szServerAddr, 64));
        buf.writeBytes(ConvertUtils.StringToBytes(szServerName, 64));
        buf.writeIntLE((int)dwLiveTime);
        buf.writeShortLE((int)wServerLevel);
        return buf;
    }

    @Override
    public int len() {
        return 180;
    }

    private int wKindID; //ushort 名称索引 用于游戏区分  2
    private int wNodeID; //ushort 节点索引  4
    private int wSortID; //ushort 排序索引  6
    private int wServerID; //ushort 房间索引  8
    private int wServerKind; //ushort 房间类型  10
    private int wServerType; //ushort 房间类型  12
    private int wServerPort; // ushort  房间端口  14
    private long lCellScore; //long 单元积分 22
    private long lEnterScore; //long 进入积分  30
    private long dwServerRule; //uint 房间规则 34
    private long dwOnLineCount; //uint 在线人数  38
    private long dwAndroidCount; // uint 机器人数  42
    private  long dwFullCount; //uint 满员人数  46
    private String szServerAddr; //string(32) 服务器地址 64  110
    private String szServerName; //String(32) 房间名称 64  174
    private long dwLiveTime; //uint 最近活动时间  178
    private int wServerLevel; //ushort 房间等级  180

    public int getwKindID() {
        return wKindID;
    }

    public void setwKindID(int wKindID) {
        this.wKindID = wKindID;
    }

    public int getwNodeID() {
        return wNodeID;
    }

    public void setwNodeID(int wNodeID) {
        this.wNodeID = wNodeID;
    }

    public int getwSortID() {
        return wSortID;
    }

    public void setwSortID(int wSortID) {
        this.wSortID = wSortID;
    }

    public int getwServerID() {
        return wServerID;
    }

    public void setwServerID(int wServerID) {
        this.wServerID = wServerID;
    }

    public int getwServerKind() {
        return wServerKind;
    }

    public void setwServerKind(int wServerKind) {
        this.wServerKind = wServerKind;
    }

    public int getwServerType() {
        return wServerType;
    }

    public void setwServerType(int wServerType) {
        this.wServerType = wServerType;
    }

    public int getwServerPort() {
        return wServerPort;
    }

    public void setwServerPort(int wServerPort) {
        this.wServerPort = wServerPort;
    }

    public long getlCellScore() {
        return lCellScore;
    }

    public void setlCellScore(long lCellScore) {
        this.lCellScore = lCellScore;
    }

    public long getlEnterScore() {
        return lEnterScore;
    }

    public void setlEnterScore(long lEnterScore) {
        this.lEnterScore = lEnterScore;
    }

    public long getDwServerRule() {
        return dwServerRule;
    }

    public void setDwServerRule(long dwServerRule) {
        this.dwServerRule = dwServerRule;
    }

    public long getDwOnLineCount() {
        return dwOnLineCount;
    }

    public void setDwOnLineCount(long dwOnLineCount) {
        this.dwOnLineCount = dwOnLineCount;
    }

    public long getDwAndroidCount() {
        return dwAndroidCount;
    }

    public void setDwAndroidCount(long dwAndroidCount) {
        this.dwAndroidCount = dwAndroidCount;
    }

    public long getDwFullCount() {
        return dwFullCount;
    }

    public void setDwFullCount(long dwFullCount) {
        this.dwFullCount = dwFullCount;
    }

    public String getSzServerAddr() {
        return szServerAddr;
    }

    public void setSzServerAddr(String szServerAddr) {
        this.szServerAddr = szServerAddr;
    }

    public String getSzServerName() {
        return szServerName;
    }

    public void setSzServerName(String szServerName) {
        this.szServerName = szServerName;
    }

    public long getDwLiveTime() {
        return dwLiveTime;
    }

    public void setDwLiveTime(long dwLiveTime) {
        this.dwLiveTime = dwLiveTime;
    }

    public int getwServerLevel() {
        return wServerLevel;
    }

    public void setwServerLevel(int wServerLevel) {
        this.wServerLevel = wServerLevel;
    }

    @Override
    public String toString() {
        return "CMDServerList{" +
                "wKindID=" + wKindID +
                ", wNodeID=" + wNodeID +
                ", wSortID=" + wSortID +
                ", wServerID=" + wServerID +
                ", wServerKind=" + wServerKind +
                ", wServerType=" + wServerType +
                ", wServerPort=" + wServerPort +
                ", lCellScore=" + lCellScore +
                ", lEnterScore=" + lEnterScore +
                ", dwServerRule=" + dwServerRule +
                ", dwOnLineCount=" + dwOnLineCount +
                ", dwAndroidCount=" + dwAndroidCount +
                ", dwFullCount=" + dwFullCount +
                ", szServerAddr='" + szServerAddr + '\'' +
                ", szServerName='" + szServerName + '\'' +
                ", dwLiveTime=" + dwLiveTime +
                ", wServerLevel=" + wServerLevel +
                '}';
    }
}
