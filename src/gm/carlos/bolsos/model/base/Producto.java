package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;

import java.time.LocalDate;
import java.util.UUID;

public class Producto {

    private double precio;
    private Material materia;
    private double tamano;
    private Marca marca;
    private boolean impermeable;
    private double peso;
    private LocalDate fechaCompra;

    public Producto(double precio, Material materia, double tamano, Marca marca, boolean impermeable, double peso, LocalDate fechaCompra) {
        this.precio = precio;
        this.materia = materia;
        this.tamano = tamano;
        this.marca = marca;
        this.impermeable = impermeable;
        this.peso = peso;
        this.fechaCompra = fechaCompra;
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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "precio=" + precio +
                ", materia=" + materia +
                ", tamano=" + tamano +
                ", marca=" + marca +
                ", impermeable=" + impermeable +
                ", peso=" + peso +
                ", fechaCompra=" + fechaCompra +
                '}';
    }
}
