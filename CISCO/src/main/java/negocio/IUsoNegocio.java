
package negocio;

import dto.UsoDTO;
import dto.ApartadoDTO;
import dto.EstadoEquipoDTO;
import java.util.List;

/**
 *
 * @author luisf piña
 */
public interface IUsoNegocio {
    
    public String determinarPantalla(String ip) throws NegocioException;

    List<UsoDTO> listarUsosActivos(int limite, int offset, String filtroBusqueda) throws NegocioException;

    List<ApartadoDTO> listarApartadosDelDia(int limite, int offset, String filtroBusqueda) throws NegocioException;

   void cancelarApartadoEquipo(String ip) throws NegocioException;
   
   void finalizarSesionEquipo(String ip) throws NegocioException;
   
   void iniciarSesionEquipo(String ip) throws NegocioException;
}
