package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * ������ͼ
 * 
 * @author Administrator
 *
 */
public class View {

	// ��������ֵ
	private final int WIDTH = 600;
	private final int HEIGHT = 500;

	// �����¼�ı���
	public static JTextArea area;

	// �ͻ���ʵ�����
	Client client=new Client();
	
	/**
	 * ����һ����ͼ
	 */
	public void create() {
		// ���ӷ�����
		client.conn("127.0.0.1", 30000);
		
		//����
		JFrame frame = new JFrame("�������");
		
		// ��¼���
		JPanel loadPanel = new JPanel();
		loadPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		frame.add(loadPanel, BorderLayout.NORTH);
		
		// ��ǩ�Լ������
		final JLabel userLabel = new JLabel("   �û�δ��¼");
		final JTextField userTextField = new JTextField(20);
		//���
		loadPanel.add(userLabel);
		loadPanel.add(userTextField);
		
		//���ûس���¼�¼�
		userTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					String user = userTextField.getText();
					if (user != null && !user.equals("")) {
						client.load(user);
						userLabel.setText("   user:" + user);
						userTextField.setText("");
						userTextField.setVisible(false);
					}
				}
			}
		});

		// �����¼���
		JPanel topPanel = new JPanel();
		loadPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// �����¼�ı���
		area = new JTextArea(14, 51);
		area.setEditable(false);
		// ������
		JScrollPane jsp = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//���
		frame.add(topPanel);
		topPanel.add(jsp);

		// �ײ��������
		JPanel bottomPanel = new JPanel();
		frame.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setPreferredSize(new Dimension(WIDTH, 165));
		// �ı���
		final JTextArea ta = new JTextArea();
		ta.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		ta.setFont(new Font("����", Font.PLAIN, 15));
		ta.setPreferredSize(new Dimension(WIDTH - 35, 100));
		ta.setText("//������������");
		ta.select(0, 0);
		ta.setLineWrap(true);
		//���ûس�������Ϣ
		ta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					if (!ta.getText().equals("") && userLabel.getText().indexOf("user:") != -1) {
						client.sendMsg(ta.getText());
						ta.setText("");
					} else {
						System.out.println("�û�δ��¼������Ϊ��");
					}
					e.consume();
				}
			}
		});
		
		//������������������Ķ�̬Ч��
		ta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (ta.getText().equals("") || ta.getText().equals("//������������"))
					ta.setText("");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (ta.getText().equals(""))
					ta.setText("//������������");
			}
		});

		// ��ť���
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		buttonPanel.setPreferredSize(new Dimension(WIDTH, 50));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		// ��ť
		JButton sendButton = new JButton("����");
		buttonPanel.add(sendButton);
		sendButton.setFocusPainted(false);
		//��Ӱ�ť��������¼�
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ta.getText() != null && ta.getText().length() != 0 && userLabel.getText().indexOf("user:") != -1) {
					client.sendMsg(ta.getText());
					ta.setText("");
				} else {
					System.out.println("�û�δ��¼������Ϊ��");
				}
			}
		});
		
		// �ײ������ӿؼ�
		bottomPanel.add(ta);
		bottomPanel.add(buttonPanel);
		//��Ӵ��ڹر��Զ��˳�ϵͳ�¼�
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				client.logout();
			}
		});

		// ��������
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * ����������������
	 * ִ����ͼ��ʵ����
	 * @param args
	 */
	public static void main(String[] args) {
		View view=new View();
		view.create();
	}
}
