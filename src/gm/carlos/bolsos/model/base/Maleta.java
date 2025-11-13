package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;
import gm.carlos.bolsos.model.enums.Seguridad;
import gm.carlos.bolsos.model.enums.TipoProducto;

import java.time.LocalDate;

public class Maleta extends Producto {

    private Seguridad seguridad;
    private boolean ruedas;


    public Maleta(TipoProducto tipoProducto, double precio, Material materia, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fechaCompra, Seguridad seguridad,boolean ruedas) {
        super(tipoProducto, precio, materia, tamano, marca, impermeable, peso, fechaCompra);
        this.seguridad = seguridad;
        this.ruedas = ruedas;
    }

    public Maleta(){

    }

    public Seguridad getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(Seguridad seguridad) {
        this.seguridad = seguridad;
    }

    public boolean isRuedas() {
        return ruedas;
    }

    public void setRuedas(boolean ruedas) {
        this.ruedas = ruedas;
    }

    @Override
    public String toString() {
        return super.toString() +
                " seguridad: " + seguridad;
    }
}
