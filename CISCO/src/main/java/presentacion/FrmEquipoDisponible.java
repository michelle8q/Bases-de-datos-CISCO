package presentacion;

import dto.EstadoEquipoDTO;
import negocio.IAlumnoNegocio;
import negocio.IUsoNegocio;
import negocio.NegocioException;

/**
 *
 * @author cinca piña
 */
public class FrmEquipoDisponible extends javax.swing.JFrame {

    private final IUsoNegocio usoNegocio;
    private final IAlumnoNegocio alumnoNegocio;
    private final String ipEquipo;
    private final EstadoEquipoDTO estadoEquipoDTO;

    public FrmEquipoDisponible(EstadoEquipoDTO dto, IUsoNegocio usoNegocio, IAlumnoNegocio alumnoNegocio, String ipEquipo) {
        initComponents();
        btnLiberarEquipo.setVisible(false);
        this.estadoEquipoDTO = dto;
        this.usoNegocio = usoNegocio;
        this.alumnoNegocio = alumnoNegocio;
        this.ipEquipo = ipEquipo;

        configurarPantalla(dto);
        this.setLocationRelativeTo(null);
    }

    private void configurarPantalla(EstadoEquipoDTO dto) {
        lblLaboratorio.setText(dto.getLaboratorio());
        lblNumEquipo.setText(String.valueOf(dto.getNumero()));

        if (dto.getEstado().equalsIgnoreCase("Disponible")) {
            lblEstado.setText("Computadora disponible");
            lblEstado.setForeground(new java.awt.Color(0, 153, 51));
            lblApartadoPor.setVisible(false);
            lblNombreAlum.setVisible(false);

            pnlLogin.setVisible(false);

        } else if (dto.getEstado().equalsIgnoreCase("Apartado")) {
            lblEstado.setText("Computadora apartada");
            lblEstado.setForeground(new java.awt.Color(204, 102, 0));
            lblApartadoPor.setVisible(true);
            lblNombreAlum.setVisible(true);
            lblNombreAlum.setText(dto.getAlumno().getNombreCompleto());

            pnlLogin.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblNumEquipo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNumEquipo1 = new javax.swing.JLabel();
        lblNumEquipo2 = new javax.swing.JLabel();
        lblLaboratorio = new javax.swing.JLabel();
        lblNumEquipo4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblApartadoPor = new javax.swing.JLabel();
        lblNombreAlum = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        pnlContenedorLogin = new javax.swing.JPanel();
        pnlLogin = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnIngresar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        JPassContrasena = new javax.swing.JPasswordField();
        btnLiberarEquipo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(227, 224, 218));

        jPanel2.setBackground(new java.awt.Color(209, 209, 209));
        jPanel2.setForeground(new java.awt.Color(220, 220, 220));

        lblNumEquipo.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        lblNumEquipo.setText("Numero Equipo");

        jLabel2.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel2.setText("Laboratorio:");

        lblNumEquipo1.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        lblNumEquipo1.setText("Equipo ");

        lblNumEquipo2.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        lblNumEquipo2.setText("-");

        lblLaboratorio.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        lblLaboratorio.setText("Ubicacion de equipo");

        lblNumEquipo4.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        lblNumEquipo4.setText("-");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblNumEquipo1)
                        .addGap(24, 24, 24)
                        .addComponent(lblNumEquipo2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNumEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNumEquipo4, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblLaboratorio)
                    .addComponent(lblNumEquipo4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumEquipo1)
                    .addComponent(lblNumEquipo)
                    .addComponent(lblNumEquipo2))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(209, 209, 209));
        jPanel3.setForeground(new java.awt.Color(220, 220, 220));

        lblApartadoPor.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        lblApartadoPor.setText("Apartado por:");

        lblNombreAlum.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        lblNombreAlum.setText("Nombre del alumno");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblApartadoPor)
                    .addComponent(lblNombreAlum))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblApartadoPor)
                .addGap(26, 26, 26)
                .addComponent(lblNombreAlum)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        lblEstado.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        lblEstado.setText("Estado del equipo");

        pnlContenedorLogin.setBackground(new java.awt.Color(227, 224, 218));

        javax.swing.GroupLayout pnlContenedorLoginLayout = new javax.swing.GroupLayout(pnlContenedorLogin);
        pnlContenedorLogin.setLayout(pnlContenedorLoginLayout);
        pnlContenedorLoginLayout.setHorizontalGroup(
            pnlContenedorLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlContenedorLoginLayout.setVerticalGroup(
            pnlContenedorLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 223, Short.MAX_VALUE)
        );

        pnlLogin.setBackground(new java.awt.Color(227, 224, 218));

        jLabel5.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel5.setText("Ingresar contraseña: ");

        btnCancelar.setBackground(new java.awt.Color(204, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar apartado");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnIngresar.setBackground(new java.awt.Color(0, 153, 0));
        btnIngresar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel4.setText("Verificando disponibilidad en:");

        btnLiberarEquipo.setBackground(new java.awt.Color(102, 204, 255));
        btnLiberarEquipo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLiberarEquipo.setText("liberar equipo");
        btnLiberarEquipo.setActionCommand("");
        btnLiberarEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiberarEquipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLayout.createSequentialGroup()
                .addContainerGap(440, Short.MAX_VALUE)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(171, 171, 171))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLayout.createSequentialGroup()
                        .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnlLoginLayout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLiberarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnIngresar))
                            .addGroup(pnlLoginLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(39, 39, 39)
                                .addComponent(JPassContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(459, 459, 459))))
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoginLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(JPassContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLayout.createSequentialGroup()
                        .addContainerGap(119, Short.MAX_VALUE)
                        .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnIngresar)
                            .addComponent(btnLiberarEquipo))
                        .addGap(53, 53, 53)))
                .addComponent(jLabel4)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContenedorLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblEstado)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(pnlContenedorLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 13, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        char[] passwordArray = JPassContrasena.getPassword();
        String contrasena = new String(passwordArray).trim();

        if (contrasena.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Por favor, ingrese la contraseña para validar su identidad.",
                    "Campo Requerido",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (this.estadoEquipoDTO == null || this.estadoEquipoDTO.getAlumno() == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: No hay ningún alumno asignado a este apartado actualmente.",
                    "Error de Contexto",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int idAlumno = this.estadoEquipoDTO.getAlumno().getId();

            boolean esValida = alumnoNegocio.verificarCredencialesAlumno(idAlumno, contrasena);

            if (esValida) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "¡Contraseña correcta! Bienvenido al sistema.",
                        "Acceso Concedido",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);

                btnLiberarEquipo.setVisible(true);
                btnCancelar.setVisible(false);
                btnIngresar.setVisible(false);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "La contraseña ingresada es incorrecta. Inténtelo de nuevo.",
                        "Acceso Denegado",
                        javax.swing.JOptionPane.ERROR_MESSAGE);

                JPassContrasena.setText("");
                JPassContrasena.requestFocus();
            }

        } catch (NegocioException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error en la verificación: " + e.getMessage(),
                    "Error del Sistema",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea cancelar el apartado de este equipo?",
                "Confirmar Cancelación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
            try {
                usoNegocio.cancelarApartadoEquipo(this.ipEquipo);

                javax.swing.JOptionPane.showMessageDialog(this, "El apartado ha sido cancelado con éxito.");

                lblEstado.setText("Computadora disponible");
                lblEstado.setForeground(new java.awt.Color(0, 153, 51));

                lblApartadoPor.setVisible(false);
                lblNombreAlum.setVisible(false);
                pnlLogin.setVisible(false);

                JPassContrasena.setText("");

            } catch (NegocioException e) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Error al cancelar apartado: " + e.getMessage(),
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnLiberarEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarEquipoActionPerformed
        if (this.estadoEquipoDTO == null || this.estadoEquipoDTO.getAlumno() == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error: No hay un apartado activo para liberar.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            this.usoNegocio.cancelarApartadoEquipo(this.ipEquipo);

            javax.swing.JOptionPane.showMessageDialog(this,
                    "El equipo se ha liberado correctamente y vuelve a estar disponible.",
                    "Equipo Liberado",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);

            lblEstado.setText("Computadora disponible");
            lblEstado.setForeground(new java.awt.Color(0, 153, 51)); // Cambia el texto a color verde

            lblApartadoPor.setVisible(false);
            lblNombreAlum.setVisible(false);
            pnlLogin.setVisible(false); 

        } catch (NegocioException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error al liberar el equipo en la base de datos: " + e.getMessage(),
                    "Error de Capas",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnLiberarEquipoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField JPassContrasena;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnLiberarEquipo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblApartadoPor;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblLaboratorio;
    private javax.swing.JLabel lblNombreAlum;
    private javax.swing.JLabel lblNumEquipo;
    private javax.swing.JLabel lblNumEquipo1;
    private javax.swing.JLabel lblNumEquipo2;
    private javax.swing.JLabel lblNumEquipo4;
    private javax.swing.JPanel pnlContenedorLogin;
    private javax.swing.JPanel pnlLogin;
    // End of variables declaration//GEN-END:variables
}
