package com.example.dungan_lakaw.cardviewnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dungan_lakaw.NotesActivity;
import com.example.dungan_lakaw.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class NoteEditor extends AppCompatActivity {

    private EditText title, text;

    private FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        title = (EditText) findViewById(R.id.txtTitleNoteeditor);
        text = (EditText) findViewById(R.id.txtTexteditor);

        add = (FloatingActionButton) findViewById(R.id.noteeditoradd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteTitle = String.valueOf(title.getText());
                String noteText = String.valueOf(text.getText());
                boolean done = true;


                File internalStorageDir = getFilesDir();

                String filename = "notes_" + System.currentTimeMillis() + ".txt";

                File file = new File(internalStorageDir,filename);

                if (file.exists()) {
                    // File already exists, handle accordingly
                    System.out.println("File exists");

                } else {
                    // Create the new file
                    try {
                        if (file.createNewFile()) {
                            // File created successfully
                            System.out.println("File createdd");
                        } else {
                            // Failed to create the file
                            System.out.println("!File createdd");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);

                    // Write the object to the file
                    NoteCards note = new NoteCards(noteTitle,noteText);
                    oos.writeObject(note);

                    // Close the ObjectOutputStream
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }




                Intent noteact = new Intent(NoteEditor.this, NotesActivity.class);
                noteact.putExtra("Title", noteTitle);
                noteact.putExtra("Text", noteText);
                noteact.putExtra("status", done);
                startActivity(noteact);


            }
        });

    }
}