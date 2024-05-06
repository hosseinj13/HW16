package org.example.repository.user;


import org.example.base.repository.BaseRepository;
import org.example.model.user.User;

import java.sql.SQLException;

public interface UserRepository extends BaseRepository<User, Long> {

    User findByUsername(String username);
}
