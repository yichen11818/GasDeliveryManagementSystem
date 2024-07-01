package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.table.GasType;

/**
 * 图书类型信息表的增删改查
 * @author rsw
 *
 */
public class GasTypeAccess {
	
	/**
	 * 查询图书类型的id
	 * @throws SQLException 
	 */
	public int queryGasTypeid(String bt_name) throws SQLException {
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT bt_id from gasdms.booktype WHERE bt_name=?";
		PreparedStatement pr = conn.prepareStatement(sql);
		pr.setString(1, bt_name);
		ResultSet rs = pr.executeQuery();
		int booktype = 0;
		while (rs.next()) { // 遍历数据库的数据
			booktype = rs.getInt("bt_id");
		}
		Connect.closeMySQL();// 关闭连接
		return booktype;
	}
	
	/**
	 * 查询图书类型及其序号
	 */
	public List<GasType> queryGasType() throws SQLException {
		List<GasType> gasTypeData = new ArrayList<GasType>();
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT * FROM gasdms.booktype ORDER BY bt_id ASC";//查询并排序（升序）
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			GasType gasType =new GasType();
			gasType.setBt_id(rs.getInt("bt_id"));
			gasType.setBt_name(rs.getString("bt_name"));
			gasTypeData.add(gasType);
		}
		Connect.closeMySQL();// 关闭连接
		return gasTypeData;
	}
	/**
	 * 新增图书类型
	 */
	public int insertGasType(String bt_name) throws SQLException {
		String sql = "INSERT INTO gasdms.booktype(bt_name) VALUES(?);";
		Connect.update_public(sql, bt_name);
	
		int id=0;
		Connection conn = Connect.connectMySQL();
		String insterID="SELECT bt_id FROM gasdms.booktype WHERE bt_name=?";
		PreparedStatement pr = conn.prepareStatement(insterID);
		pr.setString(1, bt_name);
		ResultSet rs = pr.executeQuery();
		while (rs.next()) {
			id=rs.getInt("bt_id");
		}
		return id;
	}
	/**
	 * 删除图书类型
	 */
	public void deleteGasType(int bt_id) throws SQLException {
		String sql = "DELETE FROM gasdms.booktype WHERE bt_id=?";
		Connect.update_public(sql, bt_id);
	}
	/**
	 * 
	 * 修改图书类型
	 */
	public void updateGasType(String input_bookType, int bt_id) throws SQLException {
		String sql = "UPDATE gasdms.booktype SET bt_name='"+input_bookType+"' WHERE bt_id=?";
		Connect.update_public(sql, bt_id);
	}
}
