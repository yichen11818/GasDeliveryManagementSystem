package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * 用户端界面
 *  
 *
 */
public class UserFace extends JFrame {
	final int WIDTH = 1000, HEIGHT = 730;
	static String count;
	JTabbedPane jtab = new JTabbedPane(JTabbedPane.TOP);// 创建选项卡窗格，选项卡标题在上方
	JPanel[] jpan = new JPanel[5];
	UserGas userGas =new UserGas();
	UserCloseGas userCloseGas =new UserCloseGas();
	UserMessage userMessage =new UserMessage();
	public UserFace(String count) throws SQLException {
		this.count = count;
		this.setLayout(null);
		this.setTitle("煤气管理系统（用户端）");
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);// 设置窗体居中显示
		this.setResizable(false);// 窗口不能改变大小
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// 单击窗口关闭按钮，结束程序
		this.setVisible(true);// 使窗口显示
		jtab.setSize(WIDTH, HEIGHT);
		
		jpan[0] = userGas.addPanel0();
		jtab.addTab("煤气选择", jpan[0]);
		jtab.setSelectedIndex(0);
		
		jpan[1] = userCloseGas.addPanel1();
		jtab.addTab("煤气使用管理", jpan[1]);

		jpan[2] = userMessage.addPanel2();
		jtab.addTab("个人信息管理", jpan[2]);
		this.add(jtab);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int c = JOptionPane.showConfirmDialog(null, "是否要退出系统程序", "操作验证", JOptionPane.YES_NO_OPTION);
				if (c == JOptionPane.YES_OPTION) {
						System.exit(0);
				}
			}
		});
	}
}
