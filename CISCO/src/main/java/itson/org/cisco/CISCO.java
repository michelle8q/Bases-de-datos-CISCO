/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package itson.org.cisco;

import persistencia.AlumnoDAO;
import persistencia.ConexionBD;
import persistencia.IAlumnoDAO;
import persistencia.IConexionBD;

/**
 *
 * @author cinca
 */
public class CISCO {

    public static void main(String[] args) {
        try {
            IConexionBD conexion = new ConexionBD();
            IAlumnoDAO alumnoDAO = new AlumnoDAO(conexion);
            
            alumnoDAO.buscarAlumnoPorId(1);
            if(alumnoDAO != null) {
               System.out.println("se encontro");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
