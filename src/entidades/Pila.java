/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lguim
 */
public class Pila<T> implements Serializable {
    private double MONTO;
    private Nodo<T> p;

    public Pila() {
        p=null;
    }

    public void push(T valor) {
        Nodo<T> nuevo = new Nodo<T>(valor);
        nuevo.setSgte(p);
        p = nuevo;
    }
    
    public T pop() {
        T cima = p.getInfo();
        p = p.getSgte();
        return cima;
    }

    public T top() {
        return p.getInfo();
    }

    public boolean empty() {
        return p == null;
    }

    public void clear() {
        while (p != null) {
            p = p.getSgte();
        }
    }

    // para buscar la categoria por nombre
    public <T extends NombreComparable> T buscarCategoria(Pila<T> pi, String nombreCat) {
        Pila<T> pilaAux = pi.copiarPila(); // Crear una copia de la pila original

        while (!pilaAux.empty()) {
            T elemento = pilaAux.pop();

            if (elemento.equalsNombre(nombreCat)) {
                return elemento; 
            }
        }

        return null; 
    }

    public void modificar(Pila<T> p, T dato, T nuevo) {
        Pila<T> pila2 = new Pila();
        while (!p.empty()) {
            T elemento = p.pop();
            if (elemento.equals(dato)) {
                p.push(nuevo);
                break;
            } else {
                pila2.push(elemento);
            }
        }
        while (!pila2.empty()) {
            p.push(pila2.pop());
        }
    }

    public void mostrarGastosCategoria(DefaultTableModel modelo, Categoria nombreCat) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"FECHA", "CATEGORIA", "DETALLES", "MONTO", "MONEDA"});
        Pila<Operacion> pilaGastosCat = nombreCat.getPilaGastosCategoria();
        Pila<Operacion> pilaAux = new Pila<>();

        while (!pilaGastosCat.empty()) {
            Operacion g = pilaGastosCat.pop();
            pilaAux.push(g);
            Object[] fila = {g.getFecha(), g.getCategoria(), g.getDetalles(), g.getMonto(), g.getMoneda()};
            modelo.addRow(fila);
        }

        while (!pilaAux.empty()) {
            pilaGastosCat.push(pilaAux.pop());
        }
    }
    
    
    public void mostrarIngresosCategoria(DefaultTableModel modelo, Categoria nombreCat) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"FECHA", "CATEGORIA", "DETALLES", "MONTO", "MONEDA", "MOVIMIENTO"});
        Pila<Operacion> pilaIngresosCat = nombreCat.getPilaIngresosCategoria();
        Pila<Operacion> pilaAux = new Pila<>();

        while (!pilaIngresosCat.empty()) {
            Operacion g = pilaIngresosCat.pop();
            pilaAux.push(g);
            Object[] fila = {g.getFecha(), g.getCategoria(), g.getDetalles(), g.getMonto(), g.getMoneda(), g.getMov()};
            modelo.addRow(fila);
        }

        while (!pilaAux.empty()) {
            pilaIngresosCat.push(pilaAux.pop());
        }
    }

    public void mostrar(DefaultTableModel modelo, Pila<Operacion> pi) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"FECHA", "CATEGORIA", "DETALLES", "MONTO", "MONEDA", "MOVIMIENTO"});
        Pila<Operacion> pilaAux = new Pila<>();

        while (!pi.empty()) {
            Operacion g = pi.pop(); 
            pilaAux.push(g);
            Object[] fila = {g.getFecha(), g.getCategoria(), g.getDetalles(), g.getMonto(), g.getMoneda(), g.getMov()};
            modelo.addRow(fila);
        }

        while (!pilaAux.empty()) {
            pi.push(pilaAux.pop());
        }
    }

    
    public void mostrarI(DefaultTableModel modelo, Pila<Operacion> pi) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"FECHA", "CATEGORIA", "DETALLES", "MONTO", "MONEDA", "MOVIMIENTO"});
        Pila<Operacion> pilaAux = new Pila<>();

        while (!pi.empty()) {
            Operacion g = pi.pop(); 
            pilaAux.push(g);
            Object[] fila = {g.getFecha(), g.getCategoria(), g.getDetalles(), g.getMonto(), g.getMoneda(), g.getMov()};
            if(g.getMov().equals("Ingreso")){
                modelo.addRow(fila);
            }
        }

        while (!pilaAux.empty()) {
            pi.push(pilaAux.pop());
        }
    }
    
    public void mostrarG(DefaultTableModel modelo, Pila<Operacion> pi) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"FECHA", "CATEGORIA", "DETALLES", "MONTO", "MONEDA", "MOVIMIENTO"});
        Pila<Operacion> pilaAux = new Pila<>();

        while (!pi.empty()) {
            Operacion g = pi.pop(); 
            pilaAux.push(g);
            Object[] fila = {g.getFecha(), g.getCategoria(), g.getDetalles(), g.getMonto(), g.getMoneda(), g.getMov()};
            if(g.getMov().equals("Gasto")){
                modelo.addRow(fila);
            }
        }

        while (!pilaAux.empty()) {
            pi.push(pilaAux.pop());
        }
    }
    
    
    public int size() {
        int size=0;
        Nodo<T> aux=p;
        while(aux!=null) {
            size++;
            aux=aux.getSgte();
        }
        return size;
    }

    
    public boolean buscar(String valor) {
        Nodo<Usuario> actual = (Nodo<Usuario>) p;
        while (actual != null) {
            if (actual.getInfo().getNUsuario().equals(valor)) {
                return true;
            }
            actual = actual.getSgte();
        }
        return false; 
    }
    
    public Usuario buscarUsuario(String valor) {
        Nodo<Usuario> actual = (Nodo<Usuario>) p;
        while (actual != null) {
            if (actual.getInfo().getNUsuario().equals(valor)) {
                return actual.getInfo();
            }
            actual = actual.getSgte();
        }
        return null; 
    }
    
    //Mostrar Ordenado
    
    public void mostrarOrdenFecha(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"FECHA", "CATEGORIA", "DETALLES", "MONTO", "MONEDA", "MOVIMIENTO"});
        Pila<T> copiaPila = copiarPila();
        ordenarPilaPorFecha(copiaPila); 
        while (!copiaPila.empty()) {
            Operacion operacion = (Operacion) copiaPila.pop();
            Object[] fila = {operacion.getFecha(), operacion.getCategoria(), operacion.getDetalles(), operacion.getMonto(), operacion.getMoneda(), operacion.getMov()};
            modelo.addRow(fila);
        }
    }
    
    public void mostrarOrdenCategoria(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"FECHA", "CATEGORIA", "DETALLES", "MONTO", "MONEDA", "MOVIMIENTO"});
        Pila<T> copiaPila = copiarPila();
        ordenarPilaPorCategoria(copiaPila);
        while (!copiaPila.empty()) {
            Operacion operacion = (Operacion) copiaPila.pop();
            Object[] fila = {operacion.getFecha(), operacion.getCategoria(), operacion.getDetalles(), operacion.getMonto(), operacion.getMoneda(), operacion.getMov()};
            modelo.addRow(fila);
        }
    }
    
    public void mostrarOrdenMonto(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        modelo.setColumnIdentifiers(new String[]{"FECHA", "CATEGORIA", "DETALLES", "MONTO", "MONEDA", "MOVIMIENTO"});
        Pila<T> copiaPila = copiarPila();
        ordenarPilaPorMonto(copiaPila);
        while (!copiaPila.empty()) {
            Operacion operacion = (Operacion) copiaPila.pop();
            Object[] fila = {operacion.getFecha(), operacion.getCategoria(), operacion.getDetalles(), operacion.getMonto(), operacion.getMoneda(), operacion.getMov()};
            modelo.addRow(fila);
        }
    }
    
    public Pila<T> copiarPila() {
        Pila<T> copiaPila = new Pila<>();
        Pila<T> pilaAux = new Pila<>();
        while (!empty()) {
            T elemento = pop();
            copiaPila.push(elemento);
            pilaAux.push(elemento);
        }
        while (!pilaAux.empty()) {
            push(pilaAux.pop());
        }
        return copiaPila;
    }
    
    
    
    
    private void ordenarPilaPorFecha(Pila<T> pila) {
        if (pila.empty() || pila.size() == 1) return;

        Nodo<T> current = pila.p;
        while (current != null) {
            Nodo<T> min = current;
            Nodo<T> index = current.getSgte();

            while (index != null) {
                if (((Operacion) index.getInfo()).getFecha().compareTo(((Operacion) min.getInfo()).getFecha()) < 0) {
                    min = index;
                }
                index = index.getSgte();
            }

            T temp = current.getInfo();
            current.setInfo(min.getInfo());
            min.setInfo(temp);

            current = current.getSgte();
        }
    }
    
    private void ordenarPilaPorCategoria(Pila<T> pila) {
        if (pila.empty() || pila.size() == 1) return;

        Nodo<T> current = pila.p;
        while (current != null) {
            Nodo<T> min = current;
            Nodo<T> index = current.getSgte();

            while (index != null) {
                if (((Operacion) index.getInfo()).getCategoria().compareTo(((Operacion) min.getInfo()).getCategoria()) < 0) {
                    min = index;
                }
                index = index.getSgte();
            }
            T temp = current.getInfo();
            current.setInfo(min.getInfo());
            min.setInfo(temp);

            current = current.getSgte();
        }
    }
    
    private void ordenarPilaPorMonto(Pila<T> pila) {
        if (pila.empty() || pila.size() == 1) return;

        Nodo<T> current = pila.p;
        while (current != null) {
            Nodo<T> min = current;
            Nodo<T> index = current.getSgte();

            while (index != null) {
                if (((Operacion) index.getInfo()).getMonto() < ((Operacion) min.getInfo()).getMonto()) {
                    min = index;
                }
                index = index.getSgte();
            }

            // Intercambiar el valor mínimo con el valor actual
            T temp = current.getInfo();
            current.setInfo(min.getInfo());
            min.setInfo(temp);

            current = current.getSgte();
        }
    }
    

    @Override
    public String toString() {
        return "Pila{" + "p=" + p.getInfo() + '}';
    }
    
    
    
}

