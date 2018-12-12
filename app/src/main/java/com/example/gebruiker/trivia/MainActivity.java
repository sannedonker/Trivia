package com.example.gebruiker.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    @Override
    public void gotQuestionError(String message) {
        //TODO moet dit eigenlijk?
    }


    public void start_quiz(View view) {

        // pass the questions to QuestionActivity
        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        intent.putExtra("questions", questions);
        startActivity(intent);
    }
}
