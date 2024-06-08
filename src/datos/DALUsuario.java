package datos;

import entidades.*;
import java.io.*;
import java.util.*;
import logica.*;

public class DALUsuario {
    private static RandomAccessFile flujo;
    private static final int TAMREG = 200;//TAMREG: Tamaño del registro estaba en 150
    private static int numRegistros;
    private static String nArchivo = "usuarios.txt";
    
    public static String crearFlujo() {//ES UNA ESPECIE DE CONSTRUCTOR PARA EL FLUJO, UN METODO PARA ACTUALIZARLO
        try {
            flujo = new RandomAccessFile(nArchivo,"rw");
            numRegistros = (int)Math.ceil((double)flujo.length()/(double)TAMREG);
        } catch(IOException ex) {
            return "Problema al crear el flujo: " + ex.getMessage();
        } //si hubiera problema devuelve el string
        return null; //si devuelve null, se ha podido crear el flujo sin problema
    }
    
    public static String escribirUsuario(Usuario usuario) {
        String mensaje = "";
        try {
            crearFlujo(); //este abrir y cerrar asegura que la variable numRegistros 
            flujo.close(); //tenga los valores actualizados
            mensaje = setUsuario(numRegistros, usuario);
            if (mensaje.compareTo("ok") == 0) {
                numRegistros++;
            }
        } catch (IOException ex){
            mensaje = "Excepcion: " + ex.getMessage();
        } finally {
            return mensaje;
        }
    }
    
    public static Pila<Usuario> getUsuarios() {
        Pila<Usuario> pilaUsuarios = new Pila<>();
        crearFlujo();
        int posicion=0;
        while (posicion < numRegistros) {
            // Aquí debes procesar cada línea para crear un objeto Usuario
            Usuario usuario = getUsuario(posicion);
            pilaUsuarios.push(usuario);
            posicion++;
        }
        return pilaUsuarios;
    }
    
    //VERSION 2
    
    public static String setUsuario(int posicion, Usuario usuario) {
        String mensaje = "";
        try {
            if (usuario.getTamaño() + 2 > TAMREG) {
                mensaje = "Tamaño de registro insuficiente (Muy pequeño para guardar los datos)";
            } else {
                crearFlujo();
                flujo.seek(posicion*TAMREG);
                flujo.writeUTF(usuario.getNUsuario());
                flujo.writeUTF(usuario.getContraseña());
                flujo.writeUTF(usuario.getPlan());

                String nombreArchivo = usuario.getNUsuario() + ".ser"; // Nombre de archivo único para cada usuario
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
                objectOutputStream.writeObject(usuario.getGestor());
                objectOutputStream.close();

                mensaje = "ok";
            }
        } catch (IOException ex) {
            mensaje = "Excepcion: " + ex.getMessage();
        } finally {
            try {
                flujo.close();
            } catch (IOException ex) {
                mensaje = "El flujo ya estaba cerrado: " + ex.getMessage();
            }
        }
        return mensaje;
    }
    
    public static Usuario getUsuario(int posicion) {
        Usuario usuario = null;
        try {
            crearFlujo();
            flujo.seek(posicion * TAMREG);
            String nombreUsuario = flujo.readUTF();
            String contraseña = flujo.readUTF();
            String plan = flujo.readUTF();

            String nombreArchivo = nombreUsuario + ".ser";
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(nombreArchivo));
            GestorGeneral gestor = (GestorGeneral) objectInputStream.readObject();
            objectInputStream.close();

            usuario = new Usuario(nombreUsuario, contraseña, gestor, plan);
        } catch(IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                flujo.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return usuario;
    }
    
    public static int getUsuarioNum(Usuario user) {
        crearFlujo();
        
        int posicion=0;
        while (posicion < numRegistros) {
            // Aquí debes procesar cada línea para crear un objeto Usuario
            Usuario usuario = getUsuario(posicion);
            if(usuario.getNUsuario().equals(user.getNUsuario())) {
                return posicion;
            }
            posicion++;
        }
        return -1;//si no lo encuentra (nunca deberia suceder esto)
    }
    
    
    public static String eliminarUsuario(int posicion, Usuario usuarioEliminar) {
        String mensaje = "";
        crearFlujo();
        // Verificar si la posición está dentro del rango de registros
        if (posicion < 0 || posicion >= numRegistros) {
            mensaje = "La posición especificada está fuera del rango de registros.";
        } else {
            // Calcular la nueva cantidad de registros después de eliminar uno
            int nuevosRegistros = numRegistros - 1;
            // Crear un arreglo temporal para almacenar los usuarios restantes
            
            Pila <Usuario> pilaTemporal= new Pila<>(); 
            
            
            int posicionContar=0;
            while (posicionContar < numRegistros) {
                // Aquí debes procesar cada línea para crear un objeto Usuario
                Usuario usuario = getUsuario(posicionContar);
                
                if(!usuario.getNUsuario().equals(usuarioEliminar.getNUsuario())) {
                    pilaTemporal.push(usuario);
                    System.out.println("gaa");
                }
                
                posicionContar++;
            }
            
            eliminarContenidoDeGestor(usuarioEliminar.getNUsuario());
            // Truncar el archivo para eliminar todos los registros
            eliminarContenido();
            
            crearFlujo();
            
            while(!pilaTemporal.empty()) {
                escribirUsuario(pilaTemporal.pop());
            }
            
            numRegistros = nuevosRegistros;
            // Actualizar el número de registros
            mensaje = "Usuario eliminado exitosamente.";
        }
        try {
            flujo.close();
        } catch (IOException ex) {
            mensaje = "El flujo ya estaba cerrado: " + ex.getMessage();
        }
        return mensaje;
    }
    
    public static String eliminarContenido() {
        try {
            FileWriter writer = new FileWriter(nArchivo, false);
            writer.write(""); // Escribir una cadena vacía para eliminar todo el contenido
            writer.close();
        } catch (IOException ex) {
            return "Problema al eliminar el contenido del archivo: " + ex.getMessage();
        }
        return null;
    }
    
    public static String eliminarContenidoDeGestor(String nUsuario) {
        try {
            FileWriter writer = new FileWriter(nUsuario+".ser", false);
            writer.write(""); // Escribir una cadena vacía para eliminar todo el contenido
            writer.close();
        } catch (IOException ex) {
            return "Problema al eliminar el contenido del archivo: " + ex.getMessage();
        }
        return null;
    }
    
    public static String escribirReporte(String reporte, String ubicacion) {
        //ubicacion debe tener la ubicacion de la carpeta y tambien el nombre junto a un
        // .txt al final
        //ejm de ubicacion
        // en local /reportes/(nUsuario)+Reporte.txt
        try {
            FileWriter writer = new FileWriter(ubicacion, false);
            writer.write(reporte); // Escribir una cadena vacía para eliminar todo el contenido
            writer.close();
        } catch (IOException ex) {
            return "Problema al crear el contenido del archivo: " + ex.getMessage();
        }
        return null;
    }

    /*
    public static String eliminarUsuario(String nombreUsuario) {
        String mensaje = "";
        try {
            crearFlujo();
            RandomAccessFile temp = new RandomAccessFile("temp.txt", "rw");
            for (int i = 0; i < numRegistros; i++) {
                flujo.seek(i * TAMREG);
                String nUsuario = flujo.readUTF();
                if (!nUsuario.equals(nombreUsuario)) {
                    temp.writeUTF(nUsuario);
                    temp.writeUTF(flujo.readUTF());

                    // Leer los bytes del objeto gastos
                    int gastosLength = flujo.readInt(); // Leer la longitud de los bytes
                    byte[] gastosBytes = new byte[gastosLength];
                    flujo.readFully(gastosBytes); // Leer los bytes
                    temp.writeInt(gastosBytes.length); // Escribir la longitud de los bytes
                    temp.write(gastosBytes); // Escribir los bytes
                } else {
                    mensaje = "Usuario eliminado correctamente";
                }
            }
            if (mensaje.equals("")) {
                mensaje = "Usuario no encontrado";
            }
            flujo.close();
            temp.close();
            File oldFile = new File(nArchivo);
            oldFile.delete();
            File newFile = new File("temp.txt");
            newFile.renameTo(oldFile);
        } catch (IOException ex) {
            mensaje = "Excepcion: " + ex.getMessage();
        }
        return mensaje;
    }
    */
    
    public static List<Usuario> getContenido() {
        List<Usuario> lista = new ArrayList<>();
        try {
            crearFlujo();
            for(int i=0; i<numRegistros; i++) {
                lista.add(getUsuario(i));
            }
        } finally {
            try {
                flujo.close();
            } catch (IOException ex){
                System.out.println("El flujo ya estaba cerrado: " + ex.getMessage());
            }
        }
        return lista;
    }
    
    public static int getNumRegistros() {
        try {
            crearFlujo();
            flujo.close();
        } catch(IOException ex) {
            System.out.println("El flujo ya estaba cerrado: " + ex.getMessage());
        }
        return numRegistros;
    }
    
    
    public static String imprimirGastosUsuario(Usuario usuario, String nombreArchivo) {
        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Obtener el gestor de gastos del usuario
            GestorGeneral gestor = usuario.getGestor();

            // Obtener los gastos del gestor
            Pila<Operacion> gastos = gestor.getPilaGasto();

            // Crear una lista para almacenar los gastos
            List<Double> listaDeGastos = new ArrayList<>();

            // Recorrer la pila de gastos y almacenarlos en la lista
            while (!gastos.empty()) {
                listaDeGastos.add(gastos.pop().getMonto());
            }

            // Obtener una pila nueva para iterar sobre los gastos nuevamente
            Pila<Operacion> gastosConcepto = gestor.getPilaGasto();

            // Iterar sobre todos los gastos y escribirlos en el archivo
            for (Double gasto : listaDeGastos) {
                String linea = "Concepto: " + gastosConcepto.pop().getDetalles() + ", Cantidad: " + gasto;
                bufferedWriter.write(linea);
                bufferedWriter.newLine(); // Agregar una nueva línea para cada gasto
            }

            bufferedWriter.close();
            fileWriter.close();

            String mensaje = "Gastos del usuario impresos en el archivo " + nombreArchivo;
            return mensaje;
        } catch (IOException ex) {
            String mensajeError = "Error al imprimir los gastos del usuario: " + ex.getMessage();
            return mensajeError;
        }
    }

    
}
//se pueden agregar mas metodos
//para alguna operacion funcional que tenga que ver con el archivo