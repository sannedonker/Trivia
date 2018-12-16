package com.example.gebruiker.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitActivity extends AppCompatActivity implements PostPlayer.Callback {

    private String name, score_string, time_string;

    // gets and sets score and time
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

    // submits score
    public void submit_score(View view) {

        // checks if a name was entered
        name = ((EditText) findViewById(R.id.name)).getText().toString();
        if (name.length() > 0) {

            // post player in the JSON and disable button so they can't submit multiple times
            PostPlayer player = new PostPlayer(name, score_string, time_string,
                    getApplicationContext(), SubmitActivity.this);
            Button submit_button = findViewById(R.id.submit);
            submit_button.setEnabled(false);
            Toast.makeText(this, "Please wait a few seconds for the highscores", Toast.LENGTH_LONG

            ).show();

        }
        else {
            Toast.makeText(this,"Please enter (nick)name",Toast.LENGTH_LONG).show();
        }
    }

    // if submit was succesfull
    @Override
    public void gotPost(String message) {

        // enable button again
        Button submit_button = findViewById(R.id.submit);
        submit_button.setEnabled(true);

        // go to highscore screen
        Intent intent = new Intent(SubmitActivity.this, HighScoreActivity.class);
        startActivity(intent);

    }

    // if submit was unsuccesfull
    @Override
    public void gotPostError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    // makes sure that when pressed back the user goes to the MainActivity screen
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(SubmitActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
