/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;
import entidad.UsoEntidad;
import java.util.List;
/**
 *
 * @author luisf
 */
public interface IUsoNegocio {
    List<UsoEntidad> listarUsosActivos(int limite, int offset) throws NegocioException;
    List<UsoEntidad> listarApartadosDelDia() throws NegocioException;
}