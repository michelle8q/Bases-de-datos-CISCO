package negocio;

import dto.EstadoEquipoDTO;
import dto.ListarEquipoDTO;
import dto.SoftwareDTO;
import entidad.SoftwareEntidad;
import java.util.List;

/**
 * Interfaz que define el contrato de servicios de negocio para la
 * administración de equipos de cómputo. Controla consultas de disponibilidad,
 * paginación de estaciones de trabajo, gestión de software y cambios de estado.
 *
 * * @author piña luis
 */
public interface IEquipoNegocio {

    /**
     * Recupera el estado operativo y de asignación actual de un equipo
     * específico basándose en su dirección IP.
     *
     * * @param ip Dirección IP de la máquina de interés.
     * @return Un objeto {@link EstadoEquipoDTO} con el estatus y detalles de la
     * terminal.
     * @throws NegocioException Si la IP no se encuentra registrada o mapeada en
     * el sistema.
     */
    EstadoEquipoDTO obtenerEstadoEquipo(String ip) throws NegocioException;

    /**
     * Realiza una búsqueda paginada y filtrada de los equipos pertenecientes a
     * un laboratorio específico.
     *
     * * @param nombreLaboratorio Nombre o identificador del laboratorio donde
     * se realiza la búsqueda.
     * @param filtro Criterio de búsqueda opcional para filtrar los equipos (ej.
     * por estado o tipo).
     * @param limite Cantidad máxima de equipos a mostrar por página.
     * @param pagina Número de la página actual que se desea recuperar.
     * @return Una lista de {@link ListarEquipoDTO} que cumplen con las
     * condiciones dadas.
     * @throws Exception Si ocurre un error técnico o de conectividad al
     * procesar la paginación.
     */
    List<ListarEquipoDTO> buscarEquiposPaginados(String nombreLaboratorio, String filtro, int limite, int pagina) throws Exception;

    /**
     * Calcula la cantidad total de páginas disponibles para la cuadrícula de
     * equipos, basándose en los filtros aplicados.
     *
     * * @param nombreLaboratorio Nombre del laboratorio consultado.
     * @param filtro Criterio de filtrado aplicado a las estaciones.
     * @param limite Cantidad de registros permitidos por página.
     * @return El número entero total de páginas resultantes del cálculo.
     * @throws Exception Si ocurre un fallo al contar los registros en el origen
     * de datos.
     */
    int obtenerTotalPaginas(String nombreLaboratorio, String filtro, int limite) throws Exception;

    /**
     * Modifica el estado operativo de un equipo de cómputo específico de forma
     * manual.
     *
     * * @param idEquipo Identificador único del equipo a modificar.
     * @param nuevoEstado Nueva etiqueta de estado (ej. "Disponible",
     * "Mantenimiento", "Bloqueado").
     * @throws Exception Si el id del equipo no es válido o la transición de
     * estado no es permitida.
     */
    void cambiarEstadoEquipo(int idEquipo, String nuevoEstado) throws Exception;

    /**
     * Obtiene los nombres de todos los laboratorios registrados en la
     * institución para llenar controles de selección.
     *
     * * @return Una lista de cadenas de texto con los nombres oficiales de los
     * laboratorios.
     * @throws Exception Si ocurre un error al consultar el catálogo de
     * laboratorios.
     */
    List<String> obtenerNombresLaboratorios() throws Exception;

    /**
     * Recupera el catálogo de paqueterías de software y aplicaciones que se
     * encuentran instaladas en un equipo.
     *
     * * @param idEquipo Identificador único de la máquina.
     * @return Una lista de objetos {@link SoftwareDTO} instalados en dicha
     * terminal.
     * @throws Exception Si se presentan fallas al leer la relación de programas
     * del equipo.
     */
    List<SoftwareDTO> obtenerSoftwaresPorEquipo(int idEquipo) throws Exception;

    /**
     * Genera un listado global sin paginar de todos los equipos registrados en
     * el sistema de gestión.
     *
     * * @return Una lista con la totalidad de objetos {@link ListarEquipoDTO}.
     * @throws NegocioException Si se produce un error en la capa de datos al
     * extraer el inventario.
     */
    List<dto.ListarEquipoDTO> listarEquipos() throws NegocioException;
}
