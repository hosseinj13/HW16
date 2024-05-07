package org.example.repository.user;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.conncetion.SessionFactorySingleton;
import org.example.enums.UserTypes;
import org.example.model.user.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long> implements UserRepository {

    public UserRepositoryImpl(Session session) {
        super(session);
    }


    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User findByUsername(String username) {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        User user = query.uniqueResult();
        session.close();
        return user;
    }

    @Override
    public UserTypes getUserType(Long id) {
        Session session = SessionFactorySingleton.getInstance().openSession();
        Query<String> query = session.createQuery("SELECT u.user_type FROM User u WHERE u.id = :id", String.class);
        query.setParameter("id", id);
        String userType = query.uniqueResult();
        session.close();
        return UserTypes.valueOf(userType);
    }

}
