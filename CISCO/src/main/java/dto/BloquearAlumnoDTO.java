/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author cinca
 */
public class BloquearAlumnoDTO {
    private int idAlumno;
    private String motivo;

    public BloquearAlumnoDTO() {
    }

    public BloquearAlumnoDTO(int idAlumno, String motivo) {
        this.idAlumno = idAlumno;
        this.motivo = motivo;
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
    
    
}
