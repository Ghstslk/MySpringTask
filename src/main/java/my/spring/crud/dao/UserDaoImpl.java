package my.spring.crud.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import my.spring.crud.models.Role;
import my.spring.crud.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> allUsers() {
        return em.createQuery("from User ", User.class).getResultList();
    }

    @Override
    public void deleteUser(int id) {
        em.remove(em.getReference(User.class, id));
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        User UserToBeUpdated = em.find(User.class, id);
        UserToBeUpdated.setName(updatedUser.getName());
        UserToBeUpdated.setLastname(updatedUser.getLastname());
        UserToBeUpdated.setAge(updatedUser.getAge());
        em.merge(UserToBeUpdated);
    }

    @Override
    public User getUser(int id) {
        return em.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
       em.persist(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void addRole(User user) {
        Role defaultRole = em.find(Role.class, 1);
        user.getRoles().add(defaultRole);
        em.merge(user);
    }

}
