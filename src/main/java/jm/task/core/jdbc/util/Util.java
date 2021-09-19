package jm.task.core.jdbc.util;

import java.sql.Connection;
import com.mysql.cj.jdbc.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util implements AutoCloseable {

    public static SessionFactory getSessionFactory() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default_class");
        EntityManager entityManager = emf.createEntityManager();
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        return session.getSessionFactory();
    }

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
            if (connection != null &&!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
