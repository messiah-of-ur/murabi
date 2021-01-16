package com.murabi.murker;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;

import com.murabi.murker.interchange.GameRequest;
import com.murabi.murker.interchange.GameResponse;
import com.murabi.murker.interchange.StateResponse;

public class Client {
    public static String GAME_ENDPOINT = "/v1/game";
    public static String STATE_ENDPOINT = "/v1/state";

    public static String POST = "POST";
    public static String GET = "GET";

    private OkHttpClient client;
    private MediaType mediaType;
    private Gson gson;

    public Client(OkHttpClient client, Gson gson) {
        this.client = client;
        this.mediaType = MediaType.parse("application/json");

        this.gson = gson;
    }

    public GameResponse createGame(String murkerAddr, String key) throws java.io.IOException {
        GameRequest req = new GameRequest(key);

        String marshaledReq = gson.toJson(req);

        RequestBody body = RequestBody.create(mediaType, marshaledReq);
        Response resp = fetch(murkerAddr, GAME_ENDPOINT, POST, body);

        String respAsStr = resp.body().string();

        return gson.fromJson(respAsStr, GameResponse.class);
    }

    public StateResponse fetchState(String murkerAddr) throws java.io.IOException {
        Response resp = fetch(murkerAddr, STATE_ENDPOINT, GET, null);

        String respAsStr = resp.body().string();

        return gson.fromJson(respAsStr, StateResponse.class);
    }

    private Response fetch(String murkerAddr, String endpoint, String httpVerb, RequestBody body) throws java.io.IOException {
        Request request = new Request.Builder()
        .url("http://" + murkerAddr + endpoint)
        .method(httpVerb, body)
        .addHeader("Content-Type", "application/json")
        .build();

        return client.newCall(request).execute();
    }
}