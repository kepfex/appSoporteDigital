package modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KEVIN
 */
//@ApplicationScoped
public class ConexionDB {

    private static ConexionDB instancia;
    private Connection connection;

    private final String url = "jdbc:postgresql://localhost:5432/db_soporte_digital";
    private final String usuario = "postgres";
    private final String contrasena = "admin";

    private ConexionDB() throws SQLException {
        try {
            // Establece la conexión inicial
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, usuario, contrasena);
//            System.out.println("constructor privado de conexionbd = " + url +" "+ usuario+" "+contrasena);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() throws SQLException {
        // Si la conexión es nula o está cerrada, abre una nueva conexión.
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, usuario, contrasena);
        }
//        System.out.println("base de datos conectada");
        return connection;
    }

    public static ConexionDB getInstance() throws SQLException {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
//        System.out.println("Existe una instancia");
        return instancia;
    }
}
