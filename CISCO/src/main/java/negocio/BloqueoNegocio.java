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
 *
 * @author cinca
 */
public class BloqueoNegocio implements IBloqueoNegocio {
    private IBloqueoDAO bloqueoDAO;
    private IAlumnoDAO alumno;

    public BloqueoNegocio(IBloqueoDAO bloqueoDAO) {
        this.bloqueoDAO = bloqueoDAO;
    }
    
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
    
    private void reglasNegocioBloquearAlumno(BloquearAlumnoDTO bloqueo) throws NegocioException, PersistenciaException {
        
        AlumnoEntidad alumno = this.alumno.buscarAlumnoPorId(bloqueo.getIdAlumno());
        
        if (alumno == null) {
            throw new NegocioException("El alumno no existe.");
        }
        if (!alumno.getEsInscrito()) {
            throw new NegocioException("El alumno no esta inscrito.");
        }
        if(bloqueoDAO.BloqueoExistenteAlumno(bloqueo.getIdAlumno())) {
           throw new NegocioException("El alumno ya se encuentra bloqueado.");
        }
        
    }

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
    
    private void validarBuscarPorId(int id) throws NegocioException {
       if(id <= 0) {
           throw new NegocioException("El id no es valido.");
       }
    }

    @Override
    public List<BloqueoEntidad> listarBloqueos(String filtro, int limite, int pagina) throws NegocioException {
         try {
            
            this.validarListarBloqueados(filtro, limite, pagina);

            List<BloqueoEntidad> bloqueos = this.bloqueoDAO.listarBloqueos(filtro, pagina, limite);
            return bloqueos;
            
        } catch (PersistenciaException ex) {
            System.getLogger(AlumnoNegocio.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new NegocioException(ex.getMessage());
        }
    }
    
     private void validarListarBloqueados(String filtro, int pagina, int limite) throws NegocioException {
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
