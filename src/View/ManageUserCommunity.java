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
 * 用户类型信息管理面板
 *  
 *
 */
public class ManageUserCommunity {
	int a, u_id=-1, maxcount, nowcount,row;
	String userCommunity;
	UserCommunityCon userCommunityCon = new UserCommunityCon();
	UserCon userCon =new UserCon();
	boolean isCompile, refresh;// 是否可以编辑

	protected JPanel addPanel3() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_userCommunity = new JPanel();
		jpanup_userCommunity.setLayout(null);
		jpanup_userCommunity.setPreferredSize(new Dimension(1000, 80));

		JButton[] jbt_userCommunity = {new JButton("新增小区"), new JButton("删除小区"),
				new JButton("修改小区") };
		for (int i = 0; i < jbt_userCommunity.length; i++) {
			jbt_userCommunity[i].setBounds(300 + i * 150, 20, 120, 30);
			jpanup_userCommunity.add(jbt_userCommunity[i]);
		}
		String[] columnuserCommunity = { "序号", "小区名", "最大户数","当前户数" };
		Object[][] userCommunityData = userCommunityCon.queryReaderType();
		DefaultTableModel dfttable_userCommunity = new DefaultTableModel(userCommunityData, columnuserCommunity);
		JTable table_userCommunity = new JTable(dfttable_userCommunity) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		ListSelectionModel selectionModel = table_userCommunity.getSelectionModel();// 创建表格选择器
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 一次只能选择一个列表索引
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
		//设置表格的公共属性
		TableTool.setTable(table_userCommunity, dfttable_userCommunity);
		table_userCommunity.setPreferredScrollableViewportSize(new Dimension(900, 700));// 超过范围出现滚动
		JScrollPane scrollPane = new JScrollPane(table_userCommunity);
		panel.add(jpanup_userCommunity, BorderLayout.NORTH);
		panel.add(scrollPane);
		// 新增小区
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
		// 删除小区
		jbt_userCommunity[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (u_id != -1) {
					int c = JOptionPane.showConfirmDialog(null, "是否确定删除此小区", "验证操作", JOptionPane.YES_NO_OPTION);
					if (c == JOptionPane.YES_OPTION) {
						try {
							if(userCon.existReadertype(u_id)) {
								JOptionPane.showMessageDialog(null, "此小区已经有用户使用，请尝试将此小区的用户删除后在删除此小区！", "操作失败", JOptionPane.ERROR_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "您没有选中小区！", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
				u_id = TableTool.cancelTableSelected(table_userCommunity, u_id);
			}
		});
		// 修改小区
		jbt_userCommunity[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel[] jlab_userCommunity = { new JLabel("小区序号："), new JLabel("小区名："), new JLabel("最大户数："),new JLabel("当前户数：")};
				JLabel[] jlab_hint = { new JLabel("不可修改"), new JLabel("中文汉字"), new JLabel("整数"), new JLabel("整数")};
				JTextField[] jtext_userCommunity = new JTextField[4];
				Object[] userCommunityUpdata = { u_id, userCommunity, maxcount ,nowcount};
				if (u_id != -1) {
					try {
						// 弹出修改用户类型的对话框
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
					JOptionPane.showMessageDialog(null, "您没有选中小区！", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
				u_id = TableTool.cancelTableSelected(table_userCommunity, u_id);
			}
		});
		return panel;
	}
}
