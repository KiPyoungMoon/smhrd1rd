package CinemaChain;


public class TicketDTO {
	private String email;
	private String title;
	private String date;
	private String theater;
	private String screen;
	private String cinemaTime;
	private String seat;
	private int price;
	
	public TicketDTO(String email, String title,String date, String theater, String screen, String cinemaTime, String seat,
			int price) {
		super();
		this.email = email;
		this.title = title;
		this.date = date;
		this.theater = theater;
		this.screen = screen;
		this.cinemaTime = cinemaTime;
		this.seat = seat;
		this.price = price;
	}
	
	public String getDate() {
		return date;
	}
	public String getEmail() {
		return email;
	}
	public String getTitle() {
		return title;
	}
	public String getTheater() {
		return theater;
	}
	public String getScreen() {
		return screen;
	}
	public String getCinemaTime() {
		return cinemaTime;
	}
	public String getSeat() {
		return seat;
	}
	public int getPrice() {
		return price;
	}
	
	
}
