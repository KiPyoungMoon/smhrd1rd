package CinemaChain;

public class TicketCalDTO {
   
   int adult;
   int teenager;
   int child;
   String seat;
   String time;
   
   
   
   
   
   
   
   
   public TicketCalDTO(int adult, int teenager, int child, String seat, String time) {
      super();
      this.adult = adult;
      this.teenager = teenager;
      this.child = child;
      this.seat = seat;
      this.time = time;
   }
   
   
   public int getAdult() {
      return adult;
   }
   public int getTeenager() {
      return teenager;
   }
   public int getChild() {
      return child;
   }
   public String getSeat() {
      return seat;
   }
   public String getTime() {
      return time;
   }
   
}