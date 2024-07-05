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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.ChoseCon;
import Tool.InputLimit;
import Tool.TableTool;

/**
 * 借阅归还信息管理面板
 * @author rsw
 *
 */
public class ManageChoseInfo {
	ChoseCon Chosecon = new ChoseCon();
	String adm_count;
	Object[] columnChose = { "序号", "ISBN", "书名", "图书类型", "作者", "借阅时间", "应还时间", "实际归还时间" };

	protected JPanel addPanel4() throws SQLException {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel jpanup_Chose = new JPanel();
		jpanup_Chose.setLayout(null);
		jpanup_Chose.setPreferredSize(new Dimension(1000, 80));
		JButton[] jbt_Chose = { new JButton("查询借阅信息"), new JButton("历史借阅信息") };
		JTextField jtext_Chose = new JTextField();
		for (int i = 0; i < jbt_Chose.length; i++) {
			jbt_Chose[i].setBounds(280 + i * 140, 20, 120, 30);
			jpanup_Chose.add(jbt_Chose[i]);
		}
		jtext_Chose.setBounds(60, 20, 200, 30);
		jtext_Chose.addFocusListener(new InputLimit(jtext_Chose, "账号"));// 设置文诓提示的外部类监听
		DefaultTableModel dfttableChose = new DefaultTableModel();
		JTable table_Chose = new JTable(dfttableChose) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		//设置表格的公共属性
		TableTool.setTable(table_Chose, dfttableChose);	
		table_Chose.setPreferredScrollableViewportSize(new Dimension(900, 700));// 超过范围出现滚动条
		table_Chose.getTableHeader().setReorderingAllowed(false); // 设置整列不可移动

		JScrollPane scrollPane = new JScrollPane(table_Chose);
		jpanup_Chose.add(jtext_Chose);
		panel.add(jpanup_Chose, BorderLayout.NORTH);
		panel.add(scrollPane);
		//查询借阅信息
		jbt_Chose[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm_count = jtext_Chose.getText();
				try {
					if (adm_count.equals("账号") || adm_count.equals("")) {
						JOptionPane.showMessageDialog(null, "请输入账号！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
					} else {
						dfttableChose.setDataVector(Chosecon.queryChoseInfo(adm_count, adm_count, false),
								columnChose);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//查寻历史借阅信息
		jbt_Chose[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm_count = jtext_Chose.getText();
				try {
					if (adm_count.equals("账号") || adm_count.equals("")) {
						JOptionPane.showMessageDialog(null, "请输入账号！！！", "操作失败", JOptionPane.ERROR_MESSAGE);
					} else {
						dfttableChose.setDataVector(Chosecon.queryChoseReturnDate(adm_count, adm_count, true),
								columnChose);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		return panel;
	}
}
