package CinemaChain;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		boolean endChk = true;
		MovieDAO dbms = new MovieDAO();

		while (endChk) {
			System.out.print("[1]회원가입 [2]로그인 [3]로그아웃 [4]종료 [5]회원정보 검색 [6]영화 정보 [7]티켓 정보 [8]관람가능? >> ");
			int select = sc.nextInt();
			sc.nextLine();
			switch (select) {
			case 1:
				System.out.print("이메일을 입력하세요. >> ");
				String eMail = sc.nextLine();
				// System.out.println(dbms.isEmail(eMail));
				if (dbms.isEmail(eMail)) {
					System.out.print("비밀번호를 입력하세요. >> ");
					String password = sc.nextLine();
					System.out.print("한번 더 입력해주세요. >> ");
					String passChk = sc.nextLine();
					// System.out.println(password.length());
					if (password.equals(passChk) && password.length() >= 8 && password.length() <= 15) {
						System.out.print("생년월일을 입력해주세요. >> ");
						String birth = sc.nextLine();
						System.out.print("이름을 입력해주세요. >> ");
						String name = sc.nextLine();
						int cnt = dbms.insertMember(eMail, password, birth, name);
						if (cnt > 0) {
							System.out.println("가입 성공");
						} else {
							System.out.println("가입 실패.");
						}
					}
				}
				break;
			case 2:// 로그인
				System.out.print("eMail을 입력하세요. >> ");
				eMail = sc.nextLine();
				System.out.print("비밀번호를 입력하세요. >> ");
				String Password = sc.nextLine();
				int cnt = dbms.login(eMail, Password);
				if (cnt > 0) {
					System.out.println("로그인 성공");
				} else {
					System.out.println("로그인 실패.");
				}
				break;
			case 3:// 로그아웃
				cnt = dbms.logout();
				if (cnt > 0) {
					System.out.println("로그아웃 성공");
				} else {
					System.out.println("로그아웃 실패.");
				}
				break;
			case 4:// 종료
				dbms.logout();// 종료 누르면 로그아웃 후에 종료한다.
				endChk = false;
				sc.close();
				break;
			case 5:// 회원정보 조회
				MemberDTO member = dbms.infoMember();
				if (member != null) {
					System.out.println("아이디 : " + member.geteMail());
					System.out.println("생일 : " + member.getBirth());
					System.out.println("이름 : " + member.getName());
				} else {
					System.out.println("로그인을 해주세요.");
				}
				break;
			case 6:// 영화 정보
				MovieInfo movieInfo = new MovieInfo();
				System.out.print("1~10 번호를 고르세요. >> ");
				select = sc.nextInt();
				ArrayList<MovieDTO> movieInfoList = movieInfo.MovieInfoList(select);

				String title = movieInfoList.get(0).getTitle();
				String poster = movieInfoList.get(0).getPosterSrc();
				String genre = movieInfoList.get(0).getGenre();
				String director = movieInfoList.get(0).getDirector();
				String actor = movieInfoList.get(0).getActor();
				String grade = movieInfoList.get(0).getGrade();

				System.out.println("================================");
				System.out.println(select + "번");
				System.out.println("포스터 : " + poster);
				System.out.println("제목: " + title);
				System.out.println("개요: " + genre);
				System.out.println("감독: " + director);
				System.out.println("배우: " + actor);
				System.out.println("등급: " + grade);
				for (int i = 0; i < movieInfoList.size(); i++) {
					String theater = movieInfoList.get(i).getTheater();
					String screen = movieInfoList.get(i).getScreen();
					String time = movieInfoList.get(i).getTime();
					if (i == 0) {
						System.out.println("극장명 : " + theater);
						System.out.println("상영관 : " + screen);
					} else if (!theater.equals(movieInfoList.get(i - 1).getTheater())) {
						System.out.println("극장명 : " + theater);
					} else {
						if (!screen.equals(movieInfoList.get(i - 1).getScreen())) {
							System.out.println("상영관 : " + screen);
						}
					}
					System.out.println("상영시간 : " + time);
				}

				System.out.println("================================");
				System.out.println();

				break;
			case 7:// 티켓 정보

				break;
			case 8:// 관람가능?
				dbms.getMemberAge(dbms.isLogin());
				break;

			default:
				System.out.println("다시 입력하세요.");
				break;
			}
		}
	}
}
