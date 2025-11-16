package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.FuncionalidadBolso;
import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;
import gm.carlos.bolsos.model.enums.TipoProducto;

import java.time.LocalDate;

public class Bolso extends Producto {

    private FuncionalidadBolso funcionalidadBolso;

    public Bolso(TipoProducto tipoProducto, double precio, Material material, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fechaCompra, FuncionalidadBolso funcionalidadBolso) {
        super(tipoProducto, precio, material, tamano, marca, impermeable, peso, fechaCompra);
        this.funcionalidadBolso = funcionalidadBolso;
    }

    public Bolso(){

    }

    public FuncionalidadBolso getFuncionalidadBolso() {
        return funcionalidadBolso;
    }

    public void setFuncionalidadBolso(FuncionalidadBolso funcionalidadBolso) {
        this.funcionalidadBolso = funcionalidadBolso;
    }

    @Override
    public String toString() {
        return super.toString() + " funcionalidad: " + funcionalidadBolso;
    }
}
