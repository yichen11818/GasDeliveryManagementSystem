package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * �û��˽���
 *  
 *
 */
public class UserFace extends JFrame {
	final int WIDTH = 1000, HEIGHT = 730;
	static String count;
	JTabbedPane jtab = new JTabbedPane(JTabbedPane.TOP);// ����ѡ�����ѡ��������Ϸ�
	JPanel[] jpan = new JPanel[5];
	UserGas userGas =new UserGas();
	UserCloseGas userCloseGas =new UserCloseGas();
	UserMessage userMessage =new UserMessage();
	public UserFace(String count) throws SQLException {
		this.count = count;
		this.setLayout(null);
		this.setTitle("ú������ϵͳ���û��ˣ�");
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);// ���ô��������ʾ
		this.setResizable(false);// ���ڲ��ܸı��С
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// �������ڹرհ�ť����������
		this.setVisible(true);// ʹ������ʾ
		jtab.setSize(WIDTH, HEIGHT);
		
		jpan[0] = userGas.addPanel0();
		jtab.addTab("ú��ѡ��", jpan[0]);
		jtab.setSelectedIndex(0);
		
		jpan[1] = userCloseGas.addPanel1();
		jtab.addTab("ú��ʹ�ù���", jpan[1]);

		jpan[2] = userMessage.addPanel2();
		jtab.addTab("������Ϣ����", jpan[2]);
		this.add(jtab);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int c = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫ�˳�ϵͳ����", "������֤", JOptionPane.YES_NO_OPTION);
				if (c == JOptionPane.YES_OPTION) {
						System.exit(0);
				}
			}
		});
	}
}
