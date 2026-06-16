/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 * Clase que permite incrustar y gestionar el comportamiento de un botón
 * interactivo dentro de una celda de un {@link JTable}. Funciona como el editor
 * de la celda, interceptando los clics del usuario y ejecutando una acción
 * específica (un objeto {@link Runnable}) cuando el botón es presionado.
 *
 * @author luisf
 */
public class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private Runnable accion;

    private JTable tabla;
    private int filaActiva;

    /**
     * Constructor de la clase ButtonEditor.
     *
     * * @param checkBox Un JCheckBox requerido por el constructor padre
     * {@link DefaultCellEditor}.
     * @param accion El bloque de código (interfaz Runnable) que se ejecutará al
     * hacer clic en el botón.
     */
    public ButtonEditor(JCheckBox checkBox, Runnable accion) {
        super(checkBox);
        this.accion = accion;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    /**
     * Configura y devuelve el componente (el botón) que actuará como editor en
     * la celda. Captura la tabla y la fila actual para poder utilizarlas al
     * momento de hacer clic.
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {

        this.tabla = table;
        this.filaActiva = row;

        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    /**
     * Devuelve el valor final de la celda tras la edición. Es en este punto
     * donde se detecta si el botón fue presionado, guardando la fila
     * seleccionada en las propiedades de la tabla y ejecutando la acción.
     */
    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            if (this.tabla != null) {
                this.tabla.putClientProperty("filaSeleccionada", this.filaActiva);
            }

            if (accion != null) {
                accion.run();
            }
        }
        isPushed = false;
        return label;
    }

    /**
     * Detiene la edición de la celda y resetea el estado de pulsación del
     * botón.
     */
    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
