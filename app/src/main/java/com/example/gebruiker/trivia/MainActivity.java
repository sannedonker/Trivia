package com.example.gebruiker.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements QuestionRequest.Callback {

    ArrayList<QuestionItem> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load the questions
        QuestionRequest questions = new QuestionRequest(this);
        questions.getQuestion(this);
    }

    @Override
    public void gotQuestion(ArrayList<QuestionItem> question_items) {
        questions = question_items;
    }

    // show errormessage in case something went wrong
    @Override
    public void gotQuestionError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


    public void start_quiz(View view) {

        // pass the questions to QuestionActivity
        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        intent.putExtra("questions", questions);
        startActivity(intent);
    }

    public void go_to_highscore(View view) {

        // go to highscore screen
        Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
