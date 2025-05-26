package my.spring.crud.dao;

import my.spring.crud.models.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDAO {
    List<User> allUsers();
    void addUser(User user);
    void deleteUser(int id);
    void updateUser(int id, User user  );
    User getUser(int id);
}
