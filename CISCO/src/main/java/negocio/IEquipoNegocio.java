
package negocio;

import dto.EstadoEquipoDTO;

/**
 *
 * @author piña
 */
public interface IEquipoNegocio {
    
    EstadoEquipoDTO obtenerEstadoEquipo(String ip) throws NegocioException;
    
}
