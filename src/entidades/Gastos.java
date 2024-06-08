/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.swing.DefaultListModel;

/**
 *
 * @author lguim
 */
public class Gastos implements Serializable {
    private Pila <Double> gastos= new Pila<>();
    private double limiteGasto;

    public double getLimiteGasto() {
        return limiteGasto;
    }

    public void setLimiteGasto(double limiteGasto) {
        this.limiteGasto = limiteGasto;
    }
    
    public Gastos () {
        gastos=new Pila<>();
    }
    
    public void añadirGastos(double valor) {
        gastos.push(valor);
    }
    
    public double eliminarGasto() {
        if(gastos.empty()) {
            System.out.println("Error, los gastos tan vacios");
        }
        return gastos.pop();
    }
    
    public double totalDeGastos() {
        double totalGastos=0;
        Pila<Double> aux=gastos;
        do {
            totalGastos=totalGastos+aux.pop();
        }while(!aux.empty());
        return totalGastos;
    }
    
    public void mostrarGastoAscendente() {
        if (gastos.empty()) {
            System.out.println("Error, los gastos están vacíos");
            return;
        }
        DefaultListModel modelo = new DefaultListModel();
        Pila<Double> aux = new Pila<>();
        while (!gastos.empty()) {
            double gasto = gastos.pop();
            modelo.addElement(gasto);
            aux.push(gasto);
        }
        while (!aux.empty()) {
            gastos.push(aux.pop());
        }
        for (int i = 0; i < modelo.getSize(); i++) {
            System.out.println(modelo.getElementAt(i));
        }
    }
    
    public void mostrarGastoDescendente() {
        if (gastos.empty()) {
            System.out.println("Error, los gastos están vacíos");
            return;
        }
        DefaultListModel modelo = new DefaultListModel();
        Pila<Double> aux = new Pila<>();
        while (!gastos.empty()) {
            double gasto = gastos.pop();
            modelo.addElement(gasto);
            aux.push(gasto);
        }
        while (!aux.empty()) {
            gastos.push(aux.pop());
        }
        for (int i = modelo.getSize() - 1; i >= 0; i--) {
            System.out.println(modelo.getElementAt(i));
        }
    }
    
    
            
}
