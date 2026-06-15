/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.util.List;

/**
 *
 * @author cinca
 */
public class EquipoEntidad {

    private int id;
    private int numero;
    private String direccionIP;
    private String estado;
    private String tipo;
    private LaboratorioEntidad laboratorio;
    private List<SoftwareEntidad> softwares;

    public EquipoEntidad() {
    }

    public EquipoEntidad(int id, int numero, String direccionIP, String estado,
            String tipo, LaboratorioEntidad laboratorio, List<SoftwareEntidad> softwares) {

        this.id = id;
        this.numero = numero;
        this.direccionIP = direccionIP;
        this.estado = estado;
        this.tipo = tipo;
        this.laboratorio = laboratorio;
        this.softwares = softwares;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LaboratorioEntidad getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(LaboratorioEntidad laboratorio) {
        this.laboratorio = laboratorio;
    }

    public List<SoftwareEntidad> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<SoftwareEntidad> softwares) {
        this.softwares = softwares;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

}
