package com.example.gebruiker.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScoreActivity extends AppCompatActivity implements HighScoreRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        // instantiate highscorerequest
        HighScoreRequest scores = new HighScoreRequest(this);
        scores.getHighScore(this);

    }


    @Override
    public void gotHighScore(ArrayList<Player> players_list) {

        Log.d("test", "gotHighScore: ik kom hier");

        // sort the players on score (from high to low)
        Collections.sort(players_list, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int score_1 = Integer.parseInt(p1.getScore());
                int score_2 = Integer.parseInt(p2.getScore());
                if (score_1 > score_2) { return -1; }
                else if (score_1 < score_2) { return 1; }
                else { return 0; }
            }
        });

        // sort players with the same score on time (from low to high)
        ArrayList<Player> ranked_players = new ArrayList<>();
        for (int score = 10; score >= 0; score--){

            // get all players with the same amount of points
            ArrayList<Player> sort_time = new ArrayList<>();
            for (int position = 0; position < players_list.size(); position++){
                Player player = players_list.get(position);
                int score_player = Integer.parseInt(player.getScore());
                if (score_player == score) {
                    sort_time.add(player);
                }
            }

            // sort the players on their times
            Collections.sort(sort_time, new Comparator<Player>() {
                @Override
                public int compare(Player p1, Player p2) {
                    int time_1 = Integer.parseInt(p1.getTime().replace(":", ""));
                    int time_2 = Integer.parseInt(p2.getTime().replace(":", ""));
                    if (time_1 < time_2) { return -1; }
                    else if (time_1 > time_2) { return 1; }
                    else { return 0; }
                }
            });

            // add sorted time list to the final ranked list
            ranked_players.addAll(sort_time);
        }

        // set the listview with the correct ranking
        HighScoreAdapter adapter = new HighScoreAdapter(this, R.layout.ranked_player, ranked_players, this);
        ListView lv = findViewById(R.id.ranking_view);
        lv.setAdapter(adapter);

    }

    @Override
    public void gotHighScoreError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}
