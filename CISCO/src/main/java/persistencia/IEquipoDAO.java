
package persistencia;

import entidad.EquipoEntidad;

/**
 *
 * @author piña
 */
public interface IEquipoDAO {
        
    public String obtenerNumeroEquipo(String IP) throws PersistenciaException;
    
    public String obtenerLaboratorio (String IP) throws PersistenciaException;
    
    public String obtenerEstado(String IP) throws PersistenciaException;
    
    public int obtenerIDAlumnoApartado (String IP) throws PersistenciaException;

}
