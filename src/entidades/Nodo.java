/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;

/**
 *
 * @author lguim
 */
public class Nodo<T> implements Serializable{
    private T info;
    private Nodo<T> sgte;

    public Nodo() {
        sgte=null;
    }

    public Nodo(T info) {
        this.info = info;
    }
    
    public Nodo(T info, Nodo<T> sgte) {
        this.info = info;
        this.sgte = sgte;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public Nodo<T> getSgte() {
        return sgte;
    }

    public void setSgte(Nodo<T> sgte) {
        this.sgte = sgte;
    }
    
    
}


