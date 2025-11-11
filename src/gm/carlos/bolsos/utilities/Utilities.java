package gm.carlos.bolsos.utilities;

import gm.carlos.bolsos.view.View;

import javax.swing.*;
import java.awt.*;

public class Utilities {

    public static void borrarBordeBoton(JButton btn){
        btn.setBorder(BorderFactory.createEmptyBorder());
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

    public static void configurarComponente(JComponent[] component,boolean switche){
        for(JComponent c: component){
            if(c instanceof JRadioButton){
                c.setEnabled(switche);
            }else if(c instanceof JComboBox){
                c.setEnabled(switche);
            }else if(c instanceof JComboBox){
                c.setEnabled(switche);
            }
        }
    }

    public static void mostrarPantalla(View vista,String nombrePantalla){
        CardLayout cl = (CardLayout) vista.panelCentral.getLayout();
        cl.show(vista.panelCentral, nombrePantalla);
    }

}
