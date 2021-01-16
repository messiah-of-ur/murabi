package com.murabi.api;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import okhttp3.OkHttpClient;
import com.google.gson.Gson;
import org.javatuples.Pair;

import com.murabi.conf.Config;
import com.murabi.murker.Scheduler;
import com.murabi.murker.Client;
import com.murabi.api.interchange.GameResponse;

public class GameServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");

		PrintWriter pw = res.getWriter();
		Config config = Config.parseConfig();

		OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
		Gson gson = new Gson();
		Client client = new Client(httpClient, gson);

		Scheduler scheduler = new Scheduler(config, client);

		String key = generateKey();

		Pair<com.murabi.murker.interchange.GameResponse, String> game = scheduler.scheduleGame(key);
		
		GameResponse resp = new GameResponse(game.getValue0().gameID, key, game.getValue1());
		String marshalledResp = gson.toJson(resp);

		pw.println(marshalledResp);
		pw.close();
	}

	private String generateKey() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return shuffleString(uuid);
	}

	private String shuffleString(String in) {
		ArrayList<Character> chars = new ArrayList<>();

		for (char c:in.toCharArray()) {
			chars.add(c);
		}

		StringBuilder out = new StringBuilder(in.length());
		
		while (chars.size() > 0) {
			int randIdx = (int)(Math.random() * chars.size());
			out.append(chars.remove(randIdx));
		}

		return out.toString();
	}
}
