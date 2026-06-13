/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import entidad.AlumnoEntidad;

/**
 *
 * @author cinca
 */
public interface IAlumnoDAO {
    AlumnoEntidad buscarAlumnoPorId(int id) throws PersistenciaException;
    
}
