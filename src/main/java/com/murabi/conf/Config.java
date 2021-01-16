package com.murabi.conf;

import java.util.ArrayList;

public class Config {
    public static String MURKER_ADDRESSES = "MURKER_ADDRESSES";
    public static String ADDR_SEPARATOR = ",";
    public static String DB_HOST = "DB_HOST";
    public static String DB_PORT = "DB_PORT";
    public static String DB_USER = "DB_USER";
    public static String DB_PASS = "DB_PASS";
    public static String DB_NAME = "DB_NAME";

    public ArrayList<String> murkerAddresses;
    public String dbHost;
    public String dbPort;
    public String dbUser;
    public String dbPass;
    public String dbName;

    public static Config parseConfig() {
        Config config = new Config();

        config.parseMurkerAddresses();

        config.dbHost = System.getenv(DB_HOST);
        config.dbPort = System.getenv(DB_PORT);
        config.dbUser = System.getenv(DB_USER);
        config.dbPass = System.getenv(DB_PASS);
        config.dbName = System.getenv(DB_NAME);

        return config;
    }

    private Config() {
        murkerAddresses = new ArrayList<>();
    }

    private void parseMurkerAddresses() {
        String rawAddresses = System.getenv(MURKER_ADDRESSES);
        String[] addresses = rawAddresses.split(ADDR_SEPARATOR);

        for (int i = 0; i < addresses.length; i++) {
            murkerAddresses.add(addresses[i]);
        }
    }
}