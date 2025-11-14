package gm.carlos.bolsos.model;

import com.fasterxml.jackson.core.type.TypeReference;
import gm.carlos.bolsos.model.base.Bolso;
import gm.carlos.bolsos.model.base.BolsoViaje;
import gm.carlos.bolsos.model.base.Maleta;
import gm.carlos.bolsos.model.base.Producto;
import gm.carlos.bolsos.model.enums.*;
import gm.carlos.bolsos.utilities.Utilities;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;





public class ProductoModel {

    private List<Producto> productos;

    public ProductoModel() {

        this.productos = new ArrayList<>();
    }

    public void altaBolso(TipoProducto tipoProducto, double precio, Material material, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fecha, Funcionalidad funcionalidad){
        Producto bolso = new Bolso(tipoProducto,precio, material, tamano, marca, impermeable, peso, fecha, funcionalidad);

        if(existProduct(bolso.getId())){
            Utilities.showMessage("Ya exite este producto en la lista","Producto Existente","ERROR");
            return;
        }

        productos.add(bolso);
    }

    public void altaBolsoViaje(TipoProducto tipoProducto, double precio, Material material, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fechaCompra, FuncionAdicional funcionAdicional){
        Producto bolsoViaje = new BolsoViaje(tipoProducto,precio,material,tamano,marca,impermeable,peso,fechaCompra,funcionAdicional);

        if(existProduct(bolsoViaje.getId())){
            Utilities.showMessage("Ya exite este producto en la lista","Producto Existente","ERROR");
            return;
        }

        productos.add(bolsoViaje);
    }

    public void altaMaleta(TipoProducto tipoProducto, double precio, Material material, double tamano, Marca marca, boolean impeableSiSeleccionado, int peso, LocalDate fecha, Seguridad seguridad,boolean ruedas){
        Producto maleta = new Maleta(tipoProducto,precio, material, tamano, marca, impeableSiSeleccionado, peso, fecha, seguridad, ruedas);

        if(existProduct(maleta.getId())){
            Utilities.showMessage("Ya exite este producto en la lista","Producto Existente","ERROR");
            return;
        }

        productos.add(maleta);
    }

    private boolean existProduct(String id) {
        if (id == null) return false;

        for (Producto producto : productos) {
            String pid = producto.getId();
            if (pid != null && pid.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProducto(Producto producto){
        Iterator<Producto> ite = productos.iterator();

        while (ite.hasNext()){

            Producto productoActual = ite.next();

            if(productoActual.getId().equals(producto.getId())){
                ite.remove();
                return true;
            }

        }

        return false;
    }

    public void actualizarReserva(Producto productoActualizado) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId().equals(productoActualizado.getId())) {
                productos.set(i, productoActualizado);
                return;
            }
        }
    }

    public List<Producto> getProductos() {
        return productos;
    }


    public void exportarXML(File fichero){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder  = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Utilities.showMessage("Error al configurar el analizador DOM.\n No se pudo inicializar el parser para trabajar con XML.","Error de configuración XML","ERROR");
        }

        DOMImplementation dom = builder.getDOMImplementation();
        Document document = dom.createDocument(null,"xml",null);

        Element root = document.createElement("Productos");
        document.getDocumentElement().appendChild(root);

        Element nodoProducto = null;
        Element nodoDatos = null;
        Text text = null;

        for (Producto producto: productos){
            if(producto instanceof Bolso){
                nodoProducto = document.createElement("Bolso");
            }else if(producto instanceof BolsoViaje){
                nodoProducto = document.createElement("BolsoViaje");
            }else if(producto instanceof Maleta){
                nodoProducto = document.createElement("Maleta");
            }

            root.appendChild(nodoProducto);

            nodoDatos = document.createElement("tipo-producto");
            nodoProducto.appendChild(nodoDatos);
            text = document.createTextNode(String.valueOf(producto.getTipoProducto()));
            nodoDatos.appendChild(text);


            nodoDatos = document.createElement("fecha-compra");
            nodoProducto.appendChild(nodoDatos);
            text = document.createTextNode(producto.getFechaCompra().toString());
            nodoDatos.appendChild(text);

            nodoDatos = document.createElement("precio");
            nodoProducto.appendChild(nodoDatos);
            text = document.createTextNode(String.valueOf(producto.getPrecio()));
            nodoDatos.appendChild(text);

            nodoDatos = document.createElement("material");
            nodoProducto.appendChild(nodoDatos);
            text = document.createTextNode(String.valueOf(producto.getMateria()));
            nodoDatos.appendChild(text);

            nodoDatos = document.createElement("tamano");
            nodoProducto.appendChild(nodoDatos);
            text = document.createTextNode(String.valueOf(producto.getTamano()));
            nodoDatos.appendChild(text);

            nodoDatos = document.createElement("marca");
            nodoProducto.appendChild(nodoDatos);
            text = document.createTextNode(String.valueOf(producto.getMarca()));
            nodoDatos.appendChild(text);

            nodoDatos = document.createElement("peso");
            nodoProducto.appendChild(nodoDatos);
            text = document.createTextNode(String.valueOf(producto.getPeso()));
            nodoDatos.appendChild(text);

            nodoDatos = document.createElement("impermeable");
            nodoProducto.appendChild(nodoDatos);
            text = document.createTextNode(String.valueOf(producto.isImpermeable()));
            nodoDatos.appendChild(text);

            if(producto instanceof Bolso){
                nodoDatos = document.createElement("funcionalidad");
                nodoProducto.appendChild(nodoDatos);
                text = document.createTextNode(String.valueOf(((Bolso) producto).getFuncionalidad()));
                nodoDatos.appendChild(text);
            }else if(producto instanceof BolsoViaje){
                nodoDatos = document.createElement("funcion_adicional");
                nodoProducto.appendChild(nodoDatos);
                text = document.createTextNode(String.valueOf(((BolsoViaje) producto).getFuncionAdicional()));
                nodoDatos.appendChild(text);
            }else if(producto instanceof Maleta){
                nodoDatos = document.createElement("seguridad");
                nodoProducto.appendChild(nodoDatos);
                text = document.createTextNode(String.valueOf(((Maleta) producto).getSeguridad()));
                nodoDatos.appendChild(text);

                nodoDatos = document.createElement("tiene-rueda");
                nodoProducto.appendChild(nodoDatos);
                text = document.createTextNode(String.valueOf(((Maleta) producto).isRuedas()));
                nodoDatos.appendChild(text);
            }
        }

        Source source = new DOMSource(document);
        Result result = new StreamResult(fichero);

        Transformer transformer = null;

        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source,result);

            Utilities.showMessage("Exportación completada correctamente.",
                    "Éxito", "INFORMATION");
        } catch (TransformerConfigurationException e) {
            Utilities.showMessage(
                    "Error al configurar el transformador XML.\n" +
                            "No se pudo preparar la transformación del documento.",
                    "Error de configuración XML",
                    "ERROR"
            );
        } catch (TransformerException e) {
            Utilities.showMessage(
                    "Error al transformar o guardar el archivo XML.\n" +
                            "Verifique que la ruta y el archivo sean válidos.",
                    "Error al exportar XML",
                    "ERROR"
            );
        }
    }

    public void importarXML(File fichero){
        productos = new ArrayList<Producto>();
        Bolso nuevoBolso = null;
        BolsoViaje nuevoBolsoViaje = null;
        Maleta nuevoMaleta = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Utilities.showMessage(
                    "Error al configurar el parser XML.\nNo se pudo inicializar el analizador DOM.",
                    "Error de configuración",
                    "ERROR"
            );
            return;
        }
        Document document = null;
        try {
             document = builder.parse(fichero);
        } catch (SAXException e) {
            Utilities.showMessage(
                    "El archivo XML está dañado o tiene un formato incorrecto.\n" +
                            "Verifique que el archivo sea un XML válido.",
                    "Error de formato XML",
                    "ERROR"
            );
            return;
        } catch (IOException e) {
            Utilities.showMessage("Error al leer el archivo XML.", "Error", "ERROR");
            return;
        }

        NodeList listElement = document.getElementsByTagName("*");

        for(int i = 0; i < listElement.getLength();i++){
            Element nodoProducto = (Element) listElement.item(i);

            if(nodoProducto.getTagName().equals("Bolso")){
                nuevoBolso = new Bolso();

                nuevoBolso.setTipoProducto(TipoProducto.valueOf(nodoProducto.getChildNodes().item(0).getTextContent()));
                nuevoBolso.setFechaCompra(LocalDate.parse(nodoProducto.getChildNodes().item(1).getTextContent()));
                nuevoBolso.setPrecio(Double.parseDouble(nodoProducto.getChildNodes().item(2).getTextContent()));
                nuevoBolso.setMateria(Material.valueOf(nodoProducto.getChildNodes().item(3).getTextContent()));
                nuevoBolso.setTamano(Double.parseDouble(nodoProducto.getChildNodes().item(4).getTextContent()));
                nuevoBolso.setMarca(Marca.valueOf(nodoProducto.getChildNodes().item(5).getTextContent()));
                nuevoBolso.setPeso(Integer.parseInt(nodoProducto.getChildNodes().item(6).getTextContent()));
                nuevoBolso.setImpermeable(Boolean.parseBoolean(nodoProducto.getChildNodes().item(7).getTextContent()));

                nuevoBolso.setFuncionalidad(Funcionalidad.valueOf(nodoProducto.getChildNodes().item(8).getTextContent()));
                productos.add(nuevoBolso);
            } else if(nodoProducto.getTagName().equals("BolsoViaje")){
                nuevoBolsoViaje = new BolsoViaje();

                nuevoBolsoViaje.setTipoProducto(TipoProducto.valueOf(nodoProducto.getChildNodes().item(0).getTextContent()));
                nuevoBolsoViaje.setFechaCompra(LocalDate.parse(nodoProducto.getChildNodes().item(1).getTextContent()));
                nuevoBolsoViaje.setPrecio(Double.parseDouble(nodoProducto.getChildNodes().item(2).getTextContent()));
                nuevoBolsoViaje.setMateria(Material.valueOf(nodoProducto.getChildNodes().item(3).getTextContent()));
                nuevoBolsoViaje.setTamano(Double.parseDouble(nodoProducto.getChildNodes().item(4).getTextContent()));
                nuevoBolsoViaje.setMarca(Marca.valueOf(nodoProducto.getChildNodes().item(5).getTextContent()));
                nuevoBolsoViaje.setPeso(Integer.parseInt(nodoProducto.getChildNodes().item(6).getTextContent()));
                nuevoBolsoViaje.setImpermeable(Boolean.parseBoolean(nodoProducto.getChildNodes().item(7).getTextContent()));

                nuevoBolsoViaje.setFuncionAdicional(FuncionAdicional.valueOf(nodoProducto.getChildNodes().item(8).getTextContent()));
                productos.add(nuevoBolsoViaje);
            } else if(nodoProducto.getTagName().equals("Maleta")){
                nuevoMaleta = new Maleta();

                nuevoMaleta.setTipoProducto(TipoProducto.valueOf(nodoProducto.getChildNodes().item(0).getTextContent()));
                nuevoMaleta.setFechaCompra(LocalDate.parse(nodoProducto.getChildNodes().item(1).getTextContent()));
                nuevoMaleta.setPrecio(Double.parseDouble(nodoProducto.getChildNodes().item(2).getTextContent()));
                nuevoMaleta.setMateria(Material.valueOf(nodoProducto.getChildNodes().item(3).getTextContent()));
                nuevoMaleta.setTamano(Double.parseDouble(nodoProducto.getChildNodes().item(4).getTextContent()));
                nuevoMaleta.setMarca(Marca.valueOf(nodoProducto.getChildNodes().item(5).getTextContent()));
                nuevoMaleta.setPeso(Integer.parseInt(nodoProducto.getChildNodes().item(6).getTextContent()));
                nuevoMaleta.setImpermeable(Boolean.parseBoolean(nodoProducto.getChildNodes().item(7).getTextContent()));

                nuevoMaleta.setSeguridad(Seguridad.valueOf(nodoProducto.getChildNodes().item(8).getTextContent()));
                nuevoMaleta.setRuedas(Boolean.parseBoolean(nodoProducto.getChildNodes().item(9).getTextContent()));
                productos.add(nuevoMaleta);
            }
        }

    }

    public List<Producto> importarJSON(File fichero) {
        try {
            return JsonManager.mapper.readValue(
                    fichero,
                    new TypeReference<List<Producto>>() {}
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    public void exportarJSON(File fichero) {
        try {
            JsonManager.mapper.writeValue(fichero, productos);
            System.out.println("Productos exportados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
