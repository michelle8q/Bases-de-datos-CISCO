/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 * Objeto de Transferencia de Datos (DTO) que transporta la información
 * necesaria para registrar la sanción o bloqueo de acceso a un alumno en el
 * sistema de laboratorios.
 *
 * * @author cinca
 */
public class BloquearAlumnoDTO {

    private int idAlumno;
    private String motivo;

    /**
     * Constructor por defecto de la clase BloquearAlumnoDTO.
     */
    public BloquearAlumnoDTO() {
    }

    /**
     * Constructor parametrizado para inicializar los datos de restricción de un
     * alumno.
     *
     * * @param idAlumno Identificador único del alumno que será bloqueado.
     * @param motivo La razón, justificación o infracción cometida que causa el
     * bloqueo.
     */
    public BloquearAlumnoDTO(int idAlumno, String motivo) {
        this.idAlumno = idAlumno;
        this.motivo = motivo;

    }

    /**
     * Obtiene el identificador único del alumno restringido.
     *
     * @return El ID del alumno.
     */
    public int getIdAlumno() {
        return idAlumno;
    }

    /**
     * Asigna el identificador único del alumno que se desea restringir.
     *
     * @param idAlumno El ID del alumno a asignar.
     */
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    /**
     * Obtiene el motivo o la descripción de la causa del bloqueo.
     *
     * @return Cadena de texto con el motivo detallado.
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Asigna el motivo o causa que justifica el bloqueo del alumno.
     *
     * @param motivo La descripción detallada del motivo a asignar.
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
