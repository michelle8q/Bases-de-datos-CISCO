package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que implementa la interfaz {@link IConexionBD} para establecer y
 * gestionar la conexión con la base de datos relacional (MySQL). Contiene de
 * forma centralizada las credenciales y la cadena de conexión necesarias para
 * que los objetos DAO interactúen con la base de datos del sistema.
 *
 * @author cinca
 */
public class ConexionBD implements IConexionBD {

    final String SERVER = "localhost";
    final String BASE_DATOS = "Cisco";
    private final String CADENA_CONEXION = "jdbc:mysql://" + SERVER + "/" + BASE_DATOS;
    final String USUARIO = "root";
    final String CONTRASEÑA = "michelle";

    /**
     * Establece y devuelve una nueva conexión física con la base de datos
     * utilizando el controlador JDBC y las credenciales preconfiguradas.
     *
     * * @return Una instancia de {@link Connection} abierta y lista para ser
     * utilizada en consultas.
     * @throws SQLException Si ocurre un error al intentar establecer la
     * conexión, como credenciales inválidas, base de datos no disponible o
     * problemas de red.
     */
    @Override
    public Connection crearConexion() throws SQLException {
        Connection conexion = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASEÑA);
        return conexion;
    }
}
