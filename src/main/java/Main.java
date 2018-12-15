import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static void main(String[] args) {
		try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("c:\\events.json"), Charset.defaultCharset())) {
			String message = IOUtils.toString(bufferedReader);
			ObjectMapper objectMapper = new ObjectMapper();

			ArrayList<Event> eventsList = objectMapper.readValue(message, new TypeReference<List<Event>>() {
			});

			Collections.sort(eventsList);
			Iterator eventIterator = eventsList.iterator();

			runTimer(eventIterator);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void runTimer(Iterator eventIterator) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			Event currentEvent = (Event) eventIterator.next();
			int currentTime = 0;

			@Override
			public void run() {
				currentTime = currentTime + 1;
				currentEvent = checkEvent(currentEvent, currentTime, eventIterator);
				if (currentEvent == null) {
					timer.cancel();
					timer.purge();
				}
			}
		}, 0, 1000);
	}

	private static Event checkEvent(Event currentEvent, int currentTime, Iterator eventIterator) {
		if (currentEvent.getLocalTimeInSeconds() == currentTime) {
			System.out.println(currentEvent.getEventName());
			if (eventIterator.hasNext()) {
				return checkEvent((Event) eventIterator.next(), currentTime, eventIterator);
			} else {
				return null;
			}
		} else {
			return currentEvent;
		}
	}
}
