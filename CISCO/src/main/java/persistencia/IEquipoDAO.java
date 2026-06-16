package persistencia;

import dto.EstadoEquipoDTO;
import entidad.EquipoEntidad;
import entidad.SoftwareEntidad;
import java.util.List;

/**
 * Interfaz de Acceso a Datos (DAO) para la administración de hardware y
 * estaciones de trabajo (Equipos). Provee la persistencia de inventario,
 * consulta de direccionamiento de red, estados y asociaciones de software.
 *
 * * @author piña luisf
 */
public interface IEquipoDAO {

    /**
     * Obtiene el número físico de máquina mapeado a una dirección IP
     * específica.
     *
     * * @param IP Dirección IP de la estación de cómputo.
     * @return Cadena de texto con el número identificador del equipo.
     * @throws PersistenciaException Si la IP no existe en el catálogo físico.
     */
    public String obtenerNumeroEquipo(String IP) throws PersistenciaException;

    /**
     * Recupera el nombre del laboratorio al que se encuentra asignado un equipo
     * mediante su dirección IP.
     *
     * * @param IP Dirección IP de la estación.
     * @return Nombre del laboratorio contenedor.
     * @throws PersistenciaException Si falla la consulta relacional.
     */
    public String obtenerLaboratorio(String IP) throws PersistenciaException;

    /**
     * Extrae el estatus actual de disponibilidad de un equipo en base a su
     * dirección IP.
     *
     * * @param IP Dirección IP de la máquina de interés.
     * @return Un objeto de transferencia {@link EstadoEquipoDTO} con el estado
     * interno mapeado.
     * @throws PersistenciaException Si ocurre un error al mapear la
     * información.
     */
    public EstadoEquipoDTO obtenerEstado(String IP) throws PersistenciaException;

    /**
     * Consulta qué alumno tiene apartado o reservado un equipo en particular
     * por medio de su IP.
     *
     * * @param IP Dirección IP de la máquina reservada.
     * @return Identificador único numérico (ID) del alumno que mantiene el
     * apartado.
     * @throws PersistenciaException Si no se encuentra ningún apartado vigente
     * en esa IP.
     */
    public int obtenerIDAlumnoApartado(String IP) throws PersistenciaException;

    /**
     * Realiza una consulta estructurada paginada para listar los equipos
     * pertenecientes a un laboratorio.
     *
     * * @param nombreLaboratorio Nombre oficial de la sala o laboratorio.
     * @param filtro Criterio de filtrado opcional (ej. tipo, estado).
     * @param limite Cantidad de registros por página.
     * @param pagina Número de la página solicitada para calcular el offset
     * matemático.
     * @return Una lista filtrada de objetos {@link EquipoEntidad}.
     * @throws PersistenciaException Si la consulta SQL falla.
     */
    public List<EquipoEntidad> buscarEquipos(String nombreLaboratorio, String filtro, int limite, int pagina) throws PersistenciaException;

    /**
     * Cuenta la cantidad total de equipos que cumplen con un criterio dentro de
     * un laboratorio. Utilizado para calcular controles de paginación en
     * interfaces visuales.
     *
     * * @param nombreLaboratorio Nombre de la sala de cómputo.
     * @param filtro Criterio de filtro opcional.
     * @return Cantidad entera de registros encontrados.
     * @throws PersistenciaException Si falla la función de agregación COUNT en
     * el motor.
     */
    public int contarEquipos(String nombreLaboratorio, String filtro) throws PersistenciaException;

    /**
     * Ejecuta una sentencia UPDATE directa sobre la base de datos para cambiar
     * el estado de un equipo.
     *
     * * @param idEquipo Identificador único del equipo.
     * @param nuevoEstado Nueva cadena con el estado operativo (ej.
     * "Mantenimiento", "Disponible").
     * @throws PersistenciaException Si no se puede actualizar el registro.
     */
    public void actualizarEstado(int idEquipo, String nuevoEstado) throws PersistenciaException;

    /**
     * Recupera la totalidad de nombres de laboratorios registrados en el
     * sistema de persistencia.
     *
     * * @return Lista de Strings conteniendo los nombres oficiales de las
     * salas de cómputo.
     * @throws PersistenciaException Si el catálogo no puede ser leído.
     */
    public List<String> obtenerNombresLaboratorios() throws PersistenciaException;

    /**
     * Extrae los registros de programas, paqueterías y sistemas operativos
     * instalados en una terminal.
     *
     * * @param idEquipo Identificador de la máquina de interés.
     * @return Una lista con las entidades de tipo {@link SoftwareEntidad}.
     * @throws PersistenciaException Si falla la consulta en la tabla relacional
     * intermedia.
     */
    public List<SoftwareEntidad> obtenerSoftwaresPorEquipo(int idEquipo) throws PersistenciaException;

    /**
     * Recupera el catálogo completo de todos los equipos del sistema sin
     * segmentación ni filtros.
     *
     * * @return Lista global de objetos {@link EquipoEntidad}.
     * @throws PersistenciaException Si ocurre un error general de lectura.
     */
    public List<EquipoEntidad> listarTodos() throws PersistenciaException;

}
