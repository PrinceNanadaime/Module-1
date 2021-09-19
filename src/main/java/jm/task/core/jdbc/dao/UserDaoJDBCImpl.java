package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS testTable" +
                    "( id INTEGER(1) NOT NULL AUTO_INCREMENT," +
                    "  first VARCHAR(255)," +
                    "  last VARCHAR(255)," +
                    "  age SMALLINT ," +
                    "  CONSTRAINT pk PRIMARY KEY (id)" +
                    ");");
            System.out.println("Table was created!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS testTable");
            System.out.println("Table was dropped!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.getConnection(); PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO testTable (first, last, age) VALUES (?,?,?);")){
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setByte(3,age);
            statement.executeUpdate();
            System.out.println("User с именем – "+ name +" добавлен в базу данных!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.getConnection();
             PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM testTable WHERE id = ?")){
            statement.setLong(1,id);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new LinkedList<>();
        String sql = "SELECT * FROM testTable ";

        try(Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("first"));
                user.setLastName(resultSet.getString("last"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try(Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE testTable");
            System.out.println("Table is cleaned!");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
