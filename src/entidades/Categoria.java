
package entidades;

import java.io.Serializable;

public class Categoria implements NombreComparable,Serializable{
    private String nombreCat; 
    private Pila<Operacion> pilaGastosCategoria; 
    private Pila<Operacion> pilaIngresosCategoria; 

    public Categoria(String nombreCat, boolean esGasto) {
        this.nombreCat = nombreCat;
        if (esGasto)
            this.pilaGastosCategoria = new Pila<>(); 
        else
            this.pilaIngresosCategoria = new Pila<>();   
    }

    public Categoria(String nombreCat, Pila<Operacion> pilaGastosCategoria, Pila<Operacion> pilaIngresosCategoria) {
        this.nombreCat = nombreCat;
        this.pilaGastosCategoria = pilaGastosCategoria;
        this.pilaIngresosCategoria = pilaIngresosCategoria;
    }

    public String getNombreCat() {
        return nombreCat;
    }

    public void setNombreCat(String nombreCat) {
        this.nombreCat = nombreCat;
    }

    public Pila<Operacion> getPilaGastosCategoria() {
        return pilaGastosCategoria;
    }

    public void setPilaGastosCategoria(Pila<Operacion> pilaGastosCategoria) {
        this.pilaGastosCategoria = pilaGastosCategoria;
    }

    public Pila<Operacion> getPilaIngresosCategoria() {
        return pilaIngresosCategoria;
    }

    public void setPilaIngresosCategoria(Pila<Operacion> pilaIngresosCategoria) {
        this.pilaIngresosCategoria = pilaIngresosCategoria;
    }


    public void agregarIngresoCat (Operacion ingreso){
        pilaIngresosCategoria.push(ingreso);
    }
    public void agregarGastoCat (Operacion gasto){
        pilaGastosCategoria.push(gasto);
    }
    
    public Operacion eliminarUltimoGasto() {
        if (!pilaGastosCategoria.empty()) {
            return pilaGastosCategoria.pop();
        } else {
            return null;
        }
    }
    
    public boolean equalsNombre(String nombre) {
        return this.nombreCat.equals(nombre);
    }

}
