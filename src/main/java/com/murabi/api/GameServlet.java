package com.murabi.api;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import okhttp3.OkHttpClient;
import com.google.gson.Gson;

import com.murabi.conf.Config;
import com.murabi.murker.Scheduler;
import com.murabi.murker.Client;

public class GameServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("application/json");

		PrintWriter pw = res.getWriter();
		Config config = Config.parseConfig();

		OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
		Gson gson = new Gson();
		Client client = new Client(httpClient, gson);

		Scheduler scheduler = new Scheduler(config, client);

		pw.println("{'nice': 'one'}" + scheduler.electMurker());
		pw.close();
	}
}
