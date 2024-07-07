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
 * ú��������Ϣ�����ɾ�Ĳ�
 *  
 *
 */
public class GasTypeAccess {
	
	/**
	 * ��ѯú�����͵�id
	 * @throws SQLException 
	 */
	public int queryGasTypeid(String bt_name) throws SQLException {
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT bt_id from gasdms.gastype WHERE bt_name=?";
		PreparedStatement pr = conn.prepareStatement(sql);
		pr.setString(1, bt_name);
		ResultSet rs = pr.executeQuery();
		int gastype = 0;
		while (rs.next()) { // �������ݿ������
			gastype = rs.getInt("bt_id");
		}
		Connect.closeMySQL();// �ر�����
		return gastype;
	}
	
	/**
	 * ��ѯú�����ͼ������
	 */
	public List<GasType> queryGasType() throws SQLException {
		List<GasType> gasTypeData = new ArrayList<GasType>();
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT * FROM gasdms.gastype ORDER BY bt_id ASC";//��ѯ����������
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			GasType gasType =new GasType();
			gasType.setBt_id(rs.getInt("bt_id"));
			gasType.setBt_name(rs.getString("bt_name"));
			gasTypeData.add(gasType);
		}
		Connect.closeMySQL();// �ر�����
		return gasTypeData;
	}
	/**
	 * ����ú������
	 */
	public int insertGasType(String bt_name) throws SQLException {
		String sql = "INSERT INTO gasdms.gastype(bt_name) VALUES(?);";
		Connect.update_public(sql, bt_name);
	
		int id=0;
		Connection conn = Connect.connectMySQL();
		String insterID="SELECT bt_id FROM gasdms.gastype WHERE bt_name=?";
		PreparedStatement pr = conn.prepareStatement(insterID);
		pr.setString(1, bt_name);
		ResultSet rs = pr.executeQuery();
		while (rs.next()) {
			id=rs.getInt("bt_id");
		}
		return id;
	}
	/**
	 * ɾ��ú������
	 */
	public void deleteGasType(int bt_id) throws SQLException {
		String sql = "DELETE FROM gasdms.gastype WHERE bt_id=?";
		Connect.update_public(sql, bt_id);
	}
	/**
	 * 
	 * �޸�ú������
	 */
	public void updateGasType(String input_gasType, int bt_id) throws SQLException {
		String sql = "UPDATE gasdms.gastype SET bt_name='"+input_gasType+"' WHERE bt_id=?";
		Connect.update_public(sql, bt_id);
	}
}
