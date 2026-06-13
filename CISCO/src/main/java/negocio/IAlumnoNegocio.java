/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import entidad.AlumnoEntidad;

/**
 *
 * @author cinca
 */
public interface IAlumnoNegocio {
    AlumnoEntidad buscarPorId(int id) throws NegocioException;
}
