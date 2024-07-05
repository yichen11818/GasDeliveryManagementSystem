package Controller;

import java.sql.SQLException;
import java.util.Vector;

import Model.access.GasAccess;

/**
 * ͼ����Ϣ��Ŀ�����
 * @author rsw
 *
 */
public class GasCon {
	GasAccess gasaccess = new GasAccess();

	/**
	 * ��ȷ��ѯ��ģ����ѯ��������ͼ�����ͣ�
	 */
	public Vector<Vector<Object>> getVector(int g_id,String g_name, String author) throws SQLException {
		Vector<Vector<Object>> Vdata = gasaccess.inithavesold(g_id,g_name, author);
		return Vdata;
	}

	/**
	 * ��ѯȫ��ͼ��
	 */
	public Vector<Vector<Object>> seleGas() throws SQLException {
		return gasaccess.seleGas();
	}

	/**
	 * ��ȷ��ѯ��ģ����ѯ������ͼ�����ͣ�
	 */
	public Vector<Vector<Object>> getGas(int g_id,String g_name, String author, String b_type) throws SQLException {
		Vector<Vector<Object>> gasData = gasaccess.queryGas(g_id,g_name, author, b_type);
		return gasData;
	}

	/**
	 * ��ѯͼ������
	 */
	public Vector<String> getB_type() throws SQLException {
		Vector<String> bt_name = gasaccess.seleGas_type();
		return bt_name;
	}

	/**
	 * ��ѯͼ������id
	 */
	public int seleB_name(String name) throws SQLException {
		int count = gasaccess.seleGas_name(name);
		return count;
	}

	/**
	 * ɾ��ͼ��
	 */
	public void dropGas(int g_id) throws SQLException {
		gasaccess.dropGas(g_id);
	}

	/**
	 * ����ͼ��
	 */
	public void insterGas(int g_id, String g_name, int gastype, String author , double price,
			int inventory) throws SQLException {
		gasaccess.insterGas(g_id, g_name, gastype, author, price, inventory);
	}

	/**
	 * �޸�ͼ��
	 */
	public void updateGas(int g_id, String g_name, String author, double price, int inventory) throws SQLException {
		gasaccess.updateGas(g_id, g_name, author, price, inventory );
	}

	/**
	 * ��ѯ�Ƿ���ͼ��߱���ͼ������ ɾ����ͼ������ǰ����֤û��ͼ��Ӧ�ô�ͼ������
	 */
	public boolean existGastype(int bt_id) throws SQLException {
		return gasaccess.existGastype(bt_id);
	}
	

	/**
	 * ��ѯͼ��ISBN�Ƿ���� �����ڣ�����ע��
	 */
	public boolean isGASID(int g_id) throws SQLException {
		return gasaccess.isgasId(g_id);
	}
}
