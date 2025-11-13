package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.FuncionAdicional;
import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;
import gm.carlos.bolsos.model.enums.TipoProducto;

import java.time.LocalDate;

public class BolsoViaje extends  Producto{

    private FuncionAdicional funcionAdicional;

    public BolsoViaje(TipoProducto tipoProducto, double precio, Material materia, double tamano, Marca marca, boolean impermeable, int peso, LocalDate fechaCompra, FuncionAdicional funcionAdicional) {
        super(tipoProducto, precio, materia, tamano, marca, impermeable, peso, fechaCompra);
        this.funcionAdicional = funcionAdicional;
    }

    public BolsoViaje(){

    }

    public FuncionAdicional getFuncionAdicional() {
        return funcionAdicional;
    }

    public void setFuncionAdicional(FuncionAdicional funcionAdicional) {
        this.funcionAdicional = funcionAdicional;
    }

    @Override
    public String toString() {
        return super.toString() + " funcionAdicional: " + funcionAdicional;
    }
}
