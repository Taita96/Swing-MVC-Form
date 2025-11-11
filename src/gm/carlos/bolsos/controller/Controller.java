package gm.carlos.bolsos.controller;

import gm.carlos.bolsos.model.ProductoModel;
import gm.carlos.bolsos.model.base.Bolso;
import gm.carlos.bolsos.model.base.BolsoViaje;
import gm.carlos.bolsos.model.base.Maleta;
import gm.carlos.bolsos.model.base.Producto;
import gm.carlos.bolsos.model.enums.*;
import gm.carlos.bolsos.utilities.Utilities;
import gm.carlos.bolsos.view.View;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Controller implements ActionListener, ListSelectionListener {

    private View vista;
    private ProductoModel model;

    public Controller(ProductoModel model, View vista) {
        this.model = model;
        this.vista = vista;

        registrarListener(this);

        vista.panelListarProductos.setVisible(true);
        vista.panelAnadirProducto.setVisible(false);
        vista.panelArchivos.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();
        funcionalidadBotones(evt);
    }

    private void registrarListener(ActionListener e) {

        vista.btnAnadirProductos.addActionListener(e);
        vista.btnListarProductos.addActionListener(e);
        vista.btnGestionarArchivos.addActionListener(e);
        vista.btnGuardar.addActionListener(e);

        vista.comboboxTipoProducto.setActionCommand("TipoProductoSeleccionado");
        vista.comboboxTipoProducto.addActionListener(e);
    }

    private void funcionalidadBotones(String evt) {

        switch (evt) {
            case "Listar Productos":
                verListaProductos();
                break;
            case "Añadir Producto":
                verAnadirProducto();
                break;
            case "Gestionar Archivos":
                verGestionArchivos();
                break;
            case "Guardar":
                guardarArchivo();
                break;
            case "TipoProductoSeleccionado":
                habilitarComponentes();
                break;
        }


        vista.panelCentral.revalidate();
        vista.panelCentral.repaint();
    }

    private void guardarArchivo() {

        TipoProducto tipoProdcuto = (TipoProducto) vista.comboboxTipoProducto.getSelectedItem();

        if(vista.comboboxTipoProducto.getSelectedIndex() == -1){
            Utilities.showMessage("Seleccione un tipo de producto", "Error", "ERROR");
            vista.comboboxTipoProducto.requestFocus();
            return;
        }

        Material material = (Material) vista.comboboxMaterial.getSelectedItem();
        Marca marca = (Marca) vista.comboboxMarca.getSelectedItem();

        LocalDate fecha = vista.dpFechaCompra.getDate();
        Object spinnerPrecio = vista.spnPrecio.getValue();
        String stringTamano = vista.txtTamano.getText();
        int peso = vista.sliderPeso.getValue();

        boolean impeableSiSeleccionado = vista.rbSiImpermeable.isSelected();
        boolean impeableN0Seleccionado = vista.rbNoImpermeable.isSelected();

        ComprobacionDeCamposGenerales(tipoProdcuto, material, marca, fecha, spinnerPrecio, stringTamano, peso, impeableSiSeleccionado, impeableN0Seleccionado);

        boolean isImpermable = impeableSiSeleccionado ? true : false;

        double tamano = 0;

        if (!stringTamano.isEmpty()) {
            try {
                tamano = Double.parseDouble(stringTamano);
            } catch (NumberFormatException e) {
                System.out.println("Problema tamaño" + e.getMessage());
            }
        }


        double precio = 0;
        try {
            precio = (double) spinnerPrecio;
        } catch (NumberFormatException e) {
            System.out.println("Problema precio");
        }


        Bolso nuevoBolso = null;
        BolsoViaje nuevoBolsoVaije = null;
        Maleta nuevaMaleta = null;


        switch (tipoProdcuto) {
            case BOLSO:
                Funcionalidad funcionalidad = (Funcionalidad) vista.comboboxFuncionalidadBolso.getSelectedItem();

                if (funcionalidad == null) {
                    Utilities.showMessage("Seleccione un Funcionalidad del Bolso", "Error", "ERROR");
                    vista.comboboxFuncionalidadBolso.requestFocus();
                    return;
                }
                nuevoBolso = new Bolso(precio, material, tamano, marca, isImpermable, peso, fecha, funcionalidad);
                break;
            case MALETA:

                Seguridad seguridad = (Seguridad) vista.comboboxSeguridad.getSelectedItem();
                boolean siTieneRueda = vista.rbSiRueda.isSelected();
                boolean noTieneRueda = vista.rbNoRueda.isSelected();

                if (seguridad == null) {
                    Utilities.showMessage("Seleccione un tipo de seguridad", "Error", "ERROR");
                    vista.comboboxSeguridad.requestFocus();
                    return;
                }

                if (!siTieneRueda && !noTieneRueda) {
                    Utilities.showMessage("Seleccione un tipo de seguridad", "Error", "ERROR");
                    vista.comboboxSeguridad.requestFocus();
                    return;
                }

                boolean isRuedaSeleccionada = siTieneRueda ? true : false;

                nuevaMaleta = new Maleta(precio, material, tamano, marca, isImpermable, peso, fecha, seguridad, isRuedaSeleccionada);

                break;
            case BOLSOVIAJE:
                FuncionAdicional funcionAdicional = (FuncionAdicional) vista.comboboxFuncionAdicional.getSelectedItem();

                if (funcionAdicional == null) {
                    Utilities.showMessage("Seleccione un Funcionalidad del Bolso de Viaje", "Error", "ERROR");
                    vista.comboboxFuncionAdicional.requestFocus();
                    return;
                }

                nuevoBolsoVaije = new BolsoViaje(precio, material, tamano, marca, isImpermable, peso, fecha, funcionAdicional);
                break;
        }


    }

    private void verAnadirProducto() {
        CardLayout cl = (CardLayout) vista.panelCentral.getLayout();
        cl.show(vista.panelCentral, "formulario");
//        actualizarTabla();
    }

    private void verListaProductos() {
        CardLayout cl = (CardLayout) vista.panelCentral.getLayout();
        cl.show(vista.panelCentral, "tabla");
    }

    private void verGestionArchivos() {
        CardLayout cl = (CardLayout) vista.panelCentral.getLayout();
        cl.show(vista.panelCentral, "archivos");
    }

    private void habilitarComponentes() {
        JComboBox[] combos = {vista.comboboxMarca, vista.comboboxMaterial, vista.comboboxFuncionalidadBolso, vista.comboboxSeguridad, vista.comboboxFuncionAdicional};
        Utilities.configurarComponente(combos, true);
        JRadioButton[] radioButtons = {vista.rbSiImpermeable, vista.rbNoImpermeable, vista.rbSiRueda, vista.rbNoRueda};
        Utilities.configurarComponente(radioButtons, true);

        vista.spnPrecio.setEnabled(true);
        vista.dpFechaCompra.setEnabled(true);
        vista.sliderPeso.setEnabled(true);
        vista.txtTamano.setEnabled(true);

        TipoProducto tipoProdcuto = (TipoProducto) vista.comboboxTipoProducto.getSelectedItem();
        vista.lblTipoProductos.setVisible(true);
        switch (tipoProdcuto) {
            case BOLSO:
                vista.lblTipoProductos.setText("Añadir Bolso");

                //Bolso
                vista.comboboxFuncionalidadBolso.setEnabled(true);
                vista.panelBolso.setVisible(true);

                //Bolso Viaje
                vista.panelBolsoViaje.setVisible(false);
                vista.comboboxFuncionAdicional.setEnabled(false);

                //Maleta
                vista.panelMaleta.setVisible(false);
                vista.comboboxSeguridad.setEnabled(false);
                vista.rbNoRueda.setEnabled(false);
                vista.rbNoRueda.setEnabled(false);
                break;
            case MALETA:
                vista.lblTipoProductos.setText("Añadir Maleta");

                //Bolso
                vista.comboboxFuncionalidadBolso.setEnabled(false);
                vista.panelBolso.setVisible(false);

                //Bolso Viaje
                vista.panelBolsoViaje.setVisible(false);
                vista.comboboxFuncionAdicional.setEnabled(false);

                //Maleta
                vista.panelMaleta.setVisible(true);
                vista.comboboxSeguridad.setEnabled(true);
                vista.rbNoRueda.setEnabled(true);
                vista.rbNoRueda.setEnabled(true);
                break;
            case BOLSOVIAJE:
                vista.lblTipoProductos.setText("Añadir Bolso Viaje");

                //Bolso
                vista.comboboxFuncionalidadBolso.setEnabled(false);
                vista.panelBolso.setVisible(false);

                //Bolso Viaje
                vista.panelBolsoViaje.setVisible(true);
                vista.comboboxFuncionAdicional.setEnabled(true);

                //Maleta
                vista.panelMaleta.setVisible(false);
                vista.comboboxSeguridad.setEnabled(false);
                vista.rbNoRueda.setEnabled(false);
                vista.rbNoRueda.setEnabled(false);
                break;
        }
    }

    private void ComprobacionDeCamposGenerales(TipoProducto tipoProdcuto, Material material, Marca marca, LocalDate fecha, Object spinnerPrecio, String tamaño, int peso, boolean impeableSiSeleccionado, boolean impeableN0Seleccionado) {

        if (tipoProdcuto == null) {
            Utilities.showMessage("Seleccione un tipo de producto", "Error", "ERROR");
            vista.comboboxTipoProducto.requestFocus();
            return;
        }

        if (fecha == null) {
            Utilities.showMessage("Seleccione una Fecha", "Error", "ERROR");
            vista.dpFechaCompra.requestFocus();
            return;
        }

        if (spinnerPrecio == null) {
            Utilities.showMessage("Fije un precio", "Error", "ERROR");
            vista.spnPrecio.requestFocus();
            return;
        }


        if (material == null) {
            Utilities.showMessage("Seleccione un tipo de Material", "Error", "ERROR");
            vista.comboboxMaterial.requestFocus();
            return;
        }


        if (tamaño == null) {
            Utilities.showMessage("añada el tamaño", "Error", "ERROR");
            vista.txtTamano.requestFocus();
            return;
        }

        if (!tamaño.matches("^\\d+$")) {
            Utilities.showMessage("El campo debe ser numerico", "Error", "ERROR");
            vista.txtTamano.requestFocus();
            return;
        }


        if (marca == null) {
            Utilities.showMessage("Seleccione un tipo de Marca", "Error", "ERROR");
            vista.comboboxMarca.requestFocus();
            return;
        }


        if (peso == 0) {
            Utilities.showMessage("Seleccione un peso valido desde ser mayor a 0", "Error", "ERROR");
            vista.comboboxMarca.requestFocus();
            return;
        }

        if (!impeableN0Seleccionado && !impeableSiSeleccionado) {
            Utilities.showMessage("Verificar campos Impermeable", "Error", "ERROR");
            vista.rbNoImpermeable.requestFocus();
            return;
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
    }
}
