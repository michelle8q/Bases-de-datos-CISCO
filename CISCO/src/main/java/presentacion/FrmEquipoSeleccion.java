package presentacion;

import dto.ListarEquipoDTO;
import negocio.IEquipoNegocio;
import negocio.IUsoNegocio;
import entidad.AlumnoEntidad;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana gráfica principal encargada de mostrar el mapa visual de equipos
 * (computadoras) dentro de un laboratorio específico. Permite al alumno
 * visualizar qué equipos están disponibles u ocupados y seleccionar uno para
 * visualizar su software o proceder a reservarlo/usarlo.
 *
 * * @author luisf
 */
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

    private JScrollPane scrollPane;

    /**
     * Constructor de la ventana de selección de equipos.
     *
     * * @param equipoNegocio Dependencia para consultar la información y
     * estado de los equipos.
     * @param usoNegocio Dependencia para gestionar las reglas de uso o
     * apartados de los equipos.
     * @param alumnoActual La entidad del alumno que está utilizando el sistema
     * actualmente.
     * @param nombreLaboratorio El nombre del laboratorio a consultar (ej.
     * "Laboratorio A").
     */
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

    /**
     * Inicializa los componentes base de la ventana, como paneles, etiquetas de
     * título y el contenedor de desplazamiento (ScrollPane) para el grid de
     * equipos.
     */
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
        pnlFondo.add(pnlBotones, BorderLayout.SOUTH);

        setContentPane(pnlFondo);
        pack();
    }

    /**
     * Consulta la capa de negocio para obtener dinámicamente la lista de
     * equipos reales pertenecientes al laboratorio seleccionado, y los
     * renderiza en la interfaz.
     */
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

    /**
     * Crea y personaliza un botón que dibuja la forma gráfica de un monitor de
     * computadora, asignando un color verde o rojo dependiendo de su
     * disponibilidad.
     *
     * * @param numero El número identificador del equipo.
     * @param disponible Booleano que indica si el equipo está libre para ser
     * seleccionado.
     * @return El {@link JButton} dibujado de forma personalizada.
     */
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

    /**
     * Calcula y configura dinámicamente el layout del grid según la cantidad de
     * equipos cargados.
     *
     * * @param total La cantidad total de equipos recuperados de la base de
     * datos.
     */
    private void configurarGrid(int total) {
        int columnas = 4;
        int filas = (int) Math.ceil((double) total / columnas);
        pnlEquipos.setLayout(new GridLayout(filas, columnas, 20, 20));
        pnlEquipos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 200), 3),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
    }

    /**
     * Abre el formulario secundario que lista el software instalado en el
     * equipo seleccionado, ocultando la pantalla actual.
     *
     * * @param idEquipo El ID único en base de datos del equipo seleccionado.
     * @param numEquipo El número físico o de etiqueta del equipo.
     */
    private void abrirListaSoftwares(int idEquipo, int numEquipo) {
        FrmListaSoftwaresEquipo pantalla = new FrmListaSoftwaresEquipo(
                equipoNegocio,
                usoNegocio,
                alumnoActual,
                idEquipo,
                numEquipo);
        pantalla.setVisible(true);
        this.setVisible(false);
    }

    /**
     * Cierra la ventana actual y retorna a la vista anterior (si corresponde).
     */
    private void btnAtrasActionPerformed() {
        this.dispose();
    }

    /**
     * Muestra una alerta informativa indicando al usuario la acción requerida
     * en caso de intentar avanzar sin haber seleccionado un equipo válido.
     */
    private void btnSiguienteActionPerformed() {
        JOptionPane.showMessageDialog(this,
                "Seleccione un equipo verde para continuar.",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}
