
package persistencia;

import entidad.AlumnoEntidad;

/**
 *
 * @author cinca
 */
public interface IAlumnoDAO {
    AlumnoEntidad buscarAlumnoPorId(int id) throws PersistenciaException;
    
}
