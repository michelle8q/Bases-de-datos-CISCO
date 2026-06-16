/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dto.BloquearAlumnoDTO;
import entidad.AlumnoEntidad;
import entidad.BloqueoEntidad;
import java.util.List;
import persistencia.IAlumnoDAO;
import persistencia.IBloqueoDAO;
import persistencia.PersistenciaException;

/**
 * Clase que implementa la interfaz {@link IBloqueoNegocio}. Contiene la lógica
 * de negocio responsable de la gestión de bloqueos a los alumnos dentro del
 * sistema. Se encarga de validar reglas operativas y de integridad antes de
 * solicitar modificaciones a la capa de persistencia (DAO).
 *
 * @author cinca
 */
public class BloqueoNegocio implements IBloqueoNegocio {

    private IBloqueoDAO bloqueoDAO;
    private IAlumnoDAO alumno;

    /**
     * Constructor de la clase BloqueoNegocio.
     *
     * * @param bloqueoDAO Interfaz de acceso a datos para operaciones de
     * bloqueo.
     * @param alumno Interfaz de acceso a datos para validar información de los
     * alumnos.
     */
    public BloqueoNegocio(IBloqueoDAO bloqueoDAO, IAlumnoDAO alumno) {
        this.bloqueoDAO = bloqueoDAO;
        this.alumno = alumno;
    }

    /**
     * Registra un nuevo bloqueo para un alumno en el sistema. Previamente,
     * verifica que los datos de entrada sean correctos y que se cumplan todas
     * las reglas de negocio estipuladas.
     *
     * * @param bloqueo Objeto DTO que contiene la información y el motivo del
     * bloqueo.
     * @return Una entidad {@link BloqueoEntidad} con los datos del bloqueo
     * generado.
     * @throws NegocioException Si no se cumplen las validaciones, si las reglas
     * de negocio fallan o si ocurre un error en la base de datos.
     */
    @Override
    public BloqueoEntidad bloquear(BloquearAlumnoDTO bloqueo) throws NegocioException {
        try {
            this.validarBloqueo(bloqueo);
            this.reglasNegocioBloquearAlumno(bloqueo);

            BloqueoEntidad alumnoBloqueado = this.bloqueoDAO.bloquearAlumno(bloqueo);

            return alumnoBloqueado;

        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }

    }

    /**
     * Valida que la estructura y los datos básicos del objeto de transferencia
     * (DTO) no vengan vacíos ni contengan información anómala antes de
     * proceder.
     *
     * * @param bloqueo El DTO con la información a validar.
     * @throws NegocioException Si el objeto es nulo, el ID es inválido o el
     * motivo excede la longitud permitida.
     */
    private void validarBloqueo(BloquearAlumnoDTO bloqueo) throws NegocioException {
        if (bloqueo == null) {
            throw new NegocioException("El bloqueo del alumno no puede quedar vacio.");
        }

        if (bloqueo.getIdAlumno() <= 0) {
            throw new NegocioException("El id del alumno no es valido.");
        }

        if (bloqueo.getMotivo() == null || bloqueo.getMotivo().trim().isEmpty()) {
            throw new NegocioException("El motivo del bloqueo del alumno es obligatorio.");
        }

        if (bloqueo.getMotivo().length() > 150) {
            throw new NegocioException("El motivo no puede ser mayor a la longitud de 150 caracteres.");
        }

    }

    /**
     * Verifica que la solicitud de bloqueo cumpla estrictamente con las reglas
     * de negocio operativas de la institución.
     *
     * * @param bloqueo El DTO con el ID del alumno que se desea bloquear.
     * @throws NegocioException Si el alumno no existe, no está inscrito o ya
     * tiene un bloqueo activo.
     * @throws PersistenciaException Si ocurre un error al consultar la capa de
     * datos.
     */
    private void reglasNegocioBloquearAlumno(BloquearAlumnoDTO bloqueo) throws NegocioException, PersistenciaException {

        AlumnoEntidad alumno = this.alumno.buscarAlumnoPorId(bloqueo.getIdAlumno());

        if (alumno == null) {
            throw new NegocioException("El alumno no existe.");
        }
        if (!alumno.getEsInscrito()) {
            throw new NegocioException("El alumno no esta inscrito.");
        }
        if (bloqueoDAO.BloqueoExistenteAlumno(bloqueo.getIdAlumno())) {
            throw new NegocioException("El alumno ya se encuentra bloqueado.");
        }

    }

    /**
     * Retira el bloqueo activo de un alumno mediante su identificador.
     *
     * * @param id El identificador único del bloqueo o del alumno (según la
     * implementación DAO).
     * @return El objeto {@link BloqueoEntidad} con el estado actualizado tras
     * ser desbloqueado.
     * @throws NegocioException Si el ID es inválido o si ocurre un problema
     * durante la operación.
     */
    @Override
    public BloqueoEntidad desbloquear(int id) throws NegocioException {
        try {

            this.validarBuscarPorId(id);

            BloqueoEntidad bloqueo = this.bloqueoDAO.desbloquearAlumno(id);
            return bloqueo;

        } catch (PersistenciaException ex) {
            System.getLogger(AlumnoNegocio.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new NegocioException(ex.getMessage());
        }

    }

    /**
     * Valida que el identificador ingresado para la búsqueda sea un número
     * positivo.
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
     * Recupera una lista paginada de los bloqueos registrados en el sistema,
     * aplicando un filtro de búsqueda opcional.
     *
     * * @param filtro Cadena de texto para filtrar los resultados (ej. nombre
     * o motivo).
     * @param limite Cantidad máxima de registros a recuperar por página.
     * @param pagina El índice de la página (offset) que se desea visualizar.
     * @return Una lista de {@link BloqueoEntidad} correspondiente a los
     * parámetros de búsqueda.
     * @throws NegocioException Si los parámetros de paginación son inválidos o
     * ocurre un error en la base de datos.
     */
    @Override
    public List<BloqueoEntidad> listarBloqueos(String filtro, int limite, int pagina) throws NegocioException {
        try {

            this.validarListarBloqueados(filtro, limite, pagina);

            List<BloqueoEntidad> bloqueos = this.bloqueoDAO.listarBloqueos(filtro, limite, pagina);
            return bloqueos;

        } catch (PersistenciaException ex) {
            System.getLogger(AlumnoNegocio.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new NegocioException(ex.getMessage());
        }
    }

    /**
     * Valida que los parámetros de paginación y límite para las listas se
     * encuentren dentro de los rangos lógicos permitidos por el sistema.
     *
     * * @param filtro El criterio de búsqueda.
     * @param limite El número de registros por página.
     * @param pagina La página solicitada.
     * @throws NegocioException Si la página es negativa, o si el límite está
     * fuera de los rangos (0-200).
     */
    private void validarListarBloqueados(String filtro, int limite, int pagina) throws NegocioException {
        if (pagina < 0) {
            throw new NegocioException("El número de página no puede ser negativo.");
        }
        if (limite <= 0) {
            throw new NegocioException("El límite debe ser mayor que cero.");
        }
        if (limite > 200) {
            throw new NegocioException("El límite máximo permitido es 200.");
        }
    }
}
