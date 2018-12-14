package com.example.gebruiker.trivia;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PostPlayer implements Response.ErrorListener, Response.Listener {

    String name, score, time;
    Callback activity;


    public PostPlayer(String name, String score, String time, Context context, Callback activity) {
        this.name = name;
        this.score = score;
        this.time = time;
        this.activity = activity;

        RequestQueue queue = Volley.newRequestQueue(context);
        PostRequest request = new PostRequest(Request.Method.POST,
                "https://ide50-sannedonker.cs50.io:8080/list",
                PostPlayer.this, PostPlayer.this);
        queue.add(request);
    }

    public interface Callback {
        void gotPost(String message);
        void gotPostError(String message);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotPostError(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        activity.gotPost("Relax and wait to see where you're ranked");
    }

    public class PostRequest extends StringRequest {

        // constructor
        public PostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("score", score);
            params.put("time", time);
            return params;
        }
    }

}
