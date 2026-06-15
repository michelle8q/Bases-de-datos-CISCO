package persistencia;

import entidad.EquipoEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author piña
 */
public class EquipoDAO implements IEquipoDAO {

    private IConexionBD conexion;

    public EquipoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public String obtenerNumeroEquipo(String IP) throws PersistenciaException {
        String sql = "SELECT numero FROM equipos WHERE direccionIP = ?";

        try (Connection conexion = this.conexion.crearConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, IP);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {

                    return rs.getString("numero");
                }
            }

            return null;

        } catch (SQLException e) {
            System.err.println("Error al obtener el número de equipo por IP: " + e.getMessage());
            throw new PersistenciaException("Error en la base de datos al buscar el equipo.");
        }
    }

    @Override
    public String obtenerLaboratorio(String IP) throws PersistenciaException {
        String sql = """
                     SELECT 
                         p.nombre AS nombre_plantel,
                         l.nombre AS nombre_laboratorio
                     FROM Equipos e
                     INNER JOIN Laboratorios l ON e.idLaboratorio = l.id
                     INNER JOIN Planteles p ON l.idPlantel = p.id
                     WHERE e.direccionIP = ?;
                     """;

        try (Connection conexion = this.conexion.crearConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, IP);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {

                    return rs.getString("Laboratorio");
                }
            }

            return null;

        } catch (SQLException e) {
            System.err.println("Error al obtener el número de equipo por IP: " + e.getMessage());
            throw new PersistenciaException("Error en la base de datos al buscar el equipo.");
        }
    }

    @Override
    public String obtenerEstado(String IP) throws PersistenciaException {
        String sql = """
                     SELECT estado FROM equipos WHERE direccionIP = ?
                     """;

        try (Connection conexion = this.conexion.crearConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, IP);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {

                    return rs.getString("estado");
                }
            }

            return null;

        } catch (SQLException e) {
            System.err.println("Error al obtener el número de equipo por IP: " + e.getMessage());
            throw new PersistenciaException("Error en la base de datos al buscar el equipo.");
        }
    }

   
    @Override
    public int obtenerIDAlumnoApartado(String IP) throws PersistenciaException {
       String sql = """
                     SELECT 
                         a.id AS id_alumno
                     FROM Usos u
                     INNER JOIN Alumnos a ON u.idAlumno = a.id
                     INNER JOIN Equipos e ON u.idEquipo = e.id
                     WHERE e.direccionIP = ? 
                       AND e.estado = 'Apartado'
                       AND u.fechaHoraApartado >= DATE_SUB(NOW(), INTERVAL 1 MINUTE);
                     """;

        try (Connection conexion = this.conexion.crearConexion(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, IP);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {

                    return rs.getInt("IDAlumno");
                }
            }

            return -1;

        } catch (SQLException e) {
            System.err.println("Error al obtener el número de equipo por IP: " + e.getMessage());
            throw new PersistenciaException("Error en la base de datos al buscar el equipo.");
        }

}
