package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.UserAccess;

/**
 * �û���Ϣ�������
 */
public class UserCon {
	UserAccess userDao = new UserAccess();

	// ��user���������Ϣ ע�����û�
	public void insertReader(String text, String r_number, int i, String r_name, String community , String buildings,
							 String r_tele, String r_email, String keeppass, String r_password) throws SQLException {
		userDao.insertUser(r_number, r_name, community,buildings,  r_tele, r_email, keeppass,
				r_password);
	}

	// ��ѯ�˺��Ƿ���� �����û�ע������� ��֤�˺�Ψһ�ԣ�
	public boolean isNumber(String r_number) throws SQLException {
		return userDao.isExist(r_number);
	}

	// ��¼��֤
	public boolean queryRerader(String r_number, String r_password) throws SQLException {
		boolean find = userDao.queryUser(r_number, r_password);
		return find;
	}

	// ��ѯ�û���Ϣ
	public Vector<Vector<Object>> queryUserInfo(String r_number) throws SQLException {
		Vector<Vector<Object>> userInfo = userDao.queryUserInfo(r_number);
		return userInfo;
	}

	// �޸��û���Ϣ
	public void updateUser(String tele, String email, String number)
			throws SQLException {
		userDao.updateUser( tele, email, number);
	}

	// �޸��û�����

	public void updateUserPass(String alterPass, String r_number, String r_password, String r_keepPass)
			throws SQLException {
		userDao.updateUserPass(alterPass, r_number, r_password, r_keepPass);
	}

	// ��ѯ ȫ���û�
	public Vector<Vector<Object>> seleUser() throws SQLException {
		return userDao.seleUser();
	}

	// ��ѯ�û�������Ϣ
	public Vector<Vector<Object>> queryUserInfo(String number, String name)
			throws SQLException {
		Vector<Vector<Object>> userInfo = userDao.queryUserInfo(number, name);
		return userInfo;
	}

	// ��ѯ�û�����Ϣ ģ����Ѱ ���Ͳ�ѯ

	public Vector<Vector<Object>> seleUserInfo(String number, String name,
											   String user_community) throws SQLException {
		return userDao.seleUserInfo(number, name, user_community);
	}

	// ɾ���û�

	public void dropReader(String studentNumber) throws SQLException {
		userDao.dropUser(studentNumber);
	}

	// �������� �ܱ���֤

	public boolean queryKeeppass(String forgetPass, String count) throws SQLException {
		return userDao.queryKeeppass(forgetPass, count);
	}

	// �����������������

	public void resetPass(String forgetPass, String count, String newPass) throws SQLException {
		userDao.resetPass(forgetPass, count, newPass);
	}
	
	/**
	 * ��ѯ�Ƿ����û��߱����û�����
	 * ɾ�����û�����ǰ����֤û���û�Ӧ�ô��û�����
	 */
	public boolean existReadertype(int u_id) throws SQLException {
		return  userDao.existUsertype(u_id);
	}
}
