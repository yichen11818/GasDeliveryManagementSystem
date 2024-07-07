package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.UserCon;
import Controller.UserCommunityCon;
import Tool.PubJdialog;
import Tool.TableTool;

/**
 * �û�������Ϣ�������
 *  
 *
 */
public class ManageUserCommunity {
	int a, u_id=-1, maxcount, nowcount,row;
	String userCommunity;
	UserCommunityCon userCommunityCon = new UserCommunityCon();
	UserCon userCon =new UserCon();
	boolean isCompile, refresh;// �Ƿ���Ա༭

	protected JPanel addPanel3() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_userCommunity = new JPanel();
		jpanup_userCommunity.setLayout(null);
		jpanup_userCommunity.setPreferredSize(new Dimension(1000, 80));

		JButton[] jbt_userCommunity = {new JButton("����С��"), new JButton("ɾ��С��"),
				new JButton("�޸�С��") };
		for (int i = 0; i < jbt_userCommunity.length; i++) {
			jbt_userCommunity[i].setBounds(300 + i * 150, 20, 120, 30);
			jpanup_userCommunity.add(jbt_userCommunity[i]);
		}
		String[] columnuserCommunity = { "���", "С����", "�����","��ǰ����" };
		Object[][] userCommunityData = userCommunityCon.queryReaderType();
		DefaultTableModel dfttable_userCommunity = new DefaultTableModel(userCommunityData, columnuserCommunity);
		JTable table_userCommunity = new JTable(dfttable_userCommunity) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = table_userCommunity.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if(table_userCommunity.getSelectedRow()<0) {
						return;
					}
					row=table_userCommunity.getSelectedRow();
					userCommunity = table_userCommunity.getValueAt(table_userCommunity.getSelectedRow(), 1).toString();
					u_id = Integer
							.valueOf(table_userCommunity.getValueAt(table_userCommunity.getSelectedRow(), 0).toString());
					maxcount = Integer
							.valueOf(table_userCommunity.getValueAt(table_userCommunity.getSelectedRow(), 2).toString());
					nowcount = Integer
							.valueOf(table_userCommunity.getValueAt(table_userCommunity.getSelectedRow(), 3).toString());

				}
			}
		});
		//���ñ��Ĺ�������
		TableTool.setTable(table_userCommunity, dfttable_userCommunity);
		table_userCommunity.setPreferredScrollableViewportSize(new Dimension(900, 700));// ������Χ���ֹ���
		JScrollPane scrollPane = new JScrollPane(table_userCommunity);
		panel.add(jpanup_userCommunity, BorderLayout.NORTH);
		panel.add(scrollPane);
		// ����С��
		jbt_userCommunity[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new PubJdialog(a).setVisible(true);
					if(PubJdialog.success) {
						dfttable_userCommunity.setDataVector(userCommunityCon.queryReaderType(), columnuserCommunity);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				u_id = TableTool.cancelTableSelected(table_userCommunity, u_id);
			}
		});
		// ɾ��С��
		jbt_userCommunity[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (u_id != -1) {
					int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����С��", "��֤����", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						try {
							if(userCon.existReadertype(u_id)) {
								JOptionPane.showMessageDialog(null, "��С���Ѿ����û�ʹ�ã��볢�Խ���С�����û�ɾ������ɾ����С����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
							}else {
								System.out.println(u_id);
							userCommunityCon.deleteRederType(u_id);
							dfttable_userCommunity.removeRow(table_userCommunity.getSelectedRow());
							}
						} catch (SQLException e1) {

							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ��С����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				u_id = TableTool.cancelTableSelected(table_userCommunity, u_id);
			}
		});
		// �޸�С��
		jbt_userCommunity[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel[] jlab_userCommunity = { new JLabel("С����ţ�"), new JLabel("С������"), new JLabel("�������"),new JLabel("��ǰ������")};
				JLabel[] jlab_hint = { new JLabel("�����޸�"), new JLabel("���ĺ���"), new JLabel("����"), new JLabel("����")};
				JTextField[] jtext_userCommunity = new JTextField[4];
				Object[] userCommunityUpdata = { u_id, userCommunity, maxcount ,nowcount};
				if (u_id != -1) {
					try {
						// �����޸��û����͵ĶԻ���
						new PubJdialog(180, 4, jlab_userCommunity, jtext_userCommunity, userCommunityUpdata, 3,jlab_hint).setVisible(true);
						if (PubJdialog.success) {
							table_userCommunity.setValueAt(jtext_userCommunity[1].getText(), row, 1);
							table_userCommunity.setValueAt(jtext_userCommunity[2].getText(), row, 2);
							table_userCommunity.setValueAt(jtext_userCommunity[3].getText(), row, 3);
							PubJdialog.success=false;
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ��С����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				u_id = TableTool.cancelTableSelected(table_userCommunity, u_id);
			}
		});
		return panel;
	}
}
