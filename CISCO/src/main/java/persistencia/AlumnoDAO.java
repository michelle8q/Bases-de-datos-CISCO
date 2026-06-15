package persistencia;

import entidad.AlumnoEntidad;
import entidad.CarreraEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cinca
 */
public class AlumnoDAO implements IAlumnoDAO {

    private IConexionBD conexion;

    public AlumnoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

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
