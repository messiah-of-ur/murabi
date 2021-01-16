package com.murabi.conf;

import java.util.ArrayList;

public class Config {
    public static String MURKER_ADDRESSES = "MURKER_ADDRESSES";
    public static String ADDR_SEPARATOR = ",";

    public ArrayList<String> murkerAddresses;

    public static Config parseConfig() {
        Config config = new Config();

        config.parseMurkerAddresses();

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