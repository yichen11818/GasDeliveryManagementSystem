package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * 煤气信息表的增删改查
 * 
 *  
 *
 */
public class GasAccess {
	/**
	   * 精确查询、模糊查询（不包含煤气类型）
	 */
	public Vector<Vector<Object>> inithavesold(int g_id, String g_name, String author) throws SQLException {
		String sql = "SELECT g_id,g_name,gastype,author,price,inventory from gasdms.gas LEFT JOIN gasdms.gastype "
				+ "ON gasdms.gas.gastype=gasdms.gastype.bt_id WHERE (g_id LIKE ? OR g_name LIKE ? OR author LIKE ? )";
		return Connect.queryDim_public(sql, g_id, g_name, author);
	}

	/**
	 * 查询全部煤气
	 */
	public Vector<Vector<Object>> seleGas() throws SQLException {
		String sql = "SELECT g_id,g_name,gastype,author,price,inventory from gasdms.gas LEFT JOIN gasdms.gastype "
				+ "ON gasdms.gas.gastype=gasdms.gastype.bt_id ";
		return Connect.queryExact_public(sql);
	}

	/**
	 * 精确查询、模糊查询（包含煤气类型）
	 */
	public Vector<Vector<Object>> queryGas(int g_id, String g_name, String author, String b_type)
			throws SQLException {
		String sql = "SELECT g_id,g_name,gastype,author,price,inventory from gasdms.gas LEFT JOIN gasdms.gastype  "
				+ " ON gasdms.gas.gastype=gasdms.gastype.bt_id WHERE (g_id LIKE ? OR g_name LIKE ? OR author LIKE ? ) AND bt_name=? ";
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // 存储所有数据，里面每个小的Vector是存单行的
		Connection conn = Connect.connectMySQL();// 调用数据库的连接方法
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, "%" + g_id+ "%");
		ptmt.setString(2, "%" + g_name + "%");
		ptmt.setString(3, "%" + author + "%");
		ptmt.setString(4, b_type);
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// 就是这个存单行的，最后放到上面的大的Vector里面
			// 遍历数据库中每列的结果集 column需要遍历的列数
			for (int i = 1; i <= 8; i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		Connect.closeMySQL();// 关闭连接
		return dataVector;
	}

	/**
	 * 查询煤气类型
	 */
	public Vector<String> seleGas_type() throws SQLException {
		Connection conn = Connect.connectMySQL();
		Vector<String> bt_name = new Vector<String>();
		String sql = "SELECT bt_name from gasdms.gastype ORDER BY bt_id ASC";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			bt_name.add(rs.getString("bt_name"));
		}
		Connect.closeMySQL();// 关闭连接
		return bt_name;
	}

	/**
	 * 查询煤气类型id
	 */
	public int seleGas_name(String name) throws SQLException {
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT g_id from gasdms.gas WHERE g_name=?";
		int count = 0;
		PreparedStatement pr = conn.prepareStatement(sql);
		pr.setString(1, name);
		ResultSet rs = pr.executeQuery();
		while (rs.next()) { // 遍历数据库的数据
			count = rs.getInt(1);
		}
		Connect.closeMySQL();// 关闭连接
		return count;
	}

	/**
	 * 删除煤气
	 */
	public void dropGas(int g_id) throws SQLException {
		String sql = "DELETE FROM gasdms.gas WHERE g_id=?";
		Connect.update_public(sql, g_id);
	}

	/**
	 * 新增煤气
	 */
	public void insterGas(int g_id, String g_name, int gastype, String author, double price,
						  int inventory) throws SQLException {
		String sql = "INSERT INTO gasdms.gas(g_id,g_name,gastype,author,price,inventory) VALUES(?,?,?,?,?,?)";
		Connect.update_public(sql, g_id, g_name, gastype,author, price, inventory);
	}

	/**
	 * 修改煤气信息
	 */
	public void updateGas(int g_id, String g_name,  int gastype, String author,double price, int inventory
						 ) throws SQLException {
		String sql = "UPDATE gasdms.gas SET g_name=?,gastype=? ,author=?,price=?,inventory=? WHERE g_id=?";
		Connect.update_public(sql, g_id, g_name,gastype, author, price, inventory);
	}

	/**
	 * 查询是否有煤气具备此煤气类型 删除类煤气类型前，保证没有煤气应用此煤气类型
	 */
	public boolean existGastype(int bt_id) throws SQLException {
		String sql = "SELECT gastype FROM gasdms.gas WHERE gastype=?";
		return Connect.exist(sql, bt_id);
	}

	/**
	 * 查询煤气g_id是否存在 若存在，不能注册
	 */
	public boolean isgasId(int g_id) throws SQLException {
		String sql = "SELECT ISBN FROM gasdms.gas WHERE g_id=?";
		return Connect.exist(sql, g_id);
	}
}
