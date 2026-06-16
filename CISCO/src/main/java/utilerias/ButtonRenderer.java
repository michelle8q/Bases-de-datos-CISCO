/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Clase encargada de renderizar (dibujar) visualmente un botón dentro de una
 * celda de un {@link JTable}. A diferencia del Editor, el Renderer solo se
 * ocupa de la apariencia gráfica del botón cuando no se está interactuando con
 * él.
 *
 * @author luisf
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {

    /**
     * Constructor de la clase ButtonRenderer. Configura el botón para que sea
     * opaco y se pinte correctamente sobre el fondo de la tabla.
     */
    public ButtonRenderer() {
        setOpaque(true);
    }

    /**
     * Devuelve el componente (el propio botón) configurado visualmente para la
     * celda correspondiente. Asigna el texto que debe mostrar el botón basado
     * en el valor de la celda.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());

        return this;
    }
}
