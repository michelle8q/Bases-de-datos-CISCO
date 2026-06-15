
package negocio;

import entidad.AlumnoEntidad;

/**
 *
 * @author cinca piña
 */
public interface IAlumnoNegocio {
    AlumnoEntidad buscarPorId(int id) throws NegocioException;
    
    boolean verificarCredencialesAlumno(int idAlumno, String contrasena) throws NegocioException;
}
