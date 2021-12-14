package ru.rsreu.medicinalPlants.data;

import ru.rsreu.medicinalPlants.Gears;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RawJdbcConstituentsRepository implements ConstituentsRepository{

    private DataSource dataSource;

    public RawJdbcConstituentsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Iterable<Gears> findAll() {
        List<Gears> gears = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(
                    "select id, name, type from Gears");
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Gears constituents = new Gears(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Gears.Type.valueOf(resultSet.getString("type")));
                gears.add(constituents);
            }
        } catch (SQLException e) {
            // ??? What should be done here ???
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
        return gears;
    }

    @Override
    public Gears findById(String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(
                    "select id, name, type from Films WHERE id = ?");
            statement.setString( 1, id);
            resultSet = statement.executeQuery();
            Gears gears = null;
            if(resultSet.next()) {
                gears = new Gears(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Gears.Type.valueOf(resultSet.getString("type")));
            }
            return gears;
        } catch (SQLException e) {
            // ??? What should be done here ???
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
        return null;
    }

    @Override
    public Gears save(Gears ingredient) {
        // TODO: I only needed one method for comparison purposes, so
        //       I've not bothered implementing this one (yet).
        return null;
    }
}
