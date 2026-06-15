/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import dto.BloquearAlumnoDTO;
import entidad.BloqueoEntidad;
import java.util.List;

/**
 *
 * @author cinca
 */
public interface IBloqueoNegocio {
    BloqueoEntidad bloquear(BloquearAlumnoDTO bloqueo) throws NegocioException;
    BloqueoEntidad desbloquear(int id) throws NegocioException;
    List<BloqueoEntidad> listarBloqueos(String filtro, int limite, int offset) throws NegocioException;
}
