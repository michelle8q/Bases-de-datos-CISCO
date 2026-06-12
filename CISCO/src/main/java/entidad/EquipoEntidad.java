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
    private String direccionIP;
    private String estado;
    private String tipo;
    private LaboratorioEntidad laboratorio;
    private List<SoftwareEntidad> softwates;

    public EquipoEntidad() {
    }

    public EquipoEntidad(int id, String direccionIP, String estado, String tipo, LaboratorioEntidad laboratorio) {
        this.id = id;
        this.direccionIP = direccionIP;
        this.estado = estado;
        this.tipo = tipo;
        this.laboratorio = laboratorio;
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
    
    
}
