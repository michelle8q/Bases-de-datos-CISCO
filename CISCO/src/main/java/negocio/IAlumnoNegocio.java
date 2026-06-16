package negocio;

import entidad.AlumnoEntidad;

/**
 * Interfaz que define las operaciones del servicio de lógica de negocio para la
 * gestión de alumnos. Proporciona los métodos necesarios para la autenticación
 * y consulta de información de los estudiantes.
 *
 * * @author cinca piña
 */
public interface IAlumnoNegocio {

    /**
     * Busca y recupera la información detallada de un alumno mediante su
     * identificador único.
     *
     * * @param id Identificador único del alumno a buscar.
     * @return Un objeto {@link AlumnoEntidad} que contiene la información del
     * alumno.
     * @throws NegocioException Si ocurre un error en la capa de negocio o el
     * alumno no existe.
     */
    AlumnoEntidad buscarPorId(int id) throws NegocioException;

    /**
     * Valida si las credenciales de acceso (ID y contraseña) proporcionadas por
     * un alumno son correctas.
     *
     * * @param idAlumno Identificador único del alumno que intenta
     * autenticarse.
     * @param contrasena Contraseña provista por el usuario para su
     * verificación.
     * @return {@code true} si las credenciales son válidas y coinciden;
     * {@code false} en caso contrario.
     * @throws NegocioException Si ocurre un error durante el proceso de
     * validación en la base de datos.
     */
    boolean verificarCredencialesAlumno(int idAlumno, String contrasena) throws NegocioException;
}
