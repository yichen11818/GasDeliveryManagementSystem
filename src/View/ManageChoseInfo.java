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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.ChoseCon;
import Tool.InputLimit;
import Tool.TableTool;

/**
 * ���Ĺ黹��Ϣ�������
 * @author rsw
 *
 */
public class ManageChoseInfo {
	ChoseCon Chosecon = new ChoseCon();
	String adm_count;
	Object[] columnChose = { "���", "ISBN", "����", "ͼ������", "����", "����ʱ��", "Ӧ��ʱ��", "ʵ�ʹ黹ʱ��" };

	protected JPanel addPanel4() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_Chose = new JPanel();
		jpanup_Chose.setLayout(null);
		jpanup_Chose.setPreferredSize(new Dimension(1000, 80));
		JButton[] jbt_Chose = { new JButton("��ѯ������Ϣ"), new JButton("��ʷ������Ϣ") };
		JTextField jtext_Chose = new JTextField();
		for (int i = 0; i < jbt_Chose.length; i++) {
			jbt_Chose[i].setBounds(280 + i * 140, 20, 120, 30);
			jpanup_Chose.add(jbt_Chose[i]);
		}
		jtext_Chose.setBounds(60, 20, 200, 30);
		jtext_Chose.addFocusListener(new InputLimit(jtext_Chose, "�˺�"));// ������ڲ��ʾ���ⲿ�����
		DefaultTableModel dfttableChose = new DefaultTableModel();
		JTable table_Chose = new JTable(dfttableChose) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		//���ñ��Ĺ�������
		TableTool.setTable(table_Chose, dfttableChose);	
		table_Chose.setPreferredScrollableViewportSize(new Dimension(900, 700));// ������Χ���ֹ�����
		table_Chose.getTableHeader().setReorderingAllowed(false); // �������в����ƶ�

		JScrollPane scrollPane = new JScrollPane(table_Chose);
		jpanup_Chose.add(jtext_Chose);
		panel.add(jpanup_Chose, BorderLayout.NORTH);
		panel.add(scrollPane);
		//��ѯ������Ϣ
		jbt_Chose[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm_count = jtext_Chose.getText();
				try {
					if (adm_count.equals("�˺�") || adm_count.equals("")) {
						JOptionPane.showMessageDialog(null, "�������˺ţ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
					} else {
						dfttableChose.setDataVector(Chosecon.queryChoseInfo(adm_count, adm_count, false),
								columnChose);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//��Ѱ��ʷ������Ϣ
		jbt_Chose[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm_count = jtext_Chose.getText();
				try {
					if (adm_count.equals("�˺�") || adm_count.equals("")) {
						JOptionPane.showMessageDialog(null, "�������˺ţ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
					} else {
						dfttableChose.setDataVector(Chosecon.queryChoseReturnDate(adm_count, adm_count, true),
								columnChose);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		return panel;
	}
}
