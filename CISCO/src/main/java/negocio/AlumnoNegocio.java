/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import entidad.AlumnoEntidad;
import persistencia.IAlumnoDAO;
import persistencia.PersistenciaException;

/**
 *
 * @author cinca
 */
public class AlumnoNegocio implements IAlumnoNegocio {
    private IAlumnoDAO alumnoDAO;

    public AlumnoNegocio(IAlumnoDAO alumnoDAO) {
        this.alumnoDAO = alumnoDAO;
    }
    
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
    
    private void validarBuscarPorId(int id) throws NegocioException {
       if(id <= 0) {
           throw new NegocioException("El id no es valido.");
       }
    }
    
    private void validarAlumno(AlumnoEntidad alumno) throws NegocioException {
        if(alumno == null) {
           throw new NegocioException("No existe un alumno con ese id.");
        }
        if(!alumno.getEsInscrito()) {
           throw new NegocioException("El alumno no esta inscrito.");
        }
    }

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
