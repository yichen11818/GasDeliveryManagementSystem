package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.GasAccess;

/**
 * 煤气信息表的控制器

 */
public class GasCon {
	GasAccess gasaccess = new GasAccess();

	/**
	 * 精确查询、模糊查询（不包含煤气类型）
	 */
	public Vector<Vector<Object>> getVector(int g_id,String g_name, String author) throws SQLException {
		Vector<Vector<Object>> Vdata = gasaccess.inithavesold(g_id,g_name, author);
		return Vdata;
	}

	/**
	 * 查询全部煤气
	 */
	public Vector<Vector<Object>> seleGas() throws SQLException {
		return gasaccess.seleGas();
	}

	/**
	 * 精确查询、模糊查询（包含煤气类型）
	 */
	public Vector<Vector<Object>> getGas(int g_id,String g_name, String author, String b_type) throws SQLException {
		Vector<Vector<Object>> gasData = gasaccess.queryGas(g_id,g_name, author, b_type);
		return gasData;
	}

	/**
	 * 查询煤气类型
	 */
	public Vector<String> getB_type() throws SQLException {
		Vector<String> bt_name = gasaccess.seleGas_type();
		return bt_name;
	}

	/**
	 * 查询煤气类型id
	 */
	public int seleB_name(String name) throws SQLException {
		int count = gasaccess.seleGas_name(name);
		return count;
	}

	/**
	 * 删除煤气
	 */
	public void dropGas(int g_id) throws SQLException {
		gasaccess.dropGas(g_id);
	}

	/**
	 * 新增煤气
	 */
	public void insterGas(int g_id, String g_name, int gastype, String author , double price,
			int inventory) throws SQLException {
		gasaccess.insterGas(g_id, g_name, gastype, author, price, inventory);
	}

	/**
	 * 修改煤气
	 */
	public void updateGas(int g_id, String g_name, int gastype,String author, double price, int inventory) throws SQLException {
		gasaccess.updateGas(g_id, g_name, gastype, author, price, inventory );
	}

	/**
	 * 查询是否有煤气具备此煤气类型 删除类煤气类型前，保证没有煤气应用此煤气类型
	 */
	public boolean existGastype(int bt_id) throws SQLException {
		return gasaccess.existGastype(bt_id);
	}
	

	/**
	 * 查询煤气ISBN是否存在 若存在，不能注册
	 */
	public boolean isGASID(int g_id) throws SQLException {
		return gasaccess.isgasId(g_id);
	}
}
