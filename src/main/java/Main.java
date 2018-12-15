import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("c:\\events.json"), Charset.defaultCharset())) {
			String message = IOUtils.toString(bufferedReader);
			ObjectMapper objectMapper = new ObjectMapper();

			List<Event> eventsList = objectMapper.readValue(message, new TypeReference<List<Event>>() {
			});

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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void runTimer(TreeMap<Integer, List<Event>> eventsMap) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			int currentTime = 0;

			@Override
			public void run() {
				currentTime = currentTime + 1;
				if (eventsMap.containsKey(currentTime)) {
					for (Event event : eventsMap.get(currentTime)) {
						System.out.println(event.getEventName());
					}
					if (eventsMap.lastKey() == currentTime) {
						timer.cancel();
						timer.purge();
					}
				}
			}
		}, 0, 1000);
	}
}
