package com.example.gebruiker.trivia;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HighScoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener{

    Callback activity;
    Context context;

    // constructor
    public HighScoreRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotHighScore(ArrayList<Player> players_list);
        void gotHighScoreError(String message);
    }

    // get a jsonarray with the highscore list
    void getHighScore(HighScoreRequest.Callback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest("https://ide50-sannedonker.cs50.io:8080/list",
                        HighScoreRequest.this, HighScoreRequest.this);
        queue.add(jsonArrayRequest);

        this.activity = activity;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighScoreError(error.getMessage());
    }

    // set players with their info in the players list
    @Override
    public void onResponse(JSONArray response){

        ArrayList<Player> players = new ArrayList<Player>();
        for (int position = 0; position < response.length(); position++) {
            try {
                JSONObject player = response.getJSONObject(position);
                String name = player.getString("name");
                String score = player.getString("score");
                String time = player.getString("time");
                players.add(new Player(name, score, time));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        activity.gotHighScore(players);

    }
}
