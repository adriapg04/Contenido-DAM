package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.DBConnection;
import users.User;
import users.Admin;
import users.Player;

public class UserDAOImpl implements UserDAO {

    // El metodo createuser inserta un nuevo usuario en la base de datos
    @Override
    public boolean createUser(User user) {
        // Se define la consulta SQL con parametros para insertar en la tabla users
        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";

        // Se abre la conexion a la base de datos y se prepara la sentencia SQL
        try (Connection conexion = DBConnection.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            // Se asigna al primer parametro el valor del nombre del usuario
            sentencia.setString(1, user.getUsername());

            // Se asigna al segundo parametro el valor de la contraseña del usuario
            sentencia.setString(2, user.getPassword());

            // Se determina el rol segun si el usuario tiene permisos de administrador; si true se asigna admin, sino player
            String rol = user.permisosAdmin() ? "admin" : "player";

            // Se asigna al tercer parametro el valor del rol
            sentencia.setString(3, rol);

            // Se ejecuta la consulta y se obtiene el numero de filas afectadas
            int filasAfectadas = sentencia.executeUpdate();

            // Se devuelve true si se afecto al menos una fila, false en caso contrario
            return (filasAfectadas > 0);

        } catch (SQLException e) {
            // Se captura cualquier excepcion SQL que ocurra
            e.printStackTrace();
        }
        // Se devuelve false si hubo algun error
        return false;
    }

    // Metodo getuserbyusername: obtiene un usuario de la base de datos usando su nombre
    @Override
    public User getUserByUsername(String username) {
        // Se define la consulta SQL para seleccionar todos los campos de la tabla "users" donde el username coincida
        String sql = "SELECT * FROM users WHERE username = ?";

        // Se abre la conexion a la base de datos y se prepara la sentencia SQL
        try (Connection conexion = DBConnection.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            // Se asigna al parametro de la consulta el valor del username
            sentencia.setString(1, username);

            // Se ejecuta la consulta y se guarda el resultado en un objeto ResultSet
            ResultSet rs = sentencia.executeQuery();

            // Se verifica si el ResultSet contiene algun registro
            if (rs.next()) {
                // Se obtiene el valor de la columna "password" del registro
                String password = rs.getString("password");

                // Se obtiene el valor de la columna "role" del registro
                String role = rs.getString("role");

                // Si el rol es "admin", se crea y retorna un objeto Admin; de lo contrario, se crea un objeto Player
                if (role.equalsIgnoreCase("admin")) {
                    return new Admin(username, password);
                } else {
                    return new Player(username, password);
                }
            }
        } catch (SQLException e) {
            // Se captura cualquier error SQL
            e.printStackTrace();
        }
        // Se devuelve null si no se encontro el usuario o si hubo algun error
        return null;
    }

    // Metodo updateuser: actualiza la informacion de un usuario en la base de datos usando su nombre
    @Override
    public boolean updateUser(User user) {
        // Se define la consulta SQL para actualizar el password y el rol de un usuario identificado por su username
        String sql = "UPDATE users SET password = ?, role = ? WHERE username = ?";

        // Se abre la conexion a la base de datos y se prepara la sentencia SQL
        try (Connection conexion = DBConnection.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            // Se asigna al primer parametro el nuevo password obtenido del objeto user
            sentencia.setString(1, user.getPassword());

            // Se determina el rol segun si el usuario tiene permisos de admin; si true se asigna admin, sino player
            String rol = user.permisosAdmin() ? "admin" : "player";

            // Se asigna al segundo parametro el valor del rol determinado
            sentencia.setString(2, rol);

            // Se asigna al tercer parametro el username del usuario, que sirve para identificar el registro a actualizar
            sentencia.setString(3, user.getUsername());

            // Se ejecuta la sentencia SQL y se obtiene el numero de filas afectadas por la actualizacion
            int filasAfectadas = sentencia.executeUpdate();

            // Se devuelve true si se actualizo al menos un registro, false en caso contrario
            return (filasAfectadas > 0);
        } catch (SQLException e) {
            // Se captura cualquier error SQL
            e.printStackTrace();
        }
        // Se devuelve false si ocurrio algun error
        return false;
    }

    // Metodo deleteuser: elimina un usuario de la base de datos usando su nombre
    @Override
    public boolean deleteUser(String username) {
        // Se define la consulta SQL para eliminar el registro de la tabla "users" donde el nombre coincida con el parametro
        String sql = "DELETE FROM users WHERE username = ?";

        // Se abre la conexion a la base de datos y se prepara la sentencia SQL
        try (Connection conexion = DBConnection.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            // Se asigna al parametro el valor del nombre del usuario a eliminar
            sentencia.setString(1, username);

            // Se ejecuta la sentencia SQL y se obtiene el numero de filas afectadas (eliminadas)
            int filasAfectadas = sentencia.executeUpdate();

            // Se devuelve true si se elimino al menos un registro, false en caso contrario
            return (filasAfectadas > 0);
        } catch (SQLException e) {
            // Se captura cualquier error SQL
            e.printStackTrace();
        }
        // Se devuelve false en caso de error
        return false;
    }
}
