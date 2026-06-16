package negocio;

import dto.ApartadoDTO;
import dto.EstadoEquipoDTO;
import dto.UsoDTO;
import entidad.AlumnoEntidad;
import entidad.EquipoEntidad;
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

        // Una sola búsqueda directa por IP
        UsoEntidad usoActual = this.usoDAO.buscarApartadoActivoPorIP(ip);

        validarUsoExistente(usoActual,
            "No se encontró ningún apartado activo o pendiente para la IP: " + ip);

        int idEquipo = usoActual.getEquipo().getId();
        this.usoDAO.eliminarUsoActivoPorIP(ip);
        this.equipoDAO.actualizarEstado(idEquipo, "Disponible");

    } catch (PersistenciaException e) {
        throw new NegocioException("Error en la capa de persistencia: " + e.getMessage());
    } catch (NegocioException e) {
        throw e;
    } catch (Exception e) {
        throw new NegocioException("Error al cancelar el apartado del equipo: " + e.getMessage());
    }
}

    @Override
public void finalizarSesionEquipo(String ip) throws NegocioException {
    try {
        validarIP(ip);

        UsoEntidad usoActual = this.usoDAO.buscarApartadoActivoPorIP(ip);

        validarUsoExistente(usoActual,
            "No se encontró ningún apartado pendiente para la IP: " + ip);

        boolean exito = this.usoDAO.finalizarSesion(usoActual.getId());
        validarOperacionExitosa(exito, "No se pudo finalizar la sesión en la base de datos.");

    } catch (PersistenciaException e) {
        throw new NegocioException("Error en la capa de persistencia: " + e.getMessage());
    }
}

    @Override
    public void iniciarSesionEquipo(String ip) throws NegocioException {
        try {
            validarIP(ip);

            UsoEntidad usoActual = this.usoDAO.buscarApartadoActivoPorIP(ip);

            if (usoActual != null && usoActual.getFechaHoraInicio() != null) {
                usoActual = null;
            }

            validarUsoExistente(usoActual,
                    "No se encontró ningún apartado pendiente para la IP: " + ip);

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
    
    @Override
public void registrarApartado(int idEquipo, int idAlumno) throws NegocioException {
    try {
        UsoEntidad nuevo = new UsoEntidad();
        nuevo.setFechaHoraApartado(java.time.LocalDateTime.now());

        AlumnoEntidad alumno = new AlumnoEntidad();
        alumno.setId(idAlumno);
        nuevo.setAlumno(alumno);

        EquipoEntidad equipo = new EquipoEntidad();
        equipo.setId(idEquipo);
        nuevo.setEquipo(equipo);

        boolean exito = this.usoDAO.registrarApartado(nuevo);
        validarOperacionExitosa(exito, "No se pudo registrar el apartado.");

    } catch (PersistenciaException e) {
        throw new NegocioException("Error al registrar apartado: " + e.getMessage());
    }
}

}
