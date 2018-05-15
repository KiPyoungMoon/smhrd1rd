package CinemaChain;

import java.awt.Choice;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SignUp extends JFrame {

	private ImageIcon icon;
	private JScrollPane scrollPane;
	private MovieDAO dbms = new MovieDAO();
	private SpringLayout springLayout;
	private JTextField emailBlank;
	private JButton duplicateChk;
	private JPasswordField pwBlank;
	private JPasswordField passwordField;
	private JTextField nameBlank;
	private Choice choiceYear;
	private Choice choiceMonth;
	private Choice choiceDay;
	private ImageIcon pass;

	/**
	 * Launch the application.
	 */
	public SignUp() {

		icon = new ImageIcon("../CinemaChain/src/CinemaChain/signUp.png");
		pass = new ImageIcon("../CinemaChain/src/CinemaChain/eMailPass.png");

		// 배경 Panel 생성후 컨텐츠페인으로 지정
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				// Approach 1: Dispaly image at at full size
				g.drawImage(icon.getImage(), 0, 0, null);
				// Approach 2: Scale image to size of component
				// Dimension d = getSize();
				// g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
				// Approach 3: Fix the image position in the scroll pane
				// Point p = scrollPane.getViewport().getViewPosition();
				// g.drawImage(icon.getImage(), p.x, p.y, null);
				setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
				super.paintComponent(g);
			}
		};
		SpringLayout sl_background = new SpringLayout();
		background.setLayout(sl_background);

		scrollPane = new JScrollPane(background);

		emailBlank = new JTextField();
		sl_background.putConstraint(SpringLayout.NORTH, emailBlank, 100, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, emailBlank, 153, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, emailBlank, 130, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.EAST, emailBlank, -106, SpringLayout.EAST, background);
		background.add(emailBlank);
		emailBlank.setColumns(10);
		setContentPane(scrollPane);

		emailBlank.setBorder(null);
		emailBlank.setOpaque(false); // 투명하게

		duplicateChk = new JButton();
		sl_background.putConstraint(SpringLayout.NORTH, duplicateChk, 0, SpringLayout.NORTH, emailBlank);
		sl_background.putConstraint(SpringLayout.WEST, duplicateChk, 17, SpringLayout.EAST, emailBlank);
		sl_background.putConstraint(SpringLayout.SOUTH, duplicateChk, 0, SpringLayout.SOUTH, emailBlank);
		sl_background.putConstraint(SpringLayout.EAST, duplicateChk, -22, SpringLayout.EAST, background);
		background.add(duplicateChk);
		duplicateChk.setContentAreaFilled(false);
		duplicateChk.setBorder(null);

		pwBlank = new JPasswordField();
		sl_background.putConstraint(SpringLayout.NORTH, pwBlank, 37, SpringLayout.SOUTH, emailBlank);
		sl_background.putConstraint(SpringLayout.WEST, pwBlank, 0, SpringLayout.WEST, emailBlank);
		sl_background.putConstraint(SpringLayout.SOUTH, pwBlank, -258, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, pwBlank, 0, SpringLayout.EAST, emailBlank);
		background.add(pwBlank);
		pwBlank.setColumns(10);

		pwBlank.setBorder(null);
		pwBlank.setOpaque(false); // 투명하게
		pwBlank.setEnabled(false);

		passwordField = new JPasswordField();
		sl_background.putConstraint(SpringLayout.NORTH, passwordField, 36, SpringLayout.SOUTH, pwBlank);
		sl_background.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, emailBlank);
		sl_background.putConstraint(SpringLayout.SOUTH, passwordField, -192, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, emailBlank);
		background.add(passwordField);
		passwordField.setEnabled(false);
		passwordField.setBorder(null);
		passwordField.setOpaque(false);

		nameBlank = new JTextField();
		sl_background.putConstraint(SpringLayout.NORTH, nameBlank, 29, SpringLayout.SOUTH, passwordField);
		sl_background.putConstraint(SpringLayout.WEST, nameBlank, 153, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, nameBlank, 59, SpringLayout.SOUTH, passwordField);
		sl_background.putConstraint(SpringLayout.EAST, nameBlank, 0, SpringLayout.EAST, emailBlank);
		background.add(nameBlank);
		nameBlank.setColumns(10);
		nameBlank.setBorder(null);
		nameBlank.setEnabled(false);

		choiceYear = new Choice();
		sl_background.putConstraint(SpringLayout.NORTH, choiceYear, 18, SpringLayout.SOUTH, nameBlank);
		sl_background.putConstraint(SpringLayout.WEST, choiceYear, 0, SpringLayout.WEST, emailBlank);
		sl_background.putConstraint(SpringLayout.SOUTH, choiceYear, 48, SpringLayout.SOUTH, nameBlank);
		sl_background.putConstraint(SpringLayout.EAST, choiceYear, 278, SpringLayout.WEST, background);
		background.add(choiceYear);
		for (int i = 2017; i > 1917; i--) {
			choiceYear.add(i + "");
		}

		choiceMonth = new Choice();
		sl_background.putConstraint(SpringLayout.NORTH, choiceMonth, 18, SpringLayout.SOUTH, nameBlank);
		sl_background.putConstraint(SpringLayout.WEST, choiceMonth, 6, SpringLayout.EAST, choiceYear);
		sl_background.putConstraint(SpringLayout.SOUTH, choiceMonth, 49, SpringLayout.SOUTH, nameBlank);
		sl_background.putConstraint(SpringLayout.EAST, choiceMonth, 91, SpringLayout.EAST, choiceYear);
		background.add(choiceMonth);
		for (int i = 1; i <= 12; i++) {
			choiceMonth.add(i + "");
		}

		choiceDay = new Choice();
		sl_background.putConstraint(SpringLayout.NORTH, choiceDay, 0, SpringLayout.NORTH, choiceYear);
		sl_background.putConstraint(SpringLayout.WEST, choiceDay, 6, SpringLayout.EAST, choiceMonth);
		sl_background.putConstraint(SpringLayout.SOUTH, choiceDay, 0, SpringLayout.SOUTH, choiceYear);
		sl_background.putConstraint(SpringLayout.EAST, choiceDay, -12, SpringLayout.EAST, emailBlank);
		background.add(choiceDay);
		for (int i = 1; i <= 31; i++) {
			choiceDay.add(i + "");
		}

		JButton btnConfirm = new JButton("");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		sl_background.putConstraint(SpringLayout.NORTH, btnConfirm, -58, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.WEST, btnConfirm, -347, SpringLayout.EAST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, btnConfirm, -18, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, btnConfirm, -178, SpringLayout.EAST, background);
		background.add(btnConfirm);
		btnConfirm.setContentAreaFilled(false);
		btnConfirm.setBorder(null);
		btnConfirm.setOpaque(false);

		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = nameBlank.getText();
				String eMail = emailBlank.getText();
				char[] passwordChar = pwBlank.getPassword();
				String password = "";
				for (int i = 0; i < passwordChar.length; i++) {
					password += passwordChar[i];
				}
				System.out.println("pass : " + password);
				char[] pwConfirmChar = passwordField.getPassword();
				String pwConfirm = "";
				for (int i = 0; i < pwConfirmChar.length; i++) {
					pwConfirm += pwConfirmChar[i];
				}
				System.out.println("pwcon : " + pwConfirm);
				String month = choiceMonth.getSelectedItem();
				if (month.length() == 1) {
					month = "0" + choiceMonth.getSelectedItem();
				}
				String day = choiceDay.getSelectedItem();
				if (day.length() == 1) {
					day = "0" + choiceDay.getSelectedItem();
				}
				String birth = choiceYear.getSelectedItem() + month + day;
				System.out.println("birth : " + birth);

				if (!dbms.isEmail(eMail)) {
					JOptionPane.showMessageDialog(null, "이메일 형식에 맞게 입력해주세요.");
					emailBlank.setText(null);
				} else {
					if (!password.equals(pwConfirm)) {
						JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.");
						passwordField.setText(null);
						pwBlank.setText(null);
					} else if (password.length() < 8 || password.length() > 16) {
						JOptionPane.showMessageDialog(null, "8~16자의 비밀번호를 넣어주세요.");
						passwordField.setText(null);
						pwBlank.setText(null);
					} else {
						if (name.length() > 15) {
							JOptionPane.showMessageDialog(null, "15자 이내의 이름을 입력해주세요.");
							nameBlank.setText(null);
						} else {
							int cnt = dbms.insertMember(eMail, password, birth, name);

							if (cnt > 0) {
								JOptionPane.showMessageDialog(null, "가입 성공. 로그인 해주세요.");
								dispose();
							} else {
								JOptionPane.showMessageDialog(null, "가입 실패. 다시 입력해주세요.");
							}
						}
					}
				}

			}
		});

		duplicateChk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (dbms.isDuplicate(emailBlank.getText())) {
					JOptionPane.showMessageDialog(null, "중복된 이메일입니다.");
					emailBlank.setText(null);
				} else if (!dbms.isEmail(emailBlank.getText())) {
					JOptionPane.showMessageDialog(null, "정확한 이메일 주소를 넣어주세요.");
				} else {
					nameBlank.setEnabled(true);
					pwBlank.setEnabled(true);
					pwBlank.setEchoChar('*');
					passwordField.setEnabled(true);
					passwordField.setEchoChar('*');
					choiceYear.setEnabled(true);
					choiceMonth.setEnabled(true);
					choiceDay.setEnabled(true);
					duplicateChk.setIcon(pass);
				}
			}
		});

	}

	public static void main(String[] args) {
		SignUp frame = new SignUp();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(565, 495);
		frame.setVisible(true);
	}
}
