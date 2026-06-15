package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piña
 */
public class IpDAO implements IIpDAO {

    private IConexionBD conexion;

    public IpDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

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
