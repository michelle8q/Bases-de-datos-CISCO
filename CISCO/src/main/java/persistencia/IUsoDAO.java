/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.UsoEntidad;
import java.util.List;

/**
 *
 * @author luisf
 */
public interface IUsoDAO {

    List<UsoEntidad> listarUsosActivos(int limite, int offset, String filtroBusqueda) throws PersistenciaException;

    List<UsoEntidad> listarApartadosDelDia(int limite, int offset, String filtroBusqueda) throws PersistenciaException;

    void eliminarUsoActivoPorIP(String ip) throws PersistenciaException;

    boolean finalizarSesion(int idUso) throws PersistenciaException;

    boolean iniciarSesion(int idUso) throws PersistenciaException;

    boolean registrarApartado(UsoEntidad nuevoUso) throws PersistenciaException;

    UsoEntidad buscarApartadoActivoPorIP(String ip) throws PersistenciaException;
}
