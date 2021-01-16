package com.murabi.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.BufferedReader;


class ServletUtil {
    public static int PLR_0_ID = 0;
    public static int PLR_1_ID = 1;

    public ServletUtil() {}

    public String readBody(HttpServletRequest req) throws ServletException {
		StringBuffer jb = new StringBuffer();
		String line = null;

		try {
			BufferedReader reader = req.getReader();

			while ((line = reader.readLine()) != null) {
				jb.append(line);
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage(), ex.fillInStackTrace());
		}

		return jb.toString();
	}
}