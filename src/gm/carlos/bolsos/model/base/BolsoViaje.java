package gm.carlos.bolsos.model.base;

import gm.carlos.bolsos.model.enums.FuncionAdicional;
import gm.carlos.bolsos.model.enums.Marca;
import gm.carlos.bolsos.model.enums.Material;

import java.time.LocalDate;

public class BolsoViaje extends  Producto{

    private FuncionAdicional funcionAdicional;

    public BolsoViaje(double precio, Material materia, double tamano, Marca marca, boolean impermeable, double peso, LocalDate fechaCompra, FuncionAdicional funcionAdicional) {
        super(precio, materia, tamano, marca, impermeable, peso, fechaCompra);
        this.funcionAdicional = funcionAdicional;
    }

    public FuncionAdicional getFuncionAdicional() {
        return funcionAdicional;
    }

    public void setFuncionAdicional(FuncionAdicional funcionAdicional) {
        this.funcionAdicional = funcionAdicional;
    }

    @Override
    public String toString() {
        return "BolsoViaje{" +
                "funcionAdicional=" + funcionAdicional +
                '}';
    }
}
