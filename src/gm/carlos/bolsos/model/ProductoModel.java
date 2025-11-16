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


/**
 * Modelo de datos que gestiona la colección de {@link Producto} en la aplicación.
 *
 * Proporciona métodos para:
 *
 * Agregar, eliminar y actualizar productos.
 * Exportar e importar productos en formatos XML y JSON.
 * Validar la existencia de productos por ID.
 *
 *
 * Maneja las diferentes subclases de Producto: {@link Bolso}, {@link BolsoViaje} y {@link Maleta}.
 * Además, muestra mensajes informativos y de error mediante {@link Utilities}.
 *
 */

public class ProductoModel {

    /** Lista interna que contiene los productos registrados. */
    private List<Producto> productos;

    /**
     * Constructor que inicializa la lista de productos.
     */
    public ProductoModel() {

        this.productos = new ArrayList<>();
    }

    /**
     * Agrega un nuevo producto a la lista si no existe un producto con el mismo ID.
     *
     * @param producto Producto a agregar.
     */
    public void altaProducto(Producto producto) {
        if (existProducto(producto.getId())) {
            Utilities.mostrarMensaje("Ya existe este producto en la lista", "Producto Existente", "ERROR");
            return;
        }
        productos.add(producto);
    }

    /**
     * Verifica si un producto con el mismo ID ya existe en la lista.
     *
     * @param id ID del producto a verificar.
     * @return {true} si el producto existe; {false} en caso contrario.
     */
    private boolean existProducto(String id) {
        if (id == null) return false;
        for (Producto producto : productos) {
            if (producto.getId() != null && producto.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un producto de la lista por su ID.
     *
     * @param producto Producto a eliminar.
     * @return {true} si se eliminó correctamente; {false} si no se encontró.
     */

    public boolean eliminarProducto(Producto producto) {
        Iterator<Producto> ite = productos.iterator();

        while (ite.hasNext()) {

            Producto productoActual = ite.next();

            if (productoActual.getId().equals(producto.getId())) {
                ite.remove();
                Utilities.mostrarMensaje("Producto Eliminado Exitosamente.", "Información", "INFORMATION");
                return true;
            }

        }

        return false;
    }

    /**
     * Actualiza los datos de un producto existente según su ID.
     *
     * @param productoActualizado Producto con los datos actualizados.
     */
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


    /**
     * Exporta los productos a un archivo XML.
     *
     * @param fichero Archivo de destino.
     */
    public void exportarXML(File fichero) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = factory.newDocumentBuilder();

            DOMImplementation dom = builder.getDOMImplementation();
            Document document = dom.createDocument(null, "xml", null);

            Element root = document.createElement("Productos");
            document.getDocumentElement().appendChild(root);

            Element nodoProducto = null;
            Element nodoDatos = null;
            Text text = null;

            for (Producto producto : productos) {

                if (producto instanceof Bolso) {
                    nodoProducto = document.createElement("Bolso");
                } else if (producto instanceof BolsoViaje) {
                    nodoProducto = document.createElement("BolsoViaje");
                } else if (producto instanceof Maleta) {
                    nodoProducto = document.createElement("Maleta");
                }

                root.appendChild(nodoProducto);


                crearElemento(document, nodoProducto, nodoDatos, text, "tipo-producto", String.valueOf(producto.getTipoProducto()));
                crearElemento(document, nodoProducto, nodoDatos, text, "fecha-compra", producto.getFechaCompra().toString());
                crearElemento(document, nodoProducto, nodoDatos, text, "precio", String.valueOf(producto.getPrecio()));
                crearElemento(document, nodoProducto, nodoDatos, text, "material", String.valueOf(producto.getMaterial()));
                crearElemento(document, nodoProducto, nodoDatos, text, "tamano", String.valueOf(producto.getTamano()));
                crearElemento(document, nodoProducto, nodoDatos, text, "marca", String.valueOf(producto.getMarca()));
                crearElemento(document, nodoProducto, nodoDatos, text, "peso", String.valueOf(producto.getPeso()));
                crearElemento(document, nodoProducto, nodoDatos, text, "impermeable", String.valueOf(producto.isImpermeable()));

                if (producto instanceof Bolso) {
                    crearElemento(document, nodoProducto, nodoDatos, text, "funcionalidad-bolso", String.valueOf(((Bolso) producto).getFuncionalidadBolso()));
                } else if (producto instanceof BolsoViaje) {
                    crearElemento(document, nodoProducto, nodoDatos, text, "funcionalidad-bolsoViaje", String.valueOf(((BolsoViaje) producto).getFuncionalidadBolsoViaje()));
                } else if (producto instanceof Maleta) {
                    crearElemento(document, nodoProducto, nodoDatos, text, "seguridad", String.valueOf(((Maleta) producto).getSeguridadMaleta()));
                    crearElemento(document, nodoProducto, nodoDatos, text, "tiene-rueda", String.valueOf(((Maleta) producto).hasRuedas()));

                }
            }

            Source source = new DOMSource(document);
            Result result = new StreamResult(fichero);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

            Utilities.mostrarMensaje("Archivo XML exportado exitosamente", "INFORMACION", "INFORMATION");

        } catch (ParserConfigurationException e) {
            Utilities.mostrarMensaje("Error al configurar el analizador DOM.\n No se pudo inicializar el parser para trabajar con XML.", "Error de configuración XML", "ERROR");

        } catch (TransformerConfigurationException e) {
            Utilities.mostrarMensaje(
                    "Error al configurar el transformador XML.\n" +
                            "No se pudo preparar la transformación del documento.",
                    "Error de configuración XML",
                    "ERROR"
            );
        } catch (TransformerException e) {
            Utilities.mostrarMensaje(
                    "Error al transformar o guardar el archivo XML.\n" +
                            "Verifique que la ruta y el archivo sean válidos.",
                    "Error al exportar XML",
                    "ERROR"
            );
        }
    }

    /**
     * Crea un elemento XML dentro de un nodo producto.
     *
     * @param document Documento XML.
     * @param nodoProducto Nodo padre del producto.
     * @param nodoDatos Nodo hijo a crear.
     * @param text Nodo de texto a agregar.
     * @param tagName Nombre de la etiqueta.
     * @param textoNodo Contenido del nodo.
     */
    private void crearElemento(Document document, Element nodoProducto, Element nodoDatos, Text text, String tagName, String textoNodo) {
        nodoDatos = document.createElement(tagName);
        nodoProducto.appendChild(nodoDatos);
        text = document.createTextNode(textoNodo);
        nodoDatos.appendChild(text);
    }

    /**
     * Importa productos desde un archivo XML.
     *
     * @param fichero Archivo XML de origen.
     */
    public void importarXML(File fichero) {
        productos = new ArrayList<Producto>();
        Bolso nuevoBolso = null;
        BolsoViaje nuevoBolsoViaje = null;
        Maleta nuevoMaleta = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document document = null;

        try {
            builder = factory.newDocumentBuilder();

            document = builder.parse(fichero);

            NodeList listElement = document.getElementsByTagName("*");

            boolean esCompletos = false;
            for (int i = 0; i < listElement.getLength(); i++) {
                Element nodoProducto = (Element) listElement.item(i);

                switch (nodoProducto.getTagName()) {
                    case "Bolso":
                        nuevoBolso = new Bolso();

                        esCompletos = configurarElementosComunes(nuevoBolso, nodoProducto);

                        if (!esCompletos) {
                            return;
                        }

                        String funcionalidadBolso = nodoProducto.getChildNodes().item(8).getTextContent();

                        if (funcionalidadBolso.isEmpty()) {
                            Utilities.mostrarMensaje("Error al leer el archivo XML, hay campos vacios en el archivo.", "Error", "ERROR");
                        }


                        nuevoBolso.setFuncionalidadBolso(FuncionalidadBolso.valueOf(funcionalidadBolso));


                        productos.add(nuevoBolso);
                        break;
                    case "BolsoViaje":
                        nuevoBolsoViaje = new BolsoViaje();

                        esCompletos = configurarElementosComunes(nuevoBolsoViaje, nodoProducto);

                        if (!esCompletos) {
                            return;
                        }

                        String funcionalidadBolsoViaje = nodoProducto.getChildNodes().item(8).getTextContent();

                        if (funcionalidadBolsoViaje.isEmpty()) {
                            Utilities.mostrarMensaje("Error al leer el archivo XML, hay campos vacios en el archivo.", "Error", "ERROR");
                        }

                        nuevoBolsoViaje.setFuncionalidadBolsoViaje(FuncionalidadBolsoViaje.valueOf(funcionalidadBolsoViaje));
                        productos.add(nuevoBolsoViaje);
                        break;
                    case "Maleta":
                        nuevoMaleta = new Maleta();


                        esCompletos = configurarElementosComunes(nuevoMaleta, nodoProducto);


                        if (!esCompletos) {
                            return;
                        }

                        String Seguridad = nodoProducto.getChildNodes().item(8).getTextContent();
                        String tieneRuedas = nodoProducto.getChildNodes().item(9).getTextContent();

                        if (Seguridad.isEmpty() || tieneRuedas.isEmpty()) {
                            Utilities.mostrarMensaje("Error al leer el archivo XML, hay campos vacios en el archivo.", "Error", "ERROR");
                        }

                        nuevoMaleta.setSeguridadMaleta(SeguridadMaleta.valueOf(Seguridad));
                        nuevoMaleta.setRuedas(Boolean.parseBoolean(tieneRuedas));
                        productos.add(nuevoMaleta);
                        break;
                }


            }

            Utilities.mostrarMensaje("Archivo XML importado exitosamente", "INFORMACION", "INFORMATION");

        } catch (ParserConfigurationException e) {
            Utilities.mostrarMensaje(
                    "Error al configurar el parser XML.\nNo se pudo inicializar el analizador DOM.",
                    "Error de configuración",
                    "ERROR"
            );
        } catch (SAXException e) {
            Utilities.mostrarMensaje(
                    "El archivo XML está dañado o tiene un formato incorrecto.\n" +
                            "Verifique que el archivo sea un XML válido.",
                    "Error de formato XML",
                    "ERROR"
            );
        } catch (IOException e) {
            Utilities.mostrarMensaje("Error al leer el archivo XML.", "Error", "ERROR");
        }

    }


    /**
     * Configura los elementos comunes de un producto desde un nodo XML.
     *
     * @param producto Producto a configurar.
     * @param nodoProducto Nodo XML del producto.
     * @return {true} si todos los campos están completos; {false} si hay campos vacíos.
     */
    private boolean configurarElementosComunes(Producto producto, Element nodoProducto) {

        String tipoProducto = nodoProducto.getChildNodes().item(0).getTextContent();
        String fechaCompra = nodoProducto.getChildNodes().item(1).getTextContent();
        String precio = nodoProducto.getChildNodes().item(2).getTextContent();
        String material = nodoProducto.getChildNodes().item(3).getTextContent();
        String tamano = nodoProducto.getChildNodes().item(4).getTextContent();
        String marca = nodoProducto.getChildNodes().item(5).getTextContent();
        String peso = nodoProducto.getChildNodes().item(6).getTextContent();
        String tieneImpermeable = nodoProducto.getChildNodes().item(7).getTextContent();

        if (tipoProducto.isEmpty() || fechaCompra.isEmpty() || precio.isEmpty() || material.isEmpty()
                || tamano.isEmpty() || marca.isEmpty() || peso.isEmpty() || tieneImpermeable.isEmpty()) {
            Utilities.mostrarMensaje("Error al leer el archivo XML, hay campos vacios en el archivo.", "Error", "ERROR");
            return false;
        }

        producto.setTipoProducto(TipoProducto.valueOf(tipoProducto));
        producto.setFechaCompra(LocalDate.parse(fechaCompra));
        producto.setPrecio(Double.parseDouble(precio));
        producto.setMaterial(Material.valueOf(material));
        producto.setTamano(Double.parseDouble(tamano));
        producto.setMarca(Marca.valueOf(marca));
        producto.setPeso(Integer.parseInt(peso));
        producto.setImpermeable(Boolean.parseBoolean(tieneImpermeable));

        return true;
    }

    /**
     * Importa productos desde un archivo JSON.
     *
     * @param fichero Archivo JSON de origen.
     */
    public void importarJSON(File fichero) {
        try {

            List<Producto> nuevaLista = JsonManager.mapper.readValue(
                    fichero,
                    new TypeReference<List<Producto>>() {
                    }
            );

            productos.addAll(nuevaLista);

            Utilities.mostrarMensaje("Archivo JSON importado exitosamente", "INFORMACION", "INFORMATION");
        } catch (Exception e) {
            Utilities.mostrarMensaje(
                    "El archivo JSON está dañado o tiene un formato incorrecto.\n" +
                            "Verifique que el archivo sea un JSON válido.",
                    "Error de formato JSON",
                    "ERROR"
            );
        }
    }

    /**
     * Exporta los productos a un archivo JSON.
     *
     * @param fichero Archivo JSON de destino.
     */
    public void exportarJSON(File fichero) {
        try {
            JsonManager.mapper.writeValue(fichero, productos);
            Utilities.mostrarMensaje("Archivo JSON exportado exitosamente", "INFORMACION", "INFORMATION");
        } catch (Exception e) {
            Utilities.mostrarMensaje(
                    "Error al transformar o guardar el archivo JSON.\n" +
                            "Verifique que la ruta y el archivo sean válidos.",
                    "Error al exportar JSON",
                    "ERROR"
            );
        }
    }

}
