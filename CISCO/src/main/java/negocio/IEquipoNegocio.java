/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import dto.ListarEquipoDTO;
import java.util.List;

/**
 *
 * @author piña luis
 */
public interface IEquipoNegocio {

    List<ListarEquipoDTO> buscarEquiposPaginados(String nombreLaboratorio, String filtro, int limite, int pagina) throws Exception;

    int obtenerTotalPaginas(String nombreLaboratorio, String filtro, int limite) throws Exception;

    void cambiarEstadoEquipo(int idEquipo, String nuevoEstado) throws Exception;

    List<String> obtenerNombresLaboratorios() throws Exception;

}
