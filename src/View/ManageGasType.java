
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
import Controller.GasTypeCon;
import Tool.InputLimit;
import Tool.TableTool;

/**
 * ú��������Ϣ�������
 *  
 */
public class ManageGasType {
	GasTypeCon gasTypeCon = new GasTypeCon();
	GasCon gasCon = new GasCon();
	int bt_id = -1;
	String bt_name;

	protected JPanel addPanel1() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_gasType = new JPanel();
		jpanup_gasType.setLayout(null);
		jpanup_gasType.setPreferredSize(new Dimension(1000, 80));
		JButton[] jbt_gasType = { new JButton("����ú������"), new JButton("ɾ��ú������"),
				new JButton("�޸�ú������") };
		for (int i = 0; i < jbt_gasType.length; i++) {
			jbt_gasType[i].setBounds(300 + i * 150, 20, 120, 30);
			jpanup_gasType.add(jbt_gasType[i]);
		}

		String[] columnGas = { "���", "ú������" };
		Object[][] gasTypeData = gasTypeCon.queryGasType();
		DefaultTableModel dfttable_gasType = new DefaultTableModel(gasTypeData, columnGas);
		JTable table_gasType = new JTable(dfttable_gasType) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = table_gasType.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					bt_id = Integer.valueOf(table_gasType.getValueAt(table_gasType.getSelectedRow(), 0).toString());
					bt_name = table_gasType.getValueAt(table_gasType.getSelectedRow(), 0).toString();
				}
			}
		});
		// ���ñ��Ĺ�������
		TableTool.setTable(table_gasType, dfttable_gasType);

		JScrollPane scrollPane = new JScrollPane(table_gasType);
		panel.add(jpanup_gasType, BorderLayout.NORTH);
		panel.add(scrollPane);
		// ����ú������
		jbt_gasType[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input_gasType = JOptionPane.showInputDialog(null, "����������Ҫ��ӵ�ú������", "����ú������",
						JOptionPane.YES_NO_OPTION);
				try {
					if (input_gasType != null && !input_gasType.equals("")) {
						String regex =InputLimit.CHINESE ;
						boolean result = InputLimit.regular(regex, input_gasType);
						if (result) {
							int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��������ú������", "��֤����",
									JOptionPane.YES_NO_OPTION);
							if (c == JOptionPane.YES_OPTION) {
								gasTypeCon.insertGasType(input_gasType);
								Object[] obj= {gasTypeCon.insertGasType(input_gasType),input_gasType};
								dfttable_gasType.addRow(obj);
								JOptionPane.showMessageDialog(null, "����ú�����͡���"+input_gasType, "�����ɹ�", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "������������ݸ�ʽ���󣡣���", "����ʧ��", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "�������������Ϊ�գ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				bt_id = TableTool.cancelTableSelected(table_gasType, bt_id);
			}
		});
		// ɾ��ú������
		jbt_gasType[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (bt_id != -1) {
						int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����ú������", "��֤����", JOptionPane.YES_NO_OPTION);
						if (c == JOptionPane.YES_OPTION) {
							if (gasCon.existGastype(bt_id)) {
								JOptionPane.showMessageDialog(null, "��ú�������Ѿ���ú��ʹ�ã��볢�Խ���ú�����͵�ú��ɾ������ɾ����ú�����ͣ�����", "����ʧ��",
										JOptionPane.ERROR_MESSAGE);
							} else {
								gasTypeCon.deleteGasType(bt_id);
								dfttable_gasType.removeRow(table_gasType.getSelectedRow());
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "��û��ѡ��ú�����ͣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				bt_id = TableTool.cancelTableSelected(table_gasType, bt_id);
			}
		});
		// �޸�ú������
		jbt_gasType[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bt_id != -1) {
					String input_gasType = JOptionPane.showInputDialog(null, "����������Ҫ�޸ĵ�ú������(ֻ�����뺺��)", "�޸�ú������",
							JOptionPane.YES_NO_OPTION);
					try {
						if (input_gasType != null && !input_gasType.equals("")) {
							String regex = "^[\\u4e00-\\u9fa5]{0,}$";
							boolean result = InputLimit.regular(regex, input_gasType);
							if (result) {
								int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ���޸Ĵ�ú������", "��֤����",
										JOptionPane.YES_NO_OPTION);
								if (c == JOptionPane.YES_OPTION) {
									gasTypeCon.updateGasType(input_gasType, bt_id);
									dfttable_gasType.setValueAt(input_gasType, table_gasType.getSelectedRow(), 1);// ���޸ĵ���Ϣ�Ž����
								}

							} else {
								JOptionPane.showMessageDialog(null, "������������ݸ�ʽ���󣡣���", "����ʧ��",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "�������������Ϊ�գ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "δѡ��ú�����ͣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				bt_id = TableTool.cancelTableSelected(table_gasType, bt_id);
			}
		});
		return panel;
	}
}
