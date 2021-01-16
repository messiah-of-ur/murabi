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
import java.sql.Connection;
import java.sql.SQLException;

import com.murabi.conf.Config;
import com.murabi.murker.Scheduler;
import com.murabi.murker.Client;
import com.murabi.db.Connector;
import com.murabi.db.dao.GameDAO;
import com.murabi.db.dto.GameDTO;
import com.murabi.api.interchange.GameResponse;
import com.murabi.api.interchange.GameRequest;
import com.murabi.api.ServletUtil;

public class GameServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");

		Config config = Config.parseConfig();

		OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
		Gson gson = new Gson();
		Client client = new Client(httpClient, gson);
		Scheduler scheduler = new Scheduler(config, client);	
		GameResponse resp = null;
		
		ServletUtil util = new ServletUtil();
		String body = util.readBody(req);
		GameRequest gameReq = gson.fromJson(body, GameRequest.class);

		try {
			Connection conn = Connector.obtainConn(config);
			GameDAO dao = new GameDAO(conn);

			resp = findGame(gameReq, dao, scheduler);
		} catch (SQLException ex) {
			throw new ServletException(ex.getMessage(), ex.fillInStackTrace());
		} catch (ClassNotFoundException ex) {
			throw new ServletException(ex.getMessage(), ex.fillInStackTrace());
		}
		
		String marshalledResp = gson.toJson(resp);

		PrintWriter pw = res.getWriter();
		pw.println(marshalledResp);
		pw.close();
	}

	private GameResponse findGame(GameRequest req, GameDAO dao, Scheduler scheduler) throws SQLException, IOException {
		GameDTO dto = dao.fetchRandomUnfinishedGame();

		if (dto == null) {
			System.out.println("Created a game");
			return scheduleGame(req, dao, scheduler);
		}

		System.out.println("Reused game" + dto.gameID);
		dao.updatePlayer(GameDAO.PLR_1, req.nickname, dto.gameID);
		return new GameResponse(dto.gameID, dto.gameKey, dto.murkerAddr, ServletUtil.PLR_1_ID);
	}

	private GameResponse scheduleGame(GameRequest req, GameDAO dao, Scheduler scheduler) throws SQLException, IOException {
		String key = generateKey();
		Pair<com.murabi.murker.interchange.GameResponse, String> game = scheduler.scheduleGame(key);

		GameDTO dto = new GameDTO();
		dto.gameID = game.getValue0().gameID;
		dto.gameKey = key;
		dto.murkerAddr = game.getValue1();
		dto.plr0 = req.nickname;

		dao.insertGame(dto);
		
		return new GameResponse(dto.gameID, dto.gameKey, dto.murkerAddr, ServletUtil.PLR_0_ID);
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
