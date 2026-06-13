/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDateTime;

/**
 *
 * @author cinca
 */
public class BloquearAlumnoDTO {

    private int idAlumno;
    private String motivo;
    private LocalDateTime fechaHoraFin;

    public BloquearAlumnoDTO() {
    }

    public BloquearAlumnoDTO(int idAlumno, String motivo) {
        this.idAlumno = idAlumno;
        this.motivo = motivo;
        this.fechaHoraFin = fechaHoraFin;

    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }
}
