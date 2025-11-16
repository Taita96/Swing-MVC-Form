package gm.carlos.bolsos.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


/**
 * Clase utilitaria para manejar JSON usando Jackson.
 * Proporciona un ObjectMapper estático preconfigurado para
 * trabajar con fechas LocalDate, generar JSON legible (indentado)
 * y evitar escribir fechas como timestamps.
 * Se utiliza para importar y exportar objetos Producto.
 *
 */


public class JsonManager {

    /**
     * Mapper de Jackson preconfigurado para la aplicación.
     *
     * Permite serializar y deserializar objetos Java a JSON y viceversa, soportando {@link java.time.LocalDate}.
     */
    public static final ObjectMapper mapper = new ObjectMapper();

    // Bloque estático para configurar el ObjectMapper al cargar la clase
    static {
        mapper.registerModule(new JavaTimeModule());       // Soporte LocalDate
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // No usar timestamps
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // JSON bonito
    }


}

