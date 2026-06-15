/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author luisf
 */
public class ListarEquipoDTO {

    private int id;
    private Integer numeroComputadora;
    private String direccionIP;
    private String estado;

    public ListarEquipoDTO() {
    }

    public ListarEquipoDTO(int id, int numeroComputadora, String direccionIP, String estado) {
        this.id = id;
        this.numeroComputadora = numeroComputadora;
        this.direccionIP = direccionIP;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroComputadora() {
        return numeroComputadora;
    }

    public void setNumeroComputadora(int numeroComputadora) {
        this.numeroComputadora = numeroComputadora;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
