package CinemaChain;

import java.awt.Choice;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class MyPage extends JFrame {
	JScrollPane scrollPane;
	ImageIcon icon;
	MovieDAO dbms = new MovieDAO();
	ImageIcon logOutIcon;
	ImageIcon loginIcon;
	Choice ticketList;
	MemberDTO infoMember = dbms.infoMember();
	ArrayList<TicketDTO> tickets = dbms.getTicket(dbms.isLogin());

	public MyPage() {
		icon = new ImageIcon("../CinemaChain/src/CinemaChain/myPageBackground.png");

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

		scrollPane = new JScrollPane(background);
		SpringLayout sl_background = new SpringLayout();
		background.setLayout(sl_background);

		loginIcon = new ImageIcon("../CinemaChain/src/CinemaChain/loginBtn.png");
		logOutIcon = new ImageIcon("../CinemaChain/src/CinemaChain/logoutBtn.png");
		JButton logStatus = new JButton(logOutIcon);
		sl_background.putConstraint(SpringLayout.NORTH, logStatus, 41, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.SOUTH, logStatus, 73, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.EAST, logStatus, -16, SpringLayout.EAST, background);
		background.add(logStatus);
		logStatus.setFocusPainted(false); // 선택되었을 때 외곽선 없애기
		logStatus.setContentAreaFilled(false);
		logStatus.setBorder(null);
		logStatus.setOpaque(false); // 투명하게
		logStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dbms.isLogin() != null) {
					dbms.logout();
					ImageIcon mainLogIcon = new ImageIcon("../CinemaChain/src/CinemaChain/loginBtn.png");
					CinemaChainHome.loginButton.setIcon(mainLogIcon);
					dispose();
				}
				// else {
				// Login.main(null);
				// if (dbms.isLogin() != null) {
				// logStatus.setIcon(logOutIcon);
				// }
				// }
			}
		});

		JLabel nameLb = new JLabel();
		sl_background.putConstraint(SpringLayout.NORTH, nameLb, 75, SpringLayout.SOUTH, logStatus);
		sl_background.putConstraint(SpringLayout.WEST, nameLb, 196, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, nameLb, 211, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.EAST, nameLb, -100, SpringLayout.EAST, background);
		nameLb.setFont(new Font("나눔스퀘어", Font.PLAIN, 28));
		background.add(nameLb);

		JLabel eMailLb = new JLabel();
		sl_background.putConstraint(SpringLayout.NORTH, eMailLb, 41, SpringLayout.SOUTH, nameLb);
		sl_background.putConstraint(SpringLayout.WEST, eMailLb, 0, SpringLayout.WEST, nameLb);
		sl_background.putConstraint(SpringLayout.SOUTH, eMailLb, 87, SpringLayout.SOUTH, nameLb);
		sl_background.putConstraint(SpringLayout.EAST, eMailLb, -50, SpringLayout.EAST, background);
		eMailLb.setFont(new Font("나눔스퀘어", Font.PLAIN, 28));
		background.add(eMailLb);

		JLabel birthLb = new JLabel();
		sl_background.putConstraint(SpringLayout.NORTH, birthLb, 58, SpringLayout.SOUTH, eMailLb);
		sl_background.putConstraint(SpringLayout.WEST, birthLb, 196, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, birthLb, 90, SpringLayout.SOUTH, eMailLb);
		sl_background.putConstraint(SpringLayout.EAST, birthLb, -223, SpringLayout.EAST, background);
		birthLb.setFont(new Font("나눔스퀘어", Font.PLAIN, 28));
		background.add(birthLb);

		JButton ticketView = new JButton();
		sl_background.putConstraint(SpringLayout.NORTH, ticketView, -94, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.WEST, ticketView, -311, SpringLayout.EAST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, ticketView, -31, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, ticketView, -198, SpringLayout.EAST, background);
		background.add(ticketView);
		ticketView.setFocusPainted(false); // 선택되었을 때 외곽선 없애기
		ticketView.setContentAreaFilled(false);
		ticketView.setBorder(null);
		ticketView.setOpaque(false); // 투명하게
		ticketView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TicketDTO showTicket = tickets.get(ticketList.getSelectedIndex());
				ShowTicketInfo.main(null, showTicket);
			}
		});

		JButton ticketCencel = new JButton();
		sl_background.putConstraint(SpringLayout.NORTH, ticketCencel, 0, SpringLayout.NORTH, ticketView);
		sl_background.putConstraint(SpringLayout.WEST, ticketCencel, 29, SpringLayout.EAST, ticketView);
		sl_background.putConstraint(SpringLayout.SOUTH, ticketCencel, -31, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, ticketCencel, 135, SpringLayout.EAST, ticketView);
		background.add(ticketCencel);
		ticketCencel.setFocusPainted(false); // 선택되었을 때 외곽선 없애기
		ticketCencel.setContentAreaFilled(false);
		ticketCencel.setBorder(null);
		ticketCencel.setOpaque(false); // 투명하게
		ticketCencel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ticketList.countItems() < 1) {
					JOptionPane.showMessageDialog(null, "예매된 티켓이 없습니다.!");
				} else {
					int confirm = JOptionPane.showConfirmDialog(null, "정말로 예매를 취소하시겠습니까?", "Ticket delete",
							JOptionPane.OK_CANCEL_OPTION);
					if (confirm == 0) {
						int selectedItem = ticketList.getSelectedIndex();
						// String title, String date, String theater, String screen, String time,String
						// seat
						TicketDTO clear = tickets.get(selectedItem);
						// 시간이 얼마나 남았는지, 이미 종료된 영화이면 그냥 취소되고 시간이 남아있다면 환불해준다. //아직안했음...
						String date = clear.getDate() + " " + clear.getCinemaTime();
						dateCalculate(date);
						int cnt = dbms.clearTicket(clear.getTitle(), clear.getDate(), clear.getTheater(),
								clear.getScreen(), clear.getCinemaTime(), clear.getSeat());
						if (cnt > 0) {
							// 어레이리스트에서 삭제, 초이스에서 삭제
							ticketList.remove(selectedItem);
							tickets.remove(selectedItem);
							JOptionPane.showMessageDialog(null, "티켓 삭제 완료!");
							System.out.println("티켓 : 으앙 쥬금 ㅠㅠ");
						} else {
							JOptionPane.showMessageDialog(null, "티켓 삭제 오류!\n계속해서 오류 발생시 관리자와 문의하세요.");
							System.out.println("티켓 : 오잉? 나 아직 안죽었네?");
						}
					}
				}

			}

			private void dateCalculate(String date) { // 반환타입 바꿔야함.Date 또는 String....?
				try {
					new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
					new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		ticketList = new Choice();
		sl_background.putConstraint(SpringLayout.WEST, ticketList, -184, SpringLayout.WEST, ticketView);
		sl_background.putConstraint(SpringLayout.SOUTH, ticketList, -59, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, ticketList, -72, SpringLayout.WEST, ticketView);
		background.add(ticketList);
		setContentPane(scrollPane);

		birthLb.setText(infoMember.getBirth().substring(0, 10));
		nameLb.setText(infoMember.getName());
		eMailLb.setText(infoMember.geteMail());

		for (int i = 0; i < tickets.size(); i++) {
			if (tickets.size() != 0) {
				ticketList.add(tickets.get(i).getTitle() + " " + tickets.get(i).getCinemaTime());
			}
		}
	}

	public static void main(String[] args) {
		MyPage frame = new MyPage();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(670, 590);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
