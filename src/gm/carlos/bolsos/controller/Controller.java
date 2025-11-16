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

/**
 * Clase Controladora principal para la aplicación "Tienda de Bolsos".
 * Gestiona la interacción entre la vista (GUI) y el modelo (ProductoModel),
 * manejando eventos de botones, listas y ventanas.
 *
 * Permite añadir, modificar, eliminar productos, importar/exportar XML y JSON,
 * y gestionar la configuración del programa.
 */

public class Controller implements ActionListener, ListSelectionListener, WindowListener {

    private View vista;
    private ProductoModel model;
    private Producto productoSeleccionado;
    private File ultimaRutaSeleccionada;

    /**
     * Constructor de Controller.
     *
     * @param model La instancia del modelo de productos.
     * @param vista La instancia de la vista principal.
     */

    public Controller(ProductoModel model, View vista) {
        this.model = model;
        this.vista = vista;

        cargarDatosConfiguracion();
        addWindowListener(this);
        registrarListener(this);
        addListSelectionListener(this);
    }

    /**
     * Agrega un WindowListener a la ventana principal.
     *
     * @param listener El listener que será agregado.
     */
    private void addWindowListener(WindowListener listener) {
        vista.addWindowListener(listener);
    }


    /**
     * Método que maneja todos los eventos de los botones y combobox.
     *
     * @param e Evento de acción disparado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String evt = e.getActionCommand();
        funcionalidadBotones(evt);
    }

    /**
     * Registra todos los listeners de botones y combobox.
     *
     * @param e ActionListener a registrar.
     */
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

        vista.itemNuevo.addActionListener(e);
        vista.itemNuevo.setActionCommand("nuevoItem");

        vista.itemVerLista.addActionListener(e);
        vista.itemVerLista.setActionCommand("listarItem");

        vista.itemExportarXML.addActionListener(e);
        vista.itemExportarXML.setActionCommand("exportarXMLItem");

        vista.itemImportarXML.addActionListener(e);
        vista.itemImportarXML.setActionCommand("importarXMLItem");

        vista.itemImportarJSON.addActionListener(e);
        vista.itemImportarJSON.setActionCommand("importarJSONItem");

        vista.itemExportarJSON.addActionListener(e);
        vista.itemExportarJSON.setActionCommand("exportarJSONItem");
    }

    /**
     * Controla la funcionalidad de todos los botones según su comando.
     *
     * @param evt Comando del botón.
     */
    private void funcionalidadBotones(String evt) {

        switch (evt) {
            case "Listar Productos":
            case "listarItem":

                if(productoSeleccionado != null){
                    int resp= Utilities.mensajeConfirmacion("Actualmente tienes datos para modificar\n" +
                            "¿Desea eliminar cambios?","Salir");

                    if (resp== JOptionPane.OK_OPTION) {
                        limpiarFormulario();
                        productoSeleccionado = null;
                    }else{
                        Utilities.verPatanllas(vista.panelCentral,"formulario");
                    }
                }

                if(productoSeleccionado == null){
                    Utilities.verPatanllas(vista.panelCentral,"tabla");
                }

                actualizarLista();
                break;
            case "Añadir Producto":
            case "Nuevo":
            case "nuevoItem":
                Utilities.verPatanllas(vista.panelCentral,"formulario");
                break;
            case "importarXML":
            case "importarXMLItem":
                importarXML();
                break;
            case "exportarXML":
            case "exportarXMLItem":
                exportarXML();
                break;
            case "importarJSON":
            case "importarJSONItem":
                importarJSON();
                break;
            case "exportarJSON":
            case "exportarJSONItem":
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

                if(productoSeleccionado != null){
                    int resp= Utilities.mensajeConfirmacion("Actualmente tienes datos para modificar\n" +
                            "¿Desea eliminar cambios?","Salir");

                if (resp== JOptionPane.OK_OPTION) {
                    productoSeleccionado = null;
                    limpiarFormulario();
                    Utilities.verPatanllas(vista.panelCentral,"archivos");
                }else{
                    Utilities.verPatanllas(vista.panelCentral,"formulario");
                }
            }

                if(productoSeleccionado == null){
                    Utilities.verPatanllas(vista.panelCentral,"archivos");
                }

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

    /**
     * Muestra el formulario para añadir un nuevo producto.
     * Pide confirmación si hay un producto seleccionado actualmente.
     */
    private void irFormulario(){

        int resp= Utilities.mensajeConfirmacion("¿Deseas crear un nuevo producto?","Salir");

        if (resp== JOptionPane.OK_OPTION) {
            limpiarFormulario();
            Utilities.verPatanllas(vista.panelCentral,"formulario");
        }else{
            actualizarLista();
            Utilities.verPatanllas(vista.panelCentral,"tabla");
        }
    }

    /**
     * Limpia todos los campos del formulario y desactiva componentes.
     * También resetea la selección de producto actual.
     */
    private void limpiarFormulario() {
        productoSeleccionado = null;

        vista.comboboxTipoProducto.setSelectedIndex(-1);
        
        vista.dpFechaCompra.setDate(LocalDate.now());
        vista.dpFechaCompra.setEnabled(false);
        
        vista.spnPrecio.setValue((double)0);
        vista.spnPrecio.setEnabled(false);
        
        vista.comboboxMaterial.setSelectedIndex(1);
        vista.comboboxMaterial.setEnabled(false);
        
        vista.txtTamano.setText("0");
        vista.txtTamano.setEnabled(false);
        
        vista.comboboxMarca.setSelectedIndex(1);
        vista.comboboxMarca.setEnabled(false);
        
        vista.sliderPeso.setValue(0);
        vista.sliderPeso.setEnabled(false);
        
        vista.rbSiImpermeable.setSelected(false);
        vista.rbSiImpermeable.setEnabled(false);
        
        vista.rbNoImpermeable.setSelected(false);
        vista.rbNoImpermeable.setEnabled(false);
        vista.lblTipoProductos.setVisible(false);
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

    /**
     * Permite modificar un producto seleccionado en la lista.
     * Carga los datos del producto en el formulario.
     */
    private void modificarProducto() {
        productoSeleccionado = vista.listProductos.getSelectedValue();

        if (productoSeleccionado == null) {
            Utilities.mostrarMensaje("Seleccione un Producto", "Error", "Error");
            return;
        }

        int resp= Utilities.mensajeConfirmacion("¿Seguro que deseas modificar el producto?","Salir");
        if (resp== JOptionPane.OK_OPTION) {
            vista.dpFechaCompra.setDate(productoSeleccionado.getFechaCompra());
            vista.spnPrecio.setValue(productoSeleccionado.getPrecio());
            vista.txtTamano.setText(String.valueOf(productoSeleccionado.getTamano()));
            vista.sliderPeso.setValue((int) productoSeleccionado.getPeso());
            vista.comboboxTipoProducto.setSelectedItem(productoSeleccionado.getTipoProducto());
            vista.comboboxMaterial.setSelectedItem(productoSeleccionado.getMaterial());
            vista.comboboxMarca.setSelectedItem(productoSeleccionado.getMarca());
            vista.rbSiImpermeable.setSelected(productoSeleccionado.isImpermeable());
            vista.rbNoImpermeable.setSelected(!productoSeleccionado.isImpermeable());


            if (productoSeleccionado instanceof Bolso) {
                vista.comboboxFuncionalidadBolso.setSelectedItem(
                        ((Bolso) productoSeleccionado).getFuncionalidadBolso());
            } else if (productoSeleccionado instanceof Maleta) {
                vista.comboboxSeguridad.setSelectedItem(
                        ((Maleta) productoSeleccionado).getSeguridadMaleta());
                vista.rbSiRueda.setSelected(((Maleta) productoSeleccionado).hasRuedas());
                vista.rbNoRueda.setSelected(!((Maleta) productoSeleccionado).hasRuedas());
            } else if (productoSeleccionado instanceof BolsoViaje) {
                vista.comboboxFuncionAdicional.setSelectedItem(
                        ((BolsoViaje) productoSeleccionado).getFuncionalidadBolsoViaje());
            }

            Utilities.verPatanllas(vista.panelCentral,"formulario");
        }


    }

    /**
     * Guarda un nuevo producto o actualiza uno existente.
     * Realiza validaciones antes de guardar y luego actualiza la lista.
     */
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

        Producto producto = null;
        if (productoSeleccionado == null) {
            switch (tipoProdcuto) {
                case BOLSO:
                    FuncionalidadBolso funcionalidadBolso = (FuncionalidadBolso) vista.comboboxFuncionalidadBolso.getSelectedItem();

                    if (funcionalidadBolso == null) {
                        Utilities.mostrarMensaje("Seleccione un Funcionalidad del Bolso", "Error", "ERROR");
                        vista.comboboxFuncionalidadBolso.requestFocus();
                        return;
                    }
                    model.altaProducto(new Bolso(tipoProdcuto, precio, material, tamano, marca, esImpermeableSiSeleccionado, peso, fecha, funcionalidadBolso));
                   // model.altaBolso(tipoProdcuto, precio, material, tamano, marca, esImpermeableSiSeleccionado, peso, fecha, funcionalidadBolso);
                    break;
                case MALETA:

                    SeguridadMaleta seguridadMaleta = (SeguridadMaleta) vista.comboboxSeguridad.getSelectedItem();
                    boolean siTieneRueda = vista.rbSiRueda.isSelected();
                    boolean noTieneRueda = vista.rbNoRueda.isSelected();

                    if (seguridadMaleta == null) {
                        Utilities.mostrarMensaje("Seleccione un tipo de seguridad", "Error", "ERROR");
                        vista.comboboxSeguridad.requestFocus();
                        return;
                    }

                    if (!siTieneRueda && !noTieneRueda) {
                        Utilities.mostrarMensaje("Seleccione si tiene ruedas", "Error", "ERROR");
                        vista.comboboxSeguridad.requestFocus();
                        return;
                    }
                    model.altaProducto(new Maleta(tipoProdcuto, precio, material, tamano, marca, esImpermeableSiSeleccionado, peso, fecha, seguridadMaleta, siTieneRueda));
                    //model.altaMaleta(tipoProdcuto, precio, material, tamano, marca, esImpermeableSiSeleccionado, peso, fecha, seguridadMaleta, siTieneRueda);
                    break;
                case BOLSOVIAJE:
                    FuncionalidadBolsoViaje funcionalidadBolsoViaje = (FuncionalidadBolsoViaje) vista.comboboxFuncionAdicional.getSelectedItem();

                    if (funcionalidadBolsoViaje == null) {
                        Utilities.mostrarMensaje("Seleccione un Funcionalidad del Bolso de Viaje", "Error", "ERROR");
                        vista.comboboxFuncionAdicional.requestFocus();
                        return;
                    }
                    model.altaProducto(new BolsoViaje(tipoProdcuto, precio, material, tamano, marca, esImpermeableSiSeleccionado, peso, fecha, funcionalidadBolsoViaje));
                    //model.altaBolsoViaje(tipoProdcuto, precio, material, tamano, marca, esImpermeableSiSeleccionado, peso, fecha, funcionalidadBolsoViaje);
                    break;
            }
            Utilities.mostrarMensaje("Producto Guardado", "Informacion", "INFORMATION");
        } else {


            productoSeleccionado.setTipoProducto(tipoProdcuto);
            productoSeleccionado.setFechaCompra(fecha);
            productoSeleccionado.setPrecio(precio);
            productoSeleccionado.setMarca(marca);
            productoSeleccionado.setTamano(tamano);
            productoSeleccionado.setMaterial(material);
            productoSeleccionado.setPeso(peso);
            productoSeleccionado.setImpermeable(esImpermeableSiSeleccionado);


            if (productoSeleccionado instanceof Bolso) {
                FuncionalidadBolso funcionalidadBolso = (FuncionalidadBolso) vista.comboboxFuncionalidadBolso.getSelectedItem();
                ((Bolso) productoSeleccionado).setFuncionalidadBolso(funcionalidadBolso);
            } else if (productoSeleccionado instanceof BolsoViaje) {

                FuncionalidadBolsoViaje funcionalidadBolsoViaje = (FuncionalidadBolsoViaje) vista.comboboxFuncionAdicional.getSelectedItem();
                ((BolsoViaje) productoSeleccionado).setFuncionalidadBolsoViaje(funcionalidadBolsoViaje);
            } else if (productoSeleccionado instanceof Maleta) {
                SeguridadMaleta seguridadMaleta = (SeguridadMaleta) vista.comboboxSeguridad.getSelectedItem();
                boolean siTieneRueda = vista.rbSiRueda.isSelected();

                ((Maleta) productoSeleccionado).setSeguridadMaleta(seguridadMaleta);
                ((Maleta) productoSeleccionado).setRuedas(siTieneRueda);
            }

            model.actualizarReserva(productoSeleccionado);
            productoSeleccionado = null;
            Utilities.mostrarMensaje("Producto Actualizado", "Informacion", "INFORMATION");
        }

        limpiarFormulario();
        actualizarLista();
        verListaProductos();
    }

    /**
     * Muestra el panel de la lista de productos y actualiza su contenido.
     */
    private void verListaProductos() {
        CardLayout cl = (CardLayout) vista.panelCentral.getLayout();
        cl.show(vista.panelCentral, "tabla");
        actualizarLista();
    }

    /**
     * Habilita los componentes del formulario según el tipo de producto seleccionado.
     */
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
                    vista.lblTipoProductos.setText("Estas añadiendo un Bolso");

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
                    vista.lblTipoProductos.setText("Estas añadiendo una Maleta");

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
                    vista.lblTipoProductos.setText("Estas añadiendo un Bolso de Viaje");

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

    /**
     * Valida los campos generales del formulario.
     *
     * @param material               Material seleccionado.
     * @param marca                  Marca seleccionada.
     * @param fecha                  Fecha de compra.
     * @param spinnerPrecio          Precio del producto.
     * @param tamano                 Tamaño del producto.
     * @param peso                   Peso del producto.
     * @param impeableSiSeleccionado Indica si se seleccionó "Sí" en impermeable.
     * @param impeableN0Seleccionado Indica si se seleccionó "No" en impermeable.
     * @return {@code true} si todos los campos son válidos; {@code false} en caso contrario.
     */
    private boolean ComprobacionDeCamposGenerales(Material material, Marca marca, LocalDate fecha, Object spinnerPrecio, String tamano, int peso, boolean impeableSiSeleccionado, boolean impeableN0Seleccionado) {

        if (vista.comboboxTipoProducto.getSelectedIndex() == -1) {
            Utilities.mostrarMensaje("Seleccione un tipo de producto", "Error", "ERROR");
            vista.comboboxTipoProducto.requestFocus();
            return false;
        }

        if (fecha == null) {
            Utilities.mostrarMensaje("Seleccione una Fecha", "Error", "ERROR");
            vista.dpFechaCompra.requestFocus();
            return false;
        }

        if (spinnerPrecio == null || !vista.spnPrecio.isEnabled()) {
            Utilities.mostrarMensaje("Fije un precio", "Error", "ERROR");
            vista.spnPrecio.requestFocus();
            return false;
        }

        if (material == null) {
            Utilities.mostrarMensaje("Seleccione un tipo de Material", "Error", "ERROR");
            vista.comboboxMaterial.requestFocus();
            return false;
        }

        if (tamano == null || tamano.isEmpty()) {
            Utilities.mostrarMensaje("Añada el tamaño", "Error", "ERROR");
            vista.txtTamano.requestFocus();
            return false;
        }

        if (!tamano.matches("^\\d+(\\.\\d+)?$")) {
            Utilities.mostrarMensaje("El tamaño debe ser numérico", "Error", "ERROR");
            vista.txtTamano.setText("0");
            vista.txtTamano.requestFocus();
            return false;
        }

        if (tamano.equals("0")) {
            Utilities.mostrarMensaje("El tamaño debe ser mayor a 0", "Error", "ERROR");
            vista.txtTamano.setText("0");
            vista.txtTamano.requestFocus();
            return false;
        }

        if (marca == null) {
            Utilities.mostrarMensaje("Seleccione un tipo de Marca", "Error", "ERROR");
            vista.comboboxMarca.requestFocus();
            return false;
        }

        if (peso <= 0) {
            Utilities.mostrarMensaje("Seleccione un peso válido (mayor a 0)", "Error", "ERROR");
            vista.sliderPeso.requestFocus();
            return false;
        }

        if (!impeableN0Seleccionado && !impeableSiSeleccionado) {
            Utilities.mostrarMensaje("Verifique el campo Impermeable", "Error", "ERROR");
            vista.rbNoImpermeable.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Agrega un {ListSelectionListener} a la lista de productos.
     *
     * @param listener Listener que será agregado.
     */

    private void addListSelectionListener(ListSelectionListener listener) {
        vista.listProductos.addListSelectionListener(listener);
    }

    /**
     * Actualiza la lista de productos en la vista.
     */
    private void actualizarLista() {
        vista.listModel.clear();
        for (Producto producto : model.getProductos()) {
            vista.listModel.addElement(producto);
        }
    }

    /**
     * Elimina un producto seleccionado de la lista y del modelo.
     */
    private void eliminarProducto() {
        Producto productoSeleccionado = vista.listProductos.getSelectedValue();

        if (productoSeleccionado == null) {
            Utilities.mostrarMensaje("Seleccione un Producto", "Error", "Error");
            return;
        }

        int index = vista.listProductos.getSelectedIndex();

        if (index == -1) {
            Utilities.mostrarMensaje("Selecciona un producto para eliminar.", "Error", "ERROR");
            return;
        }

        boolean eliminado = model.eliminarProducto(productoSeleccionado);

        if (eliminado) {
            vista.listModel.remove(index);
            actualizarLista();
        }
    }

    /**
     * Carga la configuración previa del programa desde el archivo productos.conf.
     * Si no existe, se asigna una ruta segura por defecto.
     */
    private void cargarDatosConfiguracion() {

        Properties configuracion = new Properties();

        try {
            configuracion.load(new FileReader("productos.conf"));
            ultimaRutaSeleccionada = new File(configuracion.getProperty("ultimaRutaSeleccionada"));
        } catch (IOException e) {
            Utilities.mostrarMensaje(
                    "El Sistema creara una ruta segura para guardar tus archivos",
                    "Information",
                    "INFORMATION"
            );
            ultimaRutaSeleccionada = new File(System.getProperty("user.home"));
        }

    }

    /**
     * Actualiza la ruta de exportación/importación recientemente utilizada.
     *
     * @param ultimaRutaExportada Carpeta donde se guardó/exportó el archivo.
     */
    private void actualizarDatosConfiguracion(File ultimaRutaExportada) {
        this.ultimaRutaSeleccionada = ultimaRutaExportada;
    }


    /**
     * Guarda la configuración actual en el archivo productos.conf.
     */
    private void guardarConfiguracion() {
        Properties configuracion = new Properties();
        configuracion.setProperty("ultimaRutaSeleccionada"
                , ultimaRutaSeleccionada.getAbsolutePath());
        try {
            configuracion.store(new PrintWriter("productos.conf")
                    , "Datos configuracion productos");
        } catch (IOException e) {
            Utilities.mostrarMensaje(
                    "No se pudo guardar el archivo de configuración 'productos.conf'.\n" +
                            "Verifique los permisos de escritura o el espacio disponible en disco.",
                    "Error al guardar configuración",
                    "ERROR"
            );
        }
    }

    /**
     * Importa productos desde un archivo XML seleccionado por el usuario.
     */
    private void importarXML() {
        JFileChooser selectorFichero = Utilities.crearSelectorFichero(ultimaRutaSeleccionada
                ,"Archivos XML","xml");
        int opt =selectorFichero.showOpenDialog(null);
        if (opt==JFileChooser.APPROVE_OPTION) {
            model.getProductos().clear();
            model.importarXML(selectorFichero.getSelectedFile());
            actualizarLista();
            verListaProductos();
        }
    }

    /**
     * Exporta productos a un archivo XML seleccionado por el usuario.
     */
    private void exportarXML() {

        if (comprobarListaExportar()) return;

        JFileChooser selectorFichero2= Utilities.crearSelectorFichero(ultimaRutaSeleccionada
                ,"Archivos XML","xml");
        int opt2=selectorFichero2.showSaveDialog(null);
        if (opt2==JFileChooser.APPROVE_OPTION) {
            File archivo = selectorFichero2.getSelectedFile();
            model.exportarXML(archivo);
            actualizarDatosConfiguracion(archivo.getParentFile());
            irFormulario();
        }
    }


    /**
     * Exporta productos a un archivo JSON seleccionado por el usuario.
     */
    private void exportarJSON() {

        if (comprobarListaExportar()) return;

        JFileChooser selectorFichero = Utilities.crearSelectorFichero(ultimaRutaSeleccionada, "Archivos JSON", "json");
        int opt = selectorFichero.showSaveDialog(null);
        if (opt == JFileChooser.APPROVE_OPTION) {
            File archivo = selectorFichero.getSelectedFile();
            model.exportarJSON(archivo);
            actualizarDatosConfiguracion(archivo.getParentFile());
            irFormulario();
        }
    }

    /**
     * Importa productos desde un archivo JSON seleccionado por el usuario.
     */
    private void importarJSON() {
        JFileChooser selectorFichero = Utilities.crearSelectorFichero(ultimaRutaSeleccionada, "Archivos JSON", "json");
        int opt = selectorFichero.showOpenDialog(null);
        if (opt == JFileChooser.APPROVE_OPTION) {
            model.getProductos().clear();
            model.importarJSON(selectorFichero.getSelectedFile());
            actualizarLista();
            verListaProductos();
        }
    }


    /**
     * Comprueba si hay productos en la lista antes de exportar.
     *
     * @return {true} si no hay productos y se debe abortar la exportación, {false} en caso contrario.
     */
    private boolean comprobarListaExportar() {
        if(vista.listModel.isEmpty()){
            int resp= Utilities.mensajeConfirmacion("Actualemte no tienes ningun Producto Guardado\n¿Quieres Crear un nuevo?","Salir");

            if (resp== JOptionPane.OK_OPTION) {
                limpiarFormulario();
                Utilities.verPatanllas(vista.panelCentral,"formulario");
                return true;
            }else{
                actualizarLista();
                Utilities.verPatanllas(vista.panelCentral,"tabla");
                return true;
            }
        }
        return false;
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
