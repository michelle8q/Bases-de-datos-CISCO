package presentacion;
 
import javax.swing.*;
import java.awt.*;
 

public class DlgApartadoConfirmacion extends JDialog {
 
    public enum Resultado { OK, CANCELAR }
 
    private Resultado resultado = Resultado.CANCELAR;
 
    private static final Color COLOR_FONDO  = new Color(220, 220, 220);
    private static final Color COLOR_TITULO = new Color(20,  20,  20);
    private static final Color COLOR_TEXTO  = new Color(50,  50,  50);
 
    public DlgApartadoConfirmacion(Frame owner, int numeroEquipo) {
        super(owner, true); // modal
        setTitle("Apartado confirmado");
        initComponents(numeroEquipo);
        setLocationRelativeTo(owner);
    }
 
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
        pnlBotones.add(btnOk,       BorderLayout.EAST);
        panel.add(pnlBotones, BorderLayout.SOUTH);
 
        setContentPane(panel);
    }
 
    public Resultado mostrar() {
        setVisible(true);
        return resultado;
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DlgApartadoConfirmacion dlg = new DlgApartadoConfirmacion(null, 2);
            DlgApartadoConfirmacion.Resultado r = dlg.mostrar();
            System.out.println("Resultado: " + r);
        });
    }
}
 