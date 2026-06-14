/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dto.UsoDTO;
import dto.ApartadoDTO;
import java.util.List;

/**
 *
 * @author luisf
 */
public interface IUsoNegocio {

    List<UsoDTO> listarUsosActivos(int limite, int offset) throws NegocioException;

    List<ApartadoDTO> listarApartadosDelDia(int limite, int offset) throws NegocioException;

}
