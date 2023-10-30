package com.example.dungan_lakaw;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class FriendsFragment extends Fragment {
    private ListView friendListView;
    private FriendList friendList;
    private List<String> friends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        friendListView = rootView.findViewById(R.id.friendListView);
        friends = new ArrayList<>();

        // Manually add friends to the list
        friends.add("John Wick");
        friends.add("Emily Ratajkowski");
        friends.add("Michael Jordan");
        friends.add("Sarah Geronimo");
        friends.add("Indian Jones");
        friends.add("Darth Vader");
        friends.add("Luke Skywalker");
        friends.add("Han Solo");
        friends.add("Peter Parker");
        friends.add("Tony Stark");
        friends.add("Harry Potter");
        friends.add("Steve Rogers");
        friends.add("Barry Allen");
        friends.add("Tara Ysbel Datan");
        friends.add("Jomar Magdalaga");
        friends.add("Mikhaila Pantoja");
        friends.add("Charlotte Taboada");
        friends.add("Lance Salera");
        // ... add more friends

        friendList = new FriendList(getActivity(), friends);
        friendListView.setAdapter(friendList);

        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected friend's name
                String selectedFriend = friends.get(position);

                // Create a new MessagesFragment instance and pass the selected friend's name
                MessagesFragment messagesFragment = new MessagesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("friendName", selectedFriend);
                messagesFragment.setArguments(bundle);

                // Navigate to the MessagesFragment
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, messagesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }
}


