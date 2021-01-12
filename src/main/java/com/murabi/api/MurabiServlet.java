package com.murabi.api;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class MurabiServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");

		PrintWriter pw = res.getWriter();
		pw.println("Welcome to Murabi Servlet");
		pw.close();
	}
}