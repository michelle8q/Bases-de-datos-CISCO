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
public class UsoDTO {
    private int idEquipo;
    private int idAlumno;
    private String nombreCompletoAlumno;
    private LocalDateTime fechaHoraInicio;

    public UsoDTO() {
    }

    public UsoDTO(int idEquipo, int idAlumno, String nombreCompletoAlumno, LocalDateTime fechaHoraInicio) {
        this.idEquipo = idEquipo;
        this.idAlumno = idAlumno;
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.fechaHoraInicio = fechaHoraInicio;
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

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

}
