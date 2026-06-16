/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.UsoEntidad;
import java.util.List;

/**
 * Interfaz de Acceso a Datos (DAO) encargada de auditar la bitácora
 * transaccional en tiempo real. Provee persistencia para el ciclo operativo de
 * las terminales: reservas, ingresos físicos y liberaciones de sesiones.
 *
 * * @author luisf
 */
public interface IUsoDAO {

    /**
     * Extrae el listado histórico y en tiempo real de los inicios de sesión de
     * uso activos en los laboratorios.
     *
     * * @param limite Cantidad máxima de registros a recuperar por paginación.
     * @param offset Punto de inicio o desplazamiento de renglones en la
     * consulta.
     * @param filtroBusqueda Texto genérico para buscar coincidencias (por
     * alumno, código o laboratorio).
     * @return Lista de entidades de tipo {@link UsoEntidad}.
     * @throws PersistenciaException Si la consulta de auditoría falla.
     */
    List<UsoEntidad> listarUsosActivos(int limite, int offset, String filtroBusqueda) throws PersistenciaException;

    /**
     * Recupera las transacciones que tienen el estatus de reservación
     * programadas únicamente para el día actual.
     *
     * * @param limite Cantidad de registros por página.
     * @param offset Punto de inicio de corte de renglones.
     * @param filtroBusqueda Filtro por palabra clave.
     * @return Lista de entidades de tipo {@link UsoEntidad} con los apartados
     * vigentes del día.
     * @throws PersistenciaException Si ocurre un fallo en los filtros
     * temporales SQL.
     */
    List<UsoEntidad> listarApartadosDelDia(int limite, int offset, String filtroBusqueda) throws PersistenciaException;

    /**
     * Elimina físicamente o libera de forma drástica una reserva o un uso
     * activo rastreado por la IP del terminal.
     *
     * * @param ip Dirección IP de la máquina involucrada.
     * @throws PersistenciaException Si la sesión no puede ser eliminada o
     * alterada.
     */
    void eliminarUsoActivoPorIP(String ip) throws PersistenciaException;

    /**
     * Actualiza la bitácora registrando la fecha/hora de fin y el cambio de
     * estatus a concluido para una sesión.
     *
     * * @param idUso Identificador único de la transacción de uso a cerrar.
     * @return {@code true} si la sesión se finalizó con éxito en el
     * almacenamiento; {@code false} en caso contrario.
     * @throws PersistenciaException Si ocurre un fallo al escribir la marca
     * temporal de cierre.
     */
    boolean finalizarSesion(int idUso) throws PersistenciaException;

    /**
     * Actualiza la transacción de un apartado a un uso activo real, registrando
     * la fecha y hora de inicio físico.
     *
     * * @param idUso Identificador de la transacción de reserva que pasa a
     * estar activa.
     * @return {@code true} si se actualizó el registro satisfactoriamente;
     * {@code false} si no hubo cambios.
     * @throws PersistenciaException Si la actualización viola reglas de
     * consistencia de datos.
     */
    boolean iniciarSesion(int idUso) throws PersistenciaException;

    /**
     * Inserta un nuevo registro transaccional en la bitácora con estatus de
     * reservación de máquina.
     *
     * * @param nuevoUso Objeto instanciado {@link UsoEntidad} con los datos de
     * asociación alumno-equipo.
     * @return {@code true} si el registro fue guardado con éxito; {@code false}
     * de lo contrario.
     * @throws PersistenciaException Si el equipo se encuentra ocupado
     * concurrentemente o la inserción falla.
     */
    boolean registrarApartado(UsoEntidad nuevoUso) throws PersistenciaException;

    /**
     * Busca y retorna de forma detallada una reservación o apartado activo
     * vinculado a una dirección IP de red.
     *
     * * @param ip Dirección IP del equipo consultado.
     * @return El objeto {@link UsoEntidad} correspondiente a la reserva en
     * curso, o {@code null} si está libre.
     * @throws PersistenciaException Si se presentan fallas de lectura en las
     * uniones relacionales (JOINs).
     */
    UsoEntidad buscarApartadoActivoPorIP(String ip) throws PersistenciaException;
}
