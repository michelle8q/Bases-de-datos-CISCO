/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dto.BloquearAlumnoDTO;
import entidad.AlumnoEntidad;
import entidad.BloqueoEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import utilerias.Utilidades;

/**
 * Clase de acceso a datos (DAO) que implementa la interfaz {@link IBloqueoDAO}.
 * Se encarga de gestionar todas las operaciones de persistencia en la base de
 * datos relacionadas con la entidad {@link BloqueoEntidad}, incluyendo el
 * registro, consulta, y levantamiento de bloqueos o sanciones aplicadas a los
 * alumnos.
 *
 * @author cinca
 */
public class BloqueoDAO implements IBloqueoDAO {

    private IConexionBD conexion;
    private IAlumnoDAO alumnoDAO;

    /**
     * Constructor de la clase BloqueoDAO.
     *
     * * @param conexion Interfaz encargada de proveer y gestionar la conexión
     * a la base de datos.
     */
    public BloqueoDAO(IConexionBD conexion) {
        this.conexion = conexion;
        this.alumnoDAO = new AlumnoDAO(conexion);
    }

    /**
     * Registra un nuevo bloqueo para un alumno en la base de datos. Establece
     * la fecha y hora de inicio con el momento actual y deja la fecha de fin
     * nula, indicando que el bloqueo está activo.
     *
     * * @param bloqueo Objeto de transferencia de datos (DTO) que contiene el
     * motivo y el ID del alumno a bloquear.
     * @return La entidad {@link BloqueoEntidad} recién creada con su ID
     * generado, o {@code null} si falló la inserción.
     * @throws PersistenciaException Si ocurre un error de acceso o escritura en
     * la base de datos.
     */
    @Override
    public BloqueoEntidad bloquearAlumno(BloquearAlumnoDTO bloqueo) throws PersistenciaException {
        try (Connection conexion = this.conexion.crearConexion()) {
            String sentenciaSQL = """
                                       INSERT INTO bloqueos (fechaHoraInicio, fechaHoraFin, motivo, idAlumno) 
                                       VALUES (?,?,?,?);
                                     """;

            PreparedStatement statement = conexion.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setNull(2, java.sql.Types.TIMESTAMP);
            statement.setString(3, bloqueo.getMotivo());
            statement.setInt(4, bloqueo.getIdAlumno());

            statement.executeUpdate();

            ResultSet llavesGeneradas = statement.getGeneratedKeys();
            if (llavesGeneradas.next()) {
                int idGenerado = llavesGeneradas.getInt(1);
                return buscarPorId(idGenerado);
            }

            return null;

        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos.");
            System.out.println("Motivo del error: " + e.getMessage());
            throw new PersistenciaException(e.getMessage());
        }
    }

    /**
     * Busca y recupera la información de un bloqueo específico utilizando su
     * identificador único (ID).
     *
     * * @param id El identificador único del bloqueo que se desea buscar.
     * @return Una instancia de {@link BloqueoEntidad} con la información del
     * bloqueo, o {@code null} si no se encuentra.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta
     * en la base de datos.
     */
    @Override
    public BloqueoEntidad buscarPorId(int id) throws PersistenciaException {
        try (Connection conexion = this.conexion.crearConexion()) {
            String sentenciaSQL = """
                                       SELECT id, fechaHoraInicio, fechaHoraFin, motivo, idAlumno FROM
                                       bloqueos WHERE id = ?;
                                     """;

            PreparedStatement statement = conexion.prepareStatement(sentenciaSQL);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            AlumnoEntidad alumno = alumnoDAO.buscarAlumnoPorId(id);

            if (rs.next()) {

                LocalDateTime fechaHoraFin = null;

                if (rs.getTimestamp("fechaHoraFin") != null) {
                    fechaHoraFin = rs.getTimestamp("fechaHoraFin").toLocalDateTime();
                }

                return new BloqueoEntidad(
                        rs.getInt("id"),
                        rs.getTimestamp("fechaHoraInicio").toLocalDateTime(),
                        fechaHoraFin,
                        rs.getString("motivo"),
                        rs.getInt("idAlumno")
                );
            }

            return null;

        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos.");
            System.out.println("Motivo del error: " + e.getMessage());
            throw new PersistenciaException(e.getMessage());
        }

    }

    /**
     * Levanta o finaliza el bloqueo de un alumno actualizando la fecha y hora
     * de fin del registro correspondiente al momento actual.
     *
     * * @param id El identificador único del bloqueo que se desea finalizar
     * (desbloquear).
     * @return La entidad {@link BloqueoEntidad} correspondiente al bloqueo
     * finalizado.
     * @throws PersistenciaException Si el bloqueo no existe, ya fue eliminado,
     * o si ocurre un error en la base de datos.
     */
    @Override
    public BloqueoEntidad desbloquearAlumno(int id) throws PersistenciaException {
        BloqueoEntidad bloqueoEliminado = buscarPorId(id);

        if (bloqueoEliminado == null) {
            throw new PersistenciaException("No se puede eliminar: el bloqueo con id " + id + " no existe o ya fue eliminado.");
        }

        try (Connection conexion = this.conexion.crearConexion()) {
            String sentenciaSQL = """
                                      UPDATE bloqueos SET fechaHoraFin = NOW() WHERE id = ? 
                                     """;

            PreparedStatement statement = conexion.prepareStatement(sentenciaSQL);
            statement.setInt(1, id);

            int filas = statement.executeUpdate();

            System.out.println("ID recibido: " + id);
            System.out.println("Filas afectadas: " + filas);

            return bloqueoEliminado;

        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos.");
            System.out.println("Motivo del error: " + e.getMessage());
            throw new PersistenciaException(e.getMessage());
        }
    }

    /**
     * Obtiene una lista paginada de los bloqueos activos en el sistema. Permite
     * filtrar los resultados mediante coincidencias en el motivo del bloqueo, o
     * en el nombre, apellidos y ID del alumno asociado.
     *
     * * @param filtro Cadena de texto utilizada para buscar coincidencias. Si
     * está vacía, no filtra los resultados.
     * @param limite La cantidad máxima de registros a recuperar por página.
     * @param pagina El número de la página que se desea consultar (utilizado
     * para calcular el offset).
     * @return Una lista de objetos {@link BloqueoEntidad} que representan los
     * bloqueos encontrados.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta
     * en la base de datos.
     */
    @Override
    public List<BloqueoEntidad> listarBloqueos(String filtro, int limite, int pagina) throws PersistenciaException {
        List<BloqueoEntidad> listaDeBloqueos = new ArrayList<>();

        try (Connection conexion = this.conexion.crearConexion()) {

            String sentenciaSQL = """
                               SELECT Bloqueos.id, Bloqueos.fechaHoraInicio, Bloqueos.fechaHoraFin, Bloqueos.motivo,
                                      Alumnos.id AS idAlumno, Alumnos.nombres, Alumnos.apellidoPaterno, Alumnos.apellidoMaterno   
                               FROM Bloqueos 
                               INNER JOIN Alumnos ON Bloqueos.idAlumno = Alumnos.id WHERE Bloqueos.fechaHoraFin > NOW() 
                                  AND (
                                    Bloqueos.motivo LIKE ?
                                    OR Alumnos.nombres LIKE ? 
                                    OR Alumnos.apellidoPaterno LIKE ? 
                                    OR Alumnos.apellidoMaterno LIKE ?
                                    OR CAST(Alumnos.id AS CHAR) LIKE ?
                                  )
                               LIMIT ? OFFSET ?
                              """;

            PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);
            int offset = Utilidades.RegresarOFFSETMySQL(limite, pagina);

            String comodinBusqueda = "%" + filtro + "%";

            sentenciaPreparada.setString(1, comodinBusqueda);
            sentenciaPreparada.setString(2, comodinBusqueda);
            sentenciaPreparada.setString(3, comodinBusqueda);
            sentenciaPreparada.setString(4, comodinBusqueda);
            sentenciaPreparada.setString(5, comodinBusqueda);

            sentenciaPreparada.setInt(6, limite);
            sentenciaPreparada.setInt(7, offset);

            ResultSet rs = sentenciaPreparada.executeQuery();

            while (rs.next()) {
                AlumnoEntidad alumno = new AlumnoEntidad(
                        rs.getInt("idAlumno"),
                        rs.getString("nombres"),
                        rs.getString("apellidoPaterno"),
                        rs.getString("apellidoMaterno")
                );

                LocalDateTime fechaHoraFin = null;
                if (rs.getTimestamp("fechaHoraFin") != null) {
                    fechaHoraFin = rs.getTimestamp("fechaHoraFin").toLocalDateTime();
                }

                listaDeBloqueos.add(new BloqueoEntidad(
                        rs.getInt("id"),
                        rs.getTimestamp("fechaHoraInicio").toLocalDateTime(),
                        fechaHoraFin,
                        rs.getString("motivo"),
                        rs.getInt("idAlumno"),
                        alumno
                ));
            }

            return listaDeBloqueos;

        } catch (SQLException excepcionSQL) {
            System.out.println("❌ Error al conectar a la base de datos para listar apartados del dia.");
            System.out.println("Motivo del error: " + excepcionSQL.getMessage());
            throw new PersistenciaException(excepcionSQL.getMessage());
        }
    }

    /**
     * Verifica si existe un bloqueo activo asociado a un alumno específico en
     * la base de datos.
     *
     * * @param idAlumno El identificador único del alumno a consultar.
     * @return {@code true} si se encuentra al menos un bloqueo activo para el
     * alumno, {@code false} en caso contrario.
     * @throws PersistenciaException Si ocurre un error al ejecutar la
     * verificación en la base de datos.
     */
    @Override
    public boolean BloqueoExistenteAlumno(int idAlumno) throws PersistenciaException {
        try (Connection conexion = this.conexion.crearConexion()) {
            String sentenciaSQL = """
                                       SELECT COUNT(*) FROM bloqueos WHERE idAlumno = ? Bloqueos.fechaHoraFin > NOW();
                                      """;

            PreparedStatement statement = conexion.prepareStatement(sentenciaSQL);
            statement.setInt(1, idAlumno);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;

            }

            return false;

        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos.");
            System.out.println("Motivo del error: " + e.getMessage());
            throw new PersistenciaException(e.getMessage());
        }

    }

}
