package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.UserCommunityAccess;

/**
 * �������ͱ�Ŀ�����
 * @author rsw
 *
 */
public class UserCommunityCon {
	UserCommunityAccess readerTypeDao = new UserCommunityAccess();

	/**
	 * ��ѯ�������ͱ��ȫ������
	 */
	public Object[][] queryReaderType() throws SQLException {
		Object[][] data_readerType =  readerTypeDao.queryUserType();
		return data_readerType;
	}

	/**
	 * ��ѯ��������
	 */
	public String[] getUserCommunity() throws SQLException {
		Object[][] data_readerType = readerTypeDao.queryUserType();
		String[] readerType = new String[data_readerType.length];
		for (int i = 0; i < data_readerType.length; i++) {
			readerType[i] = data_readerType[i][1].toString();
		}
		return readerType;
	}
	/**
	 * ��ѯ�������͵�ID
	 */
	public int queryUserCommunityID(String user_community) throws SQLException {
		int u_id=readerTypeDao.queryUserTypeID(user_community);
		return u_id;
	}
	/**
	 * ��ѯ����Ȩ��
	 */
	public Vector<Vector<Object>> queryPersonalType(String count) throws SQLException {
		return readerTypeDao.queryPersonalType(count);
	}
	/**
	 * ������������
	 */
	public void insertReaderType(String u_community,int maxcont,int maxday) throws SQLException {
		readerTypeDao.insertUserType(u_community, maxcont, maxday);
	}
	/**
	 * ɾ����������
	 */
	public void deleteRederType(int u_id) throws SQLException {
		readerTypeDao.deleteUserType(u_id);
	}
	/**
	 * ���¶�������
	 */
	public void updateRederType(String readerType,int maxcount,int maxday,int u_id) throws SQLException {
		readerTypeDao.updateUserType(readerType,maxcount ,maxday,u_id);
	}
}
