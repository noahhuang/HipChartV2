package com.hipchart.v2.model;

import com.mashape.unirest.http.Unirest;

public class HipChatClient extends Unirest {
	public static String baseUrl = "https://api.hipchat.com/v2";
	public static String auth_token = "your token";
	public static String Color_Msg_Yellow = "yellow";
	public static String Color_Msg_Green = "green";
	public static String Color_Msg_Red = "red";
	public static String Color_Msg_Purple = "purple";
	public static String Color_Msg_Gray = "gray";
	public static String Color_Msg_Random = "random";
	public HipChatClient() {
		// TODO Auto-generated constructor stub
	}

}
