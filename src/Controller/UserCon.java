package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.UserAccess;

/**
 * 用户信息表控制器
 */
public class UserCon {
	UserAccess userDao = new UserAccess();

	// 向user表中添加信息 注册新用户
	public void insertReader(String text, String r_number, int i, String r_name, String community , String buildings,
							 String r_tele, String r_email, String keeppass, String r_password) throws SQLException {
		userDao.insertUser(r_number, r_name, community,buildings,  r_tele, r_email, keeppass,
				r_password);
	}

	// 查询账号是否存在 用于用户注册和新增 保证账号唯一性）
	public boolean isNumber(String r_number) throws SQLException {
		return userDao.isExist(r_number);
	}

	// 登录验证
	public boolean queryRerader(String r_number, String r_password) throws SQLException {
		boolean find = userDao.queryUser(r_number, r_password);
		return find;
	}

	// 查询用户信息
	public Vector<Vector<Object>> queryUserInfo(String r_number) throws SQLException {
		Vector<Vector<Object>> userInfo = userDao.queryUserInfo(r_number);
		return userInfo;
	}

	// 修改用户信息
	public void updateUser(String tele, String email, String number)
			throws SQLException {
		userDao.updateUser( tele, email, number);
	}

	// 修改用户密码

	public void updateUserPass(String alterPass, String r_number, String r_password, String r_keepPass)
			throws SQLException {
		userDao.updateUserPass(alterPass, r_number, r_password, r_keepPass);
	}

	// 查询 全部用户
	public Vector<Vector<Object>> seleUser() throws SQLException {
		return userDao.seleUser();
	}

	// 查询用户所有信息
	public Vector<Vector<Object>> queryUserInfo(String number, String name)
			throws SQLException {
		Vector<Vector<Object>> userInfo = userDao.queryUserInfo(number, name);
		return userInfo;
	}

	// 查询用户的信息 模糊查寻 类型查询

	public Vector<Vector<Object>> seleUserInfo(String number, String name,
											   String user_community) throws SQLException {
		return userDao.seleUserInfo(number, name, user_community);
	}

	// 删除用户

	public void dropReader(String studentNumber) throws SQLException {
		userDao.dropUser(studentNumber);
	}

	// 忘记密码 密保验证

	public boolean queryKeeppass(String forgetPass, String count) throws SQLException {
		return userDao.queryKeeppass(forgetPass, count);
	}

	// 忘记密码后重置密码

	public void resetPass(String forgetPass, String count, String newPass) throws SQLException {
		userDao.resetPass(forgetPass, count, newPass);
	}
	
	/**
	 * 查询是否有用户具备此用户类型
	 * 删除类用户类型前，保证没有用户应用此用户类型
	 */
	public boolean existReadertype(int u_id) throws SQLException {
		return  userDao.existUsertype(u_id);
	}
}
