package gm.carlos.bolsos.controller;

import gm.carlos.bolsos.model.ProductoModel;
import gm.carlos.bolsos.view.View;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener, ListSelectionListener {

    private View vista;
    private ProductoModel model;

    public void agregarProducto(ProductoModel model, View vista) {
        this.model = model;
        this.vista = vista;

        registrarListener(this);
        vista.listarProductos.setVisible(true);
        vista.ayadirBolso.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object evt = e.getSource();
        funcionalidadBotones(evt);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    private void registrarListener(ActionListener e) {
        vista.ayadirBolsoButton.addActionListener(e);
        vista.listarProductosButton.addActionListener(e);
    }

    private void funcionalidadBotones(Object evt) {
        if (evt == vista.ayadirBolsoButton) {
            vista.listarProductos.setVisible(false);
            vista.ayadirBolso.setVisible(true);
        }
        else if (evt == vista.listarProductosButton) {
            vista.ayadirBolso.setVisible(false);
            vista.listarProductos.setVisible(true);
        }

        vista.panelCentral.revalidate();
        vista.panelCentral.repaint();
    }
}
