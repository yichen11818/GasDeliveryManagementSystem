package Controller;

import java.sql.SQLException;

import Model.access.GasTypeAccess;
import Model.table.GasType;

/**
 * 图书类型表的控制器
 * 
 * @author rsw
 *
 */
public class GasTypeCon {
	GasTypeAccess bookTypeDao = new GasTypeAccess();

	/**
	 * 查询图书类型id
	 */
	public int queryBTid(String bt_name) throws SQLException {
		int bookType = bookTypeDao.queryGasTypeid(bt_name);
		return bookType;
	}

	/**
	 * 查看图书类型
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
	 * 新增图书类型
	 * 
	 * @param bt_name
	 * @throws SQLException
	 */
	public int insertBookType(String bt_name) throws SQLException {
		return bookTypeDao.insertGasType(bt_name);
	}

	/**
	 * 删除图书类型
	 */
	public void deleteBookType(int bt_id) throws SQLException {
		bookTypeDao.deleteGasType(bt_id);
	}

	/**
	 * 修改图书类型
	 * 
	 * @param bt_id
	 * @throws SQLException
	 */
	public void updateBookType(String input_bookType, int bt_id) throws SQLException {
		bookTypeDao.updateGasType(input_bookType, bt_id);
	}

}
