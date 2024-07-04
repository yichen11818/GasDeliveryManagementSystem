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
	GasTypeAccess gasTypeDao = new GasTypeAccess();

	/**
	 * 查询图书类型id
	 */
	public int queryBTid(String bt_name) throws SQLException {
		int gasType = gasTypeDao.queryGasTypeid(bt_name);
		return gasType;
	}

	/**
	 * 查看图书类型
	 */
	public Object[][] queryGasType() throws SQLException {
		Object[][] gasTypeData = new Object[gasTypeDao.queryGasType().size()][2];
		for (int i = 0; i < gasTypeData.length; i++) {
			GasType gasType = gasTypeDao.queryGasType().get(i);
			gasTypeData[i][0] = gasType.getBt_id();
			gasTypeData[i][1] = gasType.getBt_name();
		}
		return gasTypeData;
	}

	/**
	 * 新增图书类型
	 * 
	 * @param bt_name
	 * @throws SQLException
	 */
	public int insertGasType(String bt_name) throws SQLException {
		return gasTypeDao.insertGasType(bt_name);
	}

	/**
	 * 删除图书类型
	 */
	public void deleteGasType(int bt_id) throws SQLException {
		gasTypeDao.deleteGasType(bt_id);
	}

	/**
	 * 修改图书类型
	 * 
	 * @param bt_id
	 * @throws SQLException
	 */
	public void updateGasType(String input_gasType, int bt_id) throws SQLException {
		gasTypeDao.updateGasType(input_gasType, bt_id);
	}

}
