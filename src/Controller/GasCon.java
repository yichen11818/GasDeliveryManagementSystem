package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.GasAccess;

/**
 * ú����Ϣ��Ŀ�����

 */
public class GasCon {
	GasAccess gasaccess = new GasAccess();

	/**
	 * ��ȷ��ѯ��ģ����ѯ��������ú�����ͣ�
	 */
	public Vector<Vector<Object>> getVector(int g_id,String g_name, String author) throws SQLException {
		Vector<Vector<Object>> Vdata = gasaccess.inithavesold(g_id,g_name, author);
		return Vdata;
	}

	/**
	 * ��ѯȫ��ú��
	 */
	public Vector<Vector<Object>> seleGas() throws SQLException {
		return gasaccess.seleGas();
	}

	/**
	 * ��ȷ��ѯ��ģ����ѯ������ú�����ͣ�
	 */
	public Vector<Vector<Object>> getGas(int g_id,String g_name, String author, String b_type) throws SQLException {
		Vector<Vector<Object>> gasData = gasaccess.queryGas(g_id,g_name, author, b_type);
		return gasData;
	}

	/**
	 * ��ѯú������
	 */
	public Vector<String> getB_type() throws SQLException {
		Vector<String> bt_name = gasaccess.seleGas_type();
		return bt_name;
	}

	/**
	 * ��ѯú������id
	 */
	public int seleB_name(String name) throws SQLException {
		int count = gasaccess.seleGas_name(name);
		return count;
	}

	/**
	 * ɾ��ú��
	 */
	public void dropGas(int g_id) throws SQLException {
		gasaccess.dropGas(g_id);
	}

	/**
	 * ����ú��
	 */
	public void insterGas(int g_id, String g_name, int gastype, String author , double price,
			int inventory) throws SQLException {
		gasaccess.insterGas(g_id, g_name, gastype, author, price, inventory);
	}

	/**
	 * �޸�ú��
	 */
	public void updateGas(int g_id, String g_name, int gastype,String author, double price, int inventory) throws SQLException {
		gasaccess.updateGas(g_id, g_name, gastype, author, price, inventory );
	}

	/**
	 * ��ѯ�Ƿ���ú���߱���ú������ ɾ����ú������ǰ����֤û��ú��Ӧ�ô�ú������
	 */
	public boolean existGastype(int bt_id) throws SQLException {
		return gasaccess.existGastype(bt_id);
	}
	

	/**
	 * ��ѯú��ISBN�Ƿ���� �����ڣ�����ע��
	 */
	public boolean isGASID(int g_id) throws SQLException {
		return gasaccess.isgasId(g_id);
	}
}
