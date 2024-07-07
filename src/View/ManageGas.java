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
import Tool.InputLimit;
import Tool.PubJdialog;
import Tool.TableTool;

/**
 * ú����Ϣ�������
 */
public class ManageGas extends JPanel implements ActionListener, ItemListener {
	String ISBN, g_name, author, b_type,gastype;
	int g_id = -1, inventory, row;
	double price;
	boolean isCompile, refresh;// �Ƿ���Ա༭
	public JTable table_gas;
	JButton[] page_jbt = { new JButton("��ҳ"), new JButton("��һҳ"), new JButton("��һҳ"), new JButton("βҳ"),
			new JButton("��ת") };
	JTextField jtext_find, jtext_page;
	JButton[] jbt_gasFind = { new JButton("��ѯú��"), new JButton("����ú��"), new JButton("ɾ��ú��"), new JButton("�޸�ú��") };
	DefaultTableModel dfttable_gas;
	JComboBox<String> jcb_gasType;
	Vector<String> columnNameGas;
	JLabel jlab_gas = new JLabel();
	int pageIndex = 1, pageCount;
	UserGas userGas = new UserGas();
	GasCon gasCon = new GasCon();
	ChoseCon choseCon = new ChoseCon();

	protected JPanel addPanel0() throws SQLException {
		JPanel jpanup_gas = new JPanel();
		JPanel jpandown_gas = new JPanel(new BorderLayout());
		jpanup_gas.setLayout(null);
		jpanup_gas.setPreferredSize(new Dimension(1000, 180));
		jtext_page = new JTextField();
		jtext_find = new JTextField();
		jcb_gasType = new JComboBox<String>();
		for (int i = 0; i < jbt_gasFind.length; i++) {
			jbt_gasFind[i].setBounds(400 + i * 110, 20, 100, 30);
			jpanup_gas.add(jbt_gasFind[i]);
			jbt_gasFind[i].addActionListener(this);
		}
		for (int i = 0; i < page_jbt.length; i++) {
			if (i == 4) {
				page_jbt[i].setBounds(730, 70, 80, 30);
			} else {
				page_jbt[i].setBounds(250 + i * 100, 70, 80, 30);
			}
			jpanup_gas.add(page_jbt[i]);
			page_jbt[i].addActionListener(this);
		}
		// Ĭ������ҳ�����ԡ���һҳ���͡���ҳ����ť������
		page_jbt[0].setEnabled(false);
		page_jbt[1].setEnabled(false);
		jtext_find.setBounds(160, 20, 200, 30);
		jtext_page.setBounds(650, 70, 80, 30);
		jtext_page.setDocument(new InputLimit(4));// ��������
		jtext_page.addFocusListener(new InputLimit(jtext_page, "ҳ��"));// ������ڲ��ʾ���ⲿ�����
		jtext_find.addFocusListener(new InputLimit(jtext_find, "ú��ID/ú����/��Ӧ����"));// ������ڲ��ʾ���ⲿ�����
		jcb_gasType.setBounds(50, 20, 80, 30);
		jcb_gasType.addItem("���ɸѡ");
		jcb_gasType.addItemListener(this);
		for (int k = 0; k < gasCon.getB_type().size(); k++) {
			jcb_gasType.addItem(gasCon.getB_type().get(k));
		}
		jcb_gasType.setVisible(true);
		jpanup_gas.add(jcb_gasType);
		// ��ҳ��ʾ
		jlab_gas.setBounds(400, 140, 150, 30);
		pageCount = new PageQueryCon(gasCon.seleGas()).pageCount();
		jlab_gas.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
		jpanup_gas.add(jlab_gas);
		columnNameGas = new Vector<String>();// �ֶ���
		String[] columnGas = { "ú��ID", "ú����", "��Ӧ����", "ú������", "�۸�", "�����" };
		Vector<Vector<Object>> gasData = null;
		String searchText = jtext_find.getText();
		int searchId = -1;
		try {
			searchId = Integer.parseInt(searchText);
		} catch (NumberFormatException ex) {
			// ���벻����������������
		}
		gasData = gasCon.getVector(searchId, searchText, searchText); // ���ò�ѯú���ķ���

		for (int k = 0; k < columnGas.length; k++) {
			columnNameGas.add(columnGas[k]);
		}
		dfttable_gas = new DefaultTableModel(new PageQueryCon(gasCon.seleGas()).setCurentPageIndex(),
				columnNameGas);
		table_gas = new JTable(dfttable_gas) {
			public boolean isCellEditable(int row, int column) {
				return false;// ��������༭f
			}
		};

		ListSelectionModel selectionModel = table_gas.getSelectionModel();// �������ѡ����
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// һ��ֻ��ѡ��һ���б�����
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if (table_gas.getSelectedRow() < 0) {
						return;
					}
					row = table_gas.getSelectedRow();
					g_id = Integer.valueOf(table_gas.getValueAt(table_gas.getSelectedRow(), 0).toString());
					g_name = table_gas.getValueAt(table_gas.getSelectedRow(), 1).toString();
					gastype = table_gas.getValueAt(table_gas.getSelectedRow(), 2).toString();
					author = table_gas.getValueAt(table_gas.getSelectedRow(), 3).toString();
					price = Double.valueOf(table_gas.getValueAt(table_gas.getSelectedRow(), 4).toString());
					inventory = Integer.valueOf(table_gas.getValueAt(table_gas.getSelectedRow(), 5).toString());
				}
			}
		});
		// ���ñ��Ĺ�������
		TableTool.setTable(table_gas, dfttable_gas);
		table_gas.getColumn("ú����").setPreferredWidth(170);// ����ָ���еĿ��
		table_gas.getColumn("��Ӧ����").setPreferredWidth(120);// ����ָ���еĿ��
		table_gas.getTableHeader().setReorderingAllowed(false); // �������в����ƶ�
		JScrollPane scrollPane = new JScrollPane(table_gas);
		scrollPane.setPreferredSize(new Dimension(1000, 500));
		jpanup_gas.add(jcb_gasType);
		jpanup_gas.add(jtext_find);
		jpanup_gas.add(jtext_page);
		jpandown_gas.add(scrollPane);
		this.add(jpanup_gas, BorderLayout.NORTH);
		this.add(scrollPane);

		return this;
	}

	/**
	 * ����ѯ����������ʾ�������
	 */
	public void setTableMolel(Vector<Vector<Object>> vector) {
		for (int i = 0; i < vector.size(); i++) {
			dfttable_gas.setDataVector(vector, columnNameGas);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
		}
		table_gas.getColumn("ú����").setPreferredWidth(170);// ����ָ���еĿ��
		table_gas.getColumn("��Ӧ����").setPreferredWidth(120);// ����ָ���еĿ��
		table_gas.setRowHeight(20);
	}

	/**
	 * ģ����ѯ����ȷ��ѯú��
	 */
	public void setTableModels() {
		String input = jtext_find.getText();
		Vector<Vector<Object>> gasData = null;
		try {
			gasData = gasCon.getGas(Integer.parseInt(input), input, input, b_type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < gasData.size(); i++) {
			dfttable_gas.setDataVector(gasData, columnNameGas);// �趨ģ�����ݺ��ֶ�,��ʼ���ñ�
		}
		table_gas.getColumn("ú����").setPreferredWidth(170);// ����ָ���еĿ��
		table_gas.getColumn("��Ӧ����").setPreferredWidth(120);// ����ָ���еĿ��
		table_gas.setRowHeight(20);
	}

	// ��ť�¼�
	public void actionPerformed(ActionEvent e) {
		// �鿴ú��
		if (e.getSource() == jbt_gasFind[0]) {
			if (b_type == "*" || b_type == null) {
				try {
					if (jtext_find.getText().equals("") || jtext_find.getText().equals("ú��ID/ú����/��Ӧ����")) {
						setTableMolel(new PageQueryCon(gasCon.seleGas()).setCurentPageIndex());
					} else {
						dfttable_gas.setDataVector(
								gasCon.getVector(Integer.parseInt(jtext_find.getText()), jtext_find.getText(), jtext_find.getText()),
								columnNameGas);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				if (jtext_find.getText().equals("ú��ID/ú����/��Ӧ����")) {
					jtext_find.setText("");
				}
				setTableModels();// ���þ�ȷ��ѯ��ģ����ѯ�ķ���
			}
		}
		// ����ú��
		else if (e.getSource() == jbt_gasFind[1]) {
			if (e.getSource() == jbt_gasFind[1]) {
				try {
					new PubJdialog().setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			TableTool.cancelTableSelected(table_gas, g_id);
		}
		// ɾ��ú��
		else if (e.getSource() == jbt_gasFind[2]) {
			System.out.println(g_id);
			try {
				if (g_id != -1) {
					int c = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ����ú��", "��֤����", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						if (!choseCon.queryExistGas(g_id)) {
							gasCon.dropGas(g_id);
							dfttable_gas.removeRow(table_gas.getSelectedRow());
						} else {
							JOptionPane.showMessageDialog(null, "��ú���Ѿ������Ĳ���ɾ��!!!", "����ʧ��", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "��û��ѡ��ú��������", "����ʧ��", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// ����ú��
		else if (e.getSource() == jbt_gasFind[3]) {
			JLabel[] jlab_gas = { new JLabel("ú��ID��"), new JLabel("ú������"), new JLabel("ú�����ͣ�"),new JLabel("��Ӧ������"),
					new JLabel("�۸�"), new JLabel("�������"), new JLabel("") };
			JLabel[] jlab_hint = { new JLabel("�����޸�"), new JLabel("���ĺ��ֻ�����ĸ"),new JLabel("���ĺ���"),
					new JLabel("���ĺ���"), new JLabel("1-2λС��"), new JLabel("����"), new JLabel("") };

			JTextField[] jtext_gas = new JTextField[6];
			Object[] gasUpdata = { g_id, g_name, gastype, author, price, inventory };
			if (g_id != -1) {
				try {
					new PubJdialog(270, 6, jlab_gas, jtext_gas,
							gasUpdata, 0, jlab_hint).setVisible(true);
					if (PubJdialog.success) {
						for (int i = 1; i < jtext_gas.length; i++) {
							table_gas.setValueAt(jtext_gas[i].getText(), row, i);
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "��û��ѡ��ú��������", "����ʧ��", JOptionPane.ERROR_MESSAGE);
			}
		}

		// ��ҳ
		else if (e.getSource() == page_jbt[0]) {
			try {
				setTableMolel(new PageQueryCon(gasCon.seleGas()).setCurentPageIndex());
				pageIndex = 1;
				jlab_gas.setText("��" + pageIndex + "ҳ/" + "��" + pageCount + "ҳ");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ��һҳ
		} else if (e.getSource() == page_jbt[1]) {
			try {
				setTableMolel(new PageQueryCon(gasCon.seleGas()).previousPage());
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
				setTableMolel(new PageQueryCon(gasCon.seleGas()).nextPage());
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
				setTableMolel(new PageQueryCon(gasCon.seleGas()).lastPage());
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
								new PageQueryCon(gasCon.seleGas()).jumpPage(Integer.valueOf(jtext_page.getText())));
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
		g_id = TableTool.cancelTableSelected(table_gas, g_id);
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			for (int i = 0; i < page_jbt.length; i++) {
				if (jcb_gasType.getSelectedItem() == "ȫ��") {
					b_type = "*";
				} else {
					b_type = jcb_gasType.getSelectedItem().toString();
				}
			}
		}

	}
}
