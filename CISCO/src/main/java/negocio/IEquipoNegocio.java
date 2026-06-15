package negocio;

import dto.EstadoEquipoDTO;
import dto.ListarEquipoDTO;
import dto.SoftwareDTO;
import entidad.SoftwareEntidad;
import java.util.List;

/**
 *
 * @author piña luis
 */
public interface IEquipoNegocio {

    EstadoEquipoDTO obtenerEstadoEquipo(String ip) throws NegocioException;
    

    List<ListarEquipoDTO> buscarEquiposPaginados(String nombreLaboratorio, String filtro, int limite, int pagina) throws Exception;

    int obtenerTotalPaginas(String nombreLaboratorio, String filtro, int limite) throws Exception;

    void cambiarEstadoEquipo(int idEquipo, String nuevoEstado) throws Exception;

    List<String> obtenerNombresLaboratorios() throws Exception;

    List<SoftwareDTO> obtenerSoftwaresPorEquipo(int idEquipo) throws Exception;
    

}
