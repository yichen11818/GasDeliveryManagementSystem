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
 * 煤气选退
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
		JButton[] jbt_return = { new JButton("已选煤气"), new JButton("取消煤气"), new JButton("查看历史") };
		for (int i = 0; i < jbt_return.length; i++) {
			jbt_return[i].setBounds(200 + i * 150, 20, 100, 40);
			jpanup.add(jbt_return[i]);
		}
		Object[] columnChose = { "使用序号", "煤气名","煤气类型", "供应商名", "使用起始时间", "使用结束时间", "实际使用时间" };
		try {
			choseDate = chosecon.queryChoseInfo(UserFace.count, UserFace.count, false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DefaultTableModel dfttableChose = new DefaultTableModel(choseDate, columnChose);
		JTable tableChose = new JTable(dfttableChose) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		ListSelectionModel selectionModel = tableChose.getSelectionModel();// 创建表格选择器
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 一次只能选择一个列表索引
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (selectionModel.getValueIsAdjusting()) {
					if (tableChose.getSelectedRow() < 0) {
						return;
					}
					int counts = tableChose.getSelectedRow();// 获取你选中的行号（记录）
					getChoseName = tableChose.getValueAt(counts, 2).toString();// 读取你获取行号的某一列
					chose_id = Integer.valueOf(tableChose.getValueAt(counts, 0).toString());
				}
			}
		});
		TableTool.setTable(tableChose, dfttableChose);
		// 超过范围出现滚动条
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
				dfttableChose.setDataVector(choseDate, columnChose);// 设定模型数据和字段,初始化该表
				chose_id = TableTool.cancelTableSelected(tableChose, chose_id);
			}
		});
		// 归还图书
		jbt_return[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chose_id != -1) {
					int c = JOptionPane.showConfirmDialog(null, "是否确定取消此煤气", "验证操作", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						String returnGas = "inventory=inventory+1";
						try {
							// 增加实际归还时间
							if(chosecon.returnChose(TimeTool.getNewStamep(), chose_id,gascon.seleB_name(getChoseName))) {
							dfttableChose.removeRow(tableChose.getSelectedRow());// 归还图书，删除选中的此行
							JOptionPane.showMessageDialog(null, "您已经成功取消使用" + getChoseName, "操作成功",
									JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} // 将文本框中的书名传参，以但方便修改“库存量”
					}
				} else {
					JOptionPane.showMessageDialog(null, "您没有选中煤气！", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
				chose_id = TableTool.cancelTableSelected(tableChose, chose_id);
			}
		});
		// 点击“查看历史”——查阅个人历史借阅信息(最好最新借阅的排在前边)
		jbt_return[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbt_return[1].setEnabled(false);
				dfttableChose.fireTableDataChanged();
				try {
					choseDate = chosecon.queryChoseReturnDate(UserFace.count, UserFace.count, true);
					dfttableChose.setDataVector(choseDate, columnChose);// 设定模型数据和字段,初始化该表
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
