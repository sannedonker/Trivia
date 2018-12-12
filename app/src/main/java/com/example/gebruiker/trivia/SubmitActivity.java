package com.example.gebruiker.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SubmitActivity extends AppCompatActivity {

    private String name, score_string, time_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int score = extras.getInt("score");
        long time = extras.getLong("time");

        int min = (int) ((time / (1000*60)) % 60);
        int sec = (int) (time / 1000) % 60 ;
        int milisec = (int) time % 60;

        time_string = String.format("%02d:%02d:%02d", min, sec, milisec);
        score_string = Integer.toString(score);

        ((TextView) findViewById(R.id.score)).setText("Score: " + score);
        ((TextView) findViewById(R.id.time)).setText("Time: " + time_string);
    }

    // submits score and redirects to the highscore screen
    public void submit_score(View view) {

        name = ((EditText) findViewById(R.id.name)).getText().toString();
        if (name.length() > 0) {

            // TODO safe everything in the thing so it can be printed

            // go to highscore screen
            Intent intent = new Intent(SubmitActivity.this, HighScoreActivity.class);
            startActivity(intent);
        }
        else {
            // TODO maak toast die zegt dat ze naam moeten invullen
        }
    }
}
