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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Properties;

public class Controller implements ActionListener, ListSelectionListener, WindowListener {

    private View vista;
    private ProductoModel model;
    private Producto productoSeleccionado;
    private File ultimaRutaSeleccionada;

    public Controller(ProductoModel model, View vista) {
        this.model = model;
        this.vista = vista;

        cargarDatosConfiguracion();
        addWindowListener(this);
        registrarListener(this);
        addListSelectionListener(this);
    }

    private void addWindowListener(WindowListener listener) {
        vista.addWindowListener(listener);
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
        vista.btnEliminar.addActionListener(e);
        vista.btnModificar.addActionListener(e);
        vista.btnNuevo.addActionListener(e);
        vista.btnGuardar.addActionListener(e);
        vista.btnLimpiar.addActionListener(e);

        vista.btnImportarXML.setActionCommand("importarXML");
        vista.btnImportarXML.addActionListener(e);
        vista.btnExportarXML.setActionCommand("exportarXML");
        vista.btnExportarXML.addActionListener(e);

        vista.btnImportarJson.setActionCommand("importarJSON");
        vista.btnImportarJson.addActionListener(e);
        vista.btnExportarJson.setActionCommand("exportarJSON");
        vista.btnExportarJson.addActionListener(e);

        vista.comboboxTipoProducto.setActionCommand("TipoProductoSeleccionado");
        vista.comboboxTipoProducto.addActionListener(e);
    }

    private void funcionalidadBotones(String evt) {

        switch (evt) {
            case "Listar Productos":
                Utilities.verPatanllas(vista.panelCentral,"tabla");
                actualizarLista();
                break;
            case "Añadir Producto":
            case "Nuevo":
                Utilities.verPatanllas(vista.panelCentral,"formulario");
                break;
            case "importarXML":
                importarXML();
                break;
            case "exportarXML":
                exportarXML();
                break;
            case "importarJSON":
                importarJSON();
                break;
            case "exportarJSON":
                exportarJSON();
                break;
            case "Eliminar":
                eliminarProducto();
                break;
            case "Limpiar":
                limpiarFormulario();
                break;
            case "Modificar":
                modificarProducto();
                break;
            case "Gestionar Archivos":
                Utilities.verPatanllas(vista.panelCentral,"archivos");
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




    private void limpiarFormulario() {
        vista.comboboxTipoProducto.setSelectedIndex(-1);
        
        vista.dpFechaCompra.setDate(LocalDate.now());
        vista.dpFechaCompra.setEnabled(false);
        
        vista.spnPrecio.setValue((double)0);
        vista.spnPrecio.setEnabled(false);
        
        vista.comboboxMaterial.setSelectedIndex(1);
        vista.comboboxMaterial.setEnabled(false);
        
        vista.txtTamano.setText("");
        vista.txtTamano.setEnabled(false);
        
        vista.comboboxMarca.setSelectedIndex(1);
        vista.comboboxMarca.setEnabled(false);
        
        vista.sliderPeso.setValue(0);
        vista.sliderPeso.setEnabled(false);
        
        vista.rbSiImpermeable.setSelected(false);
        vista.rbSiImpermeable.setEnabled(false);
        
        vista.rbNoImpermeable.setSelected(false);
        vista.rbNoImpermeable.setEnabled(false);

        //Bolso
        vista.comboboxFuncionalidadBolso.setEnabled(false);
        vista.panelBolso.setVisible(false);

        //Bolso Viaje
        vista.panelBolsoViaje.setVisible(false);
        vista.comboboxFuncionAdicional.setEnabled(false);

        //Maleta
        vista.panelMaleta.setVisible(false);
        vista.comboboxSeguridad.setEnabled(false);
        vista.rbNoRueda.setEnabled(false);
        vista.rbSiRueda.setEnabled(false);


    }

    private void modificarProducto() {
        productoSeleccionado = vista.listProductos.getSelectedValue();

        if (productoSeleccionado == null) {
            Utilities.showMessage("Seleccione un Producto", "Error", "Error");
            return;
        }


        vista.dpFechaCompra.setDate(productoSeleccionado.getFechaCompra());
        vista.spnPrecio.setValue(productoSeleccionado.getPrecio());
        vista.txtTamano.setText(String.valueOf(productoSeleccionado.getTamano()));
        vista.sliderPeso.setValue((int) productoSeleccionado.getPeso());
        vista.comboboxTipoProducto.setSelectedItem(productoSeleccionado.getTipoProducto());
        vista.comboboxMaterial.setSelectedItem(productoSeleccionado.getMateria());
        vista.comboboxMarca.setSelectedItem(productoSeleccionado.getMarca());
        vista.rbSiImpermeable.setSelected(productoSeleccionado.isImpermeable());
        vista.rbNoImpermeable.setSelected(!productoSeleccionado.isImpermeable());


        if (productoSeleccionado instanceof Bolso) {
            vista.comboboxFuncionalidadBolso.setSelectedItem(
                    ((Bolso) productoSeleccionado).getFuncionalidad());
        } else if (productoSeleccionado instanceof Maleta) {
            vista.comboboxSeguridad.setSelectedItem(
                    ((Maleta) productoSeleccionado).getSeguridad());
            vista.rbSiRueda.setSelected(((Maleta) productoSeleccionado).isRuedas());
            vista.rbNoRueda.setSelected(!((Maleta) productoSeleccionado).isRuedas());
        } else if (productoSeleccionado instanceof BolsoViaje) {
            vista.comboboxFuncionAdicional.setSelectedItem(
                    ((BolsoViaje) productoSeleccionado).getFuncionAdicional());
        }

        Utilities.verPatanllas(vista.panelCentral,"formulario");
    }

    private void guardarArchivo() {

        TipoProducto tipoProdcuto = (TipoProducto) vista.comboboxTipoProducto.getSelectedItem();

        Material material = (Material) vista.comboboxMaterial.getSelectedItem();
        Marca marca = (Marca) vista.comboboxMarca.getSelectedItem();

        LocalDate fecha = vista.dpFechaCompra.getDate();
        Double spinnerPrecio = (Double) vista.spnPrecio.getValue();
        String stringTamano = vista.txtTamano.getText();
        int peso = vista.sliderPeso.getValue();

        boolean esImpermeableSiSeleccionado = vista.rbSiImpermeable.isSelected();
        boolean esImpermeableNoSeleccionado = vista.rbNoImpermeable.isSelected();

        boolean valido = ComprobacionDeCamposGenerales(material, marca, fecha,
                spinnerPrecio, stringTamano, peso,
                esImpermeableSiSeleccionado, esImpermeableNoSeleccionado
        );

        if (!valido) {
            return;
        }


        double tamano = Double.parseDouble(stringTamano);
        double precio = spinnerPrecio;


        if (productoSeleccionado == null) {
            switch (tipoProdcuto) {
                case BOLSO:
                    Funcionalidad funcionalidad = (Funcionalidad) vista.comboboxFuncionalidadBolso.getSelectedItem();

                    if (funcionalidad == null) {
                        Utilities.showMessage("Seleccione un Funcionalidad del Bolso", "Error", "ERROR");
                        vista.comboboxFuncionalidadBolso.requestFocus();
                        return;
                    }
                    model.altaBolso(tipoProdcuto, precio, material, tamano, marca, esImpermeableSiSeleccionado, peso, fecha, funcionalidad);
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
                        Utilities.showMessage("Seleccione si tiene ruedas", "Error", "ERROR");
                        vista.comboboxSeguridad.requestFocus();
                        return;
                    }
                    model.altaMaleta(tipoProdcuto, precio, material, tamano, marca, esImpermeableSiSeleccionado, peso, fecha, seguridad, siTieneRueda);
                    break;
                case BOLSOVIAJE:
                    FuncionAdicional funcionAdicional = (FuncionAdicional) vista.comboboxFuncionAdicional.getSelectedItem();

                    if (funcionAdicional == null) {
                        Utilities.showMessage("Seleccione un Funcionalidad del Bolso de Viaje", "Error", "ERROR");
                        vista.comboboxFuncionAdicional.requestFocus();
                        return;
                    }
                    model.altaBolsoViaje(tipoProdcuto, precio, material, tamano, marca, esImpermeableSiSeleccionado, peso, fecha, funcionAdicional);
                    break;
            }
            Utilities.showMessage("Producto Guardado", "Informacion", "INFORMATION");
        } else {
            productoSeleccionado.setTipoProducto(tipoProdcuto);
            productoSeleccionado.setFechaCompra(fecha);
            productoSeleccionado.setPrecio(precio);
            productoSeleccionado.setMarca(marca);
            productoSeleccionado.setTamano(tamano);
            productoSeleccionado.setMateria(material);
            productoSeleccionado.setPeso(peso);
            productoSeleccionado.setImpermeable(esImpermeableSiSeleccionado);


            if (productoSeleccionado instanceof Bolso) {
                Funcionalidad funcionalidad = (Funcionalidad) vista.comboboxFuncionalidadBolso.getSelectedItem();
                ((Bolso) productoSeleccionado).setFuncionalidad(funcionalidad);
            } else if (productoSeleccionado instanceof BolsoViaje) {

                FuncionAdicional funcionAdicional = (FuncionAdicional) vista.comboboxFuncionAdicional.getSelectedItem();
                ((BolsoViaje) productoSeleccionado).setFuncionAdicional(funcionAdicional);
            } else if (productoSeleccionado instanceof Maleta) {
                Seguridad seguridad = (Seguridad) vista.comboboxSeguridad.getSelectedItem();
                boolean siTieneRueda = vista.rbSiRueda.isSelected();

                ((Maleta) productoSeleccionado).setSeguridad(seguridad);
                ((Maleta) productoSeleccionado).setRuedas(siTieneRueda);
            }

            model.actualizarReserva(productoSeleccionado);
            Utilities.showMessage("Producto Actualizado", "Informacion", "INFORMATION");
        }

        limpiarFormulario();
        actualizarLista();
        verListaProductos();
    }


    private void verListaProductos() {
        CardLayout cl = (CardLayout) vista.panelCentral.getLayout();
        cl.show(vista.panelCentral, "tabla");
        actualizarLista();
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

        if (tipoProdcuto != null) {
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

    }

    private boolean ComprobacionDeCamposGenerales(Material material, Marca marca, LocalDate fecha, Object spinnerPrecio, String tamano, int peso, boolean impeableSiSeleccionado, boolean impeableN0Seleccionado) {

        if (vista.comboboxTipoProducto.getSelectedIndex() == -1) {
            Utilities.showMessage("Seleccione un tipo de producto", "Error", "ERROR");
            vista.comboboxTipoProducto.requestFocus();
            return false;
        }

        if (fecha == null) {
            Utilities.showMessage("Seleccione una Fecha", "Error", "ERROR");
            vista.dpFechaCompra.requestFocus();
            return false;
        }

        if (spinnerPrecio == null || !vista.spnPrecio.isEnabled()) {
            Utilities.showMessage("Fije un precio", "Error", "ERROR");
            vista.spnPrecio.requestFocus();
            return false;
        }

        if (material == null) {
            Utilities.showMessage("Seleccione un tipo de Material", "Error", "ERROR");
            vista.comboboxMaterial.requestFocus();
            return false;
        }

        if (tamano == null || tamano.isEmpty()) {
            Utilities.showMessage("Añada el tamaño", "Error", "ERROR");
            vista.txtTamano.requestFocus();
            return false;
        }

        if (!tamano.matches("^\\d+(\\.\\d+)?$")) {
            Utilities.showMessage("El tamaño debe ser numérico", "Error", "ERROR");
            vista.txtTamano.requestFocus();
            return false;
        }

        if (marca == null) {
            Utilities.showMessage("Seleccione un tipo de Marca", "Error", "ERROR");
            vista.comboboxMarca.requestFocus();
            return false;
        }

        if (peso <= 0) {
            Utilities.showMessage("Seleccione un peso válido (mayor a 0)", "Error", "ERROR");
            vista.sliderPeso.requestFocus();
            return false;
        }

        if (!impeableN0Seleccionado && !impeableSiSeleccionado) {
            Utilities.showMessage("Verifique el campo Impermeable", "Error", "ERROR");
            vista.rbNoImpermeable.requestFocus();
            return false;
        }

        return true;
    }

    private void addListSelectionListener(ListSelectionListener listener) {
        vista.listProductos.addListSelectionListener(listener);
    }

    private void actualizarLista() {
        vista.listModel.clear();
        for (Producto producto : model.getProductos()) {
            vista.listModel.addElement(producto);
        }
    }

    private void eliminarProducto() {
        Producto productoSeleccionado = vista.listProductos.getSelectedValue();

        if (productoSeleccionado == null) {
            Utilities.showMessage("Seleccione un Producto", "Error", "Error");
            return;
        }

        int index = vista.listProductos.getSelectedIndex();

        if (index == -1) {
            Utilities.showMessage("Selecciona un producto para eliminar.", "Error", "ERROR");
            return;
        }

        boolean eliminado = model.eliminarProducto(productoSeleccionado);

        if (eliminado) {
            vista.listModel.remove(index);
            actualizarLista();
        }
    }


    private void cargarDatosConfiguracion() {

        Properties configuracion = new Properties();

        try {
            configuracion.load(new FileReader("productos.conf"));
            ultimaRutaSeleccionada = new File(configuracion.getProperty("ultimaRutaSeleccionada"));
        } catch (IOException e) {
            Utilities.showMessage(
                    "El Sistema creara una ruta segura para guardar tus archivos",
                    "Information",
                    "INFORMATION"
            );
            ultimaRutaSeleccionada = new File(System.getProperty("user.home"));
        }

    }

    private void actualizarDatosConfiguracion(File ultimaRutaExportada) {
        this.ultimaRutaSeleccionada = ultimaRutaExportada;
    }

    private void guardarConfiguracion() {
        Properties configuracion = new Properties();
        configuracion.setProperty("ultimaRutaSeleccionada"
                , ultimaRutaSeleccionada.getAbsolutePath());
        try {
            configuracion.store(new PrintWriter("productos.conf")
                    , "Datos configuracion productos");
        } catch (IOException e) {
            Utilities.showMessage(
                    "No se pudo guardar el archivo de configuración 'productos.conf'.\n" +
                            "Verifique los permisos de escritura o el espacio disponible en disco.",
                    "Error al guardar configuración",
                    "ERROR"
            );
        }
    }
    private void importarXML() {
        JFileChooser selectorFichero = Utilities.crearSelectorFichero(ultimaRutaSeleccionada
                ,"Archivos XML (*.xml)","xml");
        int opt =selectorFichero.showOpenDialog(null);
        if (opt==JFileChooser.APPROVE_OPTION) {
            model.getProductos().clear();
            model.importarXML(selectorFichero.getSelectedFile());
            actualizarLista();
            verListaProductos();
        }
    }

    private void exportarXML() {
        JFileChooser selectorFichero2= Utilities.crearSelectorFichero(ultimaRutaSeleccionada
                ,"Archivos XML (*.xml)","xml");
        int opt2=selectorFichero2.showSaveDialog(null);
        if (opt2==JFileChooser.APPROVE_OPTION) {
            File archivo = selectorFichero2.getSelectedFile();
            model.exportarXML(archivo);
            actualizarDatosConfiguracion(archivo.getParentFile());
        }
    }

    private void exportarJSON() {
        JFileChooser selectorFichero = Utilities.crearSelectorFichero(ultimaRutaSeleccionada, "Archivos JSON", "json");
        int opt = selectorFichero.showSaveDialog(null);
        if (opt == JFileChooser.APPROVE_OPTION) {
            File archivo = selectorFichero.getSelectedFile();
            model.exportarJSON(archivo);
            actualizarDatosConfiguracion(archivo.getParentFile());
        }
    }

    private void importarJSON() {
        JFileChooser selectorFichero = Utilities.crearSelectorFichero(ultimaRutaSeleccionada, "Archivos JSON", "json");
        int opt = selectorFichero.showOpenDialog(null);
        if (opt == JFileChooser.APPROVE_OPTION) {
            model.getProductos().clear();
            model.getProductos().addAll(model.importarJSON(selectorFichero.getSelectedFile()));
            actualizarLista();
            verListaProductos();
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int resp= Utilities.mensajeConfirmacion("¿Desea cerrar la ventana?","Salir");
        if (resp== JOptionPane.OK_OPTION) {
            guardarConfiguracion();
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
