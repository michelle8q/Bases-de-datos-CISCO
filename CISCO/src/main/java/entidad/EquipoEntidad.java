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
    private Boolean esActivo;
    private Boolean esInactivo;
    private String tipo;
    private LaboratorioEntidad laboratorio;
    private List<SoftwareEntidad> softwares;

    public EquipoEntidad() {
    }

    public EquipoEntidad(int id, String direccionIP, Boolean esActivo, Boolean esInactivo, 
            String tipo, LaboratorioEntidad laboratorio,  List<SoftwareEntidad> softwares) {
        
        this.id = id;
        this.direccionIP = direccionIP;
        this.esActivo = esActivo;
        this.esInactivo = esInactivo;
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

    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }

    public Boolean getEsInactivo() {
        return esInactivo;
    }

    public void setEsInactivo(Boolean esInactivo) {
        this.esInactivo = esInactivo;
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
    
    
}
