/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

/**
 * Entidad de dominio que representa a un estudiante o alumno dentro del
 * sistema. Almacena sus datos personales, credenciales de acceso, estado de
 * inscripción y la carrera universitaria a la que pertenece.
 *
 * * @author cinca
 */
public class AlumnoEntidad {

    private int id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String contrasena;
    private Boolean esInscrito;
    private CarreraEntidad carrera;

    /**
     * Constructor por defecto de la clase AlumnoEntidad.
     */
    public AlumnoEntidad() {
    }

    /**
     * Constructor completo para inicializar un alumno con todos sus atributos.
     *
     * * @param id Identificador único del alumno en la base de datos.
     * @param nombres Nombre o nombres del alumno.
     * @param apellidoPaterno Primer apellido del alumno.
     * @param apellidoMaterno Segundo apellido del alumno.
     * @param contrasena Contraseña cifrada o clave de acceso del alumno.
     * @param esInscrito Estado de inscripción del alumno (true si está activo).
     * @param carrera Instancia de {@link CarreraEntidad} a la que pertenece el
     * alumno.
     */
    public AlumnoEntidad(int id, String nombres, String apellidoPaterno, String apellidoMaterno,
            String contrasena, Boolean esInscrito, CarreraEntidad carrera) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.contrasena = contrasena;
        this.esInscrito = esInscrito;
        this.carrera = carrera;
    }

    /**
     * Constructor parcial enfocado únicamente en la identificación y datos
     * nominales básicos del alumno.
     *
     * * @param id Identificador único del alumno.
     * @param nombres Nombre o nombres del alumno.
     * @param apellidoPaterno Primer apellido del alumno.
     * @param apellidoMaterno Segundo apellido del alumno.
     */
    public AlumnoEntidad(int id, String nombres, String apellidoPaterno, String apellidoMaterno) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Genera y retorna la concatenación del nombre completo del alumno.
     *
     * * @return Cadena de texto con el formato "Nombres ApellidoPaterno
     * ApellidoPaterno".
     */
    public String getNombreCompleto() {
        String nombreCompleto = nombres + " " + apellidoPaterno + " " + apellidoPaterno;
        return nombreCompleto;
    }

    /**
     * Obtiene el identificador único del alumno.
     *
     * @return El ID del alumno.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador único del alumno.
     *
     * @param id El ID único a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene los nombres del estudiante.
     *
     * @return Una cadena de texto con los nombres del alumno.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Asigna los nombres del estudiante.
     *
     * @param nombres Los nombres a establecer.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene el apellido paterno del alumno.
     *
     * @return El primer apellido del alumno.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Asigna el apellido paterno del alumno.
     *
     * @param apellidoPaterno El primer apellido a establecer.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del alumno.
     *
     * @return El segundo apellido del alumno.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Asigna el apellido materno del alumno.
     *
     * @param apellidoMaterno El segundo apellido a establecer.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene la contraseña de acceso del alumno.
     *
     * @return La contraseña guardada.
     */
    public String getContraseña() {
        return contrasena;
    }

    /**
     * Asigna la contraseña de acceso del alumno.
     *
     * @param contraseña La nueva contraseña a establecer.
     */
    public void setContraseña(String contraseña) {
        this.contrasena = contraseña;
    }

    /**
     * Consulta si el alumno se encuentra actualmente inscrito en la
     * institución.
     *
     * @return true si está inscrito, false o null en caso contrario.
     */
    public Boolean getEsInscrito() {
        return esInscrito;
    }

    /**
     * Modifica el estado de inscripción del alumno en el sistema.
     *
     * @param esInscrito El estado de inscripción a establecer.
     */
    public void setEsInscrito(Boolean esInscrito) {
        this.esInscrito = esInscrito;
    }

    /**
     * Obtiene la entidad de la carrera universitaria que cursa el alumno.
     *
     * @return Objeto {@link CarreraEntidad} asociado al alumno.
     */
    public CarreraEntidad getCarrera() {
        return carrera;
    }

    /**
     * Vincula una carrera universitaria al perfil del alumno.
     *
     * @param carrera La instancia de {@link CarreraEntidad} correspondientes.
     */
    public void setCarrera(CarreraEntidad carrera) {
        this.carrera = carrera;
    }

}
