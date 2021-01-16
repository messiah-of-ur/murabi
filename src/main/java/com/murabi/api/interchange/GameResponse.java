package com.murabi.api.interchange;

public class GameResponse {
    public static String DOCKER_LOCALHOST = "host.docker.internal";
    public static String LOCALHOST = "localhost";
    public static String IP_PORT_SPLITTER = ":";

    public String gameID;
    public String key;
    public String murkerAddr;

    public GameResponse(String gameID, String key, String murkerAddr) {
        this.gameID = gameID;
        this.key = key;
        this.murkerAddr = prepareMurkerAddr(murkerAddr);
    }

    private String prepareMurkerAddr(String addr) {
        String[] chunks = addr.split(IP_PORT_SPLITTER);

        if (chunks[0].equals(DOCKER_LOCALHOST)) {
            return LOCALHOST + IP_PORT_SPLITTER + chunks[1];
        }

        return addr;
    }
}
