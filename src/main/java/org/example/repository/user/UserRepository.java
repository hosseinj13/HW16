package org.example.repository.user;


import org.example.base.repository.BaseRepository;
import org.example.enums.UserTypes;
import org.example.model.user.User;

public interface UserRepository extends BaseRepository<User, Long> {

    User findByUsername(String username);

    UserTypes getUserType(Long id);
}
