package serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import java.awt.*;
import java.io.IOException;

public class PaintDeserializer extends JsonDeserializer<Paint> {
    @Override
    public Paint deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        IntNode node = jsonParser.getCodec().readTree(jsonParser);
        int rgb = node.intValue();

        return new Color(rgb);
    }
}
