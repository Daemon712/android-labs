package ru.foobarbaz.notebook.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import ru.foobarbaz.notebook.model.Note;

import java.sql.SQLException;
import java.util.List;

public class NoteDao extends BaseDaoImpl<Note, Integer> {

    public NoteDao(ConnectionSource connectionSource, Class<Note> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public int create(Note note) {
        try {
            return super.create(note);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Note queryForId(Integer id) {
        try {
            return super.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public int update(Note note) {
        try {
            return super.update(note);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public int delete(Note note) {
        try {
            return super.delete(note);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Note> getAllNotes() {
        try {
            return queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}