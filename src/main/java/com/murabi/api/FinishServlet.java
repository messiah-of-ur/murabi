package com.murabi.api;


import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.SQLException;

import com.murabi.conf.Config;
import com.murabi.api.interchange.FinishRequest;
import com.murabi.db.dao.GameDAO;
import com.murabi.db.Connector;
import com.murabi.api.ServletUtil;

public class FinishServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");

        Config config = Config.parseConfig();

        Gson gson = new Gson();
        ServletUtil util = new ServletUtil();
        String body = util.readBody(req);

        FinishRequest finishReq = gson.fromJson(body, FinishRequest.class);

        try {
            Connection conn = Connector.obtainConn(config);
            GameDAO dao = new GameDAO(conn);

            int winner = finishReq.winner;

            if (winner != ServletUtil.PLR_0_ID && winner != ServletUtil.PLR_1_ID && winner != GameDAO.NO_WINNER) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            dao.finishGame(finishReq.gameID, finishReq.winner);
        } catch (SQLException ex) {
			throw new ServletException(ex.getMessage(), ex.fillInStackTrace());
		} catch (ClassNotFoundException ex) {
			throw new ServletException(ex.getMessage(), ex.fillInStackTrace());
		}
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Access-Control-Allow-Headers, Accept, Authorization, X-Requested-With");
        resp.addHeader("Access-Control-Max-Age", "1728000");
    }
}
