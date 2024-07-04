package View;

import Controller.UserCon;
import Controller.UserCommunityCon;
import Model.table.User;
import Tool.InputLimit;
import Tool.MD5Tool;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * ע�����
 */
public class Login extends JFrame implements ActionListener, ItemListener {
    JLabel[] jlab = {new JLabel("�˺ţ�"), new JLabel("������"), new JLabel("С����"), new JLabel("¥����"),
            new JLabel("�ֻ����룺"), new JLabel("�������䣺"), new JLabel("�ܱ���"), new JLabel("���룺"),
            new JLabel("ȷ�����룺")};// ������ǩ����
    JButton jbt = new JButton("ȷ��");

    UserCon readercon = new UserCon();
    JTextField jtext[] = new JTextField[5];
    JComboBox<String> jcb_userCommunity = new JComboBox<String>();
    JComboBox<String> jcb_buildings = new JComboBox<String>();
    JPasswordField jpassword[] = new JPasswordField[2];
    String u_community = "���С��", buildings = "2��";
    String[] hint = {"20��ͷ��11λ����", "���ĺ���", "���ĺ���", "���ĺ��ּ�����", "�ֻ��Ÿ�ʽ", "�����ʽ", "��������"};
    User user = new User();
    UserCommunityCon userCommunityCon = new UserCommunityCon();

    public Login() throws SQLException {
        int[] inuput_int = {11, 5, 11, 20, 15};// �������������λ��
        for (int i = 0; i < jtext.length; i++) {
            jtext[i] = new JTextField();
            if (i < 2) {
                jtext[i].setBounds(150, 20 + i * 40, 150, 30);
            }
            if (i >= 2) {
                jtext[i].setBounds(150, 20 + (i + 2) * 40, 150, 30);
            }
            jtext[i].setDocument(new InputLimit(inuput_int[i]));// ��������
            jtext[i].addFocusListener(new InputLimit(jtext[i], hint[i]));// ������ڲ��ʾ���ⲿ�����
            this.add(jtext[i]);
        }
        jcb_buildings.setBounds(150, 140, 80, 30);
        jcb_buildings.addItem("1��");jcb_buildings.addItem("2��");jcb_buildings.addItem("3��");jcb_buildings.addItem("4��");jcb_buildings.addItem("5��");jcb_buildings.addItem("6��");
        jcb_userCommunity.setBounds(150, 100, 80, 30);
        String[] userCommunity = userCommunityCon.getUserCommunity();
        for (int k = 0; k < userCommunity.length; k++) {
            jcb_userCommunity.addItem(userCommunity[k]);
        }
        jcb_userCommunity.setVisible(true);
        jcb_buildings.setVisible(true);
        for (int j = 0; j < jpassword.length; j++) {
            jpassword[j] = new JPasswordField();
            jpassword[j].setBounds(150, 380 + j * 40, 150, 30);
            this.add(jpassword[j]);
            jpassword[j].setDocument(new InputLimit(16));
        }
        for (int k = 0; k < jlab.length; k++) {
            jlab[k].setBounds(20, 20 + k * 40, 100, 40);
            jlab[k].setHorizontalAlignment(4);
            this.add(jlab[k]);
        }
        jbt.setBounds(160, 500, 100, 40);
        this.setLayout(null);
        this.setTitle("�˺�ע��");
        this.setSize(400, 600);
        ImageIcon icon = new ImageIcon("src/Images/icon.png");
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);// ���ô��������ʾ
        this.setResizable(false);// ���ڲ��ܸı��С
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// �������ڹرհ�ť,�Զ����ز��ͷŸô���
        this.setVisible(true);// ʹ������ʾ
        // ����������
        this.add(jbt);
        this.add(jcb_userCommunity);
        this.add(jcb_buildings);
        // ��Ӽ���
        jbt.addActionListener(this);// ���ð�ť�ļ�����
        jcb_userCommunity.addItemListener(this);
        jcb_buildings.addItemListener(this);
        // ���ڹر��¼�
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (ManageFace.count != null) {
                    dispose();
                } else {
                    new Main().jtext.setText(jtext[0].getText());
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] regex = {InputLimit.STUDENTNUMBER, InputLimit.NAME, InputLimit.CHINESE, InputLimit.CHINESEMATH,
                InputLimit.TELE, InputLimit.EMAIL, InputLimit.PASSWORD};
        // ��֤�˺ţ���20��ͷ��11λ���֣�����֤���������ģ�����֤�ֻ��š���֤���䡢��֤���루6-16λ���ֻ�����ĸ��
        String[] input = {jtext[0].getText(), jtext[1].getText(),
                jtext[2].getText(), jtext[3].getText(), new String(jpassword[0].getPassword())};
        String[] hintError = {"�˺Ÿ�ʽ����", "������ʽ����", "�ֻ��Ÿ�ʽ����", "�����ʽ����", "�����ʽ����6-16λ���ֻ���ĸ��"};
        String message = "";
        boolean result[] = InputLimit.regular(regex, input);
        Object obj = e.getSource();
        if (obj == jbt) {
            try {
                for (int i = 0; i < jtext.length; i++) {
                    for (int j = 0; j < jpassword.length; j++) {
                        if (jtext[i].getText().equals("") || new String(jpassword[j].getPassword()).equals("")) {
                            JOptionPane.showMessageDialog(null, "����ע�����Ϣ�в���Ϊ��", "��Ϣ����", JOptionPane.ERROR_MESSAGE);
                            return;// ����ѭ������ֹһֱѭ�������Ի���
                        }
                    }
                }
                for (int i = 0; i < result.length; i++) {
                    if (! result[i]) {
                        message += "\n" + hintError[i];
                    }
                }
                if (message.equals("")) {// ����������֤
                    // ������������һ�£�����ע�������Ϣ�ķ���
                    if (new String(jpassword[0].getPassword()).equals(new String(jpassword[1].getPassword()))) {
                        // ����˺��Ƿ�ע��
                        if (! readercon.isNumber(jtext[0].getText())) {
                            readercon.insertReader(jtext[0].getText(), jtext[1].getText(), userCommunityCon.queryUserCommunityID(u_community),
                                    buildings
                                    , jtext[2].getText(), jtext[3].getText(),
                                    jtext[4].getText(), jtext[5].getText(), jtext[6].getText(),
                                    MD5Tool.string2MD5(new String(jpassword[0].getPassword())));
                            // ���������MD5���ܺ��������Ϣ
                            JOptionPane.showMessageDialog(null, "�˺��ѳɹ�ע��", "�����ɹ�", JOptionPane.INFORMATION_MESSAGE);
                            this.dispose();
                            int c = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫֱ�ӽ��е�¼����", "��֤����",
                                    JOptionPane.YES_NO_OPTION);
                            if (c == JOptionPane.YES_OPTION) {
                                new Main().jtext.setText(jtext[0].getText());// ����ע����˺�set����¼ҳ��
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "���˺��Ѿ���ע��\n������˺ź��ٽ���ע��", "����ʧ��",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "�����������벻һ��", "��Ϣ����", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, message, "������Ϣ����", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    /**
     * ���������¼�
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        // TODO Auto-generated method stub
        if (e.getStateChange() == ItemEvent.SELECTED) {
            u_community = jcb_userCommunity.getSelectedItem().toString();
            buildings = jcb_buildings.getSelectedItem().toString();
        }
    }
}
