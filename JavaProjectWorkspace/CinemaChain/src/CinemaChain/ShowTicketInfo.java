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

//.setBorder(BorderFactory.createLineBorder(Color.WHITE)); //����


// Ƽ�� �����ν� ��ȭ���� ���� �ʾƼ� �ϴ� J�󺧸��� �־����...
public class ShowTicketInfo extends JFrame{

	private JFrame frame;
	private ImageIcon icon;
	private JScrollPane scrollPane;
//	private MovieDAO dbms = new MovieDAO();
	
	//�׽�Ʈ������ ���� ��ü. ���߿� ��������
//	private TicketDTO ticketList = new TicketDTO("qnemaqnema@naver.com", "�ȳ��̶� �߱��� ��ȭ�� ���鼭 �ȳ��̿� �߱��� �;�","2018-01-27", "�ȳ��� ��ȭ��", "����Ʈ �ȳ��̰�", "12:00", "12 13 14 ", 30000);
	
	
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

		// ��� Panel ������ �������������� ����
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
				setOpaque(false); // �׸��� ǥ���ϰ� ����,�����ϰ� ����
				super.paintComponent(g);
			}
		};

		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
		SpringLayout springLayout = new SpringLayout();
		background.setLayout(springLayout);
		
		int west = 40; // ������ �ʹ� �� ��� �߶��ֱ� ���� width���� west,east����
		int east = 290;
		
		JLabel titleLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, titleLb, 120, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, titleLb, 40, SpringLayout.WEST, background);
		springLayout.putConstraint(SpringLayout.SOUTH, titleLb, 180, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.EAST, titleLb, 290, SpringLayout.WEST, background);
		titleLb.setFont(new Font("HYGothic �߰�", Font.BOLD, 18));
		titleLb.setForeground(Color.WHITE);
		titleLb.setText(textAlign(east-west, ticketList.getTitle()));
		background.add(titleLb);
		
		JLabel dateLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, dateLb, 208, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, dateLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, dateLb, 0, SpringLayout.EAST, titleLb);
		dateLb.setFont(new Font("HYGothic �߰�", Font.PLAIN, 19));
		dateLb.setForeground(Color.WHITE);
		dateLb.setText(ticketList.getDate());
		dateLb.setHorizontalAlignment(JLabel.RIGHT);
		dateLb.setVerticalAlignment(JLabel.CENTER);
		background.add(dateLb);
		
		JLabel theaterLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, theaterLb, 237, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, theaterLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, theaterLb, 0, SpringLayout.EAST, titleLb);
		theaterLb.setFont(new Font("HYGothic �߰�", Font.BOLD, 17));
		theaterLb.setForeground(Color.WHITE);
		theaterLb.setText(ticketList.getTheater());
		theaterLb.setHorizontalAlignment(JLabel.RIGHT);
		theaterLb.setVerticalAlignment(JLabel.CENTER);
		background.add(theaterLb);
		
		JLabel screenLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, screenLb, 263, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, screenLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, screenLb, 0, SpringLayout.EAST, titleLb);
		screenLb.setFont(new Font("HYGothic �߰�", Font.BOLD, 17));
		screenLb.setForeground(Color.WHITE);
		screenLb.setText(ticketList.getScreen());
		screenLb.setHorizontalAlignment(JLabel.RIGHT);
		screenLb.setVerticalAlignment(JLabel.CENTER);
		background.add(screenLb);
		
		JLabel timeLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, timeLb, 290, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, timeLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, timeLb, 0, SpringLayout.EAST, titleLb);
		timeLb.setFont(new Font("HYGothic �߰�", Font.PLAIN, 19));
		timeLb.setForeground(Color.WHITE);
		timeLb.setText(ticketList.getCinemaTime());
		timeLb.setHorizontalAlignment(JLabel.RIGHT);
		timeLb.setVerticalAlignment(JLabel.CENTER);
		background.add(timeLb);
		
		JLabel seatLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, seatLb, 328, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, seatLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, seatLb, 3, SpringLayout.EAST, titleLb);
		seatLb.setFont(new Font("HYGothic �߰�", Font.PLAIN, 19));
		seatLb.setForeground(Color.WHITE);
		seatLb.setText(ticketList.getSeat());
		seatLb.setHorizontalAlignment(JLabel.RIGHT);
		seatLb.setVerticalAlignment(JLabel.CENTER);
		background.add(seatLb);
		
		JLabel creditCardLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, creditCardLb, 370, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, creditCardLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, creditCardLb, 3, SpringLayout.EAST, titleLb);
		creditCardLb.setFont(new Font("HYGothic �߰�", Font.BOLD, 17));
		creditCardLb.setForeground(Color.WHITE);
		creditCardLb.setText("���� ī��");
		creditCardLb.setHorizontalAlignment(JLabel.RIGHT);
		creditCardLb.setVerticalAlignment(JLabel.CENTER);
		background.add(creditCardLb);
		
		JLabel priceLb = new JLabel();
		springLayout.putConstraint(SpringLayout.NORTH, priceLb, 408, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, priceLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, priceLb, 0, SpringLayout.EAST, titleLb);
		priceLb.setFont(new Font("HYGothic �߰�", Font.PLAIN, 19));
		priceLb.setForeground(Color.WHITE);
		priceLb.setText(ticketList.getPrice()+"��");
		priceLb.setHorizontalAlignment(JLabel.RIGHT);
		priceLb.setVerticalAlignment(JLabel.CENTER);
		background.add(priceLb);
		
		JLabel vatLb = new JLabel(); // �ΰ���
		springLayout.putConstraint(SpringLayout.NORTH, vatLb, 435, SpringLayout.NORTH, background);
		springLayout.putConstraint(SpringLayout.WEST, vatLb, 0, SpringLayout.WEST, titleLb);
		springLayout.putConstraint(SpringLayout.EAST, vatLb, 0, SpringLayout.EAST, titleLb);
		vatLb.setFont(new Font("HYGothic �߰�", Font.PLAIN, 13));
		vatLb.setForeground(Color.WHITE);
		vatLb.setText("("+ticketList.getPrice()/10+"��)");
		vatLb.setHorizontalAlignment(JLabel.RIGHT);
		vatLb.setVerticalAlignment(JLabel.CENTER);
		background.add(vatLb);
		
		
	}

	public String textAlign(int width,String string) { //�ʺ�� ��Ʈ��
	      
	      return String.format("<html><div WIDTH=%d Style=%s>%s</div><html>",width /*�ʺ�*/,"text-align:center",string/*��Ʈ������*/);
	   }
	public static ImageIcon fileImgResize(int width, int height, String url) { // guiȭ�鿡�� main�� �ƹ����� �־��ָ� �˴ϴ�. �̹��� �ּ� �ʿ��ؿ�!
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
