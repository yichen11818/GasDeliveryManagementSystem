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
 * 煤气信息管理面板
 */
public class ManageGas extends JPanel implements ActionListener, ItemListener {
	String ISBN, g_name, author, b_type,gastype;
	int g_id = -1, inventory, row;
	double price;
	boolean isCompile, refresh;// 是否可以编辑
	public JTable table_gas;
	JButton[] page_jbt = { new JButton("首页"), new JButton("上一页"), new JButton("下一页"), new JButton("尾页"),
			new JButton("跳转") };
	JTextField jtext_find, jtext_page;
	JButton[] jbt_gasFind = { new JButton("查询煤气"), new JButton("新增煤气"), new JButton("删除煤气"), new JButton("修改煤气") };
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
		// 默认在首页，所以“上一页”和“首页”按钮不可用
		page_jbt[0].setEnabled(false);
		page_jbt[1].setEnabled(false);
		jtext_find.setBounds(160, 20, 200, 30);
		jtext_page.setBounds(650, 70, 80, 30);
		jtext_page.setDocument(new InputLimit(4));// 限制输入
		jtext_page.addFocusListener(new InputLimit(jtext_page, "页数"));// 设置文诓提示的外部类监听
		jtext_find.addFocusListener(new InputLimit(jtext_find, "煤气ID/煤气名/供应商名"));// 设置文诓提示的外部类监听
		jcb_gasType.setBounds(50, 20, 80, 30);
		jcb_gasType.addItem("类别筛选");
		jcb_gasType.addItemListener(this);
		for (int k = 0; k < gasCon.getB_type().size(); k++) {
			jcb_gasType.addItem(gasCon.getB_type().get(k));
		}
		jcb_gasType.setVisible(true);
		jpanup_gas.add(jcb_gasType);
		// 分页显示
		jlab_gas.setBounds(400, 140, 150, 30);
		pageCount = new PageQueryCon(gasCon.seleGas()).pageCount();
		jlab_gas.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
		jpanup_gas.add(jlab_gas);
		columnNameGas = new Vector<String>();// 字段名
		String[] columnGas = { "煤气ID", "煤气名", "供应商名", "煤气类型", "价格", "库存量" };
		Vector<Vector<Object>> gasData = null;
		String searchText = jtext_find.getText();
		int searchId = -1;
		try {
			searchId = Integer.parseInt(searchText);
		} catch (NumberFormatException ex) {
			// 输入不是整数，不做处理
		}
		gasData = gasCon.getVector(searchId, searchText, searchText); // 调用查询煤气的方法

		for (int k = 0; k < columnGas.length; k++) {
			columnNameGas.add(columnGas[k]);
		}
		dfttable_gas = new DefaultTableModel(new PageQueryCon(gasCon.seleGas()).setCurentPageIndex(),
				columnNameGas);
		table_gas = new JTable(dfttable_gas) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑f
			}
		};

		ListSelectionModel selectionModel = table_gas.getSelectionModel();// 创建表格选择器
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 一次只能选择一个列表索引
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
		// 设置表格的公共属性
		TableTool.setTable(table_gas, dfttable_gas);
		table_gas.getColumn("煤气名").setPreferredWidth(170);// 设置指定列的宽度
		table_gas.getColumn("供应商名").setPreferredWidth(120);// 设置指定列的宽度
		table_gas.getTableHeader().setReorderingAllowed(false); // 设置整列不可移动
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
	 * 将查询到的数据显示到表格中
	 */
	public void setTableMolel(Vector<Vector<Object>> vector) {
		for (int i = 0; i < vector.size(); i++) {
			dfttable_gas.setDataVector(vector, columnNameGas);// 设定模型数据和字段,初始化该表
		}
		table_gas.getColumn("煤气名").setPreferredWidth(170);// 设置指定列的宽度
		table_gas.getColumn("供应商名").setPreferredWidth(120);// 设置指定列的宽度
		table_gas.setRowHeight(20);
	}

	/**
	 * 模糊查询、精确查询煤气
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
			dfttable_gas.setDataVector(gasData, columnNameGas);// 设定模型数据和字段,初始化该表
		}
		table_gas.getColumn("煤气名").setPreferredWidth(170);// 设置指定列的宽度
		table_gas.getColumn("供应商名").setPreferredWidth(120);// 设置指定列的宽度
		table_gas.setRowHeight(20);
	}

	// 按钮事件
	public void actionPerformed(ActionEvent e) {
		// 查看煤气
		if (e.getSource() == jbt_gasFind[0]) {
			if (b_type == "*" || b_type == null) {
				try {
					if (jtext_find.getText().equals("") || jtext_find.getText().equals("煤气ID/煤气名/供应商名")) {
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
				if (jtext_find.getText().equals("煤气ID/煤气名/供应商名")) {
					jtext_find.setText("");
				}
				setTableModels();// 调用精确查询、模糊查询的方法
			}
		}
		// 新增煤气
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
		// 删除煤气
		else if (e.getSource() == jbt_gasFind[2]) {
			System.out.println(g_id);
			try {
				if (g_id != -1) {
					int c = JOptionPane.showConfirmDialog(null, "是否确定删除此煤气", "验证操作", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						if (!choseCon.queryExistGas(g_id)) {
							gasCon.dropGas(g_id);
							dfttable_gas.removeRow(table_gas.getSelectedRow());
						} else {
							JOptionPane.showMessageDialog(null, "此煤气已经被借阅不能删除!!!", "操作失败", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "您没有选中煤气！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// 更新煤气
		else if (e.getSource() == jbt_gasFind[3]) {
			JLabel[] jlab_gas = { new JLabel("煤气ID："), new JLabel("煤气名："), new JLabel("煤气类型："),new JLabel("供应商名："),
					new JLabel("价格："), new JLabel("库存量："), new JLabel("") };
			JLabel[] jlab_hint = { new JLabel("不可修改"), new JLabel("中文汉字或者字母"),new JLabel("中文汉字"),
					new JLabel("中文汉字"), new JLabel("1-2位小数"), new JLabel("整数"), new JLabel("") };

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
				JOptionPane.showMessageDialog(null, "您没有选中煤气！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
			}
		}

		// 首页
		else if (e.getSource() == page_jbt[0]) {
			try {
				setTableMolel(new PageQueryCon(gasCon.seleGas()).setCurentPageIndex());
				pageIndex = 1;
				jlab_gas.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 上一页
		} else if (e.getSource() == page_jbt[1]) {
			try {
				setTableMolel(new PageQueryCon(gasCon.seleGas()).previousPage());
				if (pageIndex > 1) {
					pageIndex--;
					jlab_gas.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 下一页
		} else if (e.getSource() == page_jbt[2]) {
			try {
				setTableMolel(new PageQueryCon(gasCon.seleGas()).nextPage());
				if (pageIndex < pageCount) {
					pageIndex++;
					jlab_gas.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 尾页
		} else if (e.getSource() == page_jbt[3]) {
			try {
				setTableMolel(new PageQueryCon(gasCon.seleGas()).lastPage());
				pageIndex = pageCount;
				jlab_gas.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// 跳转页数
		else if (e.getSource() == page_jbt[4]) {
			try {
				String isInt = jtext_page.getText();
				isInt = isInt.replaceAll("[0-9]", "");// 将所有的数字型字符替换为空
				// 长度为0等于说数字
				if (isInt.length() == 0) {
					if (Integer.valueOf(jtext_page.getText()) > 0
							&& Integer.valueOf(jtext_page.getText()) <= pageCount) {
						setTableMolel(
								new PageQueryCon(gasCon.seleGas()).jumpPage(Integer.valueOf(jtext_page.getText())));
						pageIndex = Integer.valueOf(jtext_page.getText());
						jlab_gas.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
					} else {
						JOptionPane.showMessageDialog(null, "请输入正确的页数", "操作失败", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "请输入整数", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		/*
		 * 每次点击按钮将煤气id设为-1，即默认没有选中煤气 并取消表格的选中状态 必须写在else if语句后，并且所有动作事件都是用else if语句来写的
		 */
		g_id = TableTool.cancelTableSelected(table_gas, g_id);
		// 尾页时，“下一页”和“尾页”按钮不可用
		if (pageIndex == pageCount) {
			page_jbt[2].setEnabled(false);
			page_jbt[3].setEnabled(false);
		} else {
			page_jbt[2].setEnabled(true);
			page_jbt[3].setEnabled(true);
		}
		// 首页时，“上一页”和“首页”不可用
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
				if (jcb_gasType.getSelectedItem() == "全部") {
					b_type = "*";
				} else {
					b_type = jcb_gasType.getSelectedItem().toString();
				}
			}
		}

	}
}
