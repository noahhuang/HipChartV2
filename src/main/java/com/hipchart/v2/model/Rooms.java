package com.hipchart.v2.model;

import java.util.List;

import org.bson.Document;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Rooms extends HipChatClient {

	private static List<Document> rooms;

	private static Document apiGetAllRooms() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(baseUrl + "/room").header("accept", "application/json").queryString("auth_token", auth_token).asJson();
			return Document.parse(jsonResponse.getBody().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Document();
	}

	private static Document apiGetRecentRoomHistory(int id) {
		// https://api.hipchat.com/v2/room/2004975/history?auth_token=6ycaB6EUg46BY1q8LIP3Yqj76Fvisq9DHjRKTCju
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(baseUrl + "/room/latest" + id + "/history").header("accept", "application/json").queryString("auth_token", auth_token)
					.asJson();
			return Document.parse(jsonResponse.getBody().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Document();
	}

	private static Document apiGetRoomMembers(int id) {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(baseUrl + "/room/" + id + "/member").header("accept", "application/json").queryString("auth_token", auth_token)
					.asJson();
			return Document.parse(jsonResponse.getBody().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Document();
	}
	
	public static void sendRoomNotification(int id, String from, String color, Boolean userNotification, String message) {
		// https://api.hipchat.com/v2/room/2004975/history?auth_token=6ycaB6EUg46BY1q8LIP3Yqj76Fvisq9DHjRKTCju
		try {
			Document msg = new Document();
			msg.append("from", from);
			msg.append("message", message);
			msg.append("color", color);
			msg.append("notify", userNotification);
			Unirest.post(baseUrl + "/room/" + id + "/notification").header("Content-Type", "application/json").queryString("auth_token", auth_token).body(msg.toJson()).asJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Document> getRooms() {
		Document doc = Rooms.apiGetAllRooms();
		List<Document> items = (List<Document>) doc.get("items");
		rooms = items;
		return items;
	}

	public static Document getRoom(String name) {
		// new request
		if (rooms == null) {
			Rooms.getRooms();
		}
		// get from exist room
		for (Document document : rooms) {
			if (document.getString("name").equalsIgnoreCase(name))
				return document;
		}
		// query again if it's not existed
		Rooms.getRooms();

		for (Document document : rooms) {
			if (document.getString("name").equalsIgnoreCase(name))
				return document;
		}

		return new Document();
	}

	@SuppressWarnings("unchecked")
	public static List<Document> getRoomMessages(String name) {
		Document room = getRoom(name);
		Document doc = apiGetRecentRoomHistory(room.getInteger("id"));
		List<Document> items = (List<Document>) doc.get("items");
		return items;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Document> getRoomMembers(String name) {
		Document room = getRoom(name);
		Document doc = apiGetRoomMembers(room.getInteger("id"));
		List<Document> items = (List<Document>) doc.get("items");
		return items;
	}
}
