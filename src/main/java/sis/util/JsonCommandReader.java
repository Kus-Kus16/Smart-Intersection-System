package sis.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sis.simulation.commands.Command;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

public class JsonCommandReader extends CommandReader {
    private final JsonParser parser;
    private final ObjectMapper mapper;

    public JsonCommandReader(String filename) throws IOException {
        File file = new File(filename);
        JsonFactory factory = new JsonFactory();
        this.parser = factory.createJsonParser(file);
        this.mapper = new ObjectMapper();
    }

    @Override
    public void open() throws IOException {
        while (!parser.isClosed()) {
            JsonToken token = parser.nextToken();

            if (JsonToken.FIELD_NAME.equals(token) && "commands".equals(parser.getCurrentName())) {
                parser.nextToken(); // ArrayStart
                return;
            }
        }

        throw new IllegalArgumentException("Commands file is invalid");
    }

    @Override
    public void close() throws IOException {
        this.parser.close();
    }

    @Override
    public Command readNextCommand() throws IOException, EOFException {
        while (!parser.isClosed()) {
            if (parser.nextToken() == JsonToken.START_OBJECT) {
                JsonNode node = mapper.readTree(parser);
                return commandFrom(node);
            }
        }

        throw new EOFException("No more commands");
    }

    private Command commandFrom(JsonNode node) {
        String commandType = node.get("type").asText();
        ObjectMapper mapper = new ObjectMapper();

        if (!COMMAND_MAP.containsKey(commandType)) {
            throw new IllegalArgumentException("Invalid command type");
        }

        return mapper.convertValue(node, COMMAND_MAP.get(commandType));
    }
}
