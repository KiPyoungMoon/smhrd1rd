package CinemaChain;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class CinemaChainHome extends JFrame {
	JScrollPane scrollPane;
	ImageIcon icon;
	ImageIcon buttonIcon;
	String btnUrl;
	static JButton loginButton;
	static ArrayList<MovieDTO> movieInfoListCenter;
	ArrayList<MovieDTO> movieInfoListLeft;
	ArrayList<MovieDTO> movieInfoListRight;
	MovieDAO dbms = new MovieDAO();

	String centerPosterUrl;
	String leftPosterUrl;
	String rightPosterUrl;
	int centerNum = 0;
	int rightNum = 0;
	int leftNum = 0;

	public CinemaChainHome() {

		MovieInfo movieInfo = new MovieInfo();
		dbms.logout();
		centerNum = 1;
		rightNum = centerNum + 1;
		leftNum = 20;

		System.out.println("left N : " + leftNum + " cntN : " + centerNum + " right N : " + rightNum);

		movieInfoListCenter = movieInfo.MovieInfoList(centerNum);
		movieInfoListLeft = movieInfo.MovieInfoList(leftNum);
		movieInfoListRight = movieInfo.MovieInfoList(rightNum);
		centerPosterUrl = movieInfoListCenter.get(0).getPosterSrc();
		leftPosterUrl = movieInfoListLeft.get(0).getPosterSrc();
		rightPosterUrl = movieInfoListRight.get(0).getPosterSrc();

		System.out.println(leftPosterUrl);
		System.out.println(centerPosterUrl);
		System.out.println(rightPosterUrl);

		icon = new ImageIcon("../CinemaChain/src/CinemaChain/HomeBackground.png");

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

		JButton centerButton = new JButton(imgResize(412, 600, centerPosterUrl));
		sl_background.putConstraint(SpringLayout.NORTH, centerButton, 107, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.SOUTH, centerButton, -10, SpringLayout.SOUTH, background);

		centerButton.setFocusPainted(false); // 선택되었을 때 외곽선 없애기
		centerButton.setContentAreaFilled(false);
		centerButton.setOpaque(false); // 투명하게
		background.add(centerButton);

		scrollPane = new JScrollPane(background);

		JButton leftButton = new JButton(imgResize(206 * 2, 500, leftPosterUrl));
		sl_background.putConstraint(SpringLayout.WEST, centerButton, 6, SpringLayout.EAST, leftButton);

		sl_background.putConstraint(SpringLayout.EAST, leftButton, -617, SpringLayout.EAST, background);
		sl_background.putConstraint(SpringLayout.NORTH, leftButton, 146, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, leftButton, 0, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, leftButton, -70, SpringLayout.SOUTH, background);
		leftButton.setFocusPainted(false); // 선택되었을 때 외곽선 없애기
		leftButton.setContentAreaFilled(false);
		leftButton.setOpaque(false); // 투명하게
		background.add(leftButton);

		JButton rightButton = new JButton(imgResize(206 * 2, 500, rightPosterUrl));
		sl_background.putConstraint(SpringLayout.EAST, centerButton, -6, SpringLayout.WEST, rightButton);
		sl_background.putConstraint(SpringLayout.NORTH, rightButton, 0, SpringLayout.NORTH, leftButton);
		sl_background.putConstraint(SpringLayout.WEST, rightButton, 639, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, rightButton, -70, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, rightButton, 0, SpringLayout.EAST, background);
		rightButton.setFocusPainted(false); // 선택되었을 때 외곽선 없애기
		rightButton.setContentAreaFilled(false);
		rightButton.setOpaque(false); // 투명하게
		background.add(rightButton);

		btnUrl = "../CinemaChain/src/CinemaChain/loginBtn.png";
		buttonIcon = new ImageIcon(btnUrl);
		loginButton = new JButton(buttonIcon);
		loginButton.setFocusPainted(false); // 선택되었을 때 외곽선 없애기
		loginButton.setContentAreaFilled(false);
		loginButton.setBorder(null);
		loginButton.setOpaque(false); // 투명하게
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String eMail = dbms.isLogin();
				System.out.println(eMail);
				if (eMail == null) {
					Login.main(null);
				} else {
					MyPage.main(null);
				}
			}
		});
		sl_background.putConstraint(SpringLayout.NORTH, loginButton, 10, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, loginButton, -109, SpringLayout.EAST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, loginButton, 62, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.EAST, loginButton, -10, SpringLayout.EAST, background);
		background.add(loginButton);

		setContentPane(scrollPane);

		centerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShowMovieInfo.main(null);
			}
		});

		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (leftNum == 1) {
					rightNum = centerNum;
					centerNum = leftNum;
					leftNum = 20;

				} else {
					rightNum = centerNum;
					centerNum = leftNum;
					leftNum--;
				}
				System.out.println("left N : " + leftNum + " cntN : " + centerNum + " right N : " + rightNum);
				movieInfoListCenter = movieInfo.MovieInfoList(centerNum);
				movieInfoListLeft = movieInfo.MovieInfoList(leftNum);
				movieInfoListRight = movieInfo.MovieInfoList(rightNum);
				centerPosterUrl = movieInfoListCenter.get(0).getPosterSrc();
				leftPosterUrl = movieInfoListLeft.get(0).getPosterSrc();
				rightPosterUrl = movieInfoListRight.get(0).getPosterSrc();
				System.out.println(leftPosterUrl);
				System.out.println(centerPosterUrl);
				System.out.println(rightPosterUrl);
				centerButton.setIcon(imgResize(412, 600, centerPosterUrl));
				leftButton.setIcon(imgResize(206 * 2, 500, leftPosterUrl));
				rightButton.setIcon(imgResize(206 * 2, 500, rightPosterUrl));
			}
		});

		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rightNum == 20) {
					centerNum = rightNum;
					leftNum = centerNum - 1;
					rightNum = 1;
				} else {
					leftNum = centerNum;
					centerNum = rightNum;
					rightNum++;
				}
				System.out.println("left N : " + leftNum + " cntN : " + centerNum + " right N : " + rightNum);
				movieInfoListCenter = movieInfo.MovieInfoList(centerNum);
				movieInfoListLeft = movieInfo.MovieInfoList(leftNum);
				movieInfoListRight = movieInfo.MovieInfoList(rightNum);
				centerPosterUrl = movieInfoListCenter.get(0).getPosterSrc();
				leftPosterUrl = movieInfoListLeft.get(0).getPosterSrc();
				rightPosterUrl = movieInfoListRight.get(0).getPosterSrc();
				System.out.println(leftPosterUrl);
				System.out.println(centerPosterUrl);
				System.out.println(rightPosterUrl);
				centerButton.setIcon(imgResize(412, 600, centerPosterUrl));
				leftButton.setIcon(imgResize(206 * 2, 500, leftPosterUrl));
				rightButton.setIcon(imgResize(206 * 2, 500, rightPosterUrl));
			}
		});
	}

	public static ImageIcon imgResize(int width, int height, String url) { // gui화면에서 main밑 아무데나 넣어주면 됩니다. 이미지 주소 필요해요!
		ImageIcon poster = null;
		try {
			URL imgUrl = new URL(url);
			poster = new ImageIcon(ImageIO.read(imgUrl).getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return poster;
	}

	public static void main(String[] args) {
		CinemaChainHome frame = new CinemaChainHome();

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(850, 750);
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
