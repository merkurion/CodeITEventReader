import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

			ArrayList<Event> events = objectMapper.readValue(message, new TypeReference<List<Event>>() {
			});
			Collections.sort(events);

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				int currentTime = 0;

				@Override
				public void run() {
					System.out.println(currentTime);
					currentTime = currentTime + 1;
				}
			}, 0, 1000);
//			for (Event e :
//					events) {
//				System.out.println(e.getEventName() + "; " + e.getTime());
//			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
