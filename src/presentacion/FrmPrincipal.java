/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

//El tipo de fuente del FrmPrincipal es Seoge Ui
//El del login es Google Sans (Extraido de la pagina del soporte xd)

import javax.swing.*;
import datos.*;
import entidades.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import logica.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author lguim
 */
public class FrmPrincipal extends javax.swing.JFrame {

    Usuario usuario=new Usuario();
    int visibilidadMenu=0;
    int visibilidadUsuario=0;
    int contCombos=0;
    
    //VARIABLES PARA HABILITAR EN EL IMPRIMIR
    String reporteGeneralDeUsuario="";
    
    
    DefaultPieDataset grafico = new DefaultPieDataset();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modelo1 = new DefaultTableModel();
    DefaultTableModel modelo2 = new DefaultTableModel();
    GestorGeneral gestor = new GestorGeneral();
    
    /**
     * Creates new form FrmPrincipal
     */
    public FrmPrincipal() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/EconomizerIcon.png")).getImage());
        setExtendedState(MAXIMIZED_BOTH);
        //jInternalFrame1.setVisible(false);
        jPnUsuario.setVisible(false);
        
        JPanelImagen mImagen= new JPanelImagen(jPnBgDeTodo,"/imagenes/bgDeTodo.png");
        jPnBgDeTodo.add(mImagen).repaint();
        jPnBgDeTodo.setOpaque (false);
        jPnBgDeTodo.setBorder(null);
        jPnBgDeTodo.setBackground(new Color(0,0,0,0));
        
        
          
        if (cmbCategorias.getSelectedIndex() == 0){
            cmbCategories1.setVisible(false);
        }
        
        if(validatedCategories()){
            btnConfirmar.setEnabled(false);
        }
        
        jpAgregarCategoria.setVisible(false);
        
        
        
        
    }
    
    public FrmPrincipal(JFrame parent) {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/EconomizerIcon.png")).getImage());
        setExtendedState(MAXIMIZED_BOTH);
        jInternalFrame1.setVisible(false);
        jPnUsuario.setVisible(false);
    }
    
    public FrmPrincipal(Usuario user) {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/EconomizerIcon.png")).getImage());
        setExtendedState(MAXIMIZED_BOTH);
        usuario=user;
        jInternalFrame1.setVisible(false);
        jPnUsuario.setVisible(false);
        lblExportarReporte.setVisible(false);
        lblHola.setText("¡Hola, "+usuario.getNUsuario()+"!");
        
        if(usuario.getPlan().equals("Premium")) {
            lblPrincipalEconomizer.setText("Economizer Premium");
        }
        
        if(usuario.getGestor()==null) {
            System.out.println("no hacer nada");
        }
        else {
            gestor=usuario.getGestor();
            System.out.println("pase porque en el gestor si hay datos");
            
            //PILA GENERAL EN TABLA
            if(gestor.getPilaGeneral().empty()) {
                System.out.println("es null en pila general");
                //nunca deberia suceder esto
            }
            else {
                gestor.mostrarOperaciones(modelo);
            }
            
            //PILA DE INGRESOS EN TABLA
            if(gestor.getPilaIngreso().empty()) {
                System.out.println("es null en pila ingreso");
            }
            else {
                Pila<Operacion> ingresoVer=gestor.getPilaIngreso().copiarPila();
                System.out.println(ingresoVer.toString());
                gestor.mostrarOperacionesI(modelo1);
            }
            
            //PILA DE GASTOS EN TABLA
            if(gestor.getPilaGasto().empty()) {
                System.out.println("es null en pila gasto");
            }
            else {
                Pila<Operacion> gastoVer=gestor.getPilaGasto().copiarPila();
                gestor.mostrarOperacionesG(modelo2);
            }
            
             //PILA DE CATEGORIAS DE INGRESOS EN CBX
            if(gestor.getPilaCategoriaIngreso().empty()) {
                System.out.println("es null en pila categoria ingreso");
            }
            else {
                Pila<Categoria> categoriasIngresos = gestor.getPilaCategoriaIngreso().copiarPila();
                Pila<Categoria> categoriasIngresos1 = gestor.getPilaCategoriaIngreso().copiarPila();
                
                cmbCategories.removeAllItems();
                while(!categoriasIngresos.empty()){
                    cmbCategories.addItem(categoriasIngresos.pop().getNombreCat());
                }
                while(!categoriasIngresos1.empty()){
                    cmbCategoriasIngreso.addItem(categoriasIngresos1.pop().getNombreCat());
                }
            }
            
            //PILA DE CATEGORIA GASTOS EN CBX
            if(gestor.getPilaCategoriaGasto().empty()) {
                System.out.println("es null en pila categoria gasto");
            }
            else {
                Pila<Categoria> categoriasGastos = gestor.getPilaCategoriaGasto().copiarPila();
                Pila<Categoria> categoriasGastos1 = gestor.getPilaCategoriaGasto().copiarPila();
                cmbCategories1.removeAllItems();
                while(!categoriasGastos.empty()){
                    cmbCategories1.addItem(categoriasGastos.pop().getNombreCat());
                }
                while(!categoriasGastos1.empty()){
                    cmbCategoriasGasto.addItem(categoriasGastos1.pop().getNombreCat());
                }
                
            }
            //INICIALIZAR AQUI TODO LO QUE TENGA QUE VER CON EL
            //INICIALIZADO DE DATOS YA SEA DE TABLA DE CBX O DE CUALQUIER OTRA OPERACION QUE TENGA
            //QUE VER CON LOS ATRIBUTOS ALMACENADOS DE GESTORGENERAL
            
            
        }
        if(usuario.getPlan().equals("Premium")) {
            Random r = new Random();
            int numero = r.nextInt(50);
            String imagenUbi="";
            System.out.println("el numero es "+ numero);
            if(numero>=25) {
                imagenUbi="/imagenes/bgDeTodoPremium1.png";
            }
            else {
                imagenUbi="/imagenes/bgDeTodoPremium.png";
            }
            JPanelImagen mImagen= new JPanelImagen(jPnBgDeTodo,imagenUbi);
            jPnBgDeTodo.add(mImagen).repaint();
            jPnBgDeTodo.setOpaque (false);
            jPnBgDeTodo.setBorder(null);
            jPnBgDeTodo.setBackground(new Color(0,0,0,0));
            jpInicio.setBackground(new Color(0,0,0,0));
            lblBienvenidaPrincipal.setForeground(Color.white);
        } else {
            JPanelImagen mImagen= new JPanelImagen(jPnBgDeTodo,"/imagenes/bgDeTodo.png");
            jPnBgDeTodo.add(mImagen).repaint();
            jPnBgDeTodo.setOpaque (false);
            jPnBgDeTodo.setBorder(null);
            jPnBgDeTodo.setBackground(new Color(0,0,0,0));
        }
        
        
        
          
        if (cmbCategorias.getSelectedIndex() == 0){
            cmbCategories1.setVisible(false);
        }
        
        if(validatedCategories()){
            btnConfirmar.setEnabled(false);
        }
        
        jpAgregarCategoria.setVisible(false);
        
        
        
    }
    
    public boolean validatedCategories(){
        if(cmbCategories.getSelectedItem().toString().equals("-Sin categorias disponibles-") && cmbCategories1.getSelectedItem().toString().equals("-Sin categorias disponibles-")){
            return true;
        }else{
            if(cmbCategorias.getSelectedItem().toString().equals("Ingreso") && cmbCategories.getSelectedItem().toString().equals("-Sin categorias disponibles-")){
                return true;
            }
            if(cmbCategorias.getSelectedItem().toString().equals("Gasto") && cmbCategories1.getSelectedItem().toString().equals("-Sin categorias disponibles-")){
                return true;
            }
        }
        return false;
    }
    
    public void limpiarCampos(){
        txtNameCategoria.setText("");
        txtFecha.setText("");
        txtMonto.setText("");
        txtDescripcion.setText("");
    }
    
    public void resetCmb(){
        cmbCategorias.setSelectedIndex(0);
        cmbCategories.setSelectedIndex(0);
        cmbCategories1.setSelectedIndex(0);
        cmbTipoCategoria.setSelectedIndex(0);
        cmbTipoCambio.setSelectedIndex(0);
        jpAgregarCategoria.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        lblCambiarFoto = new javax.swing.JLabel();
        lblConfiguracionPerfil = new javax.swing.JLabel();
        lblBalanceGeneralMenu = new javax.swing.JLabel();
        lblMejorarPlan = new javax.swing.JLabel();
        lblSoporte = new javax.swing.JLabel();
        lblReportarBug = new javax.swing.JLabel();
        lblReiniciarPerfil = new javax.swing.JLabel();
        lblEliminarPerfil = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPnUsuario = new javax.swing.JPanel();
        lblHola = new javax.swing.JLabel();
        lblCambiarNombre = new javax.swing.JLabel();
        lblCambiarContraseña = new javax.swing.JLabel();
        lblCambiarDeCuenta = new javax.swing.JLabel();
        lblCerrarSesion = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblHamburguesa = new javax.swing.JLabel();
        lblHome = new javax.swing.JLabel();
        lblAgregar = new javax.swing.JLabel();
        lblTabla = new javax.swing.JLabel();
        lblGrafico = new javax.swing.JLabel();
        lblImprimir = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblPrincipalEconomizer = new javax.swing.JLabel();
        tbpMenu = new javax.swing.JTabbedPane();
        jpInicio = new javax.swing.JPanel();
        jPnBgDeTodo = new javax.swing.JPanel();
        lblBienvenidaPrincipal = new javax.swing.JLabel();
        jpCategoria = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbCategorias = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbCategories = new javax.swing.JComboBox<>();
        jpAgregarCategoria = new javax.swing.JPanel();
        btnAgregarCategoria = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbTipoCategoria = new javax.swing.JComboBox<>();
        txtNameCategoria = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        btnSiguiente1 = new javax.swing.JButton();
        btnCancelar12 = new javax.swing.JButton();
        cmbCategories1 = new javax.swing.JComboBox<>();
        jpMontos = new javax.swing.JPanel();
        lblRellenar = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cmbTipoCambio = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        btnConfirmar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jpTablas = new javax.swing.JPanel();
        tbpTablas = new javax.swing.JTabbedPane();
        jpMovimientos = new javax.swing.JPanel();
        lblRellenar2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblSusGastos = new javax.swing.JLabel();
        lblSusIngresos = new javax.swing.JLabel();
        cmbOrdenMov = new javax.swing.JComboBox<>();
        lblSusIngresos1 = new javax.swing.JLabel();
        jpIngresos = new javax.swing.JPanel();
        lblSusIngresosI = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        lblSusGastosI = new javax.swing.JLabel();
        lblSusMovimientosI = new javax.swing.JLabel();
        lblSusIngresos2 = new javax.swing.JLabel();
        cmbOrdenIngresos = new javax.swing.JComboBox<>();
        lblSusIngresos3 = new javax.swing.JLabel();
        cmbCategoriasIngreso = new javax.swing.JComboBox<>();
        jpGastos = new javax.swing.JPanel();
        lblSusGastosG = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        lblSusMovimientosG = new javax.swing.JLabel();
        lblSusIngresosG = new javax.swing.JLabel();
        lblSusIngresos4 = new javax.swing.JLabel();
        cmbOrdenGastos = new javax.swing.JComboBox<>();
        lblSusIngresos5 = new javax.swing.JLabel();
        cmbCategoriasGasto = new javax.swing.JComboBox<>();
        jpGraficos = new javax.swing.JPanel();
        jpGraficosAbsolute = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnGenerarGrafico = new javax.swing.JButton();
        jpImprimir = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblGenerarReporte = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblExportarReporte = new javax.swing.JLabel();
        lblMenorGastoTexto = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblMenorIngresoTexto = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblMenorOperacionTexto = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblMayorGastoTexto = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblMayorIngresoTexto = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblMayorOperacionTexto = new javax.swing.JLabel();
        jpExportar = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        lblGuardarArchivo = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtRutaDeArchivo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        lblConfigurarRuta = new javax.swing.JLabel();
        lblAtrasExportar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Economizer");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(32, 32, 32));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setTitle("Menu de Economizer");
        jInternalFrame1.setVisible(false);

        jPanel13.setBackground(new java.awt.Color(51, 51, 51));

        jPanel14.setBackground(new java.awt.Color(34, 34, 34));

        lblCambiarFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CambiarFotodePerfil.png"))); // NOI18N
        lblCambiarFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCambiarFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCambiarFotoMouseClicked(evt);
            }
        });

        lblConfiguracionPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ConfiguracionDelPerfil.png"))); // NOI18N
        lblConfiguracionPerfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblConfiguracionPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConfiguracionPerfilMouseClicked(evt);
            }
        });

        lblBalanceGeneralMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/BalanceGeneral.png"))); // NOI18N
        lblBalanceGeneralMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBalanceGeneralMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBalanceGeneralMenuMouseClicked(evt);
            }
        });

        lblMejorarPlan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/MejorarPlan.png"))); // NOI18N
        lblMejorarPlan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMejorarPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMejorarPlanMouseClicked(evt);
            }
        });

        lblSoporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Soporte.png"))); // NOI18N
        lblSoporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSoporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSoporteMouseClicked(evt);
            }
        });

        lblReportarBug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ReportarBug.png"))); // NOI18N
        lblReportarBug.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblReportarBug.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReportarBugMouseClicked(evt);
            }
        });

        lblReiniciarPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ReiniciarPerfil.png"))); // NOI18N
        lblReiniciarPerfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblReiniciarPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReiniciarPerfilMouseClicked(evt);
            }
        });

        lblEliminarPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/EliminarPerfil.png"))); // NOI18N
        lblEliminarPerfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEliminarPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEliminarPerfilMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCambiarFoto)
                    .addComponent(lblSoporte))
                .addGap(94, 94, 94)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblConfiguracionPerfil)
                    .addComponent(lblReportarBug))
                .addGap(97, 97, 97)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBalanceGeneralMenu)
                    .addComponent(lblReiniciarPerfil))
                .addGap(94, 94, 94)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEliminarPerfil)
                    .addComponent(lblMejorarPlan))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMejorarPlan)
                    .addComponent(lblBalanceGeneralMenu)
                    .addComponent(lblConfiguracionPerfil)
                    .addComponent(lblCambiarFoto))
                .addGap(82, 82, 82)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSoporte)
                    .addComponent(lblReportarBug)
                    .addComponent(lblReiniciarPerfil)
                    .addComponent(lblEliminarPerfil))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Menu");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(520, 520, 520))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jInternalFrame1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 1190, 700));

        jPnUsuario.setBackground(new java.awt.Color(51, 51, 51));

        lblHola.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblHola.setForeground(new java.awt.Color(255, 255, 255));
        lblHola.setText("¡Hola! ");

        lblCambiarNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CambiarNombreDeUsuario.png"))); // NOI18N
        lblCambiarNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCambiarNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCambiarNombreMouseClicked(evt);
            }
        });

        lblCambiarContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CambiarContraseña.png"))); // NOI18N
        lblCambiarContraseña.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCambiarContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCambiarContraseñaMouseClicked(evt);
            }
        });

        lblCambiarDeCuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CambiarDeCuenta.png"))); // NOI18N
        lblCambiarDeCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCambiarDeCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCambiarDeCuentaMouseClicked(evt);
            }
        });

        lblCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CerrarSesion.png"))); // NOI18N
        lblCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarSesionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPnUsuarioLayout = new javax.swing.GroupLayout(jPnUsuario);
        jPnUsuario.setLayout(jPnUsuarioLayout);
        jPnUsuarioLayout.setHorizontalGroup(
            jPnUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnUsuarioLayout.createSequentialGroup()
                .addGroup(jPnUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnUsuarioLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPnUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCambiarContraseña)
                            .addComponent(lblCambiarNombre)
                            .addComponent(lblCerrarSesion)
                            .addComponent(lblCambiarDeCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPnUsuarioLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(lblHola)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPnUsuarioLayout.setVerticalGroup(
            jPnUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnUsuarioLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblHola)
                .addGap(31, 31, 31)
                .addComponent(lblCambiarNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCambiarContraseña)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(lblCambiarDeCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCerrarSesion)
                .addGap(15, 15, 15))
        );

        jPanel1.add(jPnUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 50, 280, 360));

        jPanel2.setBackground(new java.awt.Color(10, 10, 10));

        jPanel4.setBackground(new java.awt.Color(27, 26, 25));

        lblHamburguesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Menu_Google.png"))); // NOI18N
        lblHamburguesa.setToolTipText("Menu");
        lblHamburguesa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHamburguesa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHamburguesaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHamburguesa)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(lblHamburguesa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblHome.setForeground(new java.awt.Color(255, 255, 255));
        lblHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/home.png"))); // NOI18N
        lblHome.setToolTipText("Inicio");
        lblHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHomeMouseClicked(evt);
            }
        });

        lblAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar.png"))); // NOI18N
        lblAgregar.setToolTipText("Crear");
        lblAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAgregarMouseClicked(evt);
            }
        });

        lblTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tabla.png"))); // NOI18N
        lblTabla.setToolTipText("Tablas");
        lblTabla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTablaMouseClicked(evt);
            }
        });

        lblGrafico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grafico.png"))); // NOI18N
        lblGrafico.setToolTipText("Grafica");
        lblGrafico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblGrafico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGraficoMouseClicked(evt);
            }
        });

        lblImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/imprimir.png"))); // NOI18N
        lblImprimir.setToolTipText("Imprimir Reporte");
        lblImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImprimirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblImprimir)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblTabla)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblGrafico))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lblHome)
                .addGap(32, 32, 32)
                .addComponent(lblAgregar)
                .addGap(29, 29, 29)
                .addComponent(lblTabla)
                .addGap(28, 28, 28)
                .addComponent(lblGrafico)
                .addGap(30, 30, 30)
                .addComponent(lblImprimir)
                .addGap(0, 318, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 700));

        jPanel3.setBackground(new java.awt.Color(27, 26, 25));

        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/User_Principal_Google.png"))); // NOI18N
        lblUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsuarioMouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Configuracion_Google.png"))); // NOI18N

        lblPrincipalEconomizer.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblPrincipalEconomizer.setForeground(new java.awt.Color(255, 255, 255));
        lblPrincipalEconomizer.setText("Economizer");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblPrincipalEconomizer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1039, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUsuario)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPrincipalEconomizer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 1310, 50));

        tbpMenu.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jpInicio.setBackground(new java.awt.Color(204, 255, 255));
        jpInicio.setForeground(new java.awt.Color(204, 255, 255));

        lblBienvenidaPrincipal.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        lblBienvenidaPrincipal.setText("Le damos la bienvenida a Economizer");

        javax.swing.GroupLayout jPnBgDeTodoLayout = new javax.swing.GroupLayout(jPnBgDeTodo);
        jPnBgDeTodo.setLayout(jPnBgDeTodoLayout);
        jPnBgDeTodoLayout.setHorizontalGroup(
            jPnBgDeTodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnBgDeTodoLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(lblBienvenidaPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(690, Short.MAX_VALUE))
        );
        jPnBgDeTodoLayout.setVerticalGroup(
            jPnBgDeTodoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnBgDeTodoLayout.createSequentialGroup()
                .addContainerGap(182, Short.MAX_VALUE)
                .addComponent(lblBienvenidaPrincipal)
                .addGap(140, 140, 140))
        );

        javax.swing.GroupLayout jpInicioLayout = new javax.swing.GroupLayout(jpInicio);
        jpInicio.setLayout(jpInicioLayout);
        jpInicioLayout.setHorizontalGroup(
            jpInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPnBgDeTodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpInicioLayout.setVerticalGroup(
            jpInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInicioLayout.createSequentialGroup()
                .addComponent(jPnBgDeTodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 287, Short.MAX_VALUE))
        );

        tbpMenu.addTab("tab5", jpInicio);

        jpCategoria.setBackground(new java.awt.Color(32, 32, 32));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Seleccione Categoria ");

        cmbCategorias.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbCategorias.setForeground(new java.awt.Color(255, 255, 255));
        cmbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ingreso", "Gasto" }));
        cmbCategorias.setToolTipText("");
        cmbCategorias.setName(""); // NOI18N
        cmbCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoriasActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Seleccione Movimiento");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Categorias Disponibles");

        cmbCategories.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbCategories.setForeground(new java.awt.Color(255, 255, 255));
        cmbCategories.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Sin categorias disponibles-" }));
        cmbCategories.setToolTipText("");
        cmbCategories.setName(""); // NOI18N
        cmbCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoriesActionPerformed(evt);
            }
        });

        jpAgregarCategoria.setBackground(new java.awt.Color(41, 41, 41));
        jpAgregarCategoria.setForeground(new java.awt.Color(51, 51, 51));
        jpAgregarCategoria.setToolTipText("");
        jpAgregarCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jpAgregarCategoria.setInheritsPopupMenu(true);

        btnAgregarCategoria.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnAgregarCategoria.setText("Agregar");
        btnAgregarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCategoriaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Agregar Categoria");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tipo de Categoria");

        cmbTipoCategoria.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTipoCategoria.setForeground(new java.awt.Color(255, 255, 255));
        cmbTipoCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ingreso", "Gasto" }));
        cmbTipoCategoria.setToolTipText("");
        cmbTipoCategoria.setName(""); // NOI18N
        cmbTipoCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbTipoCategoriaMouseClicked(evt);
            }
        });
        cmbTipoCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoCategoriaActionPerformed(evt);
            }
        });

        txtNameCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        txtNameCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameCategoriaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nombre de Caregoria");

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpAgregarCategoriaLayout = new javax.swing.GroupLayout(jpAgregarCategoria);
        jpAgregarCategoria.setLayout(jpAgregarCategoriaLayout);
        jpAgregarCategoriaLayout.setHorizontalGroup(
            jpAgregarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAgregarCategoriaLayout.createSequentialGroup()
                .addGroup(jpAgregarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpAgregarCategoriaLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel8))
                    .addGroup(jpAgregarCategoriaLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jpAgregarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpAgregarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmbTipoCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtNameCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpAgregarCategoriaLayout.createSequentialGroup()
                                .addComponent(btnAgregarCategoria)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 334, Short.MAX_VALUE)
                                .addComponent(jButton2)))))
                .addGap(70, 70, 70))
        );
        jpAgregarCategoriaLayout.setVerticalGroup(
            jpAgregarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAgregarCategoriaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addGap(34, 34, 34)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNameCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jpAgregarCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarCategoria)
                    .addComponent(jButton2))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Nirmala UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("¿Agregar Categoria?");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        btnSiguiente1.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        btnSiguiente1.setText("Siguiente");
        btnSiguiente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguiente1ActionPerformed(evt);
            }
        });

        btnCancelar12.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        btnCancelar12.setText("Cancelar");
        btnCancelar12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar12ActionPerformed(evt);
            }
        });

        cmbCategories1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbCategories1.setForeground(new java.awt.Color(255, 255, 255));
        cmbCategories1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Sin categorias disponibles-" }));
        cmbCategories1.setToolTipText("");
        cmbCategories1.setName(""); // NOI18N
        cmbCategories1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategories1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCategoriaLayout = new javax.swing.GroupLayout(jpCategoria);
        jpCategoria.setLayout(jpCategoriaLayout);
        jpCategoriaLayout.setHorizontalGroup(
            jpCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCategoriaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCategoriaLayout.createSequentialGroup()
                        .addGroup(jpCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(cmbCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCategories1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(jpAgregarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99))
                    .addGroup(jpCategoriaLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jpCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jpCategoriaLayout.createSequentialGroup()
                                .addComponent(btnSiguiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(206, 206, 206)
                                .addComponent(btnCancelar12, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jpCategoriaLayout.setVerticalGroup(
            jpCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCategoriaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jpCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCategoriaLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jLabel7)
                        .addGap(25, 25, 25)
                        .addComponent(cmbCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCategories1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpAgregarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jpCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSiguiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar12, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );

        tbpMenu.addTab("tab1", jpCategoria);

        jpMontos.setBackground(new java.awt.Color(32, 32, 32));

        lblRellenar.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        lblRellenar.setForeground(new java.awt.Color(255, 255, 255));
        lblRellenar.setText("Rellene los campos de su Movimiento");

        txtMonto.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMonto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Monto:");

        cmbTipoCambio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbTipoCambio.setForeground(new java.awt.Color(255, 255, 255));
        cmbTipoCambio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PEN", "USD", "EUR" }));
        cmbTipoCambio.setToolTipText("");
        cmbTipoCambio.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo de Cambio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        cmbTipoCambio.setName(""); // NOI18N
        cmbTipoCambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoCambioActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Fecha:");

        txtFecha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Descripción:");

        txtDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtDescripcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });

        btnConfirmar.setFont(new java.awt.Font("Trebuchet MS", 0, 24)); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Nirmala UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("¿Registrar otro Movimiento?");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("<- atras");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpMontosLayout = new javax.swing.GroupLayout(jpMontos);
        jpMontos.setLayout(jpMontosLayout);
        jpMontosLayout.setHorizontalGroup(
            jpMontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMontosLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jpMontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRellenar)
                    .addGroup(jpMontosLayout.createSequentialGroup()
                        .addGroup(jpMontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMonto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(cmbTipoCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(593, Short.MAX_VALUE))
            .addGroup(jpMontosLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpMontosLayout.setVerticalGroup(
            jpMontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMontosLayout.createSequentialGroup()
                .addComponent(jLabel16)
                .addGap(2, 2, 2)
                .addComponent(lblRellenar)
                .addGroup(jpMontosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpMontosLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpMontosLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(cmbTipoCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        tbpMenu.addTab("tab2", jpMontos);

        tbpTablas.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jpMovimientos.setBackground(new java.awt.Color(32, 32, 32));

        lblRellenar2.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        lblRellenar2.setForeground(new java.awt.Color(255, 255, 255));
        lblRellenar2.setText("Sus Movimientos");

        jTable1.setModel(modelo);
        jScrollPane1.setViewportView(jTable1);

        lblSusGastos.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        lblSusGastos.setForeground(new java.awt.Color(255, 255, 255));
        lblSusGastos.setText("Sus gastos");
        lblSusGastos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Font font = lblSusGastos.getFont();

        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        lblSusGastos.setFont(font.deriveFont(attributes));
        lblSusGastos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusGastosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusGastosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusGastosMouseExited(evt);
            }
        });

        lblSusIngresos.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        lblSusIngresos.setForeground(new java.awt.Color(255, 255, 255));
        lblSusIngresos.setText("Sus Ingresos");
        lblSusIngresos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSusIngresos.setFont(font.deriveFont(attributes));
        lblSusIngresos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusIngresosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusIngresosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusIngresosMouseExited(evt);
            }
        });

        cmbOrdenMov.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbOrdenMov.setForeground(new java.awt.Color(255, 255, 255));
        cmbOrdenMov.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Defecto", "Fecha", "Categoria" }));
        cmbOrdenMov.setToolTipText("");
        cmbOrdenMov.setName(""); // NOI18N
        cmbOrdenMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOrdenMovActionPerformed(evt);
            }
        });

        lblSusIngresos1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblSusIngresos1.setForeground(new java.awt.Color(255, 255, 255));
        lblSusIngresos1.setText("Ordenar por:");
        lblSusIngresos1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSusIngresos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusIngresos1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusIngresos1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusIngresos1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jpMovimientosLayout = new javax.swing.GroupLayout(jpMovimientos);
        jpMovimientos.setLayout(jpMovimientosLayout);
        jpMovimientosLayout.setHorizontalGroup(
            jpMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMovimientosLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jpMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSusIngresos1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbOrdenMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpMovimientosLayout.createSequentialGroup()
                        .addComponent(lblRellenar2)
                        .addGap(82, 82, 82)
                        .addComponent(lblSusIngresos)
                        .addGap(76, 76, 76)
                        .addComponent(lblSusGastos)))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jpMovimientosLayout.setVerticalGroup(
            jpMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMovimientosLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jpMovimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRellenar2)
                    .addComponent(lblSusIngresos)
                    .addComponent(lblSusGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(lblSusIngresos1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbOrdenMov, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        tbpTablas.addTab("tab3", jpMovimientos);

        jpIngresos.setBackground(new java.awt.Color(32, 32, 32));

        lblSusIngresosI.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        lblSusIngresosI.setForeground(new java.awt.Color(255, 255, 255));
        lblSusIngresosI.setText("Sus Ingresos");

        jTable2.setModel(modelo1);
        jScrollPane2.setViewportView(jTable2);

        lblSusGastosI.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        lblSusGastosI.setForeground(new java.awt.Color(255, 255, 255));
        lblSusGastosI.setText("Sus Gastos");
        lblSusGastosI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Font font3 = lblSusGastosI.getFont();

        Map attributes3 = font3.getAttributes();
        attributes3.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        lblSusGastosI.setFont(font3.deriveFont(attributes3));
        lblSusGastosI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusGastosIMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusGastosIMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusGastosIMouseExited(evt);
            }
        });

        lblSusMovimientosI.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        lblSusMovimientosI.setForeground(new java.awt.Color(255, 255, 255));
        lblSusMovimientosI.setText("Sus Movimientos");
        lblSusMovimientosI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Font font2 = lblSusMovimientosI.getFont();

        Map attributes2 = font2.getAttributes();
        attributes2.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        lblSusMovimientosI.setFont(font2.deriveFont(attributes2));
        lblSusMovimientosI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusMovimientosIMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusMovimientosIMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusMovimientosIMouseExited(evt);
            }
        });

        lblSusIngresos2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblSusIngresos2.setForeground(new java.awt.Color(255, 255, 255));
        lblSusIngresos2.setText("Ordenar por:");
        lblSusIngresos2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSusIngresos2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusIngresos2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusIngresos2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusIngresos2MouseExited(evt);
            }
        });

        cmbOrdenIngresos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbOrdenIngresos.setForeground(new java.awt.Color(255, 255, 255));
        cmbOrdenIngresos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Defecto", "Fecha", "Categoria", "Monto" }));
        cmbOrdenIngresos.setToolTipText("");
        cmbOrdenIngresos.setName(""); // NOI18N
        cmbOrdenIngresos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOrdenIngresosActionPerformed(evt);
            }
        });

        lblSusIngresos3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblSusIngresos3.setForeground(new java.awt.Color(255, 255, 255));
        lblSusIngresos3.setText("Filtrar Por Categoria:");
        lblSusIngresos3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSusIngresos3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusIngresos3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusIngresos3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusIngresos3MouseExited(evt);
            }
        });

        cmbCategoriasIngreso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbCategoriasIngreso.setForeground(new java.awt.Color(255, 255, 255));
        cmbCategoriasIngreso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Defecto" }));
        cmbCategoriasIngreso.setToolTipText("");
        cmbCategoriasIngreso.setName(""); // NOI18N
        cmbCategoriasIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoriasIngresoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpIngresosLayout = new javax.swing.GroupLayout(jpIngresos);
        jpIngresos.setLayout(jpIngresosLayout);
        jpIngresosLayout.setHorizontalGroup(
            jpIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpIngresosLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jpIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpIngresosLayout.createSequentialGroup()
                        .addGroup(jpIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpIngresosLayout.createSequentialGroup()
                                .addComponent(lblSusIngresos2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(769, 769, 769))
                            .addGroup(jpIngresosLayout.createSequentialGroup()
                                .addComponent(cmbOrdenIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(787, 787, 787)))
                        .addGroup(jpIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSusIngresos3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCategoriasIngreso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpIngresosLayout.createSequentialGroup()
                        .addComponent(lblSusIngresosI)
                        .addGap(75, 75, 75)
                        .addComponent(lblSusMovimientosI, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(lblSusGastosI)))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jpIngresosLayout.setVerticalGroup(
            jpIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpIngresosLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jpIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSusIngresosI)
                    .addComponent(lblSusMovimientosI)
                    .addComponent(lblSusGastosI))
                .addGap(49, 49, 49)
                .addGroup(jpIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSusIngresos2)
                    .addComponent(lblSusIngresos3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpIngresosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbOrdenIngresos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCategoriasIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        tbpTablas.addTab("tab3", jpIngresos);

        jpGastos.setBackground(new java.awt.Color(32, 32, 32));

        lblSusGastosG.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        lblSusGastosG.setForeground(new java.awt.Color(255, 255, 255));
        lblSusGastosG.setText("Sus Gastos");

        jTable3.setModel(modelo2);
        jScrollPane3.setViewportView(jTable3);

        lblSusMovimientosG.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        lblSusMovimientosG.setForeground(new java.awt.Color(255, 255, 255));
        lblSusMovimientosG.setText("Sus Movimientos");
        lblSusMovimientosG.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Font font5 = lblSusMovimientosG.getFont();

        Map attributes5 = font5.getAttributes();
        attributes5.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        lblSusMovimientosG.setFont(font5.deriveFont(attributes5));
        lblSusMovimientosG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusMovimientosGMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusMovimientosGMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusMovimientosGMouseExited(evt);
            }
        });

        lblSusIngresosG.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        lblSusIngresosG.setForeground(new java.awt.Color(255, 255, 255));
        lblSusIngresosG.setText("Sus Ingresos");
        lblSusIngresosG.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Font font4 = lblSusIngresosG.getFont();

        Map attributes4 = font4.getAttributes();
        attributes4.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        lblSusIngresosG.setFont(font4.deriveFont(attributes4));
        lblSusIngresosG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusIngresosGMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusIngresosGMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusIngresosGMouseExited(evt);
            }
        });

        lblSusIngresos4.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblSusIngresos4.setForeground(new java.awt.Color(255, 255, 255));
        lblSusIngresos4.setText("Ordenar por:");
        lblSusIngresos4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSusIngresos4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusIngresos4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusIngresos4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusIngresos4MouseExited(evt);
            }
        });

        cmbOrdenGastos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbOrdenGastos.setForeground(new java.awt.Color(255, 255, 255));
        cmbOrdenGastos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Defecto", "Fecha", "Categoria", "Monto" }));
        cmbOrdenGastos.setToolTipText("");
        cmbOrdenGastos.setName(""); // NOI18N
        cmbOrdenGastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOrdenGastosActionPerformed(evt);
            }
        });

        lblSusIngresos5.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblSusIngresos5.setForeground(new java.awt.Color(255, 255, 255));
        lblSusIngresos5.setText("Filtrar Por Categoria:");
        lblSusIngresos5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSusIngresos5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSusIngresos5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSusIngresos5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSusIngresos5MouseExited(evt);
            }
        });

        cmbCategoriasGasto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbCategoriasGasto.setForeground(new java.awt.Color(255, 255, 255));
        cmbCategoriasGasto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por Defecto" }));
        cmbCategoriasGasto.setToolTipText("");
        cmbCategoriasGasto.setName(""); // NOI18N
        cmbCategoriasGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoriasGastoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpGastosLayout = new javax.swing.GroupLayout(jpGastos);
        jpGastos.setLayout(jpGastosLayout);
        jpGastosLayout.setHorizontalGroup(
            jpGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGastosLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jpGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpGastosLayout.createSequentialGroup()
                        .addGroup(jpGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbOrdenGastos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSusIngresos4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(769, 769, 769)
                        .addGroup(jpGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSusIngresos5, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCategoriasGasto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpGastosLayout.createSequentialGroup()
                        .addComponent(lblSusGastosG)
                        .addGap(86, 86, 86)
                        .addComponent(lblSusIngresosG)
                        .addGap(83, 83, 83)
                        .addComponent(lblSusMovimientosG)))
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jpGastosLayout.setVerticalGroup(
            jpGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpGastosLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jpGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSusGastosG)
                    .addComponent(lblSusIngresosG)
                    .addComponent(lblSusMovimientosG))
                .addGap(49, 49, 49)
                .addGroup(jpGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSusIngresos5)
                    .addComponent(lblSusIngresos4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpGastosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCategoriasGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbOrdenGastos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        tbpTablas.addTab("tab3", jpGastos);

        javax.swing.GroupLayout jpTablasLayout = new javax.swing.GroupLayout(jpTablas);
        jpTablas.setLayout(jpTablasLayout);
        jpTablasLayout.setHorizontalGroup(
            jpTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbpTablas)
        );
        jpTablasLayout.setVerticalGroup(
            jpTablasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTablasLayout.createSequentialGroup()
                .addComponent(tbpTablas, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tbpMenu.addTab("tab2", jpTablas);

        jpGraficosAbsolute.setBackground(new java.awt.Color(102, 102, 102));
        jpGraficosAbsolute.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Grafica");
        jpGraficosAbsolute.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(572, 36, -1, -1));

        btnGenerarGrafico.setText("GENERAR GRAFICO");
        btnGenerarGrafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarGraficoActionPerformed(evt);
            }
        });
        jpGraficosAbsolute.add(btnGenerarGrafico, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 530, 164, 44));

        javax.swing.GroupLayout jpGraficosLayout = new javax.swing.GroupLayout(jpGraficos);
        jpGraficos.setLayout(jpGraficosLayout);
        jpGraficosLayout.setHorizontalGroup(
            jpGraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpGraficosAbsolute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpGraficosLayout.setVerticalGroup(
            jpGraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpGraficosAbsolute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tbpMenu.addTab("tab5", jpGraficos);

        jPanel5.setBackground(new java.awt.Color(83, 83, 83));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblGenerarReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GenerarReporte.png"))); // NOI18N
        lblGenerarReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblGenerarReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGenerarReporteMouseClicked(evt);
            }
        });
        jPanel5.add(lblGenerarReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Imprimir Reporte De Usuario");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, -1, -1));

        lblExportarReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/BotonExportar.png"))); // NOI18N
        lblExportarReporte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblExportarReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExportarReporteMouseClicked(evt);
            }
        });
        jPanel5.add(lblExportarReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 560, -1, -1));

        lblMenorGastoTexto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMenorGastoTexto.setForeground(new java.awt.Color(255, 255, 255));
        lblMenorGastoTexto.setText("No Hay Gastos");
        jPanel5.add(lblMenorGastoTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 230, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Menor Gasto");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, -1, -1));

        lblMenorIngresoTexto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMenorIngresoTexto.setForeground(new java.awt.Color(255, 255, 255));
        lblMenorIngresoTexto.setText("No Hay Ingresos");
        jPanel5.add(lblMenorIngresoTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 320, -1, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Menor Ingreso");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 320, -1, -1));

        lblMenorOperacionTexto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMenorOperacionTexto.setForeground(new java.awt.Color(255, 255, 255));
        lblMenorOperacionTexto.setText("No Hay Operaciones");
        jPanel5.add(lblMenorOperacionTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 420, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Menor Operacion");
        jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 420, -1, -1));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Mayor Gasto");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, -1, -1));

        lblMayorGastoTexto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMayorGastoTexto.setForeground(new java.awt.Color(255, 255, 255));
        lblMayorGastoTexto.setText("No Hay Gastos");
        jPanel5.add(lblMayorGastoTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, -1, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Mayor Ingreso");
        jPanel5.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, -1, -1));

        lblMayorIngresoTexto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMayorIngresoTexto.setForeground(new java.awt.Color(255, 255, 255));
        lblMayorIngresoTexto.setText("No Hay Ingresos");
        jPanel5.add(lblMayorIngresoTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, -1, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Mayor Operacion");
        jPanel5.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, -1, -1));

        lblMayorOperacionTexto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMayorOperacionTexto.setForeground(new java.awt.Color(255, 255, 255));
        lblMayorOperacionTexto.setText("No Hay Operaciones");
        jPanel5.add(lblMayorOperacionTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, -1, -1));

        javax.swing.GroupLayout jpImprimirLayout = new javax.swing.GroupLayout(jpImprimir);
        jpImprimir.setLayout(jpImprimirLayout);
        jpImprimirLayout.setHorizontalGroup(
            jpImprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpImprimirLayout.setVerticalGroup(
            jpImprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tbpMenu.addTab("tab6", jpImprimir);

        jPanel7.setBackground(new java.awt.Color(83, 83, 83));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblGuardarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/GuardarArchivo.png"))); // NOI18N
        lblGuardarArchivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblGuardarArchivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGuardarArchivoMouseClicked(evt);
            }
        });
        jPanel7.add(lblGuardarArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 350, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Ruta del Archivo");
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, -1, -1));

        txtRutaDeArchivo.setEditable(false);
        txtRutaDeArchivo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jPanel7.add(txtRutaDeArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, 560, 40));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Exportar Archivo de Reporte");
        jPanel7.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, -1, -1));

        lblConfigurarRuta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ConfigurarRuta.png"))); // NOI18N
        lblConfigurarRuta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblConfigurarRuta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConfigurarRutaMouseClicked(evt);
            }
        });
        jPanel7.add(lblConfigurarRuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 240, -1, -1));

        lblAtrasExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/BotonAtrasExportar.png"))); // NOI18N
        lblAtrasExportar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAtrasExportar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAtrasExportarMouseClicked(evt);
            }
        });
        jPanel7.add(lblAtrasExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        javax.swing.GroupLayout jpExportarLayout = new javax.swing.GroupLayout(jpExportar);
        jpExportar.setLayout(jpExportarLayout);
        jpExportarLayout.setHorizontalGroup(
            jpExportarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpExportarLayout.setVerticalGroup(
            jpExportarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tbpMenu.addTab("tab7", jpExportar);

        jPanel1.add(tbpMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 1350, 650));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        usuario.setGestor(gestor);
        int numeroDeUsuario=DALUsuario.getUsuarioNum(usuario);
        DALUsuario.setUsuario(numeroDeUsuario,usuario);
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void lblHamburguesaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHamburguesaMouseClicked
        // TODO add your handling code here:
        if(visibilidadMenu==0) {
            jInternalFrame1.setVisible(true);
            visibilidadMenu=1;
        }
        else {
            jInternalFrame1.setVisible(false);
            visibilidadMenu=0;
        }
    }//GEN-LAST:event_lblHamburguesaMouseClicked

    private void lblUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuarioMouseClicked
        // TODO add your handling code here:
        if(visibilidadUsuario==0) {
            jPnUsuario.setVisible(true);
            visibilidadUsuario=1;
        }
        else {
            jPnUsuario.setVisible(false);
            visibilidadUsuario=0;
        }
    }//GEN-LAST:event_lblUsuarioMouseClicked

    private void lblCambiarNombreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambiarNombreMouseClicked
        // TODO add your handling code here:
        usuario.setGestor(gestor);
        int numeroDeUsuario=DALUsuario.getUsuarioNum(usuario);
        DALUsuario.setUsuario(numeroDeUsuario,usuario);
        CambiarNombreDeUsuario aplicacion = new CambiarNombreDeUsuario(usuario,numeroDeUsuario);
        aplicacion.setVisible(true);
        dispose();
    }//GEN-LAST:event_lblCambiarNombreMouseClicked

    private void lblCambiarContraseñaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambiarContraseñaMouseClicked
        // TODO add your handling code here:
        usuario.setGestor(gestor);
        int numeroDeUsuario=DALUsuario.getUsuarioNum(usuario);
        DALUsuario.setUsuario(numeroDeUsuario,usuario);
        CambiarContraseña aplicacion = new CambiarContraseña(usuario,numeroDeUsuario);
        aplicacion.setVisible(true);
        dispose();
    }//GEN-LAST:event_lblCambiarContraseñaMouseClicked

    private void lblCambiarDeCuentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambiarDeCuentaMouseClicked
        // TODO add your handling code here:
        usuario.setGestor(gestor);
        int numeroDeUsuario=DALUsuario.getUsuarioNum(usuario);
        DALUsuario.setUsuario(numeroDeUsuario,usuario);
        JOptionPane.showMessageDialog(null, "Vuelva Pronto "+usuario.getNUsuario()+"!");
        Login aplicacion = new Login();
        aplicacion.setVisible(true);
        dispose();
    }//GEN-LAST:event_lblCambiarDeCuentaMouseClicked

    private void lblCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarSesionMouseClicked
        // TODO add your handling code here:
        usuario.setGestor(gestor);
        int numeroDeUsuario=DALUsuario.getUsuarioNum(usuario);
        DALUsuario.setUsuario(numeroDeUsuario,usuario);
        // Cargar la imagen
        ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/EconomizerIcon.png"));
        // Mostrar el cuadro de diálogo con la imagen
        JOptionPane.showMessageDialog(null, "Vuelva Pronto "+usuario.getNUsuario()+"!", "Mensaje", JOptionPane.INFORMATION_MESSAGE, imagen);
        dispose();
    }//GEN-LAST:event_lblCerrarSesionMouseClicked

    private void lblHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHomeMouseClicked
        // TODO add your handling code here:
        tbpMenu.setSelectedIndex(0);
    }//GEN-LAST:event_lblHomeMouseClicked

    private void lblAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAgregarMouseClicked
        // TODO add your handling code here:
        if (lblAgregar.isEnabled()){
           tbpMenu.setSelectedIndex(1);
           resetCmb();
           limpiarCampos();
        }
    }//GEN-LAST:event_lblAgregarMouseClicked

    private void lblTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTablaMouseClicked
        // TODO add your handling code here:
        tbpMenu.setSelectedIndex(3);
        tbpTablas.setSelectedIndex(0);
    }//GEN-LAST:event_lblTablaMouseClicked

    private void lblGraficoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGraficoMouseClicked
        // TODO add your handling code here:
        tbpMenu.setSelectedIndex(4);
    }//GEN-LAST:event_lblGraficoMouseClicked

    private void lblImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImprimirMouseClicked
        // TODO add your handling code here:
        tbpMenu.setSelectedIndex(5);
    }//GEN-LAST:event_lblImprimirMouseClicked

    private void lblCambiarFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambiarFotoMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lblCambiarFotoMouseClicked

    private void lblConfiguracionPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfiguracionPerfilMouseClicked
        // TODO add your handling code here:
        jInternalFrame1.setVisible(false);
        visibilidadMenu=0;
        tbpMenu.setSelectedIndex(0);
        jPnUsuario.setVisible(true);
        visibilidadUsuario=1;
    }//GEN-LAST:event_lblConfiguracionPerfilMouseClicked

    private void lblBalanceGeneralMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBalanceGeneralMenuMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lblBalanceGeneralMenuMouseClicked

    private void lblMejorarPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMejorarPlanMouseClicked
        // TODO add your handling code here:
        if(usuario.getPlan().equals("Premium")) {
            ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/MejorarPlan.png"));
            JOptionPane.showMessageDialog(null,  usuario.getNUsuario()+", Usted ya tiene premium", "Premium", JOptionPane.INFORMATION_MESSAGE, imagen);
        }
        else {
            String serial = JOptionPane.showInputDialog("Ingrese su serial: ");
            System.out.println(serial+" Esto no hace nada gaa");
            usuario.setPlan("Premium");
            ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/MejorarPlan.png"));
            JOptionPane.showMessageDialog(null,  "Gracias por Elegirnos. \n"+
                    "Usted ya tiene premium y goza de todos los beneficios!", "Premium", JOptionPane.INFORMATION_MESSAGE, imagen);
            lblPrincipalEconomizer.setText("Economizer Premium");
        }
    }//GEN-LAST:event_lblMejorarPlanMouseClicked

    private void lblSoporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSoporteMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lblSoporteMouseClicked

    private void lblReportarBugMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportarBugMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lblReportarBugMouseClicked

    private void lblReiniciarPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReiniciarPerfilMouseClicked
        // TODO add your handling code here:
        String [] arreglo = {"Reiniciar Cuenta","Cancelar"};
        // Cargar la imagen
        ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/ReiniciarPerfil.png"));
        int opcion = JOptionPane.showOptionDialog(null, "Estas seguro de reiniciar tu perfil? "+
            "Perderas todo tu progreso actual.", "Confirmar Reinicio de "+
            "Perfil", 0, JOptionPane.QUESTION_MESSAGE, imagen, arreglo, "Cancelar");
        if(opcion==1) {
            JOptionPane.showMessageDialog(null, "Reinicio de perfil cancelado");
        }
        if(opcion==0) {
            GestorGeneral nuevoGestor=new GestorGeneral();
            int numUsuario= DALUsuario.getUsuarioNum(usuario);
            usuario.setGestor(nuevoGestor);
            DALUsuario.setUsuario(numUsuario, usuario);
            JOptionPane.showMessageDialog(null, "Reinicio de perfil realizado con exito");
        }
    }//GEN-LAST:event_lblReiniciarPerfilMouseClicked

    private void lblEliminarPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEliminarPerfilMouseClicked
        // TODO add your handling code here:
        String [] arreglo = {"Eliminar Cuenta","Cancelar"};
        // Cargar la imagen
        ImageIcon imagen = new ImageIcon(getClass().getResource("/imagenes/EliminarPerfil.png"));
        int opcion = JOptionPane.showOptionDialog(null, "Estas seguro de ELIMINAR tu perfil? "+
            "Perderas todo tu progreso actual, y la cuenta junto con tus datos serán eliminados. "+
            "Una vez hecho esto, no se podrá deshacer.", "Confirmar Eliminado de "+
            "Perfil", 0, JOptionPane.QUESTION_MESSAGE, imagen, arreglo, "Cancelar");
        if(opcion==1) {
            JOptionPane.showMessageDialog(null, "Eliminado de perfil cancelado");
        }
        if(opcion==0) {
            int numUsuario= DALUsuario.getUsuarioNum(usuario);
            String mensaje=DALUsuario.eliminarUsuario(numUsuario,usuario);
            System.out.println(mensaje);
            //DALUsuario.eliminarUsuario(usuario.getNUsuario());
            JOptionPane.showMessageDialog(null, "Eliminado de perfil realizado con éxito. "+
                "Esperamos que su experiencia en Economizer haya sido fructífera.");
            Login aplicacion = new Login();
            aplicacion.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_lblEliminarPerfilMouseClicked

    private void cmbCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoriasActionPerformed
        // TODO add your handling code here:

        if (contCombos==0){
            if(cmbCategorias.getSelectedIndex()==1){
                cmbCategories.setVisible(false);
                cmbCategories1.setVisible(true);
                cmbCategories1.setLocation(cmbCategories.getLocation());
                contCombos=1;
            }
        }else{
            if(cmbCategorias.getSelectedIndex()==0){
                cmbCategories1.setVisible(false);
                cmbCategories.setVisible(true);
                cmbCategories.setLocation(cmbCategories1.getLocation());
                contCombos=0;
            }
        }
    }//GEN-LAST:event_cmbCategoriasActionPerformed

    private void cmbCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoriesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCategoriesActionPerformed

    private void btnAgregarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCategoriaActionPerformed
        // TODO add your handling code here:
          if(txtNameCategoria.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Escriba un nombre para su Categoría.");
            return;
        }
        String categoria = txtNameCategoria.getText();
        if(cmbTipoCategoria.getSelectedIndex()==0){
            gestor.agregarCategoriaIngreso(categoria);
            int emp = cmbCategories.getSelectedIndex();
            if(cmbCategories.getItemAt(emp).equals("-Sin categorias disponibles-")){
                cmbCategories.removeAllItems();
            }
            cmbCategories.addItem(categoria);
            cmbCategoriasIngreso.addItem(categoria);
            
            txtNameCategoria.setText("");
            gestor.imprimirPilaIngresos();
        }
        if(cmbTipoCategoria.getSelectedIndex()==1){
            gestor.agregarCategoriaGasto(categoria);
            int emp = cmbCategories1.getSelectedIndex();
            if(cmbCategories1.getItemAt(emp).equals("-Sin categorias disponibles-")){
                cmbCategories1.removeAllItems();
            }
            cmbCategories1.addItem(categoria);
            
            cmbCategoriasGasto.addItem(categoria);
            
            
            txtNameCategoria.setText("");
            gestor.imprimirPilaGastos();
        }

    }//GEN-LAST:event_btnAgregarCategoriaActionPerformed

    private void cmbTipoCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbTipoCategoriaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoCategoriaMouseClicked

    private void cmbTipoCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoCategoriaActionPerformed

    private void txtNameCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameCategoriaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jpAgregarCategoria.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        jpAgregarCategoria.setVisible(true);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void btnSiguiente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguiente1ActionPerformed
        // TODO add your handling code here:
        if(!validatedCategories()){
            lblRellenar.setText("Rellene los campos de su "+cmbCategorias.getSelectedItem().toString());
            lblAgregar.setEnabled(false);
            tbpMenu.setSelectedIndex(2);
            btnConfirmar.setEnabled(true);
        }else{
            JOptionPane.showMessageDialog(this, "Primero seleccione una categoria.");
            btnConfirmar.setEnabled(false);
        }
    }//GEN-LAST:event_btnSiguiente1ActionPerformed

    private void btnCancelar12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar12ActionPerformed
        // TODO add your handling code here:
        tbpMenu.setSelectedIndex(0);
        lblAgregar.setEnabled(true);
    }//GEN-LAST:event_btnCancelar12ActionPerformed

    private void cmbCategories1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategories1ActionPerformed
        // TODO add your handling code here:
        cmbCategories.getLocation();
    }//GEN-LAST:event_cmbCategories1ActionPerformed

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtMontoActionPerformed

    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMontoKeyTyped

    private void cmbTipoCambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoCambioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoCambioActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        // TODO add your handling code here:
    try{
                
        if(txtFecha.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtMonto.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Rellene correctamente los campos.");
            return;
        }
        String fecha = txtFecha.getText();
        String categoriaIngreso = cmbCategories.getSelectedItem().toString();
        String categoriaGasto = cmbCategories1.getSelectedItem().toString();
        String detalles = txtDescripcion.getText();
        Double monto = Double.valueOf(txtMonto.getText());

        
        String moneda = cmbTipoCambio.getSelectedItem().toString();

        String mov = cmbCategorias.getSelectedItem().toString();

        List<DateTimeFormatter> formateadores = new ArrayList<>();
        formateadores.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        formateadores.add(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        formateadores.add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        formateadores.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        formateadores.add(DateTimeFormatter.ofPattern("dd-MM-yy"));
        formateadores.add(DateTimeFormatter.ofPattern("dd/MM/yy"));

        LocalDate fechaIngresada = null;
        for (DateTimeFormatter formatter : formateadores) {
            try {
                fechaIngresada = LocalDate.parse(fecha, formatter);
                break;
            } catch (DateTimeParseException e) {
            }
        }

        if (fechaIngresada != null) {
            if (mov.equals("Ingreso")){
                Operacion g = new Operacion(fechaIngresada, categoriaIngreso, detalles, monto, moneda, mov);
                gestor.agregarIngreso(categoriaIngreso, g);
                gestor.mostrarOperaciones(modelo);
                gestor.mostrarOperacionesI(modelo1);
                JOptionPane.showMessageDialog(this, "Se añadió un Ingreso a sus Movimientos.", "Inserción exitosa.", WIDTH);
            }else{
                Operacion g1 = new Operacion(fechaIngresada, categoriaGasto, detalles, monto, moneda, mov);
                gestor.agregarGasto(categoriaGasto, g1);
                gestor.mostrarOperaciones(modelo);
                gestor.mostrarOperacionesG(modelo2);
                JOptionPane.showMessageDialog(this, "Se añadió un Gasto sus Movimientos.", "Inserción exitosa.", WIDTH);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Por favor, ingrese la fecha en formato dd/MM/yyyy o yyyy-MM-dd.");
        }
        
        lblAgregar.setEnabled(true);
        limpiarCampos();
        
    }catch(NumberFormatException e){
        JOptionPane.showMessageDialog(null, "Ingrese un monto válido.");
    }

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        tbpMenu.setSelectedIndex(1);
        resetCmb();
        limpiarCampos();
        lblAgregar.setEnabled(true);
        
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
        tbpMenu.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel16MouseClicked

    private void lblSusGastosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusGastosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusGastosMouseEntered

    private void lblSusGastosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusGastosMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusGastosMouseExited

    private void lblSusIngresosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresosMouseEntered

    private void lblSusIngresosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresosMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresosMouseExited

    private void lblSusGastosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusGastosMouseClicked
        // TODO add your handling code here:
        tbpTablas.setSelectedIndex(2);
    }//GEN-LAST:event_lblSusGastosMouseClicked

    private void lblSusIngresosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresosMouseClicked
        // TODO add your handling code here:
        tbpTablas.setSelectedIndex(1);
    }//GEN-LAST:event_lblSusIngresosMouseClicked

    private void lblSusGastosIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusGastosIMouseClicked
        // TODO add your handling code here:
        tbpTablas.setSelectedIndex(2);
    }//GEN-LAST:event_lblSusGastosIMouseClicked

    private void lblSusGastosIMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusGastosIMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusGastosIMouseEntered

    private void lblSusGastosIMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusGastosIMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusGastosIMouseExited

    private void lblSusMovimientosIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusMovimientosIMouseClicked
        // TODO add your handling code here:
        tbpTablas.setSelectedIndex(0);
    }//GEN-LAST:event_lblSusMovimientosIMouseClicked

    private void lblSusMovimientosIMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusMovimientosIMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusMovimientosIMouseEntered

    private void lblSusMovimientosIMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusMovimientosIMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusMovimientosIMouseExited

    private void lblSusMovimientosGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusMovimientosGMouseClicked
        // TODO add your handling code here:
        tbpTablas.setSelectedIndex(0);
    }//GEN-LAST:event_lblSusMovimientosGMouseClicked

    private void lblSusMovimientosGMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusMovimientosGMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusMovimientosGMouseEntered

    private void lblSusMovimientosGMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusMovimientosGMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusMovimientosGMouseExited

    private void lblSusIngresosGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresosGMouseClicked
        // TODO add your handling code here:
        tbpTablas.setSelectedIndex(1);
    }//GEN-LAST:event_lblSusIngresosGMouseClicked

    private void lblSusIngresosGMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresosGMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresosGMouseEntered

    private void lblSusIngresosGMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresosGMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresosGMouseExited

    private void lblGenerarReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGenerarReporteMouseClicked
        // TODO add your handling code here:
        reporteGeneralDeUsuario=reporteGeneralDeUsuario+"---------- REPORTE DE USUARIO "+usuario.getNUsuario()+" ----------\n";
        //GASTOS
        if (gestor.getPilaGasto().empty()) {
            System.out.println("no generar reporte de gastos");
            reporteGeneralDeUsuario=reporteGeneralDeUsuario+"\n\nGastos: No hay gastos para el usuario "+usuario.getNUsuario();
        }
        else {
            //MAYOR GASTO
            Operacion mayorGastoOperacion = gestor.obtenerMayorGasto();
            String mayorGastoDeOperacion = "Categoria: "+ mayorGastoOperacion.getCategoria()+"\nMonto: "+mayorGastoOperacion.getMonto();
            //String mayorGastoDeOperacion=mayorGastoOperacion.toString();
            lblMayorGastoTexto.setText(mayorGastoDeOperacion);
            
            reporteGeneralDeUsuario=reporteGeneralDeUsuario+"\n\n--- Gastos para el usuario "+usuario.getNUsuario()+" ---\n\n"
                    +"** Gasto Mayor:\n"
                    +"Categoria: "+mayorGastoOperacion.getCategoria()+"\n"
                    +"Monto: "+mayorGastoOperacion.getMonto()+"\n"
                    +"Moneda: "+mayorGastoOperacion.getMoneda()+"\n"
                    +"Detalles: "+mayorGastoOperacion.getDetalles()+"\n\n";
            
            //MENOR GASTO
            Operacion menorGastoOperacion = gestor.obtenerMenorGasto();
            //String menorGastoDeOperacion=menorGastoOperacion.toString();
            String menorGastoDeOperacion = "Categoria: "+ menorGastoOperacion.getCategoria()+"\nMonto: "+menorGastoOperacion.getMonto();
            lblMenorGastoTexto.setText(menorGastoDeOperacion);
            
            reporteGeneralDeUsuario=reporteGeneralDeUsuario
                    +"** Gasto Menor:\n"
                    +"Categoria: "+menorGastoOperacion.getCategoria()+"\n"
                    +"Monto: "+menorGastoOperacion.getMonto()+"\n"
                    +"Moneda: "+menorGastoOperacion.getMoneda()+"\n"
                    +"Detalles: "+menorGastoOperacion.getDetalles()+"\n\n\n";
        }
        
        //INGRESOS
        if (gestor.getPilaIngreso().empty()) {
            System.out.println("no generar reporte de ingreso");
            reporteGeneralDeUsuario=reporteGeneralDeUsuario+"\n\nIngresos: No hay ingresos para el usuario "+usuario.getNUsuario();
        }
        else {
            //MAYOR INGRESO
            Operacion mayorIngresoOperacion = gestor.obtenerMayorIngreso();
            //String mayorIngresoDeOperacion=mayorIngresoOperacion.toString();
            String mayorIngresoDeOperacion = "Categoria: "+ mayorIngresoOperacion.getCategoria()+"\nMonto: "+mayorIngresoOperacion.getMonto();
            lblMayorIngresoTexto.setText(mayorIngresoDeOperacion);
            
            reporteGeneralDeUsuario=reporteGeneralDeUsuario+"\n\n--- Ingresos para el usuario "+usuario.getNUsuario()+" ---\n\n"
                    +"** Ingreso Mayor:\n"
                    +"Categoria: "+mayorIngresoOperacion.getCategoria()+"\n"
                    +"Monto: "+mayorIngresoOperacion.getMonto()+"\n"
                    +"Moneda: "+mayorIngresoOperacion.getMoneda()+"\n"
                    +"Detalles: "+mayorIngresoOperacion.getDetalles()+"\n\n";
            
            //MENOR INGRESO
            Operacion menorIngresoOperacion = gestor.obtenerMenorIngreso();
            String menorIngresoDeOperacion=menorIngresoOperacion.toString();
            //String menorIngresoDeOperacion = "Categoria: "+ menorIngresoDeOperacion.getCategoria()+"\nMonto: "+menorIngresoDeOperacion.getMonto();
            lblMenorIngresoTexto.setText(menorIngresoDeOperacion);
            
            reporteGeneralDeUsuario=reporteGeneralDeUsuario
                    +"** Ingreso Menor:\n"
                    +"Categoria: "+menorIngresoOperacion.getCategoria()+"\n"
                    +"Monto: "+menorIngresoOperacion.getMonto()+"\n"
                    +"Moneda: "+menorIngresoOperacion.getMoneda()+"\n"
                    +"Detalles: "+menorIngresoOperacion.getDetalles()+"\n\n\n";
        }
        
        //OPERACIONES (GENERAL)
        if (gestor.getPilaGeneral().empty()) {
            System.out.println("no generar reporte de operaciones");
            reporteGeneralDeUsuario=reporteGeneralDeUsuario+"\n\nOperaciones: No hay operaciones para el usuario "+usuario.getNUsuario();
        }
        else {
            //MAYOR OPERACION
            Operacion mayorOperacion = gestor.obtenerMayorOperacion();
            String mayorOperacionString=mayorOperacion.toString();
            lblMayorOperacionTexto.setText(mayorOperacionString);
            
            reporteGeneralDeUsuario=reporteGeneralDeUsuario+"\n\n--- Operaciones para el usuario "+usuario.getNUsuario()+" ---\n\n"
                    +"** Operacion Mayor:\n"
                    +"Tipo de Operacion: "+mayorOperacion.getMov()+"\n"
                    +"Categoria: "+mayorOperacion.getCategoria()+"\n"
                    +"Monto: "+mayorOperacion.getMonto()+"\n"
                    +"Moneda: "+mayorOperacion.getMoneda()+"\n"
                    +"Detalles: "+mayorOperacion.getDetalles()+"\n\n";
            
            //MENOR OPERACION
            Operacion menorOperacion = gestor.obtenerMenorOperacion();
            String menorOperacionString=menorOperacion.toString();
            lblMenorOperacionTexto.setText(menorOperacionString);
            
            reporteGeneralDeUsuario=reporteGeneralDeUsuario
                    +"** Operacion Menor:\n"
                    +"Tipo de Operacion: "+mayorOperacion.getMov()+"\n"
                    +"Categoria: "+menorOperacion.getCategoria()+"\n"
                    +"Monto: "+menorOperacion.getMonto()+"\n"
                    +"Moneda: "+menorOperacion.getMoneda()+"\n"
                    +"Detalles: "+menorOperacion.getDetalles()+"\n\n\n";
        }
        reporteGeneralDeUsuario=reporteGeneralDeUsuario+"\n\n----- FIN DEL REPORTE ----";
        lblExportarReporte.setVisible(true);
        txtRutaDeArchivo.setText("./reportes/"+"Reporte"+usuario.getNUsuario()+".txt");
    }//GEN-LAST:event_lblGenerarReporteMouseClicked

    private void lblConfigurarRutaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfigurarRutaMouseClicked
        // TODO add your handling code here:
        File carpetaSeleccionada;
        //Se crea un objeto JFileChooser para que se pueda seleccionar una carpeta
        JFileChooser seleccionarCarpeta = new JFileChooser();
        seleccionarCarpeta.setDialogTitle("Abrir carpeta"); //Se establece el título de la ventana
        seleccionarCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Se configura para seleccionar solo carpetas
        //Se abre la ventana
        seleccionarCarpeta.showOpenDialog(null);
        //Se guarda la ruta de la carpeta seleccionada
        carpetaSeleccionada = seleccionarCarpeta.getSelectedFile();

        JOptionPane.showMessageDialog(null,"La carpeta seleccionada es: " + carpetaSeleccionada);
        txtRutaDeArchivo.setText(carpetaSeleccionada + File.separator + "Reporte" + usuario.getNUsuario() + ".txt");
    }//GEN-LAST:event_lblConfigurarRutaMouseClicked

    private void lblGuardarArchivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGuardarArchivoMouseClicked
        // TODO add your handling code here:
        if(txtRutaDeArchivo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Elija una ruta");
        }
        else {
            String rutaCorrecta=txtRutaDeArchivo.getText();
            //DALUsuario.escribirReporte(reporteGeneralDeUsuario, "Reporte"+usuario.getNUsuario()+".txt");
            DALUsuario.escribirReporte(reporteGeneralDeUsuario, rutaCorrecta);
            JOptionPane.showMessageDialog(null, "Guardado de archivo exitoso.");
            tbpMenu.setSelectedIndex(5);
            lblExportarReporte.setVisible(false);
        }
    }//GEN-LAST:event_lblGuardarArchivoMouseClicked

    private void lblExportarReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExportarReporteMouseClicked
        // TODO add your handling code here:
        tbpMenu.setSelectedIndex(6);
    }//GEN-LAST:event_lblExportarReporteMouseClicked

    private void lblAtrasExportarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasExportarMouseClicked
        // TODO add your handling code here:
        tbpMenu.setSelectedIndex(5);
    }//GEN-LAST:event_lblAtrasExportarMouseClicked

    private void cmbOrdenMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbOrdenMovActionPerformed
        // TODO add your handling code here:

        if(cmbOrdenMov.getSelectedIndex()==0){
            gestor.mostrarOperaciones(modelo);
        }
        if(cmbOrdenMov.getSelectedIndex()==1){
            gestor.OrdenMovFecha(modelo);
        }
        if(cmbOrdenMov.getSelectedIndex()==2){
            gestor.OrdenMovCat(modelo);
        }
    }//GEN-LAST:event_cmbOrdenMovActionPerformed

    private void lblSusIngresos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos1MouseClicked

    private void lblSusIngresos1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos1MouseEntered

    private void lblSusIngresos1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos1MouseExited

    private void lblSusIngresos2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos2MouseClicked

    private void lblSusIngresos2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos2MouseEntered

    private void lblSusIngresos2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos2MouseExited

    private void cmbOrdenIngresosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbOrdenIngresosActionPerformed
        // TODO add your handling code here:
        if(cmbOrdenIngresos.getSelectedIndex()==0){
            gestor.mostrarOperacionesI(modelo1);
        }
        if(cmbOrdenIngresos.getSelectedIndex()==1){
            gestor.OrdenIngresoFecha(modelo1);
        }
        if(cmbOrdenIngresos.getSelectedIndex()==2){
            gestor.OrdenIngresoCat(modelo1);
        }
        if(cmbOrdenIngresos.getSelectedIndex()==3){
            gestor.OrdenIngresoMonto(modelo1);
        }
    }//GEN-LAST:event_cmbOrdenIngresosActionPerformed

    private void lblSusIngresos3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos3MouseClicked

    private void lblSusIngresos3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos3MouseEntered

    private void lblSusIngresos3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos3MouseExited

    private void cmbCategoriasIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoriasIngresoActionPerformed
        // TODO add your handling code here:
        if(!cmbCategoriasIngreso.getSelectedItem().toString().equals("Por Defecto")){
            String cat = cmbCategoriasIngreso.getSelectedItem().toString();
            gestor.mostrarIngresosCategoria(modelo1, cat);
        }else{
            gestor.mostrarOperacionesI(modelo1);
        }

    }//GEN-LAST:event_cmbCategoriasIngresoActionPerformed

    private void lblSusIngresos4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos4MouseClicked

    private void lblSusIngresos4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos4MouseEntered

    private void lblSusIngresos4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos4MouseExited

    private void cmbOrdenGastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbOrdenGastosActionPerformed
        // TODO add your handling code here:
        if(cmbOrdenGastos.getSelectedIndex()==0){
            gestor.mostrarOperacionesG(modelo2);
        }
        if(cmbOrdenGastos.getSelectedIndex()==1){
            gestor.OrdenGastoFecha(modelo2);
        }
        if(cmbOrdenGastos.getSelectedIndex()==2){
            gestor.OrdenGastoCat(modelo2);
        }
        if(cmbOrdenGastos.getSelectedIndex()==3){
            gestor.OrdenGastoMonto(modelo2);
        }
    }//GEN-LAST:event_cmbOrdenGastosActionPerformed

    private void lblSusIngresos5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos5MouseClicked

    private void lblSusIngresos5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos5MouseEntered

    private void lblSusIngresos5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSusIngresos5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSusIngresos5MouseExited

    private void cmbCategoriasGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoriasGastoActionPerformed
        // TODO add your handling code here:
        if(!cmbCategoriasGasto.getSelectedItem().toString().equals("Por Defecto")){
            String cat = cmbCategoriasGasto.getSelectedItem().toString();
            gestor.mostrarGastosCategoria(modelo2, cat);
        }else{
            gestor.mostrarOperacionesG(modelo2);
        }

    }//GEN-LAST:event_cmbCategoriasGastoActionPerformed

    private void btnGenerarGraficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarGraficoActionPerformed

        grafico.setValue("Ingresos",gestor.getIngresoTotal());
        grafico.setValue("Gastos",gestor.getGastoTotal());

        JFreeChart grafico_circular = ChartFactory.createPieChart(
            "Comparación Ingresos vs Gastos",
            grafico,
            true,
            true,
            false
        );
        
        ChartPanel panel = new ChartPanel(grafico_circular);
        panel.setMouseZoomable(true);
        panel.setPreferredSize(new Dimension(400,200));

        jpGraficosAbsolute.setLayout(new BorderLayout());
        jpGraficosAbsolute.add(panel,BorderLayout.NORTH);
        pack();
        repaint();
        this.setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_btnGenerarGraficoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCategoria;
    private javax.swing.JButton btnCancelar12;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnGenerarGrafico;
    private javax.swing.JButton btnSiguiente1;
    private javax.swing.JComboBox<String> cmbCategorias;
    private javax.swing.JComboBox<String> cmbCategoriasGasto;
    private javax.swing.JComboBox<String> cmbCategoriasIngreso;
    private javax.swing.JComboBox<String> cmbCategories;
    private javax.swing.JComboBox<String> cmbCategories1;
    private javax.swing.JComboBox<String> cmbOrdenGastos;
    private javax.swing.JComboBox<String> cmbOrdenIngresos;
    private javax.swing.JComboBox<String> cmbOrdenMov;
    private javax.swing.JComboBox<String> cmbTipoCambio;
    private javax.swing.JComboBox<String> cmbTipoCategoria;
    private javax.swing.JButton jButton2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPnBgDeTodo;
    private javax.swing.JPanel jPnUsuario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JPanel jpAgregarCategoria;
    private javax.swing.JPanel jpCategoria;
    private javax.swing.JPanel jpExportar;
    private javax.swing.JPanel jpGastos;
    private javax.swing.JPanel jpGraficos;
    private javax.swing.JPanel jpGraficosAbsolute;
    private javax.swing.JPanel jpImprimir;
    private javax.swing.JPanel jpIngresos;
    private javax.swing.JPanel jpInicio;
    private javax.swing.JPanel jpMontos;
    private javax.swing.JPanel jpMovimientos;
    private javax.swing.JPanel jpTablas;
    private javax.swing.JLabel lblAgregar;
    private javax.swing.JLabel lblAtrasExportar;
    private javax.swing.JLabel lblBalanceGeneralMenu;
    private javax.swing.JLabel lblBienvenidaPrincipal;
    private javax.swing.JLabel lblCambiarContraseña;
    private javax.swing.JLabel lblCambiarDeCuenta;
    private javax.swing.JLabel lblCambiarFoto;
    private javax.swing.JLabel lblCambiarNombre;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblConfiguracionPerfil;
    private javax.swing.JLabel lblConfigurarRuta;
    private javax.swing.JLabel lblEliminarPerfil;
    private javax.swing.JLabel lblExportarReporte;
    private javax.swing.JLabel lblGenerarReporte;
    private javax.swing.JLabel lblGrafico;
    private javax.swing.JLabel lblGuardarArchivo;
    private javax.swing.JLabel lblHamburguesa;
    private javax.swing.JLabel lblHola;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblImprimir;
    private javax.swing.JLabel lblMayorGastoTexto;
    private javax.swing.JLabel lblMayorIngresoTexto;
    private javax.swing.JLabel lblMayorOperacionTexto;
    private javax.swing.JLabel lblMejorarPlan;
    private javax.swing.JLabel lblMenorGastoTexto;
    private javax.swing.JLabel lblMenorIngresoTexto;
    private javax.swing.JLabel lblMenorOperacionTexto;
    private javax.swing.JLabel lblPrincipalEconomizer;
    private javax.swing.JLabel lblReiniciarPerfil;
    private javax.swing.JLabel lblRellenar;
    private javax.swing.JLabel lblRellenar2;
    private javax.swing.JLabel lblReportarBug;
    private javax.swing.JLabel lblSoporte;
    private javax.swing.JLabel lblSusGastos;
    private javax.swing.JLabel lblSusGastosG;
    private javax.swing.JLabel lblSusGastosI;
    private javax.swing.JLabel lblSusIngresos;
    private javax.swing.JLabel lblSusIngresos1;
    private javax.swing.JLabel lblSusIngresos2;
    private javax.swing.JLabel lblSusIngresos3;
    private javax.swing.JLabel lblSusIngresos4;
    private javax.swing.JLabel lblSusIngresos5;
    private javax.swing.JLabel lblSusIngresosG;
    private javax.swing.JLabel lblSusIngresosI;
    private javax.swing.JLabel lblSusMovimientosG;
    private javax.swing.JLabel lblSusMovimientosI;
    private javax.swing.JLabel lblTabla;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTabbedPane tbpMenu;
    private javax.swing.JTabbedPane tbpTablas;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNameCategoria;
    private javax.swing.JTextField txtRutaDeArchivo;
    // End of variables declaration//GEN-END:variables
}
