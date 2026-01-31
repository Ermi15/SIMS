package repository;

import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();
    private static final String FILE_PATH = "data/users.txt";

    public UserRepository() {
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        System.out.println("Looking for users file at: " + file.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String role = parts[2].trim();
                    users.add(new User(username, password, role));
                }
            }
            System.out.println("Loaded " + users.size() + " users from file");

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: users.txt file not found!");
            System.out.println("Please create data/users.txt with this content:");
            System.out.println("admin,admin123,admin");
            System.out.println("student1,pass123,student");

        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        if (users.isEmpty()) {
            System.out.println("Adding default users...");
            users.add(new User("admin", "admin123", "admin"));
            users.add(new User("student1", "pass123", "student"));
        }
    }

    public User authenticate(String username, String password) {
        System.out.println("Trying to authenticate: " + username);
        for (User user : users) {
            if (user.checkLogin(username, password)) {
                System.out.println("Login successful for: " + username);
                return user;
            }
        }
        System.out.println("Login failed for: " + username);
        return null;
    }

}
