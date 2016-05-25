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
import ru.foobarbaz.notebook.model.Tag;

public class NoteBookMainActivity extends Activity implements View.OnClickListener {

    private boolean areNotesShowing;
    private Button newNote;
    private Button newTag;
    private Button showNotes;
    private Button showTags;
    private ScrollView mainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_main);

        newNote = (Button) findViewById(R.id.newNote);
        newNote.setOnClickListener(this);

        newTag = (Button) findViewById(R.id.newTag);
        newTag.setOnClickListener(this);

        showNotes = (Button) findViewById(R.id.showNotesButton);
        showNotes.setOnClickListener(this);

        showTags = (Button) findViewById(R.id.showTagsButton);
        showTags.setOnClickListener(this);

        mainPage = (ScrollView) findViewById(R.id.scrollView);
        HelperFactory.setHelper(getApplicationContext());

        showStartPage();
    }

    private void showStartPage() {
        Intent intent = getIntent();
        if ("tags".equals(intent.getStringExtra("pageToShow"))) {
            showAllTags();
        } else {
            showAllNotes();
        }
    }

    private void showAllTags() {
        areNotesShowing = false;
        mainPage.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (Tag tag : HelperFactory.getHelper().getTagDao().getAllTags()) {
            Button button = new Button(this);
            button.setId(tag.getId());
            button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            button.setOnClickListener(this);
            button.setText(tag.toString());
            linearLayout.addView(button);
        }
        mainPage.addView(linearLayout);
    }

    private void showAllNotes() {
        areNotesShowing = true;
        mainPage.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (Note note : HelperFactory.getHelper().getNoteDao().getAllNotes()) {
            Button button = new Button(this);
            button.setId(note.getId());
            button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            button.setOnClickListener(this);
            button.setText(note.toString());
            linearLayout.addView(button);
        }
        mainPage.addView(linearLayout);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showNotesButton:
                showAllNotes();
                break;
            case R.id.showTagsButton:
                showAllTags();
                break;
            case R.id.newNote:
                showTagActivity();
                break;
            case R.id.newTag:
                showNoteActivity();
                break;
            default:
                Intent intent = new Intent(this, getClassToIntent());
                intent.putExtra(getPropertyToIntent(), Integer.toString(view.getId()));
                startActivity(intent);
        }
    }

    private void showTagActivity() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void showNoteActivity() {
        Intent tagIntent = new Intent(this, TagActivity.class);
        startActivity(tagIntent);
    }

    private Class<?> getClassToIntent() {
        if (areNotesShowing) {
            return NoteActivity.class;
        }
        return TagActivity.class;
    }

    private String getPropertyToIntent() {
        if (areNotesShowing) {
            return "noteId";
        }
        return "tagId";
    }
}