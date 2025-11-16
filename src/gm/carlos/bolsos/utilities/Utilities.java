package gm.carlos.bolsos.utilities;

import gm.carlos.bolsos.view.View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Clase con métodos auxiliares utilizados en la aplicación.
 * Incluye funciones para mostrar mensajes, configurar componentes,
 * crear selectores de archivos y gestionar paneles con CardLayout.
 */

public class Utilities {

    /**
     * Quita el borde del botón y cambia el cursor al pasar sobre él.
     * @param btn botón al que se aplica el estilo
     */
    public static void borrarBordeBoton(JButton btn) {
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Muestra un mensaje usando JOptionPane.
     * @param mensaje texto del mensaje
     * @param titulo título de la ventana
     * @param tipo tipo de mensaje (INFORMATION, WARNING, ERROR, QUESTION)
     */
    public static void mostrarMensaje(String mensaje, String titulo, String tipo) {
        int typeJOption = 0;

        switch (tipo) {
            case "INFORMATION":
                typeJOption = JOptionPane.INFORMATION_MESSAGE;
                break;
            case "WARNING":
                typeJOption = JOptionPane.WARNING_MESSAGE;
                break;
            case "ERROR":
                typeJOption = JOptionPane.ERROR_MESSAGE;
                break;
            case "QUESTION":
                typeJOption = JOptionPane.QUESTION_MESSAGE;
                break;
        }

        JOptionPane.showMessageDialog(null, mensaje, titulo, typeJOption);
    }

    /**
     * Muestra un cuadro de confirmación con opciones Sí/No.
     * @param mensaje texto del cuadro
     * @param titulo título del cuadro
     * @return 0 si el usuario elige Sí, 1 si elige No
     */
    public static int mensajeConfirmacion(String mensaje,String titulo) {
        return JOptionPane.showConfirmDialog(null,mensaje
                ,titulo,JOptionPane.YES_NO_OPTION);
    }

    /**
     * Activa o desactiva varios componentes del formulario.
     * Solo afecta JRadioButton y JComboBox.
     * @param component lista de componentes
     * @param switche true para habilitar, false para deshabilitar
     */
    public static void configurarComponente(JComponent[] component, boolean switche) {
        for (JComponent c : component) {
            if (c instanceof JRadioButton) {
                c.setEnabled(switche);
            } else if (c instanceof JComboBox) {
                c.setEnabled(switche);
            }
        }
    }

    /**
     * Crea un selector de archivos con ruta por defecto y filtro de extensión.
     * @param rutaDefecto carpeta inicial
     * @param tipoArchivos descripción del tipo de archivo
     * @param extension extensión permitida (por ejemplo: "json")
     * @return JFileChooser configurado
     */
    public static JFileChooser crearSelectorFichero(File rutaDefecto,
                                                    String tipoArchivos,
                                                    String extension) {
        JFileChooser selectorFichero = new JFileChooser();
        if (rutaDefecto != null) {
            selectorFichero.setCurrentDirectory(rutaDefecto);
        }
        if (extension != null) {
            FileNameExtensionFilter filtro = new FileNameExtensionFilter(tipoArchivos
                    , extension);
            selectorFichero.setFileFilter(filtro);
        }
        return selectorFichero;
    }

    /**
     * Muestra un panel específico dentro de un contenedor con CardLayout.
     * @param nombrePanel panel principal con CardLayout
     * @param nombreTipoPanel nombre de la tarjeta a mostrar
     */
    public static void verPatanllas(JPanel nombrePanel,String nombreTipoPanel){
        CardLayout cl = (CardLayout) nombrePanel.getLayout();
        cl.show(nombrePanel, nombreTipoPanel);
    }


}
