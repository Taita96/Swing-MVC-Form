package gm.carlos.bolsos.view;

import gm.carlos.bolsos.utilities.Utilities;

import javax.swing.*;


public class View extends JFrame{

    public JPanel panelPrincipal;
    public JPanel panelSuperior;
    public JPanel panelDerecho;
    public JPanel panelCentral;
    public JPanel listarProductos;
    public JButton listarProductosButton;
    public JButton ayadirBolsoButton;
    public JButton ayadirBolsoViajeButton;
    public JButton ayadirMaletaButton;
    public JButton gestionarArchivoButton;
    public JTable table1;
    public JPanel ayadirBolso;
    public JLabel ayadirCampoSeleccionadoLbl;
    public JTextField precioTxt;
    public JComboBox comboBox1;
    public JCheckBox siCheckBox;
    public JCheckBox noCheckBox;
    public JButton limpiarButton1;
    public JButton guardarButton;


    public View() {
        setTitle("Tienda Bolsos");
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        asignarBorrarBorderBtn();
    }



    private void asignarBorrarBorderBtn(){
        Utilities.borrarBordeBoton(listarProductosButton);
        Utilities.borrarBordeBoton(ayadirBolsoButton);
        Utilities.borrarBordeBoton(ayadirBolsoViajeButton);
        Utilities.borrarBordeBoton(ayadirMaletaButton);
        Utilities.borrarBordeBoton(gestionarArchivoButton);
    }



}
