
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
 * 煤气类型信息管理面板
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
		JButton[] jbt_gasType = { new JButton("新增煤气类型"), new JButton("删除煤气类型"),
				new JButton("修改煤气类型") };
		for (int i = 0; i < jbt_gasType.length; i++) {
			jbt_gasType[i].setBounds(300 + i * 150, 20, 120, 30);
			jpanup_gasType.add(jbt_gasType[i]);
		}

		String[] columnGas = { "序号", "煤气类型" };
		Object[][] gasTypeData = gasTypeCon.queryGasType();
		DefaultTableModel dfttable_gasType = new DefaultTableModel(gasTypeData, columnGas);
		JTable table_gasType = new JTable(dfttable_gasType) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		ListSelectionModel selectionModel = table_gasType.getSelectionModel();// 创建表格选择器
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 一次只能选择一个列表索引
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					bt_id = Integer.valueOf(table_gasType.getValueAt(table_gasType.getSelectedRow(), 0).toString());
					bt_name = table_gasType.getValueAt(table_gasType.getSelectedRow(), 0).toString();
				}
			}
		});
		// 设置表格的公共属性
		TableTool.setTable(table_gasType, dfttable_gasType);

		JScrollPane scrollPane = new JScrollPane(table_gasType);
		panel.add(jpanup_gasType, BorderLayout.NORTH);
		panel.add(scrollPane);
		// 新增煤气类型
		jbt_gasType[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input_gasType = JOptionPane.showInputDialog(null, "请您输入所要添加的煤气类型", "新增煤气类型",
						JOptionPane.YES_NO_OPTION);
				try {
					if (input_gasType != null && !input_gasType.equals("")) {
						String regex =InputLimit.CHINESE ;
						boolean result = InputLimit.regular(regex, input_gasType);
						if (result) {
							int c = JOptionPane.showConfirmDialog(null, "是否确定新增此煤气类型", "验证操作",
									JOptionPane.YES_NO_OPTION);
							if (c == JOptionPane.YES_OPTION) {
								gasTypeCon.insertGasType(input_gasType);
								Object[] obj= {gasTypeCon.insertGasType(input_gasType),input_gasType};
								dfttable_gasType.addRow(obj);
								JOptionPane.showMessageDialog(null, "新增煤气类型——"+input_gasType, "操作成功", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "您所输入的数据格式错误！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "您所输入的数据为空！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				bt_id = TableTool.cancelTableSelected(table_gasType, bt_id);
			}
		});
		// 删除煤气类型
		jbt_gasType[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (bt_id != -1) {
						int c = JOptionPane.showConfirmDialog(null, "是否确定删除此煤气类型", "验证操作", JOptionPane.YES_NO_OPTION);
						if (c == JOptionPane.YES_OPTION) {
							if (gasCon.existGastype(bt_id)) {
								JOptionPane.showMessageDialog(null, "此煤气类型已经有煤气使用，请尝试将此煤气类型的煤气删除后在删除此煤气类型！！！", "操作失败",
										JOptionPane.ERROR_MESSAGE);
							} else {
								gasTypeCon.deleteGasType(bt_id);
								dfttable_gasType.removeRow(table_gasType.getSelectedRow());
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "您没有选中煤气类型！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				bt_id = TableTool.cancelTableSelected(table_gasType, bt_id);
			}
		});
		// 修改煤气类型
		jbt_gasType[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bt_id != -1) {
					String input_gasType = JOptionPane.showInputDialog(null, "请您输入所要修改的煤气类型(只能输入汉字)", "修改煤气类型",
							JOptionPane.YES_NO_OPTION);
					try {
						if (input_gasType != null && !input_gasType.equals("")) {
							String regex = "^[\\u4e00-\\u9fa5]{0,}$";
							boolean result = InputLimit.regular(regex, input_gasType);
							if (result) {
								int c = JOptionPane.showConfirmDialog(null, "是否确定修改此煤气类型", "验证操作",
										JOptionPane.YES_NO_OPTION);
								if (c == JOptionPane.YES_OPTION) {
									gasTypeCon.updateGasType(input_gasType, bt_id);
									dfttable_gasType.setValueAt(input_gasType, table_gasType.getSelectedRow(), 1);// 将修改的信息放进表格
								}

							} else {
								JOptionPane.showMessageDialog(null, "您所输入的数据格式错误！！！", "操作失败",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "您所输入的数据为空！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "未选择煤气类型！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
				bt_id = TableTool.cancelTableSelected(table_gasType, bt_id);
			}
		});
		return panel;
	}
}
