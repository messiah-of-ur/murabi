package com.murabi.api.interchange;

import java.util.ArrayList;

import com.murabi.api.interchange.Match;

public class HistoryResponse {
    public ArrayList<Match> matches;

    public HistoryResponse() {
        this.matches = new ArrayList<>();
    }

    public void addMatch(Match match) {
        matches.add(match);
    }
}