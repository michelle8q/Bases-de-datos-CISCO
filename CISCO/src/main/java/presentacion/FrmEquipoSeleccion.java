package presentacion;

import dto.ListarEquipoDTO;
import negocio.IEquipoNegocio;
import negocio.IUsoNegocio;
import entidad.AlumnoEntidad;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FrmEquipoSeleccion extends javax.swing.JFrame {

    private IEquipoNegocio equipoNegocio;
    private IUsoNegocio usoNegocio;
    private AlumnoEntidad alumnoActual;
    private String nombreLaboratorio;

    private static final Color COLOR_DISPONIBLE = new Color(0, 153, 51);
    private static final Color COLOR_OCUPADO = new Color(204, 0, 0);
    private static final Color COLOR_HOVER = new Color(0, 180, 60);
    private static final Color COLOR_TEXTO = Color.WHITE;
    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final Color COLOR_PANEL_EQUIPOS = new Color(220, 220, 220);

    private JPanel pnlFondo;
    private JPanel pnlEquipos;
    private JLabel lblTitulo;
    private JButton btnAtras;
    private JButton btnSiguiente;
    private JScrollPane scrollPane;

    public FrmEquipoSeleccion(IEquipoNegocio equipoNegocio,
            IUsoNegocio usoNegocio,
            AlumnoEntidad alumnoActual,
            String nombreLaboratorio) {
        this.equipoNegocio = equipoNegocio;
        this.usoNegocio = usoNegocio;
        this.alumnoActual = alumnoActual;
        this.nombreLaboratorio = nombreLaboratorio;
        initComponents();
        cargarEquipos();
    }

    public FrmEquipoSeleccion() {
        initComponents();
        cargarEquiposDemo();
    }

    private void initComponents() {
        setTitle("Selección de Equipo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);

        pnlFondo = new JPanel(new BorderLayout(10, 10));
        pnlFondo.setBackground(COLOR_FONDO);
        pnlFondo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTitulo = new JLabel("Seleccione un equipo disponible");
        lblTitulo.setFont(new Font("Corbel", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(30, 30, 30));
        pnlFondo.add(lblTitulo, BorderLayout.NORTH);

        pnlEquipos = new JPanel();
        pnlEquipos.setBackground(COLOR_PANEL_EQUIPOS);
        pnlEquipos.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 200), 3));

        scrollPane = new JScrollPane(pnlEquipos);
        scrollPane.setBorder(null);
        pnlFondo.add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel(new BorderLayout());
        pnlBotones.setBackground(COLOR_FONDO);

        btnAtras = new JButton("Atrás");
        btnAtras.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnAtras.setPreferredSize(new Dimension(100, 32));
        btnAtras.addActionListener(e -> btnAtrasActionPerformed());

        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSiguiente.setPreferredSize(new Dimension(110, 32));
        btnSiguiente.addActionListener(e -> btnSiguienteActionPerformed());

        pnlBotones.add(btnAtras, BorderLayout.WEST);
        pnlBotones.add(btnSiguiente, BorderLayout.EAST);
        pnlFondo.add(pnlBotones, BorderLayout.SOUTH);

        setContentPane(pnlFondo);
        pack();
    }

    private void cargarEquipos() {
        pnlEquipos.removeAll();

        try {

            List<ListarEquipoDTO> equipos = equipoNegocio
                    .buscarEquiposPaginados(nombreLaboratorio, "", 50, 1);

            configurarGrid(equipos.size());

            for (ListarEquipoDTO equipo : equipos) {
                if (equipo.getNumeroComputadora() <= 0) {
                    continue;
                }

                boolean disponible = "Disponible".equalsIgnoreCase(equipo.getEstado());
                JButton btn = crearBotonEquipo(equipo.getNumeroComputadora(), disponible);

                if (disponible) {
                    final int idEquipo = equipo.getId();
                    final int numEquipo = equipo.getNumeroComputadora();
                    btn.addActionListener(e -> abrirListaSoftwares(idEquipo, numEquipo));
                }

                pnlEquipos.add(btn);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar equipos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        pnlEquipos.revalidate();
        pnlEquipos.repaint();
    }

    private void cargarEquiposDemo() {
        pnlEquipos.removeAll();
        configurarGrid(10);

        for (int i = 1; i <= 10; i++) {
            boolean disponible = (i != 1 && i != 7);
            JButton btn = crearBotonEquipo(i, disponible);

            if (disponible) {
                final int num = i;
                btn.addActionListener(e -> abrirListaSoftwares(num, num));
            }
            pnlEquipos.add(btn);
        }

        pnlEquipos.revalidate();
        pnlEquipos.repaint();
    }

    private JButton crearBotonEquipo(int numero, boolean disponible) {
        JButton btn = new JButton(String.valueOf(numero)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                Color base = disponible ? COLOR_DISPONIBLE : COLOR_OCUPADO;

                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                int pw = (int) (getWidth() * 0.75);
                int ph = (int) (getHeight() * 0.55);
                int px = (getWidth() - pw) / 2;
                int py = (int) (getHeight() * 0.08);

                g2.setColor(base);
                g2.setStroke(new BasicStroke(5));
                g2.drawRoundRect(px, py, pw, ph, 12, 12);
                g2.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), 40));
                g2.fillRoundRect(px, py, pw, ph, 12, 12);

                int neckX = getWidth() / 2 - 4;
                int neckY = py + ph;
                g2.setColor(base);
                g2.setStroke(new BasicStroke(6));
                g2.drawLine(neckX + 4, neckY, neckX + 4, neckY + (int) (getHeight() * 0.18));

                int baseW = (int) (getWidth() * 0.45);
                int baseX = (getWidth() - baseW) / 2;
                int baseY = neckY + (int) (getHeight() * 0.17);
                g2.setStroke(new BasicStroke(5));
                g2.drawLine(baseX, baseY, baseX + baseW, baseY);

                g2.setColor(base);
                g2.setFont(new Font("Corbel", Font.BOLD, (int) (getHeight() * 0.32)));
                FontMetrics fm = g2.getFontMetrics();
                String txt = String.valueOf(numero);
                int tx = px + (pw - fm.stringWidth(txt)) / 2;
                int ty = py + (ph + fm.getAscent() - fm.getDescent()) / 2 - 2;
                g2.drawString(txt, tx, ty);

                g2.dispose();
            }
        };

        btn.setPreferredSize(new Dimension(150, 140));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(disponible
                ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                : Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        btn.setEnabled(disponible);

        if (disponible) {
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    btn.repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    btn.repaint();
                }
            });
        }

        return btn;
    }

    private void configurarGrid(int total) {
        int columnas = 4;
        int filas = (int) Math.ceil((double) total / columnas);
        pnlEquipos.setLayout(new GridLayout(filas, columnas, 20, 20));
        pnlEquipos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 200), 3),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
    }

    private void abrirListaSoftwares(int idEquipo, int numEquipo) {
        FrmListaSoftwaresEquipo pantalla = new FrmListaSoftwaresEquipo(
                equipoNegocio, idEquipo, numEquipo);
        pantalla.setVisible(true);
        this.setVisible(false);
    }

    private void btnAtrasActionPerformed() {
        this.dispose();
    }

    private void btnSiguienteActionPerformed() {
        JOptionPane.showMessageDialog(this,
                "Seleccione un equipo verde para continuar.",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

}
