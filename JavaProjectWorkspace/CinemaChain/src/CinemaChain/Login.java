package CinemaChain;

import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Login extends JFrame {

	private ImageIcon image;
	private JTextField eMailBlank;
	private JPasswordField passwordBlank;
	private static MovieDAO dbms = new MovieDAO();
	private ImageIcon logOutIcon;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		Login frame = new Login();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(565, 486);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Create the application.
	 */
	public Login() {

		image = new ImageIcon("../CinemaChain/src/CinemaChain/login.png"); // �̰� ���� �Ť����� �� �����������..

		// ��� Panel ������ �������������� ����
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				// Approach 1: Dispaly image at at full size
				g.drawImage(image.getImage(), 0, 0, null);
				// Approach 2: Scale image to size of component
				// Dimension d = getSize();
				// g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
				// Approach 3: Fix the image position in the scroll pane
				// Point p = scrollPane.getViewport().getViewPosition();
				// g.drawImage(icon.getImage(), p.x, p.y, null);
				setOpaque(false); // �׸��� ǥ���ϰ� ����,�����ϰ� ����
				super.paintComponent(g);
			}
		};

		JScrollPane scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
		SpringLayout springLayout = new SpringLayout();
		background.setLayout(springLayout);

		eMailBlank = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, eMailBlank, 148, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, eMailBlank, 175, SpringLayout.WEST, background);
		springLayout.putConstraint(SpringLayout.SOUTH, eMailBlank, 179, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.EAST, eMailBlank, -82, SpringLayout.EAST, background);
		background.add(eMailBlank);
		eMailBlank.setColumns(10);
		eMailBlank.setBorder(null);
		eMailBlank.setOpaque(false); // �����ϰ�

		passwordBlank = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, passwordBlank, 57, SpringLayout.SOUTH, eMailBlank);
		springLayout.putConstraint(SpringLayout.WEST, passwordBlank, 0, SpringLayout.WEST, eMailBlank);
		springLayout.putConstraint(SpringLayout.SOUTH, passwordBlank, 88, SpringLayout.SOUTH, eMailBlank);
		springLayout.putConstraint(SpringLayout.EAST, passwordBlank, -82, SpringLayout.EAST, background);
		background.add(passwordBlank);
		// passwordBlank.setEchoChar(c);
		passwordBlank.setColumns(10);
		passwordBlank.setBorder(null);
		passwordBlank.setOpaque(false); // �����ϰ�
		passwordBlank.addKeyListener(new KeyListener() { //����Ű ������ �α��� �̺�Ʈ!!
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginAction();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		JButton login = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, login, 68, SpringLayout.SOUTH, passwordBlank);
		springLayout.putConstraint(SpringLayout.WEST, login, 175, SpringLayout.WEST, background);
		springLayout.putConstraint(SpringLayout.SOUTH, login, 104, SpringLayout.SOUTH, passwordBlank);
		springLayout.putConstraint(SpringLayout.EAST, login, -253, SpringLayout.EAST, background);
		background.add(login);
		login.setBorder(null);
		login.setContentAreaFilled(false);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //���⿡ �ִ��� ���� �޼ҵ�� ������. ����Ű ���� �� �̺�Ʈ �ַ���...
				loginAction();
			}
		});

		JButton signUp = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, signUp, 68, SpringLayout.SOUTH, passwordBlank);
		springLayout.putConstraint(SpringLayout.WEST, signUp, 62, SpringLayout.EAST, login);
		springLayout.putConstraint(SpringLayout.SOUTH, signUp, 0, SpringLayout.SOUTH, login);
		springLayout.putConstraint(SpringLayout.EAST, signUp, -79, SpringLayout.EAST, background);
		background.add(signUp);
		signUp.setBorder(null);
		signUp.setContentAreaFilled(false);
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp.main(null);
			}
		});
		// ���⿡ ��ư �ڵ� �߰�.
	}
	public void loginAction() {
		String eMail = eMailBlank.getText();
		char[] passwordChar = passwordBlank.getPassword();
		String password = "";
		for (int i = 0; i < passwordChar.length; i++) {
			password += passwordChar[i];
		}
		System.out.println("eMial : " + eMail);
		System.out.println("password : " + password);
		if (dbms.isDuplicate(eMail) == false) {
			JOptionPane.showMessageDialog(null, "���Ե� eMail�� �ƴմϴ�.");
		} else {
			int cnt = dbms.login(eMail, password);
			if (cnt > 0) {
				// Dialog
				String loginBtnImgUrl = "../CinemaChain/src/CinemaChain/myPage.png";
				CinemaChainHome.loginButton.setIcon(new ImageIcon(loginBtnImgUrl));
				// logOutIcon = new ImageIcon("../CinemaChain/src/CinemaChain/logoutBtn.png");
				// MyPage.logStatus.setIcon(logOutIcon);
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "�α��� ����.");
				eMailBlank.setText(null);
				passwordBlank.setText(null);
			}
		}
	}
}
