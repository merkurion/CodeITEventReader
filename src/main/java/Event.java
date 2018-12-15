import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Event implements Comparable {

	private String eventName;
	private String time;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public int compareTo(Object o) {
		LocalTime eTime = LocalTime.parse(time);
		LocalTime oTime = LocalTime.parse(((Event) o).getTime());
		return (int) ChronoUnit.MINUTES.between(oTime, eTime);
	}
}
