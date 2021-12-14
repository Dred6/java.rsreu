package ru.rsreu.medicinalPlants.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.rsreu.medicinalPlants.Gears;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class JdbcConstituentsRepository implements ConstituentsRepository{

    private JdbcTemplate jdbc;

    @Autowired
    public JdbcConstituentsRepository (JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Gears> findAll() {
        return jdbc.query("select id, name, type from Gears",
                this::mapRowToConstituents);
    }

    @Override
    public Gears findById(String id){
        return jdbc.queryForObject("select id, name, type from Gears where id=?",
                this::mapRowToConstituents, id);
    }

    @Override
    public Gears save(Gears constituents){
        jdbc.update(
                "insert into Gears (id, name, type) values (?, ?, ?)",
                constituents.getId(),
                constituents.getName(),
                constituents.getType().toString());
        return constituents;
    }

    private Gears mapRowToConstituents(ResultSet rs, int rowNum) throws SQLException{
        return new Gears(
                rs.getString("id"),
                rs.getString("name"),
                Gears.Type.valueOf(rs.getString("type")));
    }
}
