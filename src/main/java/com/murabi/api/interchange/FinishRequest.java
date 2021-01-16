package com.murabi.api.interchange;

public class FinishRequest {
    public String gameID;
    public int winner;

    public FinishRequest(String gameID, int winner) {
        this.gameID = gameID;
        this.winner = winner;
    }
}