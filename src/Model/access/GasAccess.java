package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * ú����Ϣ�����ɾ�Ĳ�
 * 
 *  
 *
 */
public class GasAccess {
	/**
	   * ��ȷ��ѯ��ģ����ѯ��������ú�����ͣ�
	 */
	public Vector<Vector<Object>> inithavesold(int g_id, String g_name, String author) throws SQLException {
		String sql = "SELECT g_id,g_name,gastype,author,price,inventory from gasdms.gas LEFT JOIN gasdms.gastype "
				+ "ON gasdms.gas.gastype=gasdms.gastype.bt_id WHERE (g_id LIKE ? OR g_name LIKE ? OR author LIKE ? )";
		return Connect.queryDim_public(sql, g_id, g_name, author);
	}

	/**
	 * ��ѯȫ��ú��
	 */
	public Vector<Vector<Object>> seleGas() throws SQLException {
		String sql = "SELECT g_id,g_name,gastype,author,price,inventory from gasdms.gas LEFT JOIN gasdms.gastype "
				+ "ON gasdms.gas.gastype=gasdms.gastype.bt_id ";
		return Connect.queryExact_public(sql);
	}

	/**
	 * ��ȷ��ѯ��ģ����ѯ������ú�����ͣ�
	 */
	public Vector<Vector<Object>> queryGas(int g_id, String g_name, String author, String b_type)
			throws SQLException {
		String sql = "SELECT g_id,g_name,gastype,author,price,inventory from gasdms.gas LEFT JOIN gasdms.gastype  "
				+ " ON gasdms.gas.gastype=gasdms.gastype.bt_id WHERE (g_id LIKE ? OR g_name LIKE ? OR author LIKE ? ) AND bt_name=? ";
		Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>(); // �洢�������ݣ�����ÿ��С��Vector�Ǵ浥�е�
		Connection conn = Connect.connectMySQL();// �������ݿ�����ӷ���
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, "%" + g_id+ "%");
		ptmt.setString(2, "%" + g_name + "%");
		ptmt.setString(3, "%" + author + "%");
		ptmt.setString(4, b_type);
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			Vector<Object> vec = new Vector<Object>();// ��������浥�еģ����ŵ�����Ĵ��Vector����
			// �������ݿ���ÿ�еĽ���� column��Ҫ����������
			for (int i = 1; i <= 8; i++) {
				vec.add(rs.getObject(i));
			}
			dataVector.add(vec);
		}
		Connect.closeMySQL();// �ر�����
		return dataVector;
	}

	/**
	 * ��ѯú������
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
		Connect.closeMySQL();// �ر�����
		return bt_name;
	}

	/**
	 * ��ѯú������id
	 */
	public int seleGas_name(String name) throws SQLException {
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT g_id from gasdms.gas WHERE g_name=?";
		int count = 0;
		PreparedStatement pr = conn.prepareStatement(sql);
		pr.setString(1, name);
		ResultSet rs = pr.executeQuery();
		while (rs.next()) { // �������ݿ������
			count = rs.getInt(1);
		}
		Connect.closeMySQL();// �ر�����
		return count;
	}

	/**
	 * ɾ��ú��
	 */
	public void dropGas(int g_id) throws SQLException {
		String sql = "DELETE FROM gasdms.gas WHERE g_id=?";
		Connect.update_public(sql, g_id);
	}

	/**
	 * ����ú��
	 */
	public void insterGas(int g_id, String g_name, int gastype, String author, double price,
						  int inventory) throws SQLException {
		String sql = "INSERT INTO gasdms.gas(g_id,g_name,gastype,author,price,inventory) VALUES(?,?,?,?,?,?)";
		Connect.update_public(sql, g_id, g_name, gastype,author, price, inventory);
	}

	/**
	 * �޸�ú����Ϣ
	 */
	public void updateGas(int g_id, String g_name,  int gastype, String author,double price, int inventory
						 ) throws SQLException {
		String sql = "UPDATE gasdms.gas SET g_name=?,gastype=? ,author=?,price=?,inventory=? WHERE g_id=?";
		Connect.update_public(sql, g_id, g_name,gastype, author, price, inventory);
	}

	/**
	 * ��ѯ�Ƿ���ú���߱���ú������ ɾ����ú������ǰ����֤û��ú��Ӧ�ô�ú������
	 */
	public boolean existGastype(int bt_id) throws SQLException {
		String sql = "SELECT gastype FROM gasdms.gas WHERE gastype=?";
		return Connect.exist(sql, bt_id);
	}

	/**
	 * ��ѯú��g_id�Ƿ���� �����ڣ�����ע��
	 */
	public boolean isgasId(int g_id) throws SQLException {
		String sql = "SELECT ISBN FROM gasdms.gas WHERE g_id=?";
		return Connect.exist(sql, g_id);
	}
}
