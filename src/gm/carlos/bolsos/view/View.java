package gm.carlos.bolsos.view;

import com.github.lgooddatepicker.components.DatePicker;
import gm.carlos.bolsos.model.base.Producto;
import gm.carlos.bolsos.model.enums.*;
import gm.carlos.bolsos.utilities.Utilities;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class View extends JFrame{

    public JPanel panelPrincipal;
    public JPanel panelSuperior;
    public JPanel panelDerecho;
    public JPanel panelCentral;
    public JPanel panelListarProductos;
    public JPanel panelAnadirProducto;
    public JPanel panelArchivos;
    public JPanel panelBolsoViaje;
    public JPanel panelBolso;
    public JPanel panelMaleta;

    public JButton btnListarProductos;
    public JButton btnAnadirProductos;
    public JButton btnGestionarArchivos;
    public JButton btnImportarXML;
    public JButton btnExportarXML;
    public JButton btnGuardar;
    public JButton btnLimpiar;
    public JButton btnEliminar;
    public JButton btnModificar;
    public JButton btnNuevo;
    public JButton btnImportarJson;
    public JButton btnExportarJson;

    public JComboBox<TipoProducto> comboboxTipoProducto;
    public JComboBox<Material> comboboxMaterial;
    public JComboBox<Marca> comboboxMarca;
    public JComboBox<Funcionalidad> comboboxFuncionalidadBolso;
    public JComboBox<Seguridad> comboboxSeguridad;
    public JComboBox<FuncionAdicional> comboboxFuncionAdicional;

    public JSeparator SpPrimero;
    public JSeparator spSegundo;

    public JSpinner spnPrecio;
    private SpinnerNumberModel modeloPrecio;

    public JLabel lblTipoProducto;
    public JLabel lblMaterial;
    public JLabel lblTipoProductos;

    private ButtonGroup impermebale;
    public JRadioButton rbSiImpermeable;
    public JRadioButton rbNoImpermeable;
    private ButtonGroup rueda;
    public JRadioButton rbSiRueda;
    public JRadioButton rbNoRueda;

    public JTextField txtTamano;


    public JSlider sliderPeso;


    public DatePicker dpFechaCompra;


    public JList<Producto> listProductos;
    public DefaultListModel<Producto> listModel;

    public View() {
        setTitle("Tienda Bolsos");

        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setVisible(true);
        asignarBorrarBorderBtn();
        initComponent();

    }

    public void initComponent(){
        comboboxMarca.setModel(new DefaultComboBoxModel<Marca>(Marca.values()));
        comboboxTipoProducto.setModel(new DefaultComboBoxModel<TipoProducto>(TipoProducto.values()));
        comboboxMaterial.setModel(new DefaultComboBoxModel<Material>(Material.values()));
        comboboxFuncionalidadBolso.setModel(new DefaultComboBoxModel<Funcionalidad>(Funcionalidad.values()));
        comboboxSeguridad.setModel(new DefaultComboBoxModel<Seguridad>(Seguridad.values()));
        comboboxFuncionAdicional.setModel(new DefaultComboBoxModel<FuncionAdicional>(FuncionAdicional.values()));

        dpFechaCompra.setDate(LocalDate.now());
        dpFechaCompra.getSettings().setFormatForDatesCommonEra(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        modeloPrecio = new SpinnerNumberModel(1,0.0,2000.0,1.0);
        spnPrecio.setModel(modeloPrecio);

        impermebale = new ButtonGroup();
        impermebale.add(rbSiImpermeable);
        impermebale.add(rbNoImpermeable);

        rueda = new ButtonGroup();
        rueda.add(rbSiRueda);
        rueda.add(rbNoRueda);

        panelBolso.setVisible(false);
        panelMaleta.setVisible(false);
        panelBolsoViaje.setVisible(false);
        lblTipoProductos.setVisible(false);

        JComboBox[] combos = {comboboxMarca,comboboxMaterial,comboboxFuncionalidadBolso,comboboxSeguridad,comboboxFuncionAdicional};
        Utilities.configurarComponente(combos,false);
        JRadioButton[] radioButtons = {rbSiImpermeable,rbNoImpermeable,rbSiRueda,rbNoRueda};
        Utilities.configurarComponente(radioButtons,false);

        spnPrecio.setEnabled(false);
        dpFechaCompra.setEnabled(false);
        sliderPeso.setEnabled(false);
        txtTamano.setEnabled(false);
        comboboxTipoProducto.setSelectedIndex(-1);

        listModel = new DefaultListModel<Producto>();
        listProductos.setModel(listModel);

        panelListarProductos.setVisible(true);
        panelAnadirProducto.setVisible(false);
        panelArchivos.setVisible(false);

    }



    private void asignarBorrarBorderBtn(){
        Utilities.borrarBordeBoton(btnListarProductos);
        Utilities.borrarBordeBoton(btnAnadirProductos);
        Utilities.borrarBordeBoton(btnGestionarArchivos);
    }





}
