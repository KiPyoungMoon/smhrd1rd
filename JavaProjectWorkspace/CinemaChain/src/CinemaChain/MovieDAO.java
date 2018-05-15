package CinemaChain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieDAO {
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "movie";
	private String pw = "movie";

	Connection conn;
	PreparedStatement pst;

	public MovieDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);

			if (conn == null) {
				System.out.println("���� ����.");
			} else {
				System.out.println("���� ����.");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isEmail(String email) {// �̸��� ���Խ� üũ.
		if (email == null)
			return false;
		String EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		Boolean b = email.matches(EMAIL_REGEX);
		return b;
	}

	public int insertMember(String eMail, String password, String birth, String name) {
		// ȸ������
		String sql = "insert into member values(?,?,?,?)";
		int cnt = 0;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, eMail);
			pst.setString(2, password);
			pst.setString(3, birth);
			pst.setString(4, name);
			cnt = pst.executeUpdate();
			pst.close();
			// System.out.println(cnt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cnt;
	}

	public int login(String eMail, String password) { // �α���
		// login���̺� �̸��� �ְ� ����.
		// login���̺� �ش� �̸����� ������ �α��� �����̴�.
		String sql = "select * from member where eMail=?";
		int cnt = 0;
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, eMail);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString(2).equals(password)) {
					sql = "insert into login values(?)";

					pst = conn.prepareStatement(sql);
					pst.setString(1, eMail);
					cnt = pst.executeUpdate();
				}
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cnt;
	}

	public int logout() {// �α׾ƿ�. ������ �Ѹ���̴� ���̺� ��ü �����ϰ� ����.
		// ���Ḧ �����ų�, ���α׷� ���� xǥ�� ������ �� �޼ҵ带 �����ϰ� �����Ѵ�.
		String sql = "delete from login";
		int cnt = 0;
		try {
			pst = conn.prepareStatement(sql);
			cnt = pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cnt;
	}

	public MemberDTO infoMember() {
		// �α��� ���¿����� ���� �˻��� �����ϴ�.
		String sql = "select birth, name from member where email=?";
		String eMail = this.isLogin();
		if (eMail != null) {
			try {
				pst = conn.prepareStatement(sql);
				pst.setString(1, eMail);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
					String birth = rs.getString(1);
					String name = rs.getString(2);
					MemberDTO member = new MemberDTO(eMail, birth, name);
					return member;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public String isLogin() {// �α��� ���̺� �˻��ؼ� �̸����� ��ȯ���ش�.
		String sql = "select * from login";
		String eMail = null;
		try {
			pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				eMail = rs.getString(1);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("email = " + eMail);
		return eMail;
	}

	public int getMemberAge(String eMail) {
		String sql = "select birth from member where eMail=?";
		int age = 0;

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, eMail);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String date = rs.getString(1);
				date = date.substring(0, 4);
				int birthYear = Integer.parseInt(date);
				// System.out.println(birthYear);
				int currentYear = currentYear();
				age = currentYear - birthYear;
				System.out.println("���� : " + age);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return age;
	}

	private int currentYear() {
		long time = System.currentTimeMillis();// ���� �ð� ���ϱ�
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String str = dayTime.format(new Date(time));
		str = str.substring(0, 4);
		int currentYear = Integer.parseInt(str);
		return currentYear;
	}

	public int ticket_payment(String email, String title, String date, String theater, String screen, String cinemaTime,
			String sheet, int price) {
		// �¼� ���� �� �����ϰ��� db�� ���� �� ����
		String url = "insert into ticket values(?,?,?,?,?,?,?,?)";
		int cnt = 0;
		try {
			pst = conn.prepareStatement(url);
			pst.setString(1, email);
			pst.setString(2, title);
			pst.setString(3, date);
			pst.setString(4, theater);
			pst.setString(5, screen);
			pst.setString(6, cinemaTime);
			pst.setString(7, sheet);
			pst.setInt(8, price);
			cnt = pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cnt;
	}

	public ArrayList<TicketDTO> getTicket(String email) {
		// ���� ���������� Ƽ�� Ȯ�νÿ� ����
		ArrayList<TicketDTO> list = new ArrayList<>();
		String url = "select * from ticket where email=?";

		try {
			pst = conn.prepareStatement(url);
			pst.setString(1, email);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				list.add(new TicketDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getSeat(String title, String date, String theater, String screen, String time) {
		// �¼� ����â���� �̹� ���ŵ� �ڸ� ���� ���ϰ� �� �� ����
		ArrayList<String> list = new ArrayList<>();
		String url = "select seat from ticket where title = ? and ticketDate = ? and theater = ? and screen = ? and cinemaTime = ?";

		try {
			pst = conn.prepareStatement(url);
			pst.setString(1, title);
			pst.setString(2, date);
			pst.setString(3, theater);
			pst.setString(4, screen);
			pst.setString(5, time);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int clearTicket(String title, String date, String theater, String screen, String time, String seat) {
		String url = "delete from ticket where title = ? and ticketDate = ? and theater = ? and screen = ? and cinemaTime = ? and seat = ?";
		int cnt = 0;
		try {
			pst = conn.prepareStatement(url);
			pst.setString(1, title);
			pst.setString(2, date);
			pst.setString(3, theater);
			pst.setString(4, screen);
			pst.setString(5, time);
			pst.setString(6, seat);
			cnt = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cnt;
	}

	public boolean isDuplicate(String eMail) {
		String url = "select eMail from member";

		try {
			pst = conn.prepareStatement(url);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).equals(eMail)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
