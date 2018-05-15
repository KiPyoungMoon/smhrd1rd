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
				System.out.println("연결 실패.");
			} else {
				System.out.println("연결 성공.");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isEmail(String email) {// 이메일 정규식 체크.
		if (email == null)
			return false;
		String EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		Boolean b = email.matches(EMAIL_REGEX);
		return b;
	}

	public int insertMember(String eMail, String password, String birth, String name) {
		// 회원가입
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

	public int login(String eMail, String password) { // 로그인
		// login테이블에 이메일 넣고 종료.
		// login테이블에 해당 이메일이 있으면 로그인 상태이다.
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

	public int logout() {// 로그아웃. 어차피 한명뿐이니 테이블 전체 삭제하고 종료.
		// 종료를 누르거나, 프로그램 종료 x표를 누르면 이 메소드를 실행하고 종료한다.
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
		// 로그인 상태에서만 정보 검색이 가능하다.
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

	public String isLogin() {// 로그인 테이블 검색해서 이메일을 반환해준다.
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
				System.out.println("나이 : " + age);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return age;
	}

	private int currentYear() {
		long time = System.currentTimeMillis();// 현재 시간 구하기
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String str = dayTime.format(new Date(time));
		str = str.substring(0, 4);
		int currentYear = Integer.parseInt(str);
		return currentYear;
	}

	public int ticket_payment(String email, String title, String date, String theater, String screen, String cinemaTime,
			String sheet, int price) {
		// 좌석 선택 후 결제하고나서 db에 넣을 때 쓰임
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
		// 마이 페이지에서 티켓 확인시에 쓰임
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
		// 좌석 선택창에서 이미 예매된 자리 선택 못하게 할 때 쓰임
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
