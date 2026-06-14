/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dto.BloquearAlumnoDTO;
import entidad.BloqueoEntidad;
import java.util.List;

/**
 *
 * @author cinca
 */
public interface IBloqueoDAO {
    BloqueoEntidad bloquearAlumno(BloquearAlumnoDTO bloqueo) throws PersistenciaException;
    BloqueoEntidad buscarPorId(int id) throws PersistenciaException;
    BloqueoEntidad desbloquearAlumno(int id);
    List<BloqueoEntidad> listarBloqueos();
    boolean BloqueoExistenteAlumno(int idAlumno)  throws PersistenciaException;
}
