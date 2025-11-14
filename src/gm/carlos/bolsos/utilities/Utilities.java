package gm.carlos.bolsos.utilities;

import gm.carlos.bolsos.view.View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class Utilities {

    public static void borrarBordeBoton(JButton btn) {
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void showMessage(String message, String title, String type) {
        int typeJOption = 0;

        switch (type) {
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

        JOptionPane.showMessageDialog(null, message, title, typeJOption);
    }

    public static int mensajeConfirmacion(String mensaje,String titulo) {
        return JOptionPane.showConfirmDialog(null,mensaje
                ,titulo,JOptionPane.YES_NO_OPTION);
    }

    public static void configurarComponente(JComponent[] component, boolean switche) {
        for (JComponent c : component) {
            if (c instanceof JRadioButton) {
                c.setEnabled(switche);
            } else if (c instanceof JComboBox) {
                c.setEnabled(switche);
            }
        }
    }

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

    public static void verPatanllas(JPanel nombrePanel,String nombreTipoPanel){
        CardLayout cl = (CardLayout) nombrePanel.getLayout();
        cl.show(nombrePanel, nombreTipoPanel);
    }


}
