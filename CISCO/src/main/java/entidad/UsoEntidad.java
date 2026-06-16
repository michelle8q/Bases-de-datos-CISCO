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
public class UsoEntidad {
    private int id;
    private LocalDateTime fechaHoraApartado;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private AlumnoEntidad alumno; 
    private EquipoEntidad equipo;
    private String estado;

    public UsoEntidad() {
    }

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

    public UsoEntidad(int id, LocalDateTime fechaHoraApartado, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, AlumnoEntidad alumno, EquipoEntidad equipo) {
        this.id = id;
        this.fechaHoraApartado = fechaHoraApartado;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.alumno = alumno;
        this.equipo = equipo;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaHoraApartado() {
        return fechaHoraApartado;
    }

    public void setFechaHoraApartado(LocalDateTime fechaHoraApartado) {
        this.fechaHoraApartado = fechaHoraApartado;
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

    public AlumnoEntidad getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoEntidad alumno) {
        this.alumno = alumno;
    }

    public EquipoEntidad getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoEntidad equipo) {
        this.equipo = equipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
