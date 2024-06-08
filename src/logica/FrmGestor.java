package logica;

import entidades.Operacion;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmGestor extends javax.swing.JFrame {

    DefaultComboBoxModel<String> modeloIngreso = new DefaultComboBoxModel();
    DefaultComboBoxModel<String> modeloCategoria = new DefaultComboBoxModel();
    DefaultTableModel modelo = new DefaultTableModel();
    GestorGeneral gestor = new GestorGeneral();

    public FrmGestor() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAgregarGasto = new javax.swing.JButton();
        btnAgregarCategoriaGasto = new javax.swing.JButton();
        btnMostrarCategoriaGasto = new javax.swing.JButton();
        txtFecha = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtMonto = new javax.swing.JTextField();
        cbxMoneda = new javax.swing.JComboBox<>();
        cbxCategoria = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();
        btnMostrarCategoriaIngreso = new javax.swing.JButton();
        btnAgregarIngreso = new javax.swing.JButton();
        btnAgregarCategoriaIngreso = new javax.swing.JButton();
        btnMostarPorFecha = new javax.swing.JButton();
        cbxIngreso = new javax.swing.JComboBox<>();
        btnReset = new javax.swing.JButton();
        txtGastoGeneral = new javax.swing.JTextField();
        txtIngresoGeneral = new javax.swing.JTextField();
        txtSaldo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAgregarGasto.setText("Agregar Gasto");
        btnAgregarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarGastoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, 150, -1));

        btnAgregarCategoriaGasto.setText("Agregar Categoria");
        btnAgregarCategoriaGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCategoriaGastoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarCategoriaGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 150, -1));

        btnMostrarCategoriaGasto.setText("Mostrar por Categoria");
        btnMostrarCategoriaGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCategoriaGastoActionPerformed(evt);
            }
        });
        jPanel1.add(btnMostrarCategoriaGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 150, -1));

        txtFecha.setBorder(javax.swing.BorderFactory.createTitledBorder("FECHA"));
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 110, -1));

        txtDescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder("DESCRIPCION"));
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 330, -1));

        txtMonto.setBorder(javax.swing.BorderFactory.createTitledBorder("MONTO"));
        jPanel1.add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 110, -1));

        cbxMoneda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PEN", "USD", "EUR" }));
        cbxMoneda.setBorder(javax.swing.BorderFactory.createTitledBorder("MONEDA"));
        jPanel1.add(cbxMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 90, 40));

        cbxCategoria.setModel(modeloCategoria);
        cbxCategoria.setBorder(javax.swing.BorderFactory.createTitledBorder("CATEGORIA GASTOS"));
        cbxCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCategoriaActionPerformed(evt);
            }
        });
        jPanel1.add(cbxCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 150, 40));

        jTable1.setModel(modelo);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 660, 270));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 570, -1, -1));

        btnMostrarCategoriaIngreso.setText("Mostrar por Categoria");
        btnMostrarCategoriaIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarCategoriaIngresoActionPerformed(evt);
            }
        });
        jPanel1.add(btnMostrarCategoriaIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 150, -1));

        btnAgregarIngreso.setText("Agregar Ingreso");
        btnAgregarIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarIngresoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 150, -1));

        btnAgregarCategoriaIngreso.setText("Agregar Categoria");
        btnAgregarCategoriaIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCategoriaIngresoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarCategoriaIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 50, 150, -1));

        btnMostarPorFecha.setText("Mostrar por Fecha");
        btnMostarPorFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostarPorFechaActionPerformed(evt);
            }
        });
        jPanel1.add(btnMostarPorFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 190, -1));

        cbxIngreso.setModel(modeloIngreso);
        cbxIngreso.setBorder(javax.swing.BorderFactory.createTitledBorder("CATEGORIA INGRESO"));
        jPanel1.add(cbxIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 150, 40));

        btnReset.setText("RESET");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel1.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 160, -1, -1));

        txtGastoGeneral.setEditable(false);
        txtGastoGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("GASTOS"));
        jPanel1.add(txtGastoGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 130, 40));

        txtIngresoGeneral.setEditable(false);
        txtIngresoGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder("INGRESOS"));
        jPanel1.add(txtIngresoGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 510, 130, 40));

        txtSaldo.setEditable(false);
        txtSaldo.setBorder(javax.swing.BorderFactory.createTitledBorder("SALDO"));
        jPanel1.add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 510, 130, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actualizarValores(){
        txtGastoGeneral.setText(String.valueOf(gestor.obtenerGasto()));
        txtIngresoGeneral.setText(String.valueOf(gestor.obtenerIngreso()));
        txtSaldo.setText(String.valueOf(gestor.obtenerSaldo()));
    }
    private void cbxCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCategoriaActionPerformed

    private void btnAgregarCategoriaGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCategoriaGastoActionPerformed
        String categoria = JOptionPane.showInputDialog("Ingrese una categoria: ");
        gestor.agregarCategoriaGasto(categoria);
        modeloCategoria.addElement(categoria);
    }//GEN-LAST:event_btnAgregarCategoriaGastoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAgregarGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarGastoActionPerformed
        String fecha = txtFecha.getText();
        String categoria = cbxCategoria.getSelectedItem().toString();
        String detalles = txtDescripcion.getText();
        Double monto = Double.valueOf(txtMonto.getText());
        String moneda = cbxMoneda.getSelectedItem().toString();
        String mov = cbxMoneda.getSelectedItem().toString();
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
            // Si se pudo parsear la fecha con éxito
            Operacion g = new Operacion(fechaIngresada, categoria, detalles, monto, moneda, mov);
            gestor.agregarGasto(categoria, g);
            gestor.mostrarOperaciones(modelo);
        } else {
            // Si no se pudo parsear la fecha con ninguno de los formatos proporcionados
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Por favor, ingrese la fecha en formato dd/MM/yyyy o yyyy-MM-dd.");
        }

        actualizarValores();
    }//GEN-LAST:event_btnAgregarGastoActionPerformed

    private void btnMostrarCategoriaGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCategoriaGastoActionPerformed
        String categoria = JOptionPane.showInputDialog("Ingrese la categoria: ");
        gestor.mostrarGastosCategoria(modelo, categoria);
    }//GEN-LAST:event_btnMostrarCategoriaGastoActionPerformed

    private void btnAgregarCategoriaIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCategoriaIngresoActionPerformed
        String categoria = JOptionPane.showInputDialog("Ingrese una categoria: ");
        gestor.agregarCategoriaIngreso(categoria);
        modeloIngreso.addElement(categoria);
    }//GEN-LAST:event_btnAgregarCategoriaIngresoActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        gestor.mostrarOperaciones(modelo);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnAgregarIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarIngresoActionPerformed
        
        String fecha = txtFecha.getText();
        String categoria = cbxIngreso.getSelectedItem().toString();
        String detalles = txtDescripcion.getText();
        Double monto = Double.valueOf(txtMonto.getText());
        String moneda = cbxMoneda.getSelectedItem().toString();
        String mov = cbxMoneda.getSelectedItem().toString();
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
            // Si se pudo parsear la fecha con éxito
            Operacion i = new Operacion(fechaIngresada, categoria, detalles, monto, moneda, mov);
            gestor.agregarIngreso(categoria, i);
            gestor.mostrarOperaciones(modelo);
        } else {
            // Si no se pudo parsear la fecha con ninguno de los formatos proporcionados
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Por favor, ingrese la fecha en formato dd/MM/yyyy o yyyy-MM-dd.");
        }

        actualizarValores();
        
    }//GEN-LAST:event_btnAgregarIngresoActionPerformed

    private void btnMostrarCategoriaIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarCategoriaIngresoActionPerformed
        String categoria = JOptionPane.showInputDialog("Ingrese la categoria: ");
        gestor.mostrarIngresosCategoria(modelo, categoria);
    }//GEN-LAST:event_btnMostrarCategoriaIngresoActionPerformed

    private void btnMostarPorFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostarPorFechaActionPerformed
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String fecha = JOptionPane.showInputDialog("Ingrese una fecha: "); 
        try {
            LocalDate fechaIngresada = LocalDate.parse(fecha, formatter);
            gestor.mostrarOperacionFecha(modelo, fechaIngresada);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Formato incorrecto");
        }
    }//GEN-LAST:event_btnMostarPorFechaActionPerformed

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
            java.util.logging.Logger.getLogger(FrmGestor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGestor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGestor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGestor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmGestor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCategoriaGasto;
    private javax.swing.JButton btnAgregarCategoriaIngreso;
    private javax.swing.JButton btnAgregarGasto;
    private javax.swing.JButton btnAgregarIngreso;
    private javax.swing.JButton btnMostarPorFecha;
    private javax.swing.JButton btnMostrarCategoriaGasto;
    private javax.swing.JButton btnMostrarCategoriaIngreso;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cbxCategoria;
    private javax.swing.JComboBox<String> cbxIngreso;
    private javax.swing.JComboBox<String> cbxMoneda;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtGastoGeneral;
    private javax.swing.JTextField txtIngresoGeneral;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtSaldo;
    // End of variables declaration//GEN-END:variables
}
