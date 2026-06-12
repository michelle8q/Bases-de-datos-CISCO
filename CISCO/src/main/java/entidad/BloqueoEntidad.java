/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.time.LocalDateTime;

/**
 *
 * @author cinca
 */
public class BloqueoEntidad {
    private int id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String motivo;
    private AlumnoEntidad alumno;

    public BloqueoEntidad() {
    }

    public BloqueoEntidad(int id, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String motivo, AlumnoEntidad alumno) {
        this.id = id;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.motivo = motivo;
        this.alumno = alumno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public AlumnoEntidad getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoEntidad alumno) {
        this.alumno = alumno;
    }
    
    
}
