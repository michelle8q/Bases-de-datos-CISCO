/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDateTime;

/**
 * Objeto de Transferencia de Datos (DTO) diseñado para monitorear y registrar
 * las sesiones activas de uso presencial y en tiempo real de los equipos
 * informáticos. Vincula una estación de cómputo con el alumno concurrente y
 * marca la hora de inicio de sesión.
 *
 * * @author luisf
 */
public class UsoDTO {

    private int idEquipo;
    private int idAlumno;
    private String nombreCompletoAlumno;
    private LocalDateTime fechaHoraInicio;

    /**
     * Constructor por defecto de la clase UsoDTO.
     */
    public UsoDTO() {
    }

    /**
     * Constructor parametrizado para inicializar un registro de uso activo de
     * estación de cómputo.
     *
     * * @param idEquipo Identificador del equipo que está siendo operado.
     * @param idAlumno Identificador del alumno que ha iniciado sesión.
     * @param nombreCompletoAlumno Nombre y apellidos completos del alumno
     * activo.
     * @param fechaHoraInicio Momento exacto (fecha y hora) en que inició el uso
     * del equipo.
     */
    public UsoDTO(int idEquipo, int idAlumno, String nombreCompletoAlumno, LocalDateTime fechaHoraInicio) {
        this.idEquipo = idEquipo;
        this.idAlumno = idAlumno;
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.fechaHoraInicio = fechaHoraInicio;
    }

    /**
     * Obtiene el identificador del equipo en uso.
     *
     * @return El ID del equipo de cómputo.
     */
    public int getIdEquipo() {
        return idEquipo;
    }

    /**
     * Asigna el identificador del equipo en uso.
     *
     * @param idEquipo El ID del equipo a establecer.
     */
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    /**
     * Obtiene el identificador del alumno que opera el equipo.
     *
     * @return El ID del alumno de la sesión.
     */
    public int getIdAlumno() {
        return idAlumno;
    }

    /**
     * Asigna el identificador del alumno a la sesión de uso.
     *
     * @param idAlumno El ID del alumno a establecer.
     */
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    /**
     * Obtiene el nombre completo del alumno operando el equipo.
     *
     * @return Nombre completo del alumno en sesión.
     */
    public String getNombreCompletoAlumno() {
        return nombreCompletoAlumno;
    }

    /**
     * Asigna el nombre completo del alumno a la sesión de uso.
     *
     * @param nombreCompletoAlumno Nombre del alumno a establecer.
     */
    public void setNombreCompletoAlumno(String nombreCompletoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
    }

    /**
     * Obtiene la marca de tiempo de la fecha y hora exactas de inicio de uso.
     *
     * @return Objeto {@link LocalDateTime} correspondiente al inicio de la
     * sesión.
     */
    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    /**
     * Especifica el momento exacto (fecha y hora) de inicio de la sesión de
     * uso.
     *
     * @param fechaHoraInicio Objeto {@link LocalDateTime} con la fecha y hora a
     * asignar.
     */
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }
}
