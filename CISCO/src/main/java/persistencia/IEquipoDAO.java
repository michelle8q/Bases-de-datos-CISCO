
package persistencia;

import entidad.EquipoEntidad;

/**
 *
 * @author piña
 */
public interface IEquipoDAO {
    
    EquipoEntidad optenerEstadoEquipo(String IP) throws PersistenciaException;
    
}
