package com.murabi.api;

import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.Gson;

import com.murabi.conf.Config;
import com.murabi.db.Connector;
import com.murabi.db.dto.GameDTO;
import com.murabi.db.dao.GameDAO;
import com.murabi.api.interchange.Match;
import com.murabi.api.interchange.HistoryResponse;


public class HistoryServlet extends HttpServlet {
    private Config config;
    private Gson gson;
    private Connection conn;
    private GameDAO dao;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.addHeader("Access-Control-Allow-Origin", "*");

        HistoryResponse history = new HistoryResponse();

        try {
            ArrayList<GameDTO> dtos = this.dao.fetchGameHistory();            

            for (int i = 0; i < dtos.size(); i++) {
                GameDTO dto = dtos.get(i);
                Match match = new Match(dto.plr0, dto.plr1, dto.winner);

                history.addMatch(match);
            }

        } catch (Exception ex) {
            throw new ServletException(ex.getMessage(), ex.fillInStackTrace());
        }

        String marshalledResp = gson.toJson(history);

        PrintWriter pw = res.getWriter();
        pw.println(marshalledResp);
        pw.close();
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Access-Control-Allow-Headers, Accept, Authorization, X-Requested-With");
        resp.addHeader("Access-Control-Max-Age", "1728000");
    }

    @Override
    public void init() throws ServletException {
        try {
            this.config = Config.parseConfig();
            this.gson = new Gson();
            this.conn = Connector.obtainConn(config);
            this.dao = new GameDAO(conn);
        } catch (Exception ex) {
            throw new ServletException(ex.getMessage());
        }
    }

    @Override
    public void destroy() {
        System.out.println("Servlet " + this.getServletName() + " has stopped");
    }
}