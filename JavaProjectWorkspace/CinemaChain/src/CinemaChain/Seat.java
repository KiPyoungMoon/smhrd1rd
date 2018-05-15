package CinemaChain;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

public class Seat extends JFrame {

	ArrayList<MovieDTO> selectedList = CinemaChainHome.movieInfoListCenter;
	private String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	private String selected = ""; // 선택된 자리들을 넣는 String변수
	int leftSeatNumber; // 남은 좌석수
	String seatString = "";
	int countSeat = 0;

	private MovieDAO dbms = new MovieDAO();
	private ImageIcon icon;
	private JScrollPane scrollPane;
	static int width = 800;
	static int height = 750;

	public static void main(String[] args, String theater, String screen, String time) {
		Seat frame = new Seat(theater, screen, time);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(width, height); // 여기서 이미지 사이즈 조절하세요!
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Create the application.
	 */

	public Seat(String theater, String screen, String time) {
		leftSeatNumber = 80;

		icon = new ImageIcon("../CinemaChain/src/CinemaChain/screen.png"); // 이미지 경로 넣어주세요

		// 배경 Panel 생성후 컨텐츠페인으로 지정
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				// Approach 1: Dispaly image at at full size
				// g.drawImage(icon.getImage(), 0, 0, null);
				// Approach 2: Scale image to size of component
				// Dimension d = getSize();
				g.drawImage(icon.getImage(), 0, 0, null);
				// Approach 3: Fix the image position in the scroll pane
				// Point p = scrollPane.getViewport().getViewPosition();
				// g.drawImage(icon.getImage(), p.x, p.y, null);
				setOpaque(false); // 그림을 표시하게 설정,투명하게 조절
				super.paintComponent(g);
			}
		};

		SpringLayout springLayout = new SpringLayout();
		background.setLayout(springLayout);

		scrollPane = new JScrollPane(background);

		Choice adultChoice = new Choice();
		springLayout.putConstraint(SpringLayout.WEST, adultChoice, 57, SpringLayout.WEST, background);
		springLayout.putConstraint(SpringLayout.EAST, adultChoice, -589, SpringLayout.EAST, background);
		background.add(adultChoice);

		Choice teenagerChoice = new Choice();
		springLayout.putConstraint(SpringLayout.SOUTH, teenagerChoice, -301, SpringLayout.SOUTH, background);
		springLayout.putConstraint(SpringLayout.SOUTH, adultChoice, -94, SpringLayout.NORTH, teenagerChoice);
		springLayout.putConstraint(SpringLayout.WEST, teenagerChoice, 0, SpringLayout.WEST, adultChoice);
		springLayout.putConstraint(SpringLayout.EAST, teenagerChoice, -589, SpringLayout.EAST, background);
		background.add(teenagerChoice);

		Choice childrenChoice = new Choice();
		springLayout.putConstraint(SpringLayout.NORTH, childrenChoice, 87, SpringLayout.SOUTH, teenagerChoice);
		springLayout.putConstraint(SpringLayout.WEST, childrenChoice, 0, SpringLayout.WEST, adultChoice);
		springLayout.putConstraint(SpringLayout.EAST, childrenChoice, -589, SpringLayout.EAST, background);
		background.add(childrenChoice);

		for (int i = 0; i <= leftSeatNumber; i++) {
			adultChoice.add(i + "");
			teenagerChoice.add(i + "");
			childrenChoice.add(i + "");
		}

		JButton btnConfirm = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, btnConfirm, 650, SpringLayout.NORTH, background);
	      springLayout.putConstraint(SpringLayout.WEST, btnConfirm, -260, SpringLayout.EAST, background);
	      springLayout.putConstraint(SpringLayout.SOUTH, btnConfirm, 720, SpringLayout.NORTH, background);
	      springLayout.putConstraint(SpringLayout.EAST, btnConfirm, -30, SpringLayout.EAST, background);

		background.add(btnConfirm);

		btnConfirm.setBorderPainted(false);
		btnConfirm.setBorder(null);
		btnConfirm.setFocusPainted(false);
		btnConfirm.setContentAreaFilled(false);
		btnConfirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String[] spilt = selected.split(" ");

				int limit = Integer.parseInt(adultChoice.getSelectedItem())
						+ Integer.parseInt(teenagerChoice.getSelectedItem())
						+ Integer.parseInt(childrenChoice.getSelectedItem());
				if (selected.equals("")) {

				} else if (limit == spilt.length) {
					TicketCalDTO dto = new TicketCalDTO(Integer.parseInt(adultChoice.getSelectedItem()),
							Integer.parseInt(teenagerChoice.getSelectedItem()),
							Integer.parseInt(childrenChoice.getSelectedItem()), selected, time);

					// db에 티켓 정보 넣기

					int confirm = JOptionPane.showConfirmDialog(null,
							"결제 확인창\n총 결제 금액은 " + totalTicketPrice(dto) + "입니다.", "your payment",
							JOptionPane.OK_CANCEL_OPTION);
					if (confirm == 0) {
						// System.out.println("확인을 눌렀음");
						int cnt = dbms.ticket_payment(dbms.isLogin(), selectedList.get(0).getTitle(), date, theater,
								screen, time, selected, totalTicketPrice(dto));
						if (cnt > 0) {
							JOptionPane.showMessageDialog(null, "결제 완료!", "Success", JOptionPane.YES_OPTION);
						} else {
							JOptionPane.showMessageDialog(null, "실행 문제가 발생하였습니다. 계속해서 오류가 발생할 경우 관리자에게 문의하십시오.", "Fail",
									JOptionPane.WARNING_MESSAGE);
						}
						dispose();
					}
				} else if (limit < spilt.length) {
					JOptionPane.showMessageDialog(null, "예약 좌석 수 초과!", "Index value exception",
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "예약 좌석 수 만큼 선택해주세요.", "Index value exception",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		// 남은자리만 보여주는거...

		// selected 안에 있는 인덱스 갯수랑 최대 선택가능한 자릿수를 비교해서 잘라낸다.
		// 확인 버튼 눌렀을 때 해야하나
		// 여기다 추가로 자릿수 체크해서 if문으로 처리한다던지...

		// 확인 버튼 액션 이벤트에 들어갈것들 미리 만들어봄
		String nn = "냠냠이 기여운데 어떻게 이걸 세상 사람들에게 알려야 할지 모르겠다. 그러니까 막 귀엽고 이래 귀엽고...";

		JLabel leftSeatNumLb = new JLabel(""); // 이 라벨이 남은 자릿수 표시해줄 라벨.
		 springLayout.putConstraint(SpringLayout.NORTH, leftSeatNumLb, 20, SpringLayout.NORTH, btnConfirm);
	      springLayout.putConstraint(SpringLayout.EAST, leftSeatNumLb, -50, SpringLayout.WEST, btnConfirm);
	      leftSeatNumLb.setForeground(Color.WHITE);
		background.add(leftSeatNumLb);
		leftSeatNumLb.setFont(new Font("나눔스퀘어 Bold", Font.PLAIN, 17));
		// leftSeatNumLb.setBorder(LineBorder.createGrayLineBorder());

		leftSeatNumLb.setText(leftSeatNumber + "");

		setContentPane(scrollPane);

		int width = 290;
		int height = 220;
		JButton[][] btnSeat = new JButton[8][10]; // 좌석 8*10칸

		ImageIcon seat = fileImgResize(20, 20, "../CinemaChain/src/CinemaChain/seat1.png");

		for (int i = 0; i < btnSeat.length; i++) {
			for (int j = 0; j < btnSeat[0].length; j++) {
				String index = "" + (i) + (j);
				btnSeat[i][j] = new JButton(seat);

				btnSeat[i][j].setBorderPainted(false);
				btnSeat[i][j].setFocusPainted(false);

				btnSeat[i][j].setContentAreaFilled(false);
				springLayout.putConstraint(SpringLayout.NORTH, btnSeat[i][j], height + (j * 35), SpringLayout.NORTH,
						background);
				springLayout.putConstraint(SpringLayout.WEST, btnSeat[i][j], width + (i * 55), SpringLayout.WEST,
						background);

				btnSeat[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// System.out.println("선택한 자리 : " + index);
						int limit = Integer.parseInt(adultChoice.getSelectedItem())
								+ Integer.parseInt(teenagerChoice.getSelectedItem())
								+ Integer.parseInt(childrenChoice.getSelectedItem());
						if (countSeat < limit) {
							seatIndex(btnSeat, index);
						} else if (countSeat == limit) {
							if (selected.contains(index)) {
								seatIndex(btnSeat, index);
							} else {
								JOptionPane.showMessageDialog(null,
										"다른 자리를 선택하고 싶은 경우\n이미 선택했던 자리를 취소하거나\n인원 수를 변경해주십시오.", "Index value exception",
										JOptionPane.YES_OPTION);
							}
						}
						leftSeatNumLb.setText(leftSeatNumber + "");

					}
				});
				background.add(btnSeat[i][j]);

			}
		}

		isSelected(theater, screen, time, btnSeat);

	}

	public void isSelected(String theater, String screen, String time, JButton[][] btnSeat) {
		ArrayList<String> reserved = dbms.getSeat(selectedList.get(0).getTitle(), date, theater, screen, time);
		for (int i = 0; i < reserved.size(); i++) {
			if (!(reserved.get(i) == null))
				seatString += reserved.get(i);
		}

		String[] seatArr = seatString.split(" ");
		for (int i = 0; i < seatArr.length; i++) {
			if (!seatArr[i].equals("")) {

				int foreIndex = Integer.parseInt(seatArr[i].substring(0, 1));
				int rearIndex = Integer.parseInt(seatArr[i].substring(1, 2));

				btnSeat[foreIndex][rearIndex].setEnabled(false);
				btnSeat[foreIndex][rearIndex].setBackground(new Color(Integer.parseInt("FF7043", 16)));
				btnSeat[foreIndex][rearIndex].setContentAreaFilled(true);

				leftSeatNumber--;
			}
		}
	}

	public String selectChair(String index) { // 자리 선택 메소드
		if (selected.contains(index)) {
			// index의 위치를 파악해서 그 위치부터 끝, 시작부터 그 위치 전까지 긁어서총 2개의 스트링에 저장해놨다가 selected를 비운 후
			// 다시 넣어준다.
			return selected.replace(index + " ", "");
		}
		return selected + index + " ";
	}

	public void btnPush(JButton btn) {

		if (SystemColor.control != btn.getBackground()) {
			leftSeatNumber--;
			countSeat++;
			btn.setBackground(SystemColor.control);
			btn.setContentAreaFilled(true);
			return;
		}
		leftSeatNumber++;
		countSeat--;
		btn.setContentAreaFilled(false);
		btn.setBackground(Color.getColor("#ff0099"));
	}

	public void seatIndex(JButton[][] btnSeat, String index) {
		selected = selectChair(index);
		btnPush(btnSeat[Integer.parseInt(index.substring(0, 1))][Integer.parseInt(index.substring(1, 2))]);
	}

	public static ImageIcon fileImgResize(int width, int height, String url) { // gui화면에서 main밑 아무데나 넣어주면 됩니다. 이미지 주소
		// 필요해요!
		ImageIcon poster = null;
		try {
			File imgUrl = new File(url);
			poster = new ImageIcon(ImageIO.read(imgUrl).getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return poster;
	}

	public static int totalTicketPrice(TicketCalDTO tc) {
		int adultPrice = 11000;
		int TeenagerPrice = 9000;
		int childPrice = 7000;

		int totalPrice = 0;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();

		try {
			boolean week = getDateDay(dateFormat.format(date), "yyyy/MM/dd");
			String[] array = tc.getTime().split(":");

			int tt = Integer.parseInt(array[0]);
			int ss = Integer.parseInt(array[1]);

			if (week == true) {// 월~목
				adultPrice -= 2000;
				TeenagerPrice -= 1000;
				childPrice -= 1000;

				if ((5 <= tt && tt < 11) || (tt == 11 && ss == 0)) {// 조조

					adultPrice -= 3000;
					TeenagerPrice -= 2000;

				} else if (tt >= 23 || tt < 5 || (tt == 5 && ss == 0)) {// 심야 기준 새벽5시 이전

					adultPrice -= 1000;
					TeenagerPrice -= 1000;
				}

			} else {
				if ((5 <= tt && tt < 11) || (tt == 11 && ss == 0)) {// 조조

					adultPrice -= 4000;
					TeenagerPrice -= 3000;
					childPrice -= 1000;

				} else if (tt >= 23 || tt < 5 || (tt == 5 && ss == 0)) {// 심야 기준 새벽5시 이전

					adultPrice -= 2000;
					TeenagerPrice -= 1000;
				}

			}

			// 좌석의 경우
			String[] seatArray = tc.getSeat().split(" ");
			int[] seat = new int[seatArray.length];
			for (int i = 0; i < seatArray.length; i++) {// 뒤에 스페이스 고료햐보긔
				seat[i] = Integer.parseInt(seatArray[i].substring(1, 2));
			}

			int cnt = 0;
			for (int i = 0; i < seat.length; i++) {
				if (0 <= seat[i] && seat[i] <= 2) {
					cnt++;
				}
			}

			totalPrice = tc.getAdult() * adultPrice + tc.getTeenager() * TeenagerPrice + tc.getChild() * childPrice
					- cnt * 1000;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return totalPrice;
	}

	public static boolean getDateDay(String date, String dateType) throws Exception {

		String day = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
		Date nDate = dateFormat.parse(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(nDate);

		int dayNum = cal.get(Calendar.DAY_OF_WEEK);

		if (2 <= dayNum && dayNum <= 5) {
			return true;
		} else {
			return false;
		}
	}

}