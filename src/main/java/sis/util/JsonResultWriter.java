package sis.util;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import sis.users.RoadUser;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class JsonResultWriter extends ResultWriter {
    private final JsonGenerator generator;
    private final Logger logger =  Logger.getLogger("soutLogger");

    public JsonResultWriter(String filename) throws IOException {
        File file = new File(filename);
        JsonFactory factory = new JsonFactory();
        this.generator = factory.createJsonGenerator(file, JsonEncoding.UTF8);
        this.generator.useDefaultPrettyPrinter();
    }

    @Override
    public void open() throws IOException {
        generator.writeStartObject();

        generator.writeFieldName("stepStatuses");
        generator.writeStartArray();
    }

    @Override
    public void startStep() throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("leftRoadUsers");
        generator.writeStartArray();
    }

    @Override
    public void onIntersectionExit(RoadUser user) {
        try {
            generator.writeString(user.getId());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not write to Json: " + e.getMessage());
        }
    }

    @Override
    public void endStep() throws IOException {
        generator.writeEndArray();
        generator.writeEndObject();
    }

    @Override
    public void close() throws IOException {
        generator.writeEndArray();

        generator.writeEndObject();
        generator.close();
    }

}
