package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.ReaderAccess;

/**
 * 用户信息表控制器
 */
public class ReaderCon {
	ReaderAccess readerDao = new ReaderAccess();

	// 向reader表中添加信息 注册新用户
	public void insertReader(String r_number, String r_name, String gender, int reader_type, String dept,
			String classes, String r_tele, String r_email, String keeppass, String r_password) throws SQLException {
		readerDao.insertUser(r_number, r_name, gender, reader_type, dept, classes, r_tele, r_email, keeppass,
				r_password);
	}

	// 查询账号是否存在 用于用户注册和新增 保证账号唯一性）
	public boolean isNumber(String r_number) throws SQLException {
		return readerDao.isExist(r_number);
	}

	// 登录验证
	public boolean queryRerader(String r_number, String r_password) throws SQLException {
		boolean find = readerDao.queryUser(r_number, r_password);
		return find;
	}

	// 查询用户信息
	public Vector<Vector<Object>> queryReaderInfo(String r_number) throws SQLException {
		Vector<Vector<Object>> readerInfo = readerDao.queryUserInfo(r_number);
		return readerInfo;
	}

	// 修改用户信息
	public void updateReader(String dept, String classes, String tele, String email, String number)
			throws SQLException {
		readerDao.updateUser(dept, classes, tele, email, number);
	}

	// 修改用户密码

	public void updateReaderPass(String alterPass, String r_number, String r_password, String r_keepPass)
			throws SQLException {
		readerDao.updateUserPass(alterPass, r_number, r_password, r_keepPass);
	}

	// 查询 全部用户
	public Vector<Vector<Object>> seleReader() throws SQLException {
		return readerDao.seleUser();
	}

	// 查询读者所有信息
	public Vector<Vector<Object>> queryReaderInfo(String number, String name, String dept, String classes)
			throws SQLException {
		Vector<Vector<Object>> readerInfo = readerDao.queryUserInfo(number, name, dept, classes);
		return readerInfo;
	}

	// 查询读者的信息 模糊查寻 类型查询

	public Vector<Vector<Object>> seleReaderInfo(String number, String name, String dept, String classes,
			String reader_type) throws SQLException {
		return readerDao.seleUserInfo(number, name, dept, classes, reader_type);
	}

	// 删除读者

	public void dropReader(String studentNumber) throws SQLException {
		readerDao.dropUser(studentNumber);
	}

	// 忘记密码 密保验证

	public boolean queryKeeppass(String forgetPass, String count) throws SQLException {
		return readerDao.queryKeeppass(forgetPass, count);
	}

	// 忘记密码后重置密码

	public void resetPass(String forgetPass, String count, String newPass) throws SQLException {
		readerDao.resetPass(forgetPass, count, newPass);
	}
	
	/**
	 * 查询是否有读者具备此读者类型
	 * 删除类读者类型前，保证没有读者应用此读者类型
	 */
	public boolean existReadertype(int rt_id) throws SQLException {
		return  readerDao.existUsertype(rt_id);
	}
}
