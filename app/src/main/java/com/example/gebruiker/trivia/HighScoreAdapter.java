package com.example.gebruiker.trivia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreAdapter extends ArrayAdapter {

    private ArrayList<Player> players;
    Context context;

    // constructor
    public HighScoreAdapter(Context context, int resource, ArrayList<Player> players, Context context1) {
        super(context, resource, players);
        this.players = players;
        this.context = context1;
    }

    public View getView(int position, View lv, ViewGroup parent) {

        // loads new items
        if (lv == null) {
            lv = LayoutInflater.from(getContext()).inflate(R.layout.ranked_player, parent, false);
        }

        // adds player to the listview
        Player player = players.get(position);
        ((TextView) lv.findViewById(R.id.name)).setText(player.getName());
        ((TextView) lv.findViewById(R.id.score)).setText(player.getScore());
        ((TextView) lv.findViewById(R.id.time)).setText(player.getTime());
        ((TextView) lv.findViewById(R.id.rank)).setText(position + 1 + ".");

        return lv;

    }

}
