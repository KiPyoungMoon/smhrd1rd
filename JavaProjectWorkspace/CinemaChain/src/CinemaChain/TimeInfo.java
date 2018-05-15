package CinemaChain;

import java.awt.Choice;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class TimeInfo extends JFrame {

	private ImageIcon icon;
	private JScrollPane scrollPane;
	private Choice theaterChoice;

	public TimeInfo() {

		icon = new ImageIcon("../CinemaChain/src/CinemaChain/ticketing.png");

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

		theaterChoice = new Choice();
		sl_background.putConstraint(SpringLayout.NORTH, theaterChoice, 235, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.SOUTH, theaterChoice, 281, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.EAST, theaterChoice, -114, SpringLayout.EAST, background); //
		background.add(theaterChoice);

		String posterUrl = CinemaChainHome.movieInfoListCenter.get(0).getPosterSrc();
		JButton poster = new JButton(CinemaChainHome.imgResize(360, 480, posterUrl));

		sl_background.putConstraint(SpringLayout.WEST, theaterChoice, 99, SpringLayout.EAST, poster); //

		sl_background.putConstraint(SpringLayout.NORTH, poster, 134, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, poster, 39, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, poster, 614, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.EAST, poster, 400, SpringLayout.WEST, background);
		background.add(poster);
		poster.setBorder(null);
		poster.setFocusable(false);
		poster.setFocusPainted(false);

		Choice screenChoice = new Choice();
		sl_background.putConstraint(SpringLayout.NORTH, screenChoice, 75, SpringLayout.SOUTH, theaterChoice);
		sl_background.putConstraint(SpringLayout.WEST, screenChoice, 99, SpringLayout.EAST, poster);
		sl_background.putConstraint(SpringLayout.EAST, screenChoice, -114, SpringLayout.EAST, background);
		background.add(screenChoice);

		Choice showTimeChoice = new Choice();
		sl_background.putConstraint(SpringLayout.NORTH, showTimeChoice, 503, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.SOUTH, screenChoice, -86, SpringLayout.NORTH, showTimeChoice);
		sl_background.putConstraint(SpringLayout.WEST, showTimeChoice, 99, SpringLayout.EAST, poster);
		sl_background.putConstraint(SpringLayout.EAST, showTimeChoice, -114, SpringLayout.EAST, background);
		background.add(showTimeChoice);

		JButton getSeat = new JButton();
		sl_background.putConstraint(SpringLayout.SOUTH, showTimeChoice, -113, SpringLayout.NORTH, getSeat);
		sl_background.putConstraint(SpringLayout.EAST, getSeat, -100, SpringLayout.EAST, background);
		sl_background.putConstraint(SpringLayout.NORTH, getSeat, 630, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, getSeat, 477, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, getSeat, 699, SpringLayout.NORTH, background);

		getSeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!showTimeChoice.getSelectedItem().equals("종료")) {
					// 좌석 선택 화면에 넘겨줄 영화관, 상영관, 상영시간.
					Seat.main(null, theaterChoice.getSelectedItem(), screenChoice.getSelectedItem(),
							showTimeChoice.getSelectedItem());
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "오늘의 영화가 모두 종료되었습니다.");
				}
			}
		});
		background.add(getSeat);
		setContentPane(scrollPane);
		getSeat.setContentAreaFilled(false);
		getSeat.setBorder(null);
		getSeat.setOpaque(false);

		for (int i = 0; i < CinemaChainHome.movieInfoListCenter.size(); i++) {
			String theater = CinemaChainHome.movieInfoListCenter.get(i).getTheater();
			// 영화 타이틀에 맞는 영화관 목록 뿌리기
			if (i == 0) {
				theaterChoice.add(theater);
			} else if (!theater.equals(CinemaChainHome.movieInfoListCenter.get(i - 1).getTheater())) {
				theaterChoice.add(theater);
			}
		}
		screenMouseAction(screenChoice);
		timeMouseAction(screenChoice, showTimeChoice);
		theaterChoice.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				screenMouseAction(screenChoice);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				screenMouseAction(screenChoice);
			}
		});
		screenChoice.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// 스크린 초이스를 선택 했을 때, 해당 영화관의 스크린 정보를 스크린 초이스에 뿌리기.
				screenMouseAction(screenChoice);
				timeMouseAction(screenChoice, showTimeChoice);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

		});
	}

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		TimeInfo frame = new TimeInfo();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(870, 790);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public void screenMouseAction(Choice screenChoice) {
		screenChoice.removeAll();
		for (int i = 0; i < CinemaChainHome.movieInfoListCenter.size(); i++) {
			if (theaterChoice.getSelectedItem().equals(CinemaChainHome.movieInfoListCenter.get(i).getTheater())) {
				if (screenChoice.getItemCount() == 0)
					screenChoice.add(CinemaChainHome.movieInfoListCenter.get(i).getScreen());
				else if (!CinemaChainHome.movieInfoListCenter.get(i).getScreen()
						.equals(CinemaChainHome.movieInfoListCenter.get(i - 1).getScreen())) {
					screenChoice.add(CinemaChainHome.movieInfoListCenter.get(i).getScreen());
				}
			}
		}
	}

	public void timeMouseAction(Choice screenChoice, Choice showTimeChoice) {
		showTimeChoice.removeAll();
		for (int i = 0; i < CinemaChainHome.movieInfoListCenter.size(); i++) {
			if (screenChoice.getSelectedItem().equals(CinemaChainHome.movieInfoListCenter.get(i).getScreen())) {
				showTimeChoice.add(CinemaChainHome.movieInfoListCenter.get(i).getTime());
			}
		}
	}
}
