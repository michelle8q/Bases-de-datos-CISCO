/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Clase de utilería que proporciona métodos estáticos auxiliares para realizar
 * operaciones comunes del sistema, como obtener configuraciones de red o
 * realizar cálculos para la base de datos.
 *
 * @author cinca
 */
public class Utilidades {

    /**
     * Obtiene la dirección IP local de la máquina donde se está ejecutando la
     * aplicación.
     *
     * * @return Una cadena de texto (String) con la dirección IP local (ej.
     * "192.168.1.10").
     * @throws UnknownHostException Si no es posible determinar la dirección IP
     * del equipo local.
     */
    public static String obtenerDireccionIP() throws UnknownHostException {
        // Obtener la información de red del equipo local
        InetAddress equipoLocal = InetAddress.getLocalHost();

        // Extraer la dirección IP en formato de texto
        String direccionIP = equipoLocal.getHostAddress();
        return direccionIP;
    }

    /**
     * Calcula el valor del parámetro OFFSET utilizado en consultas SQL
     * (específicamente MySQL) para implementar sistemas de paginación.
     *
     * * @param limite La cantidad máxima de registros que se mostrarán por
     * página.
     * @param pagina El número de la página actual que se desea visualizar.
     * @return El valor entero que representa el inicio (OFFSET) desde donde la
     * consulta debe traer los registros.
     */
    public static int RegresarOFFSETMySQL(int limite, int pagina) {
        if (pagina <= 1) {
            return 0;
        }

        if (pagina == 2) {
            return limite;
        }

        return ((int) (limite * (pagina - 1)));
    }
}
