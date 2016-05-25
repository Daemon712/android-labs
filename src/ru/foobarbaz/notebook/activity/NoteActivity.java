package ru.foobarbaz.notebook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.*;
import ru.foobarbaz.R;
import ru.foobarbaz.notebook.database.HelperFactory;
import ru.foobarbaz.notebook.model.Note;
import ru.foobarbaz.notebook.model.NoteTag;
import ru.foobarbaz.notebook.model.Tag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NoteActivity extends Activity implements View.OnClickListener {

    private Note currentNote;
    private Button save;
    private Button cancel;
    private Button delete;
    private EditText noteDate;
    private EditText noteTitle;
    private EditText noteBody;
    private ListView tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_note);

        save = (Button) findViewById(R.id.saveNoteButton);
        save.setOnClickListener(this);

        cancel = (Button) findViewById(R.id.cancelNoteButton);
        cancel.setOnClickListener(this);

        delete = (Button) findViewById(R.id.deleteNoteButton);
        delete.setOnClickListener(this);

        noteDate = (EditText) findViewById(R.id.noteDate);
        noteDate.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()));

        noteTitle = (EditText) findViewById(R.id.noteTitle);
        noteBody = (EditText) findViewById(R.id.noteBody);

        tags = (ListView) findViewById(R.id.tagListView);
        tags.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        tags.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, HelperFactory.getHelper().getTagDao().getAllTags()));
        showCurrentNote();
    }

    private void showCurrentNote() {
        Intent intent = getIntent();
        String noteId = intent.getStringExtra("noteId");
        if (noteId != null) {
            currentNote = HelperFactory.getHelper().getNoteDao().queryForId(Integer.parseInt(noteId));
            noteTitle.setText(currentNote.getTitle());
            noteBody.setText(currentNote.getBody());
            checkTagsForCurrentNote();
        }
    }

    private void checkTagsForCurrentNote() {
        ListAdapter adapter = tags.getAdapter();
        for (Tag tag : HelperFactory.getHelper().getNoteTagDao().getTagsForNote(currentNote)) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (tag.equals(adapter.getItem(i))) {
                    tags.setItemChecked(i, true);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelNoteButton:
                cancel();
                break;
            case R.id.saveNoteButton:
                save();
                break;
            case R.id.deleteNoteButton:
                delete();
                break;
        }
    }

    private void cancel() {
        finish();
    }

    private void save() {
        if (emptyRequiredFields()) {
            return;
        }
        Note noteToSave = getFilledNote();
        if (currentNote == null) {
            HelperFactory.getHelper().getNoteDao().create(noteToSave);
            createNoteTags(noteToSave);
        } else {
            currentNote.setTitle(noteToSave.getTitle());
            currentNote.setBody(noteToSave.getBody());
            currentNote.setCreatedDate(noteToSave.getCreatedDate());
            HelperFactory.getHelper().getNoteDao().update(currentNote);
            HelperFactory.getHelper().getNoteTagDao().deleteTagsForNote(currentNote);
            createNoteTags(currentNote);
        }
        finish();
    }

    private boolean emptyRequiredFields() {
        return noteDate.getText().toString().isEmpty()
                || noteTitle.getText().toString().isEmpty()
                || noteBody.getText().toString().isEmpty();
    }

    private Note getFilledNote() {
        Note note = new Note();
        note.setTitle(noteTitle.getText().toString());
        note.setBody(noteBody.getText().toString());
        note.setCreatedDate(noteDate.getText().toString());
        return note;
    }

    private void createNoteTags(Note note) {
        for (Tag tag : getSelectedTags()) {
            NoteTag noteTag = new NoteTag(note, tag);
            HelperFactory.getHelper().getNoteTagDao().create(noteTag);
        }
    }

    private List<Tag> getSelectedTags() {
        List<Tag> selectedTags = new ArrayList<>();
        SparseBooleanArray checkedPositions = tags.getCheckedItemPositions();
        for (int i = 0; i < tags.getAdapter().getCount(); i++) {
            if (checkedPositions.get(i)) {
                selectedTags.add((Tag) tags.getAdapter().getItem(i));
            }
        }
        return selectedTags;
    }

    private void delete() {
        if (currentNote == null) {
            return;
        }
        Note noteToDelete = new Note();
        noteToDelete.setId(currentNote.getId());
        HelperFactory.getHelper().getNoteDao().delete(noteToDelete);
        finish();
    }
}