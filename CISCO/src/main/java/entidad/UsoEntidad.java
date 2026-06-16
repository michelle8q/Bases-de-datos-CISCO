/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.time.LocalDateTime;

/**
 * Entidad de dominio transaccional medular encargada de auditar la bitácora de
 * uso y las reservas físicas de estaciones de cómputo efectuadas por los
 * alumnos. Registra marcas de tiempo detalladas para controlar la duración y
 * validez de las sesiones.
 *
 * * @author cinca
 */
public class UsoEntidad {

    private int id;
    private LocalDateTime fechaHoraApartado;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private AlumnoEntidad alumno;
    private EquipoEntidad equipo;
    private String estado;

    /**
     * Constructor por defecto de la clase UsoEntidad.
     */
    public UsoEntidad() {
    }

    /**
     * Constructor completo que contempla el ciclo total de vida de una sesión
     * (incluido su estado).
     *
     * * @param id Identificador único de la transacción en base de datos.
     * @param fechaHoraApartado Instante temporal en el que el alumno apartó el
     * equipo de forma remota.
     * @param fechaHoraInicio Instante temporal en el que se inició físicamente
     * la sesión en la máquina.
     * @param fechaHoraFin Instante temporal en el que concluyó el uso de la
     * terminal.
     * @param alumno Instancia del {@link AlumnoEntidad} involucrado.
     * @param equipo Instancia del {@link EquipoEntidad} ocupado.
     * @param estado Etiqueta de estatus operativo (ej. "Pendiente", "En curso",
     * "Terminado").
     */
    public UsoEntidad(int id, LocalDateTime fechaHoraApartado, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, AlumnoEntidad alumno, EquipoEntidad equipo,
            String estado) {
        this.id = id;
        this.fechaHoraApartado = fechaHoraApartado;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.alumno = alumno;
        this.equipo = equipo;
        this.estado = estado;
    }

    /**
     * Constructor parcial que prescinde del atributo de estado textual de la
     * operación.
     *
     * * @param id Identificador único de la transacción.
     * @param fechaHoraApartado Marca temporal del momento del apartado.
     * @param fechaHoraInicio Marca temporal del inicio de uso físico.
     * @param fechaHoraFin Marca temporal del cierre de sesión.
     * @param alumno Instancia del {@link AlumnoEntidad} involucrado.
     * @param equipo Instancia del {@link EquipoEntidad} ocupado.
     */
    public UsoEntidad(int id, LocalDateTime fechaHoraApartado, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, AlumnoEntidad alumno, EquipoEntidad equipo) {
        this.id = id;
        this.fechaHoraApartado = fechaHoraApartado;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.alumno = alumno;
        this.equipo = equipo;
    }

    /**
     * Obtiene el identificador único del registro de uso.
     *
     * @return El ID de la transacción.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador único del registro de uso.
     *
     * @param id El ID único a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el momento temporal en el que fue apartado el recurso
     * informático.
     *
     * @return Objeto {@link LocalDateTime} con la marca del apartado.
     */
    public LocalDateTime getFechaHoraApartado() {
        return fechaHoraApartado;
    }

    /**
     * Registra el momento exacto en el que se consolida la reserva del equipo.
     *
     * @param fechaHoraApartado Marca de tiempo para la reservación.
     */
    public void setFechaHoraApartado(LocalDateTime fechaHoraApartado) {
        this.fechaHoraApartado = fechaHoraApartado;
    }

    /**
     * Obtiene el momento de login o inicio de sesión presencial en la máquina.
     *
     * @return Objeto {@link LocalDateTime} con el arranque de sesión.
     */
    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    /**
     * Registra la marca cronológica en la que el alumno desbloquea e inicia el
     * uso del equipo.
     *
     * @param fechaHoraInicio Marca de tiempo de apertura.
     */
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    /**
     * Obtiene la marca temporal exacta del logout o fin del uso.
     *
     * @return Objeto {@link LocalDateTime} con la conclusión del uso.
     */
    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    /**
     * Registra la marca cronológica en la que la sesión es clausurada o expira.
     *
     * @param fechaHoraFin Marca de tiempo de cierre.
     */
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    /**
     * Obtiene el alumno vinculado a este servicio o reservación.
     *
     * @return Entidad {@link AlumnoEntidad}.
     */
    public AlumnoEntidad getAlumno() {
        return alumno;
    }

    /**
     * Asocia de manera formal al alumno responsable de esta transacción de uso.
     *
     * @param alumno Entidad {@link AlumnoEntidad}.
     */
    public void setAlumno(AlumnoEntidad alumno) {
        this.alumno = alumno;
    }

    /**
     * Obtiene la computadora o terminal que está sujeta a este uso o reserva.
     *
     * @return Entidad {@link EquipoEntidad}.
     */
    public EquipoEntidad getEquipo() {
        return equipo;
    }

    /**
     * Asocia la computadora específica que será afectada por este registro de
     * uso.
     *
     * @param equipo Entidad {@link EquipoEntidad}.
     */
    public void setEquipo(EquipoEntidad equipo) {
        this.equipo = equipo;
    }

    /**
     * Obtiene el estado operativo textual de la transacción.
     *
     * @return Cadena con el estado (ej. "En curso").
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Modifica el estado de control de este apartado o sesión de cómputo.
     *
     * @param estado Nuevo estado a asignar.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
