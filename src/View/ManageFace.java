package View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * ����Ա����
 * @author rsw
 *
 */
public class ManageFace extends JFrame {
	final int WIDTH = 1000, HEIGHT = 730;
	JTabbedPane jtab = new JTabbedPane(JTabbedPane.TOP);// ����ѡ�����ѡ��������Ϸ�
	JPanel[] jpan = new JPanel[5];
	static String count;
	ManageGas manageGas =new ManageGas();
	ManageReader manageReader=new ManageReader();
	ManageReaderType  manageReaderType=new ManageReaderType();
	ManageBorrowInfo manageBorrowInfo =new  ManageBorrowInfo();
	ManageSuper manageSuper =new  ManageSuper();	
	public ManageFace(String count) throws SQLException {
		this.count=count;
		this.setLayout(null);
		this.setTitle("ͼ�����ϵͳ������Ա�ˣ�");
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);// ���ô��������ʾ
		this.setResizable(false);// ���ڲ��ܸı��С
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// �������ڹرհ�ť����������
		this.setVisible(true);// ʹ������ʾ
		
		jtab.setSize(WIDTH, HEIGHT);

		jpan[0] = manageGas.addPanel0();
		jtab.addTab("ͼ����Ϣ����", jpan[0]);
		jtab.setSelectedIndex(0);

		jpan[1] = manageReader.addPanel2();
		jtab.addTab("������Ϣ����", jpan[1]);

		jpan[2] = manageReaderType.addPanel3();
		jtab.addTab("�������͹���   ", jpan[2]);

		jpan[3] = manageBorrowInfo.addPanel4();
		jtab.addTab("ͼ����Ĺ���", jpan[3]);
		
		jpan[4] = manageSuper.addPanel5(count);
		jtab.addTab("��������Ա", jpan[4]);

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
