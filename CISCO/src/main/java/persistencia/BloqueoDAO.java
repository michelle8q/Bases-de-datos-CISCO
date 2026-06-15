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

        PreparedStatement statement = conexion.prepareStatement(sentenciaSQL);

        statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        statement.setNull(2, java.sql.Types.TIMESTAMP);
        statement.setInt(3, bloqueo.getIdAlumno());
        statement.setString(4, bloqueo.getMotivo());
        

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
            return new BloqueoEntidad(
                rs.getInt("id"), 
                rs.getTimestamp("fechaHoraInicio").toLocalDateTime(),
                rs.getTimestamp("fechaHoraFin").toLocalDateTime(),
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
                                      UPDATE bloqueos SET fechaHoraFin = NOW() WHERE id = ? AND fechaHoraFin IS NULL
                                     """;

        PreparedStatement statement = conexion.prepareStatement(sentenciaSQL);
        statement.setInt(1, id);

        statement.executeUpdate();
        
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
                               SELECT Bloqueos.id, Bloqueos.fechaHoraInicio, Bloqueos.motivo,
                                      Alumnos.id AS idAlumno, Alumnos.nombres, Alumnos.apellidoPaterno, Alumnos.apellidoMaterno   
                               FROM Bloqueos 
                               INNER JOIN Alumnos ON Bloqueos.idAlumno = Alumnos.id 
                               WHERE Bloqueos.motivo LIKE ? OR Alumnos.nombres LIKE ? OR Alumnos.apellidoPaterno LIKE ? 
                               OR Alumnos.apellidoMaterno LIKE ?
                               LIMIT ? OFFSET ?;
                              """;

            PreparedStatement sentenciaPreparada = conexion.prepareStatement(sentenciaSQL);

            String comodinBusqueda = "%" + filtro + "%";
            sentenciaPreparada.setString(1, comodinBusqueda);
            sentenciaPreparada.setString(2, comodinBusqueda);
            sentenciaPreparada.setString(3, comodinBusqueda);
            sentenciaPreparada.setString(4, comodinBusqueda);
            
            int offset = Utilidades.RegresarOFFSETMySQL(limite, pagina);

            sentenciaPreparada.setInt(5, limite);
            sentenciaPreparada.setInt(6, offset);

            ResultSet resultadosConsulta = sentenciaPreparada.executeQuery();

            while (resultadosConsulta.next()) {
                listaDeBloqueos.add(new BloqueoEntidad(
                    resultadosConsulta.getInt("id"),
                    resultadosConsulta.getTimestamp("fechaHoraInicio").toLocalDateTime(),
                    resultadosConsulta.getTimestamp(null).toLocalDateTime(),
                    resultadosConsulta.getString("motivo"),
                    resultadosConsulta.getInt("idAlumno")                  
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
                                       SELECT COUNT(*) FROM bloqueos WHERE idAlumno = ? AND fechaHoraFin IS NULL;
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
