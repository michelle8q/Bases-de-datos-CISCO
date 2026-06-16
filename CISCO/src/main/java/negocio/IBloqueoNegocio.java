/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import dto.BloquearAlumnoDTO;
import entidad.BloqueoEntidad;
import java.util.List;

/**
 * Interfaz que define el contrato para la gestión de bloqueos y sanciones a los
 * alumnos. Regula la aplicación de restricciones temporales para el uso de los
 * equipos de cómputo del laboratorio.
 *
 * * @author cinca
 */
public interface IBloqueoNegocio {

    /**
     * Registra y aplica un nuevo bloqueo o sanción a un alumno en el sistema.
     *
     * * @param bloqueo Objeto de transferencia de datos
     * {@link BloquearAlumnoDTO} con la información del motivo y fechas del
     * bloqueo.
     * @return La entidad {@link BloqueoEntidad} que representa el bloqueo
     * registrado.
     * @throws NegocioException Si los datos de la sanción son inválidos o el
     * alumno ya cuenta con un bloqueo activo.
     */
    BloqueoEntidad bloquear(BloquearAlumnoDTO bloqueo) throws NegocioException;

    /**
     * Levanta o retira una sanción existente de forma manual antes de su fecha
     * de expiración programada.
     *
     * * @param id Identificador único del registro de bloqueo que se desea
     * remover.
     * @return La entidad {@link BloqueoEntidad} modificada con el estado de
     * desbloqueo.
     * @throws NegocioException Si el identificador no corresponde a ningún
     * bloqueo activo.
     */
    BloqueoEntidad desbloquear(int id) throws NegocioException;

    /**
     * Obtiene una lista paginada e histórica de las sanciones registradas en el
     * sistema, permitiendo aplicar filtros de búsqueda.
     *
     * * @param filtro Cadena de texto para filtrar los bloqueos (por ejemplo,
     * por nombre de alumno o motivo).
     * @param limite Cantidad máxima de registros a retornar por consulta
     * (tamaño de página).
     * @param offset Número de registros a omitir desde el inicio
     * (desplazamiento para paginación).
     * @return Una lista de objetos {@link BloqueoEntidad} que coinciden con los
     * criterios establecidos.
     * @throws NegocioException Si ocurre un error al realizar la consulta en la
     * persistencia.
     */
    List<BloqueoEntidad> listarBloqueos(String filtro, int limite, int offset) throws NegocioException;
}
