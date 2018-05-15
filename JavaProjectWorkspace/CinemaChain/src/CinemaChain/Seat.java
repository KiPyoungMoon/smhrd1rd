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
	private String selected = ""; // ���õ� �ڸ����� �ִ� String����
	int leftSeatNumber; // ���� �¼���
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
		frame.setSize(width, height); // ���⼭ �̹��� ������ �����ϼ���!
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Create the application.
	 */

	public Seat(String theater, String screen, String time) {
		leftSeatNumber = 80;

		icon = new ImageIcon("../CinemaChain/src/CinemaChain/screen.png"); // �̹��� ��� �־��ּ���

		// ��� Panel ������ �������������� ����
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
				setOpaque(false); // �׸��� ǥ���ϰ� ����,�����ϰ� ����
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

					// db�� Ƽ�� ���� �ֱ�

					int confirm = JOptionPane.showConfirmDialog(null,
							"���� Ȯ��â\n�� ���� �ݾ��� " + totalTicketPrice(dto) + "�Դϴ�.", "your payment",
							JOptionPane.OK_CANCEL_OPTION);
					if (confirm == 0) {
						// System.out.println("Ȯ���� ������");
						int cnt = dbms.ticket_payment(dbms.isLogin(), selectedList.get(0).getTitle(), date, theater,
								screen, time, selected, totalTicketPrice(dto));
						if (cnt > 0) {
							JOptionPane.showMessageDialog(null, "���� �Ϸ�!", "Success", JOptionPane.YES_OPTION);
						} else {
							JOptionPane.showMessageDialog(null, "���� ������ �߻��Ͽ����ϴ�. ����ؼ� ������ �߻��� ��� �����ڿ��� �����Ͻʽÿ�.", "Fail",
									JOptionPane.WARNING_MESSAGE);
						}
						dispose();
					}
				} else if (limit < spilt.length) {
					JOptionPane.showMessageDialog(null, "���� �¼� �� �ʰ�!", "Index value exception",
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "���� �¼� �� ��ŭ �������ּ���.", "Index value exception",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		// �����ڸ��� �����ִ°�...

		// selected �ȿ� �ִ� �ε��� ������ �ִ� ���ð����� �ڸ����� ���ؼ� �߶󳽴�.
		// Ȯ�� ��ư ������ �� �ؾ��ϳ�
		// ����� �߰��� �ڸ��� üũ�ؼ� if������ ó���Ѵٴ���...

		// Ȯ�� ��ư �׼� �̺�Ʈ�� ���͵� �̸� ����
		String nn = "�ȳ��� �⿩� ��� �̰� ���� ����鿡�� �˷��� ���� �𸣰ڴ�. �׷��ϱ� �� �Ϳ��� �̷� �Ϳ���...";

		JLabel leftSeatNumLb = new JLabel(""); // �� ���� ���� �ڸ��� ǥ������ ��.
		 springLayout.putConstraint(SpringLayout.NORTH, leftSeatNumLb, 20, SpringLayout.NORTH, btnConfirm);
	      springLayout.putConstraint(SpringLayout.EAST, leftSeatNumLb, -50, SpringLayout.WEST, btnConfirm);
	      leftSeatNumLb.setForeground(Color.WHITE);
		background.add(leftSeatNumLb);
		leftSeatNumLb.setFont(new Font("���������� Bold", Font.PLAIN, 17));
		// leftSeatNumLb.setBorder(LineBorder.createGrayLineBorder());

		leftSeatNumLb.setText(leftSeatNumber + "");

		setContentPane(scrollPane);

		int width = 290;
		int height = 220;
		JButton[][] btnSeat = new JButton[8][10]; // �¼� 8*10ĭ

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
						// System.out.println("������ �ڸ� : " + index);
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
										"�ٸ� �ڸ��� �����ϰ� ���� ���\n�̹� �����ߴ� �ڸ��� ����ϰų�\n�ο� ���� �������ֽʽÿ�.", "Index value exception",
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

	public String selectChair(String index) { // �ڸ� ���� �޼ҵ�
		if (selected.contains(index)) {
			// index�� ��ġ�� �ľ��ؼ� �� ��ġ���� ��, ���ۺ��� �� ��ġ ������ �ܾ�� 2���� ��Ʈ���� �����س��ٰ� selected�� ��� ��
			// �ٽ� �־��ش�.
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

	public static ImageIcon fileImgResize(int width, int height, String url) { // guiȭ�鿡�� main�� �ƹ����� �־��ָ� �˴ϴ�. �̹��� �ּ�
		// �ʿ��ؿ�!
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

			if (week == true) {// ��~��
				adultPrice -= 2000;
				TeenagerPrice -= 1000;
				childPrice -= 1000;

				if ((5 <= tt && tt < 11) || (tt == 11 && ss == 0)) {// ����

					adultPrice -= 3000;
					TeenagerPrice -= 2000;

				} else if (tt >= 23 || tt < 5 || (tt == 5 && ss == 0)) {// �ɾ� ���� ����5�� ����

					adultPrice -= 1000;
					TeenagerPrice -= 1000;
				}

			} else {
				if ((5 <= tt && tt < 11) || (tt == 11 && ss == 0)) {// ����

					adultPrice -= 4000;
					TeenagerPrice -= 3000;
					childPrice -= 1000;

				} else if (tt >= 23 || tt < 5 || (tt == 5 && ss == 0)) {// �ɾ� ���� ����5�� ����

					adultPrice -= 2000;
					TeenagerPrice -= 1000;
				}

			}

			// �¼��� ���
			String[] seatArray = tc.getSeat().split(" ");
			int[] seat = new int[seatArray.length];
			for (int i = 0; i < seatArray.length; i++) {// �ڿ� �����̽� ����Ẹ��
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