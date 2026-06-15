/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author cinca
 */
public class Utilidades {
    
    public static String obtenerDireccionIP() throws UnknownHostException {
    // Obtener la información de red del equipo local
    InetAddress equipoLocal = InetAddress.getLocalHost();
    
    // Extraer la dirección IP en formato de texto
    String direccionIP = equipoLocal.getHostAddress();   
    return direccionIP;    
    }
    
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
