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
import persistencia.IUsoDAO;
import persistencia.UsoDAO;
import presentacion.FrmAdministracionUsos;

/**
 *
 * @author cinca
 */
public class CISCO {

    public static void main(String[] args) {

        IConexionBD conexionBD = new ConexionBD(); 

        IUsoDAO usoDAO = new UsoDAO(conexionBD);

        IUsoNegocio usoNegocio = new UsoNegocio(usoDAO);

        FrmAdministracionUsos ventana = new FrmAdministracionUsos(usoNegocio);

        ventana.setVisible(true);
    }

}
