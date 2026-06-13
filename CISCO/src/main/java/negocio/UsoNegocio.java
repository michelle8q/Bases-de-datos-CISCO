/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import entidad.UsoEntidad;
import persistencia.IUsoDAO;
import persistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author luisf
 */
public class UsoNegocio implements IUsoNegocio {

    private IUsoDAO usoDAO;

    public UsoNegocio(IUsoDAO usoDAO) {
        this.usoDAO = usoDAO;
    }

    @Override
    public List<UsoEntidad> listarUsosActivos(int limite, int offset) throws NegocioException {
        try {
            List<UsoEntidad> usos = usoDAO.listarUsosActivos(limite, offset);
            if (usos.isEmpty()) {
                throw new NegocioException("No hay usos activos en este momento.");
            }
            return usos;
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    @Override
    public List<UsoEntidad> listarApartadosDelDia() throws NegocioException {
        try {
            List<UsoEntidad> apartados = usoDAO.listarApartadosDelDia();
            if (apartados.isEmpty()) {
                throw new NegocioException("No hay apartados registrados hoy.");
            }
            return apartados;
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
