package persistencia;

import entidad.EquipoEntidad;
import java.util.List;

/**
 *
 * @author piña
 */
public interface IEquipoDAO {

    public String obtenerNumeroEquipo(String IP) throws PersistenciaException;

    public String obtenerLaboratorio(String IP) throws PersistenciaException;

    public String obtenerEstado(String IP) throws PersistenciaException;

    public int obtenerIDAlumnoApartado(String IP) throws PersistenciaException;

    public List<EquipoEntidad> buscarEquipos(String nombreLaboratorio, String filtro, int limite, int pagina) throws PersistenciaException;

    public int contarEquipos(String nombreLaboratorio, String filtro) throws PersistenciaException;

    public void actualizarEstado(int idEquipo, String nuevoEstado) throws PersistenciaException;
    
    public List<String> obtenerNombresLaboratorios() throws PersistenciaException;

}
