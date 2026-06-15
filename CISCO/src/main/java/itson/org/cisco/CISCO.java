/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package itson.org.cisco;

import dto.EstadoEquipoDTO;
import negocio.AlumnoNegocio;
import negocio.EquipoNegocio;
import negocio.IAlumnoNegocio;
import negocio.IEquipoNegocio;
import negocio.IUsoNegocio;
import negocio.UsoNegocio;
import persistencia.AlumnoDAO;
import persistencia.ConexionBD;
import persistencia.EquipoDAO;
import persistencia.IAlumnoDAO;
import persistencia.IConexionBD;
import persistencia.IEquipoDAO;
import persistencia.IIpDAO;
import persistencia.IUsoDAO;
import persistencia.IpDAO;
import persistencia.UsoDAO;
import presentacion.FrmAdministracionApartados;
import presentacion.FrmAdministracionListaComputadoras;
import presentacion.FrmAdministracionUsos;
import presentacion.FrmEquipoDisponible;
import presentacion.FrmEquipoSeleccion;
import presentacion.FrmIngresoID;
import utilerias.Utilidades;

/**
 *
 * @author cinca
 */
public class CISCO {

    public static void main(String[] args) {
        try {
            IConexionBD conexionBD = new ConexionBD();
            IUsoDAO usoDAO = new UsoDAO(conexionBD);
            IIpDAO ipDAO = new IpDAO(conexionBD);
            IEquipoDAO equipoDAO = new EquipoDAO(conexionBD);
            IAlumnoDAO alumnoDAO = new AlumnoDAO(conexionBD);
            IAlumnoNegocio alumnoNegocio = new AlumnoNegocio(alumnoDAO);

            IUsoNegocio usoNegocio = new UsoNegocio(usoDAO, ipDAO, equipoDAO, alumnoDAO);
            IEquipoNegocio equipoNegocio = new EquipoNegocio(equipoDAO);

            String ipEquipo = utilerias.Utilidades.obtenerDireccionIP();

            String tipoPantalla = usoNegocio.determinarPantalla(ipEquipo);

            if (tipoPantalla.equals("Administrador")) {

                new FrmAdministracionUsos(usoNegocio, equipoNegocio).setVisible(true);

            } else if (tipoPantalla.equals("Alumno")) {
                EstadoEquipoDTO estadoEquipoDTO = equipoNegocio.obtenerEstadoEquipo(ipEquipo);

                if (estadoEquipoDTO != null) {
                    new FrmEquipoDisponible(estadoEquipoDTO, usoNegocio, alumnoNegocio, ipEquipo).setVisible(true);
                } else {
                    System.err.println("Error: Esta máquina tiene rol de Alumno pero su IP no está registrada.");
                    System.exit(0);
                }

            } else if (tipoPantalla.equals("Apartados")) {
                new FrmIngresoID().setVisible(true);
            } else {
                System.err.println("No tienes acceso");
                System.exit(0);
            }

        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
