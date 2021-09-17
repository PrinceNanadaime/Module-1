package jm.task.core.jdbc.util;

import java.sql.Connection;
import com.mysql.cj.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util implements AutoCloseable {

    private Connection connection;

    public Connection getConnection(){
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql",
                    "root", "root");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
