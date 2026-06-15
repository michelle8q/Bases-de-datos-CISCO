
package persistencia;

/**
 *
 * @author piña
 */
public interface IIpDAO {
    
    public String obtenerPantallaPorIP(String ip) throws PersistenciaException;
    
}
