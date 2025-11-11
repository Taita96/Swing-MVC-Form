package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;
import gm.carlos.bolsos.model.enums.Seguridad;

import java.time.LocalDate;

public class Maleta extends Producto {

    private Seguridad seguridad;
    private boolean ruedas;


    public Maleta(double precio, Material materia, double tamano, Marca marca, boolean impermeable, double peso, LocalDate fechaCompra, Seguridad seguridad, boolean ruedas) {
        super(precio, materia, tamano, marca, impermeable, peso, fechaCompra);
        this.seguridad = seguridad;
        this.ruedas = ruedas;
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
        return "Maleta{" +
                "seguridad=" + seguridad +
                '}';
    }
}
