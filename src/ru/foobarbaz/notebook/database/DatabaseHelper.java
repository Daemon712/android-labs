package ru.foobarbaz.notebook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import ru.foobarbaz.notebook.dao.NoteDao;
import ru.foobarbaz.notebook.dao.NoteTagDao;
import ru.foobarbaz.notebook.dao.TagDao;
import ru.foobarbaz.notebook.model.Note;
import ru.foobarbaz.notebook.model.NoteTag;
import ru.foobarbaz.notebook.model.Tag;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "notebook.db";
    private static final int DATABASE_VERSION = 1;

    private NoteDao noteDao;
    private TagDao tagDao;
    private NoteTagDao noteTagDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Note.class);
            TableUtils.createTable(connectionSource, Tag.class);
            TableUtils.createTable(connectionSource, NoteTag.class);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Note.class, true);
            TableUtils.dropTable(connectionSource, Tag.class, true);
            TableUtils.dropTable(connectionSource, NoteTag.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public NoteDao getNoteDao() {
        if (noteDao == null) {
            try {
                noteDao = new NoteDao(getConnectionSource(), Note.class);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return noteDao;
    }

    public TagDao getTagDao() {
        if (tagDao == null) {
            try {
                tagDao = new TagDao(getConnectionSource(), Tag.class);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return tagDao;
    }

    public NoteTagDao getNoteTagDao() {
        if (noteTagDao == null) {
            try {
                noteTagDao = new NoteTagDao(getConnectionSource(), NoteTag.class);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return noteTagDao;
    }
}