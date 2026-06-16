package persistencia;

/**
 * Interfaz de Acceso a Datos (DAO) especializada en operaciones de enrutamiento
 * por red. Resuelve la asociación lógica de direcciones IP con privilegios de
 * interfaz y flujos de negocio.
 *
 * * @author piña
 */
public interface IIpDAO {

    /**
     * Consulta la tabla de configuraciones de red para retornar el rol o tipo
     * de pantalla asignado a una dirección IP en el entorno físico de los
     * laboratorios.
     *
     * * @param ip Dirección IP de la estación que arranca la aplicación.
     * @return Cadena que representa el perfil del equipo (ej. "Administrador",
     * "Alumno", "Apartados").
     * @throws PersistenciaException Si la IP no está configurada o registrada
     * en la infraestructura de la base de datos.
     */
    public String obtenerPantallaPorIP(String ip) throws PersistenciaException;
}
