package com.murabi.api.interchange;

public class Match {
    public String player0;
    public String player1;
    public String winner = "None";

    public Match(String player0, String player1, int winner) {
        this.player0 = player0;
        this.player1 = player1;
        
        if (winner == 0) {
            this.winner = this.player0;
        } else if (winner == 1) {
            this.winner = this.player1;
        }
    }
}