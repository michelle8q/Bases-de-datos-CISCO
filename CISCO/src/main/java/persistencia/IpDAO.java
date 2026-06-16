package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de acceso a datos (DAO) que implementa la interfaz {@link IIpDAO}. Se
 * encarga de gestionar las consultas a la base de datos relacionadas con la
 * identificación y el tipo de los equipos a través de su dirección IP.
 *
 * @author piña
 */
public class IpDAO implements IIpDAO {

    private IConexionBD conexion;

    /**
     * Constructor de la clase IpDAO.
     *
     * * @param conexion Interfaz encargada de proveer y gestionar la conexión
     * a la base de datos.
     */
    public IpDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    /**
     * Consulta la base de datos para obtener el tipo de pantalla o rol asignado
     * a un equipo específico, identificándolo a través de su dirección IP.
     *
     * * @param ip La dirección IP del equipo que se desea consultar.
     * @return Una cadena de texto con el tipo de equipo (por ejemplo, el rol de
     * la pantalla), o {@code null} si no se encuentra ningún equipo registrado
     * con esa IP.
     * @throws PersistenciaException Si ocurre un error al intentar conectar o
     * consultar la base de datos.
     */
    @Override
    public String obtenerPantallaPorIP(String ip) throws PersistenciaException {
        String sql = "SELECT tipo FROM equipos WHERE direccionIP = ?";

        try (Connection conexion = this.conexion.crearConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, ip);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tipo");
                }
            }

            return null;

        } catch (SQLException e) {
            System.out.println("Error al conectar o consultar la base de datos.");
            System.out.println("Motivo del error: " + e.getMessage());
            throw new PersistenciaException(e.getMessage());
        }
    }

}
