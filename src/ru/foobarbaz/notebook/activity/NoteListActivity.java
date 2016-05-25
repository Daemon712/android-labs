package ru.foobarbaz.notebook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import ru.foobarbaz.R;
import ru.foobarbaz.notebook.database.HelperFactory;
import ru.foobarbaz.notebook.model.Note;

public class NoteListActivity extends Activity {
    private ScrollView mainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_main);

        Button newNote = (Button) findViewById(R.id.newNote);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteIntent = new Intent(NoteListActivity.this, NoteActivity.class);
                startActivity(noteIntent);
            }
        });

        Button showTags = (Button) findViewById(R.id.showTagsButton);
        showTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteIntent = new Intent(NoteListActivity.this, TagListActivity.class);
                startActivity(noteIntent);
            }
        });

        mainPage = (ScrollView) findViewById(R.id.scrollView);
        HelperFactory.setHelper(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        showAllNotes();
    }

    private View.OnClickListener noteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
            intent.putExtra("noteId", Integer.toString(v.getId()));
            startActivity(intent);
        }
    };

    private void showAllNotes() {
        mainPage.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (Note note : HelperFactory.getHelper().getNoteDao().getAllNotes()) {
            Button button = new Button(this);
            button.setId(note.getId());
            button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            button.setOnClickListener(noteOnClickListener);
            button.setText(note.toString());
            linearLayout.addView(button);
        }
        mainPage.addView(linearLayout);
    }
}