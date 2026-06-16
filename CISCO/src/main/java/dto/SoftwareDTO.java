/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula la información de un
 * programa o aplicación de software instalado en los equipos de los
 * laboratorios.
 *
 * * @author luisf
 */
public class SoftwareDTO {

    private int id;
    private String nombre;

    /**
     * Constructor por defecto de la clase SoftwareDTO.
     */
    public SoftwareDTO() {
    }

    /**
     * Constructor parametrizado para la inicialización completa de un registro
     * de software.
     *
     * * @param id Identificador único del registro del software.
     * @param nombre Nombre comercial o técnico de la aplicación (ej.
     * "NetBeans", "Matlab").
     */
    public SoftwareDTO(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador exclusivo del software.
     *
     * @return El ID del software.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador del software.
     *
     * @param id El ID único a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del software o aplicación.
     *
     * @return El nombre del software.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre comercial o técnico del software.
     *
     * @param nombre El nombre del programa informático a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
