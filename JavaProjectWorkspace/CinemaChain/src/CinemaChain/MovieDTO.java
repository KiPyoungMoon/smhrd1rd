package CinemaChain;

public class MovieDTO {
	private String posterSrc;
	private String title;
	private String genre;
	private String director;
	private String actor;
	private String grade;
	private String theater;
	private String screen;
	private String time;
	private int movieAge;
	private String summery;
	private String date;

	public MovieDTO(String posterSrc, String title, String genre, String director, String actor, String grade,
			String theater, String screen, String time, int movieAge, String summery, String date) {
		this.posterSrc = posterSrc;
		this.title = title;
		this.genre = genre;
		this.director = director;
		this.actor = actor;
		this.grade = grade;
		this.theater = theater;
		this.screen = screen;
		this.time = time;
		this.movieAge = movieAge;
		this.summery = summery;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public int getMovieAge() {
		return movieAge;
	}

	public String getSummery() {
		return summery;
	}

	public String getPosterSrc() {
		return posterSrc;
	}

	public String getTitle() {
		return title;
	}

	public String getGenre() {
		return genre;
	}

	public String getDirector() {
		return director;
	}

	public String getActor() {
		return actor;
	}

	public String getGrade() {
		return grade;
	}

	public String getTheater() {
		return theater;
	}

	public String getScreen() {
		return screen;
	}

	public String getTime() {
		return time;
	}
}
