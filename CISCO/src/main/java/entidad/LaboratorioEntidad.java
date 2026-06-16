/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.time.LocalTime;

/**
 * Entidad de dominio que define un Laboratorio físico (centro de cómputo)
 * dentro del sistema. Contiene reglas operativas específicas como el horario
 * reglamentario de servicio y contraseñas maestras de liberación.
 *
 * * @author cinca
 */
public class LaboratorioEntidad {

    private int id;
    private String nombre;
    private LocalTime horaInicioServicio;
    private LocalTime horaFinServicio;
    private String contrasena;
    private PlantelEntidad plantel;

    /**
     * Constructor por defecto de la clase LaboratorioEntidad.
     */
    public LaboratorioEntidad() {
    }

    /**
     * Constructor completo para inicializar un laboratorio de cómputo.
     *
     * * @param id Identificador único del laboratorio en el sistema.
     * @param nombre Nombre identificativo o sala del laboratorio (ej.
     * "Laboratorio A").
     * @param horaInicioServicio Hora exacta en la que se abre o inicia el
     * servicio público.
     * @param horaFinServicio Hora exacta en la que concluyen actividades y
     * cierra el servicio.
     * @param contrasena Clave o credencial requerida para administrar o
     * desbloquear terminales en esta sala.
     * @param plantel Instancia de {@link PlantelEntidad} de pertenencia
     * geográfica/institucional.
     */
    public LaboratorioEntidad(int id, String nombre, LocalTime horaInicioServicio, LocalTime horaFinServicio, String contrasena, PlantelEntidad plantel) {
        this.id = id;
        this.nombre = nombre;
        this.horaInicioServicio = horaInicioServicio;
        this.horaFinServicio = horaFinServicio;
        this.contrasena = contrasena;
        this.plantel = plantel;
    }

    /**
     * Obtiene el identificador único del laboratorio.
     *
     * @return El ID del laboratorio.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador único del laboratorio.
     *
     * @param id El ID a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del laboratorio o centro de cómputo.
     *
     * @return Nombre del espacio físico.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nombre descriptivo al laboratorio.
     *
     * @param nombre Nombre descriptivo a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la hora en que se habilita el acceso general de este laboratorio.
     *
     * @return Instancia de {@link LocalTime} con el inicio de actividades.
     */
    public LocalTime getHoraInicioServicio() {
        return horaInicioServicio;
    }

    /**
     * Establece la hora diaria de apertura o arranque de operaciones en el
     * laboratorio.
     *
     * @param horaInicioServicio Hora de apertura.
     */
    public void setHoraInicioServicio(LocalTime horaInicioServicio) {
        this.horaInicioServicio = horaInicioServicio;
    }

    /**
     * Obtiene la hora estipulada de finalización de actividades en el
     * laboratorio.
     *
     * @return Instancia de {@link LocalTime} con el cierre.
     */
    public LocalTime getHoraFinServicio() {
        return horaFinServicio;
    }

    /**
     * Establece la hora reglamentaria de cierre o conclusión del servicio.
     *
     * @param horaFinServicio Hora de cierre.
     */
    public void setHoraFinServicio(LocalTime horaFinServicio) {
        this.horaFinServicio = horaFinServicio;
    }

    /**
     * Obtiene la contraseña maestra o frase de validación del laboratorio.
     *
     * @return Clave del laboratorio.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Define la contraseña maestra requerida para operar cambios o desbloqueos
     * locales.
     *
     * @param contrasena Contraseña o token de seguridad.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el plantel físico o campus universitario donde reside el
     * laboratorio.
     *
     * @return Entidad del campus {@link PlantelEntidad}.
     */
    public PlantelEntidad getPlantel() {
        return plantel;
    }

    /**
     * Asocia de manera formal un plantel institucional a este laboratorio.
     *
     * @param plantel Entidad de tipo {@link PlantelEntidad}.
     */
    public void setPlantel(PlantelEntidad plantel) {
        this.plantel = plantel;
    }
}
