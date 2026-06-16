package presentacion;

import javax.swing.*;
import java.awt.*;

/**
 * Cuadro de diálogo modal personalizado para confirmar el apartado de un equipo
 * de cómputo. Muestra un mensaje indicando que el equipo ha sido apartado y
 * solicita al usuario que recuerde utilizar su contraseña para desbloquearlo
 * posteriormente.
 *
 * * @author luisf
 */
public class DlgApartadoConfirmacion extends JDialog {

    /**
     * Enumeración que define los posibles resultados de la interacción del
     * usuario con el cuadro de diálogo.
     */
    public enum Resultado {
        OK, CANCELAR
    }

    private Resultado resultado = Resultado.CANCELAR;

    private static final Color COLOR_FONDO = new Color(220, 220, 220);
    private static final Color COLOR_TITULO = new Color(20, 20, 20);
    private static final Color COLOR_TEXTO = new Color(50, 50, 50);

    /**
     * Constructor del cuadro de diálogo de confirmación.
     *
     * * @param owner El frame padre sobre el cual este diálogo será modal.
     * @param numeroEquipo El número de identificación del equipo que se está
     * apartando.
     */
    public DlgApartadoConfirmacion(Frame owner, int numeroEquipo) {
        super(owner, true); // modal
        setTitle("Apartado confirmado");
        initComponents(numeroEquipo);
        setLocationRelativeTo(owner);
    }

    /**
     * Inicializa, configura y posiciona los componentes gráficos dentro del
     * diálogo.
     *
     * * @param numeroEquipo El número del equipo para mostrarlo en el título
     * del mensaje.
     */
    private void initComponents(int numeroEquipo) {
        setResizable(false);
        setSize(420, 230);

        JPanel panel = new JPanel();
        panel.setBackground(COLOR_FONDO);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Equipo " + numeroEquipo + " apartado", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Corbel", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_TITULO);
        panel.add(lblTitulo, BorderLayout.NORTH);

        JLabel lblMensaje = new JLabel(
                "<html><div style='text-align:center;'>"
                + "Utiliza tu contraseña para<br>desbloquear el equipo apartado"
                + "</div></html>",
                SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Corbel", Font.PLAIN, 16));
        lblMensaje.setForeground(COLOR_TEXTO);
        panel.add(lblMensaje, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel(new BorderLayout());
        pnlBotones.setBackground(COLOR_FONDO);

        JButton btnCancelar = new JButton("Cancelar apartado");
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCancelar.setBackground(new Color(220, 220, 220));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> {
            resultado = Resultado.CANCELAR;
            dispose();
        });

        JButton btnOk = new JButton("OK");
        btnOk.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnOk.setBackground(new Color(220, 220, 220));
        btnOk.setPreferredSize(new Dimension(80, 30));
        btnOk.setFocusPainted(false);
        btnOk.addActionListener(e -> {
            resultado = Resultado.OK;
            dispose();
        });

        pnlBotones.add(btnCancelar, BorderLayout.WEST);
        pnlBotones.add(btnOk, BorderLayout.EAST);
        panel.add(pnlBotones, BorderLayout.SOUTH);

        setContentPane(panel);
    }

    /**
     * Hace visible el cuadro de diálogo y bloquea el hilo de ejecución hasta
     * que el usuario seleccione una opción (OK o Cancelar).
     *
     * * @return El estado final del {@link Resultado} seleccionado por el
     * usuario.
     */
    public Resultado mostrar() {
        setVisible(true);
        return resultado;
    }
}
