/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.time.LocalTime;

/**
 *
 * @author cinca
 */
public class LaboratorioEntidad {
    private int id;
    private String nombre;
    private LocalTime horaInicioServicio;
    private LocalTime horaFinServicio;
    private String contrasena;
    private PlantelEntidad plantel; 

    public LaboratorioEntidad() {
    }

    public LaboratorioEntidad(int id, String nombre, LocalTime horaInicioServicio, LocalTime horaFinServicio, String contrasena, PlantelEntidad plantel) {
        this.id = id;
        this.nombre = nombre;
        this.horaInicioServicio = horaInicioServicio;
        this.horaFinServicio = horaFinServicio;
        this.contrasena = contrasena;
        this.plantel = plantel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalTime getHoraInicioServicio() {
        return horaInicioServicio;
    }

    public void setHoraInicioServicio(LocalTime horaInicioServicio) {
        this.horaInicioServicio = horaInicioServicio;
    }

    public LocalTime getHoraFinServicio() {
        return horaFinServicio;
    }

    public void setHoraFinServicio(LocalTime horaFinServicio) {
        this.horaFinServicio = horaFinServicio;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public PlantelEntidad getPlantel() {
        return plantel;
    }

    public void setPlantel(PlantelEntidad plantel) {
        this.plantel = plantel;
    }
    
    
}
