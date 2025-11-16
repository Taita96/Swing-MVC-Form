package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;
import gm.carlos.bolsos.model.enums.SeguridadMaleta;
import gm.carlos.bolsos.model.enums.TipoProducto;

import java.time.LocalDate;

public class Maleta extends Producto {

    private SeguridadMaleta seguridadMaleta;
    private boolean ruedas;


    public Maleta(TipoProducto tipoProducto, double precio, Material material, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fechaCompra, SeguridadMaleta seguridadMaleta, boolean ruedas) {
        super(tipoProducto, precio, material, tamano, marca, impermeable, peso, fechaCompra);
        this.seguridadMaleta = seguridadMaleta;
        this.ruedas = ruedas;
    }

    public Maleta(){

    }

    public SeguridadMaleta getSeguridadMaleta() {
        return seguridadMaleta;
    }

    public void setSeguridadMaleta(SeguridadMaleta seguridadMaleta) {
        this.seguridadMaleta = seguridadMaleta;
    }

    public boolean hasRuedas() {
        return ruedas;
    }

    public void setRuedas(boolean ruedas) {
        this.ruedas = ruedas;
    }

    @Override
    public String toString() {
        return super.toString() +
                " seguridad: " + seguridadMaleta;
    }
}
