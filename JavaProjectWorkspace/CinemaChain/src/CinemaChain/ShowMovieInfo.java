package CinemaChain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class ShowMovieInfo extends JFrame {

	private ImageIcon icon;
	private JScrollPane scrollPane;
	private ImageIcon posterIcon;
	private MovieDTO movieInfo;
	private MovieDAO dbms = new MovieDAO();

	public ShowMovieInfo() {

		icon = new ImageIcon("../CinemaChain/src/CinemaChain/showMovieInfo.png");

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
		movieInfo = CinemaChainHome.movieInfoListCenter.get(0);// 영화 정보 객체 받음.
		// movieInfo

		JButton ReservationButton = new JButton();
		sl_background.putConstraint(SpringLayout.WEST, ReservationButton, -379, SpringLayout.EAST, background);
		ReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (dbms.isLogin() != null) {
					if (dbms.getMemberAge(dbms.isLogin()) < movieInfo.getMovieAge()) {
						JOptionPane.showMessageDialog(null, movieInfo.getMovieAge() + "세 이상 관람가입니다.");
					} else {
						TimeInfo.main(null);
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "로그인을 해주세요.");

					Login.main(null);
					// 로그인 완료하면 바로 예매하기로 넘어가는거 추가하고싶다...
				}
			}
		});

		sl_background.putConstraint(SpringLayout.NORTH, ReservationButton, -115, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.SOUTH, ReservationButton, -52, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, ReservationButton, -95, SpringLayout.EAST, background);
		background.add(ReservationButton);
		setContentPane(scrollPane);
		ReservationButton.setFocusPainted(false); // 선택되었을 때 외곽선 없애기
		ReservationButton.setContentAreaFilled(false);
		ReservationButton.setOpaque(false);
		ReservationButton.setBorder(null);

		String posterUrl = CinemaChainHome.movieInfoListCenter.get(0).getPosterSrc();
		JButton poster = new JButton(CinemaChainHome.imgResize(348, 480, posterUrl));
		sl_background.putConstraint(SpringLayout.NORTH, poster, 130, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, poster, 43, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, poster, -147, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, poster, -75, SpringLayout.WEST, ReservationButton);
		poster.setBorder(null);
		poster.setFocusable(false);
		background.add(poster);

		JLabel genreLb = new JLabel();

		sl_background.putConstraint(SpringLayout.NORTH, genreLb, 158, SpringLayout.NORTH, background);
		genreLb.setForeground(Color.WHITE);

		Font f1 = new Font("나눔스퀘어라운드", Font.BOLD, 20);
		genreLb.setFont(f1);

		sl_background.putConstraint(SpringLayout.WEST, genreLb, 110, SpringLayout.EAST, poster);
		sl_background.putConstraint(SpringLayout.EAST, genreLb, -109, SpringLayout.EAST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, genreLb, 300, SpringLayout.SOUTH, background);
		genreLb.setVerticalAlignment(SwingConstants.TOP);
		background.add(genreLb);

		JLabel directorLb = new JLabel();
		directorLb.setForeground(Color.WHITE);

		Font f2 = new Font("나눔스퀘어라운드", Font.BOLD, 20);
		directorLb.setFont(f2);

		sl_background.putConstraint(SpringLayout.WEST, directorLb, 110, SpringLayout.EAST, poster);
		sl_background.putConstraint(SpringLayout.EAST, directorLb, -109, SpringLayout.EAST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, genreLb, -9, SpringLayout.NORTH, directorLb);
		sl_background.putConstraint(SpringLayout.NORTH, directorLb, 195, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.SOUTH, directorLb, -193, SpringLayout.NORTH, ReservationButton);
		directorLb.setVerticalAlignment(SwingConstants.TOP);
		background.add(directorLb);

		JLabel actorLb = new JLabel();
		actorLb.setVerticalAlignment(SwingConstants.TOP);
		actorLb.setForeground(Color.WHITE);

		Font f3 = new Font("나눔스퀘어라운드", Font.BOLD, 18);
		actorLb.setFont(f3);

		sl_background.putConstraint(SpringLayout.NORTH, actorLb, 229, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, actorLb, 110, SpringLayout.EAST, poster);
		sl_background.putConstraint(SpringLayout.SOUTH, actorLb, 52, SpringLayout.SOUTH, directorLb);
		sl_background.putConstraint(SpringLayout.EAST, actorLb, -109, SpringLayout.EAST, background);
		background.add(actorLb);

		JLabel openDateLb = new JLabel();
		openDateLb.setForeground(Color.WHITE);

		Font f4 = new Font("나눔스퀘어라운드", Font.BOLD, 19);
		openDateLb.setFont(f4);

		sl_background.putConstraint(SpringLayout.NORTH, openDateLb, 160, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, openDateLb, 132, SpringLayout.EAST, poster);
		sl_background.putConstraint(SpringLayout.SOUTH, openDateLb, 57, SpringLayout.SOUTH, actorLb);
		sl_background.putConstraint(SpringLayout.EAST, openDateLb, -109, SpringLayout.EAST, background);
		background.add(openDateLb);

		JLabel summaryLb = new JLabel();
		summaryLb.setForeground(Color.WHITE);

		Font f5 = new Font("나눔스퀘어라운드", Font.BOLD, 16);
		summaryLb.setFont(f5);

		sl_background.putConstraint(SpringLayout.NORTH, summaryLb, 379, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, summaryLb, 0, SpringLayout.WEST, openDateLb);
		background.add(summaryLb);

		JLabel gradeLb = new JLabel();
		sl_background.putConstraint(SpringLayout.NORTH, gradeLb, 123, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, gradeLb, -89, SpringLayout.WEST, genreLb);
		sl_background.putConstraint(SpringLayout.SOUTH, gradeLb, 149, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.EAST, gradeLb, 25, SpringLayout.WEST, actorLb);
		gradeLb.setForeground(Color.WHITE);
		background.add(gradeLb);

		directorLb.setText(movieInfo.getDirector());
		actorLb.setText(MovieInfo.textAlign(250, movieInfo.getActor()));
		openDateLb.setText(movieInfo.getDate());
		summaryLb.setText(MovieInfo.textAlign(250, movieInfo.getSummery()));
		genreLb.setText(movieInfo.getGenre());
		gradeLb.setText(movieInfo.getMovieAge() + "세");

	}

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ShowMovieInfo frame = new ShowMovieInfo();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(855, 780);
		frame.setVisible(true);
		frame.setResizable(false);
	}

}