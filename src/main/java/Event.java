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

	public LocalTime getLocalTime() {
		return LocalTime.parse(time);
	}

	public int getLocalTimeInSeconds() {
		return getLocalTime().getHour() * 60 + getLocalTime().getMinute();
	}

	@Override
	public int compareTo(Object o) {
		return (int) ChronoUnit.MINUTES.between(((Event) o).getLocalTime(), getLocalTime());
	}
}
