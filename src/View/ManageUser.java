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
 * 读者信息管理面板
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
		JButton[] jbt_user = { new JButton("查询用户信息"), new JButton("新增用户信息"), new JButton("删除用户信息"),
				new JButton("修改用户信息") };
		JTextField jtext_user = new JTextField();
		JComboBox<String> jcb_user = new JComboBox<String>();
		for (int i = 0; i < jbt_user.length; i++) {
			jbt_user[i].setBounds(370 + i * 130, 20, 120, 30);
			jpanup_user.add(jbt_user[i]);
		}
		jtext_user.setBounds(160, 20, 200, 30);
		jtext_user.addFocusListener(new InputLimit(jtext_user, "账号/姓名"));//
		// 设置文诓提示的外部类监听
		jcb_user.setBounds(50, 20, 80, 30);
		jcb_user.addItem("全部");
		String[] readerType = userCommunityCon.getUserCommunity();
		for (int k = 0; k < readerType.length; k++) {
			jcb_user.addItem(readerType[k]);
		}
		jcb_user.setVisible(true);
		// 下拉框点击事件
		jcb_user.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (jcb_user.getSelectedItem() == "全部") {
						userCommunityInfo = "*";
					} else {
						userCommunityInfo = jcb_user.getSelectedItem().toString();
					}
				}
			}
		});
		Vector<String> columnNameUser = new Vector<String>();// 字段名
		String[] columnUser = { "账号", "姓名", "小区", "楼栋", "手机号码", "电子邮箱" };
		for (int k = 0; k < columnUser.length; k++) {
			columnNameUser.add(columnUser[k]);
		}
		DefaultTableModel dfttable_user = new DefaultTableModel(userCon.seleUser(), columnNameUser);
		JTable table_user = new JTable(dfttable_user) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		ListSelectionModel selectionModel = table_user.getSelectionModel();// 创建表格选择器
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 一次只能选择一个列表索引
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
		//设置表格的公共属性
		TableTool.setTable(table_user, dfttable_user);
		table_user.setPreferredScrollableViewportSize(new Dimension(900, 700));// 超过范围出现滚动条
		JScrollPane scrollPane = new JScrollPane(table_user);
		jpanup_user.add(jcb_user);
		jpanup_user.add(jtext_user);
		panel.add(jpanup_user, BorderLayout.NORTH);
		panel.add(scrollPane);
		// 查询
		jbt_user[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dfttable_user.setRowCount(0);
				dfttable_user.fireTableDataChanged();
				try {
					if (userCommunityInfo == "*" || userCommunityInfo == null) {
						if (jtext_user.getText().equals("账号/姓名") || jtext_user.getText().equals("")) {
							dfttable_user.setDataVector(userCon.seleUser(), columnNameUser);
						} else {
							dfttable_user.setDataVector(userCon.queryUserInfo(jtext_user.getText(),
									 jtext_user.getText()),
									columnNameUser);
						}
					} else {
						if (jtext_user.getText().equals("账号/姓名")) {
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
		// 新增
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
		// 删除
		jbt_user[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (studentNumber != null) {
					try {
						int c = JOptionPane.showConfirmDialog(null, "是否确定删除此图书类型", "验证操作", JOptionPane.YES_NO_OPTION);
						if (c == JOptionPane.YES_OPTION) {
							userCon.dropReader(studentNumber);
							dfttable_user.removeRow(table_user.getSelectedRow());// 删除表格中的这一行
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "您没有选中读者！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
				studentNumber = TableTool.setNull(table_user, studentNumber);
			}
		});
		// 更新
		jbt_user[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel[] jlab_user = { new JLabel("账号："),  new JLabel("手机号："),
						new JLabel("邮箱：") };
				JLabel[] jlab_hint = { new JLabel("不可修改"), new JLabel("中文汉字"), new JLabel("中文汉字或者数字"),
						new JLabel("13、14、15、17、18开头"), new JLabel("邮箱格式")};
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
					JOptionPane.showMessageDialog(null, "您没有选中读者！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
				}
				studentNumber = TableTool.setNull(table_user, studentNumber);
			}
		});
		return panel;
	}
}
