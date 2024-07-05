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
 * 煤气查询及选择面板
 */
public class UserGas extends JPanel implements ActionListener, ItemListener {
	GasCon gascon = new GasCon();
	JTable table;
	JTextField jtext, jtext_page;
	JScrollPane scrollPane;
	JPanel jpandown;
	DefaultTableModel dfttable;
	JComboBox<String> jcb;
	String b_type, fieldAlter;// b_type 煤气类型 fieldAlter读者修改信息的字段
	String[] columnStr = { "煤气ID", "煤气名",  "煤气类型","供应商名", "价格", "库存量"  };
	String getname;// 返回书名 （表格中）
	int g_id = -1, inventory;
	JTextField[] chose = new JTextField[2];
	ChoseCon chosecon = new ChoseCon();
	int row_get, nowcount, maxcount, choseingCount;// nowcount读者选择天数
	JButton jbt, jbtChose;
	Vector<String> columnName;
	JButton[] page_jbt = { new JButton("首页"), new JButton("上一页"), new JButton("下一页"), new JButton("尾页"),
			new JButton("跳转") };
	JLabel jlab_gas = new JLabel();
	int pageIndex = 1, pageCount;
	UserCommunityCon userCommunityCon = new UserCommunityCon();

	protected JPanel addPanel0() throws SQLException {
		JPanel jpanup = new JPanel();
		jbt = new JButton("搜索");
		jbtChose = new JButton("选择");
		jcb = new JComboBox<String>();
		jtext = new JTextField();
		jtext_page = new JTextField();
		jpanup.setLayout(null);
		jpanup.setPreferredSize(new Dimension(1000, 180));
		jbt.setBounds(570, 20, 80, 30);
		jbtChose.setBounds(670, 20, 80, 30);
		jtext.setBounds(330, 20, 200, 30);
		jtext_page.setBounds(650, 70, 80, 30);
		jtext_page.setDocument(new InputLimit(4));// 限制输入
		jtext_page.addFocusListener(new InputLimit(jtext_page, "页数"));// 设置文诓提示的外部类监听
		jtext.addFocusListener(new InputLimit(jtext, "煤气ID/煤气名/供应商"));// 设置文诓提示的外部类监听
		jcb.setBounds(200, 20, 80, 30);
		jlab_gas.setBounds(400, 140, 150, 30);
		pageCount = new PageQueryCon(gascon.seleGas()).pageCount();
		jlab_gas.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
		/*
		 * for循添加煤气类型
		 */
		jcb.addItem("煤气类型");
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
		// 默认在首页，所以“上一页”和“首页”按钮不可用
		page_jbt[0].setEnabled(false);
		page_jbt[1].setEnabled(false);
		// 注册监听
		jbt.addActionListener(this);
		jbtChose.addActionListener(this);
		jcb.addItemListener(this);
		// 设置表头
		columnName = new Vector<String>();// 字段名
		for (int i = 0; i < columnStr.length; i++) {
			columnName.add(columnStr[i]);
		}
		// 设置表格模型的数据
		dfttable = new DefaultTableModel(new PageQueryCon(gascon.seleGas()).setCurentPageIndex(), columnName);

		// 设置表格的编辑状态
		table = new JTable(dfttable) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		ListSelectionModel selectionModel = table.getSelectionModel();// 创建表格选择器
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 一次只能选择一个列表索引
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if (table.getSelectedRow() < 0) {
						return;
					}
					getname = table.getValueAt(table.getSelectedRow(), 1).toString();// 读取你获取行号的某一列
					g_id = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
					row_get = table.getSelectedRow();
					inventory = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 5).toString());
				}
			}
		});
		// 设置表格的公共属性
		TableTool.setTable(table, dfttable);
		table.getColumn("煤气名").setPreferredWidth(170);// 设置指定列的宽度
		table.getColumn("供应商名").setPreferredWidth(120);// 设置指定列的宽度
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
		// 下拉框事件
		return this;
	}

	/**
	 * 将查询到的数据显示到表格中
	 */
	public void setTableMolel(Vector<Vector<Object>> vector) {
		for (int i = 0; i < vector.size(); i++) {
			dfttable.setDataVector(vector, columnName);// 设定模型数据和字段,初始化该表
		}
		table.getColumn("煤气名").setPreferredWidth(170);// 设置指定列的宽度
		table.getColumn("供应商名").setPreferredWidth(120);// 设置指定列的宽度
		table.setRowHeight(20);
	}

	/**
	 * 模糊查询、精确查询煤气
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
			dfttable.setDataVector(gasData, columnName);// 设定模型数据和字段,初始化该表
		}
		table.getColumn("煤气名").setPreferredWidth(170);// 设置指定列的宽度
		table.getColumn("供应商名").setPreferredWidth(120);// 设置指定列的宽度
		table.setRowHeight(20);
	}

	public void actionPerformed(ActionEvent e) {
		// 搜索
		if (e.getSource() == jbt) {
			if (b_type == "*" || b_type == null) {
				try {
					if (jtext.getText().equals("") || jtext.getText().equals("煤气ID/煤气名/供应商名")) {
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
				if (jtext.getText().equals("煤气ID/煤气名/供应商名")) {
					jtext.setText("");
				}
				setTableModels();// 调用精确查询、模糊查询的方法
			}
			// 煤气选择
		} else if (e.getSource() == jbtChose) {
			if (g_id != -1) {
				int c = JOptionPane.showConfirmDialog(null, "是否确定选择此煤气", "验证操作", JOptionPane.YES_NO_OPTION);
				if (c == JOptionPane.YES_OPTION) {
					try {
						for (int i = 0; i < userCommunityCon.queryPersonalType(UserFace.count).size(); i++) {
							// 根据账号查询出读者类型的每本书的可选择天数
							nowcount = Integer.valueOf(userCommunityCon.queryPersonalType(UserFace.count).elementAt(i).elementAt(2).toString());
							// 同理，得可选择数量
							maxcount = Integer.valueOf(userCommunityCon.queryPersonalType(UserFace.count).elementAt(i)
									.elementAt(1).toString());
						}
						// 已选择的煤气数量
						choseingCount = chosecon.queryChoseInfo(UserFace.count, UserFace.count, false).length;
						int chosedate = TimeTool.getNewStamep();
						int duedate = TimeTool.getNewStamep() + nowcount * 86400;// 1天86400秒
						if (choseingCount <= maxcount) {
							if (chosecon.queryIsChoseGas(g_id, UserFace.count) == false) {
								// 插入选择时间和应该归还时间，并将煤气对应的库存量-1（事务）
								if (chosecon.insertChose(UserFace.count, g_id, chosedate, duedate, g_id)) {

									if (inventory > 1) {
										dfttable.setValueAt(
												Integer.valueOf(table.getValueAt(row_get, 5).toString()) - 1, row_get,
												5);
										JOptionPane.showMessageDialog(null, "您已经成功选择" + getname , "操作成功",
												JOptionPane.INFORMATION_MESSAGE);
									} else {
										JOptionPane.showMessageDialog(null, "该煤气库存量为1，不能选择！", "操作失败",
												JOptionPane.ERROR_MESSAGE);
									}
								} else {
									JOptionPane.showMessageDialog(null, "不能选择此煤气！", "操作失败",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "不能重复选择此煤气！", "操作失败", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "您所选择的煤气数量已经大于您的权限！", "操作失败",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "您没有选中煤气！", "操作失败", JOptionPane.ERROR_MESSAGE);
			}
			// 首页
		} else if (e.getSource() == page_jbt[0]) {
			try {
				setTableMolel(new PageQueryCon(gascon.seleGas()).setCurentPageIndex());
				pageIndex = 1;
				jlab_gas.setText("第" + pageIndex + "页/" + "共" + pageCount + "页");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 上一页
		} else if (e.getSource() == page_jbt[1]) {
			try {
				setTableMolel(new PageQueryCon(gascon.seleGas()).previousPage());
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
				setTableMolel(new PageQueryCon(gascon.seleGas()).nextPage());
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
				setTableMolel(new PageQueryCon(gascon.seleGas()).lastPage());
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
								new PageQueryCon(gascon.seleGas()).jumpPage(Integer.valueOf(jtext_page.getText())));
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
		g_id = TableTool.cancelTableSelected(table, g_id);
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

	// 下拉框点击事件
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
			for (int i = 0; i < page_jbt.length; i++) {
				if (jcb.getSelectedItem() == "煤气类型") {
					b_type = "*";
				} else {
					b_type = jcb.getSelectedItem().toString();
				}
			}
		}
	}
}
