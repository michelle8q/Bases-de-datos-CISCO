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
 *
 * @author cinca
 */
public class BloqueoDAO implements IBloqueoDAO {
    
    private IConexionBD conexion;
    private IAlumnoDAO alumnoDAO;

    public BloqueoDAO(IConexionBD conexion) {
        this.conexion = conexion;
        this.alumnoDAO = new AlumnoDAO(conexion);
    }

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
 
        if(rs.next()) {
            
            LocalDateTime fechaHoraFin = null;

            if(rs.getTimestamp("fechaHoraFin") != null) {
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
                if(rs.getTimestamp("fechaHoraFin") != null)
                   fechaHoraFin = rs.getTimestamp("fechaHoraFin").toLocalDateTime();
                
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
    
    @Override
    public boolean BloqueoExistenteAlumno(int idAlumno) throws PersistenciaException {
        try (Connection conexion = this.conexion.crearConexion()) {
                String sentenciaSQL = """
                                       SELECT COUNT(*) FROM bloqueos WHERE idAlumno = ? Bloqueos.fechaHoraFin > NOW();
                                      """;

        PreparedStatement statement = conexion.prepareStatement(sentenciaSQL);
        statement.setInt(1, idAlumno);
        ResultSet rs = statement.executeQuery();
        
        if(rs.next()) {
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
