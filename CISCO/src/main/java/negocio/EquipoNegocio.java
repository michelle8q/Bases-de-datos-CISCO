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
 * Clase que implementa la interfaz {@link IEquipoNegocio}. Se encarga de la
 * lógica de negocio relacionada con la gestión, consulta y modificación del
 * estado de los equipos de cómputo en los laboratorios. Actúa como
 * intermediario entre la capa de presentación y la capa de acceso a datos
 * (DAO), transformando las entidades del sistema en objetos de transferencia de
 * datos (DTO).
 *
 * @author piña luis
 */
public class EquipoNegocio implements IEquipoNegocio {

    private final IEquipoDAO equipoDAO;

    /**
     * Constructor de la clase EquipoNegocio. Aplica el patrón de inyección de
     * dependencias para vincular la capa de acceso a datos.
     *
     * * @param equipoDAO Interfaz encargada de proveer los métodos de
     * persistencia para los equipos.
     */
    public EquipoNegocio(IEquipoDAO equipoDAO) {
        this.equipoDAO = equipoDAO;
    }

    /**
     * Busca y recupera una lista paginada de equipos, filtrada por el nombre
     * del laboratorio y un criterio de búsqueda específico. Convierte las
     * entidades resultantes a DTOs para la vista.
     *
     * * @param nombreLaboratorio El nombre del laboratorio al que pertenecen
     * los equipos.
     * @param filtro Cadena de texto para filtrar los equipos (por ejemplo, por
     * número de computadora o IP).
     * @param limite La cantidad máxima de equipos a devolver en la página
     * actual.
     * @param pagina El número de la página que se desea consultar (offset).
     * @return Una lista de objetos {@link ListarEquipoDTO} que coinciden con
     * los criterios de búsqueda.
     * @throws Exception Si ocurre un error al consultar los datos en la base de
     * datos o al procesar la lista.
     */
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

    /**
     * Calcula el número total de páginas necesarias para mostrar todos los
     * equipos resultantes de una búsqueda, basándose en un límite de elementos
     * por página.
     *
     * * @param nombreLaboratorio El nombre del laboratorio a consultar.
     * @param filtro Cadena de texto utilizada como criterio de búsqueda.
     * @param limite La cantidad máxima de elementos que se mostrarán por
     * página.
     * @return El número entero con el total de páginas calculadas.
     * @throws Exception Si ocurre un error al realizar el conteo en la capa de
     * persistencia.
     */
    @Override
    public int obtenerTotalPaginas(String nombreLaboratorio, String filtro, int limite) throws Exception {
        try {
            int totalRegistros = equipoDAO.contarEquipos(nombreLaboratorio, filtro);

            return (int) Math.ceil((double) totalRegistros / limite);

        } catch (Exception e) {
            throw new Exception("Error al calcular el paginado: " + e.getMessage());
        }
    }

    /**
     * Modifica el estado actual de un equipo específico en el sistema.
     *
     * * @param idEquipo El identificador único del equipo cuyo estado será
     * modificado.
     * @param nuevoEstado El nuevo estado que se le asignará al equipo (ej.
     * "Disponible", "Mantenimiento").
     * @throws Exception Si ocurre un error al intentar actualizar el estado en
     * la base de datos.
     */
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

    /**
     * Obtiene una lista con los nombres de todos los laboratorios registrados
     * en el sistema.
     *
     * * @return Una lista de cadenas de texto ({@code List<String>}) con los
     * nombres de los laboratorios.
     * @throws NegocioException Si ocurre un error al cargar la información desde la
     * capa de persistencia.
     */
    @Override
    public EstadoEquipoDTO obtenerEstadoEquipo(String ip) throws NegocioException {
        try {
            return equipoDAO.obtenerEstado(ip);
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    /**
     * Consulta y devuelve el estado actual de un equipo basándose en su
     * dirección IP.
     *
     * * @param ip La dirección IP de red asignada al equipo.
     * @return Un objeto {@link EstadoEquipoDTO} con la información del estado
     * actual del equipo.
     * @throws NegocioException Si ocurre un error en la capa de persistencia al
     * realizar la búsqueda.
     */
    @Override
    public List<SoftwareDTO> obtenerSoftwaresPorEquipo(int idEquipo) throws NegocioException {
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

        } catch (PersistenciaException e) {
            throw new NegocioException("Error en el negocio al obtener softwares: " + e.getMessage());
        }
    }

    /**
     * Recupera la lista de programas de software que se encuentran instalados
     * en un equipo específico. Convierte las entidades de software recuperadas
     * en objetos DTO.
     *
     * * @param idEquipo El identificador único del equipo a consultar.
     * @return Una lista de objetos {@link SoftwareDTO} representando los
     * programas instalados.
     * @throws NegocioException Si ocurre un error al consultar la capa de
     * persistencia.
     */
    @Override
    public List<ListarEquipoDTO> listarEquipos() throws NegocioException {
        try {
            List<EquipoEntidad> entidades = equipoDAO.listarTodos();
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
            throw new NegocioException("Error al obtener la lista de equipos: " + e.getMessage());
        }
    }

}
