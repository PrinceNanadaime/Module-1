package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util util = new Util();
    private long idCounter = 1;
    private final List<User> userList = new LinkedList<>();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS testTable" +
                    "(id LONG not NULL, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " age TINYINT) ");
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
        User user = new User(name, lastName, age);
        user.setId((idCounter));
        userList.add(user);
        try (Connection connection = util.getConnection(); PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO testTable (id, first, last, age) VALUES (?,?,?,?);")){
            statement.setLong(1,user.getId());
            statement.setString(2,name);
            statement.setString(3,lastName);
            statement.setByte(4,age);
            statement.executeUpdate();
            idCounter++;
            System.out.println("User с именем – "+ name +" добавлен в базу данных!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String name = null,last = null;
        for (User user:getAllUsers()) {
            if(user.getId() == id) {
                name = user.getName();
                last = user.getLastName();
                userList.remove(user);
                break;
            }
        }
        try (Connection connection = util.getConnection();
             PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM testTable WHERE id = ?")){
            statement.setLong(1,id);
            statement.executeUpdate();
            System.out.println("\nUser " + name + " " + last + " is removed!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public void cleanUsersTable() {
        userList.clear();
        try (Connection connection = util.getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from testTable where id = ?")){
            for (User user:getAllUsers()){
                statement.setLong(1,user.getId());
                statement.executeUpdate();
            }
            System.out.println("\nUsers' table was cleaned!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
