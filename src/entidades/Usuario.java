/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;
import logica.*;

/**
 *
 * @author lguim
 */
public class Usuario {
    private String NUsuario;
    private String contraseña;
    private GestorGeneral gestor;
    private String plan;

    public Usuario() {
        NUsuario=null;
        contraseña=null;
        gestor=null;
        plan="Gratis";
    }

    public Usuario(String NUsuario, String contraseña) {
        this.NUsuario = NUsuario;
        this.contraseña = contraseña;
        this.gestor = null;
        plan="Gratis";
    }

    public Usuario(String NUsuario, String contraseña, GestorGeneral gestor) {
        this.NUsuario = NUsuario;
        this.contraseña = contraseña;
        this.gestor = gestor;
        plan="Gratis";
    }

    public Usuario(String NUsuario, String contraseña, GestorGeneral gestor, String plan) {
        this.NUsuario = NUsuario;
        this.contraseña = contraseña;
        this.gestor = gestor;
        this.plan = plan;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getNUsuario() {
        return NUsuario;
    }

    public void setNUsuario(String NUsuario) {
        this.NUsuario = NUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public GestorGeneral getGestor() {
        return gestor;
    }

    public void setGestor(GestorGeneral gestor) {
        this.gestor = gestor;
    }
    
    public int getTamaño () {
        return getNUsuario().length()*2 + 4 + 4;
    }

     
   
}
