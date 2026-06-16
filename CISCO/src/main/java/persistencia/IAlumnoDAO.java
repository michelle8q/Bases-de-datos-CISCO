package persistencia;

import entidad.AlumnoEntidad;

/**
 * Interfaz de Acceso a Datos (DAO) para la entidad Alumno. Define los métodos
 * necesarios para realizar operaciones de lectura y validación directa sobre la
 * tabla de alumnos en la base de datos.
 *
 * * @author cinca
 */
public interface IAlumnoDAO {

    /**
     * Realiza una consulta en la base de datos para recuperar a un alumno por
     * su ID.
     *
     * * @param id Identificador único del alumno.
     * @return El objeto {@link AlumnoEntidad} si es localizado, o {@code null}
     * si no existe.
     * @throws PersistenciaException Si ocurre un error técnico o de
     * conectividad con la base de datos.
     */
    AlumnoEntidad buscarAlumnoPorId(int id) throws PersistenciaException;

    /**
     * Valida si la contraseña proporcionada coincide con el registro guardado
     * en la base de datos para el alumno.
     *
     * * @param idAlumno Identificador único del alumno.
     * @param contrasena Contraseña en texto plano o hash a validar.
     * @return {@code true} si la credencial es válida; {@code false} en caso
     * contrario.
     * @throws PersistenciaException Si se genera un fallo en la consulta SQL.
     */
    boolean validarContrasena(int idAlumno, String contrasena) throws PersistenciaException;
}
