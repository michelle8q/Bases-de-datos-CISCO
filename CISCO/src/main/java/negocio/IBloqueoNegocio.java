/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import dto.BloquearAlumnoDTO;
import entidad.BloqueoEntidad;

/**
 *
 * @author cinca
 */
public interface IBloqueoNegocio {
    BloqueoEntidad guardar(BloquearAlumnoDTO bloqueo) throws NegocioException;
}
