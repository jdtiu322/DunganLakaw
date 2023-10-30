package com.example.dungan_lakaw.cardview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dungan_lakaw.R;
import java.util.ArrayList;



    public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardHolder> {

    private Context context;
    private ArrayList<EventCards> events;

    public CardViewAdapter(Context context, ArrayList<EventCards> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        EventCards event = events.get(position);
        holder.setDetails(event);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class CardHolder extends RecyclerView.ViewHolder{
        private TextView Month, Text;

        CardHolder(View itemview){
            super(itemview);
            Month = itemview.findViewById(R.id.txtMonthDate);
            Text = itemview.findViewById(R.id.textView2);
        }

        void setDetails(EventCards event){
            int day = event.getDay();
            String text = event.getMonthname();
            switch (event.getMonth()){
                case 0:
                    Month.setText("January " + day);
                    break;
                case 1:
                    Month.setText("February " + day);
                    break;
                case 2:
                    Month.setText("March " + day);
                    break;
                case 3:
                    Month.setText("April " + day);
                    break;
                case 4:
                    Month.setText("May " + day);
                    break;
                case 5:
                    Month.setText("June " + day);
                    break;
                case 6:
                    Month.setText("July " + day);
                    break;
                case 7:
                    Month.setText("August " + day);
                    break;
                case 8:
                    Month.setText("September " + day);
                    break;
                case 9:
                    Month.setText("October " + day);
                    break;
                case 10:
                    Month.setText("November " + day);
                    break;
                case 11:
                    Month.setText("December " + day);
                    break;
            }
                Text.setText(text);
        }
    }
}
