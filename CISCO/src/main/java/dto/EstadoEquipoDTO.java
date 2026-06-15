package dto;

import entidad.AlumnoEntidad;

/**
 *
 * @author piña
 */
public class EstadoEquipoDTO {

    private int numero;
    private String laboratorio;
    private String estado; 
    private AlumnoEntidad alumno; // Se quedará vacío si está disponible
    private String ip;

    public EstadoEquipoDTO(int numero, String laboratorio, String estado, AlumnoEntidad alumno) {
        this.numero = numero;
        this.laboratorio = laboratorio;
        this.estado = estado;
        this.alumno = alumno;
    }

    public int getNumero() {
        return numero;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public String getEstado() {
        return estado;
    }

    public AlumnoEntidad getNombreAlumno() {
        return alumno;
    }

    public String getIp() {
        return ip;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.alumno = alumno;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }



}
