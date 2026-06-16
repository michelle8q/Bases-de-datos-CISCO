package persistencia;

import dto.EstadoEquipoDTO;
import dto.SoftwareDTO;
import entidad.AlumnoEntidad;
import entidad.EquipoEntidad;
import entidad.SoftwareEntidad;
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
    public List<String> obtenerNombresLaboratorios() throws PersistenciaException {
        List<String> laboratorios = new ArrayList<>();
        String sql = "SELECT nombre FROM Laboratorios ORDER BY nombre ASC";

        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                laboratorios.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar laboratorios: " + e.getMessage());
            throw new PersistenciaException("Error al obtener la lista de laboratorios.");
        }
        return laboratorios;
    }

    @Override
    public EstadoEquipoDTO obtenerEstado(String IP) throws PersistenciaException {

        String sql = "SELECT "
        + "    e.numero AS numero_equipo, "
        + "    l.nombre AS laboratorio, "
        + "    e.estado AS estado_equipo, " 
        + "    a.id AS id_alumno, "
        + "    a.nombres, "
        + "    a.apellidoPaterno, "
        + "    a.apellidoMaterno "
        + "FROM Equipos e "
        + "INNER JOIN Laboratorios l ON e.idLaboratorio = l.id "
        + "LEFT JOIN Usos u ON e.id = u.idEquipo AND e.estado = 'Apartado' " 
        + "LEFT JOIN Alumnos a ON u.idAlumno = a.id "
        + "WHERE e.direccionIP = ? "
        + "ORDER BY u.id DESC LIMIT 1;";

        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, IP);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int numeroVisual = rs.getInt("numero_equipo");
                    String laboratorio = rs.getString("laboratorio");
                    String estado = rs.getString("estado_equipo");

                    AlumnoEntidad alumno = null;

                    if (rs.getObject("id_alumno") != null) {
                        alumno = new AlumnoEntidad();
                        alumno.setId(rs.getInt("id_alumno"));

                        String nombres = rs.getString("nombres");
                        String apPaterno = rs.getString("apellidoPaterno");
                        String apMaterno = rs.getString("apellidoMaterno");

                        alumno.setNombres(nombres);
                        alumno.setApellidoPaterno(apPaterno);
                        alumno.setApellidoMaterno(apMaterno);

                        // Si tu modelo requiere setNombreCompleto para pintarlo en la etiqueta:
                        alumno.setNombres(nombres);
                        alumno.setApellidoPaterno(apPaterno);
                        alumno.setApellidoMaterno(apMaterno);
                    }

                    // Enviamos el DTO limpio a la vista
                    return new EstadoEquipoDTO(numeroVisual, laboratorio, estado, alumno);
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar el estado del equipo por IP: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<SoftwareEntidad> obtenerSoftwaresPorEquipo(int idEquipo) throws PersistenciaException {
        List<SoftwareEntidad> listaSoftwares = new ArrayList<>();
        String sql = """
                     SELECT s.id, s.nombre 
                     FROM Softwares s
                     INNER JOIN EquipoSoftware es ON s.id = es.idSoftware
                     WHERE es.idEquipo = ?
                     """;

        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SoftwareEntidad software = new SoftwareEntidad();
                    software.setId(rs.getInt("id"));
                    software.setNombre(rs.getString("nombre"));
                    listaSoftwares.add(software);
                }
            }
            return listaSoftwares;

        } catch (SQLException e) {
            throw new PersistenciaException("Error al consultar los softwares del equipo: " + e.getMessage());
        }
    }

    @Override
    public List<EquipoEntidad> listarTodos() throws PersistenciaException {
        List<EquipoEntidad> lista = new ArrayList<>();

        String sql = "SELECT id, numero, direccionIP, estado, tipo FROM Equipos";

        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EquipoEntidad equipo = new EquipoEntidad();
                equipo.setId(rs.getInt("id"));
                equipo.setNumero(rs.getInt("numero"));
                equipo.setDireccionIP(rs.getString("direccionIP"));
                equipo.setEstado(rs.getString("estado"));
                equipo.setTipo(rs.getString("tipo"));

                lista.add(equipo);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar todos los equipos: " + e.getMessage());
            throw new PersistenciaException("Error en la base de datos al obtener todos los equipos.");
        }

        return lista;
    }
}
