package com.murabi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.murabi.conf.Config;

public class Connector {
    private static Connection conn = null;

    public static Connection obtainConn(Config conf) throws ClassNotFoundException, SQLException {
        if (conn != null) {
            return conn;
        }

        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://" + conf.dbHost + ":" +  conf.dbPort + "/" + conf.dbName;
        Properties props = new Properties();
        props.setProperty("user", conf.dbUser);
        props.setProperty("password", conf.dbPass);

        Connection conn = DriverManager.getConnection(url, props);

        return conn;
    }
    
    private Connector() {}
}