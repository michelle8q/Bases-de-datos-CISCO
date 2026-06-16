package negocio;

import dto.ApartadoDTO;
import dto.UsoDTO;
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

    public UsoNegocio(IUsoDAO usoDAO, IIpDAO ipDAO, IEquipoDAO equipoDAO, IAlumnoDAO alumnoDAO) {
        this.usoDAO = usoDAO;
        this.ipDAO = ipDAO;
        this.equipoDAO = equipoDAO;
        this.alumnoDAO = alumnoDAO;
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
    
    
    private void validarIP(String ip) throws NegocioException {
        if (ip == null || ip.trim().isEmpty()) {
            throw new NegocioException("La dirección IP del equipo no es válida.");
        }
    }
    
    
    @Override
    public void cancelarApartadoEquipo(String ip) throws NegocioException {
        try {
             validarIP(ip);
            this.usoDAO.eliminarUsoActivoPorIP(ip);
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
    
    @Override
    public void finalizarSesionEquipo(String ip) throws NegocioException {
        try {
            validarIP(ip);
            
            List<UsoEntidad> activos = this.usoDAO.listarUsosActivos(100, 0, ip);
            
            UsoEntidad usoActual = null;
            for (UsoEntidad uso : activos) {
                if (uso.getEquipo() != null && ip.equals(uso.getEquipo().getDireccionIP())) {
                    usoActual = uso;
                    break;
                }
            }

            if (usoActual == null) {
                List<UsoEntidad> delDia = this.usoDAO.listarApartadosDelDia(100, 0, ip);
                for (UsoEntidad uso : delDia) {
                    if (uso.getFechaHoraFin() == null && uso.getEquipo() != null && ip.equals(uso.getEquipo().getDireccionIP())) {
                        usoActual = uso;
                        break;
                    }
                }
            }

            validarUsoExistente(usoActual, "No se encontró ningún apartado pendiente para la IP: " + ip);
            
            boolean exito = this.usoDAO.finalizarSesion(usoActual.getId());
            
            validarOperacionExitosa(exito, "No se pudo iniciar la sesión en la base de datos.");
            
        } catch (PersistenciaException e) {
            throw new NegocioException("Error en la capa de persistencia: " + e.getMessage());
        }
    }
    
    @Override
    public void iniciarSesionEquipo(String ip) throws NegocioException {
        try {
            validarIP(ip);

            List<UsoEntidad> delDia = this.usoDAO.listarApartadosDelDia(100, 0, ip);
            UsoEntidad usoActual = null;

            for (UsoEntidad uso : delDia) {
                if (uso.getFechaHoraInicio() == null && uso.getFechaHoraFin() == null 
                    && uso.getEquipo() != null && ip.equals(uso.getEquipo().getDireccionIP())) {
                    usoActual = uso;
                    break;
                }
            }
            
            validarUsoExistente(usoActual, "No se encontró ningún apartado pendiente para la IP: " + ip);
            
            boolean exito = this.usoDAO.iniciarSesion(usoActual.getId());
            
            validarOperacionExitosa(exito, "No se pudo iniciar la sesión en la base de datos.");
            
        } catch (PersistenciaException e) {
            throw new NegocioException("Error en la capa de persistencia: " + e.getMessage());
        }
    }
    

    private void validarUsoExistente(UsoEntidad uso, String mensaje) throws NegocioException {
        if (uso == null) {
            throw new NegocioException(mensaje);
        }
    }
    
    private void validarOperacionExitosa(boolean exito, String mensaje) throws NegocioException {
        if (!exito) {
            throw new NegocioException(mensaje);
        }
    }

}
