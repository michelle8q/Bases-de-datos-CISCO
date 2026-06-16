/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

/**
 * Entidad de dominio que modela los atributos de un programa o software
 * (sistema operativo, suites de ofimática, lenguajes, etc.) instalado en los equipos.
 * * @author cinca
 */
public class SoftwareEntidad {
    private int id;
    private String nombre;
/**
     * Constructor por defecto de la clase SoftwareEntidad.
     */
    public SoftwareEntidad() {
    }
 /**
     * Constructor de inicialización para registros de programas instalados.
     * * @param id     Identificador único del software en base de datos.
     * @param nombre Nombre comercial y versión técnica de la paquetería (ej. "Eclipse IDE 2024").
     */
    public SoftwareEntidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    
     /**
     * Obtiene el identificador de este software.
     * @return El ID del software.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador del software.
     * @param id El ID único a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre comercial de la aplicación.
     * @return Nombre del software.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna la nomenclatura del programa informático.
     * @param nombre Nombre técnico o comercial de la aplicación.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
