
package entidades;

import java.io.Serializable;
import java.time.LocalDate;

public class Operacion implements Serializable{
    
    private LocalDate fecha ; 
    private String categoria; 
    private String detalles;
    private Double monto;
    private String moneda; 
    private String mov;
    
    
    
    public Operacion(LocalDate fecha, String categoria, String detalles, Double monto, String moneda, String mov) {
        this.fecha = fecha; 
        this.categoria = categoria;
        this.detalles = detalles;
        this.monto = monto;
        this.moneda = moneda;
        this.mov=mov;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMov() {
        return mov;
    }

    public void setMov(String mov) {
        this.mov = mov;
    }

    @Override
    public String toString() {
        return "Operacion{" + "fecha=" + fecha + ", categoria=" + categoria + ", detalles=" + detalles + ", monto=" + monto + ", moneda=" + moneda + ", mov=" + mov + '}';
    }
    
    
    
}