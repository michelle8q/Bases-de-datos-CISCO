/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package itson.org.cisco;

import dto.EstadoEquipoDTO;
import entidad.AlumnoEntidad;
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
        IConexionBD conexion = new ConexionBD();
        IAlumnoDAO alumnoDAO = new AlumnoDAO(conexion);
        IEquipoDAO equipoDAO = new EquipoDAO(conexion);
        IAlumnoNegocio alumnoNeg = new AlumnoNegocio(alumnoDAO);
        IEquipoNegocio equipoNeg = new EquipoNegocio(equipoDAO);
        FrmIngresoID frm = new FrmIngresoID(alumnoNeg, equipoNeg);
        frm.setVisible(true);
    }
}
