package persistencia;

import dto.EstadoEquipoDTO;
import entidad.AlumnoEntidad;
import entidad.EquipoEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

                    String laboratorio = rs.getString("nombre_laboratorio");
                    String plantel = rs.getString("nombre_plantel");
                    return laboratorio + " " + plantel;
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

                    return rs.getInt("id_alumno");
                }
            }

            return -1;

        } catch (SQLException e) {
            System.err.println("Error al obtener el número de equipo por IP: " + e.getMessage());
            throw new PersistenciaException("Error en la base de datos al buscar el equipo.");
        }

    }

    @Override
    public List<EquipoEntidad> buscarEquipos(String nombreLaboratorio, String filtro, int limite, int pagina) throws PersistenciaException {
        List<EquipoEntidad> lista = new ArrayList<>();

        int offset = (pagina - 1) * limite;

        String sql = """
                     SELECT e.id, e.numero, e.direccionIP, e.estado, e.tipo
                     FROM Equipos e
                     INNER JOIN Laboratorios l ON e.idLaboratorio = l.id
                     WHERE l.nombre = ? 
                       AND (e.direccionIP LIKE ? OR CAST(e.numero AS CHAR) LIKE ?)
                     LIMIT ? OFFSET ?
                     """;

        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreLaboratorio);
            ps.setString(2, "%" + filtro + "%");
            ps.setString(3, "%" + filtro + "%");
            ps.setInt(4, limite);
            ps.setInt(5, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EquipoEntidad equipo = new EquipoEntidad();
                    equipo.setId(rs.getInt("id"));
                    equipo.setNumero(rs.getInt("numero"));
                    equipo.setDireccionIP(rs.getString("direccionIP"));
                    equipo.setEstado(rs.getString("estado"));
                    equipo.setTipo(rs.getString("tipo"));
                    lista.add(equipo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar equipos con paginacion: " + e.getMessage());
            throw new PersistenciaException("Error al buscar la lista de equipos.");
        }
        return lista;
    }

    @Override
    public int contarEquipos(String nombreLaboratorio, String filtro) throws PersistenciaException {
        String sql = """
                     SELECT COUNT(e.id) AS total
                     FROM Equipos e
                     INNER JOIN Laboratorios l ON e.idLaboratorio = l.id
                     WHERE l.nombre = ? 
                       AND (e.direccionIP LIKE ? OR CAST(e.numero AS CHAR) LIKE ?)
                     """;

        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreLaboratorio);
            ps.setString(2, "%" + filtro + "%");
            ps.setString(3, "%" + filtro + "%");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al contar equipos: " + e.getMessage());
            throw new PersistenciaException("Error al contar los equipos para la paginación.");
        }
        return 0;
    }

    @Override
    public void actualizarEstado(int idEquipo, String nuevoEstado) throws PersistenciaException {
        String sql = "UPDATE Equipos SET estado = ? WHERE id = ?";
        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idEquipo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado: " + e.getMessage());
            throw new PersistenciaException("Error al actualizar el estado del equipo.");
        }
    }

    @Override
    public String obtenerEstado(String IP) throws PersistenciaException {
        String sql = "SELECT e.numero_equipo, e.laboratorio, e.estado, "
                + "a.id_alumno, a.nombre, a.apellidoPaterno, a.apellidoMaterno "
                +"FROM equipos e "
                + "LEFT JOIN apartados ap ON e.id = ap.id_equipo AND ap.activo = true "
                + "LEFT JOIN alumnos a ON ap.id_alumno = a.id_alumno "
                + "WHERE e.ip_equipo = ?";

        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, IP);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int numero = rs.getInt("numero_equipo");
                    String laboratorio = rs.getString("laboratorio");
                    String estadoEquipo = rs.getString("estado");

                    AlumnoEntidad alumno = null;
                    String nombreAlumno = rs.getString("nombre");
                    if (nombreAlumno != null) {
                        alumno = new AlumnoEntidad();
                        alumno.setId(rs.getInt("id_alumno"));
                        alumno.setNombres(nombreAlumno);
                        alumno.setApellidoPaterno(laboratorio);
                    }

                    return new EstadoEquipoDTO(numero, laboratorio, estadoEquipo, alumno);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar la base de datos", e);
        }
        return null;
    }
}



