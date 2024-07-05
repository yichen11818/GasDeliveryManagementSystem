package Model.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * ����������Ϣ�����ɾ�Ĳ�
 * @author rsw
 *
 */
public class UserCommunityAccess {

	/**
	 * ��ѯ��������
	 * @throws SQLException 
	 */
	public Object[][] queryUserType() throws SQLException{
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT * from gasdms.usercommunity";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		int len = 0;
		while(rs.next()){
			len++;//�����������֪�������ж���������
		}
		Object[][] data_readerType = new Object[len][4];
		rs.beforeFirst();
		for(int i=0;i<len ;i++) {
			rs.next();
			data_readerType[i][0] = rs.getInt("u_id");
			data_readerType[i][1] = rs.getString("u_community");
			data_readerType[i][2] = rs.getInt("maxcount");
			data_readerType[i][3] = rs.getInt("nowcount");
		}
		Connect.closeMySQL();// �ر�����
		return data_readerType;
	}
	/**
	 * ��ѯ�������͵�ID
	 */
	public int queryUserTypeID(String user_community) throws SQLException {
		Connection conn = Connect.connectMySQL();
		String sql = "SELECT u_id from gasdms.usercommunity WHERE u_community=?";
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, user_community);
		ResultSet rs = ptmt.executeQuery();
		int u_id=0;
		while (rs.next()) {
			u_id=rs.getInt("u_id");
		}
		Connect.closeMySQL();// �ر�����
		return u_id;
	}
	/**
	 * ��ѯ����Ȩ��
	 */
	public Vector<Vector<Object>> queryPersonalType(String count) throws SQLException {
		int column=3;
		String sql="SELECT u_community,maxcount,nowcount FROM gasdms.usercommunity "
				+ "WHERE u_id IN ( SELECT user_community FROM gasdms.user WHERE number=? )";
		return Connect.queryExact_public(sql,count);	
	}	
	/**
	 * ������������
	 * @throws SQLException 
	 */
	public void insertUserType(String u_community, int maxcont, int nowcount) throws SQLException {
		String sql = "INSERT INTO gasdms.usercommunity(u_community,maxcount,nowcount) VALUES(?,?,?)";
		Connect.update_public(sql, u_community,maxcont,nowcount);
	}
	/**
	 *ɾ����������
	 */
	public void deleteUserType(int u_id) throws SQLException {
		String sql = "DELETE FROM gasdms.usercommunity WHERE u_id=?";
		Connect.update_public(sql, u_id);
	}
	/**
	 * �޸Ķ�������
	 */
	public void updateUserType(String readerType, int maxcount, int nowcount, int u_id) throws SQLException {
		String sql = "UPDATE gasdms.usercommunity SET u_community=?,maxcount=?,nowcount=? WHERE u_id=?";
		Connect.update_public(sql,readerType,maxcount ,nowcount,u_id);
	}
}
