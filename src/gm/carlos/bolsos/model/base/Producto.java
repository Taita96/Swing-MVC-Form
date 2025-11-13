package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;
import gm.carlos.bolsos.model.enums.TipoProducto;

import java.time.LocalDate;
import java.util.UUID;

public class Producto {

    private String id;
    private double precio;
    private Material materia;
    private TipoProducto tipoProducto;
    private double tamano;
    private Marca marca;
    private boolean impermeable;
    private int peso;
    private LocalDate fechaCompra;

    public Producto(TipoProducto tipoProducto,double precio, Material materia, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fechaCompra) {
        this.id = UUID.randomUUID().toString();
        this.tipoProducto = tipoProducto;
        this.precio = precio;
        this.materia = materia;
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

    public Material getMateria() {
        return materia;
    }

    public void setMateria(Material materia) {
        this.materia = materia;
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

    public void setId(String id) {
        this.id = id;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String toString() {
        return   "Tipo:" + tipoProducto + " precio: " + precio + " materia: " + materia + " tamano: " + tamano +
                " marca: " + marca +
                " impermeable: " + impermeable +
                " peso: " + peso +
                " fechaCompra: " + fechaCompra;
    }
}
