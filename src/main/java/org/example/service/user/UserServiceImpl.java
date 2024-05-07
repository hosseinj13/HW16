package org.example.service.user;


import org.example.base.service.BaseServiceImpl;
import org.example.enums.UserTypes;
import org.example.model.user.User;
import org.example.repository.user.UserRepository;
import org.hibernate.SessionFactory;

import java.sql.SQLException;

public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }


    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserTypes getUserType(Long id) {
        return repository.getUserType(id);
    }
}
