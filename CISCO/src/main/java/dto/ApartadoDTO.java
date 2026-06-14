/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDateTime;

/**
 *
 * @author luisf
 */
public class ApartadoDTO {

    private int idEquipo;
    private int idAlumno;
    private String nombreCompletoAlumno;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String estado;

    public ApartadoDTO() {
    }

    public ApartadoDTO(int idEquipo, int idAlumno, String nombreCompletoAlumno, LocalDateTime horaInicio, LocalDateTime horaFin, String estado) {
        this.idEquipo = idEquipo;
        this.idAlumno = idAlumno;
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreCompletoAlumno() {
        return nombreCompletoAlumno;
    }

    public void setNombreCompletoAlumno(String nombreCompletoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
