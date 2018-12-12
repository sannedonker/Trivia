package com.example.gebruiker.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SubmitActivity extends AppCompatActivity {

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

        String time_string = String.format("Time: %02d:%02d:%02d", min, sec, milisec);

        ((TextView) findViewById(R.id.score)).setText("Score: " + score);
        ((TextView) findViewById(R.id.time)).setText(time_string);
    }
}
