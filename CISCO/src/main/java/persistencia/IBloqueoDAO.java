/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dto.BloquearAlumnoDTO;
import entidad.BloqueoEntidad;
import java.util.List;

/**
 * Interfaz de Acceso a Datos (DAO) para la gestión de bloqueos. Abstrae el
 * almacenamiento, actualización y consulta de las penalizaciones aplicadas a
 * los alumnos.
 *
 * * @author cinca
 */
public interface IBloqueoDAO {

    /**
     * Inserta un nuevo registro de sanción o bloqueo para un alumno específico.
     *
     * * @param bloqueo Objeto DTO {@link BloquearAlumnoDTO} con las marcas
     * temporales y el motivo.
     * @return La entidad {@link BloqueoEntidad} persistida con su ID
     * autogenerado.
     * @throws PersistenciaException Si la inserción SQL falla o viola
     * restricciones de integridad.
     */
    BloqueoEntidad bloquearAlumno(BloquearAlumnoDTO bloqueo) throws PersistenciaException;

    /**
     * Busca un registro de bloqueo específico a través de su identificador
     * único.
     *
     * * @param id Identificador único del bloqueo.
     * @return El objeto {@link BloqueoEntidad} correspondiente, o {@code null}
     * si no se encuentra.
     * @throws PersistenciaException Si ocurre un error en la ejecución de la
     * consulta.
     */
    BloqueoEntidad buscarPorId(int id) throws PersistenciaException;

    /**
     * Modifica o remueve de forma lógica/física una sanción activa en el
     * sistema de base de datos.
     *
     * * @param id Identificador del bloqueo a remover.
     * @return La entidad {@link BloqueoEntidad} actualizada.
     * @throws PersistenciaException Si el registro no se puede actualizar en la
     * persistencia.
     */
    BloqueoEntidad desbloquearAlumno(int id) throws PersistenciaException;

    /**
     * Consulta una lista paginada y filtrada de las sanciones históricas y
     * activas en el sistema.
     *
     * * @param filtro Criterio de búsqueda por texto (ej. nombre del alumno o
     * motivo).
     * @param limite Tamaño máximo de registros para la página actual.
     * @param offset Desplazamiento o número de registros a saltar.
     * @return Una lista de objetos {@link BloqueoEntidad}.
     * @throws PersistenciaException Si falla la consulta select estructurada.
     */
    List<BloqueoEntidad> listarBloqueos(String filtro, int limite, int offset) throws PersistenciaException;

    /**
     * Verifica si un alumno cuenta con un bloqueo activo y vigente en el
     * momento de la consulta.
     *
     * * @param idAlumno Identificador único del alumno a evaluar.
     * @return {@code true} si el alumno está actualmente bloqueado;
     * {@code false} si está libre de sanción.
     * @throws PersistenciaException Si ocurre un error al procesar el conteo o
     * verificación en base de datos.
     */
    boolean BloqueoExistenteAlumno(int idAlumno) throws PersistenciaException;
}
