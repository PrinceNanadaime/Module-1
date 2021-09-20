package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static SessionFactory getSessionFactory() {
        StandardServiceRegistryBuilder registryBuilder =
                new StandardServiceRegistryBuilder();

        Map<String, String> settings = new HashMap<>();
        settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        settings.put("hibernate.connection.password", "root");
        settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/mysql");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.show_sql", "true");

        registryBuilder.applySettings(settings);
        MetadataSources sources = new MetadataSources(registryBuilder.build())
                .addAnnotatedClass(User.class);
        return sources.buildMetadata().buildSessionFactory();
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql",
                    "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
