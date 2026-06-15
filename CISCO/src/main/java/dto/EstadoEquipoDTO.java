package dto;

/**
 *
 * @author piña
 */
public class EstadoEquipoDTO {

    private int numero;
    private String laboratorio;
    private String estado; 
    private String nombreAlumno; // Se quedará vacío si está disponible
    private String ip;

    public EstadoEquipoDTO(int numero, String laboratorio, String estado, String nombreAlumno) {
        this.numero = numero;
        this.laboratorio = laboratorio;
        this.estado = estado;
        this.nombreAlumno = nombreAlumno;
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

    public String getNombreAlumno() {
        return nombreAlumno;
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
        this.nombreAlumno = nombreAlumno;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }



}
