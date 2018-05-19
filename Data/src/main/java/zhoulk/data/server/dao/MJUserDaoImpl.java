package zhoulk.data.server.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import zhoulk.data.server.entities.MJUser;
import zhoulk.data.server.mapping.MJUserDao;

import java.util.ArrayList;

/**
 * 实现图书类型数据访问
 *
 */

@Repository
public class MJUserDaoImpl implements MJUserDao {

	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	@Override
	public MJUser getMJUserByUid(String uid) {
		MJUserDao mjUserDao=sqlSession.getMapper(MJUserDao.class);
		return mjUserDao.getMJUserByUid(uid);
	}

    @Override
    public int insertMJUser(MJUser user) {
        MJUserDao mjUserDao=sqlSession.getMapper(MJUserDao.class);
        return mjUserDao.insertMJUser(user);
    }

	@Override
	public ArrayList<MJUser> getMJUserByUserIdAndPass(@Param("userId") long userId, @Param("password") String password) {
		MJUserDao mjUserDao=sqlSession.getMapper(MJUserDao.class);
		return mjUserDao.getMJUserByUserIdAndPass(userId, password);
	}
}
