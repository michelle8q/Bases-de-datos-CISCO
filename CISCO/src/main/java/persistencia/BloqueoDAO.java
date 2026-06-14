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
                alumno
                
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
    public BloqueoEntidad desbloquearAlumno(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<BloqueoEntidad> listarBloqueos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
