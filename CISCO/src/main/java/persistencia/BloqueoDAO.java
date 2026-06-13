/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dto.BloquearAlumnoDTO;
import entidad.BloqueoEntidad;
import java.util.List;

/**
 *
 * @author cinca
 */
public class BloqueoDAO implements IBloqueoDAO {
    
    private IConexionBD conexion;

    public BloqueoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public BloqueoEntidad bloquearAlumno(BloquearAlumnoDTO bloqueo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BloqueoEntidad desbloquearAlumno(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<BloqueoEntidad> listarBloqueos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
