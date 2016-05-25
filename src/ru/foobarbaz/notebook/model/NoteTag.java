package ru.foobarbaz.notebook.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "notes_tags")
public class NoteTag {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = "note_id")
    private Note note;

    @DatabaseField(foreign = true, columnName = "tag_id")
    private Tag tag;

    public NoteTag() {
    }

    public NoteTag(Note note, Tag tag) {
        this.note = note;
        this.tag = tag;
    }
}
