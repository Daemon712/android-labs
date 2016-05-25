package ru.foobarbaz.notebook.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import ru.foobarbaz.notebook.model.Tag;

import java.sql.SQLException;
import java.util.List;

public class TagDao extends BaseDaoImpl<Tag, Integer> {

    public TagDao(ConnectionSource connectionSource, Class<Tag> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public int create(Tag tag) {
        try {
            return super.create(tag);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Tag queryForId(Integer id) {
        try {
            return super.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public int update(Tag tag) {
        try {
            return super.update(tag);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public int delete(Tag tag) {
        try {
            return super.delete(tag);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Tag> getAllTags() {
        try {
            return queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}