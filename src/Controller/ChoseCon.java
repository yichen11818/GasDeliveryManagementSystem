package Controller;

import java.sql.SQLException;

import Model.access.ChoseAccess;
import Tool.TimeTool;

/**
 * ���Ĺ黹��Ŀ�����
 * @author rsw
 *
 */
public class ChoseCon {
	ChoseAccess chosedao = new ChoseAccess();

	public boolean insertChose(String number, int g_name, int Chosedate, int duedate,int g_id) throws SQLException {
		return chosedao.insertChose(number, g_name, Chosedate, duedate,g_id);
	}

	/**
	 * ��ѯ������Ϣ
	 */
	public Object[][] queryChoseInfo(String number1, String number2, boolean isreturn) throws SQLException {
		Object[][] ChoseData = new Object[chosedao.queryChoseInfo(number1, number2, isreturn).size()][7];
		for (int i = 0; i < ChoseData.length; i++) {
			ChoseData[i][0] = chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(0);
			ChoseData[i][1] = chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(1);
			ChoseData[i][2] = chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(2);
			ChoseData[i][3] = chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(3);

			ChoseData[i][4] = TimeTool.stampToDate(
					chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(4).toString());
			ChoseData[i][5] = TimeTool.stampToDate(
					chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(5).toString());

		}
		return ChoseData;
	}
	/**
	 * ��ȡʵ�ʹ黹ʱ��
	 */
	public Object[][] queryChoseReturnDate(String number1, String number2, boolean isreturn) throws SQLException {
		Object[][] ChoseData = new Object[chosedao.queryChoseInfo(number1, number2, isreturn).size()][7];
		for (int i = 0; i < ChoseData.length; i++) {
			ChoseData[i][0] = chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(0);
			ChoseData[i][1] = chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(1);
			ChoseData[i][2] = chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(2);
			ChoseData[i][3] = chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(3);

			ChoseData[i][4] = TimeTool.stampToDate(
					chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(4).toString());
			ChoseData[i][5] = TimeTool.stampToDate(
					chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(5).toString());
			ChoseData[i][6] = TimeTool.stampToDate(
					chosedao.queryChoseInfo(number1, number2, isreturn).elementAt(i).elementAt(6).toString());
		}
		return ChoseData;
	}
	/**
	 * ͼ��黹
	 */
	public boolean returnChose(int returndate,int chose_id,int g_id) throws SQLException {
		return chosedao.returnChose(returndate,chose_id,g_id);
	}
	/**
	 * ��ѯ�����Ƿ񱻽���
	 * @throws SQLException 
	 */
	public boolean queryExistGas(int chose_b_id) throws SQLException {
		return chosedao.queryExistGas(chose_b_id);
	}
	/**
	 * ��ѯ�����Ƿ񱻽���
	 */
	public boolean queryIsChoseGas(int chose_b_id,String count)throws SQLException {
		return chosedao.queryIsChoseGas(chose_b_id, count);
	}
}
