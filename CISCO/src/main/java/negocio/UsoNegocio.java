
package negocio;

import dto.ApartadoDTO;
import dto.EstadoEquipoDTO;
import dto.UsoDTO;
import entidad.AlumnoEntidad;
import entidad.UsoEntidad;
import java.util.ArrayList;
import persistencia.IUsoDAO;
import persistencia.PersistenciaException;
import java.util.List;
import persistencia.IAlumnoDAO;
import persistencia.IEquipoDAO;
import persistencia.IIpDAO;

/**
 *
 * @author luisf piña
 */
public class UsoNegocio implements IUsoNegocio {

    private IUsoDAO usoDAO;
    private IIpDAO ipDAO;
    private IEquipoDAO equipoDAO;
    private IAlumnoDAO alumnoDAO;
    

    public UsoNegocio(IUsoDAO usoDAO, IIpDAO ipDAO) {
        this.usoDAO = usoDAO;
        this.ipDAO = ipDAO;
    }

    @Override
    public List<UsoDTO> listarUsosActivos(int limite, int offset, String filtroBusqueda) throws NegocioException {
        try {
            List<UsoEntidad> usos = usoDAO.listarUsosActivos(limite, offset, filtroBusqueda);
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
    public List<ApartadoDTO> listarApartadosDelDia(int limite, int offset, String filtroBusqueda) throws NegocioException {
        try {
            List<UsoEntidad> apartados = usoDAO.listarApartadosDelDia(limite, offset, filtroBusqueda);

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

    @Override
    public String determinarPantalla(String ip) throws NegocioException {
        try {
            String pantalla = ipDAO.obtenerPantallaPorIP(ip);

            if (pantalla == null) {
                return "SI_ACCESO";
            }

            return pantalla;

        } catch (PersistenciaException e) {
            System.err.println("Error en Negocio al determinar la pantalla para la IP: " + ip);
            throw new NegocioException("No se pudo validar la IP en el sistema: " + e.getMessage());
        }
    }

    @Override
    public EstadoEquipoDTO obtenerEstadoEquipo(String ip) throws NegocioException {
        try {

            int id = equipoDAO.obtenerIDAlumnoApartado(ip);
            String ubicacion = equipoDAO.obtenerLaboratorio(ip);
            String estado = equipoDAO.obtenerEstado(ip);
            AlumnoEntidad alumno = alumnoDAO.buscarAlumnoPorId(id); 

            return new EstadoEquipoDTO(id, ubicacion, estado, alumno);

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al procesar el estado del equipo: " + e.getMessage());
        }
    }

}
