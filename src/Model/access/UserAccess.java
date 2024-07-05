package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 用户信息表的增删改查
 * 
 *  
 *
 */
public class UserAccess {

	
	public void insertUser(String r_number, String r_name, String community, String buildings,
						   String r_tele, String r_email, String keeppass, String r_password) throws SQLException {
		String sql = "INSERT INTO gasdms.user(number,name,community,buildings,tele,email,keeppass,password) VALUES(?,?,?,?,?,?,?,?)";
		Connect.update_public(sql, r_number, r_name, community, buildings , r_tele, r_email, keeppass,
				r_password);
	}

	/**
	 * 查询账号是否存在 用于用户注册和新增用户（保证账号唯一性）
	 */
	public boolean isExist(String r_number) throws SQLException {
		String sql = "SELECT number FROM gasdms.user WHERE number=?";
		return Connect.exist(sql, r_number);
	}

	/**
	 * 查询账号密码是否存在
	 */
	public boolean queryUser(String r_number, String r_password) throws SQLException {
		String sql = "SELECT number,password FROM gasdms.user WHERE number=? AND password=?";
		return Connect.exist(sql, r_number, r_password);
	}

	/**
	 * 查询个人信息
	 */
	public Vector<Vector<Object>> queryUserInfo(String count) throws SQLException {
		String sql = "SELECT * FROM gasdms.user WHERE number=?";
		return Connect.queryExact_public(sql, count);
	}

	/**
	 * 修改用户信息
	 */
	public void updateUser(  String tele, String email, String number)
			throws SQLException {
		String sql = "UPDATE gasdms.user SET tele=?,email=? WHERE number=?";
		Connect.update_public(sql,  tele, email, number);
	}

	/**
	 * 修改密码
	 * 
	 * @throws SQLException
	 */
	public void updateUserPass(String alterPass, String r_number, String r_password, String r_keepPass)
			throws SQLException {
		String sql = "UPDATE gasdms.user SET password='" + alterPass
				+ "' WHERE number=? AND password=? AND keeppass=?";
		Connect.update_public(sql, r_number, r_password, r_keepPass);
	}

	/**
	 * 查询 全部用户
	 */
	public Vector<Vector<Object>> seleUser() throws SQLException {
		String sql = "SELECT number,name,buildings,u_community,tele,email,logindate from gasdms.user,gasdms.usercommunity where gasdms.user.user_community=gasdms.usercommunity.u_id";
		return Connect.queryExact_public(sql);
	}

	/**
	 * 查询用户的信息 模糊查寻
	 */
	public Vector<Vector<Object>> queryUserInfo(String number, String name)
			throws SQLException {
		String sql = "SELECT number,name,community,buildings,tele,email,logindate from gasdms.user,gasdms.usercommunity "
				+ "where gasdms.user.user_community=gasdms.usercommunity.u_id AND (number LIKE ? OR name LIKE ? OR dept LIKE ? OR classes LIKE ?)";
		return Connect.queryDim_public(sql, number, name);
	}

	/**
	 * 查询用户的信息 模糊查寻 类型查询 重写原因，用户类型如果模糊查寻，会出现用户类型相似性问题
	 */
	public Vector<Vector<Object>> seleUserInfo(String number, String name,
											   String user_type) throws SQLException {
		String sql = "SELECT number,name,u_community,buildings,tele,email,logindate from gasdms.user,gasdms.usercommunity "
				+ "where gasdms.user.user_community=gasdms.usercommunity.u_id AND (number LIKE ? OR name LIKE ? OR dept LIKE ? OR classes LIKE ?) AND u_community=?";
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // 存储所有数据，里面每个小的Vector是存单行的
		Connection conn = Connect.connectMySQL();// 调用数据库的连接方法
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, "%" + number + "%");
		ptmt.setString(2, "%" + name + "%");
		ptmt.setString(3, user_type);
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// 就是这个存单行的，最后放到上面的大的Vector里面
			// 遍历数据库中每列的结果集 column需要遍历的列数
			for (int i = 1; i <= 9; i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		Connect.closeMySQL();// 关闭连接
		return dataVector;
	}

	/**
	 * 删除用户信息
	 */
	public void dropUser(String studentNumber) throws SQLException {
		String sql = "DELETE FROM gasdms.user WHERE number=?";
		Connect.update_public(sql, studentNumber);
	}

	/**
	 * 忘记密码 密保验证
	 */
	public boolean queryKeeppass(String forgetPass, String count) throws SQLException {
		String sql = "SELECT keeppass FROM gasdms.user WHERE keeppass=? AND number=?";
		return Connect.exist(sql, forgetPass, count);
	}

	/**
	 * 忘记密码后重置密码
	 */
	public void resetPass(String forgetPass, String count, String newPass) throws SQLException {
		String sql = "UPDATE gasdms.user SET password='" + newPass + "' WHERE keeppass=? AND number=? ";
		Connect.update_public(sql, forgetPass, count);
	}

	/**
	 * 查询是否有用户具备此用户类型 删除类用户类型前，保证没有用户应用此用户类型
	 */
	public boolean existUsertype(int u_id) throws SQLException {
		String sql = "SELECT user_community FROM gasdms.user WHERE user_community=?";
		return Connect.exist(sql, u_id);

	}
}
