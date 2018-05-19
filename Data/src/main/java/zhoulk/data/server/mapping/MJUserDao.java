package zhoulk.data.server.mapping;

import org.apache.ibatis.annotations.Param;
import zhoulk.data.server.entities.MJUser;

import java.util.ArrayList;

/**
 * 图书类型数据访问接口
 *
 */
public interface MJUserDao {
	/*
	 * 获得所有图书类型
	 */
	public MJUser getMJUserByUid(@Param("uid") String uid);

	/**
	 * 插入用户信息
	 * @param user
     */
	public int insertMJUser(MJUser user);

	/**
	 * 根据用户名和密码登录
	 * @param userId
	 * @param password
     * @return
     */
	public ArrayList<MJUser> getMJUserByUserIdAndPass(@Param("userId") long userId, @Param("password") String password);
}
