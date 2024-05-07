package org.example.service.user;

import org.example.base.service.BaseService;
import org.example.model.user.User;

import java.sql.SQLException;

public interface UserService extends BaseService<User, Long> {
    User findByUsername(String username) ;

    String getUserType(Long id);

}
