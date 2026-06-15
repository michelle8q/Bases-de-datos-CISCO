/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.UsoEntidad;
import entidad.AlumnoEntidad;
import entidad.EquipoEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisf
 */
public class UsoDAO implements IUsoDAO {

    private IConexionBD conexionBaseDatos;

    public UsoDAO(IConexionBD conexionBaseDatos) {
        this.conexionBaseDatos = conexionBaseDatos;
    }

    @Override
    public List<UsoEntidad> listarUsosActivos(int limite, int offset, String filtroBusqueda) throws PersistenciaException {
        List<UsoEntidad> listaDeUsos = new ArrayList<>();

        try (Connection conexionAbierta = this.conexionBaseDatos.crearConexion()) {

            boolean hayFiltro = filtroBusqueda != null && !filtroBusqueda.trim().isEmpty();

            StringBuilder sentenciaSQL = new StringBuilder("""
                           SELECT Usos.id, Usos.fechaHoraApartado, Usos.fechaHoraInicio, Usos.fechaHoraFin, 
                                  Alumnos.id AS idDelAlumno, Alumnos.nombres, Alumnos.apellidoPaterno, Alumnos.apellidoMaterno, 
                                  Equipos.id AS idDelEquipo, Equipos.direccionIP 
                           FROM Usos 
                           INNER JOIN Alumnos ON Usos.idAlumno = Alumnos.id 
                           INNER JOIN Equipos ON Usos.idEquipo = Equipos.id 
                           WHERE Usos.fechaHoraFin IS NULL
                           """);

            if (hayFiltro) {
                sentenciaSQL.append("""
                 AND (Alumnos.nombres LIKE ? 
                 OR Alumnos.apellidoPaterno LIKE ? 
                 OR Alumnos.apellidoMaterno LIKE ? 
                 OR Alumnos.id LIKE ? 
                 OR Equipos.id LIKE ?) 
            """);
            }

            sentenciaSQL.append(" LIMIT ? OFFSET ?;");

            PreparedStatement sentenciaPreparada = conexionAbierta.prepareStatement(sentenciaSQL.toString());
            int indiceParametro = 1;

            if (hayFiltro) {
                String patron = "%" + filtroBusqueda.trim() + "%";
                sentenciaPreparada.setString(indiceParametro++, patron); 
                sentenciaPreparada.setString(indiceParametro++, patron); 
                sentenciaPreparada.setString(indiceParametro++, patron);
                sentenciaPreparada.setString(indiceParametro++, patron); 
                sentenciaPreparada.setString(indiceParametro++, patron); 
            }

            sentenciaPreparada.setInt(indiceParametro++, limite);
            sentenciaPreparada.setInt(indiceParametro, offset);

            ResultSet resultadosConsulta = sentenciaPreparada.executeQuery();

            while (resultadosConsulta.next()) {
                listaDeUsos.add(mapearEntidadUso(resultadosConsulta));
            }

            return listaDeUsos;

        } catch (SQLException excepcionSQL) {
            System.out.println("❌ Error al listar usos activos.");
            throw new PersistenciaException(excepcionSQL.getMessage());
        }
    }

    @Override
    public List<UsoEntidad> listarApartadosDelDia(int limite, int offset, String filtroBusqueda) throws PersistenciaException {
        List<UsoEntidad> listaDeUsos = new ArrayList<>();

        try (Connection conexionAbierta = this.conexionBaseDatos.crearConexion()) {
            boolean hayFiltro = filtroBusqueda != null && !filtroBusqueda.trim().isEmpty();

            StringBuilder sentenciaSQL = new StringBuilder("""
                               SELECT Usos.id, Usos.fechaHoraApartado, Usos.fechaHoraInicio, Usos.fechaHoraFin, 
                                      Alumnos.id AS idDelAlumno, Alumnos.nombres, Alumnos.apellidoPaterno, Alumnos.apellidoMaterno, 
                                      Equipos.id AS idDelEquipo, Equipos.direccionIP 
                               FROM Usos 
                               INNER JOIN Alumnos ON Usos.idAlumno = Alumnos.id 
                               INNER JOIN Equipos ON Usos.idEquipo = Equipos.id 
                               WHERE DATE(Usos.fechaHoraApartado) = CURDATE()
                               """);
            if (hayFiltro) {
                sentenciaSQL.append("""
                    AND (Alumnos.nombres LIKE ? 
                    OR Alumnos.apellidoPaterno LIKE ? 
                    OR Alumnos.apellidoMaterno LIKE ? 
                    OR Alumnos.id LIKE ? 
                    OR Equipos.id LIKE ?) 
               """);
            }

            sentenciaSQL.append(" LIMIT ? OFFSET ?;");

            PreparedStatement sentenciaPreparada = conexionAbierta.prepareStatement(sentenciaSQL.toString());

            int indiceParametro = 1;

            if (hayFiltro) {
                String patron = "%" + filtroBusqueda.trim() + "%";
                sentenciaPreparada.setString(indiceParametro++, patron);
                sentenciaPreparada.setString(indiceParametro++, patron);
                sentenciaPreparada.setString(indiceParametro++, patron);
                sentenciaPreparada.setString(indiceParametro++, patron);
                sentenciaPreparada.setString(indiceParametro++, patron);
            }

            sentenciaPreparada.setInt(indiceParametro++, limite);
            sentenciaPreparada.setInt(indiceParametro, offset);

            ResultSet resultadosConsulta = sentenciaPreparada.executeQuery();

            while (resultadosConsulta.next()) {
                listaDeUsos.add(mapearEntidadUso(resultadosConsulta));
            }

            return listaDeUsos;

        } catch (SQLException excepcionSQL) {
            System.out.println("❌ Error al conectar a la base de datos para listar apartados del dia.");
            System.out.println("Motivo del error: " + excepcionSQL.getMessage());
            throw new PersistenciaException(excepcionSQL.getMessage());
        }
    }

    private UsoEntidad mapearEntidadUso(ResultSet resultadosConsulta) throws SQLException {

        AlumnoEntidad alumnoAsignado = new AlumnoEntidad();
        alumnoAsignado.setId(resultadosConsulta.getInt("idDelAlumno"));
        alumnoAsignado.setNombres(resultadosConsulta.getString("nombres"));
        alumnoAsignado.setApellidoPaterno(resultadosConsulta.getString("apellidoPaterno"));
        alumnoAsignado.setApellidoMaterno(resultadosConsulta.getString("apellidoMaterno"));

        EquipoEntidad equipoAsignado = new EquipoEntidad();
        equipoAsignado.setId(resultadosConsulta.getInt("idDelEquipo"));

        Timestamp tiempoDeApartado = resultadosConsulta.getTimestamp("fechaHoraApartado");
        Timestamp tiempoDeInicio = resultadosConsulta.getTimestamp("fechaHoraInicio");
        Timestamp tiempoDeFin = resultadosConsulta.getTimestamp("fechaHoraFin");

        return new UsoEntidad(
                resultadosConsulta.getInt("id"),
                (tiempoDeApartado != null) ? tiempoDeApartado.toLocalDateTime() : null,
                (tiempoDeInicio != null) ? tiempoDeInicio.toLocalDateTime() : null,
                (tiempoDeFin != null) ? tiempoDeFin.toLocalDateTime() : null,
                alumnoAsignado,
                equipoAsignado
        );
    }
}
