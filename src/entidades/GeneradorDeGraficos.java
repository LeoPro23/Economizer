/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author lguim
 */
public class GeneradorDeGraficos {
    
    public void genera(Pila<Double> gastos) {
        while(!gastos.empty()) {
            double valor=gastos.pop();
            System.out.println("|");
            for(int i=0;i<valor;i++) {
                System.out.println("*");
            }
            System.out.println();
        }
    }
    
    
}
