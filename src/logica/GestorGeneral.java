package logica;

import entidades.*;
import java.io.Serializable;
import java.time.LocalDate;
import javax.swing.table.DefaultTableModel;

public class GestorGeneral implements Serializable {
    
    private Pila<Categoria> pilaCategoriaGasto = new Pila<>();
    private Pila<Categoria> pilaCategoriaIngreso = new Pila<>();
    
    private Pila<Operacion> pilaGasto = new Pila<>();
    private Pila<Operacion> pilaIngreso = new Pila<>();
    private Pila<Operacion> pilaGeneral = new Pila<>();
    
    private double saldo = 0;
    private double ingresoTotal = 0;
    private double gastoTotal = 0;

    public double getSaldo() {
        return saldo;
    }

    public double getIngresoTotal() {
        return ingresoTotal;
    }

    public double getGastoTotal() {
        return gastoTotal;
    }

    public Pila<Categoria> getPilaCategoriaGasto() {
        return pilaCategoriaGasto;
    }

    public Pila<Categoria> getPilaCategoriaIngreso() {
        return pilaCategoriaIngreso;
    }

    public Pila<Operacion> getPilaGeneral() {
        return pilaGeneral;
    }
    
    public Pila<Operacion> getPilaGasto() {
        return pilaGasto;
    }
    
    public Pila<Operacion> getPilaIngreso() {
        return pilaIngreso;
    }
    
    public void agregarCategoriaGasto(String nombreCat) {
        Categoria nuevaCat = new Categoria(nombreCat, true);
        pilaCategoriaGasto.push(nuevaCat);
        System.out.println("paso por aqui");
    }
    
    public void agregarCategoriaIngreso(String nombreCat) {
        Categoria nuevaCat = new Categoria(nombreCat, false);
        pilaCategoriaIngreso.push(nuevaCat);
    }
    
    public void agregarGasto(String nombreCat, Operacion g) {
        // Buscar categoria en la pilaCategoriaGasto
        Categoria categoriaElegida = pilaCategoriaGasto.buscarCategoria(pilaCategoriaGasto, nombreCat);
        if (categoriaElegida != null) {
            categoriaElegida.agregarGastoCat(g);
            pilaGasto.push(g);
            pilaGeneral.push(g);
            
            gastoTotal = gastoTotal + g.getMonto();
            saldo = saldo - gastoTotal;
            
        } else {
            System.out.println("CATEGORIA NO ENCONTRADA");
        }
    }
    
    public double obtenerGasto(){
        return gastoTotal;
    }
    
    public double obtenerSaldo(){
        return saldo;
    }
    
    public double obtenerIngreso(){
        return ingresoTotal; 
    }
    
    
    public void agregarIngreso(String nombreCat, Operacion i) {
        Categoria categoriaElegida = pilaCategoriaIngreso.buscarCategoria(pilaCategoriaIngreso, nombreCat);
        if (categoriaElegida != null) {
            categoriaElegida.agregarIngresoCat(i);
            pilaIngreso.push(i);
            pilaGeneral.push(i);
            
            ingresoTotal = ingresoTotal + i.getMonto();
            saldo = saldo +  ingresoTotal;
        } else {
            System.out.println("CATEGORIA NO ENCONTRADA");
        }
    }

    // MOSTRAR GASTOS POR CATEGORIA 
    public void mostrarGastosCategoria(DefaultTableModel modelo, String nombreCat) {
        Categoria categoriaElegida = pilaCategoriaGasto.buscarCategoria(pilaCategoriaGasto, nombreCat);
        
        if (categoriaElegida != null) {
            pilaCategoriaGasto.mostrarGastosCategoria(modelo, categoriaElegida);
        } else {
            System.err.println("Categoria no encontrada");
        }
    }

    // MOSTAR INGRESOS POR CATEGORIA 
    public void mostrarIngresosCategoria(DefaultTableModel modelo, String nombreCat) {
        Categoria categoriaElegida = pilaCategoriaIngreso.buscarCategoria(pilaCategoriaIngreso, nombreCat);
        if (categoriaElegida != null) {
            pilaCategoriaIngreso.mostrarIngresosCategoria(modelo, categoriaElegida);
        } else {
            System.err.println("Categoria no encontrada");
        }
    }

    // MOSTRAR OPERACIONES POR FECHA
    public void mostrarOperacionFecha(DefaultTableModel modelo, LocalDate fecha) {
        Pila<Operacion> filtrada = new Pila<>();
        Pila<Operacion> auxiliar = new Pila<>();
        
        while (!pilaGeneral.empty()) {
            Operacion op = pilaGeneral.pop();            
            auxiliar.push(op);
            
            if (op.getFecha().equals(fecha)) {
                filtrada.push(op);
            }
        }
        while (!auxiliar.empty()) {
            Operacion n = auxiliar.pop();
            pilaGeneral.push(n);
        }
        
        pilaGeneral.mostrar(modelo, filtrada);
    }

    // MOSTRAR OPERACIONES GENERAL 
    public void mostrarOperaciones(DefaultTableModel modelo) {
        pilaGeneral.mostrar(modelo, pilaGeneral);
    }
    
    public void mostrarOperacionesI(DefaultTableModel modelo) {
        pilaGeneral.mostrarI(modelo, pilaGeneral);
    }
    
    public void mostrarOperacionesG(DefaultTableModel modelo) {
        pilaGeneral.mostrarG(modelo, pilaGeneral);
    }
    
    public void imprimirPilaIngresos(){
        System.out.println(pilaCategoriaIngreso );
    }
    
    public void imprimirPilaGastos(){
        System.out.println(pilaCategoriaGasto );
    }
    
    //Orden Movimientos
    
    public void OrdenMovCat(DefaultTableModel modelo) {
          pilaGeneral.mostrarOrdenCategoria(modelo);
    }
    
    public void OrdenMovFecha(DefaultTableModel modelo) {
          pilaGeneral.mostrarOrdenFecha(modelo);
    }
    //Orden Ingresos
    
    public void OrdenIngresoCat(DefaultTableModel modelo) {
          pilaIngreso.mostrarOrdenCategoria(modelo);
    }
    
    public void OrdenIngresoFecha(DefaultTableModel modelo) {
          pilaIngreso.mostrarOrdenFecha(modelo);
    }
    
    public void OrdenIngresoMonto(DefaultTableModel modelo) {
          pilaIngreso.mostrarOrdenMonto(modelo);
    }
    
    //Orden Gastos
    
    public void OrdenGastoCat(DefaultTableModel modelo) {
          pilaGasto.mostrarOrdenCategoria(modelo);
          
    }
    
    public void OrdenGastoFecha(DefaultTableModel modelo) {
          pilaGasto.mostrarOrdenFecha(modelo);
    }
    
    public void OrdenGastoMonto(DefaultTableModel modelo) {
          pilaGasto.mostrarOrdenMonto(modelo);
    }
    

    public Operacion obtenerMayorGasto() {
        
        Pila <Operacion> pilaDeGastos = pilaGasto.copiarPila();
        
        Operacion mayorGastoOperacion=pilaDeGastos.pop();
        double mayorGasto = mayorGastoOperacion.getMonto();
        
        while (!pilaDeGastos.empty()) {
            
            Operacion GastoOperacion=pilaDeGastos.pop();
            double monto=GastoOperacion.getMonto();
            
            if (monto > mayorGasto) {
                mayorGasto = monto;
                mayorGastoOperacion=GastoOperacion;
            }
        }
        
        return mayorGastoOperacion;
    }

    public Operacion obtenerMenorGasto() {
        
        Pila <Operacion> pilaDeGastos = pilaGasto.copiarPila();
        
        Operacion menorGastoOperacion=pilaDeGastos.pop();
        double menorGasto = menorGastoOperacion.getMonto();
        
        while (!pilaDeGastos.empty()) {
            
            Operacion GastoOperacion=pilaDeGastos.pop();
            double monto=GastoOperacion.getMonto();
            
            if (monto < menorGasto) {
                menorGasto = monto;
                menorGastoOperacion=GastoOperacion;
            }
        } 
        
        return menorGastoOperacion;
    }

    public Operacion obtenerMayorIngreso() {
        
        Pila <Operacion> pilaDeIngresos = pilaIngreso.copiarPila();
        
        Operacion mayorIngresoOperacion=pilaDeIngresos.pop();
        double mayorIngreso = mayorIngresoOperacion.getMonto();
        
        while (!pilaDeIngresos.empty()) {
            
            Operacion IngresoOperacion=pilaDeIngresos.pop();
            double monto=IngresoOperacion.getMonto();
            
            if (monto > mayorIngreso) {
                mayorIngreso = monto;
                mayorIngresoOperacion=IngresoOperacion;
            }
        } 
        
        return mayorIngresoOperacion;
    }

    public Operacion obtenerMenorIngreso() {
        
        Pila <Operacion> pilaDeIngresos = pilaIngreso.copiarPila();
        
        Operacion menorIngresoOperacion=pilaDeIngresos.pop();
        double menorIngreso = menorIngresoOperacion.getMonto();
        
        while (!pilaDeIngresos.empty()) {
            
            Operacion IngresoOperacion=pilaDeIngresos.pop();
            double monto=IngresoOperacion.getMonto();
            
            if (monto < menorIngreso) {
                menorIngreso = monto;
                menorIngresoOperacion = IngresoOperacion;
            }
        } 
        
        return menorIngresoOperacion;
    }

    public Operacion obtenerMayorOperacion() {
        Pila <Operacion> pilaDeOperaciones = pilaGeneral.copiarPila();
        
        Operacion mayorOperacion = pilaDeOperaciones.pop();
        double mayorOp = mayorOperacion.getMonto();
        
        while (!pilaDeOperaciones.empty()) {
            
            Operacion OperacionTemp = pilaDeOperaciones.pop();
            double montoTemp = OperacionTemp.getMonto();
            
            if (montoTemp > mayorOp) {
                mayorOp = montoTemp;
                mayorOperacion = OperacionTemp;
            }
        }
        
        return mayorOperacion;
    }
    public Operacion obtenerMenorOperacion() {
        Pila <Operacion> pilaDeOperaciones = pilaGeneral.copiarPila();
        
        Operacion menorOperacion = pilaDeOperaciones.pop();
        double menorOp = menorOperacion.getMonto();
        
        while (!pilaDeOperaciones.empty()) {
            
            Operacion OperacionTemp = pilaDeOperaciones.pop();
            double montoTemp = OperacionTemp.getMonto();
            
            if (montoTemp < menorOp) {
                menorOp = montoTemp;
                menorOperacion = OperacionTemp;
            }
        }
        
        return menorOperacion;
    }
    
}

//    public Categoria buscarCategoria(String nombre, Pila<Categoria> pi) {
//        Pila<Categoria> pilaAux = new Pila();
//        while (!pi.empty()) {
//            Categoria elemento = pi.pop();
//            pilaAux.push(elemento);
//            if (elemento.getNombreCat().equals(nombre)) {
//                while (!pilaAux.empty()) {
//                    pi.push(pilaAux.pop());
//                }
//                return elemento;
//            }
//        }
//
//        while (!pilaAux.empty()) {
//            pi.push(pilaAux.pop());
//        }
//
//        return null;
//    }
