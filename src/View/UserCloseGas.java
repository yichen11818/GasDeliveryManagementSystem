package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.GasCon;
import Controller.ChoseCon;
import Tool.TableTool;
import Tool.TimeTool;

/**
 * ú��ѡ��
 */
public class UserCloseGas {
	GasCon gascon = new GasCon();
	ChoseCon chosecon = new ChoseCon();
	UserGas userGas =new UserGas();
	Object[][] choseDate = null;
	String getChoseName, user;
	int chose_id = -1;

	protected JPanel addPanel1() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup = new JPanel();
		jpanup.setLayout(null);
		panel.setPreferredSize(new Dimension(1000, 700));
		jpanup.setPreferredSize(new Dimension(1000, 80));
		JButton[] jbt_return = { new JButton("��ѡú��"), new JButton("ȡ��ú��"), new JButton("�鿴��ʷ") };
		for (int i = 0; i < jbt_return.length; i++) {
			jbt_return[i].setBounds(200 + i * 150, 20, 100, 40);
			jpanup.add(jbt_return[i]);
		}
		Object[] columnChose = { "ʹ�����", "ú����","ú������", "��Ӧ����", "ʹ����ʼʱ��", "ʹ�ý���ʱ��", "ʵ��ʹ��ʱ��" };
		try {
			choseDate = chosecon.queryChoseInfo(UserFace.count, UserFace.count, false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel dfttableChose = new DefaultTableModel(choseDate, columnChose);
		JTable tableChose = new JTable(dfttableChose) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = tableChose.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if (tableChose.getSelectedRow() < 0) {
						return;
					}
					int counts = tableChose.getSelectedRow();// ��ȡ��ѡ�е��кţ���¼��
					getChoseName = tableChose.getValueAt(counts, 2).toString();// ��ȡ���ȡ�кŵ�ĳһ��
					chose_id = Integer.valueOf(tableChose.getValueAt(counts, 0).toString());
				}
			}
		});
		TableTool.setTable(tableChose, dfttableChose);
		// ������Χ���ֹ�����
		JScrollPane scrollPane = new JScrollPane(tableChose);
		panel.add(jpanup, BorderLayout.NORTH);
		panel.add(scrollPane);
		jbt_return[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbt_return[1].setEnabled(true);
				dfttableChose.setRowCount(0);
				dfttableChose.fireTableDataChanged();
				try {
					choseDate = chosecon.queryChoseInfo(UserFace.count, UserFace.count, false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dfttableChose.setDataVector(choseDate, columnChose);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
				chose_id = TableTool.cancelTableSelected(tableChose, chose_id);
			}
		});
		// �黹ͼ��
		jbt_return[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chose_id != -1) {
					int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ȡ����ú��", "��֤����", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						String returnGas = "inventory=inventory+1";
						try {
							// ����ʵ�ʹ黹ʱ��
							if(chosecon.returnChose(TimeTool.getNewStamep(), chose_id,gascon.seleB_name(getChoseName))) {
							dfttableChose.removeRow(tableChose.getSelectedRow());// �黹ͼ�飬ɾ��ѡ�еĴ���
							JOptionPane.showMessageDialog(null, "���Ѿ��ɹ�ȡ��ʹ��" + getChoseName, "�����ɹ�",
									JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} // ���ı����е��������Σ��Ե������޸ġ��������
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ��ú����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				chose_id = TableTool.cancelTableSelected(tableChose, chose_id);
			}
		});
		// ������鿴��ʷ���������ĸ�����ʷ������Ϣ(������½��ĵ�����ǰ��)
		jbt_return[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbt_return[1].setEnabled(false);
				dfttableChose.fireTableDataChanged();
				try {
					choseDate = chosecon.queryChoseReturnDate(UserFace.count, UserFace.count, true);
					dfttableChose.setDataVector(choseDate, columnChose);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
					tableChose.setModel(dfttableChose);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				chose_id = TableTool.cancelTableSelected(tableChose, chose_id);
			}
		});
		return panel;
	}

}
