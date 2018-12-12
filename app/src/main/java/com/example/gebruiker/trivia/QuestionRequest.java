package com.example.gebruiker.trivia;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    Callback activity;
    Context context;

    // constructor
    public QuestionRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotQuestion(ArrayList<QuestionItem> questions);
        void gotQuestionError(String message);
    }

    void getQuestion(Callback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                    "https://opentdb.com/api.php?amount=10&category=19&difficulty=medium&type=multiple",
                    null, QuestionRequest.this, QuestionRequest.this);
        queue.add(jsonObjectRequest);

        this.activity = activity;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response){
        JSONArray results = null;
        try {
            results = response.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<QuestionItem> questions = new ArrayList<QuestionItem>(results.length());
        for (int position = 0; position < results.length(); position++){
            try {
                JSONObject question_list = results.getJSONObject(position);
                String question = question_list.getString("question");
                String correct_answer = question_list.getString("correct_answer");
                ArrayList<String> incorrect_answers = new ArrayList<>();
                JSONArray incorrect_array = question_list.getJSONArray("incorrect_answers");
                for (int i = 0; i < incorrect_array.length(); i++) {
                    incorrect_answers.add(incorrect_array.getString(i));
                }
                questions.add(new QuestionItem(question, correct_answer, incorrect_answers));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        activity.gotQuestion(questions);
    }


}
