package serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.*;
import java.io.IOException;

public class StrokeDeserializer extends JsonDeserializer<Stroke> {
    @Override
    public Stroke deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
        ObjectNode root = objectMapper.readTree(jsonParser);

        float lineWidth = Float.parseFloat(root.path("lineWidth").asText("1.0"));
        int endCap = root.path("endCap").asInt(BasicStroke.CAP_SQUARE);
        int lineJoin = root.path("lineJoin").asInt(BasicStroke.JOIN_MITER);
        float miterLimit = Float.parseFloat(root.path("miterLimit").asText("10.0"));
        // Handle other properties...
//        String name = root.path("name").asText("null");
//        if (name != null) {
//            // Use the "name" property for additional logic, if needed
//            // Example: You might use the name to modify the stroke properties
//            // For now, let's just print it
//            System.out.println("Stroke name: " + name);
//        }

        // Create a new BasicStroke with the extracted properties
        return new BasicStroke(lineWidth, endCap, lineJoin, miterLimit);
        //return new BasicStroke(3f);
    }
}
