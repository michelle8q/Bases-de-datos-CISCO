package negocio;


import dto.EstadoEquipoDTO;
import dto.ListarEquipoDTO;
import dto.SoftwareDTO;
import entidad.EquipoEntidad;
import entidad.SoftwareEntidad;
import java.util.ArrayList;
import java.util.List;
import persistencia.IEquipoDAO;
import persistencia.PersistenciaException;


/**
 *
 * @author piña luis
 */
public class EquipoNegocio implements IEquipoNegocio {

    private final IEquipoDAO equipoDAO;

    // Inyección de dependencias: le pasamos el DAO por el constructor
    public EquipoNegocio(IEquipoDAO equipoDAO) {
        this.equipoDAO = equipoDAO;
    }

    @Override
    public List<ListarEquipoDTO> buscarEquiposPaginados(String nombreLaboratorio, String filtro, int limite, int pagina) throws Exception {
        try {
            List<EquipoEntidad> entidades = equipoDAO.buscarEquipos(nombreLaboratorio, filtro, limite, pagina);

            List<ListarEquipoDTO> listaDTOs = new ArrayList<>();

            for (EquipoEntidad entidad : entidades) {
                ListarEquipoDTO dto = new ListarEquipoDTO();
                dto.setId(entidad.getId());
                dto.setNumeroComputadora(entidad.getNumero());
                dto.setDireccionIP(entidad.getDireccionIP());
                dto.setEstado(entidad.getEstado());

                listaDTOs.add(dto);
            }

            return listaDTOs;

        } catch (Exception e) {
            throw new Exception("Error al procesar la lista de equipos: " + e.getMessage());
        }
    }

    @Override
    public int obtenerTotalPaginas(String nombreLaboratorio, String filtro, int limite) throws Exception {
        try {
            int totalRegistros = equipoDAO.contarEquipos(nombreLaboratorio, filtro);

            return (int) Math.ceil((double) totalRegistros / limite);

        } catch (Exception e) {
            throw new Exception("Error al calcular el paginado: " + e.getMessage());
        }
    }

    @Override
    public void cambiarEstadoEquipo(int idEquipo, String nuevoEstado) throws Exception {
        try {

            equipoDAO.actualizarEstado(idEquipo, nuevoEstado);
        } catch (Exception e) {
            throw new Exception("Error al procesar el cambio de estado: " + e.getMessage());
        }
    }

    @Override
    public List<String> obtenerNombresLaboratorios() throws Exception {
        try {
            return equipoDAO.obtenerNombresLaboratorios();
        } catch (Exception e) {
            throw new Exception("Error al cargar la lista de laboratorios: " + e.getMessage());
        }
    }


    @Override
    public EstadoEquipoDTO obtenerEstadoEquipo(String ip) throws NegocioException {
        try {
            return equipoDAO.obtenerEstado(ip);
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    @Override
    public List<SoftwareDTO> obtenerSoftwaresPorEquipo(int idEquipo) throws Exception {
        try {
            List<SoftwareEntidad> entidades = equipoDAO.obtenerSoftwaresPorEquipo(idEquipo);

            List<SoftwareDTO> dtos = new ArrayList<>();
            for (SoftwareEntidad entidad : entidades) {
                SoftwareDTO dto = new SoftwareDTO();
                dto.setId(entidad.getId());
                dto.setNombre(entidad.getNombre());
                dtos.add(dto);
            }

            return dtos;

        } catch (Exception e) {
            throw new Exception("Error al obtener los softwares del equipo: " + e.getMessage());
        }
    }
}
