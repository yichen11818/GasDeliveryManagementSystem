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
	GasAccess bd = new GasAccess();

	/**
	 * ��ȷ��ѯ��ģ����ѯ��������ͼ�����ͣ�
	 */
	public Vector<Vector<Object>> getVector(String ISBN,String b_name, String author) throws SQLException {
		Vector<Vector<Object>> Vdata = bd.inithavesold(ISBN,b_name, author);
		return Vdata;
	}

	/**
	 * ��ѯȫ��ͼ��
	 */
	public Vector<Vector<Object>> seleGas() throws SQLException {
		return bd.seleGas();
	}

	/**
	 * ��ȷ��ѯ��ģ����ѯ������ͼ�����ͣ�
	 */
	public Vector<Vector<Object>> getGas(String ISBN,String b_name, String author, String b_type) throws SQLException {
		Vector<Vector<Object>> gasData = bd.queryGas(ISBN,b_name, author, b_type);
		return gasData;
	}

	/**
	 * ��ѯͼ������
	 */
	public Vector<String> getB_type() throws SQLException {
		Vector<String> bt_name = bd.seleGas_type();
		return bt_name;
	}

	/**
	 * ��ѯͼ������id
	 */
	public int seleB_name(String name) throws SQLException {
		int count = bd.seleGas_name(name);
		return count;
	}

	/**
	 * ɾ��ͼ��
	 */
	public void dropGas(int b_id) throws SQLException {
		bd.dropGas(b_id);
	}

	/**
	 * ����ͼ��
	 */
	public void insterGas(String ISBN, String b_name, int gastype, String author, String press, double price,
			int inventory) throws SQLException {
		bd.insterGas(ISBN, b_name, gastype, author, press, price, inventory);
	}

	/**
	 * �޸�ͼ��
	 */
	public void updateGas(String ISBN, String b_name, String author, String press, double price, int inventory,
			int b_id) throws SQLException {
		bd.updateGas(ISBN, b_name, author, press, price, inventory, b_id);
	}

	/**
	 * ��ѯ�Ƿ���ͼ��߱���ͼ������ ɾ����ͼ������ǰ����֤û��ͼ��Ӧ�ô�ͼ������
	 */
	public boolean existGastype(int bt_id) throws SQLException {
		return bd.existGastype(bt_id);
	}
	

	/**
	 * ��ѯͼ��ISBN�Ƿ���� �����ڣ�����ע��
	 */
	public boolean isISBN(String ISBN) throws SQLException {
		return bd.isISBN(ISBN);
	}
}
