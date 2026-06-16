package negocio;

import dto.ApartadoDTO;
import dto.EstadoEquipoDTO;
import dto.UsoDTO;
import entidad.AlumnoEntidad;
import entidad.EquipoEntidad;
import entidad.UsoEntidad;
import java.util.ArrayList;
import persistencia.IUsoDAO;
import persistencia.PersistenciaException;
import java.util.List;
import persistencia.IAlumnoDAO;
import persistencia.IEquipoDAO;
import persistencia.IIpDAO;

/**
 * Clase que implementa la interfaz {@link IUsoNegocio}. Contiene la lógica de
 * negocio relacionada con el uso y apartado de los equipos de cómputo. Se
 * encarga de intermediar entre la capa de presentación y la capa de
 * persistencia (DAOs), aplicando validaciones y transformando entidades en
 * objetos de transferencia de datos (DTOs).
 *
 * @author luisf piña
 */
public class UsoNegocio implements IUsoNegocio {

    private IUsoDAO usoDAO;
    private IIpDAO ipDAO;
    private IEquipoDAO equipoDAO;
    private IAlumnoDAO alumnoDAO;

    /**
     * Constructor de la clase UsoNegocio.
     *
     * * @param usoDAO Interfaz de acceso a datos para las operaciones de
     * Uso/Apartado.
     * @param ipDAO Interfaz de acceso a datos para resolver roles/pantallas
     * mediante IP.
     * @param equipoDAO Interfaz de acceso a datos para gestionar la información
     * de los equipos.
     * @param alumnoDAO Interfaz de acceso a datos para gestionar la información
     * de los alumnos.
     */
    public UsoNegocio(IUsoDAO usoDAO, IIpDAO ipDAO, IEquipoDAO equipoDAO, IAlumnoDAO alumnoDAO) {
        this.usoDAO = usoDAO;
        this.ipDAO = ipDAO;
        this.equipoDAO = equipoDAO;
        this.alumnoDAO = alumnoDAO;
    }

    /**
     * Recupera una lista paginada de los usos de equipos que se encuentran
     * actualmente activos.
     *
     * * @param limite La cantidad máxima de registros a recuperar.
     * @param offset El punto de inicio para la paginación.
     * @param filtroBusqueda Cadena de texto para filtrar los resultados.
     * @return Una lista de {@link UsoDTO} con la información de los usos
     * activos.
     * @throws NegocioException Si no hay usos activos o si ocurre un error en
     * la capa de persistencia.
     */
    @Override
    public List<UsoDTO> listarUsosActivos(int limite, int offset, String filtroBusqueda) throws NegocioException {
        try {
            List<UsoEntidad> usos = usoDAO.listarUsosActivos(limite, offset, filtroBusqueda);
            if (usos.isEmpty()) {
                throw new NegocioException("No hay usos activos en este momento.");
            }

            List<UsoDTO> listaDTOs = new ArrayList<>();

            for (UsoEntidad uso : usos) {
                String nombreCompleto = uso.getAlumno().getNombres() + " "
                        + uso.getAlumno().getApellidoPaterno() + " "
                        + uso.getAlumno().getApellidoMaterno();

                UsoDTO dto = new UsoDTO(
                        uso.getEquipo().getId(),
                        uso.getAlumno().getId(),
                        nombreCompleto,
                        uso.getFechaHoraInicio()
                );

                listaDTOs.add(dto);
            }

            return listaDTOs;
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    /**
     * Obtiene una lista paginada de todos los apartados registrados en el día
     * actual, calculando su estado (Pendiente, Activo o Finalizado).
     *
     * * @param limite La cantidad máxima de registros a recuperar.
     * @param offset El punto de inicio para la paginación.
     * @param filtroBusqueda Cadena de texto para filtrar los resultados.
     * @return Una lista de {@link ApartadoDTO} con los detalles del apartado.
     * @throws NegocioException Si no hay apartados registrados hoy o si ocurre
     * un error de base de datos.
     */
    @Override
    public List<ApartadoDTO> listarApartadosDelDia(int limite, int offset, String filtroBusqueda) throws NegocioException {
        try {
            List<UsoEntidad> apartados = usoDAO.listarApartadosDelDia(limite, offset, filtroBusqueda);

            if (apartados.isEmpty()) {
                throw new NegocioException("No hay apartados registrados hoy o no hay más páginas.");
            }

            List<ApartadoDTO> listaDTOs = new ArrayList<>();

            for (UsoEntidad uso : apartados) {

                String nombreCompleto = uso.getAlumno().getNombres() + " "
                        + uso.getAlumno().getApellidoPaterno() + " "
                        + uso.getAlumno().getApellidoMaterno();

                String estado = "Pendiente";
                if (uso.getFechaHoraInicio() != null && uso.getFechaHoraFin() == null) {
                    estado = "Activo";
                } else if (uso.getFechaHoraFin() != null) {
                    estado = "Finalizado";
                }

                ApartadoDTO dto = new ApartadoDTO(
                        uso.getEquipo().getId(),
                        uso.getAlumno().getId(),
                        nombreCompleto,
                        uso.getFechaHoraInicio(),
                        uso.getFechaHoraFin(),
                        estado
                );

                listaDTOs.add(dto);
            }

            return listaDTOs;
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    /**
     * Determina el rol o la pantalla que le corresponde a un equipo basado en
     * su dirección IP.
     *
     * * @param ip La dirección IP del equipo.
     * @return El tipo de pantalla asignada. Devuelve "SI_ACCESO" si el equipo
     * no tiene una pantalla específica definida.
     * @throws NegocioException Si ocurre un error de validación en el sistema.
     */
    @Override
    public String determinarPantalla(String ip) throws NegocioException {
        try {
            String pantalla = ipDAO.obtenerPantallaPorIP(ip);

            if (pantalla == null) {
                return "SI_ACCESO";
            }

            return pantalla;

        } catch (PersistenciaException e) {
            System.err.println("Error en Negocio al determinar la pantalla para la IP: " + ip);
            throw new NegocioException("No se pudo validar la IP en el sistema: " + e.getMessage());
        }
    }

    /**
     * Valida que una dirección IP no sea nula ni esté vacía.
     *
     * * @param ip La dirección IP a validar.
     * @throws NegocioException Si la IP es inválida.
     */
    private void validarIP(String ip) throws NegocioException {
        if (ip == null || ip.trim().isEmpty()) {
            throw new NegocioException("La dirección IP del equipo no es válida.");
        }
    }

    /**
     * Cancela un apartado pendiente o activo asociado a la dirección IP de un
     * equipo, liberándolo y regresando su estado a "Disponible".
     *
     * * @param ip La dirección IP del equipo que tiene el apartado.
     * @throws NegocioException Si no existe un apartado, la IP es inválida o
     * falla la operación.
     */
    @Override
    public void cancelarApartadoEquipo(String ip) throws NegocioException {
        try {
            validarIP(ip);

            // Una sola búsqueda directa por IP
            UsoEntidad usoActual = this.usoDAO.buscarApartadoActivoPorIP(ip);

            validarUsoExistente(usoActual,
                    "No se encontró ningún apartado activo o pendiente para la IP: " + ip);

            int idEquipo = usoActual.getEquipo().getId();
            this.usoDAO.eliminarUsoActivoPorIP(ip);
            this.equipoDAO.actualizarEstado(idEquipo, "Disponible");

        } catch (PersistenciaException e) {
            throw new NegocioException("Error en la capa de persistencia: " + e.getMessage());
        } catch (NegocioException e) {
            throw e;
        } catch (Exception e) {
            throw new NegocioException("Error al cancelar el apartado del equipo: " + e.getMessage());
        }
    }

    /**
     * Finaliza la sesión actual (uso activo) de un equipo específico
     * identificado por su IP.
     *
     * * @param ip La dirección IP del equipo.
     * @throws NegocioException Si no existe un uso activo o si ocurre un error
     * al registrar el fin de sesión.
     */
    @Override
    public void finalizarSesionEquipo(String ip) throws NegocioException {
        try {
            validarIP(ip);

            UsoEntidad usoActual = this.usoDAO.buscarApartadoActivoPorIP(ip);

            validarUsoExistente(usoActual,
                    "No se encontró ningún apartado pendiente para la IP: " + ip);

            boolean exito = this.usoDAO.finalizarSesion(usoActual.getId());
            validarOperacionExitosa(exito, "No se pudo finalizar la sesión en la base de datos.");

        } catch (PersistenciaException e) {
            throw new NegocioException("Error en la capa de persistencia: " + e.getMessage());
        }
    }

    /**
     * Inicia una sesión formal de uso en un equipo que previamente había sido
     * apartado.
     *
     * * @param ip La dirección IP del equipo.
     * @throws NegocioException Si el equipo no estaba apartado, si ya tenía
     * sesión iniciada o si falla la base de datos.
     */
    @Override
    public void iniciarSesionEquipo(String ip) throws NegocioException {
        try {
            validarIP(ip);

            UsoEntidad usoActual = this.usoDAO.buscarApartadoActivoPorIP(ip);

            if (usoActual != null && usoActual.getFechaHoraInicio() != null) {
                usoActual = null;
            }

            validarUsoExistente(usoActual,
                    "No se encontró ningún apartado pendiente para la IP: " + ip);

            boolean exito = this.usoDAO.iniciarSesion(usoActual.getId());
            validarOperacionExitosa(exito, "No se pudo iniciar la sesión en la base de datos.");

        } catch (PersistenciaException e) {
            throw new NegocioException("Error en la capa de persistencia: " + e.getMessage());
        }
    }

    /**
     * Helper que valida la existencia de una entidad de uso.
     *
     * * @param uso El objeto {@link UsoEntidad} a verificar.
     * @param mensaje El mensaje de error que se lanzará si el objeto es nulo.
     * @throws NegocioException Si el objeto uso es nulo.
     */
    private void validarUsoExistente(UsoEntidad uso, String mensaje) throws NegocioException {
        if (uso == null) {
            throw new NegocioException(mensaje);
        }
    }

    /**
     * Helper que valida si una operación booleana fue exitosa.
     *
     * * @param exito El resultado de la operación.
     * @param mensaje El mensaje de error que se lanzará si no fue exitosa.
     * @throws NegocioException Si el parámetro exito es falso.
     */
    private void validarOperacionExitosa(boolean exito, String mensaje) throws NegocioException {
        if (!exito) {
            throw new NegocioException(mensaje);
        }
    }

    /**
     * Registra un nuevo apartado de equipo a nombre de un alumno específico.
     *
     * * @param idEquipo El identificador único del equipo a apartar.
     * @param idAlumno El identificador único del alumno que realiza el
     * apartado.
     * @throws NegocioException Si ocurre un error al intentar registrar la
     * información en la base de datos.
     */
    @Override
    public void registrarApartado(int idEquipo, int idAlumno) throws NegocioException {
        try {
            UsoEntidad nuevo = new UsoEntidad();
            nuevo.setFechaHoraApartado(java.time.LocalDateTime.now());

            AlumnoEntidad alumno = new AlumnoEntidad();
            alumno.setId(idAlumno);
            nuevo.setAlumno(alumno);

            EquipoEntidad equipo = new EquipoEntidad();
            equipo.setId(idEquipo);
            nuevo.setEquipo(equipo);

            boolean exito = this.usoDAO.registrarApartado(nuevo);
            validarOperacionExitosa(exito, "No se pudo registrar el apartado.");

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al registrar apartado: " + e.getMessage());
        }
    }

}
