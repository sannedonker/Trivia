package com.example.gebruiker.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionActivity extends AppCompatActivity {

    int counter = 0;
    int nmbr_questions = 10;
    int points = 0;
    String answer;
    ArrayList<QuestionItem> questions;
    long start_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        questions = (ArrayList<QuestionItem>) intent.getSerializableExtra("questions");

        QuestionItem question = questions.get(counter);

        // get all the answers in one list and sort them
        ArrayList<String> answers = question.getIncorrect_answers();
        answer = question.getCorrect_answer();
        answers.add(answer);
        Collections.sort(answers);

        // set the question and the buttons
        ((TextView) findViewById(R.id.question)).setText(question.getQuestion());
        ((TextView) findViewById(R.id.answer_a)).setText(answers.get(0));
        ((TextView) findViewById(R.id.answer_b)).setText(answers.get(1));
        ((TextView) findViewById(R.id.answer_c)).setText(answers.get(2));
        ((TextView) findViewById(R.id.answer_d)).setText(answers.get(3));

        start_time = System.currentTimeMillis();

    }

    public void answer_clicked(View view) {

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(counter + 1);

        // check if the given answer is the correct answer
        int id = view.getId();
        CharSequence answer_given = ((TextView) findViewById(id)).getText();
        if (answer_given.equals(answer)) {
            points += 1;
        }

        counter += 1;

        if (counter < nmbr_questions) {

            // get the next question
            QuestionItem next_question = questions.get(counter);

            // get all the answers in one list and sort them
            ArrayList<String> next_answers = next_question.getIncorrect_answers();
            answer = next_question.getCorrect_answer();
            next_answers.add(answer);
            Collections.sort(next_answers);

            // set the question and the buttons
            ((TextView) findViewById(R.id.question)).setText(next_question.getQuestion());
            ((TextView) findViewById(R.id.answer_a)).setText(next_answers.get(0));
            ((TextView) findViewById(R.id.answer_b)).setText(next_answers.get(1));
            ((TextView) findViewById(R.id.answer_c)).setText(next_answers.get(2));
            ((TextView) findViewById(R.id.answer_d)).setText(next_answers.get(3));

        }
        else {

            long end_time = System.currentTimeMillis();
            long play_time = end_time - start_time;

            // pass the score and time to SubmitActivity
            Intent intent = new Intent(QuestionActivity.this, SubmitActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("score", points);
            extras.putLong("time", play_time);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    // makes sure that when pressed back the user goes to the MainActivity screen
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
