package com.example.dungan_lakaw.cardviewnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dungan_lakaw.cardviewnotes.NoteCards;
import com.example.dungan_lakaw.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesCardHolder>{
    private Context context;
    private ArrayList<NoteCards> notes;


    public NotesAdapter(Context context, ArrayList<NoteCards> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_view,parent,false);
        return new NotesCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesCardHolder holder, int position) {
        NoteCards note = notes.get(position);
        holder.setDetailsNotes(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesCardHolder extends RecyclerView.ViewHolder{
        private TextView Title, text;

        NotesCardHolder(View noteview){
            super(noteview);
            Title = noteview.findViewById(R.id.txtNotetitle);
            text = noteview.findViewById(R.id.textView3);
        }

        void setDetailsNotes(NoteCards note){
            String title = note.getTitle();
            String Text = note.getText();
//            System.out.println(note.getText());
//            System.out.println(note.getTitle());
            Title.setText(note.getTitle());
            text.setText(note.getText());
        }
    }
}
