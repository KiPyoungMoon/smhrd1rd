package CinemaChain;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Component;

//.setBorder(BorderFactory.createLineBorder(Color.WHITE)); //보더


// 티켓 디자인시 영화관을 넣지 않아서 일단 J라벨먼저 넣어뒀음...
public class ShowTicketInfo extends JFrame{

	private JFrame frame;
	private ImageIcon icon;
	private JScrollPane scrollPane;
//	private MovieDAO dbms = new MovieDAO();
	
	//테스트용으로 만든 객체. 나중에 지워야함
//	private TicketDTO ticketList = new TicketDTO("qnemaqnema@naver.com", "냠냠이랑 뒹구는 영화를 보면서 냠냠이와 뒹굴고 싶어","2018-01-27", "냠냠이 영화관", "컴포트 냠냠이관", "12:00", "12 13 14 ", 30000);
	
	
	/**
	 * Launch the application.
	 */
	static int width = 336;
	static int height = 600;
	public static void main(String[] args,TicketDTO showTicket) {
		ShowTicketInfo frame = new ShowTicketInfo(showTicket);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(width+17, height+37);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Create the application.
	 */
	public ShowTicketInfo(TicketDTO showTicket) {
	TicketDTO ticketList = showTicket;
//	public ShowTicketInfo() {
		
		icon = fileImgResize(width, height, "../CinemaChain/src/CinemaChain/ticketBackground.png");

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
		setContentPane(scrollPane);
		SpringLayout springLayout = new SpringLayout();
		background.setLayout(springLayout);
		
		int west = 40; // 제목이 너무 길 경우 잘라주기 위해 width계산용 west,east변수
		int east = 290;
		
		JLabel titleLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, titleLb, 120, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, titleLb, 40, SpringLayout.WEST, background);
		springLayout.putConstraint(SpringLayout.SOUTH, titleLb, 180, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.EAST, titleLb, 290, SpringLayout.WEST, background);
		titleLb.setFont(new Font("HYGothic 중간", Font.BOLD, 18));
		titleLb.setForeground(Color.WHITE);
		titleLb.setText(textAlign(east-west, ticketList.getTitle()));
		background.add(titleLb);
		
		JLabel dateLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, dateLb, 208, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, dateLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, dateLb, 0, SpringLayout.EAST, titleLb);
		dateLb.setFont(new Font("HYGothic 중간", Font.PLAIN, 19));
		dateLb.setForeground(Color.WHITE);
		dateLb.setText(ticketList.getDate());
		dateLb.setHorizontalAlignment(JLabel.RIGHT);
		dateLb.setVerticalAlignment(JLabel.CENTER);
		background.add(dateLb);
		
		JLabel theaterLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, theaterLb, 237, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, theaterLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, theaterLb, 0, SpringLayout.EAST, titleLb);
		theaterLb.setFont(new Font("HYGothic 중간", Font.BOLD, 17));
		theaterLb.setForeground(Color.WHITE);
		theaterLb.setText(ticketList.getTheater());
		theaterLb.setHorizontalAlignment(JLabel.RIGHT);
		theaterLb.setVerticalAlignment(JLabel.CENTER);
		background.add(theaterLb);
		
		JLabel screenLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, screenLb, 263, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, screenLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, screenLb, 0, SpringLayout.EAST, titleLb);
		screenLb.setFont(new Font("HYGothic 중간", Font.BOLD, 17));
		screenLb.setForeground(Color.WHITE);
		screenLb.setText(ticketList.getScreen());
		screenLb.setHorizontalAlignment(JLabel.RIGHT);
		screenLb.setVerticalAlignment(JLabel.CENTER);
		background.add(screenLb);
		
		JLabel timeLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, timeLb, 290, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, timeLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, timeLb, 0, SpringLayout.EAST, titleLb);
		timeLb.setFont(new Font("HYGothic 중간", Font.PLAIN, 19));
		timeLb.setForeground(Color.WHITE);
		timeLb.setText(ticketList.getCinemaTime());
		timeLb.setHorizontalAlignment(JLabel.RIGHT);
		timeLb.setVerticalAlignment(JLabel.CENTER);
		background.add(timeLb);
		
		JLabel seatLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, seatLb, 328, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, seatLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, seatLb, 3, SpringLayout.EAST, titleLb);
		seatLb.setFont(new Font("HYGothic 중간", Font.PLAIN, 19));
		seatLb.setForeground(Color.WHITE);
		seatLb.setText(ticketList.getSeat());
		seatLb.setHorizontalAlignment(JLabel.RIGHT);
		seatLb.setVerticalAlignment(JLabel.CENTER);
		background.add(seatLb);
		
		JLabel creditCardLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, creditCardLb, 370, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, creditCardLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, creditCardLb, 3, SpringLayout.EAST, titleLb);
		creditCardLb.setFont(new Font("HYGothic 중간", Font.BOLD, 17));
		creditCardLb.setForeground(Color.WHITE);
		creditCardLb.setText("신한 카드");
		creditCardLb.setHorizontalAlignment(JLabel.RIGHT);
		creditCardLb.setVerticalAlignment(JLabel.CENTER);
		background.add(creditCardLb);
		
		JLabel priceLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, priceLb, 408, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, priceLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, priceLb, 0, SpringLayout.EAST, titleLb);
		priceLb.setFont(new Font("HYGothic 중간", Font.PLAIN, 19));
		priceLb.setForeground(Color.WHITE);
		priceLb.setText(ticketList.getPrice()+"원");
		priceLb.setHorizontalAlignment(JLabel.RIGHT);
		priceLb.setVerticalAlignment(JLabel.CENTER);
		background.add(priceLb);
		
		JLabel vatLb = new JLabel(); // 부가세
		springLayout.putConstraint(SpringLayout.NORTH, vatLb, 435, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, vatLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, vatLb, 0, SpringLayout.EAST, titleLb);
		vatLb.setFont(new Font("HYGothic 중간", Font.PLAIN, 13));
		vatLb.setForeground(Color.WHITE);
		vatLb.setText("("+ticketList.getPrice()/10+"원)");
		vatLb.setHorizontalAlignment(JLabel.RIGHT);
		vatLb.setVerticalAlignment(JLabel.CENTER);
		background.add(vatLb);
		
		
	}

	public String textAlign(int width,String string) { //너비랑 스트링
	      
	      return String.format("<html><div WIDTH=%d Style=%s>%s</div><html>",width /*너비*/,"text-align:center",string/*스트링변수*/);
	   }
	public static ImageIcon fileImgResize(int width, int height, String url) { // gui화면에서 main밑 아무데나 넣어주면 됩니다. 이미지 주소 필요해요!
		ImageIcon poster = null;
		try {
			File imgUrl = new File(url);
			poster = new ImageIcon(ImageIO.read(imgUrl).getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return poster;
	}
}
