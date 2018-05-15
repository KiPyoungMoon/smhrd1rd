package CinemaChain;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MovieInfo {
	Elements movie;

	public int getLastMovie() {
		String url = "http://movie.naver.com/movie/running/current.nhn#";
		Document lastMovieDoc;
		int lastNum = 0;

		try {
			lastMovieDoc = Jsoup.connect(url).get();
			movie = lastMovieDoc.select("dl>dt>span+a");
			// movie.get(movie.get(movie.size()-1)
			lastNum = movie.size() - 1;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(lastNum);
		return lastNum;
	}

	public ArrayList<MovieDTO> MovieInfoList(int select) {
		String url = "http://movie.naver.com/movie/running/current.nhn#";
		Document moviepageDoc;
		ArrayList<MovieDTO> movieList = new ArrayList<MovieDTO>();

		try {
			moviepageDoc = Jsoup.connect(url).get();
			movie = moviepageDoc.select("dl>dt>span+a");

			String address = movie.get(select - 1).attr("href");
			// System.out.println("MovieInfoList address = " + address);
			movieList = this.oneMovieInfo(address);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieList;
	}

	public ArrayList<MovieDTO> oneMovieInfo(String address) throws IOException {
		Document movieInfoDoc;
		ArrayList<MovieDTO> movieInfoList = new ArrayList<MovieDTO>();
		address = "http://movie.naver.com" + address;
		movieInfoDoc = Jsoup.connect(address).get();

		String posterSrc = movieInfoDoc.select(".poster>a>img").attr("src").replace("?type=m77_110_2", "");
		String title = movieInfoDoc.selectFirst("h3>a").text();
		String genre = movieInfoDoc.selectFirst(".mv_info > dl > dd:nth-child(2) > p > span:nth-child(1)").text();
		String director = movieInfoDoc.selectFirst(".step2+dd>p").text();
		String actor = movieInfoDoc.select(".step3+dd>p>a").text();
		String grade = movieInfoDoc.select(".step4+dd>p").text();
		String date = movieInfoDoc.selectFirst(".mv_info > dl > dd:nth-child(2) > p > span:nth-child(4)").text();
		int movieAge = this.getMovieAge(grade);
		String schedule = address.replace("basic", "running");

		Element summaryHeader = movieInfoDoc
				.selectFirst("div.section_group.section_group_frst > div:nth-child(1) > div > div.story_area > h5");
		Element summary = movieInfoDoc
				.selectFirst("div.section_group.section_group_frst > div:nth-child(1) > div > div.story_area > p");
		String summaryHeaderText = "";
		if (summaryHeader == null) {
			summaryHeaderText = "";
		} else {
			summaryHeaderText = summaryHeader.text();
		}
		String summaryText = summary.text();
		if (summaryText.length() > 100) {
			summaryText = summary.text().substring(0, 100);
		}
		String summaryAll = summaryHeaderText + summaryText + "..."; // summary = �ٰŸ�+"..."

		movieInfoList = this.schedule(posterSrc, title, genre, director, actor, grade, schedule, movieAge, summaryAll,
				date);
		return movieInfoList;
	}

	public static String textAlign(int width, String string) { // �ʺ�� ��Ʈ��
		return String.format("<html><div WIDTH=%d>%s</div><html>", width /* �ʺ� */, string/* ��Ʈ������ */);
	}

	private int getMovieAge(String grade) {// ��ȭ ��� �о�ͼ� ���̸� ������ִ� �޼ҵ�
		int movieAge = 0;
		grade = grade.substring(5, 7);
		if (grade.contains("��ü")) {
			movieAge = 0;
		} else if (grade.contains("û��")) {
			movieAge = 19;
		} else {
			movieAge = Integer.parseInt(grade);
		}
		return movieAge;
	}

	public ArrayList<MovieDTO> schedule(String posterSrc, String title, String genre, String director, String actor,
			String grade, String schedule, int movieAge, String summeryAll, String date) {
		// ��ȭ �������� url�� �޾ƿ����
		Document doc;
		MovieDTO movieInfo = null;
		ArrayList<MovieDTO> movieInfoList = new ArrayList<MovieDTO>();
		String time;
		try {
			doc = Jsoup.connect(schedule).get();
			Element movieTime = doc.selectFirst("#movieEndTabMenu li+li+li+li+li+li+li a");
			String timeTable = "http://movie.naver.com/movie/bi/mi" + movieTime.attr("href").substring(1);

			doc = Jsoup.connect(timeTable).get();
			Elements cinemaName = doc.select(".rsv_area strong a"); // ��ȭ�� �̸� �ܾ��

			for (int i = 0; i < cinemaName.size(); i++) {
				String theater = cinemaName.get(i).text();
				Elements cinemaRoom = doc
						.select("#runningLayer > li:nth-child(" + (i + 1) + /* ���� �� �κ� */") > div div .no_cine_info");
				// ��ȭ�� �����ִ� �󿵰����� �ܾ���µ�
				for (int j = 0; j < cinemaRoom.size(); j++) {
					String screen = cinemaRoom.get(j).text();
					Elements cinemaTime = doc
							.select("li:nth-child(" + (i + 1) + ") > div div:nth-child(" + (j + 2) + ") .mv_box a");
					// �ش� �󿵰��� �󿵽ð����� �ܾ�´�
					if (cinemaTime.size() == 0) {
						time = "����";
						movieInfo = new MovieDTO(posterSrc, title, genre, director, actor, grade, theater, screen, time,
								movieAge, summeryAll, date);
						movieInfoList.add(movieInfo);
					}
					for (int k = 0; k < cinemaTime.size(); k++) {
						time = cinemaTime.get(k).text();

						// "18:20 ����20:43"���·� ���� �� ���� ��� �ܾ ������ �� ������ �ִ� �ð��� �־��ַ���...
						time = time.substring(0, 5); // �ð��� �������
						// ���⿡ ���� �ش� ���̺� ������ ���ٸ� ������ �־��ִ� if������ ������ �ɵ�.
						movieInfo = new MovieDTO(posterSrc, title, genre, director, actor, grade, theater, screen, time,
								movieAge, summeryAll, date);
						movieInfoList.add(movieInfo);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movieInfoList;
	}
}
