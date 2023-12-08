package maxim.lab5.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import maxim.lab5.model.parent.Device;
import maxim.lab5.model.parent.DeviceWrapper;
import maxim.lab5.storage.DeviceStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class JsonHelper {

    private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);

    private JsonHelper() {
    }

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static void saveJSON(List<Device> devices) {
        try {
            DeviceWrapper wrapper = new DeviceWrapper(devices);
            mapper.writeValue(new File("data.json"), wrapper);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл.");
            log.error("Ошибка при записи в файл.");
            log.error(e.getMessage());
        }
    }

    public static List<Device> readJSON() {
        return readFromJson();
    }

    public static List<Device> readFromJson() {
        try {
            DeviceWrapper devices = mapper.readValue(new File("data.json"), DeviceWrapper.class);
            return devices.getDevices();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении из файла.");
            log.error("Ошибка при чтении данных из файла. Проверьте корректность полей и их значений," +
                    " а также наличие файла.");
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }


}
