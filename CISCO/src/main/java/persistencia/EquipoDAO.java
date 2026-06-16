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
 * Clase de acceso a datos (DAO) que implementa la interfaz {@link IEquipoDAO}.
 * Se encarga de gestionar todas las operaciones de persistencia en la base de
 * datos relacionadas con la entidad {@link EquipoEntidad}, como la consulta de
 * información de equipos por IP, actualización de estados, búsqueda paginada y
 * relación con laboratorios y softwares.
 *
 * @author piña
 */
public class EquipoDAO implements IEquipoDAO {

    private IConexionBD conexion;

    /**
     * Constructor de la clase EquipoDAO.
     *
     * * @param conexion Interfaz encargada de proveer y gestionar la conexión
     * a la base de datos.
     */
    public EquipoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    /**
     * Obtiene el número identificador de un equipo a partir de su dirección IP.
     *
     * * @param IP La dirección IP del equipo que se desea consultar.
     * @return El número del equipo como cadena de texto, o {@code null} si no
     * se encuentra.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta
     * en la base de datos.
     */
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

    /**
     * Obtiene el nombre del laboratorio y del plantel al que pertenece un
     * equipo, identificándolo por su dirección IP.
     *
     * * @param IP La dirección IP del equipo.
     * @return Una cadena de texto concatenando el nombre del laboratorio y el
     * plantel (ej. "Laboratorio 1 Campus Norte"), o {@code null} si no se
     * encuentra.
     * @throws PersistenciaException Si ocurre un error de acceso a la base de
     * datos.
     */
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

    /**
     * Obtiene el identificador (ID) del alumno que tiene apartado un equipo
     * específico, siempre y cuando el estado del equipo sea 'Apartado' y el
     * apartado se haya realizado en el último minuto.
     *
     * * @param IP La dirección IP del equipo consultado.
     * @return El ID del alumno si cumple con las condiciones, o {@code -1} en
     * caso contrario.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta.
     */
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

    /**
     * Obtiene una lista paginada de equipos pertenecientes a un laboratorio
     * específico. Permite filtrar los resultados mediante coincidencias en la
     * dirección IP o el número del equipo.
     *
     * * @param nombreLaboratorio El nombre exacto del laboratorio a consultar.
     * @param filtro Cadena de texto para buscar coincidencias (IP o número).
     * @param limite Cantidad máxima de registros a recuperar por página.
     * @param pagina Número de página que se desea visualizar.
     * @return Una lista de {@link EquipoEntidad} con los resultados de la
     * búsqueda.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta.
     */
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

    /**
     * Cuenta el número total de equipos que coinciden con un laboratorio y un
     * filtro específicos. Este método es utilizado principalmente para calcular
     * el total de páginas necesarias en la paginación.
     *
     * * @param nombreLaboratorio El nombre exacto del laboratorio.
     * @param filtro Cadena de texto para filtrar por IP o número de equipo.
     * @return El número total de equipos que cumplen con los criterios.
     * @throws PersistenciaException Si ocurre un error al realizar el conteo en
     * la base de datos.
     */
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

    /**
     * Actualiza el estado (ej. Disponible, Apartado, En Uso, Mantenimiento) de
     * un equipo específico.
     *
     * * @param idEquipo El identificador único del equipo a actualizar.
     * @param nuevoEstado El nuevo estado que se le asignará al equipo.
     * @throws PersistenciaException Si ocurre un error al ejecutar la
     * actualización.
     */
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

    /**
     * Recupera una lista con los nombres de todos los laboratorios registrados
     * en la base de datos, ordenados alfabéticamente.
     *
     * * @return Una lista de cadenas de texto con los nombres de los
     * laboratorios.
     * @throws PersistenciaException Si ocurre un error al acceder a la base de
     * datos.
     */
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

    /**
     * Obtiene un reporte detallado del estado actual de un equipo buscando por
     * su dirección IP. El reporte incluye información del equipo, el
     * laboratorio al que pertenece y, si se encuentra en uso o apartado, los
     * datos del alumno responsable.
     *
     * * @param IP La dirección IP del equipo a consultar.
     * @return Un objeto {@link EstadoEquipoDTO} con la información del estado,
     * o {@code null} si el equipo no existe.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta.
     */
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

    /**
     * Recupera la lista de programas de software que se encuentran instalados
     * en un equipo en particular.
     *
     * * @param idEquipo El identificador único del equipo.
     * @return Una lista de entidades {@link SoftwareEntidad} que representan
     * los programas instalados.
     * @throws PersistenciaException Si ocurre un error al realizar la consulta.
     */

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

    /**
     * Obtiene una lista completa con todos los equipos registrados en la base
     * de datos, sin aplicar ningún tipo de filtro o paginación.
     *
     * * @return Una lista que contiene todos los objetos
     * {@link EquipoEntidad}.
     * @throws PersistenciaException Si ocurre un error al obtener los registros
     * de la base de datos.
     */
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
