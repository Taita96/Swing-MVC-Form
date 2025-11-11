package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.Funcionalidad;
import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;

import java.time.LocalDate;

public class Bolso extends Producto {

    private Funcionalidad funcionalidad;

    public Bolso(double precio, Material materia, double tamano, Marca marca, boolean impermeable, double peso, LocalDate fechaCompra, Funcionalidad funcionalidad) {
        super(precio, materia, tamano, marca, impermeable, peso, fechaCompra);
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
