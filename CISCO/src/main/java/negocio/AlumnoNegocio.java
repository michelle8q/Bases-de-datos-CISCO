/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import entidad.AlumnoEntidad;
import persistencia.IAlumnoDAO;
import persistencia.PersistenciaException;

/**
 * Clase que implementa la interfaz {@link IAlumnoNegocio}. Contiene la lógica
 * de negocio para la gestión y validación de los alumnos en el sistema. Sirve
 * como intermediario entre la capa de presentación y la capa de acceso a datos
 * (DAO), asegurando que se cumplan las reglas de negocio aplicables antes de
 * realizar operaciones en la base de datos.
 *
 * @author cinca
 */
public class AlumnoNegocio implements IAlumnoNegocio {

    private IAlumnoDAO alumnoDAO;

    /**
     * Constructor de la clase AlumnoNegocio.
     *
     * * @param alumnoDAO Interfaz de acceso a datos para las operaciones
     * relacionadas con los alumnos.
     */
    public AlumnoNegocio(IAlumnoDAO alumnoDAO) {
        this.alumnoDAO = alumnoDAO;
    }

    /**
     * Busca un alumno en el sistema mediante su identificador único (ID).
     * Aplica reglas de negocio para asegurar que el ID sea válido, que el
     * alumno exista y que se encuentre actualmente inscrito.
     *
     * * @param id El identificador único del alumno a buscar.
     * @return Una instancia de {@link AlumnoEntidad} con los datos del alumno
     * encontrado.
     * @throws NegocioException Si el ID es inválido, si el alumno no existe, si
     * no está inscrito, o si ocurre un error en la capa de persistencia.
     */
    @Override
    public AlumnoEntidad buscarPorId(int id) throws NegocioException {
        try {

            this.validarBuscarPorId(id);

            AlumnoEntidad alumno = this.alumnoDAO.buscarAlumnoPorId(id);

            this.validarAlumno(alumno);

            return alumno;

        } catch (PersistenciaException ex) {
            System.getLogger(AlumnoNegocio.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new NegocioException(ex.getMessage());
        }
    }

    /**
     * Valida que el identificador del alumno sea un número positivo.
     *
     * * @param id El ID a evaluar.
     * @throws NegocioException Si el ID es menor o igual a cero.
     */
    private void validarBuscarPorId(int id) throws NegocioException {
        if (id <= 0) {
            throw new NegocioException("El id no es valido.");
        }
    }

    /**
     * Valida la existencia y el estado de inscripción de un alumno recuperado
     * de la base de datos.
     *
     * * @param alumno El objeto {@link AlumnoEntidad} a evaluar.
     * @throws NegocioException Si el objeto alumno es nulo o si su estado de
     * inscripción es falso.
     */
    private void validarAlumno(AlumnoEntidad alumno) throws NegocioException {
        if (alumno == null) {
            throw new NegocioException("No existe un alumno con ese id.");
        }
        if (!alumno.getEsInscrito()) {
            throw new NegocioException("El alumno no esta inscrito.");
        }
    }

    /**
     * Verifica que la contraseña proporcionada por un alumno coincida con la
     * registrada en el sistema. Antes de consultar la base de datos, valida que
     * la contraseña no sea nula ni esté vacía.
     *
     * * @param idAlumno El identificador único del alumno.
     * @param contrasena La cadena de texto con la contraseña a validar.
     * @return {@code true} si la contraseña es correcta, {@code false} en caso
     * contrario.
     * @throws NegocioException Si la contraseña está vacía o si ocurre un error
     * al consultar la capa de persistencia.
     */
    @Override
    public boolean verificarCredencialesAlumno(int idAlumno, String contrasena) throws NegocioException {
        try {
            if (contrasena == null || contrasena.trim().isEmpty()) {
                throw new NegocioException("La contraseña no puede estar vacía.");
            }
            return this.alumnoDAO.validarContrasena(idAlumno, contrasena);
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
