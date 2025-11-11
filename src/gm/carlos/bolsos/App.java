package gm.carlos.bolsos;

import gm.carlos.bolsos.controller.Controller;
import gm.carlos.bolsos.model.ProductoModel;
import gm.carlos.bolsos.view.View;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class App {

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    View vista = new View();
                    ProductoModel model = new ProductoModel();
                    new Controller(model,vista);
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
