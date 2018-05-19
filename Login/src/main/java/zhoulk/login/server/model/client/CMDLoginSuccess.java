package zhoulk.login.server.model.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import zhoulk.tool.convert.ConvertUtils;
import zhoulk.tool.model.CMDContent;

import java.sql.Date;

/**
 * Created by zlk on 2018/4/23.
 */
public class CMDLoginSuccess extends CMDContent {
    @Override
    protected void decode0(ByteBuf in) {
        uid = ConvertUtils.ByteBufToString(in, 66);
        headId = in.readIntLE();
        userId = in.readIntLE();
        gameId = in.readIntLE();
        exp = in.readIntLE();
        loveLines = in.readIntLE();
        userScore = in.readLongLE();
        userGold = in.readLongLE();
        userInsure = in.readLongLE();
        userBeans = in.readLongLE();
        faceId = in.readUnsignedShortLE();
        gender = in.readUnsignedByte();
        enable = in.readUnsignedByte();
        nickName = ConvertUtils.ByteBufToString(in, 64);
        pass = ConvertUtils.ByteBufToString(in, 66);
        agentId = in.readIntLE();
    }

    @Override
    protected ByteBuf encode0() {
        ByteBuf buf = Unpooled.buffer(len());
        //buf.writeBytes(ConvertUtils.StringToBytes(uid, 66));
        buf.writeIntLE(headId);
        buf.writeIntLE(userId);
        buf.writeIntLE(gameId);
        buf.writeIntLE(exp);
        buf.writeIntLE(loveLines);
        buf.writeLongLE(userScore);
        buf.writeLongLE(userGold);
        buf.writeLongLE(userInsure);
        buf.writeLongLE(userBeans);
        buf.writeShortLE(faceId);
        buf.writeByte(gender);
        buf.writeByte(enable);
        buf.writeBytes(ConvertUtils.StringToBytes(nickName, 64));
        buf.writeBytes(ConvertUtils.StringToBytes(pass, 66));
        buf.writeIntLE(agentId);
        return buf;
    }

    @Override
    public int len() {
        return 190;
    }

    // '用户标识符，微信登录 unionId，游客登录 ceshi+[0-1000]'
    private String uid; //66
    //'自定头像'
    private int headId; //70
    //'用户 I D'
    private int userId; //74
    //'游戏 I D'
    private int gameId; // 78
    //'经验数值'
    private int exp; //82
    //'用户魅力'
    private int loveLines; // 86
    //'用户游戏币'
    private long userScore; // 94
    //'用户元宝'
    private long userGold; //102
    //'用户银行'
    private long userInsure; //110
    //'用户游戏豆 //钻石'
    private long userBeans; //118
    //'头像标识'
    private int faceId; //120
    //用户性别'
    private int gender; // 121
    //'使能标识'
    private int enable; //122
    //'用户昵称'
    private String nickName; //64 186
    //动态密码
    private String pass; //66  252
    //上级代理号
    private int agentId; //4  256

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getHeadId() {
        return headId;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLoveLines() {
        return loveLines;
    }

    public void setLoveLines(int loveLines) {
        this.loveLines = loveLines;
    }

    public long getUserScore() {
        return userScore;
    }

    public void setUserScore(long userScore) {
        this.userScore = userScore;
    }

    public long getUserGold() {
        return userGold;
    }

    public void setUserGold(long userGold) {
        this.userGold = userGold;
    }

    public long getUserInsure() {
        return userInsure;
    }

    public void setUserInsure(long userInsure) {
        this.userInsure = userInsure;
    }

    public long getUserBeans() {
        return userBeans;
    }

    public void setUserBeans(long userBeans) {
        this.userBeans = userBeans;
    }

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "MJUser{" +
                ", uid='" + uid + '\'' +
                ", headId=" + headId +
                ", userId=" + userId +
                ", gameId=" + gameId +
                ", exp=" + exp +
                ", loveLines=" + loveLines +
                ", userScore=" + userScore +
                ", userGold=" + userGold +
                ", userInsure=" + userInsure +
                ", userBeans=" + userBeans +
                ", faceId=" + faceId +
                ", gender=" + gender +
                ", enable=" + enable +
                ", nickName='" + nickName + '\'' +
                ", pass='" + pass + '\'' +
                ", agentId=" + agentId +
                '}';
    }
}
