/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

/**
 * Entidad de dominio que representa un programa académico o carrera
 * universitaria impartida dentro de la institución. Determina restricciones
 * operativas como el límite de tiempo diario asignado para el uso de
 * laboratorios.
 *
 * * @author cinca
 */
public class CarreraEntidad {

    private int id;
    private String nombre;
    private String telefono;
    private int tiempoDiario;

    /**
     * Constructor por defecto de la clase CarreraEntidad.
     */
    public CarreraEntidad() {
    }

    /**
     * Constructor completo para instanciar una carrera académica.
     *
     * * @param id Identificador único de la carrera en base de datos.
     * @param nombre Nombre oficial de la carrera (ej. "Ingeniería en
     * Software").
     * @param telefono Contacto o teléfono de la coordinación de la carrera.
     * @param tiempoDiario Cantidad de minutos permitidos por día para el uso de
     * equipos.
     */
    public CarreraEntidad(int id, String nombre, String telefono, int tiempoDiario) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tiempoDiario = tiempoDiario;
    }

    /**
     * Obtiene el identificador de la carrera.
     *
     * @return El ID único.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador de la carrera.
     *
     * @param id El ID único a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del programa académico.
     *
     * @return Nombre de la carrera.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre a la carrera.
     *
     * @param nombre Nombre oficial de la carrera.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número de teléfono de la coordinación académica.
     *
     * @return Teléfono de contacto.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Asigna un número telefónico de contacto para la carrera.
     *
     * @param telefono El número de teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el tiempo límite diario en minutos asignado a esta carrera.
     *
     * @return Cuota de tiempo diario en minutos.
     */
    public int getTiempoDiario() {
        return tiempoDiario;
    }

    /**
     * Define el límite reglamentario de tiempo de uso en minutos por día para
     * la carrera.
     *
     * @param tiempoDiario Cantidad de minutos permitidos.
     */
    public void setTiempoDiario(int tiempoDiario) {
        this.tiempoDiario = tiempoDiario;
    }

}
