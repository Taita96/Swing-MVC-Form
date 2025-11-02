package gm.carlos.bolsos.model;

import gm.carlos.bolsos.model.enums.Funcionalidad;

import java.time.LocalDate;

public class Bolso extends Producto {

    private Funcionalidad funcionalidad;

    public Bolso(Long id, double precio, String material, double tamano, String marca,
                 boolean impermeable, double peso, LocalDate fechaCompra, Funcionalidad funcionalidad) {
        super(id, precio, material, tamano, marca, impermeable, peso, fechaCompra);
        this.funcionalidad = funcionalidad;
    }

    public Funcionalidad getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(Funcionalidad funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    @Override
    public String toString() {
        return super.toString() + ", funcionalidad=" + funcionalidad + " }";
    }
}
