/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

/**
 * Entidad de dominio que representa a un campus o plantel institucional de la
 * universidad. Actúa como contenedor del nivel superior para organizar los
 * distintos laboratorios de cómputo.
 *
 * * @author cinca
 */
public class PlantelEntidad {

    private int id;
    private String nombre;

    /**
     * Constructor por defecto de la clase PlantelEntidad.
     */
    public PlantelEntidad() {
    }

    /**
     * Constructor parametrizado para la inicialización básica de un plantel
     * educativo.
     *
     * * @param id Identificador numérico único del plantel.
     * @param nombre Nombre o denominación oficial del campus (ej. "Campus
     * Centro").
     */
    public PlantelEntidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador del plantel.
     *
     * @return El ID asignado.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador único del plantel.
     *
     * @param id El ID a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del campus universitario.
     *
     * @return Nombre del plantel.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre al campus o instalación.
     *
     * @param nombre Nombre del plantel.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
