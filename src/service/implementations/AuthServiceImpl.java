package service.implementations;

import model.User;
import repository.UserRepository;
import service.interfaces.AuthService;

public class AuthServiceImpl implements AuthService {
    private UserRepository userRepo = new UserRepository();
    private User currentUser;

    @Override
    public User login(String username, String password) {
        currentUser = userRepo.authenticate(username, password);
        return currentUser;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}