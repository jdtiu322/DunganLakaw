package com.example.dungan_lakaw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dungan_lakaw.cardviewnotes.NoteCards;
import com.example.dungan_lakaw.cardviewnotes.NoteEditor;
import com.example.dungan_lakaw.cardviewnotes.NotesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity{

    private RecyclerView Notesrecyclerview;
    private NotesAdapter notesAdapter;
    private ArrayList<NoteCards> notearraylist;
    private FloatingActionButton btnaddnotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        btnaddnotes = (FloatingActionButton) findViewById(R.id.fabaddnotes);

        InitializeNotes();

//        try{
//            Intent result = getIntent();
//            String title = result.getStringExtra("Title");
//            String text = result.getStringExtra("Text");
//            if(!title.equals(null)){
//                CreateNotes(title,text);
//            }
//        }catch (NullPointerException e){
//            System.out.println("Erororororor");
//        }


// Create collections to store the deserialized objects

        File directory = getFilesDir();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();

                if (fileName.contains("note")) {
                    // Found a file with "notes" in its name
                    // Perform desired operations on the file

                    try (FileInputStream fileIn1 = new FileInputStream(getFilesDir()+"/"+fileName);
                         ObjectInputStream objectIn1 = new ObjectInputStream(fileIn1);
                         // Open more files and object input streams as needed
                    ) {
                        NoteCards deserializedObject1;
                        // Create variables to hold deserialized objects

                        while (fileIn1.available() > 0) {
                            deserializedObject1 = (NoteCards) objectIn1.readObject();
                            CreateNotes(deserializedObject1.getTitle(),deserializedObject1.getText());
                            // Add deserialized object to the collection
                            System.out.println("sdfsdfsdfsdf");
                        }

                        // Read and process objects from more files as needed
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }










        btnaddnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noteeditor = new Intent(NotesActivity.this, NoteEditor.class);
                startActivity(noteeditor);

//                File internalStorageDir = getFilesDir();
//
//                String filename = "note_" + System.currentTimeMillis() + ".txt";
//
//                File file = new File(internalStorageDir,filename);
//
//                if (file.exists()) {
//                    // File already exists, handle accordingly
//                    System.out.println("File exists");
//
//                } else {
//                    // Create the new file
//                    try {
//                        if (file.createNewFile()) {
//                            // File created successfully
//                            System.out.println("File createdd");
//                        } else {
//                            // Failed to create the file
//                            System.out.println("!File createdd");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

//                try{
//                    Intent result = getIntent();
//                    String title = result.getStringExtra("Title");
//                    String text = result.getStringExtra("Text");
//                    NoteCards note = new NoteCards(title,text);
//                    CreateNotes(title,text);
//                }catch (NullPointerException e){
//                    System.out.println("Erororororor");
//                }

//                try {
//                    FileOutputStream fos = new FileOutputStream(file);
//                    ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//                    // Write the object to the file
//                    oos.writeObject(note);
//
//                    // Close the ObjectOutputStream
//                    oos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }




//
//                Intent result = getIntent();
//                String title = result.getStringExtra("Title");
//                String text = result.getStringExtra("Text");

//                System.out.println(title);
//                System.out.println(text);

//                String title = "ONG";
//                String text = "sdfsdfsdf";
////
////
//                CreateNotes(title,text);
            }
        });
    }

    private void CreateNotes(String title, String text) {
//        if(title.equals(null) || text.equals(null)){
//            System.out.println("error");
//        }else{
        NoteCards note = new NoteCards(title,text);

        notearraylist.add(note);


        notesAdapter.notifyDataSetChanged();
//        }
    }

    private void InitializeNotes() {
        Notesrecyclerview = (RecyclerView) findViewById(R.id.Notesrecyclerview);
        Notesrecyclerview.setLayoutManager(new GridLayoutManager(NotesActivity.this, 2));
        notearraylist = new ArrayList<>();

        notesAdapter = new NotesAdapter(NotesActivity.this,notearraylist);
        Notesrecyclerview.setAdapter(notesAdapter);
    }
}