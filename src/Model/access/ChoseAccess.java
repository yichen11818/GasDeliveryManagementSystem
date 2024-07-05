package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

/**
 * ���Ĺ黹��Ϣ�����ɾ�Ĳ�
 * @author rsw
 *
 */
public class ChoseAccess {

	/**
	 * ����
	 * ���������Ϣ
	 */
	public boolean insertChose(String number, int g_name, int chosedate, int duedate,int g_id){
		boolean isCommit=false;
		Connection conn = Connect.connectMySQL();
		String sql = "INSERT INTO gasdms.chose(r_number,chose_b_id,chosedate,duedate,isreturn) VALUES(?,?,?,?,'0')";
		try {
			conn.setAutoCommit(false);//���Զ��ύ����Ϊfalse 
			PreparedStatement ptmt1 = conn.prepareStatement(sql);
			ptmt1.setString(1, number);
			ptmt1.setInt(2, g_name);
			ptmt1.setLong(3, chosedate);
			ptmt1.setLong(4, duedate);
			ptmt1.executeUpdate();
			
			String updateInvebtorySql="UPDATE gasdms.gas SET inventory=inventory-1 WHERE g_id=?;";
			PreparedStatement ptmt2 = conn.prepareStatement(updateInvebtorySql);
			ptmt2.setInt(1, g_id);
			ptmt2.executeUpdate();
			
			conn.commit();//�����������ɹ����ֶ��ύ  
			isCommit=true;
		} catch (SQLException e) {
			try {
				System.out.println("����ع�");
				isCommit=false;
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    //һ������һ�������������ع���ʹ�������������ɹ�  
			e.printStackTrace();
		}  
		Connect.closeMySQL();// �ر�����
		return isCommit;
	}
	/**
	 * ����
	 * ����ʵ�ʹ黹ʱ�䣬����isreturn���Ƿ�黹����0��Ϊ1
	 */
	public boolean returnChose(int returndate,int chose_id,int g_id){
		boolean isCommit=false;
		Connection conn = Connect.connectMySQL();
		try {
			String sql = "UPDATE gasdms.chose SET returndate=?,isreturn='1' WHERE chose_id=?";
			conn.setAutoCommit(false);//���Զ��ύ����Ϊfalse 
			PreparedStatement ptmt1 = conn.prepareStatement(sql);
			ptmt1.setInt(1, returndate);
			ptmt1.setInt(2, chose_id);
			ptmt1.executeUpdate();
			
			String updateInvebtorySql="UPDATE gasdms.gas SET inventory=inventory+1 WHERE g_id=?;";
			PreparedStatement ptmt2 = conn.prepareStatement(updateInvebtorySql);
			ptmt2.setInt(1, g_id);
			ptmt2.executeUpdate();
			conn.commit();//�����������ɹ����ֶ��ύ  
			isCommit=true;
		} catch (SQLException e) {
			try {
				System.out.println("����ع�");
				isCommit=false;
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    //һ������һ�������������ع���ʹ�������������ɹ�  
			e.printStackTrace();
			e.printStackTrace();
		}
		Connect.closeMySQL();// �ر�����
		return isCommit;	
	}	
	/**
	 * �鿴������Ϣ�ķ���
	 * @throws SQLException 
	 */
	public Vector<Vector<Object>> queryChoseInfo(String number1,String number2,boolean isreturn) throws SQLException {
		String sql ="SELECT chose_id,gas.g_name,bt_name,author,chosedate,duedate,returndate FROM gasdms.gas,gasdms.gastype,gasdms.chose WHERE gasdms.gas.gastype=gasdms.gastype.bt_id" +
				" AND chose.r_number=? AND chose.chose_b_id=gas.g_id AND gas.g_id IN (SELECT chose.chose_b_id FROM gasdms.chose WHERE r_number=? ) AND chose.isreturn=? ORDER BY chosedate DESC";
		return Connect.queryExact_public(sql,number1,number2,isreturn);
	}
	/**
	 * ��ѯ�����Ƿ񱻽���
	 * @throws SQLException 
	 */
	public boolean queryExistGas(int chose_b_id) throws SQLException {
		String sql="SELECT chose_b_id FROM gasdms.chose WHERE chose_b_id=? AND isreturn='0'";
		return Connect.exist(sql, chose_b_id);
	}
	/**
	 * ��ѯ�����Ƿ񱻽���
	 */
	public boolean queryIsChoseGas(int chose_b_id,String count)throws SQLException {
		String sql="SELECT chose_b_id FROM gasdms.chose WHERE chose_b_id=? AND r_number=? AND isreturn='0'";
		return Connect.exist(sql, chose_b_id,count);
	}
}
