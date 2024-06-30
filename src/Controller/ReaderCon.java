package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.ReaderAccess;

/**
 * �û���Ϣ�������
 */
public class ReaderCon {
	ReaderAccess readerDao = new ReaderAccess();

	// ��reader���������Ϣ ע�����û�
	public void insertReader(String r_number, String r_name, String gender, int reader_type, String dept,
			String classes, String r_tele, String r_email, String keeppass, String r_password) throws SQLException {
		readerDao.insertUser(r_number, r_name, gender, reader_type, dept, classes, r_tele, r_email, keeppass,
				r_password);
	}

	// ��ѯ�˺��Ƿ���� �����û�ע������� ��֤�˺�Ψһ�ԣ�
	public boolean isNumber(String r_number) throws SQLException {
		return readerDao.isExist(r_number);
	}

	// ��¼��֤
	public boolean queryRerader(String r_number, String r_password) throws SQLException {
		boolean find = readerDao.queryUser(r_number, r_password);
		return find;
	}

	// ��ѯ�û���Ϣ
	public Vector<Vector<Object>> queryReaderInfo(String r_number) throws SQLException {
		Vector<Vector<Object>> readerInfo = readerDao.queryUserInfo(r_number);
		return readerInfo;
	}

	// �޸��û���Ϣ
	public void updateReader(String dept, String classes, String tele, String email, String number)
			throws SQLException {
		readerDao.updateUser(dept, classes, tele, email, number);
	}

	// �޸��û�����

	public void updateReaderPass(String alterPass, String r_number, String r_password, String r_keepPass)
			throws SQLException {
		readerDao.updateUserPass(alterPass, r_number, r_password, r_keepPass);
	}

	// ��ѯ ȫ���û�
	public Vector<Vector<Object>> seleReader() throws SQLException {
		return readerDao.seleUser();
	}

	// ��ѯ����������Ϣ
	public Vector<Vector<Object>> queryReaderInfo(String number, String name, String dept, String classes)
			throws SQLException {
		Vector<Vector<Object>> readerInfo = readerDao.queryUserInfo(number, name, dept, classes);
		return readerInfo;
	}

	// ��ѯ���ߵ���Ϣ ģ����Ѱ ���Ͳ�ѯ

	public Vector<Vector<Object>> seleReaderInfo(String number, String name, String dept, String classes,
			String reader_type) throws SQLException {
		return readerDao.seleUserInfo(number, name, dept, classes, reader_type);
	}

	// ɾ������

	public void dropReader(String studentNumber) throws SQLException {
		readerDao.dropUser(studentNumber);
	}

	// �������� �ܱ���֤

	public boolean queryKeeppass(String forgetPass, String count) throws SQLException {
		return readerDao.queryKeeppass(forgetPass, count);
	}

	// �����������������

	public void resetPass(String forgetPass, String count, String newPass) throws SQLException {
		readerDao.resetPass(forgetPass, count, newPass);
	}
	
	/**
	 * ��ѯ�Ƿ��ж��߾߱��˶�������
	 * ɾ�����������ǰ����֤û�ж���Ӧ�ô˶�������
	 */
	public boolean existReadertype(int rt_id) throws SQLException {
		return  readerDao.existUsertype(rt_id);
	}
}
