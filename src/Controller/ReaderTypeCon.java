package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.UserTypeAccess;

/**
 * 读者类型表的控制器
 * @author rsw
 *
 */
public class ReaderTypeCon {
	UserTypeAccess readerTypeDao = new UserTypeAccess();

	/**
	 * 查询读者类型表的全部数据
	 */
	public Object[][] queryReaderType() throws SQLException {
		Object[][] data_readerType =  readerTypeDao.queryUserType();
		return data_readerType;
	}

	/**
	 * 查询读者类型
	 */
	public String[] getReaderType() throws SQLException {
		Object[][] data_readerType = readerTypeDao.queryUserType();
		String[] readerType = new String[data_readerType.length];
		for (int i = 0; i < data_readerType.length; i++) {
			readerType[i] = data_readerType[i][1].toString();
		}
		return readerType;
	}
	/**
	 * 查询读者类型的ID
	 */
	public int queryReaderTypeID(String reader_type) throws SQLException {
		int rt_id=readerTypeDao.queryUserTypeID(reader_type);
		return rt_id;
	}
	/**
	 * 查询个人权限
	 */
	public Vector<Vector<Object>> queryPersonalType(String count) throws SQLException {
		return readerTypeDao.queryPersonalType(count);
	}
	/**
	 * 新增读者类型
	 */
	public void insertReaderType(String rt_name,int maxcont,int maxday) throws SQLException {
		readerTypeDao.insertUserType(rt_name, maxcont, maxday);
	}
	/**
	 * 删除读者类型
	 */
	public void deleteRederType(int rt_id) throws SQLException {
		readerTypeDao.deleteUserType(rt_id);
	}
	/**
	 * 更新读者类型
	 */
	public void updateRederType(String readerType,int maxcount,int maxday,int rt_id) throws SQLException {
		readerTypeDao.updateUserType(readerType,maxcount ,maxday,rt_id);
	}
}
