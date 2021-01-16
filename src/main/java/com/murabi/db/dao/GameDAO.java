package com.murabi.db.dao;

import java.util.ArrayList;
import java.util.Random;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import com.murabi.db.dto.GameDTO;

public class GameDAO {
    public static int NO_WINNER = -1;
    public static String GAME_ID = "game_id";
    public static String GAME_KEY = "game_key";
    public static String MURKER_ADDR = "murker_addr";
    public static String WINNER = "winner";
    public static String PLR_0 = "plr_0";
    public static String PLR_1 = "plr_1";

    private Connection conn;

    public GameDAO(Connection conn) {
        this.conn = conn;
    }

    public GameDTO fetchRandomUnfinishedGame() throws SQLException {
        ArrayList<GameDTO> dtos = new ArrayList<>();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT " + GAME_ID + ", " + GAME_KEY + ", " + MURKER_ADDR + " FROM games "+
                "WHERE " + WINNER + "=-1 AND " + PLR_0 + "!='' AND " + PLR_1 + " is NULL"
            );

            while (rs.next()) {          
                GameDTO dto = new GameDTO();

                dto.gameID = rs.getString(GAME_ID);
                dto.gameKey = rs.getString(GAME_KEY);
                dto.murkerAddr = rs.getString(MURKER_ADDR);

                dtos.add(dto);
            }   
        } finally {
            stmt.close();
        }

        if (dtos.size() == 0) {
            return null;
        }

        int rnd = new Random().nextInt(dtos.size());
        return dtos.get(rnd);
    }

    public void updatePlayer(String playerCol, String nickname, String gameID) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = simpleUpdateStatement(playerCol, gameID);
            stmt.setString(1, nickname);

            stmt.executeUpdate();
        } finally {
            stmt.close();
        }
    }

    public void insertGame(GameDTO dto) throws SQLException {
        String query = "INSERT INTO games (" + GAME_ID + ", " + GAME_KEY + ", " + MURKER_ADDR + ", " + PLR_0 + ") " +
                       "VALUES (?,?,?,?)";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, dto.gameID);
            stmt.setString(2, dto.gameKey);
            stmt.setString(3, dto.murkerAddr);
            stmt.setString(4, dto.plr0);

            stmt.executeUpdate();
        } finally {
            stmt.close();
        }
    }

    public void finishGame(String gameID, int winner) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = simpleUpdateStatement(WINNER, gameID);
            stmt.setInt(1, winner);

            stmt.executeUpdate();
        } finally {
            stmt.close();
        }
    }

    private PreparedStatement simpleUpdateStatement(String colName, String gameID) throws SQLException {
        String query = "UPDATE games SET " + colName + "=? WHERE " + GAME_ID + "=?";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(2, gameID);
        } catch (SQLException e) {
            stmt.close();
            throw e;
        }

        return stmt;
    }
}