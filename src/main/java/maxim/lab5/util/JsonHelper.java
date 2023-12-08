package maxim.lab5.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import maxim.lab5.model.parent.Device;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static maxim.lab5.util.Constants.formatter;

/**
 * @author Kirill Emelyanov
 */

public class JsonHelper {

    private JsonHelper() {
    }

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static void saveJSON(List<Device> devices) {
        try {
            mapper.writeValue(new File("data.json"), devices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
