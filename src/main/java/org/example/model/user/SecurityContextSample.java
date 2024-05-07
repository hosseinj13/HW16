package org.example.model.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class SecurityContextSample {
    private Map<String, User> users;
    private PasswordEncoder passwordEncoder;

    public SecurityContextSample() {
        this.users = new HashMap<>();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.put(user.getUsername(), user);
        System.out.println("User signed up successfully.");
    }

    public boolean login(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("User not found.");
        }
        return false;
    }
}
