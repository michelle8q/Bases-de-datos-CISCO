/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 * Objeto de Transferencia de Datos (DTO) simplificado para procesar solicitudes
 * directas de vinculación entre un alumno y un equipo de cómputo específico. Se
 * utiliza principalmente como payload en las operaciones de creación de
 * reservas.
 *
 * * @author cinca
 */
public class ApartarEquipoDTO {

    private int idAlumno;
    private int idEquipo;

    /**
     * Constructor por defecto de la clase ApartarEquipoDTO.
     */
    public ApartarEquipoDTO() {
    }

    /**
     * Constructor parametrizado para la asignación inmediata de una solicitud
     * de apartado.
     *
     * * @param idAlumno Identificador único del alumno solicitante.
     * @param idEquipo Identificador único del equipo de cómputo a apartar.
     */
    public ApartarEquipoDTO(int idAlumno, int idEquipo) {
        this.idAlumno = idAlumno;
        this.idEquipo = idEquipo;
    }

    /**
     * Obtiene el identificador único del alumno.
     *
     * @return El ID del alumno.
     */
    public int getIdAlumno() {
        return idAlumno;
    }

    /**
     * Asigna el identificador único del alumno.
     *
     * @param idAlumno El ID del alumno a asignar.
     */
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    /**
     * Obtiene el identificador único del equipo de cómputo.
     *
     * @return El ID del equipo.
     */
    public int getIdEquipo() {
        return idEquipo;
    }

    /**
     * Asigna el identificador único del equipo de cómputo.
     *
     * @param idEquipo El ID del equipo a asignar.
     */
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

}
