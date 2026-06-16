package negocio;

import dto.UsoDTO;
import dto.ApartadoDTO;
import dto.EstadoEquipoDTO;
import java.util.List;

/**
 * Interfaz operativa que rige las transacciones en tiempo real de los
 * laboratorios. Controla el ciclo de vida de las sesiones de cómputo
 * (apartados, inicios de sesión, finalizaciones y cancelaciones), además de
 * determinar los flujos de interfaz según la red.
 *
 * * @author luisf piña
 */
public interface IUsoNegocio {

    /**
     * Evalúa la dirección IP de la máquina local para resolver el tipo de
     * pantalla o rol de interfaz gráfica que el sistema debe inicializar de
     * forma predeterminada.
     *
     * * @param ip Dirección IP de la estación de trabajo actual.
     * @return Una cadena de texto con el rol determinado (ej. "Administrador",
     * "Alumno", "Apartados").
     * @throws NegocioException Si la IP no puede ser clasificada bajo ningún
     * privilegio del sistema.
     */
    String determinarPantalla(String ip) throws NegocioException;

    /**
     * Obtiene la lista paginada de las sesiones de uso de cómputo que se
     * encuentran activas en este momento.
     *
     * * @param limite Cantidad máxima de registros por página.
     * @param offset Desplazamiento para la paginación de resultados.
     * @param filtroBusqueda Criterio de texto opcional para buscar por alumno,
     * equipo o laboratorio.
     * @return Lista de objetos {@link UsoDTO} que representan sesiones activas.
     * @throws NegocioException Si falla la comunicación con la capa de datos al
     * consultar la bitácora activa.
     */
    List<UsoDTO> listarUsosActivos(int limite, int offset, String filtroBusqueda) throws NegocioException;

    /**
     * Recupera las reservas o apartados de equipos agendados para la fecha del
     * día en curso.
     *
     * * @param limite Cantidad máxima de registros a retornar por página.
     * @param offset Desplazamiento de los registros.
     * @param filtroBusqueda Filtro de búsqueda de texto.
     * @return Lista de objetos {@link ApartadoDTO} correspondientes al día de
     * hoy.
     * @throws NegocioException Si ocurre un error al procesar la agenda del
     * día.
     */
    List<ApartadoDTO> listarApartadosDelDia(int limite, int offset, String filtroBusqueda) throws NegocioException;

    /**
     * Cancela un apartado previo efectuado sobre un equipo basándose en la
     * dirección IP de la terminal.
     *
     * * @param ip Dirección IP del equipo cuyo apartado será liberado o
     * cancelado.
     * @throws NegocioException Si no existe una reservación vigente ligada a
     * dicha dirección IP.
     */
    void cancelarApartadoEquipo(String ip) throws NegocioException;

    /**
     * Finaliza formalmente la sesión de uso actual de un equipo, registrando la
     * hora de término y cambiando el estado de la estación a disponible.
     *
     * * @param ip Dirección IP de la máquina donde se cierra la sesión de
     * trabajo.
     * @throws NegocioException Si no se encuentra una sesión activa asociada a
     * esa IP.
     */
    void finalizarSesionEquipo(String ip) throws NegocioException;

    /**
     * Registra el inicio físico de una sesión sobre una máquina previamente
     * apartada por un alumno.
     *
     * * @param ip Dirección IP de la estación de cómputo que será ocupada.
     * @throws NegocioException Si el alumno cuenta con impedimentos, bloqueos o
     * expiró el tiempo de tolerancia.
     */
    void iniciarSesionEquipo(String ip) throws NegocioException;

    /**
     * Genera un nuevo registro de apartado o reserva sobre una estación de
     * trabajo para un estudiante determinado.
     *
     * * @param idEquipo Identificador único del equipo que se desea reservar.
     * @param idAlumno Identificador único del alumno que realiza la
     * reservación.
     * @throws NegocioException Si el equipo no está disponible, el alumno está
     * sancionado o excede su cuota diaria de tiempo.
     */
    void registrarApartado(int idEquipo, int idAlumno) throws NegocioException;

}
