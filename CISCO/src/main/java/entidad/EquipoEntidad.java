/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.util.List;

/**
 * Entidad de dominio que mapea las características de una estación de cómputo
 * (computadora) alojada en un laboratorio. Contiene información técnica y la
 * lista de programas instalados.
 *
 * * @author cinca
 */
public class EquipoEntidad {

    private int id;
    private int numero;
    private String direccionIP;
    private String estado;
    private String tipo;
    private LaboratorioEntidad laboratorio;
    private List<SoftwareEntidad> softwares;

    /**
     * Constructor por defecto de la clase EquipoEntidad.
     */
    public EquipoEntidad() {
    }

    /**
     * Constructor parametrizado completo para instanciar un equipo informático.
     *
     * * @param id Identificador único del equipo en la base de datos.
     * @param numero Número físico o etiqueta asignada al equipo en el
     * laboratorio.
     * @param direccionIP Dirección lógica de red IP configurada en la estación.
     * @param estado Estado físico o de disponibilidad (ej. "Disponible",
     * "Mantenimiento").
     * @param tipo Clasificación del equipo (ej. "Escritorio", "Laptop").
     * @param laboratorio Instancia de {@link LaboratorioEntidad} al cual está
     * adscrito.
     * @param softwares Colección con las aplicaciones instaladas
     * {@link SoftwareEntidad}.
     */
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

    /**
     * Obtiene el identificador de la computadora en la base de datos.
     *
     * @return El ID del equipo.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el identificador único del equipo de cómputo.
     *
     * @param id El ID único a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la dirección IP asignada a este equipo.
     *
     * @return Cadena de texto con la IP.
     */
    public String getDireccionIP() {
        return direccionIP;
    }

    /**
     * Asigna la dirección IP de red de la estación.
     *
     * @param direccionIP Dirección IP en formato String.
     */
    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    /**
     * Obtiene el estado actual de operatividad o disponibilidad.
     *
     * @return Estado del equipo.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Modifica la condición operativa del equipo.
     *
     * @param estado El nuevo estado a establecer.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el tipo o arquitectura física del equipo.
     *
     * @return Tipo de máquina.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece la categoría o tipo de hardware del equipo.
     *
     * @param tipo El tipo de equipo.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la información del laboratorio que contiene al equipo.
     *
     * @return Objeto {@link LaboratorioEntidad}.
     */
    public LaboratorioEntidad getLaboratorio() {
        return laboratorio;
    }

    /**
     * Vincula el equipo a un espacio o centro de cómputo específico.
     *
     * @param laboratorio Instancia de {@link LaboratorioEntidad}.
     */
    public void setLaboratorio(LaboratorioEntidad laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * Obtiene la lista completa de software instalado en este ordenador.
     *
     * @return Lista de tipo {@link SoftwareEntidad}.
     */
    public List<SoftwareEntidad> getSoftwares() {
        return softwares;
    }

    /**
     * Establece o actualiza el catálogo de paqueterías de software del equipo.
     *
     * @param softwares Lista con entidades de software.
     */
    public void setSoftwares(List<SoftwareEntidad> softwares) {
        this.softwares = softwares;
    }

    /**
     * Obtiene el número físico de inventario o etiqueta asignada al
     * monitor/gabinete.
     *
     * @return Número identificador del equipo.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Asigna el número secuencial o etiqueta física del equipo en el
     * laboratorio.
     *
     * @param numero Número secuencial.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

}
