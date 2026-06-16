/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 * Objeto de Transferencia de Datos (DTO) optimizado para proyecciones masivas o
 * componentes visuales de tipo tabla (grids/listados) de los equipos de
 * cómputo. Agrupa los campos mínimos indispensables para ser procesados por
 * interfaces de usuario.
 *
 * * @author luisf
 */
public class ListarEquipoDTO {

    private int id;
    private Integer numeroComputadora;
    private String direccionIP;
    private String estado;

    /**
     * Constructor por defecto de la clase ListarEquipoDTO.
     */
    public ListarEquipoDTO() {
    }

    /**
     * Constructor parametrizado completo para agilizar el mapeo desde capas de
     * persistencia.
     *
     * * @param id El identificador único en la base de datos del equipo.
     * @param numeroComputadora El número asignado al equipo dentro del
     * laboratorio.
     * @param direccionIP La dirección de red IP de la máquina.
     * @param estado El estado de uso actual del equipo (ej. "Disponible").
     */
    public ListarEquipoDTO(int id, int numeroComputadora, String direccionIP, String estado) {
        this.id = id;
        this.numeroComputadora = numeroComputadora;
        this.direccionIP = direccionIP;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador de la base de datos del equipo.
     *
     * @return El ID del equipo.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador de la base de datos del equipo.
     *
     * @param id El ID único a asignar.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el número físico visible de la computadora.
     *
     * @return El número correlativo de la computadora.
     */
    public int getNumeroComputadora() {
        return numeroComputadora;
    }

    /**
     * Asigna el número físico identificador de la computadora.
     *
     * @param numeroComputadora El número a asignar.
     */
    public void setNumeroComputadora(int numeroComputadora) {
        this.numeroComputadora = numeroComputadora;
    }

    /**
     * Obtiene la dirección IP del equipo.
     *
     * @return Cadena de texto de la IP.
     */
    public String getDireccionIP() {
        return direccionIP;
    }

    /**
     * Asigna la dirección IP de red al objeto.
     *
     * @param direccionIP La IP en formato String a establecer.
     */
    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    /**
     * Obtiene el estado actual asignado al equipo.
     *
     * @return El estado actual (ej. "Disponible", "Ocupado").
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Modifica o asigna el estado operativo del equipo.
     *
     * @param estado El nuevo estado de texto a asignar.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
