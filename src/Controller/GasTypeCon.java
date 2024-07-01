package Controller;

import java.sql.SQLException;

import Model.access.GasTypeAccess;
import Model.table.GasType;

/**
 * ͼ�����ͱ�Ŀ�����
 * 
 * @author rsw
 *
 */
public class GasTypeCon {
	GasTypeAccess bookTypeDao = new GasTypeAccess();

	/**
	 * ��ѯͼ������id
	 */
	public int queryBTid(String bt_name) throws SQLException {
		int bookType = bookTypeDao.queryGasTypeid(bt_name);
		return bookType;
	}

	/**
	 * �鿴ͼ������
	 */
	public Object[][] queryBookType() throws SQLException {
		Object[][] bookTypeData = new Object[bookTypeDao.queryGasType().size()][2];
		for (int i = 0; i < bookTypeData.length; i++) {
			GasType gasType = bookTypeDao.queryGasType().get(i);
			bookTypeData[i][0] = gasType.getBt_id();
			bookTypeData[i][1] = gasType.getBt_name();
		}
		return bookTypeData;
	}

	/**
	 * ����ͼ������
	 * 
	 * @param bt_name
	 * @throws SQLException
	 */
	public int insertBookType(String bt_name) throws SQLException {
		return bookTypeDao.insertGasType(bt_name);
	}

	/**
	 * ɾ��ͼ������
	 */
	public void deleteBookType(int bt_id) throws SQLException {
		bookTypeDao.deleteGasType(bt_id);
	}

	/**
	 * �޸�ͼ������
	 * 
	 * @param bt_id
	 * @throws SQLException
	 */
	public void updateBookType(String input_bookType, int bt_id) throws SQLException {
		bookTypeDao.updateGasType(input_bookType, bt_id);
	}

}
