/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa una sanción o restricción temporal
 * (bloqueo) aplicada a un alumno, impidiéndole apartar o usar equipos de
 * cómputo durante un lapso de tiempo.
 *
 * * @author cinca
 */
public class BloqueoEntidad {

    private int id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String motivo;
    private int idAlumno;
    private AlumnoEntidad alumno;

    /**
     * Constructor por defecto de la clase BloqueoEntidad.
     */
    public BloqueoEntidad() {
    }

    /**
     * Constructor completo que vincula tanto la clave foránea numérica como el
     * objeto de entidad Alumno.
     *
     * * @param id Identificador único del registro de bloqueo en base de
     * datos.
     * @param fechaHoraInicio Fecha y hora exactas en que entra en vigor la
     * sanción.
     * @param fechaHoraFin Fecha y hora exactas en que expira la sanción.
     * @param motivo Descripción detallada de la causa de la sanción.
     * @param idAlumno ID de la clave foránea del alumno involucrado.
     * @param alumno Instancia completa del {@link AlumnoEntidad} sancionado.
     */
    public BloqueoEntidad(int id, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String motivo, int idAlumno, AlumnoEntidad alumno) {
        this.id = id;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.motivo = motivo;
        this.idAlumno = idAlumno;
        this.alumno = alumno;
    }

    /**
     * Constructor relacional que utiliza el objeto completo de la entidad
     * Alumno.
     *
     * * @param id Identificador único del registro de bloqueo.
     * @param fechaHoraInicio Fecha y hora de inicio de la sanción.
     * @param fechaHoraFin Fecha y hora de conclusión de la sanción.
     * @param motivo Descripción o justificación del bloqueo.
     * @param alumno Instancia completa del {@link AlumnoEntidad} sancionado.
     */
    public BloqueoEntidad(int id, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String motivo, AlumnoEntidad alumno) {
        this.id = id;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.motivo = motivo;
        this.alumno = alumno;
    }

    /**
     * Constructor relacional que utiliza únicamente la referencia por ID del
     * alumno.
     *
     * * @param id Identificador único del registro de bloqueo.
     * @param fechaHoraInicio Fecha y hora de inicio de la sanción.
     * @param fechaHoraFin Fecha y hora de conclusión de la sanción.
     * @param motivo Descripción o justificación del bloqueo.
     * @param idAlumno Identificador numérico único del alumno involucrado.
     */
    public BloqueoEntidad(int id, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String motivo, int idAlumno) {
        this.id = id;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.motivo = motivo;
        this.idAlumno = idAlumno;
    }

    /**
     * Obtiene el identificador del bloqueo.
     *
     * @return El ID del bloqueo.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador único del bloqueo.
     *
     * @param id El ID único a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el momento de inicio de la sanción.
     *
     * @return Objeto {@link LocalDateTime} con la marca de inicio.
     */
    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    /**
     * Especifica el momento de inicio de la sanción.
     *
     * @param fechaHoraInicio Fecha y hora de inicio a establecer.
     */
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    /**
     * Obtiene el momento programado para el término del bloqueo.
     *
     * @return Objeto {@link LocalDateTime} con la marca de fin.
     */
    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    /**
     * Especifica el momento de expiración de la sanción.
     *
     * @param fechaHoraFin Fecha y hora de término a establecer.
     */
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    /**
     * Obtiene la descripción o motivo que originó el bloqueo.
     *
     * @return Cadena de texto explicativa.
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Asigna el motivo o justificación del bloqueo.
     *
     * @param motivo Texto detallando la causa.
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * Obtiene la entidad del alumno asociado a este bloqueo.
     *
     * @return Objeto {@link AlumnoEntidad} vinculado.
     */
    public AlumnoEntidad getAlumno() {
        return alumno;
    }

    /**
     * Vincula un objeto de alumno a este registro de bloqueo.
     *
     * @param alumno La entidad {@link AlumnoEntidad} a asociar.
     */
    public void setAlumno(AlumnoEntidad alumno) {
        this.alumno = alumno;
    }

    /**
     * Obtiene el identificador numérico del alumno penalizado.
     *
     * @return El ID del alumno.
     */
    public int getIdAlumno() {
        return idAlumno;
    }

    /**
     * Asigna el identificador numérico del alumno al registro.
     *
     * @param idAlumno El ID del alumno a establecer.
     */
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

}
