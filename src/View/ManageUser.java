package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import Tool.InputLimit;
import Tool.PubJdialog;
import Tool.TableTool;

/**
 * ������Ϣ�������
 */
public class ManageUser {
	int row;
	String studentNumber, tele, email;
	String userCommunityInfo;
	boolean isCompile, refresh;
	UserCommunityCon userCommunityCon = new UserCommunityCon();
	UserCon userCon = new UserCon();

	protected JPanel addPanel2() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_user = new JPanel();
		jpanup_user.setLayout(null);
		jpanup_user.setPreferredSize(new Dimension(1000, 80));
		JButton[] jbt_user = { new JButton("��ѯ�û���Ϣ"), new JButton("�����û���Ϣ"), new JButton("ɾ���û���Ϣ"),
				new JButton("�޸��û���Ϣ") };
		JTextField jtext_user = new JTextField();
		JComboBox<String> jcb_user = new JComboBox<String>();
		for (int i = 0; i < jbt_user.length; i++) {
			jbt_user[i].setBounds(370 + i * 130, 20, 120, 30);
			jpanup_user.add(jbt_user[i]);
		}
		jtext_user.setBounds(160, 20, 200, 30);
		jtext_user.addFocusListener(new InputLimit(jtext_user, "�˺�/����"));//
		// ������ڲ��ʾ���ⲿ�����
		jcb_user.setBounds(50, 20, 80, 30);
		jcb_user.addItem("ȫ��");
		String[] readerType = userCommunityCon.getUserCommunity();
		for (int k = 0; k < readerType.length; k++) {
			jcb_user.addItem(readerType[k]);
		}
		jcb_user.setVisible(true);
		// ���������¼�
		jcb_user.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (jcb_user.getSelectedItem() == "ȫ��") {
						userCommunityInfo = "*";
					} else {
						userCommunityInfo = jcb_user.getSelectedItem().toString();
					}
				}
			}
		});
		Vector<String> columnNameUser = new Vector<String>();// �ֶ���
		String[] columnUser = { "�˺�", "����", "С��", "¥��", "�ֻ�����", "��������" };
		for (int k = 0; k < columnUser.length; k++) {
			columnNameUser.add(columnUser[k]);
		}
		DefaultTableModel dfttable_user = new DefaultTableModel(userCon.seleUser(), columnNameUser);
		JTable table_user = new JTable(dfttable_user) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = table_user.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if(table_user.getSelectedRow()<0) {
						return;
					}
					row= table_user.getSelectedRow();
					studentNumber = table_user.getValueAt(table_user.getSelectedRow(), 0).toString();
					tele = table_user.getValueAt(table_user.getSelectedRow(), 4).toString();
					email = table_user.getValueAt(table_user.getSelectedRow(), 5).toString();
				}
			}
		});
		//���ñ��Ĺ�������
		TableTool.setTable(table_user, dfttable_user);
		table_user.setPreferredScrollableViewportSize(new Dimension(900, 700));// ������Χ���ֹ�����
		JScrollPane scrollPane = new JScrollPane(table_user);
		jpanup_user.add(jcb_user);
		jpanup_user.add(jtext_user);
		panel.add(jpanup_user, BorderLayout.NORTH);
		panel.add(scrollPane);
		// ��ѯ
		jbt_user[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dfttable_user.setRowCount(0);
				dfttable_user.fireTableDataChanged();
				try {
					if (userCommunityInfo == "*" || userCommunityInfo == null) {
						if (jtext_user.getText().equals("�˺�/����") || jtext_user.getText().equals("")) {
							dfttable_user.setDataVector(userCon.seleUser(), columnNameUser);
						} else {
							dfttable_user.setDataVector(userCon.queryUserInfo(jtext_user.getText(),
									 jtext_user.getText()),
									columnNameUser);
						}
					} else {
						if (jtext_user.getText().equals("�˺�/����")) {
							jtext_user.setText("");
						}
						dfttable_user.setDataVector(
								userCon.seleUserInfo(jtext_user.getText(), jtext_user.getText(),
										jtext_user.getText()),
								columnNameUser);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				studentNumber = TableTool.setNull(table_user, studentNumber);
			}
		});
		// ����
		jbt_user[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Login();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				studentNumber = TableTool.setNull(table_user, studentNumber);
			}
		});
		// ɾ��
		jbt_user[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (studentNumber != null) {
					try {
						int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����ͼ������", "��֤����", JOptionPane.YES_NO_OPTION);
						if (c == JOptionPane.YES_OPTION) {
							userCon.dropReader(studentNumber);
							dfttable_user.removeRow(table_user.getSelectedRow());// ɾ������е���һ��
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ�ж��ߣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				studentNumber = TableTool.setNull(table_user, studentNumber);
			}
		});
		// ����
		jbt_user[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel[] jlab_user = { new JLabel("�˺ţ�"),  new JLabel("�ֻ��ţ�"),
						new JLabel("���䣺") };
				JLabel[] jlab_hint = { new JLabel("�����޸�"), new JLabel("���ĺ���"), new JLabel("���ĺ��ֻ�������"),
						new JLabel("13��14��15��17��18��ͷ"), new JLabel("�����ʽ")};
				JTextField[] jtext_user = new JTextField[5];
				Object[] readerUpdata = { studentNumber, tele, email };
				if (studentNumber != null) {
					try {
						new PubJdialog(210, 5, jlab_user, jtext_user, readerUpdata, 2,jlab_hint).setVisible(true);
						if (PubJdialog.success) {
							table_user.setValueAt(jtext_user[1].getText(), row, 4);
							table_user.setValueAt(jtext_user[2].getText(), row, 5);
							table_user.setValueAt(jtext_user[3].getText(), row, 6);
							table_user.setValueAt(jtext_user[4].getText(), row, 7);
							PubJdialog.success=false;
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ�ж��ߣ�����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
				studentNumber = TableTool.setNull(table_user, studentNumber);
			}
		});
		return panel;
	}
}
