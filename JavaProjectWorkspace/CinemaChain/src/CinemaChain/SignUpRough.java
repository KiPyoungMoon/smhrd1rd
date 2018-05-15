package CinemaChain;

import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class SignUpRough {

	private JFrame frame;
	private JTextField emailBlank;
	private JPasswordField pwBlank;
	private JPasswordField pwConfirmBlank;
	private JLabel nameLabel;
	private JTextField nameBlank;
	private JButton btnConfirm;
	private MovieDAO dbms = new MovieDAO();
	ImageIcon icon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpRough window = new SignUpRough();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignUpRough() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		icon = new ImageIcon("../CinemaChain/src/CinemaChain/signUp.png");

		// 배경 Panel 생성후 컨텐츠페인으로 지정
		JPanel backSignUp = new JPanel() {
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

		emailBlank = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, emailBlank, 40, SpringLayout.NORTH, frame.getContentPane());
		emailBlank.setText("이메일");
		springLayout.putConstraint(SpringLayout.NORTH, emailBlank, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, emailBlank, 150, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(emailBlank);
		emailBlank.setColumns(10);

		pwBlank = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, pwBlank, 14, SpringLayout.SOUTH, emailBlank);
		springLayout.putConstraint(SpringLayout.EAST, pwBlank, 0, SpringLayout.EAST, emailBlank);
		pwBlank.setText("비밀번호");

		pwBlank.setEnabled(false);
		frame.getContentPane().add(pwBlank);
		pwBlank.setColumns(10);

		pwConfirmBlank = new JPasswordField();
		springLayout.putConstraint(SpringLayout.SOUTH, pwBlank, -6, SpringLayout.NORTH, pwConfirmBlank);
		springLayout.putConstraint(SpringLayout.NORTH, pwConfirmBlank, 97, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, pwConfirmBlank, 0, SpringLayout.EAST, emailBlank);
		pwConfirmBlank.setText("비밀번호 재입력");
		pwConfirmBlank.setEnabled(false);
		frame.getContentPane().add(pwConfirmBlank);
		pwConfirmBlank.setColumns(10);

		JLabel emailLabel = new JLabel("이메일", Label.RIGHT);
		springLayout.putConstraint(SpringLayout.NORTH, emailLabel, 13, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, emailLabel, 108, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, emailLabel, -6, SpringLayout.WEST, emailBlank);
		frame.getContentPane().add(emailLabel);

		JLabel pwLabel = new JLabel("패스워드", Label.RIGHT);
		springLayout.putConstraint(SpringLayout.NORTH, pwLabel, 57, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, emailLabel, -29, SpringLayout.NORTH, pwLabel);
		springLayout.putConstraint(SpringLayout.WEST, pwBlank, 8, SpringLayout.EAST, pwLabel);
		frame.getContentPane().add(pwLabel);

		JLabel lblNewLabel_2 = new JLabel("패스워드 확인", Label.RIGHT);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_2, 28, SpringLayout.SOUTH, pwLabel);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_2, -284, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pwConfirmBlank, 0, SpringLayout.EAST, lblNewLabel_2);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_2, 65, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("생일", Label.RIGHT);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_3, 0, SpringLayout.EAST, emailLabel);
		frame.getContentPane().add(lblNewLabel_3);

		nameLabel = new JLabel("이름", Label.RIGHT);
		springLayout.putConstraint(SpringLayout.WEST, pwLabel, 0, SpringLayout.WEST, nameLabel);
		springLayout.putConstraint(SpringLayout.NORTH, nameLabel, 61, SpringLayout.SOUTH, lblNewLabel_2);
		frame.getContentPane().add(nameLabel);

		nameBlank = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, nameBlank, 150, SpringLayout.WEST, frame.getContentPane());
		nameBlank.setText("이름");
		nameBlank.setEnabled(false);
		springLayout.putConstraint(SpringLayout.EAST, nameLabel, -32, SpringLayout.WEST, nameBlank);
		springLayout.putConstraint(SpringLayout.NORTH, nameBlank, -3, SpringLayout.NORTH, nameLabel);
		frame.getContentPane().add(nameBlank);
		nameBlank.setColumns(10);

		btnConfirm = new JButton("확인");
		springLayout.putConstraint(SpringLayout.NORTH, btnConfirm, 21, SpringLayout.SOUTH, nameBlank);
		springLayout.putConstraint(SpringLayout.WEST, btnConfirm, 178, SpringLayout.WEST, frame.getContentPane());
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
				char[] pwConfirmChar = pwConfirmBlank.getPassword();
				String pwConfirm = "";
				for (int i = 0; i < pwConfirmChar.length; i++) {
					pwConfirm += pwConfirmChar[i];
				}
				System.out.println("pwcon : " + pwConfirm);
				String birth = "19820212";

				if (!dbms.isEmail(eMail)) {
					JOptionPane.showMessageDialog(null, "이메일 형식에 맞게 입력해주세요.");
				} else {
					if (!password.equals(pwConfirm)) {
						JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.");
					} else if (password.length() < 8 || password.length() > 16) {
						JOptionPane.showMessageDialog(null, "8~16자의 비밀번호를 넣어주세요.");
					} else {
						if (name.length() > 15) {
							JOptionPane.showMessageDialog(null, "15자 이내의 이름을 입력해주세요.");
						} else {
							int cnt = dbms.insertMember(eMail, password, birth, name);

							if (cnt > 0) {
								JOptionPane.showMessageDialog(null, "회원 가입이 완료되었습니다.");
								System.exit(0);
							} else {
								JOptionPane.showMessageDialog(null, "가입 실패. 다시 입력해주세요.");
								nameBlank.setText("이름");
								emailBlank.setText("이메일");
								pwBlank.setText("비밀번호");
								pwConfirmBlank.setText("비밀번호 재입력");
							}
						}
					}
				}

			}
		});

		frame.getContentPane().add(btnConfirm);
		Choice choiceYear = new Choice();
		springLayout.putConstraint(SpringLayout.NORTH, choiceYear, 133, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, pwConfirmBlank, -6, SpringLayout.NORTH, choiceYear);
		springLayout.putConstraint(SpringLayout.WEST, choiceYear, 6, SpringLayout.EAST, lblNewLabel_3);
		for (int i = 2017; i > 1917; i--) {
			choiceYear.add(i + "");
		}
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_3, 0, SpringLayout.NORTH, choiceYear);
		frame.getContentPane().add(choiceYear);

		Choice choiceMonth = new Choice();
		springLayout.putConstraint(SpringLayout.NORTH, choiceMonth, 6, SpringLayout.SOUTH, pwConfirmBlank);
		springLayout.putConstraint(SpringLayout.WEST, choiceMonth, 204, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, choiceYear, -6, SpringLayout.WEST, choiceMonth);
		nameBlank.setEnabled(false);
		for (int i = 1; i <= 12; i++) {
			choiceMonth.add(i + "");
		}
		frame.getContentPane().add(choiceMonth);

		Choice choiceDay = new Choice();
		springLayout.putConstraint(SpringLayout.NORTH, choiceDay, 6, SpringLayout.SOUTH, pwConfirmBlank);
		nameBlank.setEnabled(false);
		for (int i = 1; i <= 31; i++) {
			choiceDay.add(i + "");
		}
		springLayout.putConstraint(SpringLayout.WEST, choiceDay, 6, SpringLayout.EAST, choiceMonth);
		frame.getContentPane().add(choiceDay);

		JButton duplicateChk = new JButton("중복  체크");
		springLayout.putConstraint(SpringLayout.NORTH, duplicateChk, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, duplicateChk, 290, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, emailBlank, -6, SpringLayout.WEST, duplicateChk);
		duplicateChk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dbms.isDuplicate(emailBlank.getText())) {
					JOptionPane.showMessageDialog(null, "중복된 이메일입니다.");
					emailBlank.setText("이메일");
				} else {
					nameBlank.setEnabled(true);
					pwBlank.setEnabled(true);
					pwBlank.setEchoChar('*');
					pwConfirmBlank.setEnabled(true);
					pwConfirmBlank.setEchoChar('*');
					choiceYear.setEnabled(true);
					choiceMonth.setEnabled(true);
					choiceDay.setEnabled(true);
					// duplicateChk.setLabel("성공.");
				}
			}
		});
		frame.getContentPane().add(duplicateChk);
	}
}
