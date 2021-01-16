package com.murabi.api.interchange;

public class GameResponse {
    public String gameID;
    public String key;
    public String murkerAddr;

    public GameResponse(String gameID, String key, String murkerAddr) {
        this.gameID = gameID;
        this.key = key;
        this.murkerAddr = murkerAddr;
    }
}