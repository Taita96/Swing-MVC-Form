package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.FuncionalidadBolsoViaje;
import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;
import gm.carlos.bolsos.model.enums.TipoProducto;

import java.time.LocalDate;

public class BolsoViaje extends  Producto{

    private FuncionalidadBolsoViaje funcionalidadBolsoViaje;

    public BolsoViaje(TipoProducto tipoProducto, double precio, Material material, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fechaCompra, FuncionalidadBolsoViaje funcionalidadBolsoViaje) {
        super(tipoProducto, precio, material, tamano, marca, impermeable, peso, fechaCompra);
        this.funcionalidadBolsoViaje = funcionalidadBolsoViaje;
    }

    public BolsoViaje(){

    }

    public FuncionalidadBolsoViaje getFuncionalidadBolsoViaje() {
        return funcionalidadBolsoViaje;
    }

    public void setFuncionalidadBolsoViaje(FuncionalidadBolsoViaje funcionalidadBolsoViaje) {
        this.funcionalidadBolsoViaje = funcionalidadBolsoViaje;
    }

    @Override
    public String toString() {
        return super.toString() + " funcionAdicional: " + funcionalidadBolsoViaje;
    }
}
