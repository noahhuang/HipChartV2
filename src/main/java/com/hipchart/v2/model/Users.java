package com.hipchart.v2.model;

import java.util.List;

import org.bson.Document;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Users extends HipChatClient {

	private static List<Document> users;

	private static Document apiGetAllUsers() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get(baseUrl + "/user").header("accept", "application/json").queryString("auth_token", auth_token).asJson();
			return Document.parse(jsonResponse.getBody().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Document();
	}

	public static void apiSendUserNotification(int id, String message) {
		// https://api.hipchat.com/v2/room/2004975/history?auth_token=6ycaB6EUg46BY1q8LIP3Yqj76Fvisq9DHjRKTCju
		try {
			Document msg = new Document();
			msg.put("message", message);
			msg.put("notify", true);
			msg.put("message_format", "text");
			Unirest.post(baseUrl + "/user/" + id + "/message").header("Content-Type", "application/json").queryString("auth_token", auth_token)
					.body(msg.toJson()).asJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Document> getUsers() {
		Document doc = Users.apiGetAllUsers();
		List<Document> items = (List<Document>) doc.get("items");
		users = items;
		return items;
	}

	public static Document getUser(String name) {
		// new request
		if (users == null) {
			Users.getUsers();
		}
		// get from exist room
		for (Document document : users) {
			if (document.getString("name").equalsIgnoreCase(name))
				return document;
		}
		// query again if it's not existed
		Users.getUsers();
		for (Document document : users) {
			if (document.getString("name").equalsIgnoreCase(name) || document.getString("mention_name").equalsIgnoreCase(name))
				return document;
		}

		return new Document();
	}
}
