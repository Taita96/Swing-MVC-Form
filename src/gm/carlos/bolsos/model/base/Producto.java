package gm.carlos.bolsos.model.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;
import gm.carlos.bolsos.model.enums.TipoProducto;

import java.time.LocalDate;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "tipoProducto",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Bolso.class, name = "BOLSO"),
        @JsonSubTypes.Type(value = Maleta.class, name = "MALETA"),
        @JsonSubTypes.Type(value = BolsoViaje.class, name = "BOLSOVIAJE")
})


public class Producto {

    private String id;
    private double precio;
    private Material material;
    private TipoProducto tipoProducto;
    private double tamano;
    private Marca marca;
    private boolean impermeable;
    private int peso;
    private LocalDate fechaCompra;

    public Producto(TipoProducto tipoProducto, double precio, Material material, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fechaCompra) {
        this.id = UUID.randomUUID().toString();
        this.tipoProducto = tipoProducto;
        this.precio = precio;
        this.material = material;
        this.tamano = tamano;
        this.marca = marca;
        this.impermeable = impermeable;
        this.peso = peso;
        this.fechaCompra = fechaCompra;
    }

    public Producto() {
        this.id = UUID.randomUUID().toString();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public double getTamano() {
        return tamano;
    }

    public void setTamano(double tamano) {
        this.tamano = tamano;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public boolean isImpermeable() {
        return impermeable;
    }

    public void setImpermeable(boolean impermeable) {
        this.impermeable = impermeable;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getId() {
        return id;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String toString() {
        return   "Tipo:" + tipoProducto + " precio: " + precio + " materia: " + material + " tamano: " + tamano +
                " marca: " + marca +
                " impermeable: " + impermeable +
                " peso: " + peso +
                " fechaCompra: " + fechaCompra;
    }
}
