package ru.foobarbaz.notebook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ru.foobarbaz.R;
import ru.foobarbaz.notebook.database.HelperFactory;
import ru.foobarbaz.notebook.model.Tag;

public class TagActivity extends Activity implements View.OnClickListener {

    private Tag currentTag;
    private Button save;
    private Button cancel;
    private Button delete;
    private EditText tagName;
    private ListView notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_tag);

        cancel = (Button) findViewById(R.id.cancelTagButton);
        cancel.setOnClickListener(this);

        save = (Button) findViewById(R.id.saveTagButton);
        save.setOnClickListener(this);

        delete = (Button) findViewById(R.id.deleteTagButton);
        delete.setOnClickListener(this);

        tagName = (EditText) findViewById(R.id.tagName);
        notes = (ListView) findViewById(R.id.noteListView);
        notes.setChoiceMode(AbsListView.CHOICE_MODE_NONE);

        showCurrentTag();
    }

    private void showCurrentTag() {
        Intent intent = getIntent();
        String tagId = intent.getStringExtra("tagId");
        if (tagId == null) {
            return;
        }
        currentTag = HelperFactory.getHelper().getTagDao().queryForId(Integer.parseInt(tagId));
        tagName.setText(currentTag.getName());
        notes.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, HelperFactory.getHelper().getNoteTagDao().getNotesForTag(currentTag)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelTagButton:
                cancel();
                break;
            case R.id.saveTagButton:
                save();
                break;
            case R.id.deleteTagButton:
                delete();
                break;
        }
    }

    private void cancel() {
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, NoteBookMainActivity.class);
        intent.putExtra("pageToShow", "tags");
        startActivity(intent);
    }

    private void save() {
        if (emptyRequiredFields()) {
            return;
        }
        Tag tagToSave = getFilledTag();
        if (currentTag == null) {
            HelperFactory.getHelper().getTagDao().create(tagToSave);
        } else {
            tagToSave.setId(currentTag.getId());
            HelperFactory.getHelper().getTagDao().update(tagToSave);
        }
        startMainActivity();
    }

    private boolean emptyRequiredFields() {
        return tagName.getText().toString().isEmpty();
    }

    private Tag getFilledTag() {
        Tag tag = new Tag();
        tag.setName(tagName.getText().toString());
        return tag;
    }

    private void delete() {
        if (currentTag == null) {
            return;
        }
        Tag tagToDelete = new Tag();
        tagToDelete.setId(currentTag.getId());
        HelperFactory.getHelper().getTagDao().delete(tagToDelete);
        startMainActivity();
    }
}