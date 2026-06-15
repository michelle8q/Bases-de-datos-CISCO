/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package itson.org.cisco;

import negocio.IUsoNegocio;
import negocio.UsoNegocio;
import persistencia.AlumnoDAO;
import persistencia.ConexionBD;
import persistencia.IAlumnoDAO;
import persistencia.IConexionBD;
import persistencia.IIpDAO;
import persistencia.IUsoDAO;
import persistencia.IpDAO;
import persistencia.UsoDAO;
import presentacion.FrmAdministracionUsos;
import presentacion.FrmEquipoDisponible;
import presentacion.FrmIngresoID;
import utilerias.Utilidades;

/**
 *
 * @author cinca
 */
public class CISCO {

    public static void main(String[] args) {
        try {
            IConexionBD conexionBD = new ConexionBD(); // Tu clase de conexión

            IUsoDAO usoDAO = new UsoDAO(conexionBD);

            IIpDAO ipDAO = new IpDAO(conexionBD);

            IUsoNegocio usoNegocio = new UsoNegocio(usoDAO, ipDAO);

            String ipEquipo = Utilidades.obtenerDireccionIP();
            String tipoPantalla = usoNegocio.determinarPantalla(ipEquipo);

            if (tipoPantalla.equals("Administrador")) {
                new FrmAdministracionUsos(usoNegocio).setVisible(true);
            } else if (tipoPantalla.equals("Alumno")) {
                new FrmEquipoDisponible().setVisible(true);
            } else if (tipoPantalla.equals("Apartados")) {
                new FrmIngresoID().setVisible(true);
            } else {
                System.err.println("No tienes acceso");
                System.exit(0);
            }

        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }

//    public static void main(String[] args) {
//
//        IConexionBD conexionBD = new ConexionBD();
//
//        IUsoDAO usoDAO = new UsoDAO(conexionBD);
//
//        IUsoNegocio usoNegocio = new UsoNegocio(usoDAO);
//
//        FrmAdministracionApartados ventana = new FrmAdministracionApartados(usoNegocio);
//
//        ventana.setVisible(true);
//    }
}
