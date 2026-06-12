/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author cinca
 */
public class ApartarEquipoDTO {
    private int idAlumno;
    private int idEquipo;

    public ApartarEquipoDTO() {
    }

    public ApartarEquipoDTO(int idAlumno, int idEquipo) {
        this.idAlumno = idAlumno;
        this.idEquipo = idEquipo;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
    
    
}
