package ru.foobarbaz.notebook.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import ru.foobarbaz.notebook.database.HelperFactory;
import ru.foobarbaz.notebook.model.Note;
import ru.foobarbaz.notebook.model.NoteTag;
import ru.foobarbaz.notebook.model.Tag;

import java.sql.SQLException;
import java.util.List;

public class NoteTagDao extends BaseDaoImpl<NoteTag, Integer> {

    public NoteTagDao(ConnectionSource connectionSource, Class<NoteTag> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public int create(NoteTag noteTag) {
        try {
            return super.create(noteTag);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public NoteTag queryForId(Integer id) {
        try {
            return super.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public int update(NoteTag noteTag) {
        try {
            return super.update(noteTag);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public int delete(NoteTag noteTag) {
        try {
            return super.delete(noteTag);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Tag> getTagsForNote(Note note) {
        try {
            PreparedQuery<Tag> preparedQuery = getTagPreparedQuery();
            preparedQuery.setArgumentHolderValue(0, note);
            return HelperFactory.getHelper().getTagDao().query(preparedQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private PreparedQuery<Tag> getTagPreparedQuery() {
        try {
            QueryBuilder<NoteTag, Integer> noteTagBuilder = queryBuilder();
            noteTagBuilder.selectColumns("tag_id");
            SelectArg selectArg = new SelectArg();
            noteTagBuilder.where().eq("note_id", selectArg);
            QueryBuilder<Tag, Integer> tagBuilder = HelperFactory.getHelper().getTagDao().queryBuilder();
            tagBuilder.where().in("id", noteTagBuilder);
            return tagBuilder.prepare();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Note> getNotesForTag(Tag tag) {
        try {
            PreparedQuery<Note> preparedQuery = getNotePreparedQuery();
            preparedQuery.setArgumentHolderValue(0, tag);
            return HelperFactory.getHelper().getNoteDao().query(preparedQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private PreparedQuery<Note> getNotePreparedQuery() {
        try {
            QueryBuilder<NoteTag, Integer> noteTagBuilder = queryBuilder();
            noteTagBuilder.selectColumns("note_id");
            SelectArg selectArg = new SelectArg();
            noteTagBuilder.where().eq("tag_id", selectArg);
            QueryBuilder<Note, Integer> noteBuilder = HelperFactory.getHelper().getNoteDao().queryBuilder();
            noteBuilder.where().in("id", noteTagBuilder);
            return noteBuilder.prepare();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void deleteTagsForNote(Note note) {
        try {
            DeleteBuilder<NoteTag, Integer> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq("note_id", note.getId());
            deleteBuilder.delete();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
