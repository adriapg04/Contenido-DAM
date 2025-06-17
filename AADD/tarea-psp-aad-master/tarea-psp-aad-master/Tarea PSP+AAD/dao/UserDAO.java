package dao;

import users.User;

/**
 * Interfaz que define las operaciones CRUD para la entidad User.
 */
public interface UserDAO {
    boolean createUser(User user);
    User getUserByUsername(String username);
    boolean updateUser(User user);
    boolean deleteUser(String username);
}
