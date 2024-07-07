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

import Controller.GasCon;
import Controller.ChoseCon;
import Controller.PageQueryCon;
import Controller.UserCommunityCon;
import Tool.InputLimit;
import Tool.TableTool;
import Tool.TimeTool;

/**
 * ú����ѯ��ѡ�����
 */
public class UserGas extends JPanel implements ActionListener, ItemListener {
	GasCon gascon = new GasCon();
	JTable table;
	JTextField jtext, jtext_page;
	JScrollPane scrollPane;
	JPanel jpandown;
	DefaultTableModel dfttable;
	JComboBox<String> jcb;
	String b_type, fieldAlter;// b_type ú������ fieldAlter�����޸���Ϣ���ֶ�
	String[] columnStr = { "ú��ID", "ú����",  "ú������","��Ӧ����", "�۸�", "�����"  };
	String getname;// �������� ������У�
	int g_id = -1, inventory;
	JTextField[] chose = new JTextField[2];
	ChoseCon chosecon = new ChoseCon();
	int row_get, nowcount, maxcount, choseingCount;// nowcount����ѡ������
	JButton jbt, jbtChose;
	Vector<String> columnName;
	JButton[] page_jbt = { new JButton("��ҳ"), new JButton("��һҳ"), new JButton("��һҳ"), new JButton("βҳ"),
			new JButton("��ת") };
	JLabel jlab_gas = new JLabel();
	int pageIndex = 1, pageCount;
	UserCommunityCon userCommunityCon = new UserCommunityCon();

	protected JPanel addPanel0() throws SQLException {
		JPanel jpanup = new JPanel();
		jbt = new JButton("����");
		jbtChose = new JButton("ѡ��");
		jcb = new JComboBox<String>();
		jtext = new JTextField();
		jtext_page = new JTextField();
		jpanup.setLayout(null);
		jpanup.setPreferredSize(new Dimension(1000, 180));
		jbt.setBounds(570, 20, 80, 30);
		jbtChose.setBounds(670, 20, 80, 30);
		jtext.setBounds(330, 20, 200, 30);
		jtext_page.setBounds(650, 70, 80, 30);
		jtext_page.setDocument(new InputLimit(4));// ��������
		jtext_page.addFocusListener(new InputLimit(jtext_page, "ҳ��"));// ������ڲ��ʾ���ⲿ�����
		jtext.addFocusListener(new InputLimit(jtext, "ú��ID/ú����/��Ӧ��"));// ������ڲ��ʾ���ⲿ�����
		jcb.setBounds(200, 20, 80, 30);
		jlab_gas.setBounds(400, 140, 150, 30);
		pageCount = new PageQueryCon(gascon.seleGas()).pageCount();
		jlab_gas.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
		/*
		 * forѭ���ú������
		 */
		jcb.addItem("ú������");
		for (int k = 0; k < gascon.getB_type().size(); k++) {
			jcb.addItem(gascon.getB_type().get(k));
		}
		jcb.setVisible(true);

		for (int i = 0; i < page_jbt.length; i++) {
			if (i == 4) {
				page_jbt[i].setBounds(730, 70, 80, 30);
			} else {
				page_jbt[i].setBounds(250 + i * 100, 70, 80, 30);
			}
			jpanup.add(page_jbt[i]);
			page_jbt[i].addActionListener(this);
		}
		// Ĭ������ҳ�����ԡ���һҳ���͡���ҳ����ť������
		page_jbt[0].setEnabled(false);
		page_jbt[1].setEnabled(false);
		// ע�����
		jbt.addActionListener(this);
		jbtChose.addActionListener(this);
		jcb.addItemListener(this);
		// ���ñ�ͷ
		columnName = new Vector<String>();// �ֶ���
		for (int i = 0; i < columnStr.length; i++) {
			columnName.add(columnStr[i]);
		}
		// ���ñ��ģ�͵�����
		dfttable = new DefaultTableModel(new PageQueryCon(gascon.seleGas()).setCurentPageIndex(), columnName);

		// ���ñ��ı༭״̬
		table = new JTable(dfttable) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭
			}
		};
		ListSelectionModel selectionModel = table.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if (table.getSelectedRow() < 0) {
						return;
					}
					getname = table.getValueAt(table.getSelectedRow(), 1).toString();// ��ȡ���ȡ�кŵ�ĳһ��
					g_id = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
					row_get = table.getSelectedRow();
					inventory = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 5).toString());
				}
			}
		});
		// ���ñ��Ĺ�������
		TableTool.setTable(table, dfttable);
		table.getColumn("ú����").setPreferredWidth(170);// ����ָ���еĿ��
		table.getColumn("��Ӧ����").setPreferredWidth(120);// ����ָ���еĿ��
		table.setRowHeight(20);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000, 500));
		jpanup.add(jbt);
		jpanup.add(jbtChose);
		jpanup.add(jcb);
		jpanup.add(jtext);
		jpanup.add(jtext_page);
		jpanup.add(jlab_gas);
		this.add(jpanup, BorderLayout.NORTH);
		this.add(scrollPane);
		// �������¼�
		return this;
	}

	/**
	 * ����ѯ����������ʾ�������
	 */
	public void setTableMolel(Vector<Vector<Object>> vector) {
		for (int i = 0; i < vector.size(); i++) {
			dfttable.setDataVector(vector, columnName);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
		}
		table.getColumn("ú����").setPreferredWidth(170);// ����ָ���еĿ��
		table.getColumn("��Ӧ����").setPreferredWidth(120);// ����ָ���еĿ��
		table.setRowHeight(20);
	}

	/**
	 * ģ����ѯ����ȷ��ѯú��
	 */
	public void setTableModels() {
		String input = jtext.getText();
		Vector<Vector<Object>> gasData = null;
		try {
			gasData = gascon.getGas(Integer.parseInt(input), input, input, b_type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < gasData.size(); i++) {
			dfttable.setDataVector(gasData, columnName);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
		}
		table.getColumn("ú����").setPreferredWidth(170);// ����ָ���еĿ��
		table.getColumn("��Ӧ����").setPreferredWidth(120);// ����ָ���еĿ��
		table.setRowHeight(20);
	}

	public void actionPerformed(ActionEvent e) {
		// ����
		if (e.getSource() == jbt) {
			if (b_type == "*" || b_type == null) {
				try {
					if (jtext.getText().equals("") || jtext.getText().equals("ú��ID/ú����/��Ӧ����")) {
						setTableMolel(new PageQueryCon(gascon.seleGas()).setCurentPageIndex());
					} else {
						dfttable.setDataVector(gascon.getVector(Integer.parseInt(jtext.getText()), jtext.getText(), jtext.getText()),
								columnName);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				if (jtext.getText().equals("ú��ID/ú����/��Ӧ����")) {
					jtext.setText("");
				}
				setTableModels();// ���þ�ȷ��ѯ��ģ����ѯ�ķ���
			}
			// ú��ѡ��
		} else if (e.getSource() == jbtChose) {
			if (g_id != -1) {
				int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ѡ���ú��", "��֤����", JOptionPane.YES_NO_OPTION);
				if (c == JOptionPane.YES_OPTION) {
					try {
						for (int i = 0; i < userCommunityCon.queryPersonalType(UserFace.count).size(); i++) {
							// �����˺Ų�ѯ���������͵�ÿ����Ŀ�ѡ������
							nowcount = Integer.valueOf(userCommunityCon.queryPersonalType(UserFace.count).elementAt(i).elementAt(2).toString());
							// ͬ���ÿ�ѡ������
							maxcount = Integer.valueOf(userCommunityCon.queryPersonalType(UserFace.count).elementAt(i)
									.elementAt(1).toString());
						}
						// ��ѡ���ú������
						choseingCount = chosecon.queryChoseInfo(UserFace.count, UserFace.count, false).length;
						int chosedate = TimeTool.getNewStamep();
						int duedate = TimeTool.getNewStamep() + nowcount * 86400;// 1��86400��
						if (choseingCount <= maxcount) {
							if (chosecon.queryIsChoseGas(g_id, UserFace.count) == false) {
								// ����ѡ��ʱ���Ӧ�ù黹ʱ�䣬����ú����Ӧ�Ŀ����-1������
								if (chosecon.insertChose(UserFace.count, g_id, chosedate, duedate, g_id)) {

									if (inventory > 1) {
										dfttable.setValueAt(
												Integer.valueOf(table.getValueAt(row_get, 5).toString()) - 1, row_get,
												5);
										JOptionPane.showMessageDialog(null, "���Ѿ��ɹ�ѡ��" + getname , "�����ɹ�",
												JOptionPane.INFORMATION_MESSAGE);
									} else {
										JOptionPane.showMessageDialog(null, "��ú�������Ϊ1������ѡ��", "����ʧ��",
												JOptionPane.ERROR_MESSAGE);
									}
								} else {
									JOptionPane.showMessageDialog(null, "����ѡ���ú����", "����ʧ��",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "�����ظ�ѡ���ú����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "����ѡ���ú�������Ѿ���������Ȩ�ޣ�", "����ʧ��",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "��û��ѡ��ú����", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			}
			// ��ҳ
		} else if (e.getSource() == page_jbt[0]) {
			try {
				setTableMolel(new PageQueryCon(gascon.seleGas()).setCurentPageIndex());
				pageIndex = 1;
				jlab_gas.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��һҳ
		} else if (e.getSource() == page_jbt[1]) {
			try {
				setTableMolel(new PageQueryCon(gascon.seleGas()).previousPage());
				if (pageIndex > 1) {
					pageIndex--;
					jlab_gas.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��һҳ
		} else if (e.getSource() == page_jbt[2]) {
			try {
				setTableMolel(new PageQueryCon(gascon.seleGas()).nextPage());
				if (pageIndex < pageCount) {
					pageIndex++;
					jlab_gas.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// βҳ
		} else if (e.getSource() == page_jbt[3]) {
			try {
				setTableMolel(new PageQueryCon(gascon.seleGas()).lastPage());
				pageIndex = pageCount;
				jlab_gas.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// ��תҳ��
		else if (e.getSource() == page_jbt[4]) {
			try {
				String isInt = jtext_page.getText();
				isInt = isInt.replaceAll("[0-9]", "");// �����е��������ַ��滻Ϊ��
				// ����Ϊ0����˵����
				if (isInt.length() == 0) {
					if (Integer.valueOf(jtext_page.getText()) > 0
							&& Integer.valueOf(jtext_page.getText()) <= pageCount) {
						setTableMolel(
								new PageQueryCon(gascon.seleGas()).jumpPage(Integer.valueOf(jtext_page.getText())));
						pageIndex = Integer.valueOf(jtext_page.getText());
						jlab_gas.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
					} else {
						JOptionPane.showMessageDialog(null, "��������ȷ��ҳ��", "����ʧ��", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "����������", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		/*
		 * ÿ�ε����ť��ú��id��Ϊ-1����Ĭ��û��ѡ��ú�� ��ȡ������ѡ��״̬ ����д��else if���󣬲������ж����¼�������else if�����д��
		 */
		g_id = TableTool.cancelTableSelected(table, g_id);
		// βҳʱ������һҳ���͡�βҳ����ť������
		if (pageIndex == pageCount) {
			page_jbt[2].setEnabled(false);
			page_jbt[3].setEnabled(false);
		} else {
			page_jbt[2].setEnabled(true);
			page_jbt[3].setEnabled(true);
		}
		// ��ҳʱ������һҳ���͡���ҳ��������
		if (pageIndex == 1) {
			page_jbt[0].setEnabled(false);
			page_jbt[1].setEnabled(false);
		} else {
			page_jbt[0].setEnabled(true);
			page_jbt[1].setEnabled(true);
		}
	}

	// ���������¼�
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
			for (int i = 0; i < page_jbt.length; i++) {
				if (jcb.getSelectedItem() == "ú������") {
					b_type = "*";
				} else {
					b_type = jcb.getSelectedItem().toString();
				}
			}
		}
	}
}
