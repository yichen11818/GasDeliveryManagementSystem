package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * �û���Ϣ�����ɾ�Ĳ�
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
	 * ��ѯ�˺��Ƿ���� �����û�ע��������û�����֤�˺�Ψһ�ԣ�
	 */
	public boolean isExist(String r_number) throws SQLException {
		String sql = "SELECT number FROM gasdms.user WHERE number=?";
		return Connect.exist(sql, r_number);
	}

	/**
	 * ��ѯ�˺������Ƿ����
	 */
	public boolean queryUser(String r_number, String r_password) throws SQLException {
		String sql = "SELECT number,password FROM gasdms.user WHERE number=? AND password=?";
		return Connect.exist(sql, r_number, r_password);
	}

	/**
	 * ��ѯ������Ϣ
	 */
	public Vector<Vector<Object>> queryUserInfo(String count) throws SQLException {
		String sql = "SELECT * FROM gasdms.user WHERE number=?";
		return Connect.queryExact_public(sql, count);
	}

	/**
	 * �޸��û���Ϣ
	 */
	public void updateUser(  String tele, String email, String number)
			throws SQLException {
		String sql = "UPDATE gasdms.user SET tele=?,email=? WHERE number=?";
		Connect.update_public(sql,  tele, email, number);
	}

	/**
	 * �޸�����
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
	 * ��ѯ ȫ���û�
	 */
	public Vector<Vector<Object>> seleUser() throws SQLException {
		String sql = "SELECT number,name,buildings,u_community,tele,email,logindate from gasdms.user,gasdms.usercommunity where gasdms.user.user_community=gasdms.usercommunity.u_id";
		return Connect.queryExact_public(sql);
	}

	/**
	 * ��ѯ�û�����Ϣ ģ����Ѱ
	 */
	public Vector<Vector<Object>> queryUserInfo(String number, String name)
			throws SQLException {
		String sql = "SELECT number,name,community,buildings,tele,email,logindate from gasdms.user,gasdms.usercommunity "
				+ "where gasdms.user.user_community=gasdms.usercommunity.u_id AND (number LIKE ? OR name LIKE ? OR dept LIKE ? OR classes LIKE ?)";
		return Connect.queryDim_public(sql, number, name);
	}

	/**
	 * ��ѯ�û�����Ϣ ģ����Ѱ ���Ͳ�ѯ ��дԭ���û��������ģ����Ѱ��������û���������������
	 */
	public Vector<Vector<Object>> seleUserInfo(String number, String name,
											   String user_type) throws SQLException {
		String sql = "SELECT number,name,u_community,buildings,tele,email,logindate from gasdms.user,gasdms.usercommunity "
				+ "where gasdms.user.user_community=gasdms.usercommunity.u_id AND (number LIKE ? OR name LIKE ? OR dept LIKE ? OR classes LIKE ?) AND u_community=?";
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // �洢�������ݣ�����ÿ��С��Vector�Ǵ浥�е�
		Connection conn = Connect.connectMySQL();// �������ݿ�����ӷ���
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, "%" + number + "%");
		ptmt.setString(2, "%" + name + "%");
		ptmt.setString(3, user_type);
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// ��������浥�еģ����ŵ�����Ĵ��Vector����
			// �������ݿ���ÿ�еĽ���� column��Ҫ����������
			for (int i = 1; i <= 9; i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		Connect.closeMySQL();// �ر�����
		return dataVector;
	}

	/**
	 * ɾ���û���Ϣ
	 */
	public void dropUser(String studentNumber) throws SQLException {
		String sql = "DELETE FROM gasdms.user WHERE number=?";
		Connect.update_public(sql, studentNumber);
	}

	/**
	 * �������� �ܱ���֤
	 */
	public boolean queryKeeppass(String forgetPass, String count) throws SQLException {
		String sql = "SELECT keeppass FROM gasdms.user WHERE keeppass=? AND number=?";
		return Connect.exist(sql, forgetPass, count);
	}

	/**
	 * �����������������
	 */
	public void resetPass(String forgetPass, String count, String newPass) throws SQLException {
		String sql = "UPDATE gasdms.user SET password='" + newPass + "' WHERE keeppass=? AND number=? ";
		Connect.update_public(sql, forgetPass, count);
	}

	/**
	 * ��ѯ�Ƿ����û��߱����û����� ɾ�����û�����ǰ����֤û���û�Ӧ�ô��û�����
	 */
	public boolean existUsertype(int u_id) throws SQLException {
		String sql = "SELECT user_community FROM gasdms.user WHERE user_community=?";
		return Connect.exist(sql, u_id);

	}
}
