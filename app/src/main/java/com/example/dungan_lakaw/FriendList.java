package com.example.dungan_lakaw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class FriendList extends ArrayAdapter<String> {

    private List<String> friendsList;
    private Context context;

    public FriendList(Context context, List<String> friendsList) {
        super(context, 0, friendsList);
        this.friendsList = friendsList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String friendName = friendsList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        }

        TextView friendTextView = convertView.findViewById(R.id.friendNameTextView);
        friendTextView.setText(friendName);

        return convertView;
    }
}
