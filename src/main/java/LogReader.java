import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogReader {

	private static void runTimer(TreeMap<Integer, List<Event>> eventsMap) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int currentTime = 0;

			@Override
			public void run() {
				currentTime = currentTime + 1;
				if (eventsMap.containsKey(currentTime)) {
					for (Event event : eventsMap.get(currentTime)) {
						System.out.println(event.getLocalTime() + " : " + event.getEventName());
					}
					if (eventsMap.lastKey() == currentTime) {
						timer.cancel();
						timer.purge();
					}
				}
			}
		}, 0, 1000);
	}

	public void readJSON(String resource) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Event> eventsList = null;
		try {
			eventsList = objectMapper.readValue(this.getClass().getResource(resource), new TypeReference<List<Event>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (eventsList != null) {
			TreeMap<Integer, List<Event>> eventsMap = new TreeMap<>();
			for (Event e : eventsList) {
				int currentSeconds = e.getLocalTimeInSeconds();

				if (eventsMap.containsKey(currentSeconds)) {
					eventsMap.get(currentSeconds).add(e);
				} else {
					eventsMap.put(currentSeconds, new ArrayList<Event>() {{
						add(e);
					}});
				}
			}
			runTimer(eventsMap);
		}
	}
}
