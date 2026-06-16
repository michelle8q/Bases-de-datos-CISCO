/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDateTime;

/**
 * Objeto de Transferencia de Datos (DTO) que representa la información detallada 
 * de una reserva o apartado de equipo de cómputo dentro del sistema.
 * Contiene datos tanto del equipo reservado como del alumno que realiza la acción.
 * * @author luisf
 */
public class ApartadoDTO {

    private int idEquipo;
    private int idAlumno;
    private String nombreCompletoAlumno;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String estado;
/**
     * Constructor por defecto de la clase ApartadoDTO.
     */
    public ApartadoDTO() {
    }
/**
     * Constructor con todos los campos para inicializar el objeto de transferencia.
     * * @param idEquipo             Identificador único del equipo de cómputo.
     * @param idAlumno             Identificador único del alumno que reserva.
     * @param nombreCompletoAlumno Nombre y apellidos completos del alumno.
     * @param horaInicio           Fecha y hora programada para el inicio del apartado.
     * @param horaFin              Fecha y hora programada para la finalización del apartado.
     * @param estado               Estado actual de la reserva (ej. "Activo", "Cancelado", "Finalizado").
     */
    public ApartadoDTO(int idEquipo, int idAlumno, String nombreCompletoAlumno, LocalDateTime horaInicio, LocalDateTime horaFin, String estado) {
        this.idEquipo = idEquipo;
        this.idAlumno = idAlumno;
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }
/**
     * Obtiene el identificador único del equipo de cómputo.
     * @return El ID del equipo.
     */
    public int getIdEquipo() {
        return idEquipo;
    }

    /**
     * Asigna el identificador único del equipo de cómputo.
     * @param idEquipo El ID del equipo a asignar.
     */
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    /**
     * Obtiene el identificador único del alumno.
     * @return El ID del alumno.
     */
    public int getIdAlumno() {
        return idAlumno;
    }

    /**
     * Asigna el identificador único del alumno.
     * @param idAlumno El ID del alumno a asignar.
     */
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    /**
     * Obtiene el nombre completo del alumno.
     * @return Cadena de texto con el nombre completo del alumno.
     */
    public String getNombreCompletoAlumno() {
        return nombreCompletoAlumno;
    }

    /**
     * Asigna el nombre completo del alumno.
     * @param nombreCompletoAlumno El nombre completo a asignar.
     */
    public void setNombreCompletoAlumno(String nombreCompletoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
    }

    /**
     * Obtiene la fecha y hora de inicio del apartado.
     * @return Objeto {@link LocalDateTime} con el momento de inicio.
     */
    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * Asigna la fecha y hora de inicio del apartado.
     * @param horaInicio El objeto {@link LocalDateTime} a asignar.
     */
    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Obtiene la fecha y hora de finalización del apartado.
     * @return Objeto {@link LocalDateTime} con el momento de término.
     */
    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    /**
     * Asigna la fecha y hora de finalización del apartado.
     * @param horaFin El objeto {@link LocalDateTime} a asignar.
     */
    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * Obtiene el estado actual de la reserva.
     * @return El estado del apartado como una cadena de texto.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Asigna el estado actual de la reserva.
     * @param estado El estado a asignar (ej. "Activo", "Expirado").
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
