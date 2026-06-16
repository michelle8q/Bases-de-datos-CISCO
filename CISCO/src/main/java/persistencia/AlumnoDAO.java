package persistencia;

import entidad.AlumnoEntidad;
import entidad.CarreraEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase de acceso a datos (DAO) que implementa la interfaz {@link IAlumnoDAO}.
 * Se encarga de gestionar todas las operaciones de persistencia en la base de
 * datos relacionadas con la entidad {@link AlumnoEntidad}, como la búsqueda de
 * estudiantes inscritos y la validación de sus credenciales.
 *
 * @author cinca
 */
public class AlumnoDAO implements IAlumnoDAO {

    private IConexionBD conexion;

    /**
     * Constructor de la clase AlumnoDAO.
     *
     * * @param conexion Interfaz encargada de proveer y gestionar la conexión
     * a la base de datos.
     */
    public AlumnoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    /**
     * Busca y recupera la información de un alumno específico a partir de su
     * identificador único (ID). La consulta está restringida a recuperar
     * únicamente alumnos que se encuentren activos/inscritos
     * ({@code esInscrito = true}) y realiza un cruce (JOIN) para obtener los
     * datos de la carrera asociada al alumno.
     *
     * * @param id El identificador único del alumno que se desea buscar en la
     * base de datos.
     * @return Una instancia poblada de {@link AlumnoEntidad} que incluye los
     * datos del alumno y su carrera, o {@code null} si el alumno no existe o no
     * se encuentra inscrito.
     * @throws PersistenciaException Si ocurre un error de conexión o acceso a
     * la base de datos durante la consulta.
     */
    @Override
    public AlumnoEntidad buscarAlumnoPorId(int id) throws PersistenciaException {
        try (Connection conexion = this.conexion.crearConexion()) {
            String sentenciaSQL = """
                                       SELECT a.id, a.nombres, a.apellidoPaterno, a.apellidoMaterno, a.contrasena, a.esInscrito, c.id AS idCarrera,
                                       c.nombre, c.telefono, c.tiempoDiario
                                       FROM Alumnos a INNER JOIN Carreras c 
                                       ON a.idCarrera = c.id WHERE a.id = ? AND a.esInscrito = true;
                                     """;

            PreparedStatement statement = conexion.prepareStatement(sentenciaSQL);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                CarreraEntidad carrera = new CarreraEntidad(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getInt("tiempoDiario")
                );

                return new AlumnoEntidad(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidoPaterno"),
                        rs.getString("apellidoMaterno"),
                        rs.getString("contrasena"),
                        rs.getBoolean("esInscrito"),
                        carrera
                );
            }

            return null;
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos.");
            System.out.println("Motivo del error: " + e.getMessage());
            throw new PersistenciaException(e.getMessage());
        }

    }

    /**
     * Valida si la contraseña proporcionada coincide con la almacenada en la
     * base de datos para un alumno en particular.
     *
     * * @param idAlumno El identificador único del alumno cuyas credenciales
     * se desean verificar.
     * @param contrasena La contraseña en texto plano que se va a comparar con
     * el registro de la base de datos.
     * @return {@code true} si la contraseña coincide con los registros del
     * alumno correspondiente, {@code false} en caso contrario o si el alumno no
     * existe.
     * @throws PersistenciaException Si ocurre un error al ejecutar la
     * validación en la base de datos.
     */
    @Override
    public boolean validarContrasena(int idAlumno, String contrasena) throws PersistenciaException {
        String sql = "SELECT COUNT(*) FROM Alumnos WHERE id = ? AND contrasena = ?;";

        try (Connection con = this.conexion.crearConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAlumno);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al validar la contraseña del alumno: " + e.getMessage());
        }
        return false;
    }

}
