package HipChartV2.HipChartV2;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.hipchart.v2.model.Rooms;

public class HipchatUtils {
	// ENVIRONMENT DEPENDENT
	private static String TEST_ROOM_NAME = "test";
	private static List<Document> messages = new ArrayList<>();
	public static String host = "";

	public static void sendMessageRoom(String room, String message, String color) {
		try {
			boolean isNewMsg = true;
			String lastMessage = "";
			messages = Rooms.getRoomMessages(TEST_ROOM_NAME);
			if (messages != null) {
				for (Document historyMessage : messages) {
					lastMessage = historyMessage.getString("message");
					if (lastMessage.equalsIgnoreCase(message)) {
						isNewMsg = false;
					}
				}
			}
			if (isNewMsg == true) {
				if (room.isEmpty()) {
					room = TEST_ROOM_NAME;
				}
				Document doc = Rooms.getRoom(room);
				Rooms.sendRoomNotification(doc.getInteger("id"), room, color, true, message);
				Thread.sleep(2000);
			}
		} catch (Exception e) {

		}
	}

}
