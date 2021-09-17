package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Jon","Snow", (byte)21);
        userService.saveUser("Nikita","Kotenkov", (byte)20);
        userService.saveUser("Kazuya","Mishima", (byte)42);
        userService.saveUser("Geralt","of Rivia", (byte)91);

        System.out.println("\nAdded users: ");
        for (User user: userService.getAllUsers()) {
            System.out.println(user);
        }

        userService.removeUserById(3);

        System.out.println("\nUsers after removing: ");
        for (User user: userService.getAllUsers()) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        System.out.println("\nUsers after cleaning table: ");
        if (userService.getAllUsers().isEmpty()) {
            System.out.println("Table is empty!\n");
        }

        userService.dropUsersTable();
    }
}
