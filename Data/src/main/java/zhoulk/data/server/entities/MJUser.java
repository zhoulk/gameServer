package zhoulk.data.server.entities;

import zhoulk.login.server.model.client.CMDLoginOtherPlatform;

import java.sql.Date;

/**
 * 图书类型
 *
 */
public class MJUser {

    public void convertFromModel(CMDLoginOtherPlatform loginOtherPlatform){
        uid = loginOtherPlatform.getSzUserUin();
    }

	private long id;
	// '用户标识符，微信登录 unionId，游客登录 ceshi+[0-1000]'
	private String uid;
	//'自定头像'
	private int headId;
	//'用户 I D'
	private int userId;
	//'游戏 I D'
	private int gameId;
	//'经验数值'
	private int exp;
	//'用户魅力'
	private int loveLines;
	//'用户游戏币'
	private int userScore;
	//'用户元宝'
	private int userGold;
	//'用户银行'
	private int userInsure;
	//'用户游戏豆 //钻石'
	private int userBeans;
	//'头像标识'
	private int faceId;
	//用户性别'
	private int gender;
	//'使能标识'
	private int enable;
	//'用户昵称'
	private String nickName;
	//动态密码
	private String pass;
	//上级代理号
	private int agentId;
	//
	private Date create_time;
	//
	private Date update_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public int getUserScore() {
		return userScore;
	}

	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}

	public int getUserGold() {
		return userGold;
	}

	public void setUserGold(int userGold) {
		this.userGold = userGold;
	}

	public int getUserInsure() {
		return userInsure;
	}

	public void setUserInsure(int userInsure) {
		this.userInsure = userInsure;
	}

	public int getUserBeans() {
		return userBeans;
	}

	public void setUserBeans(int userBeans) {
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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

    @Override
    public String toString() {
        return "MJUser{" +
                "id=" + id +
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
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
