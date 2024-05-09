package com.mryazonexx.mrvaultnote;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteFormDetails extends AppCompatActivity {

    EditText noteTitleBox, noteContentBox;
    ImageButton saveNotesButton;
    TextView noteTitle, deleteNoteBoxButton;
    String title, content, docId;
    boolean isEditMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_form_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        noteTitleBox = findViewById(R.id.note_title_box);
        noteContentBox = findViewById(R.id.note_content_box);
        saveNotesButton = findViewById(R.id.save_notes_button);
        noteTitle = findViewById(R.id.note_title);
        deleteNoteBoxButton = findViewById(R.id.delete_note_box_button);

        //receive data
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if (docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }

        noteTitleBox.setText(title);
        noteContentBox.setText(content);
        if (isEditMode) {
            noteTitle.setText("Edit yor note");
            deleteNoteBoxButton.setVisibility(View.VISIBLE);
        }

        saveNotesButton.setOnClickListener((v) -> saveNote());
        deleteNoteBoxButton.setOnClickListener((v) -> deleteNoteFromFirebase());

    }

    void saveNote() {
        String noteTitle = noteTitleBox.getText().toString();
        String noteContent = noteContentBox.getText().toString();
        if (noteTitle == null || noteTitle.isEmpty()) {
            noteTitleBox.setError("Title is Required");
            return;
        }
        if (noteContent == null || noteContent.isEmpty()) {
            noteContentBox.setError("Content is Required");
            return;
        }

        NoteTools note = new NoteTools();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);

    }

    void saveNoteToFirebase(NoteTools note) {
        DocumentReference documentReference;

        if (isEditMode) {
            //update the note
            documentReference = Tools.getCollectionReferenceForNotes().document(docId);
        } else {
            //create nwe note
            documentReference = Tools.getCollectionReferenceForNotes().document();
        }
        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //note is add
                    Tools.showToast(NoteFormDetails.this, "Note added successfully");
                } else {
                    Tools.showToast(NoteFormDetails.this, task.getException().getLocalizedMessage());
                }
            }

        });

    }

    void deleteNoteFromFirebase() {
        DocumentReference documentReference;
        documentReference = Tools.getCollectionReferenceForNotes().document(docId);


        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //note is deleted
                    Tools.showToast(NoteFormDetails.this, "Note deleted successfully");
                    finish();
                } else {
                    Tools.showToast(NoteFormDetails.this, task.getException().getLocalizedMessage());
                }
            }

        });

    }

}

