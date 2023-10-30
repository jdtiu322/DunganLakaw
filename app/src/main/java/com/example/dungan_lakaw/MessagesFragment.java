package com.example.dungan_lakaw;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessagesFragment extends Fragment {

    private TextView messageTextView, messageTextView2;
    private EditText messageEditText;
    private Button sendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        messageTextView = view.findViewById(R.id.messageTextView);
        messageTextView2 = view.findViewById(R.id.messageTextView2);
        messageEditText = view.findViewById(R.id.messageEditText);
        sendButton = view.findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        // Populate initial fake messages
        String fakeMessages = getFakeMessages("Hello, how are things with you?");
        String fakeMessages2 = getFakeMessages("Wanna go mountain climbing with us? It will be fun, the whole gang is coming.");
        messageTextView.setText(fakeMessages);
        messageTextView2.setText(fakeMessages2);


        return view;
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            TextView sentMessageTextView = new TextView(getContext());
            sentMessageTextView.setText(message);
            sentMessageTextView.setTextColor(getResources().getColor(R.color.white));
            sentMessageTextView.setBackgroundResource(R.drawable.message_background_sent);
            sentMessageTextView.setPadding(8, 8, 8, 8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 8, 0, 0);
            sentMessageTextView.setLayoutParams(layoutParams);

            LinearLayout messageContainer = getView().findViewById(R.id.messageContainer);
            messageContainer.addView(sentMessageTextView);

            messageEditText.setText("");
        }
    }

    private String getFakeMessages(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(message);
        return stringBuilder.toString();
    }
}
