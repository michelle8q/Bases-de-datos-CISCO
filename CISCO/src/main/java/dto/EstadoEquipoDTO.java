package dto;

import entidad.AlumnoEntidad;

/**
 * Objeto de Transferencia de Datos (DTO) estructurado para describir la
 * situación operativa en tiempo real de una estación de trabajo de cómputo.
 * Muestra su disponibilidad, dirección de red y la entidad del estudiante
 * asociado si está en uso.
 *
 * * @author piña
 */
public class EstadoEquipoDTO {

    private int numero;
    private String laboratorio;
    private String estado;
    private AlumnoEntidad alumno; // Se quedará vacío si está disponible
    private String ip;

    /**
     * Constructor parametrizado para instanciar la radiografía de estado de un
     * equipo.
     *
     * * @param numero El número físico/etiqueta asignado a la computadora.
     * @param laboratorio El nombre del laboratorio al que pertenece la
     * estación.
     * @param estado El estado operativo actual (ej. "Disponible", "Ocupado",
     * "Mantenimiento").
     * @param alumno La entidad del alumno {@link AlumnoEntidad} que ocupa el
     * equipo (null si está libre).
     */
    public EstadoEquipoDTO(int numero, String laboratorio, String estado, AlumnoEntidad alumno) {
        this.numero = numero;
        this.laboratorio = laboratorio;
        this.estado = estado;
        this.alumno = alumno;
    }

    /**
     * Obtiene el número físico de la computadora.
     *
     * @return El número de la estación de cómputo.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Obtiene el nombre del laboratorio donde está ubicada la estación.
     *
     * @return El nombre del laboratorio.
     */
    public String getLaboratorio() {
        return laboratorio;
    }

    /**
     * Obtiene la condición o estado actual de disponibilidad del equipo.
     *
     * @return El estado del equipo.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Obtiene la información completa del alumno que está usando el equipo.
     *
     * @return El objeto {@link AlumnoEntidad} asociado, o null si está libre.
     */
    public AlumnoEntidad getAlumno() {
        return alumno;
    }

    /**
     * Obtiene la dirección IP lógica configurada en el equipo.
     *
     * @return Cadena de texto con la dirección IP.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Asigna el número físico o de etiqueta a la computadora.
     *
     * @param numero El número de la computadora a asignar.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Asigna el laboratorio de ubicación correspondiente.
     *
     * @param laboratorio El nombre del laboratorio a asignar.
     */
    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * Modifica el estado operativo del equipo.
     *
     * @param estado El nuevo estado a asignar (ej. "En Mantenimiento").
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Asigna un alumno a la sesión actual del equipo.
     *
     * * @param nombreAlumno Parámetro de entrada (Nota: La lógica interna
     * asocia la propiedad de clase).
     */
    public void setAlumno(String nombreAlumno) {
        this.alumno = alumno;
    }

    /**
     * Asigna la dirección IP de red correspondiente al equipo.
     *
     * @param ip La dirección IP en formato de texto a establecer.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

}
