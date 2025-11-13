package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.Funcionalidad;
import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;
import gm.carlos.bolsos.model.enums.TipoProducto;

import java.time.LocalDate;

public class Bolso extends Producto {

    private Funcionalidad funcionalidad;

    public Bolso(TipoProducto tipoProducto, double precio, Material materia, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fechaCompra, Funcionalidad funcionalidad) {
        super(tipoProducto, precio, materia, tamano, marca, impermeable, peso, fechaCompra);
        this.funcionalidad = funcionalidad;
    }

    public Bolso(){

    }

    public Funcionalidad getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(Funcionalidad funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    @Override
    public String toString() {
        return super.toString() + " funcionalidad: " + funcionalidad + " }";
    }
}
