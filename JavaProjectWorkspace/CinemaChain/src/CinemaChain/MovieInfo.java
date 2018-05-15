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
		String summaryAll = summaryHeaderText + summaryText + "..."; // summary = 줄거리+"..."

		movieInfoList = this.schedule(posterSrc, title, genre, director, actor, grade, schedule, movieAge, summaryAll,
				date);
		return movieInfoList;
	}

	public static String textAlign(int width, String string) { // 너비랑 스트링
		return String.format("<html><div WIDTH=%d>%s</div><html>", width /* 너비 */, string/* 스트링변수 */);
	}

	private int getMovieAge(String grade) {// 영화 등급 읽어와서 나이를 계산해주는 메소드
		int movieAge = 0;
		grade = grade.substring(5, 7);
		if (grade.contains("전체")) {
			movieAge = 0;
		} else if (grade.contains("청소")) {
			movieAge = 19;
		} else {
			movieAge = Integer.parseInt(grade);
		}
		return movieAge;
	}

	public ArrayList<MovieDTO> schedule(String posterSrc, String title, String genre, String director, String actor,
			String grade, String schedule, int movieAge, String summeryAll, String date) {
		// 영화 상세페이지 url을 받아오면됨
		Document doc;
		MovieDTO movieInfo = null;
		ArrayList<MovieDTO> movieInfoList = new ArrayList<MovieDTO>();
		String time;
		try {
			doc = Jsoup.connect(schedule).get();
			Element movieTime = doc.selectFirst("#movieEndTabMenu li+li+li+li+li+li+li a");
			String timeTable = "http://movie.naver.com/movie/bi/mi" + movieTime.attr("href").substring(1);

			doc = Jsoup.connect(timeTable).get();
			Elements cinemaName = doc.select(".rsv_area strong a"); // 영화관 이름 긁어옴

			for (int i = 0; i < cinemaName.size(); i++) {
				String theater = cinemaName.get(i).text();
				Elements cinemaRoom = doc
						.select("#runningLayer > li:nth-child(" + (i + 1) + /* 숫자 들어갈 부분 */") > div div .no_cine_info");
				// 영화관 마다있는 상영관들을 긁어오는데
				for (int j = 0; j < cinemaRoom.size(); j++) {
					String screen = cinemaRoom.get(j).text();
					Elements cinemaTime = doc
							.select("li:nth-child(" + (i + 1) + ") > div div:nth-child(" + (j + 2) + ") .mv_box a");
					// 해당 상영관의 상영시간들을 긁어온다
					if (cinemaTime.size() == 0) {
						time = "종료";
						movieInfo = new MovieDTO(posterSrc, title, genre, director, actor, grade, theater, screen, time,
								movieAge, summeryAll, date);
						movieInfoList.add(movieInfo);
					}
					for (int k = 0; k < cinemaTime.size(); k++) {
						time = cinemaTime.get(k).text();

						// "18:20 종료20:43"형태로 있을 때 종료 라는 단어가 있으면 그 이전에 있던 시간만 넣어주려고...
						time = time.substring(0, 5); // 시간만 들어있음
						// 여기에 만약 해당 테이블 값들이 없다면 공백을 넣어주는 if구문을 넣으면 될듯.
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
