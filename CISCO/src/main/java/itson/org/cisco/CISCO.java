/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package itson.org.cisco;

import dto.EstadoEquipoDTO;
import negocio.AlumnoNegocio;
import negocio.BloqueoNegocio;
import negocio.EquipoNegocio;
import negocio.IAlumnoNegocio;
import negocio.IBloqueoNegocio;
import negocio.IEquipoNegocio;
import negocio.IUsoNegocio;
import negocio.UsoNegocio;
import persistencia.AlumnoDAO;
import persistencia.BloqueoDAO;
import persistencia.ConexionBD;
import persistencia.EquipoDAO;
import persistencia.IAlumnoDAO;
import persistencia.IBloqueoDAO;
import persistencia.IConexionBD;
import persistencia.IEquipoDAO;
import persistencia.IIpDAO;
import persistencia.IUsoDAO;
import persistencia.IpDAO;
import persistencia.UsoDAO;
import presentacion.FrmAdministracionUsos;
import presentacion.FrmEquipoDisponible;
import presentacion.FrmIngresoID;

/**
 * Clase principal y punto de entrada (Entry Point) del sistema de control de
 * laboratorios CISCO. Se encarga de coordinar el arranque de la aplicación
 * mediante el levantamiento y enlace de la arquitectura multicapa (Persistencia
 * y Capa de Negocio). * Además, implementa un mecanismo de enrutamiento
 * dinámico por red: detecta la dirección IP física de la máquina local y, en
 * función de su rol asignado en la base de datos, inicializa la interfaz
 * gráfica (GUI) correspondiente para el Administrador, Alumnos o el Centro de
 * Apartados.
 *
 * * @author cinca piña luisf
 */
public class CISCO {

    /**
     * Método de arranque del sistema (Main Thread). Ejecuta secuencialmente las
     * siguientes acciones de inicialización:
     *
     * Instancia el manejador de conexiones a la base de datos. Construye las
     * unidades de persistencia (DAOs) inyectando la conexión. Inicializa la
     * lógica de negocio aplicando inversión de dependencias. Recupera de forma
     * nativa la IP privada asignada a la interfaz de red actual. Determina y
     * levanta de forma exclusiva el formulario de presentación adecuado.
     *
     * En caso de accesos inválidos o fallos de red críticos, interrumpe de
     * forma segura la ejecución.
     *
     * * @param args Argumentos opcionales recibidos por la línea de comandos
     * (no utilizados).
     */
    public static void main(String[] args) {

        try {
            IConexionBD conexionBD = new ConexionBD();
            IUsoDAO usoDAO = new UsoDAO(conexionBD);
            IIpDAO ipDAO = new IpDAO(conexionBD);
            IBloqueoDAO bloqueoDAO = new BloqueoDAO(conexionBD);
            IEquipoDAO equipoDAO = new EquipoDAO(conexionBD);
            IAlumnoDAO alumnoDAO = new AlumnoDAO(conexionBD);
            IAlumnoNegocio alumnoNegocio = new AlumnoNegocio(alumnoDAO);
            IBloqueoNegocio bloqueoNegocio = new BloqueoNegocio(bloqueoDAO, alumnoDAO);

            IUsoNegocio usoNegocio = new UsoNegocio(usoDAO, ipDAO, equipoDAO, alumnoDAO);
            IEquipoNegocio equipoNegocio = new EquipoNegocio(equipoDAO);

            String ipEquipo = utilerias.Utilidades.obtenerDireccionIP();
            System.out.println("IP de esta máquina: " + ipEquipo);

            String tipoPantalla = usoNegocio.determinarPantalla(ipEquipo);

            if (tipoPantalla.equals("Administrador")) {

                new FrmAdministracionUsos(usoNegocio, equipoNegocio, bloqueoNegocio).setVisible(true);

            } else if (tipoPantalla.equals("Alumno")) {
                EstadoEquipoDTO estadoEquipoDTO = equipoNegocio.obtenerEstadoEquipo(ipEquipo);

                if (estadoEquipoDTO != null) {
                    new FrmEquipoDisponible(estadoEquipoDTO, usoNegocio, alumnoNegocio, equipoNegocio, ipEquipo).setVisible(true);

                } else {
                    System.err.println("Error: Esta máquina tiene rol de Alumno pero su IP no está registrada.");
                    System.exit(0);
                }

            } else if (tipoPantalla.equals("Apartados")) {
                new FrmIngresoID(alumnoNegocio, equipoNegocio, usoNegocio).setVisible(true);

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
