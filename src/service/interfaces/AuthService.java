package service.interfaces;

import model.User;

public interface AuthService {
    User login(String username, String password);
    User getCurrentUser();
}
