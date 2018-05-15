package CinemaChain;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		boolean endChk = true;
		MovieDAO dbms = new MovieDAO();

		while (endChk) {
			System.out.print("[1]ȸ������ [2]�α��� [3]�α׾ƿ� [4]���� [5]ȸ������ �˻� [6]��ȭ ���� [7]Ƽ�� ���� [8]��������? >> ");
			int select = sc.nextInt();
			sc.nextLine();
			switch (select) {
			case 1:
				System.out.print("�̸����� �Է��ϼ���. >> ");
				String eMail = sc.nextLine();
				// System.out.println(dbms.isEmail(eMail));
				if (dbms.isEmail(eMail)) {
					System.out.print("��й�ȣ�� �Է��ϼ���. >> ");
					String password = sc.nextLine();
					System.out.print("�ѹ� �� �Է����ּ���. >> ");
					String passChk = sc.nextLine();
					// System.out.println(password.length());
					if (password.equals(passChk) && password.length() >= 8 && password.length() <= 15) {
						System.out.print("��������� �Է����ּ���. >> ");
						String birth = sc.nextLine();
						System.out.print("�̸��� �Է����ּ���. >> ");
						String name = sc.nextLine();
						int cnt = dbms.insertMember(eMail, password, birth, name);
						if (cnt > 0) {
							System.out.println("���� ����");
						} else {
							System.out.println("���� ����.");
						}
					}
				}
				break;
			case 2:// �α���
				System.out.print("eMail�� �Է��ϼ���. >> ");
				eMail = sc.nextLine();
				System.out.print("��й�ȣ�� �Է��ϼ���. >> ");
				String Password = sc.nextLine();
				int cnt = dbms.login(eMail, Password);
				if (cnt > 0) {
					System.out.println("�α��� ����");
				} else {
					System.out.println("�α��� ����.");
				}
				break;
			case 3:// �α׾ƿ�
				cnt = dbms.logout();
				if (cnt > 0) {
					System.out.println("�α׾ƿ� ����");
				} else {
					System.out.println("�α׾ƿ� ����.");
				}
				break;
			case 4:// ����
				dbms.logout();// ���� ������ �α׾ƿ� �Ŀ� �����Ѵ�.
				endChk = false;
				sc.close();
				break;
			case 5:// ȸ������ ��ȸ
				MemberDTO member = dbms.infoMember();
				if (member != null) {
					System.out.println("���̵� : " + member.geteMail());
					System.out.println("���� : " + member.getBirth());
					System.out.println("�̸� : " + member.getName());
				} else {
					System.out.println("�α����� ���ּ���.");
				}
				break;
			case 6:// ��ȭ ����
				MovieInfo movieInfo = new MovieInfo();
				System.out.print("1~10 ��ȣ�� ������. >> ");
				select = sc.nextInt();
				ArrayList<MovieDTO> movieInfoList = movieInfo.MovieInfoList(select);

				String title = movieInfoList.get(0).getTitle();
				String poster = movieInfoList.get(0).getPosterSrc();
				String genre = movieInfoList.get(0).getGenre();
				String director = movieInfoList.get(0).getDirector();
				String actor = movieInfoList.get(0).getActor();
				String grade = movieInfoList.get(0).getGrade();

				System.out.println("================================");
				System.out.println(select + "��");
				System.out.println("������ : " + poster);
				System.out.println("����: " + title);
				System.out.println("����: " + genre);
				System.out.println("����: " + director);
				System.out.println("���: " + actor);
				System.out.println("���: " + grade);
				for (int i = 0; i < movieInfoList.size(); i++) {
					String theater = movieInfoList.get(i).getTheater();
					String screen = movieInfoList.get(i).getScreen();
					String time = movieInfoList.get(i).getTime();
					if (i == 0) {
						System.out.println("����� : " + theater);
						System.out.println("�󿵰� : " + screen);
					} else if (!theater.equals(movieInfoList.get(i - 1).getTheater())) {
						System.out.println("����� : " + theater);
					} else {
						if (!screen.equals(movieInfoList.get(i - 1).getScreen())) {
							System.out.println("�󿵰� : " + screen);
						}
					}
					System.out.println("�󿵽ð� : " + time);
				}

				System.out.println("================================");
				System.out.println();

				break;
			case 7:// Ƽ�� ����

				break;
			case 8:// ��������?
				dbms.getMemberAge(dbms.isLogin());
				break;

			default:
				System.out.println("�ٽ� �Է��ϼ���.");
				break;
			}
		}
	}
}
