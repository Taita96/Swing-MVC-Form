package gm.carlos.bolsos.utilities;

import javax.swing.*;
import java.awt.*;

public class Utilities {

    public static Image renderizarIconos(String ruta){
        ImageIcon icono = new ImageIcon(Utilities.class.getResource(ruta));
        System.out.println((int) icono.getIconWidth() +""+ (int) icono.getIconHeight());
        Image img = icono.getImage();
        return img.getScaledInstance(icono.getIconWidth(),icono.getIconHeight(),Image.SCALE_AREA_AVERAGING);
    }

    public static void borrarBordeBoton(JButton btn){
        btn.setBorder(BorderFactory.createEmptyBorder());

    }
}
