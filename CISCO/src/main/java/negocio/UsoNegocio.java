/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dto.ApartadoDTO;
import dto.UsoDTO;
import entidad.UsoEntidad;
import java.util.ArrayList;
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
    public List<UsoDTO> listarUsosActivos(int limite, int offset) throws NegocioException {
        try {
            List<UsoEntidad> usos = usoDAO.listarUsosActivos(limite, offset);
            if (usos.isEmpty()) {
                throw new NegocioException("No hay usos activos en este momento.");
            }

            List<UsoDTO> listaDTOs = new ArrayList<>();

            for (UsoEntidad uso : usos) {
                String nombreCompleto = uso.getAlumno().getNombres() + " "
                        + uso.getAlumno().getApellidoPaterno() + " "
                        + uso.getAlumno().getApellidoMaterno();

                UsoDTO dto = new UsoDTO(
                        uso.getEquipo().getId(),
                        uso.getAlumno().getId(),
                        nombreCompleto,
                        uso.getFechaHoraInicio()
                );

                listaDTOs.add(dto);
            }

            return listaDTOs;
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    public List<ApartadoDTO> listarApartadosDelDia(int limite, int offset) throws NegocioException {
        try {
            List<UsoEntidad> apartados = usoDAO.listarApartadosDelDia(limite, offset);

            if (apartados.isEmpty()) {
                throw new NegocioException("No hay apartados registrados hoy o no hay más páginas.");
            }

            List<ApartadoDTO> listaDTOs = new ArrayList<>();

            for (UsoEntidad uso : apartados) {

                String nombreCompleto = uso.getAlumno().getNombres() + " "
                        + uso.getAlumno().getApellidoPaterno() + " "
                        + uso.getAlumno().getApellidoMaterno();

                String estado = "Pendiente";
                if (uso.getFechaHoraInicio() != null && uso.getFechaHoraFin() == null) {
                    estado = "Activo";
                } else if (uso.getFechaHoraFin() != null) {
                    estado = "Finalizado";
                }

                ApartadoDTO dto = new ApartadoDTO(
                        uso.getEquipo().getId(),
                        uso.getAlumno().getId(),
                        nombreCompleto,
                        uso.getFechaHoraInicio(),
                        uso.getFechaHoraFin(),
                        estado
                );

                listaDTOs.add(dto);
            }

            return listaDTOs;
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

}
