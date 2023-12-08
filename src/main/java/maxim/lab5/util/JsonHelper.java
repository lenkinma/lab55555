package maxim.lab5.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import maxim.lab5.model.parent.Device;
import maxim.lab5.model.parent.DeviceWrapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class JsonHelper {

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
            e.printStackTrace();
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
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


}
