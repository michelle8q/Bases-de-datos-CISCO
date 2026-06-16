/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interfaz responsable del puente de conectividad con el Sistema Gestor de Base
 * de Datos (SGBD). Abstrae los parámetros de conexión (URL, usuario,
 * contraseña) para proveer un canal de comunicación.
 *
 * * @author cinca
 */
public interface IConexionBD {

    /**
     * Establece y retorna una conexión activa hacia el motor de la base de
     * datos configurado.
     *
     * * @return Objeto {@link Connection} listo para ejecutar sentencias SQL.
     * @throws SQLException Si las credenciales son incorrectas o el servidor no
     * responde.
     */
    Connection crearConexion() throws SQLException;
}
