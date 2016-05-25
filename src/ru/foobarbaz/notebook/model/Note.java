package ru.foobarbaz.notebook.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "notes")
public class Note {

    @DatabaseField(generatedId =  true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "created_date", canBeNull = false)
    private String createdDate;

    @DatabaseField(columnName = "title", canBeNull = false)
    private String title;

    @DatabaseField(columnName = "body", canBeNull = false)
    private String body;

    public Note() {
    }

    public Note(int id, String createdDate, String title, String body) {
        this.id = id;
        this.createdDate = createdDate;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return getCreatedDate() + "\n" +
                getTitle() + "\n" +
                getBody();
    }
}